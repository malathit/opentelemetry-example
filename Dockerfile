FROM maven:3.8.1-openjdk-11-slim AS build-env
WORKDIR /app
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.3.1/opentelemetry-javaagent-all.jar .
ADD spring/pom.xml /app/pom.xml
RUN mvn dependency:go-offline
COPY spring/src /app/src
RUN mvn package

FROM gcr.io/distroless/java:11
COPY --from=build-env /app/target/*.jar /app/main.jar
COPY --from=build-env /app/opentelemetry-javaagent-all.jar /app/opentelemetry-javaagent-all.jar
WORKDIR /app
ENV JAVA_TOOL_OPTIONS "-javaagent:./opentelemetry-javaagent-all.jar -Dotel.resource.attributes=service.name=employee-api"
CMD ["main.jar"]