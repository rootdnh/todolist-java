FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdj-17-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

EXPOSE 8080

COPY --from=build /target/todolis-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]