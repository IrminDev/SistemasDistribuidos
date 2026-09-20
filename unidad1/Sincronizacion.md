# Sincronización del acceso a memoria

## Operaciones de lectura y escritura

Los threads dentro de un proceso se comunican entre sí utilizando la memoria. Las operaciones que se realizan sobre la memoria son la lectura y escritura de variables (localidades de memoria).

Para que un thread pueda leer los datos que escribe otro thread en la memoria, es necesario ordenar las operaciones de lectura y escritura que realizan los threads.

Supongamos que tenemos dos threads, el thread T1 y el thread T2.

Si el thread T2 escribe a una variable y posteriormente el thread T1 lee la variable, el thread T1 tendrá el valor que escribió el thread T2.

## Orden escritura-escritura

Ahora supongamos que los threads T1 y T2 escriben al mismo tiempo una variable y posteriormente los threads leen al mismo tiempo la misma variable. ¿Qué valores leyeron los threads?

Evidentemente no es posible garantizar que el thread T1 haya leído el valor que escribió el thread T2 o que haya leído el valor que escribió el mismo thread T1. 

Igualmente, no es posible garantizar que el thread T2 haya leído el valor que escribió el thread T1 o que el thread T1 haya el valor que escribió el mismo thread T2.

Entonces, para garantizar que un thread lee el valor de una variable escrito por otro thread, es necesario ordenar las operaciones de escritura y de lectura que realizan los threads.

Ahora supongamos que el thread T1 espera a que el thread T2 escriba la variable, entonces después el thread T1 escribe a la variable.

En este caso, el valor que leen los threads T1 Y T2 es el valor que escribió el thread T1.

Notar que dos o más threads pueden leer simultáneamente una variable.

## Orden escritura-lectura

Ahora supongamos que el thread T1 lee la variable y posteriormente el thread T2 escribe la variable ¿qué valor leyó el thread T1?

Para que el thread T1 pueda leer el valor que escribe el thread T2, es necesario ordenar las operaciones de escritura y lectura.

Para garantizar que el thread T1 lea el valor escrito por el thread T2, el thread T1 debe esperar a que el thread T2 escriba la variable.

 

Si dos o más threads acceden a una misma variable, y al menos uno de los threads escribe la variable, entonces es necesario sincronizar el acceso de los threads a la variable.

Sincronizar el acceso de los threads significa ordenar las operaciones de escritura y de lectura que realizan los threads.

## La instrucción `synchronized`
 
Para ordenar las lecturas y escrituras que realizan los threads en Java, se utiliza la instrucción synchronized.
 

```java
synchronized (objeto)
{
  instrucciones
}
```

Debemos recordar que en Java todos los objetos tienen un lock asociado.
 
En la clase de Sistemas Operativos se explica que un lock (o cerrojo) es un mecanismo que permite limitar el acceso a un recurso compartido por varios procesos o threads, por ejemplo, una variable, un archivo, una impresora. etc. 
 
A la capacidad que tiene un sistema de controlar el acceso a recursos compartidos se le conoce como exclusión mutua.

La instrucción `synchronized` funciona de la siguiente manera:

- Primero se verifica si el lock del objeto está bloqueado. Si el lock está bloqueado, el thread espera a que se desbloquee.

- Si el lock está desbloqueado, el thread lo bloquea —se dice que adquiere el lock— y ejecuta las instrucciones dentro del bloque.

- Al terminar las instrucciones, el thread desbloquea el lock —se dice que libera el lock—. Entonces, el sistema operativo notifica a uno de los threads que esperan el lock para que lo adquiera y ejecute las instrucciones dentro del bloque.

- Al terminar, el thread desbloquea nuevamente el lock y el sistema operativo notifica a alguno de los threads que esperan.

 
Como podemos ver, la instrucción `synchronized` evita que dos o más threads ejecuten simultáneamente un bloque de instrucciones. Al bloque de instrucciones que solo puede ser ejecutado por un thread se le llama sección crítica.
 
Veamos un ejemplo.
 
Supongamos que tenemos dos threads que incrementan una variable estática llamada "n" dentro de un ciclo for.
 
La variable estática "n" es "global" a todas las instancias de la clase, por tanto los threads pueden leer y escribir esta variable.

```java
class A extends Thread
{
  static long n;
  public void run()
  {
    for (int i = 0; i < 100000; i++)
        n++;
  }
  public static void main(String[] args) throws Exception
  {
    A t1 = new A();
    A t2 = new A();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println( n );
  }
}
```
 
En este caso, la clase principal A es subclase de la clase Thread, por tanto hereda los métodos run, start y join (entre otros).
 
El programa debería desplegar 200000 ya que cada thread incrementa 100,000 veces la variable "n".
 
Preguntas:

- ¿Por qué despliega un número menor a 200,000?
- ¿Por qué cada vez que se ejecuta el programa despliega un número diferente?
 

