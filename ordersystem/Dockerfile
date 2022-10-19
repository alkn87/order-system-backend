# Set a variable that can be used in all stages.
ARG BUILD_HOME=/gradle-docker-builder

# Gradle image for the build stage.
FROM gradle:7.5.1-jdk17-alpine as build-image

# Set the working directory.
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME

# Copy project into the build container.
COPY --chown=gradle:gradle . $APP_HOME/

# Build the application.
RUN gradle --no-daemon build

# Java image for the application to run in.
FROM openjdk:17.0.2-jdk-oraclelinux8

# Copy the jar file in and name it app.jar.
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
COPY --from=build-image $APP_HOME/build/libs/*.jar app.jar

# The command to run when the container starts.
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
