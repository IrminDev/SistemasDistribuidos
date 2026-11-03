# Principios del cómputo en la nube

## Servicios virtuales a nivel masivo

El cómputo en la nube es un modelo de arquitectura basado en una infraestructura de cómputo ofrecida como servicios virtuales a nivel masivo.

El concepto clave en el cómputo en la nube es el "servicio", así, se ofrece:

- IaaS (Infrastructure as a Service) Servicio de creación de infraestructura virtual y física.
- PaaS (Platform as a Service) Servicios de DBMS, plataformas de desarrollo y pruebas.
- SaaS (Software as a Service) Servicio de aplicaciones de software.
- Otros servicios con la terminación "as a Service":
  - DaaS (Data as a Service) Servicio de acceso a datos.
  - DRaaS (Disaster Recovery as a Service) Servicio de recuperación de máquinas virtuales ante desastres.

## Escalabilidad en la nube

La escalabilidad es la capacidad de un sistema en la nube de aumentar o disminuir los recursos aprovisionados (CPU virtuales y memoria de una máquina virtual, disco de sistema operativo, disco de datos.

La escalabilidad vertical se refiere al crecimiento o descrecimiento de las capacidades de una computadora.

La escalabilidad horizontal se refiere a la creación o eliminación de máquinas virtuales.

## Disponibilidad en la nube

La disponibilidad es la capacidad de un sistema en la nube para estar accesible (disponible) en todo momento, de manera que los usuarios puedan acceder el sistema.

La disponibilidad se mide en porcentaje que representa el tiempo durante el cual el sistema está disponible (tiempo de actividad o uptime).

La disponibilidad es: **100 x el tiempo de actividad / tiempo total**.

Por ejemplo, si el sistema está disponible 364 días de 365 días, entonces la disponibilidad es del 99.7260%

Los proveedores de nube establecen la disponibilidad de los servicios en un SLA (Service Level Agreement) o Acuerdo de nivel de servicio.

## Confiabilidad en la nube

La confiabilidad es la capacidad de un sistema en la nube para funcionar durante largos períodos de tiempo sin interrupciones.

Para garantizar la confiabilidad, los proveedores de nube implementan diversas estrategias, por ejemplo:

- **Geo-Redundant Storage.** La replicación de los respaldos en regiones a cientos de kilómetros de la región actual.

- **Zona de disponibilidad.** Uno o más datacenters equipados con su propia alimentación, refrigeración y red.

- **Dominios de error.** Las máquinas virtuales que pertenecen a un mismo dominio de error comparten la misma fuente de alimentación y red física.

- **Dominios de actualización.** Las máquinas virtuales dentro de un mismo dominio de actualización se reiniciarán simultáneamente durante el mantenimiento planeado. Solo se reiniciará un dominio de actualización a la vez.

- **Balanceo de carga.** Un balanceador de carga permite escalar un sistema, y al mismo tiempo evita puntos únicos de falla aumentando la disponibilidad.

- **Site Recovery.** Permite replicar y recuperar máquinas virtuales en caso de falla o desastre.

- **Respaldos.** Copia de seguridad de máquinas virtuales y archivos, permitiendo la recuperación de los datos en caso de ransomware, pérdida o corrupción de los datos

- **Alta disponibilidad en DBMS.** Crea una réplica de la base de datos en una zona de disponibilidad diferente.

## Transparencia

La transparencia es la capacidad de los usuarios para acceder los recursos de nube sin la necesidad de conocer los detalles de la infraestructura de nube.

Podemos dividir la transparencia en siete categorías:

- **Transparencia en el acceso a los datos.** Los usuarios y aplicaciones acceden a los datos en la nube de manera estandarizada.
- **Transparencia de ubicación.** Los usuarios de nube acceden a los recursos independientemente de su localización física.
- **Transparencia de migración.** Los usuarios de nube no son afectados por la migración de los datos y de los procesos que pueda realizar la plataforma.
- **Transparencia de reubicación.** La transparencia de reubicación se refiere a la capacidad de la nube de cambiar la ubicación de un recurso mientras está en uso, sin que el usuario que accede el recurso se vea afectado.
- **Transparencia de replicación.** La transparencia de replicación es la capacidad de la nube de ocultar la existencia de recursos replicados.
- **Transparencia de concurrencia.** La transparencia de concurrencia se refiere a la capacidad de la nube de ocultar el hecho de que varios usuarios y procesos comparten los diferentes recursos de manera concurrente.
- **Transparencia ante fallas.** La transparencia ante fallas es la capacidad de la nube de ocultar una falla.

Por ejemplo, si un sistema que se encuentra totalmente replicado, cuando el sistema principal falla entonces el usuario accederá de manera transparente a la réplica del sistema.

Más adelante en el curso veremos cómo replicar un sistema completo en la nube utilizando un administrador de tráfico de red y cómo implementar sistemas tolerantes a fallas en la nube mediante balance de carga.

## Rendimiento

El rendimiento es la eficiencia con la que un sistema en la nube utiliza los recursos de nube, incluyendo la capacidad de procesamiento, almacenamiento, ancho de banda, entre otros recursos.

## Desempeño

El desempeño se refiere a la velocidad con la ejecuta un sistema en la nube.

El desempeño depende de las técnicas de programación y del rendimiento del sistema. El objetivo es mantener el sistema funcionando de manera consistente incluso bajo cargas de trabajo pesadas.

## La elasticidad en la nube

Debido a que el cómputo en la nube está basado fundamentalmente en la virtualización de los recursos informáticos, este modelo de arquitectura ofrece una ventaja única, la posibilidad de hacer crecer y decrecer los recursos aprovisionados.

Supongamos un servicio de streaming bajo demanda, como es el caso de Netflix. En este tipo de servicio la demanda crece los fines de semana y decrece los días entre semana.

Si el proveedor del servicio no aprovisiona los recursos suficientes para atender la demanda del fin de semana, entonces muchos usuarios se quedarán sin servicio.

Por otra parte, si el proveedor del servicio aprovisiona los recursos necesarios para atender a sus usuarios el fin de semana, estos recursos estarán subutilizados los días entre semana, lo cual resulta en pérdidas económicas.

Sin lugar a dudas, el éxito que han alcanzado las empresas proveedoras de streaming bajo demanda, se debe a que su modelo de negocio está basado en la posibilidad que les ofrece la nube para crecer y decrecer los recursos aprovisionados, a esta característica de la nube se le llama elasticidad.

El cómputo elástico es la habilidad de hacer crecer y decrecer rápidamente la capacidad de cómputo (CPUs), la memoria y el almacenamiento para adaptarse a la demanda.

Para implementar el cómputo elástico se utilizan herramientas de monitoreo, las cuales aprovisionan y des-aprovisionan recursos conforme son necesarios, sin detener la operación.

Sin embargo, hay proveedores de nube que afirman que no existe tal cosa como "nube privada", ya que el tema de elasticidad se ve acotado en un equipo "privado" debido a la limitada escalabilidad.

En cambio, en la nube pública la escalabilidad es casi ilimitada, ya que si se agotan los recursos de un data center, sin ningún problema se puede escalar a otro data center, ya sea del mismo proveedor o de otros proveedores.

Una característica importante de la nube (pública) es "pagar por lo que se usa", esto significa que solo se paga por los recursos aprovisionados.

En el caso de un centro de cómputo tradicional (también es el caso de la "nube privada") la empresa paga por todo el equipo, lo use a toda su capacidad o no.

Consideremos la siguiente gráfica:

### Escenario On-premise

Al inicio los requerimientos informáticos de la empresa son pocos, sin embargo ésta debe hacer una gran inversión inicial ya que debe adquirir el equipo que le permita operar durante un periodo de tiempo determinado.

Para saber cuál será la inversión inicial  se deberá hacer una planeación de capacidad.

Suponiendo que la empresa crece, entonces en determinado momento deberá realizar la actualización de su equipo informático.

Entonces se deberá hacer una planeación de capacidad para establecer el tamaño de la inversión, estimando un tiempo determinado de operación.

No obstante las previsiones, la empresa podría decrecer, por tanto los requerimientos informáticos también decrecen. El costo de la actualización no se recupera, incluso, en poco tiempo el equipo se hará obsoleto.

### Escenario nube

Ahora supongamos que la empresa utiliza servicios en la nube. Entonces la inversión inicial es mínima, ya que la empresa contratará los servicios indispensables para operar. Conforme la empresa crece, la elasticidad de la nube permite adecuar el tamaño de la infraestructura informática de acuerdo a las necesidades.

Si la empresa decrece, también decrecen sus necesidades y el costo de la infraestructura informática. En la gráfica, las barras corresponden al costo de los servicios de nube. Podemos ver que este costo se ajusta a las necesidades de la empresa.

Podemos concluir que al utilizar los servicios de nube se optimiza la relación costo/beneficio, ya que solo pagamos lo que realmente usamos.
