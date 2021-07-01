[![Build Status](https://travis-ci.com/gustavomina/meli.svg?branch=master)](https://travis-ci.com/gustavomina/meli)   [![codecov](https://codecov.io/gh/gustavomina/meli/branch/master/graph/badge.svg?token=MNSAV8XWCF)](https://codecov.io/gh/gustavomina/meli)

# MeLi - Mutant Finder

Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Mens. 

Te ha contratado a ti para que desarrolles un proyecto que detecte si un humano es mutante basándose en su secuencia de ADN.

Para eso te ha pedido crear un programa con un método o función con la siguiente firma: 

``boolean isMutant(String[] dna);``

En donde recibirás como parámetro un array de Strings que representan cada fila de una tabla de (NxN) con la secuencia del ADN.

Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada del ADN. 


| A | T | G | C | G | A |
| ------ | ------ | ------ | ------ |  ------ | ------ |
| C | A | G | T | G | C | 
| T | T | A | T | T | T |
| A | G | A | A | G | G |
| G | C | A | T | C | A |
| T | C | A | C | T | G |


| A | T | G | C | G | A |
| ------ | ------ | ------ | ------ |  ------ | ------ |
| C | A | G | T | G | C |
| T | T | A | T | G | T |
| A | G | A | A | G | G |
| C | C | C | C | T | A |
| T | C | A | C | T | G |

Sabrás si un humano es mutante, si encuentras ​más de una secuencia de cuatro letras iguales​, de forma oblicua, horizontal o vertical.
Ejemplo (Caso mutante): 
 
``String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};``
 
En este caso el llamado a la función isMutant(dna) devuelve “true”. 
 
Desarrolla el algoritmo de la manera más eficiente posible

### Desafíos:

**Nivel 1**: Programa (en cualquier lenguaje de programación) que cumpla con el método pedido por Magneto

**Nivel 2**: Crear una API REST, hostear esa API en un cloud computing libre (Google App Engine, Amazon AWS, etc), crear el servicio “/mutant/” en donde se pueda detectar si un humano es mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el siguiente formato: 

`` POST → /mutant/ { “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] } ``

En caso de verificar un mutante, debería devolver un **HTTP** **200-OK**, en caso contrario un **403-Forbidden** 

**Nivel 3**: Anexar una base de datos, la cual guarde los ADN’s verificados con la API. Solo 1 registro por ADN. Exponer un servicio extra "/stats" que devuelva un Json con las estadísticas de las verificaciones de ADN: {“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4} 

Tener en cuenta que la API puede recibir fluctuaciones agresivas de tráfico (Entre 100 y 1 millón de peticiones por segundo). 
 
Test-Automáticos, Code coverage > 80%. 

 
#### Entregar: 
 
- Código Fuente (Para Nivel 2 y 3: En repositorio github). 
- Instrucciones de cómo ejecutar el programa o la API. (Para Nivel 2 y 3: En README de github).
- URL de la API (Nivel 2 y 3). 

## Implementación
Para la implementación del algoritmo se realizó principalmente la búsqueda haciendo uso de un algoritmo **BFS - Breadth First Search** en el cual se visita un elemento de la matriz y se buscan los elementos adyacentes al mismo, una vez se tienen los elementos adyacentes, se evalúa cada uno y si el carácter de dicho elemento es igual al elemento current, se sigue buscando en los adyacentes al nodo nuevo en la misma dirección, por ejemplo, si estoy evaluando en nodo [0, 0] que tiene la letra A, y el nodo adyacente que se encuentra en la posición [0, 1] (al este), se sigue buscando los nodos adyacentes que se encuentran al este del nodo [0, 1], esta búsqueda termina cuando se encuentra un carácter diferente al carácter evaluado o cuando se encuentran 4 caracteres consecutivos con el mismo valor.

## Tecnología
- Java 8
- Spring Framework
- AWS Lambda Function
- AWS Proxy RDS
- AWS RDS
- AWS API Gateway

## Modo de uso
### Pre-Requisitos
- [AWS CLI](https://aws.amazon.com/cli/)
- [SAM CLI](https://github.com/awslabs/aws-sam-cli)
- [Maven](https://maven.apache.org/)

### Despliegue
En una consola navegue hasta la carpeta en la cual se encuentra el proyecto y use SAM CLI para construir el artefacto a desplegar

```
$ sam build
```

Este comando compila la aplicacion y prepara el paquete de despliegue en el directorio `.aws-sam`.

Para desplegar la aplicacion en su cuenta AWS, puede usar la opcion de despliegue guiado que ofrece SAM CLI y seguir las instrucciones.

```
$ sam deploy --guided
```

Una vez el despliegue ha terminado, SAM CLI imprime el resultado en la salida estandar, incluyendo la URL de la aplicacion.

```
...
---------------------------------------------------------------------------------------------------------------------
OutputKey-Description                                  OutputValue
---------------------------------------------------------------------------------------------------------------------
SpringMutantFinderApi - URL for application            https://mf1lb8qbxl.execute-api.us-east-2.amazonaws.com/mutant
---------------------------------------------------------------------------------------------------------------------
```

## Servicios
El API desplegado en AWS cuenta con las siguientes rutas:

- [POST](https://mf1lb8qbxl.execute-api.us-east-2.amazonaws.com/mutant), que recibe un json como el siguiente

   ````
   {
       "dna" : ["AAAA", "ATCG", "GTCA", "GGGG"]
   }
   ````

- [GET](https://mf1lb8qbxl.execute-api.us-east-2.amazonaws.com/mutant/stats), que devuelve un json de la forma: 

  ````
  {
      "count_mutant_dna":40, 
      "count_human_dna":100: 
      "ratio":0.4
  }
  ````
