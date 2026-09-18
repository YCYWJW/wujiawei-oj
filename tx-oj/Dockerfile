FROM maven:3.9-eclipse-temurin-17 as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Run the web service on container startup.
CMD ["java","-jar","/app/target/tx-oj-1.0-SNAPSHOT.jar","--spring.profiles.active=prod"]