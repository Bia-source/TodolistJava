FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk:latest -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:latest

EXPOSE 8080

COPY --from=build /target/todolist-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]