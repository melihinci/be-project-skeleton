you need docker, firstly you should pull versions images in postgres-docker-compose.yml
than run docker_run.bat
after that, you can start microservices under the project/.idea/runConfigurations folder by given order
-->evrekaserver
-->AuthService,FirstService
-->apigateways
please wait a few minutes while eureka is discovering microservices.
than you can call REST methods with postman (there is a postman collection in project) 
PS: each restart drops previous data in Databases.
