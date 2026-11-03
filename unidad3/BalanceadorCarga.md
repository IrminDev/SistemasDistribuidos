# Balance de carga en la nube

El balance de carga es la distribución equilibrada de carga entre un grupo de servidores (p.e. servidores web) o recursos en el back-end (p.e. unidades de almacenamiento).

La carga es el tráfico de red entrante a un recurso, por ejemplo, las peticiones a un servidor o las lecturas/escrituras a una unidad de almacenamiento.

## Azure Load Balancer

El balanceador de carga en Azure opera en la capa de transporte (nivel 4) del modelo OSI, por tanto, soporta los protocolos TCP y UDP.

El balanceador de carga utiliza un algoritmo de distribución haciendo el hash de cinco elementos: la IP de origen, el puerto de origen, la IP de destino, el puerto de destino y el tipo de protocolo

En Azure se puede crear dos tipos de balanceadores de carga:

### Balanceador de carga público

Un balanceador de carga público mapea la dirección IP pública (IP de front-end) y el puerto a una dirección IP privada y puerto de una máquina virtual (la IP pública y el puerto conforman un endpoint de Internet).

Utilizando reglas en el balanceador de carga, es posible distribuir la carga por tipo de tráfico (HTTP, HTTPS, FTP, SMTP, SSH, RDP, MySQL, SQLServer, etc).

### Balanceador de carga interno

Un balanceador de carga interno distribuye el tráfico entre los recursos que se encuentran dentro de una red virtual.

Este tipo de balanceador de carga se utiliza para balancear carga (interna) que viene del firewall o para balancear la carga de las máquinas virtuales que ejecutan procesos de negocio que no son visibles al usuario externo.

La IP de un balanceador de carga interno nunca se expone como endpoint de Internet.

En un escenario de nube híbrida, es posible conectar servidores on-premise a un balanceador de carga interno.

## Escalamiento mediante balance de carga

El balance de carga permite escalar las aplicaciones e implementar servicios con alta disponibilidad dentro de una zona de disponibilidad y a través de diferentes zonas de disponibilidad.

Agregando máquinas virtuales al balanceador de carga los sistemas incrementan su capacidad de atender peticiones, así mismo, se evita la interrupción del servicio cuándo uno de los servidores falla.

### Sondeo de estado

Los sondeos de estado permiten verificar el estado de cada máquina virtual (instancia de back-end) conectada al balanceador de carga. Si el sondeo de estado no recibe respuesta de la máquina virtual entonces no se enviarán peticiones a la máquina virtual hasta que el sondeo de estado se restablezca.

El balanceador de carga estándar soporta sondeo de estado mediante los protocolos TCP, HTTP y HTTPS.

Antes de agregar reglas de equilibrio de carga a un balanceador de carga es necesario crear al menos un sondeo de estado.
Ver: Sondeos de estado de Azure Load Balancer.

## Región

Una región es un área geográfica con un conjunto de datacenters interconectados mediante una red de baja latencia.

### Datacenter

Un datacenter es una instalación física con infraestructura de servidores, redes y almacenamiento.

Fuente: Microsoft

Cada datacenter cuenta con contenedores modulares (similares a los contenedores que utiliza el transporte marítimo).

Los contenedores se reciben completamente ensamblados y cuentan con tres entradas principales:

- Agua. Se utiliza para refrigerar los servidores.
- Energía. Suministra la energía eléctrica a los servidores.
- Red. Permite la conexión de los servidores con el exterior.

Cada contenedor puede tener cientos de servidores; los contenedores más grandes pueden tener entre 250 y 2,500 servidores.

Cada servidor tiene 2 o 4 CPU físicos, y cada CPU puede tener entre 24 y 64 núcleos (p.e. la serie Intel Xenon o AMD EPYC)

Cada servidor puede tener entre 64 GB y 1 TB de memoria RAM.

Cada servidor puede tener un almacenamiento local SSD entre 1 TB y 4 TB.

En los contenedores se usan redes de 10 Gbps a 100 Gbps para la conectividad interna y externa.

### Zona de disponibilidad

Una zona de disponibilidad (Availability Zone) se compone por uno o más datacenters de una misma región, equipados con su propia alimentación, refrigeración y red.

Si una zona de disponibilidad falla, otras zonas estarán disponibles dentro de la misma región.

Las zonas de disponibilidad permiten implementar alta disponibilidad en caso de fallas de energía, red o refrigeración a nivel datacenter.

### Conjunto de disponibilidad

Un conjunto de disponibilidad es una agrupación lógica de máquinas virtuales de una región, distribuidas en dominios de error y dominios de actualización.

Los conjuntos de disponibilidad reducen el riesgo de interrupciones en las máquinas virtuales por fallas de hardware o mantenimientos planificados.

El conjunto de disponibilidad se deberá asignar al momento de crear la máquina virtual, una vez creada la máquina virtual, no es posible asignar una máquina virtual a un conjunto de disponibilidad.

Un conjunto de disponibilidad consta de:

- 1, 2 o 3 dominios de error. Las máquinas virtuales que pertenecen a un mismo dominio de error comparten la misma fuente de alimentación, red física y refrigeración. En este caso, la alimentación, la red y la refrigeración son, cada uno, un único punto de fallo. En el siguiente ejemplo, se muestran cuatro máquinas virtuales distribuidas en dos dominios de error:

- De 1 a 20 dominios de actualización. Las máquinas virtuales dentro de un mismo dominio de actualización se reiniciarán simultáneamente durante el mantenimiento planeado. Solo se reiniciará un dominio de actualización a la vez. Si se creó un dominio de error, solo se puede crear un dominio de actualización. En el siguiente ejemplo, se muestran cuatro máquinas virtuales distribuidas en tres dominios de actualización; solo se apagará un dominio de actualización a la vez (en este caso, se está apagando el primer dominio de actualización) en caso de un mantenimiento planificado:

El conjunto de disponibilidad se deberá asignar al momento de crear la máquina virtual.

Azure no cobra por los conjuntos de disponibilidad; el pago es por cada máquina virtual dentro del conjunto de disponibilidad.

## Costo del balance de carga en Azure

En Azure se puede configurar reglas de equilibrio de carga y reglas NAT.

Las reglas NAT son utilizadas para mapear el tráfico entre las direcciones IP públicas y privadas.

Las reglas de equilibrio de carga se cobran por número de reglas por hora.

Además, se cobra la cantidad de datos procesados de entrada y de salida, independientemente de las reglas.

En el Standard Load Balancer no se cobra por hora si no hay reglas configuradas.

Las reglas NAT son gratuitas. Ver: Precios de Load Balancer

El siguiente modelo muestra los componentes de un balanceador de carga en Azure:
