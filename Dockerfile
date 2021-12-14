FROM adoptopenjdk/openjdk15-openj9:alpine-slim
RUN mkdir /opt/app
COPY target/disqo-demo-1.0.jar /opt/app/
ENTRYPOINT java $JAVA_OPTS -Xshareclasses -Xquickstart -jar /opt/app/disqo-demo-1.0.jar