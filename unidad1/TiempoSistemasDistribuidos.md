# El tiempo en los sistemas distribuidos
 

El tiempo es una referencia que utilizan los sistemas distribuidos en varias situaciones.

Supongamos una plataforma de comercio electrónico que funciona a nivel global. En cada país se tiene un servidor con una base de datos donde se registran las compras, incluyendo la fecha y hora en la que se realiza cada compra.

Para consolidar las compras a nivel mundial cada servidor debe enviar los datos a un servidor central.

Sin embargo, no es posible ordenar las compras por fecha debido a dos situaciones:

- Cada compra se ha registrado con la fecha y hora local.
- No es posible garantizar que los relojes de los servidores funcionen a la misma velocidad.

 
Para ilustrar este problema supongamos que un cliente en México realiza una compra a las 8 PM, y un cliente en España realiza una compra a las 2 AM del día siguiente. ¿Quién compró primero?
 
Aparentemente el cliente en México realizó la compra antes que el cliente en España, debido a que la fecha de la compra del cliente en México es un día anterior a la fecha de la compra del cliente en España.
 
Sin embargo, en realidad el cliente en España realizó la compra una hora antes que el cliente en México, debido a que la diferencia horaria entre México y España es de 7 horas.
 
> *Mapa de los husos horarios oficiales vigentes (dominio público).* 
 
La solución a este problema es registrar en las bases de datos la fecha y hora global en un solo campo de tipo Datetime o Timestamp, en lugar de la fecha y hora local. Además, los servidores deberán sincronizar sus relojes internos a una misma hora (mediante NTP).
 
Como se dijo anteriormente, el tiempo es una referencia para establecer un orden en una secuencia de eventos (como serían las compras en una plataforma de comercio electrónico).
 
Por otra parte, si los servidores no requieren consolidar las compras, tampoco será necesario que exista un acuerdo en los tiempos que marcan sus relojes.
 
El ejemplo anterior ilustra una regla muy importante de los sistemas distribuidos, la cual podemos enunciar de la siguiente manera: si dos computadoras no están conectadas, entonces no requieren sincronizar sus tiempos.
 
 
## Sincronización de relojes
 
Sincronizar dos o más relojes significa que los servidores se ponen de acuerdo en una misma hora.
 
Notar que un grupo de servidores pueden ponerse de acuerdo en una hora y otro grupo de servidores puede ponerse de acuerdo en otra hora; solo si ambos grupos de servidores se conectan, entonces ambos grupos de servidores deberán acordar una hora.
 
Actualmente la mayoría de las computadoras y dispositivos móviles se encuentran conectados a Internet, por tanto, la mayoría de los relojes están sincronizados mediante un servidor de tiempo.
 
 
## Segundos solares
 
El concepto de tiempo que utilizamos en la práctica se basa en la percepción que tenemos del día.
 
Un día es un período de luz y obscuridad debido a la rotación de la tierra sobre su eje.
 
Dividimos convencionalmente el día en 24 horas, cada hora en 60 minutos y cada minuto en 60 segundos.
 
Por tanto, la tierra tarda 86,400 segundos en dar una vuelta sobre su eje, en términos de velocidad angular estamos hablando de 360/86400=0.00416 grados/segundo.
 
A la fracción 1/86400 de día le llamamos segundo solar.
 
Sin embargo la velocidad angular de la tierra no es constante, debido a que la rotación de la tierra se está deteniendo lentamente.
 
## Segundos atómicos
 
Una forma más precisa de medir el tiempo es utilizar un reloj atómico de Cesio 133.
 
En un reloj atómico se aplica microondas con diferentes frecuencias a átomos de Cesio 133, entonces los electrones del átomo de Cesio 133 absorben energía y cambian de estado; posteriormente los átomos regresan a su estado basal emitiendo fotones.
 
A la frecuencia que produce más cambios de estado en los electrones del átomo de Cesio 133 se le llama frecuencia natural de resonancia.
 
La frecuencia natural de resonancia del Cesio 133 es de 9,192,631,770 ciclos/segundo, es decir, el átomo de Cesio 133 muestra un máximo de absorción de energía cuando se le aplica microondas con una frecuencia de 9,192,631,770 Hertzios.
 
Entonces se define el segundo atómico como el recíproco de la frecuencia natural de resonancia del Cesio 133 (recordar que el periodo de una onda es el recíproco de su frecuencia).
 
Los relojes atómicos de Cesio 133 son extremadamente precisos, ya que independientemente de las condiciones ambientales (temperatura, presión, etc.), se adelantan o atrasan un segundo cada 300 millones de años.
 
Los relojes atómicos son tan precisos que se han utilizado para probar los postulados de la teoría general de la relatividad, la cual predice la dilatación del tiempo debidos a la distorsión que causa la gravedad al espacio-tiempo.
 
Utilizando relojes atómicos de Cesio 133 se ha demostrado que el tiempo no transcurre a la misma velocidad a diferentes altitudes, ya que al nivel del mar, donde la gravedad es mayor, el tiempo se dilata (transcurre más lentamente) con respecto al tiempo medido en una montaña elevada donde la gravedad es menor.
 
