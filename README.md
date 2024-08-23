# Performance Scorecard

## Description

The **Performance Scorecard** project is a microservices-based application designed to track employee performance. It includes various APIs that manage information across different databases, allowing organizations to effectively monitor productivity.

- **Developed an employee performance tracking microservice** with multiple APIs for managing data across different databases, enabling organizations to oversee and improve productivity.
- **Implemented secure user authentication** using JSON Web Tokens (JWT) and established role-based access control through Spring Security to ensure appropriate user privileges.

## Setup MySQL for Development Mode

### Without Docker

If you are running the application locally without Docker, follow these steps:

1. **Install Maven and Build the Source Code**
   - Run the command: `mvn clean install` to build the source code into a `.jar` file.

2. **Install Java Development Kit (JDK)**
   - Ensure that JDK is installed to run the `.jar` file.
   - Run the command: `java -jar score-tracker.jar` to execute the `.jar` file.

### Using Docker

To use Docker for development, follow these steps:

1. **Create a Dockerfile**
   - The Dockerfile is divided into two stages to build the image:

     **Stage 1:**
     - Base Image: `maven:3.8.3-openjdk-17`
     - Copy source code into `/app` and run: `mvn clean install` to build the `.jar` file.

     **Stage 2:**
     - Base Image: `openjdk:17-alpine`
     - Copy the `.jar` file from the build stage.
     - Run the application using: `java -jar score-tracker.jar`.

   - Dockerfile content:
     ```dockerfile
     FROM maven:3.8.3-openjdk-17 as build
     WORKDIR /app
     COPY . .
     RUN mvn clean install

     FROM openjdk:17-alpine
     COPY --from=build /app/target/score-tracker-0.0.1-SNAPSHOT.jar score-tracker.jar
     EXPOSE 8080
     ENTRYPOINT exec java -jar score-tracker.jar
     ```

2. **Build the Docker Image**
   - Run the command: `docker build -t score-tracker:1.0 .`
   - `-t` (tag) specifies the name and version of the image. Here, the image name is `score-tracker` and the version is `1.0`.

3. **Check the Created Docker Image**
   - Run the command: `docker images` to list the created images.

4. **Start the Application**
   - Run the command: `docker-compose up` to start both the database and the Spring application.
