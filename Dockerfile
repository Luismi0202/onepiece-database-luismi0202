FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew
COPY build.gradle* settings.gradle* gradle.properties ./
RUN ./gradlew --version
COPY src src
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx512m -Dfile.encoding=UTF-8 -Dorg.gradle.workers.max=1"
RUN --mount=type=cache,target=/root/.gradle/caches \
    --mount=type=cache,target=/root/.gradle/wrapper \
    ./gradlew bootJar --no-daemon --warning-mode all
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
