# Cómputo en la nube

## Aprovisionamiento de recursos informáticos On-premise

En el pasado, el aprovisionamiento de recursos informáticos On-premise (en las instalaciones de la empresa) representaba el concurso de diferentes proveedores de bienes y servicios, como eran los representantes de ventas, ingenieros de preventa, fabricantes de los equipos, fabricante del sistema operativo, fabricante de la base de datos, agentes aduanales, transportistas, instaladores del site, proveedor de energía, proveedor de comunicaciones, instaladores del hardware, instaladores del software, entre otros.

Entonces, el aprovisionamiento de recursos informáticos era un proceso complejo y tardado, el cual culminaba con el sistema en producción.

Después había que reaprovisionar cuando crecían las necesidades de la empresa.

## Cómputo como servicio

El concepto de cómputo en la nube (cloud computing) está basado en las ideas del profesor John McCarthy de la Universidad de Stanford.

En un discurso en 1961, predijo que la tecnología de tiempo compartido (la posibilidad de que múltiples usuarios y procesos utilicen una misma computadora) podría llevar en el futuro, a ofrecer las capacidades de cómputo y las aplicaciones como servicios públicos.

Esta idea se concretaría en lo que actualmente conocemos como cómputo en la nube, al ofrecer servicios virtualizados a nivel masivo en Internet.

Por otra parte, en 1984 John Burdette Gage acuñó el slogan The network is the computer para Sun Microsystems, el cual significa que la computadora de escritorio es una "ventana" a la red, y mediante el software apropiado, es posible transferir tareas de cómputo a otras computadoras.

A la posibilidad de trasferir tareas de cómputo a otras computadoras se le conoce como computation offloading.

## Fábricas de información

En 2006 aparece el artículo "The Information Factories" en la prestigiosa revista WIRED [1].

En este artículo se presenta la idea de "fábricas de información" (Information Factories).

El autor, George Gilder, argumenta que la tecnología de la información evoluciona hacia un modelo de procesamiento y almacenamiento de datos centralizados en grandes centros de datos.

Estos centros de datos estarían equipados con grandes servidores y redes de alta velocidad.

Entonces, en lugar de depender de computadoras personales, los usuarios accederán a servicios informáticos a través de redes de comunicación utilizando dispositivos más simples, como serían las computadoras portátiles y los dispositivos móviles como tabletas y teléfonos celulares.

> "The desktop is dead. Welcome to the Internet cloud, where massive facilities across the globe will store all the data you'll ever use", The Information Factories, George Gilder.

Este modelo de informática centralizada permitiría contar con capacidades "ilimitadas" de procesamiento y almacenamiento, lo cual proporcionará niveles de escalabilidad sin precedentes.

Si bien George Gilder no introduce el concepto de cómputo en la nube como tal, su artículo explora las implicaciones sociales y económicas de este modelo de computación basado en la idea de "fábrica de información".

## Proveedores de servicios en la nube

### Amazon

En 2006 Amazon lanzó la plataforma Amazon Web Services (AWS) incluyendo los servicios Elastic Computer (EC2) y Storage Cloud (S3).

AWS popularizó el modelo de Infraestructura como Servicio (IaaS, Infrastructure as a Service), el cual permite a las empresas alquilar recursos de cómputo y almacenamiento en lugar de invertir en hardware propio.

### Google

En 2008 Google lanzó Google App Engine, inicialmente como una plataforma para desarrollar y alojar aplicaciones web.

Posteriormente, Google lanza el servicio Google Cloud Storage en 2010 y en 2013 el servicio Google Compute Engine (GCE). Con estos servicios Google entra en el mercado de infraestructura como Servicio (IaaS).

La marca Google Cloud Platform (GCP) se lanzó oficialmente en 2011.

### Microsoft

En 2010 Microsoft lanzó la plataforma Windows Azure (ahora conocida como Microsoft Azure), con servicios para desarrolladores al nivel de plataforma (PaaS), entre otros:

- Windows Azure Compute para la ejecución de aplicaciones en la nube utilizando máquinas virtuales administradas por Microsoft.
- Windows Azure Storage para el almacenamiento de datos estructurados y no estructurados.
- Windows Azure SQL Database el cual es SQL Server en la nube.

En 2012 Microsoft lanzó los servicios:

1. Azure Virtual Machines, con el cual Microsoft entra en el mercado de IaaS.
2. Azure Virtual Network el cual permite crear redes virtuales en la nube.
3. Azure Storage IaaS el cual ofrece almacenamiento para las máquinas virtuales.

En 2015 Microsoft lanza el servicio Azure Container Service el cual permite ejecutar contenedores Docker en Azure.

## Definición de cómputo en la nube

El Instituto Nacional de Estándares y Tecnología (NIST) de Estados Unidos, define el cómputo en la nube de la siguiente manera [2]:

