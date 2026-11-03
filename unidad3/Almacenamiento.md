# Almacenamiento en la nube

Los servicios de almacenamiento en la nube que vamos a estudiar en el curso son los siguientes:

- Azure Disk Storage. Discos para máquinas virtuales.
- Azure Blob Storage. Grandes objetos binarios (Binary Large Objects) no estructurados.
- Azure Files. Archivos compartidos (File Systems) en la nube.

## Azure Disk Storage

El servicio de Azure Disk Storage proporciona almacenamiento persistente para máquinas virtuales.

Cuando se crea una máquina virtual, se crea un disco de arranque para el sistema operativo.

También es posible crear discos de datos, independientes del disco de sistema operativo, los cuales se pueden adjuntar y quitar de la máquina virtual en cualquier momento.

Los discos pueden ser discos administrados (managed disks) o discos no administrados (unmanaged disks), así mismo, pueden ser HDD (Hard Disk Drive) o SSD (Solid State Disk).

Los discos de datos son utilizados como almacenamiento persistente para bases de datos, servidores de archivos (p.e. servidores web) y servidores de aplicaciones.

Azure Disk Storage ofrece:

- Disponibilidad de 99.999%
- Encriptado.
- Durabilidad grado empresarial con tres réplicas.
- Respaldo nativo.

- Recuperación ante desastres.
- Monitoreo.

## Discos no administrados

Los discos no administrados deben ser administrados por el usuario, esto es, el usuario es responsable de administrar la infraestructura de almacenamiento y la replicación para alta disponibilidad.

Los discos no administrados se almacenan en una cuenta de almacenamiento como blobs VHD (Virtual Hard Disk). El usuario debe crear y administrar la cuenta de almacenamiento y adjuntar los discos VHD a las máquinas virtuales.

La escalabilidad de los discos no administrados está limitada por la capacidad de la cuenta de almacenamiento.

## Discos administrados
En los discos administrados, Azure se encarga de la administración del almacenamiento y la replicación para alta disponibilidad.

Los discos administrados se pueden adjuntar directamente con las máquinas virtuales.

La escalabilidad no se ve limitada por la capacidad de una cuenta de almacenamiento.

Los discos administrados pueden ser respaldados y restaurados directamente.

### Discos HDD Standard

Los discos HDD estándares son recomendados para cargas de trabajo que no son críticas y accesos a los datos poco frecuentes.

Los discos HDD estándares ofrecen una capacidad máxima de 32 TB, con 2,000 IOPS (operaciones de entrada/salida por segundo) y un ancho de banda de 500 MB por segundo.

### Discos SSD Standard

Los discos SSD estándares son recomendados para cargas de trabajo de producción, con requerimientos de latencia consistente a bajo costo.

Los discos SSD estándares ofrecen una capacidad máxima de 32 TB, con 6,000 IOPS (operaciones de entrada/salida por segundo) y un ancho de banda de 750 MB por segundo.

### Discos SSD Premium

Los discos SSD premium son recomendados para cargas de trabajo de producción, con requerimientos de alto rendimiento.

Los discos SSD premium ofrecen una capacidad máxima de 32 TB, con 20,000 IOPS (operaciones de entrada/salida por segundo) y un ancho de banda de 900 MB por segundo.

### Discos Ultra

Los discos ultra son recomendados para las aplicaciones más demandantes en entrada/salida intensiva y latencias por debajo de un milisegundo.

Los discos ultra ofrecen una capacidad máxima de 64 TB, con 160,000 IOPS (operaciones de entrada/salida por segundo) y un ancho de banda de 2 GB por segundo.

### Crear un disco de datos administrado
1. Ingresar al portal de Azure.
2. En el campo de búsqueda escribir "Discos".
3. Seleccionar la pestaña "Comenzar".
4. Dar clic en el botón "Crear".
5. Seleccionar un grupo de recursos o crear uno nuevo.
6. Ingresar en nombre del disco.
7. Seleccionar la región (la región debe ser la misma que la región de la máquina virtual a la que se conectará el disco).
8. En el campo "Zona de disponibilidad" seleccionar la zona de disponibilidad o bien, seleccionar "No se requiere redundancia de la infraestructura".

9. En el campo "Tamaño" seleccionar "Cambiar de tamaño".
10. En el campo "Tipo de almacenamiento" seleccionar el tipo de disco a crear, p.e. "SSD estándar (almacenamiento con redundancia local)".

Nota. El disco SSD Premium solo se puede adjuntar a máquinas virtuales creadas en una zona de disponibilidad.

11. Seleccionar el tamaño del disco, p.e. 4 GB.

12. Dar clic en el botón "Revisar y crear".
13. Dar clic en el botón "Crear".

### Adjuntar un disco de datos a una máquina virtual

1. Seleccionar la máquina virtual.
2. Seleccionar la opción "Discos".
3. Seleccionar la opción "Adjuntar discos existentes".

4. En el campo "Nombre del disco" seleccionar el disco a adjuntar.
5. Dar clic en el botón "Aplicar".

Notar que un disco de datos puede ser adjuntado a la máquina virtual encendida.

En Azure los nombres de los discos se asignan en el orden en que son detectados por el sistema, p.e. si el primer disco detectado es /dev/sda (el disco de sistema operativo), /dev/sdb sería el segundo, y así sucesivamente. En este caso, el primer disco de datos adjuntado sería /dev/sdc ya que ya existe un disco /dev/sdb.

