Interfaz de red

Una interfaz de red (NIC, Network Interface Card) permite a una máquina virtual conectarse a la red virtual.

Una máquina virtual puede tener una o más interfaces de red.

Cada interfaz de red tiene al menos una configuración IP.

Una configuración IP contiene una IP privada y opcionalmente una IP pública.

A una configuración IP se le puede asociar una IP pública, la cual podrá ser utilizada por la máquina virtual para conectarse a Internet.

Existen dos tipos de configuraciones IP: la "Principal" y las "Secundarias". Solo la IP pública de la configuración "Principal" es visible a Internet. La configuración "Principal" no se puede eliminar.

Una interfaz de red se puede crear y posteriormente se puede agregar a una máquina virtual.
Una IP pública también se puede crear y posteriormente se puede asociar a una configuración IP.

Puerta de enlace de red virtual (VPN Gateway)

Una puerta de enlace de red virtual (Virtual Network Gateway o VPN Gateway) es un Gateway implementado en software el cual permite conectar, mediante un canal encriptado, redes virtuales (VNet) entre sí o bien redes virtuales con redes on-premise.

Para conectar dos redes virtuales se requiere crear dos puertas de enlace de red virtual (Virtual Network Gateway), una para cada red virtual a interconectar.

A la conexión de dos redes virtuales se le conoce como VPN VNet-to-VNet.

Fuente: Configure a VNet-to-VNet VPN gateway connection by using the Azure portal

Por otra parte, para conectar una red on-premise con una red virtual (VNet) se requiere crear una puerta de enlace virtual para la red virtual y configurar un router en la red on-premise.

A la conexión de una red on-premise a una red virtual se le conoce como VPN Site-to-Site o nube híbrida.

Subred de puerta de enlace

Antes de crear un VPN Gateway es necesario crear una subred de Gateway (Gateway subnet), esta subred contiene las direcciones IP que utilizarán las máquinas virtuales y servicios que implementarán el VPN Gateway.

La subred de gateway debe llamarse "GatewaySubnet", este nombre le indica a Azure que se trata de la subred donde desplegará el VPN Gateway.

Ver: About VPN Gateway configuration settings

Emparejamiento de redes virtuales

El emparejamiento (peering) de redes virtuales permite conectar redes virtuales utilizando la red interna de Microsoft, de manera que el tráfico de emparejamiento no pasa por Internet.

No se cobra por crear el emparejamiento, pero sí se cobra el tráfico entrante y el tráfico saliente.

En cuanto a los costos, el tráfico en la misma región (intra-región) es más barato que el tráfico entre diferentes regiones.

El emparejamiento es más barato, más rápido y con mayor ancho de banda que VPN vnet-to-vnet.

Al igual que una VPN vnet-to-vnet, los espacios de direcciones IP de las redes virtuales a emparejar no deben superponerse.

1. Crear una red virtual en una región, llamarla: vnet-1
2. Crear una red virtual en otra región, llamarla: vnet-2
3. Seleccionar la red virtual vnet-1.
4. Seleccionar la opción "Emparejamientos" en la sección "Configuración".
5. Dar clic en la opción "+Agregar".
6. En la sección "Resumen de la red virtual remota":
6.1 Ingresar el nombre del vínculo de emparejamiento remoto, llamarlo: emparejamiento-vnet-2
6.2 En el campo "Tipo de emparejamiento" seleccionar "Red virtual".
6.3 En el campo "Red virtual" seleccionar la red virtual vnet-2.
6.4 Habilitar "Permitir 'vnet-2' para acceder a 'vnet-1'".
7. En la sección "Resumen de la red virtual local":
7.1 Ingresar el nombre del vínculo de emparejamiento local, llamarlo: emparejamiento-vnet-1
7.2 Habilitar "Permitir 'vnet-1' para acceder a 'vnet-2'".
8. Dar clic en el botón "Agregar".

Modelo de red de Azure

El siguiente diagrama de clases muestra la relación entre los diferentes componentes de una red virtual en Azure:

Creación de una red virtual en Azure

1. Ingresar al portal de Azure.

2. Ingresar "Redes virtuales" en el campo "Buscar recursos, servicios y documentos".

3. Dar clic en el botón "+Crear".

4. Seleccionar el grupo de recursos o crear uno nuevo.

5. Ingresar el nombre de la red virtual en el campo "Nombre de red virtual".

6. Seleccionar la región donde se creará la red virtual (por ejemplo: East US)

7. Seleccionar la opción "Direcciones IP".

Por default aparece un intervalo de direcciones con una subred "default".

8. Para agregar más intervalos de direcciones escribir cada intervalo en notación CIDR en el campo que aparece abajo del último intervalo definido.

9. Para agregar una subred dar clic en "+Agregar una subred":

9.1 Ingresar el nombre de la subred.

9.2 Ingresar el intervalo de direcciones de la subred en formato CIDR, este intervalo debe estar dentro del espacio de direcciones.

10. Dar clic en el botón "Revisar y crear".

11. Dar clic en el botón Crear.

12. Dar clic en el botón "Ir al recurso" para ver la red virtual creada.

Creación de un grupo de seguridad de red

1. En la página de inicio de Azure buscar "Grupos de seguridad de red".

2. Seleccionar "+Crear".

3. En el campo "Grupo de recursos" seleccionar el grupo de recursos donde se creará el grupo de seguridad de red o "Crear nuevo".

4. Ingresar el nombre del grupo de seguridad.

5. Seleccionar la región. La región del NSG debe ser la misma que las subredes o interfaces de red (NIC) que se asociarán al NSG.

6. Dar clic en el botón "Revisar y crear".

7. Dar clic en el botón "Crear".

8. Dar clic en el botón "Ir al recurso".

9. Para agregar reglas de seguridad de entrada o de salida seleccionar las opciones "Reglas de seguridad de entrada" o "Reglas de seguridad de salida" en el menú "Configuración" en la parte izquierda de la pantalla.

## Agregar una subred a una red virtual

1. En la página de inicio buscar "Redes virtuales"

2. Seleccionar la red virtual donde se creará la subred.

3. Seleccionar "Subredes" en el menú "Configuración" que aparece a la izquierda de la pantalla.

4. Seleccionar la opción "+Subred".

5. Ingresar el nombre de la subred.

6. Ingresar el intervalo de direcciones IP de subred en notación CIDR.

7. Si se quiere asociar un grupo de seguridad de red (NSG) a la subred, seleccionarlo.

8. Dar clic en el botón "Guardar".

Entonces se puede ejecutar ifconfig en la máquina virtual para ver las interfaces y las direcciones IP privadas correspondientes.

## Creación de una máquina virtual y su conexión a una red virtual existente

Cuando se crea una máquina virtual se puede conectar a una subred de una red virtual existente.

1. Seleccionar el grupo de recursos o crear uno nuevo.

2. Ingresar el nombre de la máquina virtual.

3. Seleccionar la misma región donde se creó la red virtual.

4. Seleccionar la imagen, por ejemplo Ubuntu Server 20.04 LTS - x64 gen.2

5. Seleccionar el tamaño, por ejemplo: Standard_B1s

6. En el campo Tipo de autenticación seleccionar "Contraseña".

7. Ingresar el nombre del usuario y la contraseña.

8. Seleccionar la opción "Discos"

9. Seleccionar el tipo de disco de sistema operativo:, por ejemplo: HDD estándar

10. Seleccionar la opción "Redes".

11. Seleccionar la red virtual a la que se conectará la máquina virtual. Notar que sólo se puede seleccionar las redes virtuales que se encuentran en la misma región.

12. Seleccionar la subred a la que se conectará la máquina virtual.

13. Seleccionar una IP pública existente o "Ninguno" si no se requiere la IP pública. También se puede crear una nueva dirección IP pública seleccionando la opción "Crear".

14. Seleccionar la opción "Administración".

15.  En el campo "Diagnóstico de arranque" seleccionar "Deshabilitar".

16. Dar clic en el botón "Revisar y crear".

17. Dar clic en el botón "Crear".

18. Dar clic en el botón "Ir al recurso".

## Creación de una interfaz de red

1. En la página de inicio de Azure buscar "Interfaces de red".

2. Seleccionar "+Crear".

3. Ingresar el grupo de recursos.

4. Ingresar el nombre de la interfaz de red.

5. Seleccionar la región donde se creará la interface de red. La región deberá ser la misma donde se creó la red virtual a la que se conectará la interface.

6. Seleccionar la red virtual a la que se conectará la interface de red.

7. Seleccionar la subred a la que se conectará la interface de red.

8. En el campo "Asignación de la dirección IP privada" se puede seleccionar una IP dinámica o estática, si se selecciona IP estática se deberá ingresar una dirección IP estática.

9. Seleccionar el grupo de seguridad de red.

10. Dar clic en el botón "Revisar y crear".

