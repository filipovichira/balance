FROM openjdk:14-jdk-alpine
COPY build/libs/balance-*.jar balance.jar
ENTRYPOINT ["java","-jar","balance.jar"]
EXPOSE 8080
