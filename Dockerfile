FROM maven:3-jdk-8

ADD . /checker
WORKDIR /checker

CMD ["mvn", "compile", "jetty:run"]
