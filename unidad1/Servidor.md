# Servidor.java

El programa Servidor.java va a esperar una conexión del cliente, entonces recibirá los datos que envía el cliente y a su vez, enviará datos al cliente.

Primeramente, vamos a crear un socket servidor que va a abrir, en este caso, el puerto 50000:

```java
ServerSocket servidor = new ServerSocket(50000);
```

Notar que en Windows, por razones de seguridad el firewall solicita al usuario administrador permiso para abrir este puerto.

Ahora invocamos el método accept de la clase ServerSocket.

El método accept es bloqueante, lo que significa que el thread principal del programa quedará en estado de espera pasiva (una espera que no ocupa ciclos de CPU) hasta recibir una conexión del cliente. Cuando se recibe la conexión el método accept regresa un socket, en este caso vamos a declarar una variable de tipo Socket llamada "conexion":

```java
Socket conexion = servidor.accept();
```

Una vez establecida la conexión con el cliente, el servidor podrá enviar y recibir datos.
 
Creamos un stream de salida y un stream de entrada:
 
```java
DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
DataInputStream entrada = new DataInputStream(conexion.getInputStream());
```

Recordemos que el cliente envía un entero de 32 bits, entonces el servidor deberá recibir este dato utilizando el método readInt:
 
```java
int n = entrada.readInt();
System.out.println(n);
```

Ahora el servidor recibe un número punto flotante de 64 bits utilizando el método readDouble:
 
```java
double x = entrada.readDouble();
System.out.println(x);
```

El servidor recibe una cadena de cuatro caracteres:
 
```java
byte[] buffer = new byte[4];
read(entrada,buffer,0,4);
System.out.println(new String(buffer,"UTF-8"));
```

El servidor envía una cadena de cuatro caracteres:
 
```java
salida.write("HOLA".getBytes());
```

Ahora vamos a recibir los cinco números punto flotante empacados en un arreglo de bytes.
Recordemos que los cinco números punto flotante de 64 bits ocupan 40 bytes (5x8bytes).

```java
byte[] a = new byte[5*8];
read(entrada,a,0,5*8);
```

Una vez recibido el arreglo de bytes, lo convertimos a un objeto `ByteBuffer` utilizando el método wrap de la clase `ByteBuffer`:
 
```java
ByteBuffer b = ByteBuffer.wrap(a);
```

Para extraer los números punto flotante, utilizamos el método getDouble de la clase ByteBuffer:
 

```java
for (int i = 0; i < 5; i++) System.out.println(b.getDouble());    
```

Finalmente, cerramos la conexión con el cliente:
 
```java
conexion.close();
```
 
## Un cliente con reintentos de conexión
 
Como vimos anteriormente, para que el cliente se conecte al servidor es necesario que el servidor inicie su ejecución antes que el cliente, sin embargo, para algunas aplicaciones el cliente debe esperar a que el servidor inicie su ejecución.
 
Veamos ahora cómo implementar el reintento de conexión cuando el servidor no está ejecutando.
 
```java
Socket conexion = null;
for(;;)
  try
  {
    conexion = new Socket("localhost",50000);
    break;
  }
  catch (Exception e)
  {
    Thread.sleep(100);
  }
```

Como podemos ver, cada vez que el cliente falla en establecer la conexión con el servidor, espera 100 milisegundos y vuelve a intentar la conexión. Cuando el cliente logra conectarse con el servidor entonces sale del ciclo for.
 