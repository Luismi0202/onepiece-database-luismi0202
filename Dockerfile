# Etapa de construcción
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copiar solo lo necesario primero para aprovechar caché
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

COPY build.gradle* settings.gradle* ./
# (Si usas libs.versions.toml u otros archivos de config, añádelos aquí)

# Comprobar wrapper y versión
RUN ./gradlew --version

# Copiar código fuente
COPY src src

# Opcional: variables de memoria (ajusta si falla por heap)
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx512m -XX:MaxMetaspaceSize=256m"

# Build con más detalle
RUN ./gradlew clean build -x test --no-daemon --stacktrace --debug

# Etapa de ejecución
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
