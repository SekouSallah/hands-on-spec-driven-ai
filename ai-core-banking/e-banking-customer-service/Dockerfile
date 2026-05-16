FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="AI Core Banking Workshop"
LABEL service="customer-service"

WORKDIR /app

COPY ./target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
