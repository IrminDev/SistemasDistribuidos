# Túnel SSH

El túnel SSH es un mecanismo que utiliza el comando SSH para implementar un canal encriptado, esto es, usando SSH es posible ejecutar un cliente el cual se comunicará con un servidor remoto (application server) utilizando el túnel SSH.

El sistema SSH consiste en un cliente y un servidor.

Para abrir una terminal con conexión segura ejecutamos el comando "ssh" el cual se conecta al servidor SSH.

En general, las distribuciones físicas de Linux no tienen instalado el servidor SSH, por tanto, es necesario instalarlo.

Por otra parte, las máquinas virtuales en la nube tienen instalado el servidor SSH, lo que permite al usuario conectarse a la máquina virtual utilizando el comando "ssh".

El túnel SSH opera en la capa 5 del modelo OSI (capa de sesión) y en la capa 6 (capa de presentación). El túnel SSH utiliza la capa de sesión para establecer, mantener y finalizar la sesión entre el cliente y el servidor, y la capa de presentación para encriptar el tráfico entre el cliente y el servidor.

Cuando habilitamos el túnel SSH para comunicar el cliente con el servidor, el cliente se conecta localmente al cliente SSH (el cliente SSH hace las veces de servidor); entonces el cliente SSH envía los datos al servidor SSH, el cual a su vez redirige los datos al servidor (el servidor SSH hace las veces de cliente del servidor).

Debido a que el túnel SSH permite mapear los puertos, el cliente se puede conectar al cliente SSH a través de un puerto diferente, al puerto que utiliza el servidor SSH para conectarse al servidor.

El mapeo de puertos es de utilidad cuando el puerto que utiliza el servidor está ocupado.

En el siguiente diagrama podemos ver que el cliente se conecta a un servidor proxy en el cliente SSH, a través del puerto 3049, mientras que un cliente proxy en el servidor SSH, se conecta al servidor a través del puerto 2049:

Podemos notar que los programas cliente SSH y servidor SSH funcionan como proxys transparentes, cuando se utilizan para implementar un túnel SSH.

## Implementación de un túnel SSH

Ahora vamos a crear una VPN cliente-servidor entre los programas Cliente.java y Servidor2.java utilizando un túnel SSH en modo local forwarding.

1. Crear una máquina virtual en Azure con Ubuntu 24.

2. Acceder a la máquina virtual utilizando SSH.

3. Instalar el JDK en la máquina virtual.

4. Enviar el programa ServidorHTTP.java a la máquina virtual.

5. Compilar y ejecutar en la máquina virtual el programa `ServidorHTTP.java`:

```bash
javac ServidorHTTP.java
java ServidorHTTP
```

6. En la computadora local (cliente) subir el túnel SSH:

```bash
ssh -N -f -L 80:localhost:8080 usuario@<ip-máquina-virtual>
```

Dónde:

- `-N`: no se ejecutará un comando remoto; solo se creará el túnel SSH.
- `-f`: `ssh` se ejecutará en segundo plano (*background*).
- `-L 80:localhost:8080`: el navegador se conecta al cliente SSH por el puerto 80. El destino `localhost:8080` indica que el servidor SSH se conecta al servidor HTTP por el puerto 8080. El túnel mapea el puerto 80 al puerto 8080.
- `usuario`: nombre del usuario en la computadora remota.
- `<ip-máquina-virtual>`: dirección IP de la computadora remota.

En Linux, ejecutando el siguiente comando, se puede ver que el túnel queda ejecutando en segundo plano:

```bash
ps -ef | grep ssh
```

7. En la computadora local (cliente), ingresar en el navegador:

```text
http://localhost
```

En este caso suponemos que el navegador (cliente) se conecta al puerto local 80 de localhost, y el programa ServidorHTTP.java escucha el puerto 8080 (podemos ver que SSH mapea el puerto local 80 al puerto remoto 8080).

También se puede ejecutar el programa ServidorHTTP.java como comando remoto, desde la computadora local (cliente) de la siguiente manera:

```bash
ssh -L 80:localhost:8080 usuario@<ip-máquina-virtual> java ServidorHTTP
```

Notar que en este caso no se utilizaron las opciones -N y -f.

## Autenticación mediante clave pública y clave privada

Cuando se ejecuta el comando SSH, la autenticación por omisión es mediante usuario y contraseña. Sin embargo, si queremos que el túnel SSH suba automáticamente en un script, será necesario implementar la autenticación mediante clave pública y clave privada.

### Creación de la clave pública y la clave privada

1. Acceder a la computadora cliente utilizando SSH.

2. Para crear el par de claves, ejecutar el siguiente comando en la computadora local (cliente), la opción -t indica el algoritmo a utilizar y la opción -b el tamaño de la llave en bits:

```bash
ssh-keygen -t rsa -b 2048
```

Presionar la tecla Enter cuando se pregunta el nombre del archivo.

Cuando se pregunta la contraseña presionar la tecla "Enter" si no se requiere la contraseña.

La contraseña permite encriptar la clave privada en la computadora local (cliente). Si se define una contraseña ésta se deberá ingresar cada vez que se ejecute SSH.

3. Para copiar la clave pública a la máquina virtual donde ejecuta el servidor:

```bash
ssh-copy-id usuario@<ip-máquina-virtual>
```

Dónde "usuario" es el nombre del usuario en la máquina virtual donde ejecuta el servidor y <ip-máquina-virtual> es la dirección IP de la máquina virtual.

4. En la computadora local (cliente) probar el acceso a la máquina virtual, ahora la autenticación utilizará las claves pública y privada por tanto no se preguntará la contraseña:

```bash
ssh usuario@<ip-máquina-virtual>
```

> **Nota:**

Cuando se utiliza el par de llaves para la autenticación de SSH en Ubuntu, es recomendable incluir la opción -i (la cual indica la ruta de la llave privada) al crear el túnel.

Si el túnel escucha un puerto menor a 1024 del lado del cliente, se deberá crear el túnel utilizando "sudo", lo que crea una sesión diferente a la sesión del usuario propietario del par de llaves, por tanto, "ssh" no encuentra la llave privada y entonces pregunta la contraseña.

Por ejemplo, supongamos que el túnel escucha el puerto 80 del lado del cliente, y el usuario se llama "ubuntu", entonces agregamos la opción -i al crear el túnel:

```bash
sudo ssh -i /home/ubuntu/.ssh/id_rsa -N -f -L 80:localhost:8080 ubuntu@<ip-máquina-virtual>
```

Por otra parte, cuando se utiliza el par de llaves para la autenticación de SSH en Windows, no es necesario utilizar la opción `-i`, ya que en Windows no es necesario ejecutar SSH con permisos de administrador.
