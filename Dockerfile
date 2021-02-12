FROM openjdk:11
COPY src/scripts/start.sh /start.sh
COPY /target/aws-signer-0.0.1.jar /app.jar
CMD ["/bin/bash", "/start.sh"]