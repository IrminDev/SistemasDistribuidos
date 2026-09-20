## Cliente.java

El programa Cliente.java es un ejemplo de un cliente de sockets TCP que se conecta a un servidor y posteriormente envía y recibe datos.

Primeramente, vamos a crear un socket que se conectará al servidor. En este caso el servidor se llama "localhost" (computadora local) y el puerto abierto en el servidor es el 50000.

En general el nombre del servidor puede ser un nombre de dominio (como midominio.com o una dirección IP). El número de puerto es un número entero entre 0 y 65535.

Socket conexion = new Socket("localhost",50000);

En este caso declaramos una variable de tipo Socket llamada "conexión" la cual va a contener una instancia de la clase Socket.

Es importante aclarar que antes de crear el socket, el programa servidor debe estar en ejecución y esperando una conexión, de otra manera la instrucción anterior produce una excepción, la cual desde luego debería controlarse dentro de un bloque try.

Para enviar datos al servidor a través del socket, vamos a crear un stream de salida de la siguiente manera:

```java
DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
```

De la misma forma, para leer los datos que envía el servidor a través del socket, creamos un stream de entrada:

```java
DataInputStream entrada = new DataInputStream(conexion.getInputStream());
```

Ahora podemos enviar y recibir datos del servidor. Veamos algunos ejemplos.

Vamos a enviar un entero de 32 bits, en este caso el número 123, utilizando el método `writeInt`:

```java
salida.writeInt(123);
```

Ahora vamos a enviar un número punto flotante de 64 bits utilizando el método `writeDouble`:

```java
salida.writeDouble(1234567890.1234567890);
```

Vamos a enviar la cadena de caracteres "hola":

```java
salida.write("hola".getBytes());
```

Debido a que el método write envía un arreglo de bytes, para enviar la cadena de caracteres "hola" es necesario convertirla a arreglo de bytes mediante el método getBytes.

Por omisión el método getBytes utiliza la codificación default (UTF-8), para usar otra codificación se puede pasar como parámetro el nombre de la codificación como string, por ejemplo "UTF-8". Ver más adelante el tema: Codificación de texto.

Ahora supongamos que el servidor envía al cliente una cadena de caracteres.

Para que el cliente reciba la cadena de caracteres es necesario que conozca el número de bytes que envía el servidor, en este caso el servidor envía una cadena de caracteres de 4 bytes.

Para recibir los bytes se utiliza el método read de la clase `DataInputStream`. El método read tiene tres parámetros, el primer parámetro es un arreglo de bytes con una longitud suficiente para contener los bytes a recibir.

El segundo parámetro indica la posición, dentro del arreglo de bytes, donde se pondrán los bytes a recibir, y el tercer parámetro indica el número de bytes a recibir.

El siguiente código crea un arreglo de 4 bytes, invoca el método read de la clase DataInputStream, crea una instancia de la clase String utilizando los bytes recibidos.

Debido a que la variable buffer contiene los bytes correspondientes a la cadena de caracteres que envió el servidor, para obtener la cadena de caracteres utilizamos el constructor de la clase String para crear una cadena de caracteres a partir del arreglo de bytes indicando la codificación, en este caso UTF-8.

```java
byte[] buffer = new byte[4];
entrada.read(buffer,0,4);
System.out.println(new String(buffer,"UTF-8"));
```

Sin embargo, el método read podría obtener solo una parte del mensaje enviado. Esta situación la explicaremos más adelante.

## La clase ByteBuffer

Ahora veremos cómo enviar de manera eficiente un conjunto de números punto flotante de 64 bits. 

Supongamos que vamos a enviar cinco números punto flotante de 64 bits.

Primero "empacaremos" los números utilizando un objeto ByteBuffer.

Cinco números punto flotante de 64 bits ocupan 5x8 bytes (64 bits=8 bytes). Entonces vamos a crear un objeto de tipo ByteBuffer con una capacidad de 40 bytes:

```java
ByteBuffer b = ByteBuffer.allocate(5*8);
```

Utilizamos el método putDouble para agregar cinco números al objeto ByteBuffer:

```java
b.putDouble(1.1);
b.putDouble(1.2);
b.putDouble(1.3);
b.putDouble(1.4);
b.putDouble(1.5);
```

Para enviar el "paquete" de números, convertimos el objeto `ByteBuffer` a un arreglo de bytes utilizando el método array de la clase `ByteBuffer`:

```java
byte[] a = b.array();
```

Entonces enviamos el arreglo de bytes utilizando el método write:
 
```java
salida.write(a);
```

Para terminar el programa cerramos la conexión con el servidor (al cerrar el socket se cierran también los streams asociados), en este caso vamos a poner un retardo de un segundo, antes de cerrar la conexión, para permitir que el servidor tenga tiempo de recibir los datos:

```java
Thread.sleep(1000);
conexion.close();
```