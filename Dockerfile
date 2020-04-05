#
# Build stage
#
FROM maven:3.6.0-jdk-8-slim AS build
COPY src /src
COPY pom.xml .
RUN mvn -f pom.xml clean package


#
# Package Stage
#
FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
COPY --from=build target/*-SNAPSHOT.jar app.jar

ENV JAVA_OPTIONS    -Xmx256m
ENV TIME_ZONE Asia/Singapore

ENV SPRING_PROFILES_ACTIVE dt

RUN echo "$TIME_ZONE" > /etc/timezone
#RUN dpkg-reconfigure -f noninteractive tzdata

WORKDIR /app

#TESTING
EXPOSE 12142

ENTRYPOINT ["java","-jar","/app.jar","-Djava.security.egd=file:/dev/./urandom"]

