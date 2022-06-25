FROM openjdk:17-alpine
WORKDIR /app
VOLUME /tmp
COPY target/VideoReceiversManager-*.war app.war
CMD ["java", "-Dspring.profiles.active=docker", "-jar","app.war"]