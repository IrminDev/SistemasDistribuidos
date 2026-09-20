# Servidor multithread

Anteriormente vimos el programa Servidor.java el cual invoca el método accept para esperar una conexión del cliente, debido a que este método es bloqueante el programa queda en espera pasiva hasta que el cliente se conecta.

Cuando el servidor recibe una conexión, el método accept regresa un socket. Entonces el cliente y el servidor podrán intercambiar datos. Generalmente el servidor procesa los datos que recibe del cliente y al terminar vuelve a invocar el método accept para esperar otra conexión.

Sin embargo, mientras el servidor procesa los datos que recibe del cliente, no puede recibir otra conexión. Para resolver este problema los servidores se construyen utilizando threads.

Entonces, vamos a ver cómo construir un servidor multithread.

## Ejecución paralela y ejecución concurrente

En las clases de Sistemas Operativos se explica que un thread (hilo) es la ejecución secuencial de las instrucciones de un programa.

Un proceso puede crear uno o más threads (hilos de ejecución), los cuales van a ejecutar simultáneamente.

Si la computadora tiene un CPU dual core, entonces el CPU podrá ejecutar en paralelo (al mismo tiempo) dos threads, si el CPU es quad core entonces podrá ejecutar en paralelo cuatro threads, y así sucesivamente.

Por otra parte, si un programa crea un número de threads mayor al número de procesadores físicos (cores) disponibles en la computadora, entonces los threads ejecutarán en forma concurrente (por turnos).

## Programación multithread en Java

Supongamos que tenemos una clase llamada P.

Dentro de la clase P definimos una clase interior (nested class) llamada Worker la cual es subclase de la clase Thread:

```java
class P
{
  static class Worker extends Thread
  {
    public void run()
    {
    }
  }
  public static void main(String[] args) throws Exception
  {
  }
}
```

Podemos ver que hemos incluido en la clase Worker un método público llamado run el cual no tiene parámetros ni regresa resultado.

## El método start

Para iniciar la ejecución de un thread, debemos crear una instancia de la clase Worker e invocar el método start (este método se hereda de la clase Thread):

```java
Worker w = new Worker();
w.start();
```

Entonces se crea un hilo que inicia invocando el método run que hemos definido en la clase Worker.

Un thread finaliza su ejecución cuando el método run termina. Cuando un thread finaliza, no puede volver a ejecutarse.

## El método join

Supongamos que el thread principal (el thread que invocó el método start) requiere esperar que el thread w termine su ejecución, entonces el thread principal deberá invocar el método join:

```java
Worker w = new Worker();
w.start();
w.join();
```

El método join queda en un estado de espera pasiva mientras el thread "w" se encuentra ejecutando, cuando el thread "w" termina, el método join regresa, entonces el thread principal continua su ejecución.

Ahora supongamos que el thread principal requiere crear dos threads y esperar a que terminen su ejecución. Entonces creamos dos instancias de la clase Worker e invocamos los métodos start y join para cada thread:

```java
Worker w1 = new Worker();
Worker w2 = new Worker();
w1.start();
w2.start();
w1.join();
w2.join();
```

Cuando un thread (en este caso el thread principal) espera la terminación de uno o más threads para continuar su ejecución, se dice que se implementa una barrera. En este caso estamos implementando una barrera mediante dos métodos join.

# Servidor2.java

Ahora vamos a implementar un servidor de sockets multithread.
La idea es que el servidor multithread espere conexiones y para cada conexión cree un thread que procese los datos que envía el cliente.
 
Vamos a invocar el método accept dentro de un ciclo, y para cada conexión vamos a crear un thread.
 
```java
class Servidor2
{
  static class Worker extends Thread
  {
    Socket conexion;
    Worker(Socket conexion)
    {
      this.conexion = conexion;
    }
    public void run()
    {
    }
  }
  public static void main(String[] args) throws Exception
  {
    ServerSocket servidor = new ServerSocket(50000);
    for (;;)
    {
      Socket conexion = servidor.accept();
      Worker w = new Worker(conexion);
      w.start();
    }
  }
}
```

Este código será la base para otros programas que desarrollaremos en el curso.
 

El constructor de la clase Worker pasa como parámetro el socket que crea el método accept, ya que el método run requiere el socket para recibir y enviar datos al cliente.

Ahora agregaremos el siguiente código al método run:

```java
try
{
  DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
  DataInputStream entrada = new DataInputStream(conexion.getInputStream());
  int n = entrada.readInt();
  System.out.println(n);
  double x = entrada.readDouble();
  System.out.println(x);
  byte[] buffer = new byte[4];
  read(entrada,buffer,0,4);
  System.out.println(new String(buffer,"UTF-8"));
  salida.write("HOLA".getBytes());
  byte[] a = new byte[5*8];
  read(entrada,a,0,5*8);
  ByteBuffer b = ByteBuffer.wrap(a);
  for (int i = 0; i < 5; i++) System.out.println(b.getDouble());
}
catch(Exception e)
{
  System.err.println(e.getMessage());
}
finally
{
  try
  {
    conexion.close();
  }
  catch (Exception e2)
  {
    System.err.println(e2.getMessage());
  }
}
```

Podemos ver en el programa Servidor2.java que el método run crea los streams que se utilizarán para enviar y recibir datos del cliente. Notar que el programa Servidor2.java es completamente compatible con el programa Cliente.java