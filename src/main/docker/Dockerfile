FROM openjdk:8-jre-alpine
MAINTAINER Maksym Stepanenko <stepanenkomaksi@gmail.com>
RUN  apk update && apk upgrade && apk add netcat-openbsd && apk add bash && apk add tcpdump
RUN mkdir -p /usr/local/auth
RUN mkdir -p /usr/local/auth/pcap
ADD @project.build.finalName@.jar /usr/local/auth/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh