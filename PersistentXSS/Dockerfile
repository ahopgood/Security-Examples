FROM eclipse-temurin:21.0.11_10-jre-noble

WORKDIR /app
EXPOSE 8080

COPY target/PersistentXSS-*.jar /app/service.jar
COPY docker-start.sh /app/start.sh

RUN apt-get update -q -y \
    && apt-get upgrade -q -y \
    && apt autoremove -q -y

ENTRYPOINT ["bash", "/app/start.sh"]