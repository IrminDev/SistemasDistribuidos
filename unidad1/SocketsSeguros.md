# Cliente-servidor con sockets seguros

Para que un cliente y un servidor se comuniquen mediante sockets seguros, se requiere un certificado digital.

Para propósitos de prueba, vamos a crear un certificado autofirmado utilizando el programa keytool incluido en el JDK.

En Java los certificados y las llaves privadas correspondientes se almacenan en un archivo llamado "keystore" o repositorio de certificados.

Para crear el repositorio de certificados `keystore_servidor.jks`, ejecutar:

```bash
keytool -genkeypair -keyalg RSA -alias certificado_servidor -keystore keystore_servidor.jks -storepass 1234567
```

La opción genkeypair genera un par de claves pública y privada.

La clave pública se pone en un certificado autofirmado con un solo elemento en la ruta de certificación. El alias, en este caso "certificado_servidor", define un nombre con el cual vamos a identificar el certificado. Keystore es un archivo (repositorio) donde se va a almacenar el certificado y la clave privada correspondiente. Keyalg es el algoritmo a utilizar para generar el par de claves, en este caso RSA. storepass es la contraseña para el keystore.

Entonces se deberán capturar los datos del certificado. Ejemplo:

```text
¿Cuáles son su nombre y su apellido?
  [Unknown]:  nombre
¿Cuál es el nombre de su unidad de organización?
  [Unknown]:  unidad
¿Cuál es el nombre de su organización?
  [Unknown]:  organizacion
¿Cuál es el nombre de su ciudad o localidad?
  [Unknown]:  CDMX
¿Cuál es el nombre de su estado o provincia?
  [Unknown]:  CDMX
¿Cuál es el código de país de dos letras de la unidad?
  [Unknown]:  MX
¿Es correcto CN=nombre, OU=unidad, O=organizacion, L=CDMX, ST=CDMX, C=MX?
  [no]:  si

Introduzca la contraseña de clave para <certificado_servidor>

(INTRO si es la misma contraseña que la del almacén de claves):
```

> **Importante:** Para Java, la clave del certificado `<certificado_servidor>` y la clave (`storepass`) del almacén (`keystore`) deben ser iguales. En este caso: `1234567`.

> **Nota:** En un certificado firmado por una CA, el campo `CN` (nombre común) contiene el nombre del dominio que ampara el certificado digital. Cuando un navegador web se conecta a un servidor web, compara el campo `CN` con el dominio indicado en la URL. Si no coinciden, despliega una advertencia.

Debido a que el keystore "keystore_servidor.jks" contiene la llave privada del servidor, es conveniente crear un keystore para el cliente, el cual contenga solamente el certificado del servidor, para esto vamos a exportar el certificado "certificado_servidor" contenido en el keystore:

```bash
keytool -exportcert -keystore keystore_servidor.jks -alias certificado_servidor -rfc -file certificado_servidor.pem
```

La opción exportcert lee del keystore el certificado identificado por el alias y genera un archivo texto que contiene el certificado, en este caso se genera el archivo certificado_servidor.pem

Entonces vamos a crear un keystore que utilizará el cliente (repositorio de confianza), este keystore deberá contener el certificado del servidor:

```bash
keytool -import -alias certificado_servidor -file certificado_servidor.pem -keystore keystore_cliente.jks -storepass 123456
```

La opción import lee el archivo certificado_servidor.pem e inserta el certificado en el keytore keystore_cliente.jks, identificando el certificado mediante el alias. Storepass es la contraseña del keystore.

En la siguiente figura podemos ver que el servidor utilizará el keystore que contiene el certificado del servidor y la clave privada respectiva. El cliente utilizará el keystore que solo contiene el certificado del servidor.

Por otra parte, también es posible que los programas cliente y servidor utilicen un certificado firmado por una CA, en este caso será necesario que el keystore que utilizará el servidor contenga: 1) los certificados intermedios, 2) el certificado del servidor y 3) la clave privada correspondiente.

Debido a que el certificado del servidor es un certificado de confianza emitido por una CA, no es necesario crear el repositorio keystore_cliente.jks. Más adelante en el curso veremos el proceso de adquisición de un certificado digital para un dominio y cómo crear el keystore del servidor.

Ahora veremos cómo crear un cliente y un servidor los cuales se comunicarán mediante sockets seguros.

## `ClienteSSL.java`

Primeramente asignamos a la propiedad "javax.net.ssl.trustStore" el nombre del keystore del cliente, y a la propiedad "javax.net.ssl.trustStorePassword" la contraseña del keystore del cliente:

```java
System.setProperty("javax.net.ssl.trustStore", "keystore_cliente.jks");
System.setProperty("javax.net.ssl.trustStorePassword","123456");
```

Nota. El método System.setProperty(String nombre_propiedad,String valor_propiedad) permite asignar un valor a una propiedad (variable) del sistema (Java). Debido a que las propiedades son "globales", es posible recuperar el valor de una propiedad del sistema desde cualquier parte del código, utilizando el método System.getProperty(String nombre_propiedad).

Ahora el cliente debe crear una instancia de la clase SSLSocketFactory:

```java
SSLSocketFactory cliente = (SSLSocketFactory) SSLSocketFactory.getDefault();
```

Entonces vamos a crear un socket que se conectará al servidor invocando el método createSocket de la clase SSLSocketFactory. En este caso el servidor se llama "localhost" (computadora local) y el puerto abierto en el servidor es el 50000.

```java
Socket conexion = cliente.createSocket("localhost", 50000);
```

Abrimos los streams de salida y de entrada como lo hicimos anteriormente.

```java
DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
DataInputStream entrada = new DataInputStream(conexion.getInputStream());
```

Ahora podemos enviar datos al servidor, por ejemplo vamos a enviar un double:

```java
salida.writeDouble(123456789.123456789);
```

Para terminar el programa cerramos la conexión con el servidor (al cerrar el socket se cierran también los streams asociados), en este caso vamos a poner un retardo de un segundo antes de cerrar la conexión, para permitir que el servidor tenga tiempo de recibir los datos:

```java
Thread.sleep(1000);
conexion.close();
```

 

## `ServidorSSL.java`

Al igual que el cliente, el servidor requiere el nombre del keystore y la contraseña, entonces asignamos a la propiedad "javax.net.ssl.keystore" el nombre del keystore del servidor, y a la propiedad "javax.net.ssl.keystorePassword" la contraseña del keystore del servidor:

```java
System.setProperty("javax.net.ssl.keyStore", "keystore_servidor.jks");
System.setProperty("javax.net.ssl.keyStorePassword","1234567");
```

El servidor debe crear una instancia de la clase SSLServerSocketFactory:

```java
SSLServerSocketFactory socket_factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
```

Vamos a crear un socket servidor que va a abrir, en este caso, el puerto 50000 utilizando el método createServerSocket de la clase SSLServerSocketFactory:

```java
ServerSocket socket_servidor = socket_factory.createServerSocket(50000);
```

Ahora invocamos el método accept de la clase ServerSocket. Cuando se recibe la conexión el método accept regresa un socket:

```java
Socket conexion = socket_servidor.accept();
```

Abrimos los streams de salida y de entrada:

```java
DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
DataInputStream entrada = new DataInputStream(conexion.getInputStream());
```

Ahora podemos recibir datos del cliente, en este caso vamos a recibir un double:

```java
double x = entrada.readDouble();
System.out.println(x);
```

Finalmente, cerramos la conexión:

```java
conexion.close();
```

> **Nota:** Si el certificado del servidor fue emitido por una CA, no es necesario crear el repositorio `keystore_cliente.jks`. Por tanto, `ClienteSSL.java` no requiere inicializar las propiedades `javax.net.ssl.trustStore` y `javax.net.ssl.trustStorePassword`.

## Implementación de un servidor HTTPS

Un servidor HTTPS es un servidor HTTP con sockets seguros, por lo tanto es muy sencillo convertir nuestro servidor HTTP en un servidor HTTPS.

Como vimos anteriormente, para establecer una comunicación segura entre un cliente y un servidor es necesario contar con un certificado digital, el cual puede ser un certificado autofirmado o bien, un certificado firmado por una autoridad certificadora (CA).

En el ejemplo que sigue utilizaremos el mismo repositorio que creamos anteriormente (keystore_servidor.jks), el cual contiene la llave privada y el certificado digital del servidor.

Debido a que el certificado autofirmado no es reconocido por el navegador como un certificado válido, al acceder a nuestro servidor HTTPS el navegador desplegará un mensaje de advertencia, indicando que el certificado no es de confianza porque está autofirmado.

Para propósito de pruebas, se puede dar clic al botón "Avanzado" y continuar, toda vez que el certificado lo hemos firmado nosotros. Cabe aclarar que en general no se debe acceder a una página web si el navegador despliega un mensaje de advertencia.

### `ServidorHTTPS.java`

El único cambio que haremos en el código del servidor HTTP, es agregar las instrucciones necesarias para crear el socket servidor seguro:

```java
public static void main(String[] args) throws Exception
{
    System.setProperty("javax.net.ssl.keyStore","keystore_servidor.jks");
    System.setProperty("javax.net.ssl.keyStorePassword","1234567");
    SSLServerSocketFactory socket_factory = (SSLServerSocketFactory)
                                            SSLServerSocketFactory.getDefault();
    ServerSocket servidor = socket_factory.createServerSocket(443);
    for(;;)
    {
      Socket conexion = servidor.accept();
      new Worker(conexion).start();
    }
}
```

El puerto que escucha el servidor es el 443 el cual es el estándar para HTTPS, sin embargo, es recomendable que el servidor utilice un puerto mayor a 1024 ya que los puertos menores o iguales a 1024 solo pueden ser abiertos por procesos que ejecutan con permisos de administración (sudo).

En general, es recomendable que un servidor (Apache, Tomcat, Weblogic, MySQL, Oracle, etc.) abra un puerto mayor que 1024 y se ejecute con permisos no administrativos, de lo contrario, se corre el riesgo de que un atacante tome el control del servidor y con ello pueda acceder a recursos del sistema.

Para probar el acceso a nuestro servidor HTTPS solo tenemos que ingresar la siguiente URL en un navegador:

```text
https://localhost
```

Más adelante veremos cómo adquirir un certificado firmado por una autoridad certificadora y cómo crear un repositorio (keystore) con este certificado, de manera que cualquier navegador pueda conectarse a nuestro servidor HTTPS sin ningún problema de seguridad.