Por ejemplo, un reloj atómico en un satélite GPS (a unos 20,000 Km de altura), se adelanta 7 microsegundos al día con respecto a un reloj atómico en la superficie de la tierra.
 
A este fenómeno se le conoce como dilatación gravitacional del tiempo.
 
 
## Tiempo atómico internacional (TAI)
 
Se define el tiempo atómico internacional (TAI) como el promedio de los segundos atómicos transcurridos desde el 1 de enero de 1958, dicho promedio obtenido de casi 70 relojes de Cesio 133 al rededor del mundo.

## Tiempo universal coordinado (UTC)
 
El tiempo universal coordinado UTC (Coordinated Universal Time) es el estándar de tiempo que regula actualmente el tiempo de los relojes a nivel internacional.
 
El tiempo UTC ha reemplazado el tiempo medio de Greenwich GMT.
 
El tiempo GMT toma como referencia la posición del sol a medio día. Tanto el tiempo GMT como el tiempo UTC consideran el día solar compuesto por 86,400 segundos solares.
 
Debido a que nuestro planeta disminuye su velocidad angular lentamente, el segundo solar dura más que el segundo atómico.
 
Para sincronizar los segundos UTC con los segundos TAI, el tiempo UTC se debe “adelantar” para alcanzar el tiempo TAI, para esto “se salta” un segundo UTC una vez al año; se dice entonces que se introducen segundos vacíos en el tiempo UTC.
 
Por ejemplo, en el siguiente diagrama se muestra cómo los segundos solares son más largos que los segundos atómicos, en esta caso para sincronizar los segundos solares (UTC) con los segundos atómicos (TAI), se salta del segundo 6 al 8, es decir, el segundo "vacío" es el segundo 7:
 
 
La autoridad responsable de introducir los segundos vacíos es el International Earth Rotation and Reference Systems Service (IERS).
 
Los proveedores de nube han adoptado el uso del tiempo UTC para los relojes en las máquinas virtuales, por ejemplo, cuando se ejecuta el comando date en una máquina virtual con Ubuntu en Azure, se obtiene la fecha y hora UTC.
 
El tiempo UTC es mantenido por el International Bureau of Weights and Measures (BIPM).
 
## Network Time Protocol (NTP)
 
El protocolo de tiempo de red (Network Time Protocol - NTP) define un procedimiento centralizado para la sincronización de relojes.
 
En este procedimiento los clientes consultan un servidor de tiempo, el cual proporciona el tiempo UTC ajustado al tiempo TAI (mediante la introducción de segundos vacíos).
 
 
En el protocolo NTP el servidor A se conecta al servidor de tiempo B, entonces el servidor B responde con los tiempos T2 y T3.
 
T2 es el tiempo en que el servidor B recibe la conexión del servidor A y T3 es el tiempo en que el servidor B envía la respuesta al servidor A.
 
T1 es el tiempo en que el servidor A se conecta al servidor B y T4 es el tiempo en que el servidor A recibe la respuesta del servidor B.
 
Cuando el servidor A recibe la respuesta del servidor B, calcula el tiempo de respuesta Tres suponiendo que el tiempo que tarda el requerimiento Treq es igual al tiempo de respuesta Tres.
 
```text
T4 - T1 = Treq + Tres + (T3 - T2) = 2 Tres + (T3 - T2)
```
 
Despejando Tres el servidor A puede establecer su tiempo en T3+Tres, es decir, el tiempo que envió el servidor B más el tiempo que tarda en llegar la respuesta.
 
Debido a que el servidor de tiempo puede saturarse al recibir muchas peticiones,  se suele implementar el mismo procedimiento sobre una topología de árbol.
 
Los servidores de los estratos superiores del árbol son más exactos que los servidores de estratos inferiores, debido a que la diferencia entre treq y tres es menor.
 
## Instalación de NTP en Ubuntu
 
Para asegurar que un servidor Ubuntu mantiene su reloj sincronizado con el tiempo UTC, podemos instalar NTP en el servidor ejecutando los siguientes comandos:
 

```bash
sudo apt-get update
sudo apt-get install ntp
```


## Conclusiones

1. En una empresa, es muy importante que se configure el tiempo UTC en todas las computadoras de back-end, incluso aquellas que ejecutan sistemas locales.

Las máquinas virtuales que ejecutan en la nube utilizan el tiempo UTC, por esta razón, es recomendable que los servidores de la empresa también utilicen el tiempo UTC, de manera que no sea necesario modificar las aplicaciones cuando la empresa migre a la nube.

2. No se debe utilizar el tipo de dato Date (fecha) en la base de datos, es recomendable utilizar el tipo de dato Datetime (fecha-hora).

3. Un sistema no debe registrar la fecha-hora local en la base de datos; el sistema deberá registrar la fecha-hora UTC, de manera que el sistema esté preparado para migrar a la nube.

4. El front-end del sistema debe mostrar al usuario la fecha-hora local, mientras que el back-end debe guardar la fecha-hora UTC. El front-end deberá realizar la conversión hora-local a hora UTC y viceversa, es decir, el front-end enviará la fecha-hora UTC al back-end, así mismo, el back-end enviará al front-end la fecha-hora UTC.

5. Es necesario instalar NTP en los servidores para mantener sincronizados sus relojes.
