# Stage 1: Clonar e inicializar o submódulo
FROM alpine/git AS submodule
WORKDIR /repo
COPY .git .git
COPY .gitmodules .gitmodules
COPY src/backend src/backend
RUN git submodule update --init --recursive

# Stage 2: Build da aplicação Java que está DENTRO do submódulo
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copia TODO o conteúdo do submódulo (que agora está preenchido)
COPY --from=submodule /repo/src/backend/ .
# Agora sim o pom.xml está aqui e o Maven funciona
RUN mvn clean package -DskipTests

# Stage 3: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]