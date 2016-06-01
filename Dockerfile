FROM maven:3-jdk-8

ADD . /checker
WORKDIR /checker

RUN maven clojure:run