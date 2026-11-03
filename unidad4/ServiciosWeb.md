# Servicios web

La clase de hoy veremos los conceptos básicos de los servicios web (Web Services) así como los elementos de servicios web SOAP (Simple Object Access Protocol) y REST (REpresentational State Transfer).

## Conceptos básicos de Servicios web

En el documento Web Services Architecture (2004) del World Wide Web Consortium (W3C) define un servicio web como:

“Un sistema de software diseñado para soportar la interacción interoperable de máquina a máquina en una red. Cuenta con una interfaz descrita en un formato procesable por máquina (específicamente Servicios Web de lenguaje de descripción, conocido por el acrónimo WSDL). Otros sistemas interactúan con el servicio Web de una manera prescrita por su descripción utilizando mensajes SOAP, típicamente transportados utilizando HTTP con una serialización XML en conjunción con otros estándares relacionados con la Web”.

Un servicio web es un concepto abstracto que se implementa mediante un agente concreto.

Un agente es el software o hardware que envía y recibe mensajes.

El servicio es el recurso caracterizado por la funcionalidad que provee. Un servicio web no cambia aún cuando cambie el agente, es decir, la funcionalidad es independiente de la implementación de ésta.

El propósito de un servicio web es proveer cierta funcionalidad a nombre de su propietario (una persona o una organización).

La entidad proveedora es aquella persona u organización que provee un agente que implementa un determinado servicio.

Una entidad solicitante es una persona u organización que desea hacer uso del servicio mediante un agente solicitante (también llamado solicitante del servicio) que intercambia mensajes con el agente proveedor (también llamado  proveedor del servicio).

En la mayoría de los casos el agente solicitante es el que inicia la comunicación con el agente proveedor, aunque no siempre es así, no obstante se sigue llamando agente solicitante aunque no sea el que inicia la comunicación.

La semántica de un servicio web es la expectativa compartida sobre el comportamiento del servicio, en particular el comportamiento en respuesta a los mensajes que recibe.

Se le llama contrato al acuerdo entre la entidad solicitante y la entidad proveedora. Un contrato puede ser explícito o implícito, escrito u oral, establecido entre las personas y/o las computadoras, legal o informal.

Hay dos tipos de contratos:

- Contrato de descripción. La descripción del servicio es el contrato que gobierna la mecánica de interacción con un servicio en particular, esto es, los nombres de los métodos web, los nombres de los parámetros, los tipos de dato de los parámetros y el tipo de dato del resultado. En los servicios SOAP el contrato de descripción es el WSDL, y en los servicios REST el contrato de descripción puede escribirse utilizando OpenAPI.
- Contrato semántico. La semántica del servicio es el contrato que gobierna el significado y propósito de la interacción, esto es, la funcionalidad del servicio, por tanto, el contrato semántico describe lo que hace cada método web. El contrato semántico se escribe en lenguaje natural.

También puede haber contratos “híbridos” que incluyan elementos de descripción y elementos de semántica.

A partir de una descripción detallada del servicio web en lenguaje natural, es posible utilizar una IAG para generar el contrato de descripción y el contrato semántico; así mismo, apartir de los contratos, la IAG podrá generar el código correspondiente al agente proveedor y al agente solicitante.

### Participación en un servicio web

Una entidad solicitante puede participar de un servicio web de diferentes maneras. La siguiente figura muestra el proceso general de participación en un servicio web.

1. Las entidades solicitante y proveedora se conocen una a la otra, o por lo menos una conoce a la otra.
2. Las entidades acuerdan la descripción (WSD: Web Service Description) y semántica del servicio.
3. La descripción y la semántica son implementadas por el agente solicitante y el agente proveedor.
4. Los agentes solicitante y proveedor intercambian mensajes.

Fuente: Web Services Architecture, W3C

## Servicios web SOAP

SOAP (Simple Object Access Protocol) define un protocolo de RPC (Remote Procedure Call) basado en XML, para la interacción cliente-servidor a través de la red utilizando:

1) HTTP como la base de transporte

2) Documentos XML para la codificación de requerimientos y respuestas.

SOAP permite la comunicación entre aplicaciones ejecutando en diferentes sistemas operativos, con diferentes tecnologías y lenguajes de programación.
### Componentes de un mensaje SOAP

Un mensaje SOAP es un documento XML compuesto por los siguientes elementos:

- Envelope (sobre). Identifica el documento XML como un mensaje SOAP.
- Header (encabezado). Contiene información de encabezado.
- Body (cuerpo). Contiene información del requerimiento y la respuesta.
- Fault (falla). Contiene errores e información de estatus.

