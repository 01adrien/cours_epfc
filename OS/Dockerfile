FROM ubuntu:latest

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install apache2 -y
RUN apt-get install nano

EXPOSE 80

CMD /usr/sbin/apache2ctl -D FOREGROUND


# docker run -d \
#   -it \
#   --name web \
#   --mount type=bind,source=./htdocs,target=/var/www/html \
#   apache