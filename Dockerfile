# =========================
# 1) BUILD STAGE (Gradle)
# =========================
FROM gradle:8.7-jdk21 AS build
WORKDIR /app

# Copia primero los archivos de build para cachear dependencias
# (ajusta los nombres si usas Maven; más abajo te dejo alternativa)
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
COPY gradlew gradlew

# (anti-CRLF y permisos de ejecución)
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Pre-resuelve dependencias (mejora la cache)
RUN ./gradlew --no-daemon dependencies || true

# Copia el código fuente
COPY src src

# Compila el fat JAR (salta tests en CI/CD para ir más rápido)
RUN ./gradlew --no-daemon clean build -x test

# =========================
# 2) RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Copiamos el jar construido
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Render asigna el puerto en la variable PORT.
# Spring lo recogerá con -Dserver.port. Exponemos 8080 por convención.
EXPOSE 8080

# (OPCIONAL) Variables de entorno documentadas (Render las inyecta en runtime)
# ENV SPRING_DATASOURCE_URL=""
# ENV SPRING_DATASOURCE_USERNAME=""
# ENV SPRING_DATASOURCE_PASSWORD=""

# Arranque:
# -Dserver.port=${PORT:-8080} -> usa el puerto de Render o 8080 en local
# No fijamos las credenciales aquí; las leerá Spring de las env vars.
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]