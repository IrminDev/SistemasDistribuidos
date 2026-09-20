# Servidor HTTP

## ¿Qué es un servidor HTTP?

La clase de hoy vamos a construir un servidor HTTP utilizando como base el servidor multi-thread que vimos anteriormente.

Un servidor HTTP es un servidor que recibe peticiones HTTP a través de una conexión TCP.

En general, los servidores HTTP son multi-thread, de tal manera que el servidor creará un thread cada vez que un cliente se conecta, de esta forma cada thread se encarga de recibir la petición y enviar la respuesta al cliente.

Por default los servidores HTTP reciben conexiones a través del puerto 80, sin embargo un servidor HTTP puede utilizar otro puerto, por ejemplo el puerto 8080.

El protocolo HTTP define las operaciones que el servidor HTTP puede realizar cuando recibe una petición, a estas operaciones se les conoce como "métodos".

Los métodos HTTP más utilizados son: GET, POST, PUT, DELETE y HEAD.

El método GET es utilizado por los navegadores (browsers) para solicitar un recurso o contenido al servidor HTTP.

Si el recurso existe, el servidor regresa al navegador como mínimo lo siguiente: el código 200 (OK), el tipo del recurso, la longitud del recurso y el contenido.

Si el recurso no existe, el servidor regresa al navegador el código 404 (Not Found).

El método HEAD es idéntico al método GET, solo que el método HEAD no regresa el recurso.

Más adelante veremos cómo utilizar los métodos GET, POST, PUT y DELETE para implementar servicios web de tipo REST.

## `ServidorHTTP.java`

Ahora vamos a implementar un servidor HTTP; tomaremos como base el código del servidor multithread que vimos la clase anterior.

El servidor HTTP escuchará el puerto 80 (el puerto default para HTTP).

El servidor HTTP va a responder dos end-points:

- `/suma?a=<valor>&b=<valor>&c=<valor>` va a regresar la suma de los tres valores.
- `/` va a regresar una página HTML incluyendo un botón; cuando se presione el botón se hará la siguiente petición: `/suma?a=1&b=2&c=3`.

Tal como lo hace el servidor multithread que vimos la clase anterior, cada vez que un cliente (navegador) se conecte al servidor HTTP, se creará un thread el cual recibirá como parámetro el socket cliente.

Primeramente, escribimos las siguientes instrucciones en el método run():

```java
BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
PrintWriter salida = new PrintWriter(conexion.getOutputStream());
```

En este caso no vamos a utilizar las clases DataInputStream y DataOutpuStream debido a que necesitamos los métodos readLine() y println() los cuales están disponibles en las clases BufferedReader y PrintWriter, respectivamente.

Notar que creamos la instancia de BufferedReader a partir del InputStream disponible en el socket, así mismo, creamos la instancia de PrintWriter a partir del OutputStream disponible en el socket.

Ahora vamos a recibir la petición mediante el método readLine().

Cada línea en el protocolo HTTP termina con un carriage return (código ASCII 13) y line feed (código ASCII 10), por tanto, para leer la petición utilizamos el método readline().

```java
String req = entrada.readLine();
System.out.println("Petición: " + req);
```

El método readLine() reconoce cualquier terminador de línea (\r, \n o \r\n) y lo descarta; no obstante, la petición enviada por el cliente termina con \r\n, la string "req" no contiene \r ni \n.

Además de la petición, el navegador envía encabezados; cada encabezado termina con carriage return  y line feed).

Después de los encabezados, el navegador envía una línea en blanco (carriage return  y line feed).

Podemos leer los encabezados mediante el siguiente código (notar que no estamos procesando los encabezados, solo los desplegamos):

```java
for (;;)
{
  String encabezado= entrada.readLine();
  System.out.println("Encabezado: " + encabezado);
  if (encabezado.equals("")) break;
}
```

Para extraer los valores que pasan como parámetros a la función "suma", implementaremos la función valor().
 
Esta función obtiene el valor de cada parámetro en la URL; recibe como parámetros una string con los parámetros incluidos en la URL, y el nombre de uno de los parámetros (variable), entonces regresa el valor del parámetro.

 

```java
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
```

