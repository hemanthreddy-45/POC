# Example Dockerfile
FROM openjdk:11-jre-slim
COPY . /app
WORKDIR /app
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/myapp.jar"]