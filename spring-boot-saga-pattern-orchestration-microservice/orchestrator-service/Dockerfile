FROM maven:3.9.5-eclipse-temurin-21-alpine AS build

COPY ./src /usr/src/app/src
COPY ./pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean install -P prod -D skipTests

FROM openjdk:21-slim

COPY --from=build /usr/src/app/target/*.jar /usr/local/lib/app.jar

EXPOSE 8009

ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]