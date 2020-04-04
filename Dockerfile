FROM java:8-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENV JAVA_OPTIONS    -Xmx256m
ENV TIME_ZONE Asia/Singapore

ENV SPRING_PROFILES_ACTIVE dt

RUN echo "$TIME_ZONE" > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata

WORKDIR /app

#TESTING
EXPOSE 12142

ENTRYPOINT ["java","-jar","/app.jar","-Djava.security.egd=file:/dev/./urandom"]

