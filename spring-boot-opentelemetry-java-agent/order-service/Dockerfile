## Version 1
#FROM maven:3.9.5-eclipse-temurin-21-alpine AS build
#COPY ./src /usr/src/app/src
#COPY ./pom.xml /usr/src/app
#RUN mvn -f /usr/src/app/pom.xml clean install -DskipTests
#FROM openjdk:21-slim
#RUN apt-get update && apt-get install -y curl && \
#    mkdir -p /otelagent && \
#    curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.32.0/opentelemetry-javaagent.jar -o /otelagent/opentelemetry-javaagent.jar
#COPY --from=build /usr/src/app/target/*.jar /usr/local/lib/app.jar
#EXPOSE 8081
#ENTRYPOINT ["java", "-javaagent:/otelagent/opentelemetry-javaagent.jar", "-jar", "/usr/local/lib/app.jar"]


## Version 2
FROM maven:3.9.5-eclipse-temurin-21-alpine AS build
COPY ./src /usr/src/app/src
COPY ./pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean install -DskipTests
FROM openjdk:21-slim
COPY --from=build /usr/src/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]