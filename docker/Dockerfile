FROM maven:3.8.1-openjdk-17 AS build
ENV APP_HOME=/app/
WORKDIR $APP_HOME
COPY ../pom.xml $APP_HOME
COPY ../src $APP_HOME/src/
RUN mvn clean -e -B package


FROM openjdk:17-oracle
ENV APP_HOME=/app/
ENV ARTIFACT_NAME=crud_animals-1.0-SNAPSHOT-jar-with-dependencies.jar
ARG JAR_FILE=$APP_HOME/target/$ARTIFACT_NAME
COPY --from=build $JAR_FILE /opt/crud/app.jar
ENTRYPOINT ["java", "-jar", "/opt/crud/app.jar"]