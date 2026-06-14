FROM eclipse-temurin:8-jdk
EXPOSE 8080

VOLUME /tmp
ADD target/*.jar  /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx256m","/app.jar","--spring.profiles.active=prod"]