# Banking Solution application

## Technologies:
- <b> REST API Architecture</b>
- <b> Mapstruct</b>
- <b> JWT</b>
- <b> MySQL</b>
- <b> Flyway</b>
- <b> Docker</b>

## To start application:
```bash
docker-compose up
```

Docker will pull the MySQL and Spring Boot images (if our machine does not have it before).

To generate all annotated classes use:
```bash
mvn clean install
```

Then run main method in TechnicalTaskPichieApplication to finally start application

## To check test coverage:
Go to ```target/site/jacoco/index.html```
