FROM openjdk:20-jdk-slim
COPY /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080

