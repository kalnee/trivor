FROM java:8

RUN mkdir /app
WORKDIR /app
ADD build/libs/trivor-insights-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080 8080

ENTRYPOINT ["java", "-jar", "trivor-insights-0.0.1-SNAPSHOT.jar"]
