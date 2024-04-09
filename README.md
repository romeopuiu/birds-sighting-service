Service supports CRUD operations for birds and sightings
<h3>API documentation:</h3>
http://localhost:8080/swagger-ui/index.html

Get the source code:

Clone the repository:
git clone https://github.com/romeopuiu/birds-sighting-service.git

<b>Prerequisites:</b>
* Spring Boot 2.7.0
* Java 11
* Maven
* PostgreSQL Database
* Lombok 1.18.6 to generate boilerplate code

Spring Boot birds-sighting-service:

1. Navigate to the birds-sighting-service directory:
   cd birds-sighting-service

2. Build the app:
   mvn clean install
3. Run Spring Boot birds-sighting-service
   mvn spring-boot:run


<h4>PostgreSQL Database</h4>
1. Modify application.yml:
spring:
   application:
   name: birds-sighting-service
   datasource:
   driver-class-name: org.postgresql.Driver
   url: jdbc:postgresql://localhost:5432/YOUR_DATABASE_NAME
   username: YOUR_DATABASE_USERNAME
   password: YOUR_DATABASE_PASSWORD

jpa:
properties:
hibernate:
dialect: org.hibernate.dialect.PostgreSQLDialect
show-sql: true
generate-ddl: true


<h5>Using Docker compose to run Spring Boot birds-sighting-service:</h5>
Before using docker compose have to modify file docker-compose
at line :  SPRING_DATASOURCE_PASSWORD: YOUR_DATABASE_PASSWORD


  docker compose up
<h5>Stop the Spring Boot birds-sighting-service application:</h5>
  docker compose down

<h5>Testing Spring Boot birds-sighting-service application  using Postman Tool:</h5>
After starting the application open Postman:

