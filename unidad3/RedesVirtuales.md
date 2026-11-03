# Clases de direcciones IP v4

Las direcciones IP versión 4 se dividen en cinco clases o rangos, a saber:

- Clase A
- Clase B
- Clase C
- Clase D
- Clase E

Cada clase se define por un rango de valores que puede tomar el primer byte de la dirección IP, así las clases A, B y C son utilizadas para la comunicación unicast, mientras que la clase D es utilizada exclusivamente para la comunicación multicast.

La clase E está reservada para propósitos experimentales.

La siguiente tabla muestra los bytes que identifican a las redes y a los hosts en cada clase (Rango del primer byte), así como la máscara de subred, número de redes y número de hosts por red en cada clase.

| Clase | Rango del primer byte | Identificador de red y host | Máscara de subred | Número de subredes | Número de hosts por subred |
|---|---|---|---|---:|---:|
| A | 1 - 126 | R.H.H.H | 255.0.0.0 | 126 | 16,777,214 |
| B | 128 - 191 | R.R.H.H | 255.255.0.0 | 16,382 | 65,534 |
| C | 192 - 223 | R.R.R.H | 255.255.255.0 | 2,097,150 | 254 |
| D | 224 – 239 | — | — | — | — |
| E | 240 – 255 | — | — | — | — |

Referencia: Explicación de las cantidades de hosts y subredes (Cisco)

Las direcciones 127.X.X.X (loopback address) son utilizadas para identificar a la computadora local (localhost).

La dirección 255.255.255.255 es usada para broadcast a todos los hosts en la LAN. Las direcciones 224.0.0.1 y 224.0.0.255 están reservadas.

Las direcciones de clase A son utilizadas por algunos gobiernos y por proveedores de nube (p.e. Microsoft Azure) ¿Qué dirección IP está asociada al dominio m4gm.com?

Las direcciones de clase B son utilizadas por empresas e instituciones públicas de tamaño medio (como sería el caso del IPN) las cuales podrían requerir más de 254 direcciones públicas.

Las direcciones de clase C son usadas por empresas e instituciones públicas que no requieren muchas direcciones IP públicas.

La clase D a su vez, se divide en los siguientes intervalos de direcciones IP de acuerdo a su uso:

| Dirección inicial | Dirección final | Uso |
|---|---|---|
| 224.0.0.0 | 224.0.0.255 | Direcciones reservadas para control de tráfico en una red local (p.e. enrutamiento) |
| 224.0.1.0 | 224.0.1.255 | Direcciones para multicast global, se enrutan a través de Internet |
| 224.0.2.0 | 224.0.255.255 | Direcciones reservadas para uso futuro |
| 224.1.0.0 | 238.255.255.255 | Direcciones disponibles para aplicaciones multicast |
| 239.0.0.0 | 239.255.255.255 | Direcciones para multicast local; no se enrutan en Internet |

Referencia: RFC5771: IANA Guidelines for IPv4 Multicast Address Assignments

## Classless Inter-Domain Routing (CIDR)

Como vimos anteriormente, las direcciones IP se agrupan en cinco clases, sin embargo, esta clasificación tiene un inconveniente, que muchas direcciones IP se desperdician debido a que solo se pueden asignar redes con tres tamaños fijos (clases A, B y C).

Así mismo, la clasificación de IPs por clase requiere que los ruteadores almacenen tablas de enrutamiento muy grandes, ya que se debe almacenar direcciones IP individuales y direcciones IP de subredes, lo que necesita gran cantidad de espacio de almacenamiento y por tanto el incremento en el costo de éstos dispositivos.

Como una alternativa a la clasificación de direcciones IP por clases, se desarrolló el concepto de Enrutamiento de dominios sin clases o CIDR (Classless Inter-Domain Routing).

La notación CIDR es una notación que agrega un número llamado longitud del prefijo a la dirección IPv4, el cual indica el número de unos que forman la máscara de subred a la que pertenece la IP.

Por ejemplo: 192.54.20.10/24 indica que la IP es 192.54.20.10 y la máscara de subred es 255.255.255.0, ya que 25510=111111112 es decir ocho unos binarios, por tanto, una longitud del prefijo igual a 24 indica que la máscara de subred contiene 24 unos (contados de izquierda a derecha).

Otro ejemplo es el siguiente: 192.54.20.10/20, en este caso la máscara de subred correspondiente es: 255.255.240.0 ya que 25510=111111112 y 24010 = 111100002, es decir, 8+8+4= 20 unos.

De esta manera, es posible saber si dos o más IPs en formato CIDR pertenecen a la misma subred, solo es necesario verificar que las IPs coinciden en los N primeros bits, donde N es la longitud del prefijo que sucede a la diagonal "/".