> "Cloud computing is a model for enabling ubiquitous, convenient, on-demand network access to a shared pool of configurable computing resources (e.g., networks, servers, storage, applications, and services) that can be rapidly provisioned and released with minimal management effort or service provider interaction."

## Las cinco características esenciales del cómputo en la nube

### 1. Autoservicio bajo demanda

Los usuarios pueden unilateralmente aprovisionar automáticamente capacidades en la infraestructura del proveedor del servicio de nube, tales como tiempo de servidor, almacenamiento y red, de acuerdo a sus necesidades sin intervención humana.

### 2. Acceso amplio por medio de la red

Las capacidades que ofrece el proveedor de nube están disponibles a través de la red, y son accedidas mediante mecanismos estándares utilizando clientes heterogéneos como teléfonos celulares, tabletas, computadoras de escritorio, etc.

### 3. Recursos compartidos

Los recursos de cómputo del proveedor de nube son compartidos por múltiples usuarios usando un modelo “multiinquilino” (multitenant). Los recursos físicos y virtuales (procesamiento, almacenamiento, memoria, ancho de banda, etc.) son asignados y reasignados dinámicamente de acuerdo a las necesidades de los usuarios.

Los recursos asignados son transparentes en términos de la ubicación, se manera que el usuario no tiene que saber qué recurso físico está utilizando. No obstante, el usuario puede determinar en qué región o datacenter creará los recursos virtuales.

### 4. Elasticidad

Las capacidades pueden ser aprovisionadas y des-aprovisionadas elásticamente (manualmente o automáticamente), permitiendo al usuario escalar hacia arriba o hacia abajo, horizontalmente o verticalmente, bajo demanda. La escalabilidad hacia arriba con frecuencia parece ser ilimitada.

### 5. Medición del servicio

Los recursos de nube pueden ser monitoreados con la finalidad de controlar y optimizar automáticamente su utilización. Este monitoreo se realiza a diferentes niveles de abstracción de acuerdo al tipo de servicio (almacenamiento, procesamiento, ancho de banda, cuentas de usuario, etc.).

## Los tres modelos de servicio del cómputo en la nube

### 1. Infraestructura como servicio

En el nivel de Infraestructura como servicio o Infrastructure as a Service (IaaS), el usuario puede aprovisionar procesamiento (máquinas virtuales), almacenamiento (discos virtuales), redes y otros servicios que le permiten ejecutar cualquier software.

En este nivel el usuario tiene el control de sistema operativo, el almacenamiento, algunos componentes de la red, y las aplicaciones que ejecutan sobre el sistema operativo.

### 2. Plataforma como servicio

En el nivel de plataforma como servicio o Platform as a Service (PaaS), el usuario puede aprovisionar servicios de instancias de bases de datos, respaldos, ejecución de servicio web, plataformas de desarrollo y pruebas, y otros servicios sin la necesidad de aprovisionar o controlar la infraestructura subyacente (servicios de IaaS como serían la máquina virtual, la red o el almacenamiento).

### 3. Software como servicio

En el nivel de software como servicio o Software as a Service (SaaS), el usuario puede utilizar las aplicaciones publicadas por el proveedor de nube, las cuales ejecutan sobre la infraestructura de nube. El usuario no tiene que aprovisionar servicios de PaaS o de IaaS.

Los servicios al nivel de SaaS se acceden mediante una aplicación cliente, p.e. un navegador (web browser). Los usuarios acceden a las aplicaciones de SaaS mediante un usuario y contraseña o mediante un par de claves.

## Los cuatro modelos de despliegue del cómputo en la nube

### 1. Nube privada

Los servicios de nube son aprovisionados para el uso exclusivo de una sola organización.

La organización puede ser propietaria de la infraestructura, de un tercero o de una combinación de ambas.

De igual forma, la administración y operación puede ser realizada por la misma organización, por un tercero o una combinación de ambas.

En cuanto a la ubicación, la infraestructura puede estar on-premise (en las instalaciones de la organización), o bien, off-premise (fuera de las instalaciones de la organización).

### 2. Nube comunitaria

Los servicios de nube son aprovisionados para el uso de una comunidad de usuarios u organizaciones con objetivos compartidos (p.e. empresas, instituciones de gobierno, etc.).

Al igual que la nube privada, la propiedad, administración y operación de la infraestructura puede ser de la comunidad, un tercero o una combinación de ambos.

La infraestructura puede estar ubicada en instalaciones de la comunidad, un tercero o una combinación de ambas.

### 3. Nube pública

Los servicios de nube son aprovisionados para el público en general.

La infraestructura está ubicada en las instalaciones del proveedor de los servicios de nube (Amazon, Microsoft, Google, etc.).

### 4. Nube híbrida

La infraestructura de nube es una composición de dos o más modelos de despliegue (nube privada, nube comunitaria, nube pública).

La comunicación entre las redes de las diferentes nubes, se realiza mediante tecnologías propietarias o estándares que permitan implementar canales de comunicación seguros (como son las VPN).
