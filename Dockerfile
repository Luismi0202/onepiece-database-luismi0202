# Etapa de construcción
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Wrapper y caché de dependencias
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

# Archivos de build
COPY build.gradle* settings.gradle* ./
RUN ./gradlew --version

# Código fuente
COPY src src

# Memoria y paralelismo bajos para Render
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx512m -Dfile.encoding=UTF-8 -Dorg.gradle.workers.max=1"

# Compila solo el jar ejecutable de Spring Boot con logs claros
RUN ./gradlew clean bootJar --no-daemon --stacktrace --info

# Etapa de ejecución
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia el jar generado (cualquier versión)
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto y perfil
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java","-jar","app.jar"]
