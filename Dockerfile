# Verwende ein Java-Laufzeit-Basisimage
FROM maven:3.8-openjdk-17-slim

# Arbeitsverzeichnis im Container setzen
WORKDIR /app

# Kopiere das JAR-File ins Arbeitsverzeichnis
COPY pom.xml .
COPY target/*.jar app.jar

# Exponiere den Port, auf dem dein Spring Boot läuft (Standard: 8080)
EXPOSE 8080