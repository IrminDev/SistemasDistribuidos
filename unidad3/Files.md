# Azure Files

Azure Files es un servicio administrado de archivos compartidos en la nube, los cuales se pueden acceder mediante el protocolo SMB (Server Message Block).

Para utilizar Azure Files, se debe crear una cuenta de almacenamiento, y dentro de ésta, se podrán crear recursos compartidos de archivos; para cada recurso compartido de archivos se podrá configurar una capacidad entre 100 GB y 100 TB.

Los recursos compartidos de archivos funcionan como filesystems sin servidor (serverless), y pueden montarse simultáneamente en Linux, Windows y MacOS.

Debido a que Azure Files no soporta el encriptado sobre el protocolo NFS, la seguridad en la transferencia se basa en la seguridad de la red virtual, por tanto, NFS solo puede utilizarse dentro de una red virtual de Azure.

SMB puede utilizarse fuera de la red virtual.

Para acceder los recursos compartidos de archivos, además de los protocolos SMB y NFS, también es posible utilizar APIs para .NET, Java, Python, JavaScript, C++, y Go, o bien, la API REST de Azure Files.

Reproducir Vídeo

Recurso compartido de archivos mediante SMB

Crear una cuenta de almacenamiento

1. Ingresar al portal de Azure.
2. En el campo de búsqueda escribir "Cuentas de almacenamiento".
3. Seleccionar "+Crear".
4. Seleccionar un grupo de recursos o crear uno nuevo.
5. Ingresar en nombre de la cuenta de almacenamiento. El nombre debe ser único en Azure, de 3 a 24 caracteres (letras minúsculas y dígitos).
6. Seleccionar la región.
7. En el campo "Servicio principal" seleccionar "Azure Files".
8. En el campo "Rendimiento" seleccionar "Prémium". Esta selección no se puede cambiar después de crear la cuenta de almacenamiento.
9. En el campo "Redundancia" seleccionar "Almacenamiento con redundancia local (LRS)".
10. Dar clic en el botón "Revisar y crear".
11. Dar clic en el botón "Crear".

Agregar un recurso compartido de archivos

1. Seleccionar la cuenta de almacenamiento.
2. Seleccionar la opción "Recursos compartidos de archivos".
3. Seleccionar "+Recurso compartido de archivos".
4. Ingresar el nombre del recurso compartido de archivos.
5. Ingresar la capacidad aprovisionada (entre 100 y 102400 GB).
6. En la sección "Backup" se puede deshabilitar la opción "Habilitar copia de respaldo".
7. Dar clic en el botón "Revisar+crear".
8. Dar clic en el botón "Crear".

Crear una conexión al recurso compartido de archivos

1. Seleccionar la cuenta de almacenamiento.
2. Seleccionar la opción "Recursos compartidos de archivos".
3. Seleccionar el recurso compartido de archivos.
4. Dar clic en la opción "Conectar".
5. Seleccionar "Linux".
6. Dar clic en el botón "Mostrar script".
7. Copiar el script.

Montar el recurso compartido de archivos en una máquina virtual con Ubuntu

1. Crear una máquina virtual con Ubuntu.
2. Ejecutar el script obtenido anteriormente.

Notar que el script crea un directorio el cual será el punto de montaje, p.e. /media/<recurso-compartido-de-archivos>

3. Para ver el archivo en Azure:
3.1 Seleccionar la cuenta de almacenamiento.
3.2 Seleccionar la opción "Recursos compartidos de archivos".
3.3 Seleccionar el recurso compartido de archivos.
3.4 Seleccionar la opción "Examinar".
4. Puede cargar un archivo directamente en Azure:
4.1 Seleccionar la cuenta de almacenamiento.
4.2 Seleccionar la opción "Recursos compartidos de archivos".
4.3 Seleccionar el recurso compartido de archivos.
4.4 Seleccionar la opción "Examinar".
4.5 Seleccionar "Cargar".
4.6 Seleccionar "Buscar archivos".
4.7 Seleccionar el archivo en la ventana.
4.8 Dar clic en el botón "Cargar".
4.9 En la máquina virtual se puede ver que el archivo se encuentra en el directorio punto de montaje /media/<recurso-compartido-de-archivos>

Donde <recurso-compartido-de-archivos> es el nombre del recurso compartido de archivos.

Notas.

- Antes de ejecutar el script es recomendable borrar las credenciales anteriores (si ya se había montado un recurso compartido de archivos previamente): /etc/smbcredentials/<cuenta-de-almacenamiento>.cred
- Para desmontar el recurso compartido de archivos se deberá ejecutar: sudo umount /media/<recurso-compartido-de-archivos>
- Para volver a montar el recurso compartido de archivos es necesario ejecutar nuevamente el script (comentando la línea donde se crea el directorio).
