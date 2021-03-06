# syntax=docker/dockerfile:1
FROM openjdk:11-jre-slim
COPY /target/otus-istyazhkina-diploma-*.jar /app/app.jar
WORKDIR /app

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]