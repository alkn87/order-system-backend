# Set a variable that can be used in all stages.
ARG BUILD_HOME=/gradle-docker-builder

# Gradle image for the build stage.
FROM gradle:8-jdk17-alpine AS build-image

# Set the working directory.
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME

# Copy project into the build container.
COPY --chown=gradle:gradle . $APP_HOME/

# Build the application.
RUN gradle --no-daemon :station-service:buildLayers

FROM openjdk:17-alpine

# Copy the jar file in and name it app.jar.
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME/station-service

COPY --from=build-image $APP_HOME/build/docker/main/layers/libs /libs
COPY --from=build-image $APP_HOME/build/docker/main/layers/classes /classes
COPY --from=build-image $APP_HOME/build/docker/main/layers/resources /resources
COPY --from=build-image $APP_HOME/build/docker/main/layers/application.jar application.jar
EXPOSE 8182
ENTRYPOINT ["java", "-jar", "/application.jar"]
