# La clase de hoy vamos a explicar cómo utilizar JSON para serializar y de-serializar objetos.

JSON (JavaScript Object Notation) es un formato texto para el intercambio de datos.

JSON define una sintaxis parecida a la sintaxis utilizada en Javascript para escribir objetos.

Las diferencias entre un objeto JSON y un objeto Javascript son las siguientes:

- Un objeto JSON lleva comillas en los nombres de los campos.
- Los valores en un objeto JSON solo pueden ser arreglos, objetos, números, strings, true, false y null
- Un objeto JSON no permite comentario.

Por ejemplo:

```javascript
Objeto Javascript: {a:1, b:2, c:3}
Objeto JSON: {"a":1, "b":2, "c":3}
```

JSON es un formato independiente del lenguaje de programación, de manera que es posible escribir fácilmente programas en cualquier lenguaje que creen mensajes en formato JSON, así como programas que lean mensajes en formato JSON.

En JSON es posible crear dos estructuras: objetos y arreglos.

## Sintaxis de JSON

```text
Un whitespace es un separador de tokens, de acuerdo a la siguiente sintaxis:

Fuente: www.json.org
```

Un objeto es una colección no ordenada de parejas nombre:valor separadas por coma. Un objeto comienza con una llave que abre “{“ y termina con una llave que cierra “}”.

```text
La sintaxis de un objeto es la siguiente:

Fuente: www.json.org
```

Un arreglo es una colección ordenada de valores separados por coma. Un arreglo comienza con un corchete que abre “[“ y termina con un corchete que cierra “]”.

```text
La sintaxis de un arreglo es la siguiente:

Fuente: www.json.org
```

Un valor puede ser una cadena de caracteres entre comillas, o un número, o un objeto, o un arreglo, o las constantes true, false o null.

```text
La sintaxis de un valor es la siguiente:

Fuente: www.json.org
```

Una cadena de caracteres (string) es una secuencia de cero o más caracteres Unicode encerrados entre comillas.

```text
Fuente: www.json.org
Aquí, "codepoint" se refiere a cualquier carácter.
```

Notas.

- Un texto JSON se codifica por default utilizando UTF-8, por tanto, una string JSON puede incluir caracteres de cualquier idioma.
- Debido a que los nombres de los campos son strings, también los nombres de los campos pueden incluir caracteres de cualquier idioma.
- Se puede representar cualquier carácter mediante una secuencia de escape \uxxxx, donde xxxx es un número hexadecimal de cuatro dígitos. La secuencia de escape \uxxxx es necesaria para incluir caracteres de cualquier idioma, cuando se codifica un texto JSON utilizando codificación ANSI o codificación ASCII, sin embargo, la codificación estándar para JSON es UTF-8.
- Debido a que la codificación ASCII es un subconjunto de ANSI, y ANSI es un subconjunto de UTF-8, un texto JSON con codificación ASCII o ANSI puede ser des-serializado correctamente.

```text
Un número tiene la siguiente sintaxis:

Fuente: www.json.org
```

## Ahora veremos un ejemplo, utilizando GSON (implementación de JSON desarrollada por Google).

Se requiere descargar el archivo gson-2.8.6.jar de la siguiente URL:
https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.6/gson-2.8.6.jar

Capturar el siguiente programa EjemploGSON.java y colocarlo en el mismo directorio donde está el archivo gson-2.8.6.jar que se descargó anteriormente.

```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;

class EjemploGSON
{
  static class Empleado
  {
String nombre;
int edad;
float sueldo;
Timestamp fecha_ingreso;
Empleado(String nombre,int edad,float sueldo,Timestamp fecha_ingreso)
{
  this.nombre = nombre;
  this.edad = edad;
  this.sueldo = sueldo;
  this.fecha_ingreso = fecha_ingreso;
}
  }

  public static void main(String[] args)
  {
Empleado[] e = new Empleado[3];
e[0] = new Empleado("Hugo",20,1000,Timestamp.valueOf("2020-01-01 20:10:00"));
e[1] = new Empleado("Paco",21,2000,Timestamp.valueOf("2019-10-01 10:15:00"));
e[2] = new Empleado("Luis",22,3000,Timestamp.valueOf("2018-11-01 00:00:00"));

Gson j = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();

String s = j.toJson(e);
System.out.println(s);

Empleado[] v = (Empleado[])j.fromJson(s,Empleado[].class);

for (int i = 0; i < v.length; i++)
  System.out.println(v[i].nombre + " " + v[i].edad + " " + v[i].sueldo + " " + v[i].fecha_ingreso);
  }
}
```

Para compilar el programa EjemploGSON.java se debe ejecutar el siguiente comando:

```bash
javac -cp gson-2.8.6.jar EjemploGSON.java
```

Para ejecutar el programa en Windows:

```bash
java -cp gson-2.8.6.jar;. EjemploGSON
```

Para ejecutar el programa en Linux:

```bash
java -cp gson-2.8.6.jar:. EjemploGSON
```

Ahora se explicará cómo funciona este programa.

Primeramente, se declaran los imports de las clases que se utilizarán: Gson, GsonBuilder y Timestamp.

Se define una clase Empleado que contiene los campos: nombre, edad, sueldo y fecha_ingreso. Por conveniencia se define un constructor para inicializar los campos al crear una instancia.

Notar que la fecha se maneja como fecha-hora debido a que en los sistemas globales (Internet) la fecha no indica qué sucede antes y que sucede después a nivel mundial; recordar lo que se vimos sobre tiempo UTC y tiempo local.

```text
En la función "main" se crea un arreglo de 3 empleados, cada elemento se inicializa con una instancia de la clase "Empleado" con diferentes datos.

Entonces se crea una instancia de la clase Gson. Notar que se utiliza el método setDateFormat() para utilizar el formato de fecha ISO8601, el cual es el formato que utiliza Javascript para manejar fecha-hora.

Después se utiliza el método toJson de la clase Gson para serializar el arreglo de empleados. Esta clase produce la siguiente string:

[{"nombre":"Hugo","edad":20,"sueldo":1000.0,"fecha_ingreso":"2020-01-01T20:10:00.000"},{"nombre":"Paco","edad":21,"sueldo":2000.0,"fecha_ingreso":"2019-10-01T10:15:00.000"},{"nombre":"Luis","edad":22,"sueldo":3000.0,"fecha_ingreso":"2018-11-01T00:00:00.000"}]

Finalmente se utiliza el método fromJson de la clase Gson para deserializar la string anterior y producir un nuevo arreglo de empleados. Entonces se despliegan los datos de los empleados:

Hugo 20 1000.0 2020-01-01 20:10:00.0
```

Paco 21 2000.0 2019-10-01 10:15:00.0
Luis 22 3000.0 2018-11-01 00:00:00.0
