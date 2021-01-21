FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app
EXPOSE 8080

COPY target/PersistentXSS-*.jar /app/service.jar
COPY docker-start.sh /app/start.sh

RUN apt-get update -q -y \
    && apt-get upgrade -q -y \
    && apt autoremove -q -y

ENTRYPOINT ["bash", "/app/start.sh"]