11. Dar clic en el botón "Crear".

12. Dar clic en el botón "Ir al recurso".

13. Si se quiere asociar un NSG a la interface seleccionar la opción "Grupo de seguridad de red", seleccionar el NSG y dar clic en la opción "Guardar".

## Añadir una interface de red a una máquina virtual

Para añadir (asociar) una interface de red a una máquina virtual:

1. En la página de inicio de Azure buscar "Máquinas virtuales".

2. Seleccionar la máquina virtual.

3. Detener la máquina virtual.

4. Seleccionar la opción "Redes" en el menú "Configuración" que aparece a la izquierda de la pantalla.

5. Seleccionar la opción "Adjuntar interfaz de red".

6. Seleccionar una interfaz de red previamente creada.

7. Dar clic en el botón "Aceptar".

8. En la pantalla "Redes" se puede seleccionar la interface de red conectada.

## Quitar una interface de red de una máquina virtual

Para quitar (desasociar) una interface de red de una máquina virtual:

1. En la página de inicio de Azure buscar "Máquinas virtuales".

2. Seleccionar la máquina virtual.

3. Detener la máquina virtual.

4. Seleccionar la opción "Redes" en el menú "Configuración" que aparece a la izquierda de la pantalla.

5. Seleccionar la opción "Desasociar interfaz de red".

6. Seleccionar la interface de red a desconectar (desasociar).

## Agregar una configuración IP a una interface de red

1. Buscar "Interfaces de red" en la barra e búsqueda en la pantalla de inicio de Azure.

2. Seleccionar la interfaz de red.

3. Seleccionar "Configuraciones de IP".

4. Dar clic en +"Agregar"

5. Ingresar el nombre de la configuración IP.

6. Seleccionar el tipo de asignación de la IP privada: dinámica o estática.

7. Seleccionar "Asociar" si se quiere asociar una IP pública a la configuración. Notar que solo la IP pública de la configuración "Principal" es visible a Internet.

Dando clic a los tres puntos que se encuentran a la derecha de una configuración se puede eliminar la configuración. La configuración "Principal" no se puede eliminar.

## Agregar una IP pública a una máquina virtual

Para agregar una IP pública a una máquina virtual, es necesario asignar (asociar) la IP pública a la configuración IP principal de la interface de red de la máquina virtual.

1. En la página de inicio de Azure buscar "Máquinas virtuales".

2. Seleccionar la máquina virtual.

3. Seleccionar la opción "Redes" en el menú "Configuración" que aparece a la izquierda de la pantalla.

4. Seleccionar la interfaz de red.

5. Seleccionar "Configuraciones de IP".

6. Seleccionar la configuración IP a la que se le asignará la IP pública.

7. Checar el campo "Asociar dirección IP pública"

8. En el campo "Dirección IP pública" seleccionar la IP pública o crear una nueva.

9. Dar clic al botón "Guardar".

10. Cerrar la ventana.

## Quitar una IP pública a una máquina virtual

Para quitar la IP pública a una máquina virtual, es necesario desasociar la IP pública de la configuración IP principal de la interface de red de la máquina virtual.

1. En la página de inicio de Azure buscar "Máquinas virtuales".

2. Seleccionar la máquina virtual.

3. Seleccionar la opción "Redes" en el menú "Configuración" que aparece a la izquierda de la pantalla.

4. Seleccionar la interfaz de red.

5. Seleccionar "Configuraciones de IP".

6. Seleccionar la configuración IP a la que se le asignará la IP pública.

7. En el campo "Asociar dirección IP pública" quitar el checado.

8. Dar clic al botón "Guardar".

9. Cerrar la ventana.

## Creación de una Puerta de enlace de red virtual (VPN Gateway)

1. Acceder al portal de Azure.

2. Seleccionar "Crear un recurso".

3. Seleccionar "Puerta de enlace de red virtual".

4. Dar clic en el botón "Crear".

5. Ingresar el nombre del gateway. Notar que el grupo de recursos es el mismo que el grupo de recursos donde está la red virtual.

6. Seleccionar la región.

7. En el campo "Tipo de puerta de enlace" seleccionar "VPN".

8. Seleccionar el SKU "VpnGw2AZ".

9. EN el campo "Generación" seleccionar "Generation2".

10. Seleccionar la red virtual a la que se conectará el gateway (notar que solo aparecen en la lista las redes virtuales creadas en la misma región).

