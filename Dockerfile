FROM openjdk:17-alpine
WORKDIR /app
VOLUME /tmp
COPY certificate.p12 .
COPY target/VideoReceiversManager-*.war app.war
CMD ["java", "-Dspring.profiles.active=docker", "-jar","app.war"]