# =========================
# 1) BUILD STAGE
# =========================
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
COPY gradlew .

RUN chmod +x gradlew

COPY src src

RUN ./gradlew --no-daemon clean bootJar -x test

# =========================
# 2) RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]