La notación CIDR permite implementar redes con una variedad en el número de hosts, a esta propiedad se le llama máscara de subred de longitud variable (VLSM: Variable Length Subnet Mask).

Por ejemplo, supongamos que tenemos dos computadoras con las siguientes IP:

192.30.50.20/22
192.30.48.10/22

¿Estas computadoras pertenecen a la misma subred?

Primero convertimos cada IP a binario. Entonces verificamos que los primeros 22 dígitos (de izquierda a derecha) coincidan.

192.030.050.020 = 11000000 00011110 00110010 00010100
192.030.048.010 = 11000000 00011110 00110000 00001010
255.255.252.000 = 11111111 11111111 11111100 00000000  (22 unos)

En efecto, los primeros 22 dígitos binarios coinciden en ambas IPs, por tanto, las dos computadoras pertenecen a la misma subred.

## Espacios de direcciones IP

La notación CIDR permite definir espacios de direcciones IP en una red.

Un espacio de direcciones es un intervalo de direcciones IP definido en notación CIDR.

Por ejemplo, 10.2.0.0/16 indica el intervalo de direcciones IP entre 10.2.0.0 y 10.2.255.255, un total de 65,536 direcciones IP.

El número total de direcciones IP que contiene un espacio de direcciones se puede calcular utilizando la longitud del prefijo CIDR, en el caso anterior el número de direcciones se calcula de la siguiente manera: 2(32-16)=216=65536.

Por otra parte la longitud del prefijo /32 define un espacio de direcciones IP con un número de direcciones IP igual a: 2(32-32)=20=1

## Direcciones IP utilizables y no utilizables

La siguiente tabla muestra el número total de IPs para cada longitud del prefijo, el número de IPs utilizables, las IPs no utilizables y la máscara de subred.

Las direcciones IPs no utilizables son la dirección IP de la subred (la primera dirección IP del intervalo de direcciones IP) y la dirección IP de broadcast (la última dirección IP del intervalo de direcciones IP); la dirección de broadcast se utiliza para enviar tráfico a todos los dispositivos dentro de la subred.
| Longitud del prefijo | Número total de IPs | IPs utilizables | IPs no utilizables | Máscara de subred |
|---|---|---|---|---|
| /32 | 1 | 1 | Ninguna | 255.255.255.255 |
| /31 | 2 | 0 | Todas (Solo para punto a punto) | 255.255.255.254 |
| /30 | 4 | 2 | Red, Broadcast | 255.255.255.252 |
| /29 | 8 | 6 | Red, Broadcast | 255.255.255.248 |
| /28 | 16 | 14 | Red, Broadcast | 255.255.255.240 |
| /27 | 32 | 30 | Red, Broadcast | 255.255.255.224 |
| /26 | 64 | 62 | Red, Broadcast | 255.255.255.192 |
| /25 | 128 | 126 | Red, Broadcast | 255.255.255.128 |
| /24 | 256 | 254 | Red, Broadcast | 255.255.255.0 |
| /23 | 512 | 510 | Red, Broadcast | 255.255.254.0 |
| /22 | 1024 | 1022 | Red, Broadcast | 255.255.252.0 |
| /21 | 2048 | 2046 | Red, Broadcast | 255.255.248.0 |
| /20 | 4096 | 4094 | Red, Broadcast | 255.255.240.0 |
| /19 | 8192 | 8190 | Red, Broadcast | 255.255.224.0 |
| /18 | 16384 | 16382 | Red, Broadcast | 255.255.192.0 |
| /17 | 32768 | 32766 | Red, Broadcast | 255.255.128.0 |
| /16 | 65536 | 65534 | Red, Broadcast | 255.255.0.0 |
| /15 | 131072 | 131070 | Red, Broadcast | 255.254.0.0 |
| /14 | 262144 | 262142 | Red, Broadcast | 255.252.0.0 |
| /13 | 524288 | 524286 | Red, Broadcast | 255.248.0.0 |
| /12 | 1048576 | 1048574 | Red, Broadcast | 255.240.0.0 |
| /11 | 2097152 | 2097150 | Red, Broadcast | 255.224.0.0 |
| /10 | 4194304 | 4194302 | Red, Broadcast | 255.192.0.0 |
| /9 | 8388608 | 8388606 | Red, Broadcast | 255.128.0.0 |
| /8 | 16777216 | 16777214 | Red, Broadcast | 255.0.0.0 |

Referencia: Classless Inter-domain Routing (CIDR): The Internet Address Assignment and Aggregation Plan

