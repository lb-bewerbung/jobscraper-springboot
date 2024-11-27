## Was ist die Aufgabe des Projekts
Die Spring Boot Applikation zielt darauf ab, die Effizienz der Jobsuche zu verbessern. Sie sammelt regelmäßig Stellenangebote nach
festgelegten Suchbegriffen und organisiert deren Inhalte in einer Datenbank. Mithilfe einer simplen Weboberfläche können spezifische
Stellenangebote in einer erweiterten Suchfunktion identifiziert werden. (Verlinkung zu Bild)

## UML
<p align="center">
  <a href="diagrams/CI-CD-Flowchart.png"><img src="diagrams/CI-CD-Flowchart.png" alt="CI-CD-Flowchart.png" height="150" width="200"></a>
  <a href="diagrams/System-architecture.png"><img src="diagrams/System-architecture.png" alt="diagrams/System-architecture.png" height="150" width="200"></a>
</p>

## Verwendete Technologien
**Web-Code**<br>
Java Spring Boot

**Build**<br>
Maven

**Container**<br>
Docker

**Automatisierungsserver**<br>
Jenkins (siehe <a href="https://github.com/lb-bewerbung/jobscraper-jenkins">Jobscraper-Jenkins</a>)

**Konfigurationsmanagement**<br>
Ansible (siehe <a href="https://github.com/lb-bewerbung/jobscraper-ansible">Jobscraper-Ansible</a>)

**Server / Webserver**<br>
Ubuntu, Apache

## Erweiterungsmöglichkeiten
Es werden nachfolgend einige Optionen dargestellt, wie die Applikation und die CI-CD-Prozesse in zukünftigen Iterationen erweitert werden können.

**App:**
- Unittests für die alle Funktionen der App
- Integrationstests für Datenbank und Website
- SQL-Datenbank für mehr Effizienz und Stabilität mit wachsendem Datenbestand und -umfang

**CI-CD**
- Image Versionen mit CommitId erzeugen
- Pipeline mit Git-tags steuern
- Docker Image Speicherung und Deploy via Dockerhub
- Metriken Report und Pipeline Steuerung via ChatOps (Slack, Teams etc.)
- Container Orchestrierung mit Kubernetes
- 
**Website:**
- Notizen und Marker für Jobeinträge