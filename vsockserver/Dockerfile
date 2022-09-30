FROM amazoncorretto:11

WORKDIR /app

COPY target/vsockserver-0.0.1-SNAPSHOT.jar ./app.jar

COPY libs/libhelloworld.so /usr/lib/
COPY libs/nsm-cli ./nsm-cli

CMD ["java", "-jar", "/app/app.jar"]