Para desarrollar servicios web SOAP en Java (p.e. Tomcat), se deberá utilizar una biblioteca que implemente la especificación JAX-WS.

### Web Services Description Language (WSDL)

Un documento WSDL es un archivo XML que contiene la descripción de un servicio web SOAP. Este especifica la localización del servicio y los métodos del servicio.

Un cliente puede hacer un requerimiento HTTP a un servicio web SOAP para obtener el WSDL que describe el servicio web.

Los elementos de un documento WSDL son los siguientes:
| Elemento | Descripción |
|---|---|
| <types> | Define los tipos de dato usados por el servicio web |
| <message> | Define los elementos de datos para cada operación |
| <portType> | Describe las operaciones que pueden ser ejecutadas y los mensajes involucrados |
| <binding> | Define el protocolo y el formato de los datos para cada portType |

En la actividad individual de la clase de hoy, vamos a implementar un servicio web SOAP en Java.

Una vez publicado el servicio web, ingresamos la siguiente URL  en un navegador:

```text
http://localhost:8080/ServicioWeb?wsdl
```

Entonces obtenemos el WSDL del servicio web:

```xml
<definitions xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"
     xmlns:wsp="http://www.w3.org/ns/ws-policy"
     xmlns:wsp1_2="http://schemas.xmlsoap.org/ws/2004/09/policy"
     xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata"
     xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
     xmlns:tns="http://ws/"
     xmlns:xsd="http://www.w3.org/2001/XMLSchema"
     xmlns="http://schemas.xmlsoap.org/wsdl/"
     targetNamespace="http://ws/"
     name="ServicioWebService">

<types>
<xsd:schema>
    <xsd:import namespace="http://ws/"
                schemaLocation="http://localhost:8080/ServicioWeb?xsd=1"/>
</xsd:schema>
</types>

<message name="mayusculas">
<part name="parameters" element="tns:mayusculas"/>
</message>

<message name="mayusculasResponse">
<part name="parameters" element="tns:mayusculasResponse"/>
</message>

<message name="suma">
<part name="parameters" element="tns:suma"/>
</message>

<message name="sumaResponse">
<part name="parameters" element="tns:sumaResponse"/>
</message>

<message name="suma2">
<part name="parameters" element="tns:suma2"/>
</message>

<message name="suma2Response">
<part name="parameters" element="tns:suma2Response"/>
</message>

<portType name="ServicioWeb">
<operation name="mayusculas">
    <input wsam:Action="http://ws/ServicioWeb/mayusculasRequest"
           message="tns:mayusculas"/>
    <output wsam:Action="http://ws/ServicioWeb/mayusculasResponse"
            message="tns:mayusculasResponse"/>
</operation>

<operation name="suma">
    <input wsam:Action="http://ws/ServicioWeb/sumaRequest"
           message="tns:suma"/>
    <output wsam:Action="http://ws/ServicioWeb/sumaResponse"
            message="tns:sumaResponse"/>
</operation>

<operation name="suma2">
    <input wsam:Action="http://ws/ServicioWeb/suma2Request"
           message="tns:suma2"/>
    <output wsam:Action="http://ws/ServicioWeb/suma2Response"
            message="tns:suma2Response"/>
</operation>
</portType>

<binding name="ServicioWebPortBinding" type="tns:ServicioWeb">
<soap:binding transport="http://schemas.xmlsoap.org/soap/http"
              style="document"/>

<operation name="mayusculas">
    <soap:operation soapAction=""/>
    <input>
        <soap:body use="literal"/>
    </input>
    <output>
        <soap:body use="literal"/>
    </output>
</operation>

<operation name="suma">
    <soap:operation soapAction=""/>
    <input>
        <soap:body use="literal"/>
    </input>
    <output>
        <soap:body use="literal"/>
    </output>
</operation>

<operation name="suma2">
    <soap:operation soapAction=""/>
    <input>
        <soap:body use="literal"/>
    </input>
    <output>
        <soap:body use="literal"/>
    </output>
</operation>
</binding>

<service name="ServicioWebService">
<port name="ServicioWebPort" binding="tns:ServicioWebPortBinding">
    <soap:address location="http://localhost:8080/ServicioWeb"/>
</port>
</service>

</definitions>
```

### A continuación, se explica las secciones del WSDL:

```text
<definitions>

El nombre del servicio web: name="ServicioWebService"
Los namespaces con los prefijos: wsu, wsp, wsp1_2, wsam, soap, tns, xsd.
El espacio de nombres principal del servicio: targetNamespace="http://ws/"
```

```text
<types>

Define los tipos de datos que utiliza el servicio.
```

Importa un esquema XSD externo "http://localhost:8080/ServicioWeb?xsd=1" donde se definen los tipos de datos de los parámetros.

```text
<message>
```

Define los mensajes entreda y salida (request y reponse). Cada mensaje hace referencia a un elemento definido en el XSD.

```text
<portType>

Define las operaciones del servicio, en este caso: mayusculas, suma y suma2.
```

```text
<binding>

Define cómo se comunica el servicio, en este caso, usa SOAP sobre HTTP.
```

```text
<service>

Define el endpoint del servicio "http://localhost:8080/ServicioWeb"
```

De la misma forma, para obtener el esquema XSD externo, ingresamos la URL "http://localhost:8080/ServicioWeb?xsd=1" en el navegador:

```xml
<xs:schema xmlns:tns="http://ws/"
   xmlns:xs="http://www.w3.org/2001/XMLSchema"
   version="1.0"
   targetNamespace="http://ws/">

<xs:element name="mayusculas" type="tns:mayusculas"/>
<xs:element name="mayusculasResponse" type="tns:mayusculasResponse"/>
<xs:element name="suma" type="tns:suma"/>
<xs:element name="suma2" type="tns:suma2"/>
<xs:element name="suma2Response" type="tns:suma2Response"/>
<xs:element name="sumaResponse" type="tns:sumaResponse"/>

<xs:complexType name="suma">
<xs:sequence>
    <xs:element name="a" type="xs:double"/>
    <xs:element name="b" type="xs:double"/>
</xs:sequence>
</xs:complexType>

<xs:complexType name="sumaResponse">
<xs:sequence>
    <xs:element name="return" type="xs:double"/>
</xs:sequence>
</xs:complexType>

<xs:complexType name="suma2">
<xs:sequence>
    <xs:element name="a"
                type="xs:int"
                minOccurs="0"
                maxOccurs="unbounded"/>
    <xs:element name="b"
                type="xs:int"
                minOccurs="0"
                maxOccurs="unbounded"/>
</xs:sequence>
</xs:complexType>

<xs:complexType name="suma2Response">
<xs:sequence>
    <xs:element name="return"
                type="xs:int"
                minOccurs="0"
                maxOccurs="unbounded"/>
</xs:sequence>
</xs:complexType>

<xs:complexType name="mayusculas">
<xs:sequence>
    <xs:element name="s"
                type="xs:string"
                minOccurs="0"/>
</xs:sequence>
</xs:complexType>

<xs:complexType name="mayusculasResponse">
<xs:sequence>
    <xs:element name="return"
                type="xs:string"
                minOccurs="0"/>
</xs:sequence>
</xs:complexType>

</xs:schema>
```

## Servicios web REST

REST (REpresentational State Transfer) define un conjunto de principios arquitectónicos para la creación de servicios web.

REST fue presentado por Roy Fielding el año 2000 en su tesis doctoral "Architectural Styles and the Design of Network-based Software Architectures".

El diseño de servicios web estilo REST sigue cuatro principios:

- Utilizar métodos HTTP de forma explícita.
- Un servicio web utiliza los métodos de HTTP: POST para crear un recurso, GET para leer un recurso, PUT para cambiar el estado o actualizar un recurso, y DELETE para borrar un recurso.
- Los servicios son "sin estado" (stateless).
- Los clientes de servicios web estilo REST deben enviar peticiones completas e independientes, es decir, las peticiones deben incluir todos los datos que permitan completar el servicio, sin la necesidad de guardar un estado entre peticiones.
- Los URIs representan una estructura de directorios.
- Los URIs (Uniform Resource Identifier) deben ser intuitivos y auto-explicados. Un URI es una jerarquía que corresponde a la estructura de los servicios web definidos en la empresa.
- Se transfiere XML, JSON o ambos.
- Los recursos que provee un servicio web pueden ser documentos, imágenes, videos y en general objetos. La representación de objetos mediante XML o JSON es fácil e independiente de la plataforma.

Para desarrollar servicios web REST en Java (p.e. Tomcat), se deberá utilizar una biblioteca que implemente la especificación JAX-RS (p.e. Jersey).

### OpenAPI

Al igual que los servicios SOAP, para desarrollar servicios REST se requiere definir un contrato de descripción y un contrato semántico.

Para definir el contrato de descripción para un servicio REST, se utiliza generalmente OpenAPI.

La especificación de OpenAPI define un lenguaje estándar que permite describir los métodos web de un servicio web.

Un contrato de descripción definido mediante OpenAPI, puede ser utilizado para la generación de documentación, la generación de código para el servidor, la generación de código para el cliente, la generación de programas de prueba, entre otros usos.

