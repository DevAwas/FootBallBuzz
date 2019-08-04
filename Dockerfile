FROM openjdk:8-jdk-alpine

ADD build/libs/footballBuzz-0.1.0.jar footballapp.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

ENTRYPOINT ["java","-jar","/footballapp.jar"]


