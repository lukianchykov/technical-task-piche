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

## Authorization

The X Payment API uses **Bearer Token Authentication**. To access the API, clients must provide a bearer token as part of the HTTP request's `Authorization` header.

### Required Headers

1. **`Authorization` (Required)**
    - **Description:** Bearer Token for accessing the API.
    - **Value:** Must include the string of the format `eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYW5kYXJpbiIsImlhdCI6MTc0ODUxOTMyNywiZXhwIjoxNzQ4NjA1NzI3fQ.rn3evhVJ1n74_hAiYN5KJFNDGLjH1RoRsYuRm_x7FVTSSuuXH9I1z5OWXgQCLdpOE4dd3NmRbX-qKCyZiCZOTw`.

## To create a user
Use ```api/v1/auth/signup```
```json
{
  "username": "Mandarin",
  "email": "lukandrii64@gmail.com",
  "password": "5a6b7c8d9e0f",
  "role": ["USER", "ADMIN"]
}
```

## To login as user
Use ```api/v1/auth/signin```
```json
{
  "username": "Mandarin",
  "password": "5a6b7c8d9e0f"
}
```
Response provided:
```json
{
  "roles": [
    "ROLE_USER",
    "ROLE_ADMIN"
  ],
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYW5kYXJpbiIsImlhdCI6MTc0ODUyNDcxOSwiZXhwIjoxNzQ4NjExMTE5fQ.gMBmbYnTvzaBLDEy-AsHEnkIdLrYafqoWqoN6D73k5Hm3smp0X9QoeMMZ0enOvLxmNSTUVpDeLEFP-TEk4wVbQ",
  "type": "Bearer",
  "id": 1,
  "username": "Mandarin",
  "email": "lukandrii64@gmail.com"
}
```
## To access endpoints
### Example with `curl`

Here's how to use header with `curl` to make a request:

```bash
curl -X GET "'http://localhost:8080/api/v1/accounts/{accountNumber}'" \
-H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYW5kYXJpbiIsImlhdCI6MTc0ODUyNDg0NywiZXhwIjoxNzQ4NjExMjQ3fQ.lbkwTfVjivBVaE3aIYl9k1AG_mmp5fuVBg6GXPxmLe1A8pAr7fUL3DhooxueZ4AQWfzeu6lhKGlf4wWhRPX_cA"
```

Replace `{accountNumber}` with the actual account number if you want to retrieve.



