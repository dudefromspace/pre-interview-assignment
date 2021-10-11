FROM openjdk:8-jdk-alpine
MAINTAINER Sauveek Sen sauveeksen@gmail.com
COPY pre-interview-assignment-backend/target/pre-interview-assignment-backend-1.0.0.jar pre-interview-assignment-backend-1.0.0.jar
ENTRYPOINT ["java","-jar","/pre-interview-assignment-backend-1.0.0.jar"]