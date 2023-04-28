FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /home/app
	
COPY src /home/app/src

COPY src/main/resources/application*.properties /home/app/src/main/resources/

# COPY pom.xml /home/app


# For following variable refer the pom.xml file 'name' tag
ARG JAR_FILE=/home/app/target/job_portal-0.0.1-SNAPSHOT.jar

COPY --from=build /home/app/src/main/resources/application*.properties /opt/app/src/main/resources/

WORKDIR /opt/app
	

COPY --from=build ${JAR_FILE} app.jar

RUN mvn -f /home/app/pom.xml clean package -DskipTests

CMD java -jar app.jar
CMD "echo" "hello docker"