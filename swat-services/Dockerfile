# Stage 1: Build the application
FROM gradle:7.5.1-jdk17 AS build

WORKDIR /app

COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle /app/
COPY settings.gradle /app/

# Give gradle permissions
RUN chmod +x /app/gradlew

RUN ./gradlew build -x test --no-daemon

COPY src /app/src

RUN ./gradlew bootJar -x test --no-daemon

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/swat-1.0.0-SNAPSHOT.jar swat-1.0.0-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "swat-1.0.0-SNAPSHOT.jar"]
