# Use the official openjdk image as the base image
FROM openjdk:11-jdk-slim

# Set the working directory in the Docker container
WORKDIR /app

# Copy the HelloWorld.java file into the Docker container
COPY src .

# Compile the HelloWorld.java file
RUN javac /app/src/main/java/ru/dz/Main.java

# Specify the command to run when the Docker container starts
CMD ["java", "Main"]
