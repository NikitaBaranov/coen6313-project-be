FROM java:8
VOLUME /tmp
ADD target/coen6313-project-0.1.0.jar app.jar
EXPOSE 8080
RUN bash -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]