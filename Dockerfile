FROM openjdk:11
EXPOSE 8081
COPY /build/libs/power-up-arquetipo-0.0.1-SNAPSHOT.jar users.jar
ENTRYPOINT ["java","-jar","users.jar"]