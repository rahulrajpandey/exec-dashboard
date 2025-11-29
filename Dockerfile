# ============================================================
# 1. FRONTEND BUILD STAGE (Node + Vite build)
# ============================================================
FROM node:20-alpine AS frontend-build

WORKDIR /frontend

# Copy dependency files first for caching
COPY frontend/package*.json ./

# Deterministic install (uses package-lock)
RUN npm ci

# Copy rest of frontend source
COPY frontend/ .

# check for badly formatted code
RUN npm run lint && npm run format

# Build optimized production bundle
RUN npm run build

# Cleanup node_modules and npm cache (reduces build layer size)
RUN rm -rf node_modules /root/.npm


# ============================================================
# 2. BACKEND BUILD STAGE (Maven + JDK 25)
# ============================================================
FROM eclipse-temurin:25-jdk AS backend-build

WORKDIR /workspace

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copy top-level pom first (caching)
COPY pom.xml .

# Copy backend pom (caching)
COPY backend/pom.xml backend/

# Download dependencies BEFORE copying full backend source
RUN mvn -pl backend -am dependency:go-offline

# Copy backend source
COPY backend backend

# Copy built frontend assets into backend static resources
COPY --from=frontend-build /frontend/dist backend/src/main/resources/static/

# Build the Spring Boot JAR
RUN mvn -pl backend -am clean package -DskipTests

# ============================================================
# 3. RUNTIME STAGE (small JRE-only image)
# ============================================================
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copy final JAR
COPY --from=backend-build /workspace/backend/target/backend-1.0-SNAPSHOT.jar app.jar

# Health check endpoint
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]