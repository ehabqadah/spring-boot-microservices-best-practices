# spring-boot-microservices-best-practices
Best Practices for Developing Rest-Based Microservices with Spring-Boot
A service offers REST APIs for managing/consuming orders. 

## Owner

- Ehab Qadah

### Tools and Technologies Used ###
 * Spring Boot -2.4.0-SNAPSHOT

 * Maven 
 * MYSQL 
 * Swagger 
 * Flywaydb
 * Hibernate 5 
 * lighthouse-orm-commons
 * Docker and Docker Compose
 
 Check out [here]() for more details.

### APIs documentation ###

[LOCAL](https://localhots:9090/api/swagger-ui.html)

### Management APIs ###

[actuator](https://localhots:9090/api/actuator)


### Local MySql Setup ###

Setup a local mysql container: 
- Go into `docker-compose` directory under the root directory of the project
- Use `docker-compose -p "sql" up -d`
- Access http://localhost:8081/?server=db&username=exchange_rates
- Use `local_user/P@ssword1` as user/password credentials

Create `local_user` user and empty schema `local_db` by executing the following SQL commands:

```SQL
CREATE USER IF NOT EXISTS  'local_user'@'%' IDENTIFIED BY 'P@ssword1';
CREATE DATABASE IF NOT EXISTS local_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
FLUSH PRIVILEGES;
GRANT ALL PRIVILEGES ON local_db.* TO 'local_user'@'%';
```

### Docker Image ###

```
export IMAGE_NAME="<image-name>"
export GIT_COMMIT="<git-commit>"

docker build -t $IMAGE_NAME --build-arg GIT_COMMIT=$GIT_COMMIT  --build-arg ENVIRONMENT=local   .
```

### Deployment ###

The micro-service can be deployed on a Kubernetes cluster using the [DEPLOYMENT](/spring-boot-microservices-best-practices/kubernetes/k8s-deployment.yaml) pipeline.
