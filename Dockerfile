FROM java:8
MAINTAINER labsoft-2018

ADD target/accounts-0.0.1-SNAPSHOT-standalone.jar /srv/accounts.jar

EXPOSE 8740

CMD ["java", "-jar", "/srv/accounts.jar"]
