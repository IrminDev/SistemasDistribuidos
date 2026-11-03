# La clase de hoy vamos a ver el servicio de respaldos en la nube de Azure.

## Microsoft Azure Backup

Azure Backup es un servicio de respaldos en la nube al nivel de PaaS, que no requiere que el usuario cuente con el personal para realizar los respaldos ni la instalación de una infraestructura de almacenamiento.

Azure Backup permite respaldar y restaurar archivos, directorios, máquinas virtuales completas, bases de datos de SQL Server, o archivos y directorios On-Premise (mediante un agente que ejecuta en el equipo local).

Azure Backup es un servicio al nivel de plataforma (PaaS: Platform as a Service) debido a que se trata de un servicio de respaldos totalmente administrado sin la necesidad de crear una infraestructura (máquina virtual y almacenamiento).

Para proteger al usuario de ataques de ransomware (secuestro de datos) Azure Backup implementa la autenticación multi-factor. Así mismo, genera alertas si se detecta actividad sospechosa de respaldo o restauración de los datos del usuario.

Reproducir Vídeo

## Punto de restauración

Cada vez que se ejecuta el proceso de respaldo (backup job) para un recurso (p.e. una máquina virtual), se crea un punto de restauración, entonces es posible utilizar cualquier punto de restauración para recuperar los datos que se tenían en un momento dado.

Después del primer respaldo completo, los subsecuentes respaldos serán incrementales, es decir, solo van a incluir los cambios (archivos nuevos, archivos eliminados y archivos modificados) realizados desde el último respaldo.

## Almacén de Recovery Services

Un almacén de Recovery Services (vault) es un contenedor lógico que almacena los puntos de restauración.

Cuando el proceso de respaldo es eliminado, Azure Backup retiene por dos semanas los datos en el almacén del recovery services, de manera que el usuario puede recuperar los respaldos en caso de borrado accidental.

Al eliminar el proceso de respaldo no se elimina el almacén de Recovery Services.

Al eliminar el proceso de respaldo, los puntos de restauración quedan en estado soft-deleted durante 14 días (o más si se configura Enhanced Soft Delete con un período mayor).

Mientras existan puntos de restauración en soft-deleted, no se puede eliminar el vault.

Pasados los 14 días, los puntos de restauración se borran definitivamente, sin embargo, el vault sigue existiendo. Por tanto, es necesario eliminar explícitamente el vault.
Una característica importante de Azure Backup es que no limita la transferencia de datos de entrada o de salida.

La transferencia de datos de entrada se refiere a los datos transferidos al almacén de Recovery Services cuando se realiza el respaldo.

La transferencia de datos de salida se refiere a los datos que son transferidos desde el almacén de Recovery Services cuando se realiza una restauración.

## Geo-Redundant storage

Por omisión, el almacén de Recovery Services se crea como Geo-Redundant storage, lo cual asegura que los datos respaldados se replicarán en una región a cientos de kilómetros de la región actual.

## Políticas de respaldo

Es posible crear políticas de respaldo (también llamadas directivas de copia de seguridad), donde se define cuando se ejecuta el proceso de respaldo (backup job) y por cuánto tiempo se almacenarán los puntos de restauración.

Por omisión, la política de respaldo DailyPolicy ejecuta un respaldo diario y mantiene los puntos de restauración por 30 días.

## Restauración de una máquina virtual

La restauración de una máquina virtual se realiza seleccionando un punto de restauración.

Para restaurar una máquina virtual completa no es necesario detener o eliminar la máquina virtual previamente respaldada.

Para conectarse a la máquina virtual restaurada de utiliza el usuario y contraseña de la máquina virtual respaldada.

## Costo de Azure Backup

Azure Backup tiene costo (Azure Backup pricing) sin embargo es más barato que aprovisionar una máquina virtual con discos de almacenamiento para ejecutar los respaldos y mantener los datos.

El costo mensual del servicio de Azure Backup se calcula en base al tamaño del disco de la máquina virtual más el almacenamiento.

## Ventajas de Azure Backup

Azure Backup tiene entre otras ventajas:

- La replicación automáticamente en otra región geográficamente alejada.
- Los mecanismos de seguridad adicionales como las notificaciones de actividad sospechosa y la autenticación multi-factor.
- La creación de puntos de restauración de la máquina virtual en diferentes tiempos sin tener que sacarla de producción.

## Referencias

- Quick start - Back up a virtual machine in Azure
- Azure Backup pricing
- How to restore Azure VM data in Azure portal
- Redundancia de Azure Storage

- What is Azure Backup service?

# Actividades individuales a realizar

En esta actividad vamos a realizar un respaldo de una máquina virtual en Azure y la recuperación de la máquina virtual a partir del respaldo.

## Habilitar el respaldo de una máquina virtual en Azure

1. En el portal de Azure, seleccionar la máquina virtual que se va a respaldar o bien, crear una nueva máquina virtual.

2. Seleccionar la opción "Backup".

3. Crear un almacén de Recovery Services.

4. Seleccionar el grupo de recursos donde se colocará el almacén.

5. Seleccionar la política de respaldo, por omisión DailyPolicy, o dar clic en "Crear una nueva directiva" para crear una nueva política de respaldo.

Si se crea una nueva política se puede definir la frecuencia de respaldo (diario o semanal), la hora en la que se realizará el respaldo y el tiempo que se conservará los puntos de restauración.

6. Dar clic en el botón "Habilitar Backup"

7. Dar clic en la campana de notificaciones para verificar que se haya realizado la implementación del proceso de respaldo.

## Iniciar un respaldo completo

Anteriormente vimos cómo habilitar un proceso de respaldo (backup job) para realizar un respaldo diario de una máquina virtual completa a cierta hora del día.

