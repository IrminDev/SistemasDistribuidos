# El sistema de archivos distribuidos NFS

El sistema de archivos NFS (Network File System) permite que una computadora (cliente) tenga acceso de manera transparente a los archivos contenidos en un servidor remoto.

Utilizando NFS, la computadora cliente puede montar un sistema de archivos remoto, por tanto, es posible desde el cliente, crear, borrar, leer y escribir archivos que se encuentran "físicamente" en el servidor.

NFS se implementa mediante una arquitectura cliente-servidor.

Cuando la computadora cliente monta el sistema de archivos remoto, el cliente NFS se conecta al servidor NFS, entonces el cliente podrá acceder a los archivos en el sistema de archivos remoto, utilizando el protocolo NFS a través del puerto default 2049.

## Instalación de NFS en el servidor

1. Para instalar NFS en el servidor se ejecutan los siguientes comandos:

```bash
sudo apt update
sudo apt install nfs-kernel-server
```

2. Crear el directorio (punto de montaje) que va a compartir el servidor con los clientes:

```bash
sudo mkdir /var/nfs -p
```

La opción -p indica que se deberán crear todos los directorios en la ruta si no existen.

3. El propietario del directorio /var/nfs es root debido a que este directorio se creó con sudo. Podemos ver el propietario del directorio /var/nfs ejecutando el comando:

```bash
ls -l /var
```

4. Debido a que NFS convierte el acceso del usuario root en el cliente en un acceso con el usuario "nobody:nogroup" en el servidor, es necesario cambiar el propietario y permisos del directorio creado anteriormente:

```bash
sudo chown nobody:nogroup /var/nfs
sudo chmod 777 /var/nfs
```

5. Podemos verificar el nuevo propietario:

```bash
ls -l /var
```

6. Ahora se debe registrar el directorio creado en el archivo de configuración de NFS.

6.1 Editar el archivo /etc/exports:

```bash
sudo vi /etc/exports
```

6.2 Agregar la siguiente línea, guardar y salir del editor (en este caso 40.76.45.28 es la IP del cliente):

```exports
/var/nfs 40.76.45.28(insecure,rw,sync,no_subtree_check)
```

La opción "insecure" permite que NFS use un puerto "inseguro", es decir, un puerto mayor o igual a 1024, ya que los puertos por debajo de 1024 solo pueden ser abiertos con permisos de administrador. En este caso usaremos el puerto 2049 el cual es el puerto estándar para NFS.

### Notas de seguridad.

- Debido a que los atacantes saben que el puerto 2049 es el puerto utilizado para NFS, es recomendable utilizar otro puerto.
- Se recomienda utilizar la versión 4 de NFS la cual implementa el protocolo de autenticación Kerberos (autenticación mutua entre el cliente y el servidor) así como el encriptado mediante TLS o IPsec, sin embargo, la versión 4 puede resultar más lenta que la versión 3.
- Es importante configurar el NSG del servidor para que solo acepte conexiones desde las direcciones IP de clientes de confianza.

La opción "rw" permite la lectura y escritura.

La opción "sync" hace que las modificaciones realizadas por el cliente sobre los archivos se escriban en el disco del servidor. Por otra parte, la opción "async" no escribe los cambios en el disco, lo que puede ocasionar que los cambios se pierdan en caso de una caída del servidor.

```text
La opción "no_subtree_check" deshabilita la verificación de que el archivo que accede el cliente esté en el árbol de directorios exportado. Esta verificación causa problemas si el archivo es renombrado cuando el cliente lo tiene abierto. En general la opción "subtree_check" se utiliza para sistemas de archivos donde no se renombran los archivos y la opción "no_subtree_check" se utiliza para sistemas de archivos donde se realizará renombramiento de archivos.
```

Para una descripción de los permisos se puede consultar Understanding the /etc/exports File y exports linux page.

6.3 Actualizar la tabla de file systems exportados por NFS:

```bash
sudo exportfs -ra
```

6.4 Para ver los file systems exportados por NFS:

```bash
sudo exportfs
```

6.5 Para activar la nueva configuración, se requiere reiniciar el servidor NFS:

```bash
sudo systemctl restart nfs-kernel-server
```

7. Ahora debemos abrir el puerto de entrada 2049 utilizando el portal de Azure:

```text
Intervalos de puertos de destino: 2049
Protocolo: TCP
Nombre: puerto_nfs
```

## Instalación de NFS en el cliente

8. Para instalar NFS en el cliente se ejecutan los siguientes comandos:

```bash
sudo apt update
sudo apt install nfs-common
```

9. Crear el directorio (punto de montaje) en el cliente. el cual será "espejo" del punto de montaje del servidor:

```bash
sudo mkdir -p /nfs
```

10. Montar el directorio remoto /var/nfs (en este caso 40.87.94.140 es la IP del servidor):

```bash
sudo mount -v -t nfs 40.87.94.140:/var/nfs /nfs
```

11. Para desmontar el directorio /nfs:

```bash
sudo umount /nfs
```

## Probar el acceso a archivos remotos

11. En el servidor crear un archivo, en este caso utilizamos el editor vi:

```bash
vi /var/nfs/texto.txt
```

12. Escribir un texto (como el siguiente) guardar y salir de la edición:

```text
Esta es una prueba
```

