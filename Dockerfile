FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY src src
ENV GRADLE_OPTS="-Xmx2048m -Dfile.encoding=UTF-8"
RUN ./gradlew clean bootJar --no-daemon --info --stacktrace

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