También es posible pedir a una IAG (p.e. ChatGPT), que genere un contrato de descripción utilizando OpenAPI a partir de una descripción en lenguaje natural, y luego genere código para el cliente y el servidor a partir del contrato de descripción.

Reproducir Vídeo

# Actividades individuales a realizar

En esta actividad vamos a crear un servicio web SOAP utilizando JDK8.

Nota. Debido a que SOAP se considera obsoleto, JAX-WS no está soportado a partir de la versión 11 del JDK, toda vez que se alienta a los desarrolladores a implementar servicios REST mediante JAX-RS. En cualquier caso, para implementar servicios SOAP a partir del JDK 11 será necesario utilizar alguna biblioteca de JAX-WS (p.e. Metro).

1. Primeramente creamos un directorio llamado "ws" en el cual vamos a colocar los programas que vamos a utilizar. Este directorio corresponde al paquete "ws" donde colocaremos las clases.

2. Vamos a crear el archivo "ServicioWeb.java" en el directorio "ws":

```java
package ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

@WebService
public class ServicioWeb {

@WebMethod
public double suma(@WebParam(name = "a") double a, @WebParam(name = "b") double b) {
return a + b;
}

@WebMethod
public String mayusculas(@WebParam(name = "s") String s) {
return s.toUpperCase();
}

@WebMethod
public List<Integer> suma2(@WebParam(name = "a") List<Integer> a, @WebParam(name = "b") List<Integer> b) {
List<Integer> c = new ArrayList<Integer>();
for (int i = 0; i < a.size(); i++)
    c.add(a.get(i) + b.get(i));
return c;
}
}
```

En este caso, nuestro servicio web va a incluir las operaciones: suma (suma dos números punto flotante), mayúsculas (convierte a mayúsculas una cadena de caracteres) y suma2 (suma los elementos de dos listas de enteros)

3. Para compilar el servicio web ejecutamos el siguiente comando en el directorio padre del directorio "ws":

```bash
javac ws/ServicioWeb.java
```

4. Para publicar el servicio web utilizamos el programa "Servidor.java" que funciona como servidor de aplicaciones:

```java
package ws;

import javax.xml.ws.Endpoint;

public class Servidor {
public static void main(String[] args) {
Endpoint.publish("http://localhost:8080/ServicioWeb", new ServicioWeb());
}
}
```

5. Para compilar el servidor ejecutamos el siguiente comando en el directorio padre del directorio "ws":

```bash
javac ws/Servidor.java
```

6. Ahora debemos iniciar la ejecución del servidor en otra ventana de cmd en Windows o terminal de Linux o MacOS:

```bash
java ws/Servidor
```

7. Ahora vamos a crear el cliente que consumirá el servicio web. Vamos a utilizar el programa "wsimport" disponible en el JDK8 el cual va a generar las clases que utilizará el cliente.

Al programa "wsimport" se le pasa como parámetro la URL del WSDL del servicio web, en este caso la URL es "http://localhost:8080/ServicioWeb?wsdl".

```text
Para ver el WSDL de nuestro servicio web, se puede ingresar ésta URL en un navegador.
```

Nota: antes de ejecutar el siguiente comando hacer una copia del archivo ServicioWeb.java debido a que wsimport lo borra.

Ejecutar el siguiente comando en el directorio padre del directorio "ws":

```bash
wsimport "http://localhost:8080/ServicioWeb?wsdl"
```

8. Vamos a crear el archivo Cliente.java en el directorio "ws":

```java
package ws;

import java.net.URL;
import javax.xml.namespace.QName;
import ws.ServicioWeb;
import ws.ServicioWebService;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
public static void main(String[] args) throws Exception {
ServicioWebService s = new ServicioWebService(
    new URL("http://localhost:8080/ServicioWeb?wsdl"),
    new QName("http://ws/", "ServicioWebService")
);
ServicioWeb obj = s.getServicioWebPort();

System.out.println(obj.suma(100, 200));
System.out.println(obj.mayusculas("hola"));

List<Integer> a = new ArrayList<Integer>();
a.add(1);
a.add(2);
a.add(3);

List<Integer> b = new ArrayList<Integer>();
b.add(4);
b.add(5);
b.add(6);

List<Integer> c = obj.suma2(a, b);
for (int i = 0; i < c.size(); i++)
    System.out.println(c.get(i));
}
}
```

9. Para compilar el cliente ejecutamos el siguiente comando en el directorio padre del directorio "ws":

```bash
javac ws/Cliente.java
```

10. Para ejecutar el cliente:

```bash
java ws/Cliente
```

Nota. Debido a que JDK 8.0 tiene soporte para JAX-WS, no es necesario incluir en la compilación y la ejecución la opción -cp con el archivo .jar correspondiente a JAX-WS.
