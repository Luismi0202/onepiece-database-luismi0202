# Etapa de construcción
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY . .
# Garantiza permisos del wrapper
RUN chmod +x gradlew
# Construcción sin tests, con más detalle de logs
RUN ./gradlew clean build -x test --no-daemon --stacktrace --info

# Etapa de ejecución
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copia sólo el JAR final (ajusta el nombre si cambia)
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
