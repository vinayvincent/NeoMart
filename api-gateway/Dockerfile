FROM amazoncorretto:21
WORKDIR /app
COPY build/libs/*.jar api-gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]
