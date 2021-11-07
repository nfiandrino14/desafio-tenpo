FROM openjdk:17
ADD target/desafio-tenpo-1.0.jar /usr/share/app.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/app.jar"]
EXPOSE 8080