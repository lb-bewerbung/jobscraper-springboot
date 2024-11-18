# Verwende ein Java-Laufzeit-Basisimage
FROM openjdk:17-jdk-slim

# Arbeitsverzeichnis im Container setzen
WORKDIR /app

# Kopiere das JAR-File ins Arbeitsverzeichnis
COPY target/*.jar app.jar

# Exponiere den Port, auf dem dein Spring Boot läuft (Standard: 8080)
EXPOSE 8080