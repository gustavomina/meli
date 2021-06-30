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