Para que el servidor HTTP procese la petición, vamos a utilizar el método startsWith() de la clase String.
```java
if (req.startsWith("GET / "))
{
  String respuesta =
    "<html>" +
    "<script>" +
    "function get(req,callback)" +
    "{" +
    "const xhr = new XMLHttpRequest();" +
    "xhr.open('GET', req, true);" +
    "xhr.onload=function()" +
    "{" +
    "if (callback != null) callback(xhr.status,xhr.response);" +
    "};" +
    "xhr.send();" +
    "}" +
    "</script>" +
    "<body>" +
    "<button onclick=\"get('/suma?a=1&b=2&c=3',function(status,response){alert('estatus:' + status + ' ' + 'respuesta: ' + response);})\">Aceptar</button>" +
    "</body>" +
    "</html>";
  salida.println("HTTP/1.1 200 OK");
  salida.println("Content-type: text/html; charset=utf-8");
  salida.println("Content-length: " + respuesta.length());
  salida.println("Connection: close");
  salida.println();
  salida.println(respuesta);
  salida.flush();
}
```
else
if (req.startsWith("GET /suma?"))
{
  String parametros = req.split(" ")[1].split("\\?")[1];
  String respuesta = String.valueOf(valor(parametros,"a") + valor(parametros,"b") +
                                                               valor(parametros,"c"));
  salida.println("HTTP/1.1 200 OK");
  salida.println("Access-Control-Allow-Origin: *");
  salida.println("Content-type: text/plain; charset=utf-8");
  salida.println("Content-length: "+respuesta.length());
  salida.println("Connection: close");
  salida.println();
  salida.println(respuesta);
  salida.flush();
}
 

Notar que en la respuesta que envía el servidor HTTP al navegador, es necesario separar los encabezados del contenido, mediante una línea en blanco; en este caso el contenido es de tipo texto.

El servidor HTTP envía al navegador el código 200 indicando que no hubo error, además envía el tipo mime del contenido utilizando el encabezado Content-Type y la longitud del contenido mediante el encabezado Content-Length.

El navegador debe conocer el tipo mime del contenido para poder interpretarlo correctamente, por ejemplo, si el recurso es una página web, el tipo mime es "text/html", entonces el navegador interpretará el contenido como un archivo HTML (desplegará botones, campos de captura, etc.).

Si el tipo mime es text/plain, el navegador desplegará el texto en la pantalla.

La forma general del encabezado Content-Type es la siguiente:

```text
Content-Type: tipo/subtipo
```

Donde tipo es el tipo del contenido (por ejemplo, text) y subtipo es una especificación del tipo (por ejemplo: html).

A la cadena tipo/subtipo se le llama tipo mime (Multipurpose Internet Mail Extensions).

Los servidores pueden obtener el tipo mime de un archivo a partir de la extensión del archivo. Ver: Lista completa de tipos MIME

Por default, el protocolo HTTP/1.1 deja abierta la conexión entre el cliente y el servidor, de manera que el cliente (navegador) pueda enviar varias peticiones utilizando la misma conexión.

En este caso, el servidor envió al navegador el encabezado “Connection: close”, indicando que la conexión se cerrará después de enviar la respuesta.

En código anterior, el servidor HTTP regresa al navegador un texto plano: la suma de los valores que pasan como parámetros en la URL.

Más adelante se explicará el encabezado "Access-Control-Allow-Origin", el cual habilita CORS (Cross-Origin Resource Sharing).

## Respuesta `404 File Not Found`

Si la petición enviada por el navegador no inicia con "/" o "GET /suma?" entonces el servidor regresará al navegador el código de error 404 indicando que no se encontró el recurso:

```java
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
```

Si queremos que el servidor envíe al navegador otro mensaje de error, podemos enviar un código entre 400 y 499, agregando un mensaje específico. Ver: Códigos de estado de respuesta HTTP.

 

## CORS — Cross-Origin Resource Sharing

Supongamos que tenemos un servidor HTTP que actúa a la vez como servidor web y como servidor de aplicaciones (p.e. ServidorHTTP.java, Tomcat, Weblogic, GlassFish, IIS, etc.).

El servidor HTTP va a alojar la página web p.html. Esta página web invoca (utilizando Javascript), el método web suma(int a,int b,int c)

Cuando el navegador solicita la página web p.html al servidor, el servidor le envía la página.

Supongamos que el usuario selecciona una opción en la página web, la cual invoca el método "suma".

Entonces el servidor HTTP envía la respuesta al navegador, en este caso, el número 6 (la suma 1+2+3).

¿Qué pasa si la página web está alojada en un servidor que ejecuta en una computadora diferente a la computadora donde ejecuta el método web?

Para que el navegador no bloquee la respuesta del método web, la respuesta debe incluir uno o varios encabezados "Access-Control-Allow-Origin", cada encabezado deberá incluir el dominio desde el cual se permite el acceso al método web.

Un asterisco * indica que cualquier origen puede acceder el método web.

## El encabezado `Last-Modified`

Un servidor HTTP puede regresar al navegador una variedad de contenidos, por ejemplo, una página web, una imagen, un archivo, etc.