11. En el campo "Intervalo de direcciones de subred de puerta de enlace" (o el campo "subred") se muestra automáticamente un intervalo correspondiente a la "GatewaySubnet"

12. Seleccionar "Crear" una dirección IP o "Usar existente". Si se va a crear una IP pública ingresar el nombre. Notar que el gateway requiere una dirección IP pública para recibir la conexión de otro gateway.

13. En el campo "Habilitar el modo activo/activo" seleccionar "Deshabilitado".

14. Dar clic en el botón "Revisar y crear".

15. Dar clic en el botón "Crear".

Nota. "Setting up a virtual network is free of charge. However, we do charge for the VPN gateway that connects to on-premises and other virtual networks in Azure. This charge is based on the amount of time that gateway is provisioned and available".

Fuente: https://azure.microsoft.com/en-gb/pricing/details/vpn-gateway

## Creación de una VPN VNet-to-VNet

1. Crear dos redes virtuales en dos regiones diferentes (p.e. East US y West US):

- Se deberá crear en cada red virtual una subred llamada "GatewaySubnet". Esta subred será utilizada por las máquinas virtuales que aprovisiona Azure para ejecutar el Gateway. Para crear la subred "GatewaySubnet" al momento de crear la red virtual:

- Seleccionar la opción "Agregar una subred" en la ventana "Direcciones IP".
- En el campo "Propósito de la subred" seleccionar "Virtual Network Gateway".
- En el campo "Dirección inicial" ingresar la dirección IP inicial del intervalo de direcciones IP que define la subred.
- En el campo "Tamaño" seleccionar el tamaño del intervalo de direcciones IP de la subred.
- Dar clic en el botón "Agregar".

- Cada red virtual en un grupo de recursos diferente.
- Las dos redes virtuales no deberán tener direcciones IP en común (e.d. cada red virtual deberá tener sus espacios de direcciones diferentes).

2. Crear dos puertas de enlace de red virtual (Virtual Network Gateway), una para cada red virtual.

3. En el cuadro de búsqueda del portal de Azure buscar "Redes virtuales".

4. Seleccionar la primera red virtual a conectar.

5. En el menú "Configuración" que aparece en la parte izquierda de la pantalla seleccionar "Dispositivos conectados" (en la lista aparecen los dispositivos conectados a la red virtual, p.e. interfaces de red virtual, Gateways, etc.).

6. Seleccionar el Gateway conectado a la red virtual.

7. Ahora vamos a crear una conexión de la primera red a la segunda red y una conexión de la segunda red a la primera red. En el menú "Configuración" que aparece en la parte izquierda de la pantalla seleccionar "Conexiones".

8. Seleccionar "+Agregar" para agregar una nueva conexión.

9. Seleccionar el tipo de conexión: "De VNet a VNet".

10. Checar el campo "Establecer conectividad bidireccional" (debido a que las conexiones son unidireccionales, al indicar conectividad bidireccional se van a crear dos conexiones de manera que el tráfico pueda ir del primer Gateway al segundo Gateway y viceversa.

11. Ingresar el nombre de la primera conexión.

12. Ingresar el nombre de la segunda conexión.

13. Seleccionar la región donde se encuentra la primera red virtual a conectar (seleccionada en el paso 3).

14. Seleccionar la pestaña "Opciones".

15. Seleccionar el primer Gateway (la puerta de enlace virtual seleccionada en el paso 5).

16. Seleccionar el segundo Gateway (la puerta de enlace virtual que estará en el otro extremo de la VPN).

17. Ingresar una clave compartida para el encriptado de la conexión (letras y números).

18. Dar clic en el botón "Revisar y crear".

19. Dar clic en el botón "Crear".

20. Para ver el estado de las conexiones, seleccionar ingresar "Todos los recursos" en el cuadro de búsqueda de recursos. Seleccionar la primera conexión (el nombre que se ingresó en el paso 11). El estado deberá ser "Conectado". De igual manera se puede verificar el estado de la segunda conexión.

En general una VPN no se encuentra en estado "conectado" mientras no inicie la transmisión de tráfico.

21. Ahora se puede crear una maquina virtual en la primera VNet y otra máquina virtual en la segunda VNet. Entonces se puede hacer "ping" de la primera máquina virtual a la segunda y viceversa utilizando las direcciones IP privadas de las máquinas virtuales.

Las máquinas virtuales no responden al "ping" cuando se realiza a través de IP públicas.

Nota. Para eliminar los Gateways es necesario eliminar primero las conexiones.
