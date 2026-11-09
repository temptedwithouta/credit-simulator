FROM maven:3.9.11-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml ./pom.xml
COPY src ./src

RUN mvn -B clean package assembly:single

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/target/credit-simulator-1.0-SNAPSHOT-jar-with-dependencies.jar ./credit-simulator.jar

ENTRYPOINT ["java", "-jar", "credit-simulator.jar"]