Para ver los nombres de los discos (asignados por Azure) y sus particiones, se puede ejecutar el siguiente comando:

```bash
lsblk
```

Cuando se crea un nuevo disco de datos, éste viene como disco RAW, lo cual significa que no tiene un sistema de archivos (filesystem) que permita almacenar datos de manera estructurada. Por tanto, es necesario formatearlo antes de que pueda ser montado y utilizado.

### Formatear un disco de datos

1. Formatear con el sistema de archivos ext4 un disco de datos (p.e. /dev/sdc) recién creado:

```bash
sudo mkfs.ext4 /dev/sdc
```

## Montar un disco de datos

1. Crear un punto de montaje (p.e. /mnt/datos):

```bash
sudo mkdir /mnt/datos
```

2. Montar el disco en el punto de montaje:

```bash
sudo mount /dev/sdc /mnt/datos
```

### Quitar un disco de datos de una máquina virtual
1. Ingresar a la máquina virtual y desmontar el disco.
2. Seleccionar la máquina virtual.
3. Seleccionar la opción "Discos".
4. Dar clic en ícono "Desasociar" que aparece al final del renglón correspondiente al disco que se va a quitar.
5. Dar clic en el botón "Aplicar".

6. Ahora podemos ejecutar el comando lsblk para ver que el disco ya no está conectado a la máquina virtual.
Notas:

- Un disco de datos puede ser desasociado de la máquina virtual encendida.
- Antes de desasociar un disco de datos es necesario desmontarlo.

### Eliminar un disco de datos

1. Ingresar al portal de Azure.
2. En el campo de búsqueda escribir "Discos".
3. Seleccionar la pestaña "Recursos".
4. Seleccionar el disco a eliminar.
5. Dar clic en el ícono "Eliminar".

Reproducir Vídeo

### Encriptado de un disco de datos

Como una medida se seguridad, adicional al encriptado que realiza Azure, vamos a encriptar el disco de datos utilizando criptografía simétrica.

1. Crear un punto de montaje, p.e. /mnt/securedata:

```bash
sudo mkdir /mnt/securedata
```

2. Instalar el módulo dm-crypt:

```bash
sudo apt-get update
sudo apt-get install dmsetup cryptsetup
```

3. Cargar el módulo dm-crypt:

```bash
sudo modprobe dm-crypt
```

4. Verificar que el device-mapper haya reconocido el modulo dm-crypt y agregado crypt como destino disponible:

```bash
sudo dmsetup targets
```

5. Debido a que el disco recien creado tiene un formato RAW, debemos formatear el disco para encripción, en este caso el disco es /dev/sdc:

```bash
sudo cryptsetup -y luksFormat /dev/sdc
```

Se debe ingresar la clave (passphrase).

6. Verificar que el disco ha sido inicializado para encripción:

```bash
sudo cryptsetup luksDump /dev/sdc
```

7. Inicializar un device mapper para ser montado (aqui "securedata" es el nombre del device mapeado, notar que el device mapper se coloca en el directorio /dev/mapper:  /dev/mapper/securedata):

```bash
sudo cryptsetup luksOpen /dev/sdc securedata
```

8. Formatear el device mapper (en este caso con el tipo de filesystem ext4):

```bash
sudo mkfs.ext4 -m 0 /dev/mapper/securedata
```

9. Montar el device mapper:

```bash
sudo mount /dev/mapper/securedata /mnt/securedata
```

10. Para ver el filesystem montado, ejecutar el comando:

```bash
df
```

Notar que el directorio punto de montaje es propiedad del usuario root y del grupo root, si es necesario se puede cambiar el propietario y grupo.
11. Para desmontar el device mapper y cerrar el encriptado:

```bash
sudo umount /mnt/securedata
sudo cryptsetup luksClose securedata
```

12. Para montar nuevamente el disco (cada vez que se reinicie la máquina virtual):

```bash
sudo cryptsetup luksOpen /dev/sdc securedata
sudo mount /dev/mapper/securedata /mnt/securedata
```

### Cómo leer un disco a nivel RAW
El siguiente programa permite leer un disco a nivel RAW (a un nivel por debajo del sistema de archivos), para verificar que los datos se almacenan encriptados, por otra parte, si el disco se formateó y se montó sin encriptar, entonces los datos aparecerán en claro:

```c
// Carlos Pineda G. 2012
// Requiere instalar gcc:
// sudo apt-get update
// sudo apt-get install gcc
// Se compila:
// gcc -o p p.c
// Se ejecuta:
// sudo ./p
#include <stdio.h>
#include <errno.h>
#include <ctype.h>
#define LONG_BUFFER (4*1024)
char buffer[LONG_BUFFER];
int main ()
{
  FILE *f = fopen("aquí va el nombre del device, p.e. /dev/sdc","rb");
  int i;
  if (f == NULL)
  {
fprintf(stderr,"Error al abrir el device, errno=%i\n",errno);
return -1;
  }
  for(;;)
  {
fread(buffer,LONG_BUFFER,1,f);
if (feof(f))
break;
for (i = 0; i < LONG_BUFFER; i++)
if (isprint(buffer[i]))
printf("%c",buffer[i]);
  }
  return 0;
}
```