Para iniciar al momento el respaldo completo de la máquina virtual:

1. Seleccionar la máquina virtual en el portal de Azure. Seleccionar "Backup" en el menú de operaciones.

2. Seleccionar "Realizar copia de seguridad ahora" para crear el primer respaldo completo de la máquina virtual. Los subsecuentes respaldos automáticos serán incrementales.

3. Indicar la fecha de retención de la copia de seguridad o aceptar la fecha establecida en la política de respaldo utilizada (por omisión, 30 días).

4. Dar clic en el botón "Aceptar"

5. Dar clic en la campana de notificaciones para verificar que se haya iniciado el respaldo de la máquina virtual.

6. Para ver el progreso del respaldo seleccionar la opción "Ver todos los trabajos" en la página "Backup" de la máquina virtual. Seleccionar la opción "Actualizar" para refrescar la pantalla que muestra el estado del proceso de respaldo. El respaldo ha terminado cuando se despliega "Completada".

El tiempo que tarda el respaldo depende del número de procesadores virtuales, el tamaño de la memoria RAM y el tamaño del disco en la máquina virtual. El respaldo tardará más si la máquina virtual tiene poca memoria y pocos procesadores virtuales o el disco es grande.

Una vez terminado el respaldo en la página "Backup" de la máquina virtual aparecerá el punto de restauración creado.

## Restaurar una máquina virtual

Para restaurar una máquina virtual completa:
1. Seleccionar la máquina virtual en el portal de Azure.

2. Seleccionar la opción "Backup" en el menú de "Operaciones".

3. Seleccionar la opción "Restaurar VM".

4. En "Punto de restauración" dar clic en la opción "Seleccionar".

5. Seleccionar el punto de restauración y dar clic en el botón "Aceptar".

6. En "Tipo de restauración" seleccionar "Crear una nueva máquina virtual".

7. Ingresar el nombre de la nueva máquina virtual.

8. Seleccionar la red virtual.

9. Seleccionar la ubicación del almacenamiento provisional. Esta cuenta de almacenamiento se utilizará temporalmente durante la restauración.

Si no se encuentra una cuenta de almacenamiento será necesario crearla de la siguiente manera (cerrar la ventana actual "Restauración de la máquina virtual"):

9.1 En la ventana de búsqueda de Azure escribir: cuentas de almacenamiento
9.2 Dar clic en la opción +Crear
9.3 Seleccionar el grupo de recursos de la máquina virtual.
9.4 Ingresar un nombre para la cuenta de almacenamiento (no debe existir en Azure).
9.5 Seleccionar la misma ubicación del vault (almacén de Recovery Services) en el procedimiento Habilitar el respaldo de una máquina virtual en Azure.
9.6 En "Replicación" seleccionar "Almacenamiento con redundancia local (LRS)"
9.7 Dar clic en el botón "Revisar y crear".
9.8 Dar clic en el botón "Crear".
9.9 Ir al primer paso del procedimiento Restaurar una máquina virtual.

10. Dar clic en el botón "Restaurar".

11. Dar clic en la campana de notificaciones para verificar que se haya iniciado la restauración de la máquina virtual.

12. Para ver el progreso de la restauración seleccionar la opción "Ver todos los trabajos" en la página "Backup" de la máquina virtual. Seleccionar la opción "Actualizar" para refrescar la pantalla que muestra el estado del proceso de restauración.

Una vez terminada la restauración de la máquina virtual, la nueva máquina virtual aparecerá en la lista de máquinas virtuales en el portal de Azure.

Para conectar con la nueva máquina virtual se utilizará las mismas credenciales (usuario y contraseña) definidas para la máquina virtual respaldada.

Podemos verificar que la configuración de la nueva máquina virtual es idéntica a la configuración de la máquina virtual respaldada.

## Eliminar un proceso de respaldo

Para eliminar un proceso de respaldo y los puntos de respaldo asociados:

1. Seleccionar la máquina virtual en el portal de Azure.

2. Seleccionar la opción "Backup" en el menú de "Operaciones".

3. Seleccionar "Detener copia de seguridad". Si no se ve la opción presionar los tres puntos ...

4. Seleccionar la opción "Retener datos de copia de seguridad" o bien "Eliminar datos de copia de seguridad".

5. Ingresar el nombre del elemento de copia de seguridad, es este caso el nombre de la máquina virtual respaldada.

6. Opcionalmente se puede indicar el motivo por el cual se va a eliminar el proceso de respaldo. También es posible escribir algún comentario en la ventana "Comentarios".

7. Dar clic en el botón "Detener copia de seguridad".

8. Dar clic en la campana de notificaciones para verificar que se haya detenido el proceso de copia de seguridad y en su caso, se haya eliminado los datos.

9. Para eliminar el almacén de Recovery Services (vault) hacer lo siguiente:

Nota importante: Para eliminar el almacén de Recovery Services es necesario que hayan pasado 14 días desde el último respaldo, ya que la retención de los datos es por dos semanas: "Recovery Services vault cannot be deleted as there are backup items in soft deleted state in the vault. The soft deleted items are permanently deleted after 14 days of delete operation". Fuente: Portal de Microsoft Azure.

9.1 Ir al inicio del portal de Azure.
9.2 Seleccionar "Todos los recursos.
9.3 Seleccionar el vault (almacén de Recovery Services) a eliminar (tener la precaución de seleccionar el almacén correcto).
9.4 Seleccionar también la cuenta de almacenamiento, si se creó la cuenta de almacenamiento en el paso 9 del procedimiento Restaurar una máquina virtual.
9.5 Seleccionar la opción "Eliminar".
9.6 Confirmar la eliminación del almacén dando clic al botón "Sí"
9.7 Dar clic en la campana de notificaciones para verificar que se haya eliminado el almacén.
