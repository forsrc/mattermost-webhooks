#FROM openjdk:8-jdk-alpine
FROM forsrc/centos-jdk-ssh
VOLUME /tmp
ARG JAR_FILE
ARG RES_FILE
RUN mkdir app
ADD ${JAR_FILE} app/app.jar
RUN mkdir app/config
ADD ${RES_FILE} app/config
ADD entrypoint.sh /
RUN chmod -R u+x /app
RUN chmod u+x /entrypoint.sh

EXPOSE 8077

ENTRYPOINT /entrypoint.sh
