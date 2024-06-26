FROM openjdk:21-jre-slim
WORKDIR /app
COPY target/birds-sighting-service.jar /app
CMD ["java", "-jar", "birds-sighting-service.jar"]