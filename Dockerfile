#
FROM maven:3.8.1 AS build

WORKDIR /home/app
	
COPY src /home/app/src

COPY src/main/resources/application*.properties /home/app/src/main/resources/

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

	

COPY --from=build ${JAR_FILE} app.jar

CMD java -jar app.jar
