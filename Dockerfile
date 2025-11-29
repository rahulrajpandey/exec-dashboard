# -----------------------------
# 1. Build Stage (Maven + JDK 25)
# -----------------------------
FROM eclipse-temurin:25-jdk AS build

WORKDIR /workspace

# Install Maven (Ubuntu-based image)
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copy pom files first (better Docker layer caching)
COPY pom.xml .
COPY backend/pom.xml ./backend/

# Copy source code
COPY . .

# Build backend only
RUN mvn -pl backend -am clean package -DskipTests

# -----------------------------
# 2. Runtime Stage (JRE 25 Alpine)
# -----------------------------
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /workspace/backend/target/backend-1.0-SNAPSHOT.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