13. En el cliente editar el archivo /nfs/texto.txt:

```bash
vi /nfs/texto.txt
```

14. Agregar la siguiente línea, guardar y salir de la edición:

```text
Esta es otra prueba
```

15. En el servidor desplegar el contenido del archivo /var/nfs/texto.txt:

```bash
more /var/nfs/texto.txt
```

16. En el cliente desplegar el contenido del directorio /nfs:

```bash
ls -l /nfs
```

## Referencia

Sistemas de archivos de red

# NFS sobre un túnel SSH

Las versiones de NFS anteriores a la versión 4 no utilizan un canal seguro para autenticar y encriptar la comunicación, por esta razón cuando se utiliza NFS fuera de una red segura es necesario implementarlo sobre un túnel SSH.

Cuando habilitamos el túnel SSH para comunicar el cliente NFS con el servidor NFS, el cliente NFS se conecta localmente al cliente SSH (el cliente SSH hace las veces de servidor de NFS); entonces el cliente SSH envía los datos al servidor SSH, el cual a su vez redirige los datos al servidor de NFS (el servidor SSH hace las veces de cliente del servidor de NFS).

Debido a que el túnel SSH permite mapear los puertos, el cliente NFS se puede conectar al cliente SSH a través de un puerto diferente, al puerto que utiliza el servidor SSH para conectarse al servidor NFS. El mapeo de puertos es de utilidad cuando el puerto estándar 2049 está ocupado.

En la siguiente arquitectura podemos ver que el cliente NFS se conecta al cliente SSH, a través del puerto 3049, mientras que el servidor SSH se conecta al servidor NFS en localhost, a través del puerto 2049:

## Instalación de NFS seguro en el servidor

1. Para instalar NFS en el servidor se ejecutan los siguientes comandos:

```bash
sudo apt update
sudo apt install nfs-kernel-server
sudo apt install portmap
```

2. Crear el directorio compartido en el servidor;

```bash
sudo mkdir /var/nfs -p
```

3. El propietario del directorio /var/nfs es root debido a que este directorio se creó con sudo. Podemos ver el propietario del directorio /var/nfs ejecutando el comando:

```bash
ls -l /var
```

4. Debido a que NFS convierte el acceso del usuario root en el cliente en un acceso con el usuario "nobody:nogroup" en el servidor, es necesario cambiar el propietario y permisos del directorio creado anteriormente:

```bash
sudo chown nobody:nogroup /var/nfs
sudo chmod 777 /var/nfs
```

5. Podemos verificar el nuevo propietario:

```bash
ls -l /var
```

6. Ahora se debe registrar el directorio creado en el archivo de configuración de NFS.

6.1 Editar el archivo /etc/exports:

```bash
sudo vi /etc/exports
```

6.2 Agregar la siguiente línea, guardar y salir del editor:

```exports
/var/nfs localhost(insecure,rw,sync,no_subtree_check)
```

Notar que el servidor aceptará conexiones del servidor SSH el cual actúa como cliente de NFS.

6.3 Actualizar la tabla de file systems exportados por NFS:

```bash
sudo exportfs -ra
```

6.4 Para ver los file systems exportados por NFS:

```bash
sudo exportfs
```

6.5 Para activar la nueva configuración, se requiere reiniciar el servidor NFS:

```bash
sudo systemctl restart nfs-kernel-server
```

Notar que en este caso no se abrirá el puerto de entrada 2049 en el servidor.

Nota de seguridad. Es recomendable configurar el NSG del servidor para que solo acepte conexiones SSH desde las direcciones IP de clientes de confianza.

## Instalación de NFS seguro en el cliente

8. Para instalar NFS en el cliente se ejecutan los siguientes comandos:

```bash
sudo apt update
sudo apt install nfs-common
sudo apt install portmap
```

9. Crear el directorio de montaje en el cliente (punto de montaje):

```bash
sudo mkdir -p /nfs
```

10. Para crear un túnel SSH entre cliente y el servidor se ejecuta el siguiente comando:

```bash
ssh -fNv -L 3049:localhost:2049 ubuntu@40.84.237.35
```

En este caso, a manera de ejemplo, estamos mapeando el puerto 3049 al puerto 2049, es decir, el cliente NFS se conectará al cliente SSH (el cual hace las veces de servidor NFS) a través del puerto 3049, y el servidor SSH (el cual hace las veces de cliente NFS) se conectará al servidor NFS a través del puerto 2049.

```text
Aquí "ubuntu" es un usuario en el servidor y "40.84.237.35" es la dirección IP del servidor.
```

11. Montar el directorio remoto:

```bash
sudo mount -t nfs -o port=3049 localhost:/var/nfs /nfs
```

Debido a que el puente SSH mapeará el puerto 3049 al puerto 2049, es necesario indicar el puerto al que se conectará el cliente NFS, en este caso el puerto 3049.

12. Para desmontar el directorio /nfs:

```bash
sudo umount /nfs
```

Nota. El uso de NFS sobre túnel SSH está indicado cuando la comunicación entre el cliente NFS y el servidor NFS cruza Internet, sin embargo, cuando el cliente NFS y el servidor NFS se encuentran ambos en una red segura (VNet), no es necesario utilizar el túnel SSH, toda vez que el encriptado incrementa la latencia en la comunicación.
