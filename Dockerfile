# =========================
# 1) BUILD STAGE
# =========================
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia TODO lo necesario para Gradle PRIMERO
COPY gradle gradle
COPY gradlew gradlew
COPY gradle.properties .
COPY settings.gradle.kts .
COPY build.gradle.kts .

# Asegura permisos ejecutables
RUN chmod +x gradlew

# Descarga dependencias (cachea esta capa)
RUN ./gradlew --no-daemon dependencies 2>/dev/null || true

# Ahora copia el código
COPY src src

# Construye el JAR con logs detallados
RUN ./gradlew --no-daemon clean bootJar -x test --info

# =========================
# 2) RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]
