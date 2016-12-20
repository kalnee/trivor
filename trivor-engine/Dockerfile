FROM java:8

RUN mkdir /app
WORKDIR /app
ADD build/libs/trivor-engine-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080 8080

ENTRYPOINT ["java", "-jar", "trivor-engine-0.0.1-SNAPSHOT.jar"]
