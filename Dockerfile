FROM maven:3.9.9-eclipse-temurin-21 as build
RUN mkdir -p /src/app
WORKDIR /src/app
# Copia o conteúdo do submódulo/src/backend
COPY psg-si-2025-2-p5-tias-7679101-gestao-sustentavel-de-restaurante/src/backend/ .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
COPY --from=build /src/app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8181