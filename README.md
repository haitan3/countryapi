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

Para el método <b>GET</b>, listamos todos los países guardados en nuestra BBDD local.
- Si no hemos guardado países previamente, la lista estará vacía.
- Si hemos guardado países previamente, el programa nos devolverá todo el listado, persistiendo los datos en caso de desconectar el programa o desactivando el contenedor

Para el método <b>POST</b>, no olvidemos la estructura del JSON:<br/>
{<br/>
"country": "Bulgaria", <br/>
"population": 6927288 <br/>
}
- Si el país no existe en la base de datos local, el programa obtiene la información de la API externa.
- En caso de que ese país tampoco exista en la API externa lo guardamos en la BBDD con valores por defecto.
- Creamos una excepción para el caso, lanzándola y controlándola en los momentos oportunos.

