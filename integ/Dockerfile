FROM openjdk:8-jdk-alpine

LABEL maintainer="steliomo@gmail.com" 

VOLUME [ "/opt/grocery/data" ]

EXPOSE 8080

ARG jar_file

ADD ${jar_file} grocery-integ.jar

# Prod
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-Xdebug", "-jar", "grocery-integ.jar" ]

# Dev
#ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-Xdebug", "-Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n", "-jar", "grocery-integ.jar" ]
