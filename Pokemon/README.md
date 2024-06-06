# Java-Pokemon
Java Spring DB Backend Pokemons

### Run container with DB and keycloak
- `docker-compose up --build -d`
- `docker-compose down`
### Run database
- create database`docker run --rm --name postgres -p 5433:5432 -e POSTGRES_PASSWORD=123 postgres`
- login: `postgres:123`
- port `5433`
- create database `create database test;`

### Run keycloak
- `docker run -p 8091:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=123 quay.io/keycloak/keycloak start-dev`
- login: `admin:123`
- create realm `EA`
- create client `web-app`
- create roles `ROLE_SUPERADMIN`
- create user `admin` with password `1234`
- enable: email verified
- add credentials: `1234` not temporary
- assign role `ROLE_SUPERADMIN` to user `admin` from client `web-app`

### Swagger docs
http://localhost:8090/swagger-ui/index.html

### Endpoints
### Pokemons
http://localhost:8090/pokemons

http://localhost:8090/statistics

http://localhost:8090/statistics/pokemon

http://localhost:8090/trainers

### Trainers
http://localhost:8090/trainers

### Statistics
http://localhost:8090/statistics

http://localhost:8090/statistics/pokemon

http://localhost:8090/statistics/trainer