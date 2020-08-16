FROM openjdk:8u201-jdk-alpine3.9
MAINTAINER Samuel Addico <samuel@financemobile.app>
ADD target/fmassets.jar fmassets.jar
ENTRYPOINT ["java", "-jar", "/fmassets.jar"]
EXPOSE 8084