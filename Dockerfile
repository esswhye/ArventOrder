FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar


ENV TIME_ZONE Asia/Singapore
ENV SPRING_PROFILES_ACTIVE dt

#TESTING
EXPOSE 12142

ENTRYPOINT ["java","-jar","/app.jar"]
