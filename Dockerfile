#
FROM maven:3.8.1 AS build

WORKDIR /home/app

COPY src /home/app/src

COPY src/main/resources/application*.properties /home/app/src/main/resources/


ENV SPRING_DATASOURCE_URL=jdbc:postgresql://127.0.0.1:5432/jobportal
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=root
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_HIBERNATE_SHOW_SQL=true
ENV SERVER_ERROR_INCLUDE_BINDING_ERRORS=always
ENV SPRING_MAIN_ALLOW_CIRCULAR_REFERENCES=true
ENV SPRING_JPA_OPEN_IN_VIEW=false
ENV  REDIS_HOST=127.0.0.1
ENV REDIS_PORT=6379
ENV REDIS_PASSWORD= 

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -DskipTests
  
# Package stage
FROM adoptopenjdk/openjdk11:alpine-jre

# For following variable refer the pom.xml file 'name' tag
ARG JAR_FILE=/home/app/target/job_portal-0.0.1-SNAPSHOT.jar

#Get argument variable from docker-compose file


RUN apk update && \
    apk add --no-cache fontconfig ttf-dejavu && \
    rm -rf /var/cache/apk/*
WORKDIR /opt/app

COPY --from=build /home/app/src/main/resources/application*.properties /opt/app/src/main/resources/

COPY --from=build ${JAR_FILE} app.jar

CMD java -jar app.jar

