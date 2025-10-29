FROM openjdk:25-jdk-slim

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven && mvn clean package assembly:single

CMD ["java", "-jar", "target/credit-simulator-1.0-SNAPSHOT-jar-with-dependencies.jar"]