# Redes virtuales en Azure

Cuando se crea una máquina virtual en Azure se crea por default:

- Una red virtual (VNet).
- Una subred.
- Una interfaz de red (NIC, Network Interface Card) incluyendo una IP privada.
- Un grupo de seguridad de red (NSG, Network Security Group).
- Una IP pública.
- Un disco de sistema operativo.

## Grupo de recursos

Un grupo de recursos es similar a un directorio de sistema operativo.

Un grupo de recursos puede contener diferentes recursos de Azure.

Cuando se elimina un grupo de recursos también se eliminan los recursos que contiene el grupo de recursos.

## Red virtual (VNet)

Una red virtual es una red privada en Azure.

Las computadoras conectadas a una red virtual pueden comunicarse en forma segura utilizando sus direcciones IP privadas debido a que las direcciones IP privadas no son visibles desde Internet.

Una red virtual está compuesta por uno o más espacios de direcciones IPv4.

Un espacio de direcciones es un intervalo de direcciones IP definido en notación CIDR.

Los intervalos de direcciones IP en una red virtual no se superponen, es decir, los intervalos no tienen direcciones IP en común.

Por ejemplo, 10.2.0.0/16 indica el intervalo 10.2.0.0 a 10.2.255.255, un total de 65,536 direcciones.

El tamaño máximo del espacio de direcciones de una red virtual es /8 (en notación CIDR, 224=16,777,216 direcciones IP).

Ver: Agregar o quitar un rango de direcciones.

## Subred

Una red virtual puede tener una o varias subredes.

Cada subred se define como un intervalo de direcciones IP dentro de la red virtual.

Cuando se crea una red virtual, se crea por default una subred llamada “default”. Esta subred puede modificarse o eliminarse.

El tamaño mínimo de una subred es /29 (23=8 direcciones IP) y el máximo /8 (224=16,777,216 direcciones IP).

Dos computadoras conectadas a diferentes subredes dentro de una misma red virtual se pueden comunicar.

## Grupo de seguridad de red

Un grupo de seguridad de red (NSG, Network Security Group) es un conjunto de reglas de entrada y reglas de salida para los puertos.

Las reglas de puerto de entrada controlan las conexiones TCP y paquetes UDP que vienen del exterior de la máquina virtual, mientras que las reglas de puerto de salida controlan las conexiones TCP y paquetes UDO que salen de la máquina virtual.

Notar que una vez que se establece la conexión TCP, la comunicación es bidireccional, es decir, la computadora que se conecta (cliente) puede enviar y recibir datos de la computadora a la que se conecta (servidor),

Cada regla de entrada permite/deniega conexiones TCP o paquetes UDP desde:

- Una dirección IP de origen específica.
- Un intervalo de direcciones IP de origen.
- Cualquier dirección IP de origen.
- Un intervalo de puertos de entrada.
- Un puerto de entrada específico.
- Cualquier puerto de entrada.

Cada regla de salida permite/deniega conexiones TCP o paquetes UDP hacia:

- Una dirección IP de destino específica.
- Un intervalo de direcciones IP de destino.
- Cualquier dirección IP de destino.
- Un intervalo de puertos de destino,.
- Un puerto de destino específico.
- Cualquier puerto de destino.

También se puede configurar si se permite o deniega un protocolo específico (TCP, UDP, ICMP o cualquiera de los anteriores) o si se permite o deniega cualquier protocolo.

A cada regla del grupo de seguridad de red se le asigna una prioridad, de tal manera que las reglas se aplican de menor a mayor prioridad, es decir, las reglas con menor prioridad tienen mayor precedencia.

Por ejemplo, si una regla con menor prioridad permite el tráfico y otra regla con mayor prioridad deniega el mismo tráfico, entonces se aplicará la primera regla y se ignorará la segunda.

Un grupo de seguridad de red puede asociarse a nivel subred y a nivel interfaz de red.

Un mismo grupo de seguridad de red puede asociarse a varias subredes y varias interfaces de red.

Por default todo el tráfico que viene de un balanceador de carga o de la red virtual es permitido, y todo el tráfico que viene de Internet es denegado, por tanto para cambiar esto es necesario crear las reglas necesarias para controlar el tráfico de entrada y de salida.

En la siguiente figura podemos ver cómo dos computadoras en la misma red virtual pueden comunicarse sin restricciones.

Por otra parte, para conectar las computadoras a Internet es conveniente configurar las reglas de entrada y de salida.

Debido a que las direcciones IP en una red virtual son privadas, para comunicar dos computadoras conectadas a diferentes redes virtuales se deberá utilizar las direcciones IP públicas de las computadoras:
