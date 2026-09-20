# Proxy

Un proxy es un servidor cuya función es recibir el tráfico TCP de un cliente y reenviarlo a un servidor final, así mismo, recibir el tráfico TCP del servidor y reenviarlo al cliente.

Existen diferentes tipos de proxies, entre otros:

- **Proxy transparente:** redirige el tráfico TCP sin que el cliente se dé cuenta de que existe un proxy entre el cliente y el servidor. Reenvía el tráfico TCP sin considerar un protocolo específico sobre TCP, como HTTP, SMTP o FTP.

- **Proxy inverso:** recibe el tráfico de red de un cliente y lo distribuye entre diferentes servidores de acuerdo con una configuración específica, ocultando las direcciones IP de los servidores.

Un proxy inverso también puede:

- Almacenar contenido estático, como imágenes y archivos JS o CSS.
- Distribuir tráfico mediante reglas de balanceo de carga.
- Distribuir tráfico de acuerdo con la petición, como un API gateway.
- Centralizar la gestión de la comunicación SSL utilizando un solo certificado digital en el proxy.

- **Proxy HTTP/HTTPS:** permite monitorear y filtrar las peticiones que envía el cliente al servidor HTTP, así como las respuestas del servidor.

## Arquitectura de un proxy transparente

Un proxy transparente consta de un servidor y un cliente.

Cuando el servidor del proxy recibe una conexión del cliente, el cliente del proxy se conecta al servidor.

El servidor del proxy recibe tráfico de red del cliente y lo envía al servidor, así mismo, el cliente del proxy recibe tráfico de red del servidor y lo envía al cliente.


## Implementación de un proxy transparente en Java

Vamos a implementar un proxy transparente mediante un servidor TCP multithread.

Cuando el servidor del proxy recibe la conexión del cliente, crea un thread de tipo Worker_1, éste thread crea un segundo thread de tipo Worker_2.

El thread de tipo Worker_2 se conecta al servidor remoto y se encarga de reenviar el tráfico TCP del servidor remoto al cliente.

El thread de tipo Worker_1 se encarga de reenviar el tráfico TCP del cliente al servidor remoto.

Debido a que un proxy transparente no implementa un protocolo específico sobre TCP, es necesario que el reenvío del tráfico TCP del cliente al servidor se pueda realizar en paralelo al reenvío del tráfico TCP del servidor al cliente. Por esta razón, es necesario crear dos threads que reenvíen el tráfico TCP de manera independiente (full-duplex).

El código del proxy transparente es el siguiente:

```java
/*
  Proxy.java
  Carlos Pineda G. 2024
*/
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
class Proxy
{
  static String host_remoto;
  static int puerto_remoto;
  static int puerto_local;
  static class Worker_1 extends Thread
  {
    Socket cliente_1,cliente_2;
    Worker_1(Socket cliente_1)
    {
      this.cliente_1 = cliente_1;
    }
    public void run()
    {
      try
      {
        // se conecta al host remoto
        cliente_2 = new Socket(host_remoto,puerto_remoto);
        // thread que dirige el tráfico del host remoto al cliente
        new Worker_2(cliente_1,cliente_2).start();
        InputStream entrada_1 = cliente_1.getInputStream();
        OutputStream salida_2 = cliente_2.getOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while((n = entrada_1.read(buffer)) != -1)
        {
          salida_2.write(buffer,0,n);
          salida_2.flush();
        }
      }
      catch (IOException e)
      {
        // captura silenciosamente las excepciones por desconexión
      }
      finally
      {
        try
        {
          if (cliente_1 != null) cliente_1.close();
          if (cliente_2 != null) cliente_2.close();
        }
        catch (IOException e2)
        {
          e2.printStackTrace();
        }
      }
    }
  }
  static class Worker_2 extends Thread
  {
    Socket cliente_1,cliente_2;
    Worker_2(Socket cliente_1,Socket cliente_2)
    {
      this.cliente_1 = cliente_1;
      this.cliente_2 = cliente_2;
    }
    public void run()
    {
      try
      {
        InputStream entrada_2 = cliente_2.getInputStream();
        OutputStream salida_1 = cliente_1.getOutputStream();
        byte[] buffer = new byte[4096];
        int n;
        while((n = entrada_2.read(buffer)) != -1)
        {
          salida_1.write(buffer,0,n);
          salida_1.flush();
        } 
      }
      catch (IOException e)
      {
        // captura silenciosamente las excepciones por desconexión
      }
      finally
      {
        try
        {
          if (cliente_1 != null) cliente_1.close();
          if (cliente_2 != null) cliente_2.close();
        }
        catch (IOException e2)
        {
          e2.printStackTrace();
        }
      }
    }
  }
  public static void main(String[] args) throws Exception
  {
    if (args.length != 3)
    { 
      System.err.println("Uso:\njava Proxy <host-remoto> <puerto-remoto> <puerto-local>");
      System.exit(1);
    }
    host_remoto = args[0];
    puerto_remoto = Integer.parseInt(args[1]);
    puerto_local = Integer.parseInt(args[2]);
    System.out.println("host_remoto: " + host_remoto + ", puerto_remoto: " + puerto_remoto + ", puerto_local: " + puerto_local);
    ServerSocket ss = new ServerSocket(puerto_local);
    for(;;)
    {
      // espera una conexión del cliente
      Socket cliente_1 = ss.accept();
      // thread que dirige el tráfico del cliente al host remoto
      new Worker_1(cliente_1).start();
    }
  }
}
```
