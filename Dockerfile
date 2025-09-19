FROM openjdk:21

WORKDIR /app

COPY . /app

RUN ./mvnw -B clean package -DskipTests

EXPOSE 8084

CMD ["java", "-jar", "target/agendaApi-0.0.1-SNAPSHOT.jar"]