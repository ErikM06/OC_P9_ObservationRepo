FROM openjdk:11
WORKDIR /usr/app
COPY build/libs/observationRepo-0.0.1-SNAPSHOT.jar mediscreen-notes-repo-microService.jar
CMD  java -jar mediscreen-notes-repo-microService.jar

