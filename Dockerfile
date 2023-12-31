FROM openjdk:11-jre-slim

ARG JAR_FILE=build/libs/board-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} board-service.jar

ENTRYPOINT ["java", "-jar", "board-service.jar"]