FROM openjdk:17
LABEL authors="erik-pc"
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]