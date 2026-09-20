# Máquinas virtuales en Azure

## Crear una máquina virtual con Ubuntu

Ingresar al [portal de Azure](https://azure.microsoft.com/es-mx/features/azure-portal/).

1. Dar clic al botón "Iniciar sesión".

2. En el portal de Azure seleccionar "Máquinas virtuales".

3. Seleccionar la opción "+Crear".

4. Seleccionar la opción "Máquina virtual en Azure"

5. Seleccionar el grupo de recursos o crear uno nuevo. Un grupo de recursos es similar a una carpeta donde se pueden colocar los diferentes recursos de nube que se crean en Azure.

6. Ingresar el nombre de la máquina virtual.

7. Seleccionar la región donde se creará la máquina virtual. Notar que el costo de la máquina virtual depende de la región. Es recomendable crear la máquina virtual en una región cercana de manera que la comunicación con la máquina virtual sea más rápida.

7.1 En el campo "Opciones de disponibilidad" seleccionar "No se requiere redundancia de la infraestructura".

7.2 En el campo "Tipo de seguridad" seleccionar "Estándar".

8. Seleccionar la imagen, en este caso vamos a seleccionar Ubuntu Server 24.

8.1 En el campo "Arquitectura de VM" seleccionar: `x64`.

9. En el campo "Tamaño" dar clic en "Ver todos los tamaños", en este caso vamos a seleccionar una máquina virtual con 1 GB de memoria RAM (Tamaño de VM B1s). Dar clic en el botón "Seleccionar".

10. En el campo "Tipo de autenticación" seleccionar "Contraseña".

11. Ingresamos el nombre del usuario, por ejemplo: ubuntu

12. Ingresamos la contraseña y confirmamos la contraseña. La contraseña debe tener al menos 12 caracteres, debe al menos una letra minúscula, una letra mayúscula, un dígito y un carácter especial.

13. En las "Reglas de puerto de entrada" se deberá dejar abierto el puerto 22 para utilizar SSH (la terminal de secure shell).

14. Dar clic en el botón "Siguiente: Discos>"

15. Seleccionar el tipo de disco de sistema operativo, en este caso vamos a seleccionar HDD estándar.

16. Dar clic en el botón "Siguiente: Redes>"

Por default se creará una red virtual y la máquina virtual se conectará a esta red virtual. También se creará una IP pública.

17. Dar clic en el botón "Siguiente: Administración>"

18. Dar clic en el botón "Siguiente: Supervisión>"

19. En el campo "Diagnóstico de arranque" seleccionar "Deshabilitar".

20. Dar clic en el botón "Revisar y crear".

21. Dar clic en el botón "Crear".

22. Dar clic a la campana de notificaciones (barra superior de la pantalla) para verificar que la maquina virtual se haya creado.

23. Dar clic en el botón "Ir al recurso". En la página se puede ver la dirección IP pública de la máquina virtual. Esta dirección puede cambiar cada vez que se apague y se encienda la máquina virtual.

24. Para conectarnos a la máquina virtual vamos a utilizar el programa ssh disponible en Windows, Linux y MacOS.

25. En una ventana de comandos de Windows o una terminal de Linux o macOS, ejecutar `ssh` así:

```bash
ssh usuario@ip-máquina-virtual
```

Donde usuario es el usuario que ingresamos en el paso 11, ip-máquina-virtual es la ip pública de la máquina virtual.

26. Para enviar o recibir archivos de la máquina virtual, utilizar `sftp`, disponible en Windows, Linux y macOS:

```bash
sftp usuario@ip-máquina-virtual
```

Para enviar archivos se utiliza el comando put y para recibir archivos se utiliza el comando get.
 
Para mayor información sobre `sftp`, consultar [How To Use SFTP to Securely Transfer Files](https://www.digitalocean.com/community/tutorials/how-to-use-sftp-to-securely-transfer-files-with-a-remote-server-es).


## Abrir un puerto de entrada

Para que los programas que ejecutan en la máquina virtual pueda recibir conexiones a través de un determinado puerto, es necesario crear una regla de entrada para el puerto.

Por ejemplo, vamos a abrir el puerto 50000 en la máquina virtual que acabamos de crear:

1. Entrar al portal de Azure.
2. Seleccionar "Máquinas virtuales".
3. Seleccionar la máquina virtual.
4. Dar clic en "Configuración de red".
5. Dar clic en el botón "+Crear ACL del puerto".
6. Seleccionar "Regla de puerto de entrada".
7. En "Intervalos de puertos de destino", ingresar `50000`.
8. Seleccionar el protocolo `TCP`.
9. En "Nombre", ingresar un nombre para la regla, por ejemplo: `Puerto_50000`.
10. Dar clic en el botón "Aceptar".


## Detener una máquina virtual

Cuando una máquina virtual no se utiliza es conveniente detenerla con el fin de reducir el costo. Para detener una máquina virtual:

1. Dar clic en la opción "Detener" en el portal de Azure.
2. Cuando se pregunta ¿Desea detener ...? dar clic en el botón "Sí".

Esperar a que el estado de la máquina virtual sea "Desasignada".

## Encender una máquina virtual

Para encender una máquina virtual

1. Seleccionar la opción "Iniciar" en la página de la máquina virtual dentro del portal de Azure.

Esperar a que el estado de la máquina virtual sea "En ejecución".

## Eliminar una máquina virtual

Para eliminar una máquina virtual:

1. Seleccionar la opción "Eliminar" en la página de la máquina virtual dentro del portal de Azure.

2. Seleccionar los recursos a eliminar: disco de SO, interfaces de red y direcciones IP públicas.

3. Aceptar la advertencia que dice que se eliminarán los recursos seleccionados.

4. Dar clic en el botón "Eliminar".

## Restablecer la contraseña de una máquina virtual

En caso de no recordar la contraseña de un usuario de una máquina virtual, se puede realizar el siguiente procedimiento:

1. Ejecutar el portal de Azure.
2. Seleccionar "Máquinas virtuales". La máquina virtual debe estar encendida.
3. Seleccionar la máquina virtual que se desea modificar.
4. En el menú izquierdo, seleccionar "Restablecer contraseña".
5. En el menú "Modo", seleccionar "Restablecer contraseña".
6. En "Nombre de usuario", ingresar el usuario.
7. En "Contraseña", ingresar la nueva contraseña.
8. En "Confirmar contraseña", repetir la nueva contraseña.
9. Dar clic en el ícono "Actualizar".


## Crear una imagen de una máquina virtual con Ubuntu

Vamos a ver cómo crear la imagen de una máquina virtual con Ubuntu en Azure y cómo crear máquinas virtuales a partir de la imagen.

Una máquina virtual generalizada es una máquina virtual cuyo sistema operativo se ha despojado de la configuración específica y la configuración de usuarios.

Una imagen generalizada es la captura de un sistema operativo de una máquina virtual generalizada..

### Notas importantes

1. La captura de la imagen de una máquina virtual inutiliza la máquina virtual ya que una máquina virtual generalizada no se puede iniciar o modificar.

2. La generalización de una máquina virtual no implica que se borre toda la información confidencial que pudiera existir en la máquina virtual. Es muy importante considerar lo anterior si se va a redistribuir la imagen de la máquina virtual.

3. La generalización de una máquina virtual no elimina el archivo /etc/resolv.conf (ver: resolvconf)

4. La generalización de una máquina virtual deshabilita la contraseña de root.

5. La opción +user del comando waagent elimina la última cuenta creada en la máquina virtual incluyendo el directorio del usuario. Si se desea conservar el usuario y el directorio, no se deberá utilizar la opción +user al generalizar la máquina virtual mediante el comando waagent.

6. Para generalizar una máquina virtual con Windows se utiliza el programa sysprep.exe, ver: https://docs.microsoft.com/en-us/azure/virtual-machines/generalize.

7. Una imagen se cobra de acuerdo al espacio en disco que ocupa, ver: Precios de Managed Disks.

8. El tamaño del disco predeterminado en la imagen es el tamaño del disco de sistema operativo de la máquina virtual. Al crear la máquina virtual a partir de la imagen, el tamaño del disco de sistema operativo podrá ser mayor al que tenía la máquina virtual original, pero no podrá ser de menor tamaño.

Para generalizar la máquina virtual utilizaremos el agente `waagent`, que elimina los datos específicos de la máquina virtual.

1. Crear una máquina virtual con Ubuntu.

2. Abrir una ventana cmd de Windows o una terminal de Linux o MacOs.

3. Ejecutar el programa ssh en la ventana, pasando como parámetros el usuario (por ejemplo ubuntu) y la ip pública de la máquina virtual:

```bash
ssh usuario@ip
```

4. Para generalizar la máquina virtual y eliminar la última cuenta de usuario creada incluyendo el directorio del usuario, ejecutar el comando:
```bash
sudo waagent -deprovision+user
```
 
Si se quiere conservar en la imagen la última cuenta de usuario creada, ejecutar el comando:
 
```bash
sudo waagent -deprovision
```
 
5. En el portal de Azure seleccionar la máquina virtual que se quiera capturar como imagen.
 

6. Seleccionar la opción "Captura".

6.1 En la opción "Compartir imagen con Shared Image Gallery" seleccionar "No, capturar solo una imagen administrada".

7. Marcar la casilla "Eliminar automáticamente esta máquina virtual después de crear la imagen", ya que una máquina virtual generalizada no se puede iniciar o modificar.

8. Ingresar el nombre de la imagen a crear.

9. Dar clic en el botón "Crear".

10. Dar clic en la campana de notificaciones para verificar que se haya creado la imagen de la máquina virtual.

## Crear una máquina virtual a partir de una imagen
1. En la sección "Todos los recursos" en el portal de Azure seleccionar la imagen de la máquina virtual.
 

2. Seleccionar la opción "+Crear máquina virtual".

3. Seleccionar el grupo de recursos donde se creará la máquina virtual.

4. Ingresar el nombre de la máquina virtual.

5. Seleccionar el tamaño de la máquina virtual.

6. Seleccionar el tipo de autenticación (Clave pública SSH o Contraseña). En su caso, ingresar el usuario y contraseña.

7. Dar clic en el botón "Siguiente: Discos >"

8. Seleccionar el tipo de disco del sistema operativo (p.e. HDD estándar). Notar que el tamaño predeterminado del disco es el tamaño del disco de sistema operativo de la máquina virtual original. Se puede seleccionar un tamaño mayor pero no un tamaño menor al tamaño del disco original.

9. Si no hay otra configuración que se quiera realizar, dar clic en el botón "Revisar y crear".

10. Dar clic en el botón "Crear".
