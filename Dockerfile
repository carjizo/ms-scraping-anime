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
FROM openjdk:17-jdk-alpine
MAINTAINER carloshernanjimenez876@gmail.com
VOLUME /tmp
COPY /target/scraping-animeflv-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]