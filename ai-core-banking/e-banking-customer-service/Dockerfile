FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="AI Core Banking Workshop"
LABEL service="customer-service"

WORKDIR /app

COPY target/e-banking-customer-service-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
