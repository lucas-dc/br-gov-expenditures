FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY target/br-gov-expenses-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]