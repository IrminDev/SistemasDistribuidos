# Replicación de un sistema completo en la nube

En la actualidad el cómputo en la nube nos permite realizar el aprovisionamiento dinámico de recursos de forma fácil y rápida.
Entonces ¿por qué no replicar el sistema completo?

## Arquitectura de un sistema replicado

La siguiente figura muestra la arquitectura general de un sistema replicado:

En este caso se supone que el sistema consta de dos o más servidores de aplicaciones conectados a un servidor de bases de datos, el cual puede implementar la replicación de datos mediante discos espejo.

El administrador de tráfico funciona como un proxy inverso, el cual recibe las conexiones y peticiones de los clientes y las reenvía al sistema principal con copia a la réplica.

El sistema principal procesa las peticiones del cliente, y envía las respuestas al administrador de tráfico, el cual las reenvía al cliente. Notar que el administrador de tráfico deberá ignorar las respuestas de la réplica.

Para garantizar la recuperación en caso de desastre catastrófico en el site donde ejecuta el sistema principal, la réplica deberá ejecutar en una locación diferente, por lo tanto, el administrador de tráfico y la réplica deberán tener cada uno una IP pública.

Mientras el sistema principal puede estar ejecutando en la nube o en una instalación propia de la empresa (on-premise), la réplica estaría funcionando en la nube, en alguna locación geográfica diferente a la locación donde ejecuta el sistema principal.

El administrador de tráfico puede ser un programa o un appliance localizado en mismo site donde ejecuta el sistema principal, de manera que el administrador de tráfico se pueda conectar al sistema principal mediante una red privada.

Como puede observarse, tanto el sistema principal como la réplica realizarán las mismas transacciones sobre la base de datos (y/o el sistema de archivos), por lo que la consistencia de los datos está garantizada.

Si se produce un error en la comunicación entre el administrador de tráfico y la réplica, el cliente deberá recibir un de error de comunicación.

En caso de falla del sistema principal y/o falla del administrador de tráfico, solo habrá que redefinir el dominio del sistema a la IP pública de la réplica, entonces los clientes estarán conectados directamente al sistema de respaldo.

Desde luego, posteriormente será necesario: 1) sacar de producción la réplica, 2) realizar una copia espejo de la réplica, y 3) restaurar el sistema principal y el administrador de tráfico.

## Replicación de una arquitectura de tres capas en la nube

Como caso de estudio, veremos la replicación de la arquitectura de tres capas en la nube que implementamos en la tarea 1, consistente en una aplicación web (front-end), servicios web para Tomcat (back-end) y MySQL.

Como se explicó anteriormente, para replicar un sistema, podemos crear una máquina virtual en la nube (réplica) que procese todas las peticiones que realizan los clientes, simultáneamente al proceso de las mismas peticiones que realiza el sistema principal.

La arquitectura del sistema replicado que estudiaremos es la siguiente:

En el NSG (Network Security Group) del sistema principal se abrirá el puerto de entrada 80, y en el NSG de la réplica se abrirá el puerto de entrada 8080.

AdministradorTrafico.java

Vamos a utilizar el programa Proxy.java el cual es un proxy transparente escrito en Java, modificado para que funcione como un administrador de tráfico.

La arquitectura del programa Proxy.java se muestra a continuación:

Como podemos ver, el programa Proxy.java consta de un servidor y un cliente.

El servidor del proxy recibe la conexión del cliente del usuario, entonces el cliente del proxy realiza una conexión al servidor del usuario.

Cada petición que envía el cliente del usuario es recibida por el servidor del proxy, entonces es reenviada por el cliente del proxy al servidor del usuario.

La respuesta del servidor del usuario es recibida por el cliente del proxy, el cual la reenvía al servidor del proxy, y este a su vez, la reenvía al cliente del usuario.

Para implementar el administrador de tráfico, se agregó al programa Proxy.java, un cliente al programa el cual se conectará a una réplica del servidor del usuario, de manera que ahora las peticiones que recibe el servidor del proxy serán reenviadas al servidor principal y al servidor réplica.

Podemos ver que sólo la respuesta del servidor principal será enviada al usuario.

Para probar la replicación de una arquitectura de tres capas basada en Tomcat, haremos lo siguiente:

1. Crear dos máquinas virtuales utilizando la imagen que se creó en la tarea

2. Abrir el puerto de entrada 80 protocolo TCP en el NSG (Network Security Group) de la máquina virtual 1 (sistema principal).

3. Abrir el puerto de entrada 8080 protocolo TCP en el NSG (Network Security Group) de la máquina virtual 2 (réplica), ingresar en el campo "Origen" ("Source" si la pantalla está en inglés) la IP de la máquina virtual 1 (por seguridad, la máquina virtual 1 será la única computadora que podrá acceder la máquina virtual 2).
4. Conectar a la máquina virtual 1 utilizando el programa ssh.

5. Utilizando el programa sftp enviar a la máquina virtual 1 el archivo: AdministradorTrafico.java

6. Compilar en la máquina virtual 1 el programa AdministradorTrafico.java.

7. Iniciar Tomcat en las máquinas virtuales 1 y 2.

8. Ejecutar en la máquina virtual 1 el administrador de tráfico:

```bash
sudo java AdministradorTrafico localhost 8080 ip-máquina-virtual-2 8080 80 &
```

Donde:

- localhost es la IP de la máquina virtual principal, donde ejecuta el administrador de tráfico.
- 8080 es el puerto que abre Tomcat en la máquina virtual principal.
- IP-máquina-virtual-2 es la IP de la réplica,
- 8080 es el puerto abierto por Tomcat en la réplica,
- 80 es el puerto abierto por el programa AdministradorTrafico.java en el sistema principal

Notar que no es necesario abrir el puerto 8080 en la primera máquina virtual, ya que el programa AdministradorTrafico.java y Tomcat se comunican localmente mediante loopback.

En este caso ejecutamos el administrador de tráfico con "sudo" para que éste pueda abrir el puerto 80 en la máquina virtual 1.

9. En la computadora local (Windows, Linux o MacOS):

9.1 En un navegador se deberá ingresar la URL para iniciar la aplicación web, notar que no es necesario ingresar el número de puerto, ya que se utiliza el puerto default 80:

http://ip-máquina-virtual-1/prueba.html

9.2 Ahora se deberá ejecutar diversas funciones en la aplicación web de manera que se realicen las transacciones correspondientes en la base de datos.

9.3 Podemos verificar que las bases de datos en la máquina virtual y la máquina virtual réplica son idénticas.

## Administrador de tráfico SSL

En el caso anterior utilizamos el programa AdministradorTrafico.java como un administrador de tráfico para HTTP, lo cual no es de utilidad en la práctica debido a que actualmente se utiliza el protocolo HTTPS en lugar del protocolo HTTP por razones de seguridad.

En realidad, el programa AdministradorTrafico.java permite derivar cualquier tráfico TCP no SSL, por ejemplo: HTTP, FTP, Telnet, SMTP, etc.

Para que el programa AdministradorTraficor.java pueda administrar tráfico HTTPS, solo es necesario implementar la conexión con sockets seguros en el servidor del proxy; el programa modificado se llama AdministradorTraficoSSL.java

La arquitectura del sistema replicado es la siguiente:

Ahora, se abrirá el puerto de entrada 443 en el NSG (Network Security Group) del sistema principal, y se abrirá el puerto de entrada 22 en el NSG de la réplica.
El programa AdministradorTraficoSSL.java requiere un keystore que contenga el certificado del servidor, este certificado puede ser un certificado auto-firmado o un certificado emitido por una CA. Por tanto, es necesario definir el nombre del keystore y la contraseña en el programa AdministradorTraficoSSL.java.

Notar que en esta arquitectura no es necesario instalar un certificado digital en Tomcat, ya que Tomcat seguirá utilizando el protocolo HTTP.

Debido a que el administrador de tráfico (AdministradorTraficoSSL.java) se comunica en forma insegura con el Tomcat que ejecuta en la réplica, será necesario crear un túnel SSH entre el administrador de tráfico y Tomcat.

Para ejecutar el administrador de tráfico HTTPS:

```bash
sudo java AdministradorTraficoSSL localhost 8080 localhost 8081 443 &
```

Notar que la IP de la máquina virtual 2 se substituye por "localhost", debido a que el segundo cliente del administrador de tráfico se conecta al cliente de SSH, el cual ejecuta en la computadora principal.

También es necesario que el cliente SSH abra un puerto diferente (p.e. 8081) al puerto que abre Tomcat (8080), debido a que el cliente SSH y Tomcat ejecutan en la misma computadora.

Entonces para ejecutar la aplicación web, ingresamos en el navegador la URL de la aplicación web:

https://ip-máquina-virtual-1/prueba.html

Si el certificado del servidor es un certificado auto-firmado, entonces el navegador mostrará una advertencia que indica que el certificado es inseguro. En este caso podemos continuar debido a que sabemos que el certificado es seguro porque lo hemos creado nosotros.
