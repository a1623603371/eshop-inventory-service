FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
<<<<<<< HEAD
ADD eshop.inventory.service-1.0-SNAPSHOT.jar app.jar
=======
ADD eshop-inventory-service-1.0-SNAPSHOT.jar app.jar
>>>>>>> 52a928a7fe8582ba4963b6e549e97f8a0ba283a4
#RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8773
