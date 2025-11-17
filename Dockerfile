# Stage 1: Clonar o repositório do submódulo diretamente
FROM alpine/git AS submodule
WORKDIR /repo
# Clona diretamente o repositório do backend
RUN git clone https://github.com/ICEI-PUCMinas-PSG-SI-TI/psg-si-2025-2-p5-tias-7679101-gestao-sustentavel-de-restaurante backend

# Stage 2: Build da aplicação Java (APENAS src/backend)
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copia SOMENTE a pasta src/backend
COPY --from=submodule /repo/backend/src/backend/ .
RUN mvn clean package -DskipTests

# Stage 3: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]