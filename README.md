# customers-service
![build](https://travis-ci.org/rso-vaje-6315/customers-service.svg)

## Usage

### Library

Import dependency

```xml
<dependency>
    <groupId>si.rso.customers</groupId>
    <artifactId>customers-service-lib-v1</artifactId>
    <version>${rso-customers.version}</version>
</dependency>
```

### Docker container

Download image

```
docker pull rso6315/customers-service:latest
```

Start docker container

```
docker run -d -p <PORT>:8080
    -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://<DB_HOST>:<DB_PORT>/<DB_NAME>
    -e ENV KUMULUZEE_DATASOURCES0_USERNAME=<DB_USER>
    -e ENV KUMULUZEE_DATASOURCES0_PASSWORD=<DB_PASS>
    -e KUMULUZEE_SECURITY_KEYCLOAK_CLIENT-SECRET=<KEYCLOAK_CLIENT_SECRET>
    --name=customers-service
    rso6315/customers-service:latest
```

Service will be available at: `http://<HOST>:<PORT>/v1`

Openapi JSON spec: `http://<HOST>:<PORT>/api-specs/v1/openapi.json`

Openapi UI: `http://<HOST>:<PORT>/api-specs/ui`


## Build

First you need to package project:

```mvn clean package -P rso```

Then you can build docker image:

```docker build -t rso6315/customers-service:latest .```

Finally, deploy library module to Nexus:

```
mvn deploy -DskipTests=true -P rso,deploy-lib
```