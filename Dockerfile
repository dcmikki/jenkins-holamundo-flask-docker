
FROM ubuntu:latest

RUN 	apt-get update && apt-get -y upgrade
RUN 	apt-get install -y python python-pip 
RUN 	mkdir -p /opt/app

COPY 	src/* /opt/app/
COPY 	requirements.txt /opt/app
RUN 	/usr/bin/pip install --upgrade -r /opt/app/requirements.txt
COPY	docker-entrypoint.sh /

ENTRYPOINT 	"/docker-entrypoint.sh"
EXPOSE	5000
