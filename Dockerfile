FROM maven:3.9.9-eclipse-temurin-21 as build
RUN mkdir -p /src/app
WORKDIR /src/app
COPY . /src/app
RUN mvn clean package -DskipTests
FROM eclipse-temurin:21-jre-alpine
COPY --from=build /src/app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8181