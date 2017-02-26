# RESTful web service con spring-boot

##### Servicio para la gestión de las operaciones de un hotel, el cual consta de los siguientes componentes:

>- Servicio de inventario.
>- Servicio de consulta de disponibilidad de cupo.
>- Servicio de reservación.
>- Servicio de factura.

Cada semana se trabajará un módulo diferente en el que se mostrarán
progresivamente diferentes aspectos del diseño de las APIs REST, y
las posibilidades que spring-boot ofrece para ello.


### Software requerido
>- JDK 1.8+.
>- Maven 3.0+.
>- Firefox, Chrome o Postman.

### 	Construir un JAR ejecutable

Para construir un JAR ejecutable, desde la línea de comando ejecutamos el comando:

```
 mvn compile package
```
### Ejecutar JAR

Una vez generado el JAR, podemos correr la aplicación con:

```
java -jar target/hotel-web-api-1.0-SNAPSHOT.jar
```
### Correr la aplicación con Maven

Ejecutando el siguiente comando podemos correr la aplicación sin generar el JAR:

```
mvn spring-boot:run
```  

### Desplegar la aplicacion en un contenedor externo.

Para construir un archivo war que sea ejecutable y desplegable en un contenedor externo
modificar en el pom:

<br>

1. Cambiar el tag <packing> de jar a war.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!-- ... -->
    <packaging>war</packaging>
    <!-- ... -->
</project>
```  
<br>  

2. Agregar la dependencia del contenedor embebido y marcarla como "provided".

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <!-- ... -->
  <dependencies>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-tomcat</artifactId>
          <scope>provided</scope>
      </dependency>
      <!-- ... -->
  </dependencies>
</project>
```
<br>

### Proyecto final: Web service EndPoints.
-------------------------

#### Servicio de consulta de disponibilidad de cupo.

<br>

| Método  | EndPoint                 | Retorna | Autenticación | Autorización  |
| ------- | ----------------------   | ------- | ------------- | ------------- |
|  GET    | /v1/disponibilidad/cupos | cupos   |  NO           |               |

<br>

##### Request Parameters.

<br>

| Query parameter | Valor                                                                                                           |
| --------------- | --------------------------------------------------------------------------------------------------------------- |
| fechaIngreso    | Requerido. Fecha y hora de entrada en el formato ISO 8601. Debe ser igual o posterior a la fecha actual.        |
| fechaSalida     | Requerido. Fecha y hora de salida en el formato ISO 8601. Debe ser igual o posterior al parámetro fechaIngreso. |
| categoria       | Opcional. ID de la categoría de los cuarto a retornar.                                                          |
| offset          | Opcional. Número de cuartos a retornar.                                                                         |
| limit           | Opcional. Indice del primer cuarto a retornar.                                                                  |

**NOTA**: 404 Bad Request si alguno de los parámetros tiene un tipo de dato inválido, o bien si fechaIngreso o fechaSalida no satisfacen las
reglas indicadas.

<br>

##### Representación recurso cupos.

```json
{
   "fechIngreso":"2017-02-12T23:28:56.782Z",
   "fechaSalida":"2017-02-13T23:28:56.782Z",
   "cuartos":[
       {
          "id":1,
          "numero":1,
          "descripcion":"Vista a la piscina",
          "categoria":1
       },
       {
          "id":2,
          "numero":2,
          "descripcion":"Remodelado recientemente",
          "categoria":1
       }
   ]
}
```
<br>

#### Servicio de reservación.

<br>

| Método  | EndPoint                                  | Retorna              | Autenticación       | Autorización        |
| ------- | ----------------------------------------- | -------------------- | ------------------  | ------------------- |
|  GET    | /v1/reservaciones/{reservacion-id}        | reservacion          |  NO                 |                     |

<br>

| PATH_PARAMETER | VALOR                |
| -------------- | -------------------- |
| reservacion-id | ID de la reservación |

<br>

##### Representación recurso reservación.

```json
{  
   "id":"1",
   "desde":"2017-02-17T23:28:56.782Z",
   "hasta":"2017-02-18T23:28:56.782Z",
   "cuarto":{
      "id":1,
      "numero":1,
      "descripcion":"Vista a la piscina",
      "categoria":1
   },
   "huesped":{
       "id": "1",
       "nombre": "Juan Pérez",
       "email": "jperez@gmail.com",
       "telefono": "78999354",
   }
}
```

<br>

| KEY     | VALUE TYPE  | DESCRIPCION             |
| ------- | ----------- | ----------------------- |
| id      |  integer    | ID de la reservación    |
| desde   |  date-time  | Fecha de ingreso        |
| hasta   |  date-time  | Fecha de salida         |
| cuarto  |  objeto     | Cuarto reservado        |
| huesped |  objeto     | Huesped/cliente         |

<br>

| Método  | EndPoint                                  | Retorna              |  Autenticación      | Autorización        |
| ------- | ----------------------------------------- | -------------------- | ------------------  | ------------------- |
|  POST   | /v1/reservaciones                         | reservacion creada   |  Basic Authorization| TODOS               |

<br>

| HEADER         | VALOR                                   |
| -------------- | --------------------------------------- |
| Authorization  | Basic {username}:{password}             |
| Content-Type   | Cuerpo de la petición: application/json |

<br>

| REQUEST BODY   | VALUE TYPE        | VALUE                                                                                                                                                 |
| -------------- | ----------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- |
| recurso        | Objeto recurso    | JSON serializado de la reservación a agregar. Ej. {"desde":"2017-02-17T23:28:56.782Z","hasta":"2017-02-18T23:28:56.782Z","cuarto":"2","huesped":"1"}  |

**NOTA** La fecha de ingreso debe ser igual o posterior a la fecha actual, mientras que la fecha de salida deberá ser posterior a la fecha de ingreso.

<br>

##### Formato de respuesta.

<br>

Si la reservación es agregada exitosamente, el HTTP status code en el HEADER de la repuesta es 201 OK. 404 Bad Request si alguno de los parámetros tiene un tipo de dato inválido, o bien si fechaIngreso o fechaSalida no satisfacen las reglas indicadas.
