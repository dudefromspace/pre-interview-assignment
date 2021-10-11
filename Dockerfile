FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER Sauveek Sen sauveeksen@gmail.com
COPY pre-interview-assignment-backend/target/pre-interview-assignment-backend-0.0.1-SNAPSHOT.jar pre-interview-assignment-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/pre-interview-assignment-backend-0.0.1-SNAPSHOT.jar"]