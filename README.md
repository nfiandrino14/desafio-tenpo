# Desafio Tenpo ->  spring-boot-docker-rest-api
Building RESTFul API Services using spring boot, PostgreSQL and Swagger Documentation with containerization using Docker

Steps for executing :

1. Clone/Download the repository.

   		git clone https://github.com/nfiandrino14/desafio-tenpo.git

2. Navigate to folder project.

   		cd desafio-tenpo

3. Install mvn dependencies.

		mvn clean install

4. Open the terminal and go to the directory where docker-compose.yml is located and run the below command and will build the PostgreSQL and Spring Boot Rest API Containers.

   		docker-compose up

5. Open another terminal and run the below command to get the list of running containers, if you want to use same terminal in futher you can run docker-compose up -d (detached mode).

   		docker ps

5. After executing above steps without any errors and docker containers are up and running, open the browser and navigate to below url:

   		http://localhost:8080/swagger-ui.html#/


6. You can also use the api with this postman collection: https://www.getpostman.com/collections/3cd217cc09e0378dda94
