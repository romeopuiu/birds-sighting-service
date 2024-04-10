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

 And then run the command :

  docker compose up

<h5>Stop the Spring Boot birds-sighting-service application:</h5>
  docker compose down

<h5>Testing Spring Boot birds-sighting-service application  using Postman Tool:</h5>
After starting the application open Postman:


![save-bird](https://github.com/romeopuiu/birds-sighting-service/assets/49139565/fea30c30-0b85-4363-8522-517eea6e742d)



![get_all_birds](https://github.com/romeopuiu/birds-sighting-service/assets/49139565/d58a5b0b-d2c4-43a0-b46e-a7a6c60eeee7)


![bird-by-color](https://github.com/romeopuiu/birds-sighting-service/assets/49139565/1838854f-6de6-4363-940b-8dfa9af0cf12)



![bird-by-name](https://github.com/romeopuiu/birds-sighting-service/assets/49139565/7f9127d5-912a-45ec-92c4-f13f4d4a4267)


![delete-bird](https://github.com/romeopuiu/birds-sighting-service/assets/49139565/af7617e6-5042-4de1-80f8-057027cba9c3)




![update-bird](https://github.com/romeopuiu/birds-sighting-service/assets/49139565/af4c6dfd-c677-4b1e-ab09-db0f0b64516d)



