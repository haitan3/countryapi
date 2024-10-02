# Country API

## Descripción

Esta es una aplicación que permite gestionar información sobre países utilizando una API externa.

## Requisitos

- Docker
- Java 17

## Cómo ejecutar

1. Clona el repositorio. lanzar siguiente comando -> git clone https://github.com/haitan3/countryapi.git
2. Navega al directorio del proyecto.
3. para construir y ejecutar la aplicación 
<br/>
Lanzar -> `docker-compose up --build`

4. Para volver a lanzar el contenedor y acceder a los mismos registros.
<br/>
Lanzar `docker-compose up`

5. Para apagar el contenedor <br/>
Lanzar `docker-compose down`

## Endpoints
- Agrega o actualiza un país.
- `POST http://localhost:8080//api/v1/data/country`
- Lista todos los países.
- `GET http://localhost:8080//api/v1/data/country` 
