En clases anteriores vimos servicios de la nube al nivel de infraestructura (IaaS: Infrastructure as a Service), como son la creación de máquinas virtuales con Windows y Linux, la creación de imágenes de máquinas virtuales, NSG, NIC, creación de redes y subredes virtuales, VPN Gateways y balance de carga.

La clase de hoy veremos el servicio administrado de base de datos MySQL, el cual ofrece Azure al nivel de plataforma (PaaS: Platform as a Service).

## Azure Database for MySQL

Cuando una empresa instala sus sistemas en la nube requiere así mismo tener acceso a un DBMS.

Uno de los más populares manejadores de bases de datos en la actualidad es MySQL (usado por empresas de clase mundial como Google, YouTube, Facebook, Twitter, PayPal, etc.), el cual puede utilizarse en su versión gratuita (Community Server) o en su versión Enterprise Edition.

Como hemos visto en clases previas, es fácil instalar MySQL sobre una máquina virtual, crear una base de datos y acceder a ella utilizando el monitor de mysql o un programa escrito en algún lenguaje como Java, C#, Python, Node.js, entre otros.

Sin embargo, en un entorno empresarial será necesario llevar a cabo la administración de MySQL, lo cual incluye la instalación, monitoreo, respaldo de las bases de datos, recuperación ante desastres, espejo de discos, etc.

Entonces la empresa tiene dos opciones, la primera es contratar el personal que se encargue de la administración del DBMS, y la segunda contratar el servicio completo en la nube. Este servicio de DBMS completamente administrado es un ejemplo de plataforma como servicio (PaaS).

Azure ofrece MySQL Community Server completamente administrado como servicio.

Este servicio de nube, llamado Azure Database for MySQL, permite a la empresa delegar la administración de la base de datos y contar con alta disponibilidad (99.99%) y escalabilidad dinámica, así como el acceso remoto a MySQL mediante conexión segura.

## Referencias

- ¿Qué es PaaS?
- Azure Database for MySQL

- ¿Qué es Azure Database for MySQL (servidor flexible)?

## Crear un servidor en Azure Database for MySQL

Ahora vamos a crear una instancia de MySQL al nivel de PaaS en la nube de Azure.

Para crear una instancia de MySQL:

1. Ingresar al portal de Azure.

2. En la barra de búsqueda escribir: Servidores de Azure Database for MySQL

3. Dar clic en "+Crear"

4. Dar clic en el botón "Creación avanzada" en el cuadro "Servidor flexible".

5. Seleccionar un grupo de recursos existente o crear uno nuevo.

6. Ingresar el nombre del servidor, este nombre debe ser único en Azure ya que se le agregará un subdominio de Azure.

7. Seleccionar la región.

Nota. No todas las regiones soportan la creación de instancias de MySQL, si se produce un error al crear la instancia, cambiar la región.

8. Seleccionar la versión de MySQL, por ejemplo 8.0

9. En el campo "Tipo de carga de trabajo" seleccionar "Para proyectos de desarrollo o aficiones"

10. Seleccionar la opción "Configurar servidor" en la sección "Proceso y almacenamiento".

11. En el campo "Computer tier" seleccionar "Flexible".

12. En el campo "Tamaño de proceso" seleccionar "Standard_B1ms".

13. En el campo "Storage size (in GiB)" seleccionar: 20

14. En el campo "IOPS" seleccionar "IOPS aprovisionados previamente". Mover el control deslizante a los IOPS (I/O per second) requerimos.

15. Habilitar "Crecimiento automático del almacenamiento".

16. Dar clic en el botón "Guardar".

17. En el campo "Método de autenticación" seleccionar "Autenticación de MySQL". Ingresar el "Nombre de usuario de administrador" (notar que no se permite ingresar "root" ya que este usuario está reservado para la administración del DBMS por parte de Azure).

18. Ingresar la contraseña. Confirmar la contraseña ingresada.

19. Dar clic en el botón "Siguiente: Redes >".

20. En el campo "Método de conectividad" seleccionar "Acceso público".

Nota. Si se selecciona "Acceso privado" se deberá indicar la red y subred a la que se conectará la máquina virtual que ejecutar MySQL.

20.1 Habilitar el campo "Permitir el acceso público a este recurso a través de Internet mediante una dirección IP pública"

21. Ahora vamos a configurar el firewall del servidor:

21.1 Para que la computadora actual pueda conectarse al servidor (p.e. para ejecutar el monitor de mysql), seleccionar la opción "+ Agregar dirección IP del cliente actual".

21.2 Para que otra computadora se pueda conectar al servidor, ingresar en los campos "IP inicial" y en "IP final" la IP de la computadora.

21.3 Para que cualquier computadora se pueda conectar al servidor, ingresar 0.0.0.0 como IP inicial y 255.255.255.255 como IP final, pero esto no es recomendable por razones de seguridad.

21.4 Para que todos los recursos de Azure (máquinas virtuales, Azure Functions, etc.) tengan acceso al servidor (incluso los recursos que no están en la misma suscripción), seleccionar "Si" en la opción "Permitir el acceso público a este servidor desde cualquier servicio de Azure dentro de Azure"

22. Dar clic en el botón "Revisar y crear".

23. Dar clic en el botón "Crear".

24. En el panel seleccionar la opción "Información general" en el menú de la izquierda de la pantalla.

Podemos ver el nombre del dominio del servidor (en este caso el servidor se llama prueba-mysql):

prueba-mysql.mysql.database.azure.com

25. Dar clic al botón "Revisar" en la sección "Ver cadena de conexión".

25.1 Abrir la pestaña "Conectarse desde el explorador o localmente"

```bash
Ahora podemos conectarnos al servidor de MySQL ejecutando el siguiente comando:

mysql -h prueba-mysql.mysql.database.azure.com -P 3306 -u administrador -p
```

En este caso el usuario configurado en el paso 17 es "administrador".

## Crear una base de datos

```sql
create database servicio_web;

use servicio_web;

create table usuarios
(
id_usuario integer auto_increment primary key,
email varchar(100) not null,
nombre varchar(100) not null,
apellido_paterno varchar(100) not null,
apellido_materno varchar(100),
fecha_nacimiento datetime not null,
telefono varchar(20),
genero char(1)
);

create table fotos_usuarios
(
id_foto integer auto_increment primary key,
foto longblob,
id_usuario integer not null
);

alter table fotos_usuarios add foreign key (id_usuario) references usuarios(id_usuario);
create unique index usuarios_1 on usuarios(email);
```

## Crear un usuario remoto

Para crear un usuario local en MySQL, se agrega al nombre del usuario "@localhost".

Para crear un usuario que pueda acceder en forma remota MySQL, en este caso el usuario se llama "hugo":

create user hugo identified by 'contraseña-del-usuario-hugo';

grant all on servicio_web.* to hugo;

## Detener o eliminar la instancia de MySQL

Para evitar mayores gastos se puede detener o eliminar la instancia de MySQL.
