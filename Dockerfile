FROM maven:3.9.8-eclipse-temurin-21 as build
RUN mkdir -p /src/app
WORKDIR /src/app
COPY . /src/app
RUN mvn clean package -DskipTests
FROM openjdk:21-jdk-slim
COPY --from=build /src/app/target/genius.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8181
