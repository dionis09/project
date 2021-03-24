# syntax=docker/dockerfile:experimental
FROM maven:3.6.3-openjdk-14-slim as maven

ENV HOME=/home/usr/app

RUN mkdir -p $HOME

WORKDIR $HOME

ADD pom.xml $HOME

COPY . $HOME

RUN --mount=type=cache,target=/root/.m2 mvn -f pom.xml clean install -DskipTests
#----------------------------
FROM adoptopenjdk/openjdk14:jre-14.0.2_12-alpine

COPY --from=maven /home/usr/app/target/project-0.0.1-SNAPSHOT.jar project-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","project-0.0.1-SNAPSHOT.jar"]