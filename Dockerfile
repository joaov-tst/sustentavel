# Stage 1: Clonar e inicializar o submódulo
FROM alpine/git AS submodule
WORKDIR /repo
COPY .git .git
COPY .gitmodules .gitmodules
RUN git submodule update --init --recursive

# Stage 2: Build da aplicação Java (APENAS src/backend)
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copia SOMENTE a pasta src/backend do submódulo
COPY --from=submodule /repo/psg-si-2025-2-p5-tias-7679101-gestao-sustentavel-de-restaurante/src/backend/ .
RUN mvn clean package -DskipTests

# Stage 3: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]