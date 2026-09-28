import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

class ServidorHTTP
{
  static class Worker extends Thread
  {
    Socket conexion;

    Worker(Socket conexion)
    {
      this.conexion = conexion;
    }

    int valor(String parametros, String variable) throws Exception
    {
      String[] p = parametros.split("&");
      for (int i = 0; i < p.length; i++)
      {
        String[] s = p[i].split("=");
        if (s[0].equals(variable))
          return Integer.parseInt(s[1]);
      }
      throw new Exception("Se espera la variable: " + variable);
    }

    public void run()
    {
      try
      {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        PrintWriter salida = new PrintWriter(conexion.getOutputStream());
	      String fecha_if_modified_since = null;

        String req = entrada.readLine();
        System.out.println("Petición: " + req);

        for (;;)
        {
          String encabezado = entrada.readLine();
          System.out.println("Encabezado: " + encabezado);

          if (encabezado.startsWith("If-Modified-Since: "))
	          fecha_if_modified_since = encabezado.substring(19);

          if (encabezado.equals("")) break;
        }

        if (req.startsWith("GET /suma?"))
        {
          String parametros = req.split(" ")[1].split("\\?")[1];

          String respuesta = String.valueOf(
            valor(parametros, "a") +
            valor(parametros, "b") +
            valor(parametros, "c")
          );

          salida.println("HTTP/1.1 200 OK");
          salida.println("Access-Control-Allow-Origin: *");
          salida.println("Content-type: text/plain; charset=utf-8");
          salida.println("Content-length: " + respuesta.length());
          salida.println("Connection: close");
          salida.println();
          salida.println(respuesta);
          salida.flush();
        }
	      else if (req.startsWith("GET /"))
        {
	        Path path = Paths.get(req.substring(5,req.length()-9));

	        if (Files.exists(path))
	        {
	          byte[] buffer = Files.readAllBytes(path);

	          Date fecha_modificacion = new Date(Files.getLastModifiedTime(path).toMillis());
	          String fecha_last_modified = String.format("%04d-%02d-%02d %02d:%02d:%02d",
						  fecha_modificacion.getYear()+1900,
						  fecha_modificacion.getMonth()+1,
						  fecha_modificacion.getDate(),
						  fecha_modificacion.getHours(),
						  fecha_modificacion.getMinutes(),
						  fecha_modificacion.getSeconds());

	          if (!fecha_last_modified.equals(fecha_if_modified_since))
	          {
              salida.println("HTTP/1.1 200 OK");
              salida.println("Content-type: text/html; charset=utf-8");
              salida.println("Content-length: " + buffer.length);
              salida.println("Last-Modified: " + fecha_last_modified);
              salida.println("Connection: close");
              salida.println();
              salida.flush();
	            conexion.getOutputStream().write(buffer);
	          }
	          else
	          {
              salida.println("HTTP/1.1 304 Not Modified");
              salida.flush();
	          }
	        }
	        else
	        {
	          String respuesta = "<html><body><h1>404 File Not Found</h1></body></html>";
	          salida.println("HTTP/1.1 404 Not Found");
	          salida.println("Content-Type: text/html; charset=utf-8");
	          salida.println("Content-Length: " + respuesta.length());
	          salida.println("Connection: close");
	          salida.println();
	          salida.println(respuesta);
	          salida.flush();
	        }
        }
      }
      catch (Exception e)
      {
        System.err.println("Error en la conexión: " + e.getMessage());
      }
      finally
      {
        try
        {
          conexion.close();
        }
        catch (Exception e)
        {
          System.err.println("Error en close: " + e.getMessage());
        }
      }
    }
  }

  public static void main(String[] args) throws Exception
  {
    ServerSocket socket_servidor = new ServerSocket(80);

    for (;;)
    {
      Socket conexion = socket_servidor.accept();
      new Worker(conexion).start();
    }
  }
}