La instrucción `n++` se compone de tres operaciones:

- Copiar el valor de la variable `n` a un registro del procesador.
- Incrementar el valor del registro.
- Escribir el valor del registro en la variable `n`.

Debido a que los threads t1 y t2 ejecutan en paralelo en una computadora con dos o más núcleos, el thread t1 puede leer y escribir la variable n al mismo tiempo que el thread t2:

Dado que la lectura que realiza t1 no está ordenada con respecto a la escritura que hace t2 y que la lectura que realiza t2 no está ordenada con respecto a la escritura que hace t1, es posible que no se escriba algún incremento en la variable n lo que produce un valor final menor a 200,000.

Entonces debemos impedir que ambos threads ejecuten al mismo tiempo la instrucción `n++`. Justamente esta instrucción es la sección crítica.

Para resolver este problema vamos a ejecutar la instrucción `n++` dentro de una instrucción `synchronized`; notar que utilizamos el objeto `obj` para sincronizar los threads.
Entonces el código del programa queda de la siguiente manera:

```java
class A extends Thread
{
  static long n;
  static Object obj = new Object();
  public void run()
  {
    for (int i = 0; i < 100000; i++)
      synchronized(obj)
      {
        n++;
      }
  }
  public static void main(String[] args) throws Exception
  {
    A t1 = new A();
    A t2 = new A();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println( n );
  }
}
```

Al ejecutar varias veces el programa anterior podemos ver que el valor final de la variable n siempre es 200,000, debido a que ahora las operaciones de lectura y escritura se ejecutan en el orden correcto:

 
Si bien es cierto que es necesario sincronizar los threads para que el programa funcione correctamente, la sincronización hace más lento el programa, ya que obliga a que ciertas partes del programa se ejecuten en serie (una tras otra) y no en paralelo (al mismo tiempo).
 

## Sincronización de threads

### El método `wait()`

Cuando un thread ejecuta el método wait() de la clase Object, el thread entra en un estado de espera pasiva (el thread no consume ciclos de CPU mientras espera).

El método wait() debe utilizarse dentro de una instrucción synchronized.

En Java cada lock tiene una cola de espera, de manera que cada thread que ejecuta el método wait(), se forma en la cola de espera del lock asociado a la instrucción synchronized.

Una vez que el thread queda en estado de espera pasiva, se libera el lock. Cuando el thread "despierta" vuelve a adquirir el lock.

El método wait() puede producir la excepción InterruptedException, por tanto es necesario gestionar las interrupciones colocando el método wait() dentro de un bloque try.

#### Nota

Si un método no gestiona una interrupción, es necesario agregar al encabezado del método la claúsula throws seguida de una lista de las interrupciones que pueden producirse dentro del método, esto le dice al compilador que el método no gestiona esas interrupciones, por tanto, tendrá que gestionarlas el método que invoca al método actual.

Por ejemplo:

```java
public static void main(String[] args) throws Exception
```

En este caso, el método “main” no va a gestionar ninguna excepción, por tanto, la cláusula “throws Exception” le dice al compilador que la gestión de la excepción Exception y todas sus subclases, la realizará el método que invoca al método “main”.

Cuando usamos “throws Exception”, la gestión de todas las excepciones se delega al método que invoca el método actual, debido a que "Exception" es la superclase de todas las excepciones.

Debido a que el método “main” no es invocado por otro método Java, si se produce una excepción en el método “main”, el programa termina y se despliega en la pantalla la excepción que se produjo.


### Los métodos `notify()` y `notifyAll()`

Para "despertar" un thread que se encuentra en espera pasiva, otro thread deberá invocar el método notify() o el método notifyAll().

El método notify() "despierta" el primer thread que se encuentra en la cola de espera del lock.

El método notifyAll() "despierta" todos los threads que se encuentran en la cola de espera del lock.

Al igual que el método wait(), los métodos notify() y notifyAll() deben ejecutar dentro de una instrucción synchronized.

Debido a que los métodos wait(), notify() y notifyAll() son métodos de la clase Object, se heredan a todas las clases en Java.

El siguiente programa muestra un ejemplo del uso de wait() y notify().

En este caso se crea un thread el cual entra en modo de espera pasiva invocando el método wait(), entonces el thread principal "despierta" el thread invocando el método notify().

```java
class Notificacion
{
  static Object obj = new Object();
  static class Worker extends Thread
  {
    public void run()
    {
      try
      {
        synchronized(obj)
        {
          System.out.println("antes de wait");
          obj.wait();
          System.out.println("después de wait");
        }
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  public static void main(String[] args) throws Exception
  {
    Worker w = new Worker();
    w.start();
    Thread.sleep(1000);
    synchronized(obj)
    {
      System.out.println("antes de notify");
      obj.notify();
      System.out.println("después de notify");
    }
  }
}
```
