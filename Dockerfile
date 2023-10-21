FROM maven:3.8.3-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean install


FROM openjdk:17-alpine
VOLUME /tmp
COPY --from=build /app/target/score-tracker-0.0.1-SNAPSHOT.jar score-tracker.jar
EXPOSE 8080
ENTRYPOINT exec java -jar score-tracker.jar
