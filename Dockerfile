FROM maven:3.8.4-openjdk-17 AS build

RUN mkdir /code && \
    	cd /code && \
    	git clone https://github.com/karolina-wisniewska/DriverAPI.git . && \
    	mvn package && \
    	mkdir /opt/app && \
    	mv /code/target/driver-0.0.1-SNAPSHOT.jar /opt/app && \
    	cd / && \
    	rm -r /code

EXPOSE 8080

WORKDIR /opt/app

CMD java -jar /opt/app/driver-0.0.1-SNAPSHOT.jar
