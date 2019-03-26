FROM mysql:5.6

LABEL maintainer="steliomo@gmail.com"

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=grocery
ENV MYSQL_USER=grocery
ENV MYSQL_PASSWORD=grocery
ENV MYSQL_DATA_DIRECTORY=/opt/grocery/data/system/mysql

ADD mysql.cnf /root/.my.cnf
ADD localtime /etc/localtime

COPY run.sh /run.sh

VOLUME /opt/grocery/data

EXPOSE 3306

CMD ["/run.sh"]