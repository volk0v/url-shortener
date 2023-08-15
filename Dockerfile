FROM openjdk:17
LABEL authors="volk0v"
MAINTAINER "volk0v"

ADD . /app/
WORKDIR /app

RUN ./mvnw clean package  \
    && mv target/url-shortener-*.jar app.jar \
    && rm -rf target

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]