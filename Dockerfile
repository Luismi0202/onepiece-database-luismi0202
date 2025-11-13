# Etapa de construcción
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
# Modifica esta línea para saltar los tests
RUN gradle build --no-daemon -x test

# Etapa de ejecución
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copia el JAR construido desde la etapa anterior
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
# Expone el puerto en el que corre Spring Boot (por defecto 8080)
EXPOSE 8080
# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