En general, estos recursos no se modifican con frecuencia en el servidor, por esta razón es conveniente que el navegador guarde una copia del recurso y solo reciba una actualización del recurso cuando éste ha sido modificado en el servidor.

Los navegadores almacenan copias de los recursos en una cache.

Almacenar los recursos en la cache tiene muchas ventajas, entre otras: mejora la velocidad de carga, reduce el ancho de banda utilizado, ahorra energía en el cliente (esto es especialmente útil en dispositivos móviles), mejora la experiencia del usuario y reduce la carga en el servidor.

Para que el navegador guarde un recurso en la cache, el servidor envía al navegador la respuesta "HTTP/1.1 200 OK", el encabezado "Last-Modified" y el recurso solicitado. El encabezado, "Last-Modified" le dice al navegador la fecha (en formato RFC1123) de la última modificación del recurso.

Por ejemplo, la respuesta:

```http
HTTP/1.1 200 OK
Last-Modified:  Fri, 25 Aug 2023 21:00:00 GMT
```

Le dice al navegador que guarde el recurso en la cache indicando así mismo la fecha de la última modificación, en este caso el viernes 25 de agosto de 2023 a las 21 horas tiempo GMT.

Cada vez que el navegador solicita el recurso al servidor, el navegador agregará el siguiente encabezado a la petición:

```http
If-Modified-Since: Fri, 25 Aug 2023 21:00:00 GMT
```

Esto le dice al servidor que el navegador tiene una copia del recurso solicitado, indicando la fecha de última modificación (la cual le envío el servidor previamente utilizando el encabezado "Last-Modified").

Entonces el servidor deberá comparar la fecha enviada por el navegador y la fecha de la última modificación del recurso en el servidor, si la primera fecha es menor a la segunda fecha, entonces el servidor deberá enviar al navegador la respuesta "HTTP/1.1 200 OK", el encabezado "Last-Modified" incluyendo la nueva fecha de modificación y el recurso solicitado. De otra forma, el servidor solo enviará al navegador la siguiente respuesta:

```http
HTTP/1.1 304 Not Modified
```

Como podemos ver, el navegador recibe la actualización del recurso solo si el recurso se modificó después de que el navegador lo recibió por última vez.

¿Es conveniente guardar cualquier respuesta en la cache?

No siempre, ya que en algunos casos las respuestas podrían ser distintas en cada petición, por ejemplo, la respuesta de un servicio web.

En tal caso, el servidor no regresará al navegador el encabezado "Last-Modified", por tanto, el navegador no guardará en la cache la respuesta y tampoco enviará el encabezado "If-Modified-Since" en cada petición ulterior.


## El encabezado `Cache-Control`

El encabezado Cache-Control es enviado por el servidor para controlar la cache del navegador.
Las opciones más comunes del encabezado Cache-Control son las siguientes:

- `public`: el contenido puede almacenarse en forma pública; cualquier usuario puede tener acceso al contenido.
- `private`: el contenido debe almacenarse en forma privada en el navegador del usuario; solo el usuario tiene acceso al contenido.
- `no-store`: el contenido no debe almacenarse.
- `no-cache`: el contenido puede almacenarse en la cache, pero el navegador validará si fue modificado.
- `max-age=tiempo`: indica el tiempo en segundos que se guardará el contenido en la cache.
- `immutable`: indica que el contenido no será modificado por el servidor.


Es posible incluir varias opciones separadas por comas, considerando que las opciones más restrictivas pueden anular a las más permisivas.


## El encabezado `Content-Disposition`

El encabezado Content-Disposition es enviado por el servidor, para indicar al navegador que guarde en el disco local el contenido que regresa una petición GET, en lugar de mostrarlo en la pantalla.

Por ejemplo, el siguiente encabezado indica al navegador que guarde el archivo descargado con el nombre "documento.pdf":

```http
Content-Disposition: attachment; filename="documento.pdf"
```

## Los encabezados `Server` y `Date`

Opcionalmente el servidor HTTP podría enviar al navegador los siguientes encabezados, indicando el nombre del servidor y la fecha:

```java
salida.println("Server: ServidorHTTP.java");
salida.println("Date: " + new Date());
```

## ¿Cómo obtener la IP del cliente?

Para desplegar la dirección IP del cliente que se conectó al servidor HTTP, podemos incluir la siguiente instrucción al principio del método run():

```java
System.out.println(conexion.getRemoteSocketAddress().toString());
```

En general, los servidores guardan en una bitácora la fecha y hora, la IP del cliente y la petición GET de manera que se pueda monitorear cualquier ataque que reciba el servidor HTTP.
