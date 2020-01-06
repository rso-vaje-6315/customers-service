FROM openjdk:11-jre-slim

ENV JAVA_ENV=PRODUCTION
ENV KUMULUZEE_ENV_NAME=prod
ENV KUMULUZEE_ENV_PROD=true
ENV KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://localhost:5432/customers-service
ENV KUMULUZEE_DATASOURCES0_USERNAME=not_set
ENV KUMULUZEE_DATASOURCES0_PASSWORD=not_set
ENV KUMULUZEE_DISCOVERY_CONSUL_AGENT=http://localhost:8500
ENV KUMULUZEE_CONFIG_CONSUL_AGENT=http://localhost:8500

ENV KC_AUTH_CLIENT-SECRET=not_set

RUN mkdir /app
WORKDIR /app

ADD ./api/v1/target/customers-service.jar /app

EXPOSE 8080

CMD ["java", "-jar", "customers-service.jar"]
