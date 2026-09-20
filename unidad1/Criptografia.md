# Criptografía y sockets seguros

En esta clase veremos cómo implementar un cliente y un servidor mediante sockets seguros.

Primeramente, vamos a explicar los conceptos básicos de PKI (Public Key Infrastructure).

## Encriptado y desencriptado simétrico

La criptografía simétrica es un conjunto de algoritmos que permiten encriptar y desencriptar utilizando la misma clave, conocida como clave secreta o clave privada.

Ejemplos de algoritmos simétricos:

- AES-128
- AES-256
- RC4
- RC5
- DES
- 3DES

Los algoritmos simétricos son muy rápidos, sin embargo, resulta complicado intercambiar las claves.

Por ejemplo, supongamos que un amigo se va a estudiar a otro país y en un momento dado te pide le envíes un documento electrónico utilizando email. Desde luego habría que encriptar el documento para evitar que alguna otra persona pudiera hacer mal uso de él.

El problema es que ambos deberían tener la clave para encriptar y desencriptar. A este problema se le conoce como problema de distribución de clave (key distribution problem).

La única solución es que ambos hayan compartido la clave para encriptar y desencriptar antes del viaje, ya que tampoco es seguro enviar la clave por email.


 

## Encriptado y desencriptado asimétrico

Una mejor solución al problema de distribución de claves es el uso de la criptografía asimétrica.

En la criptografía asimétrica el destinatario del mensaje tiene dos claves, una llamada clave privada y otra llamada clave pública.

La clave privada es una clave que mantiene en secreto el destinatario de los datos, mientras que la clave pública es una clave que puede conocer cualquiera.

En la criptografía asimétrica (también llamada criptografía de llave pública o PKI: Public Key Infrastructure), el originario utiliza la clave pública del destinatario para encriptar los datos. Entonces el destinatario utiliza su clave privada para desencriptar los datos encriptados y obtener los datos en claro.


## Firma digital

El par de claves pública y privada pueden utilizarse para autenticar un documento electrónico. A esta autenticación se le llama firma digital.

El efecto de una firma digital es el mismo que tiene una firma autógrafa, esto es, el no repudio de un documento: la posibilidad de probar que el documento fue, en efecto, firmado por una persona (la persona no puede repudiar el documento).

Supongamos que vamos a enviar un documento (datos) a un destinatario. Para garantizar que el documento procede de un origen determinado, el originario genera un hash de los datos y encripta el hash con su clave privada (la cual solo es conocida por el originario). Al hash encriptado le llamamos la firma digital del documento.


Entonces el originario envía al destinatario el documento y la firma digital respectiva.

Para verificar la firma, el destinatario desencripta la firma digital utilizando la clave pública del originario, entonces obtiene el hash del documento. Así mismo, el destinatario genera el hash del documento recibido.

Si los dos hashes son iguales, entonces podemos estar seguros que el documento recibido procede del propietario de la clave pública y que el documento no ha sido modificado, debido a que cualquier modificación en el documento cambiaría el hash.

## Certificado digital

Ahora bien, ¿Cómo podemos garantizar que una clave privada pertenece a una persona u organismo o dominio de Internet? (es decir, que en efecto la persona firmó un documento). 

Considerando que la clave privada es uno a uno con la clave pública, ¿Cómo sabemos que la clave pública pertenece a la persona u organismo o dominio de Internet?

Si el originario nos envió su clave pública por email, y sabemos con certeza que la dirección de correo electrónico pertenece a esta persona, entonces concluimos que la clave pública pertenece a esta persona, de tal manera que podemos verificar la firma digital de cualquier documento que nos envíe.

Sin embargo, no siempre podemos conocer a ciencia cierta que una dirección de correo electrónico pertenece a una persona determinada.

Para resolver el problema de la identidad de una clave pública (y con ello la identidad de la clave privada), se utiliza un documento electrónico llamado certificado digital.

Un certificado digital es un documento electrónico que contiene, entre otros datos:

- La identidad —nombre— de una persona, organización o dominio de Internet.
- Las fechas de validez del certificado.
- La clave pública de la persona, organización o dominio de Internet.
- La firma digital de los datos que contiene el certificado.

La clave pública que contiene el certificado está asociada a una clave privada que solo conoce la persona u organización o dominio de Internet propietario del certificado digital.

Para mayor seguridad, la clave privada generalmente se encripta con una clave simétrica.

El certificado digital es firmado —avalado— por una autoridad certificadora (CA).

Cuando se adquiere un certificado firmado por una CA, además del certificado se obtiene un archivo conocido como *bundle*. Los certificados incluidos en el *bundle* se llaman certificados intermedios. El archivo contiene los certificados de CA que forman una ruta de certificación desde el certificado emitido hasta un certificado raíz preinstalado en la computadora.

El sistema operativo de nuestra computadora cuenta con un repositorio de certificados de confianza; en este repositorio se instalan los certificados raíz (autofirmados) de las autoridades certificadoras en las que confiamos.

Por defecto, el sistema operativo incluye en el repositorio de certificados de confianza los certificados de las autoridades certificadoras reconocidas internacionalmente. Estos certificados se conocen como certificados raíz (*root*).

Nuestra computadora reconoce como válido un certificado (por ejemplo, el certificado de un dominio al que se conecta el navegador), si hay una ruta de certificación entre el certificado y un certificado en el repositorio de certificados de confianza.

El estándar más utilizado para certificados digitales es el X.509 (el SAT utiliza el mismo estándar para la firma digital).

Existen dos tipos de certificados:

- Certificados autofirmados.
- Certificados firmados por una CA.

 

## Certificado autofirmado

Un certificado autofirmado es aquel que se firma con la misma clave privada correspondiente a la clave pública que contiene el certificado.

Un certificado autofirmado se puede crear mediante el comando `keytool` del JDK.

Al crear un certificado autofirmado, se crea un par de claves pública y privada, la clave pública se incluye en el certificado y este se firma utilizando la clave privada.

Los datos de identidad en el certificado autofirmado los captura el usuario al crear el certificado, por tanto, un certificado autofirmado no tiene validez como medio de identificación (identidad de la clave pública), no obstante, puede utilizarse para implementar comunicación segura.

 

## Certificado firmado por una autoridad certificadora (CA)

Un certificado firmado por una autoridad certificadora (CA) es un certificado el cual ha sido firmado por un organismo que actúa como autoridad, dicha autoridad garantiza que el certificado pertenece a una determinada persona u organización.

Un certificado de CA es similar a una credencial, la cual contiene el nombre, la fotografía y demás información de una persona; para garantizar que los datos de la credencial corresponden a la persona, la credencial deberá ser firmada por una autoridad.

Los certificados de autoridad certificadora se pueden adquirir con un proveedor de certificados digitales, por ejemplo, [cheapsslsecurity.com](https://cheapsslsecurity.com).

Existen dos tipos de certificados firmados por una CA:

- Certificados que verifican el dominio.
- Certificados que verifican el dominio y, adicionalmente, la empresa.

Para poder tener un certificado con verificación de dominio, es necesario ser propietario de un dominio.

Por otra parte, para poder tener un certificado con verificación de empresa, es necesario tener una empresa y un dominio.
