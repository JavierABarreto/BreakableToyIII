FROM eclipse-temurin:23 as build
WORKDIR /backend

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:23

WORKDIR /backend
COPY --from=build /backend/build/libs/SpotifyApp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/backend/app.jar"]