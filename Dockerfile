# FROM ubuntu:latest AS build
#
# RUN apt-get update
# RUN apt-get install openjdk-17-jdk -y
# COPY . .
#
# RUN apt-get install maven -y
# RUN mvn clean install
#
# FROM openjdk:17-jdk-slim
#
# EXPOSE 8001
#
# COPY --from=build /target/scraping-animeflv-0.0.1-SNAPSHOT.jar app.jar
#
# ENTRYPOINT [ "java", "-jar", "app.jar" ]

# Importing JDK and copying required files
FROM openjdk:17-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final Docker image using OpenJDK 19
FROM openjdk:17-jdk
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /app/target/scraping-animeflv-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8001