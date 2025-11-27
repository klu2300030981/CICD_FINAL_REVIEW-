# Dockerfile for course_service (Place in: ./JFSD_BackendDeployment-main/course_service/Dockerfile)

# Stage 1: Build the JAR file
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the Final Runtime Image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# --- Expose Port 2003 ---
EXPOSE 2003
ENTRYPOINT ["java", "-jar", "app.jar"]