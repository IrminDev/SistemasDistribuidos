# Azure Blob Storage

Azure Blob Storage es un servicio que permite almacenar cantidades masivas de datos no estructurados, a un costo reducido.

Azure Blob Storage es escalable, durable, seguro y con la capacidad de manejar cargas de trabajo sobre demanda.

Azure Blob Storage puede almacenar objetos binarios, tales como datos binarios utilizados por las aplicaciones, videos, audios, textos, respaldos, etc. Los blobs se organizan en contenedores.

Azure Blob Storage permite optimizar los costos, ya que ofrece la posibilidad de colocar los datos en diferentes niveles de acceso (access tiers) de acuerdo a la frecuencia en la que son accedidos y/o modificados los datos:

- Frecuente (hot tier). Nivel online optimizado para almacenar datos que son accedidos y modificados frecuentemente. Este nivel es el más rápido y el más costoso.
- Esporádico (cool tier). Nivel online optimizado para almacenar datos que son accedidos y modificados con poca frecuencia. Este nivel es más lento y menos costoso que el nivel "frecuente". Los datos en el nivel "esporádico" deben almacenarse como mínimo 30 días.
- Inactivo (cold tier). Nivel online optimizado para almacenar datos que casi nunca son accedidos o modificados. Este nivel más lento y menos costoso que el nivel "esporádico". Los datos en el nivel "inactivo" deben almacenarse como mínimo 90 días.
- Archivo (archive tier). Nivel offline optimizado para almacenar datos que casi nunca son accedidos y con la posibilidad de ser accedidos con altas latencias (del orden de horas). Los datos en el nivel "archivo" deben almacenarse como mínimo 180 días.

## Notas

- El nivel de acceso predeterminado es "frecuente".
- Para cambiar al nivel de acceso "esporádico", se debe seleccionar la opción "Configuración" en la cuenta de almacenamiento.
- El nivel de acceso "archivo" solo se puede establecer para el blob no para la cuenta de almacenamiento.

Para acceder los blobs es posible utilizar APIs para .NET, Java, Python, JavaScript, C++, y Go, o bien, la API REST de Azure Blob Storage.

## Reproducir Vídeo

## Referencias

- Introduction to Azure Storage
- Niveles de acceso frecuente, esporádico y de archivo para los datos de blobs
- Delegación del acceso mediante una firma de acceso compartido
- Azure Blob Storage pricing

Para utilizar Azure Blob Storage, se debe crear una cuenta de almacenamiento, y dentro de ésta, se podrán crear contenedores; cada contenedor tiene una capacidad máxima de 100 TB. Una vez que hemos creado un contenedor, podemos cargar los archivos (blobs) al contenedor.

Ahora veremos cómo crear una cuenta de almacenamiento, un contenedor, y cómo cargar y acceder blobs.

## Crear una cuenta de almacenamiento

1. Ingresar al portal de Azure.
2. En el campo de búsqueda escribir "Cuentas de almacenamiento".
3. Seleccionar "+Crear".
4. Seleccionar un grupo de recursos o crear uno nuevo.
5. Ingresar en nombre de la cuenta de almacenamiento. El nombre debe ser único en Azure, de 3 a 24 caracteres (letras minúsculas y dígitos).
6. Seleccionar la región.
7. En el campo "Servicio principal" seleccionar "Azure Blob Storage o Azure Data Lake Storage Gen2".
8. En el campo "Rendimiento" seleccionar "Estándar". Esta selección no se puede cambiar después de crear la cuenta de almacenamiento.
9. En el campo "Redundancia" seleccionar "Almacenamiento con redundancia local (LRS)".
10. Dar clic en el botón "Revisar".
11. Dar clic en el botón "Crear".

## Crear un contenedor en una cuenta de almacenamiento

1. Seleccionar la cuenta de almacenamiento.
2. Seleccionar "Contenedores" en la sección "Almacenamiento de datos".
3. Agregar un contenedor seleccionando en la opción "+Contenedor".
4. Ingresar el nombre del contenedor. El nombre solo puede contener letras minúsculas, números o guiones, debe iniciar con una letra o número, no puede contener guiones seguidos.
5. Dar clic en el botón "Crear".

## Cargar archivos a un contenedor

1. Seleccionar la cuenta de almacenamiento.
2. Seleccionar "Contenedores".
3. Seleccionar el contenedor.
4. Seleccionar la opción "Cargar".
5. Seleccionar "Buscar archivos".
6. Seleccionar el archivo en la ventana.
7. Dar clic en el botón "Cargar".

Crear un token de acceso compartido (SAS: Shared Access Signature) para un contenedor

1. Seleccionar la cuenta de almacenamiento.
2. Seleccionar "Contenedores".
3. Seleccionar el contenedor.
4. Seleccionar "Tokens de acceso compartido" en la sección "Configuración".
5. Seleccionar método de firma "Clave de cuenta".
6. En el campo "Clave de firma" seleccionar la clave que se va a asociar a la SAS (clave 1 o clave 2).
7. En el campo "Permisos" seleccionar los permisos que se otorgarán.
8. Si se requiere restringir desde donde se accederá el blob, ingresar la IP o intervalo de IPs.
9. Dar clic en el botón "Generar URL y token de SAS".
10. Copiar la URL que aparece en el campo "URL de SAS de Blob".

La forma de una URL de contenedor es la siguiente:

https://<cuenta-de-almacenamiento>.blob.core.windows.net/<nombre-del-contenedor>?sp=r&st=<fecha-UTC-inicial>&se=<fecha-UTC-final>&spr=https&sv=2022-11-02&sr=c&sig=<SAS>

Crear un token de acceso compartido (SAS: Shared Access Signature) para un blob

1. Seleccionar la cuenta de almacenamiento.
2. Seleccionar "Contenedores".
3. Seleccionar el contenedor.
4. En el menú contextual del blob (los tres puntos al final) seleccionar la opción "Generar SAS".
5. Seleccionar método de firma "Clave de cuenta".
6. En el campo "Clave de firma" seleccionar la clave que se va a asociar a la SAS (clave 1 o clave 2).
7. En el campo "Permisos" seleccionar los permisos que se otorgarán.
8. Si se requiere restringir desde donde se accederá el blob, ingresar la IP o intervalo de IPs.
9. Dar clic en el botón "Generar URL y token de SAS".
10. Copiar la URL que aparece en el campo "URL de SAS de Blob".

Nota. A partir de una URL de contenedor, es posible obtener una URL para acceder un blob en el contenedor, solo se agrega el nombre del blob (nombre del archivo) a la URL del contenedor:

https://<cuenta-de-almacenamiento>.blob.core.windows.net/<nombre-del-contenedor>/<nombre-del-blob>?sp=<permisos>&st=<fecha-UTC-inicial>&se=<fecha-UTC-final>&spr=https&sv=2022-11-02&sr=<tipo de SAS: c: contenedor, b: blob>&sig=<SAS>

## Revocar una o varias SAS

Para revocar (invalidar) una o varias SAS, asociadas a una clave de acceso de una cuenta de almacenamiento (Clave 1 o clave 2), se deberá cambiar (girar) la clave:

1. Seleccionar la cuenta de almacenamiento.
2. Seleccionar la opción "Claves de acceso".
3. Para cambiar una clave, dar clic en "Girar clave" en la clave 1 o la clave 2 (key1 o key2).

## Notas

- Se puede tener varias SAS simultáneamente.
- Una vez que se ha girado la clave, se deberá generar nuevas SAS asociadas a la clave modificada.

El siguiente programa Java obtiene un blob utilizando una URL de contenedor, a la que se ha agregado el nombre del blob. Una vez leído el blob, se despliega la longitud del blob:

```java
// Blob.java
// Generado por ChatGPT. 2024
// Modificado por Carlos Pineda G.

import java.net.*;
import java.io.*;

public class Blob {
  public static void main(String[] args) {
// URL del blob con SAS
String sasBlobUrl =
"https://<cuenta-de-almacenamiento>.blob.core.windows.net/<nombre-del-contenedor>/<nombre-del-blob>?sp=<permisos>&st=<fecha-UTC-inicial>&se=<fecha-UTC-final>&spr=https&sv=2022-11-02&sr=<tipo de SAS>&sig=<SAS>";
try {
    // Crear una conexión HTTP GET a la URL del blob con SAS
    URL url = new URL(sasBlobUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    // Obtener la respuesta
    int responseCode = connection.getResponseCode();
    // Verificar si la solicitud fue exitosa (código 200)
    if (responseCode == HttpURLConnection.HTTP_OK) {
        // Leer el contenido del blob
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        // Convertir el contenido a un arreglo de bytes
        byte[] blobBytes = outputStream.toByteArray();
        // Mostrar la longitud del blob en bytes
        System.out.println("Longitud del blob: " + blobBytes.length + " bytes");
        // Cerrar los flujos de entrada y salida
        outputStream.close();
        inputStream.close();
    } else {
        System.out.println("Error al obtener el blob. Código de respuesta: " + responseCode);
    }
    // Cerrar la conexión
    connection.disconnect();
} catch (Exception e) {
    System.err.println("Error al obtener el blob: " + e.getMessage());
}
}
```

}

# Actividades individuales a realizar

## Descarga y carga de una imagen administrada

En esta actividad, vamos a descargar una imagen administrada de Azure, cargarla nuevamente y crear una máquina virtual con la imagen cargada.

Pre-requisitos:

- 30 GB en el disco duro de la computadora local (Linux).
- Azure CLI instalado en la computadora local (Linux).

### Descargar la imagen

```bash
1.Acceder a Azure desde la computadora local:

az login

2. Obtener el ID de la imagen:

GRUPO_RECURSOS=<grupo-de-recursos>
IMAGEN=<nombre-de-la-imagen>
ID_IMAGEN=$(az image show -g "$GRUPO_RECURSOS" -n "$IMAGEN" --query id -o tsv)
MAQUINA_VIRTUAL=<nombre-de-la-máquina-virtual-temporal>
VHD=<nombre-de-la-máquina-virtual-temporal>.vhd

3. Crear una máquina virtual temporal a partir de la imagen:

az vm create -g "$GRUPO_RECURSOS" -n "$MAQUINA_VIRTUAL" --image "$ID_IMAGEN" --size Standard_B1s --public-ip-address "" --os-disk-delete-option Detach --admin-username <nombre-del-usuario> --generate-ssh-keys

4. Obtener el ID del disco y el nombre del disco de sistema operativo:

ID_DISCO=$(az vm show -g "$GRUPO_RECURSOS" -n "$MAQUINA_VIRTUAL" --query "storageProfile.osDisk.managedDisk.id" -o tsv)
NOMBRE_DISCO=$(basename "$ID_DISCO")

5. Eliminar la máquina virtual temporal (el disco de sistema operativo queda sin asignar):

az vm delete -g "$GRUPO_RECURSOS" -n "$MAQUINA_VIRTUAL" --yes

6. Generar una SAS para descargar el VHD con AzCopy:

SAS=$(az disk grant-access -g "$GRUPO_RECURSOS" -n "$NOMBRE_DISCO" --access-level Read --duration-in-seconds 43200 -o tsv)

O bien:

SAS=$(az disk grant-access --resource-group "$GRUPO_RECURSOS" --name "$NOMBRE_DISCO" --access-level Read --duration-in-seconds 7200 --query accessSas -o tsv)

7. Podemos ver la SAS generada:

echo "$SAS"

8. Instalar AzCopy en Ubuntu:

curl -sL https://aka.ms/downloadazcopy-v10-linux | tar -xz
sudo cp ./azcopy_linux_amd64_*/azcopy /usr/local/bin/

9. Descargar la imagen del disco (notar que se ejecuta con sudo):

sudo azcopy copy "$SAS" "$VHD"

10. Revocar la SAS:

az disk revoke-access -g "$GRUPO_RECURSOS" -n "$NOMBRE_DISCO"
```

11. En el portal de Azure eliminar todos los recursos asociados a la máquina virtual temporal.

### Cargar la imagen

1.Acceder a Azure desde la computadora local (Linux):

```bash
az login

2. Crear una cuenta de almacenamiento

GRUPO_RECURSOS=<grupo-de-recursos>
REGION="<región>" # p.e. "eastus2"
CUENTA_ALMACENAMIENTO=<nombre-de-la-cuenta-de-almacenamiento>
CONTENEDOR=<nombre-del-contenedor>
VHD=<nombre-de-la-máquina-virtual-temporal>.vhd
DISCO=<nombre-del-disco>
IMAGEN=<nombre-de-la-imagen-restaurada>

az storage account create -g "$GRUPO_RECURSOS" -n "$CUENTA_ALMACENAMIENTO" -l "$REGION" --sku Standard_LRS

3. Crear un contenedor dentro de la cuenta de almacenamiento:

az storage container create --account-name "$CUENTA_ALMACENAMIENTO" --account-key "$CLAVE_CUENTA_ALMACENAMIENTO" -n "$CONTENEDOR"

4. Obtener la clave (KEY) de la cuenta de almacenamiento:

CLAVE_CUENTA_ALMACENAMIENTO=$(az storage account keys list -g "$GRUPO_RECURSOS" -n "$CUENTA_ALMACENAMIENTO" --query [0].value -o tsv)

5. Obtener la SAS para acceder al contenedor:

SAS=$(az storage container generate-sas --account-name "$CUENTA_ALMACENAMIENTO" -n "$CONTENEDOR" --permissions acwl --expiry 2099-01-01T00:00Z --account-key "$CLAVE_CUENTA_ALMACENAMIENTO" -o tsv)

6. Subir el VHD como blob al contenedor (ejecutar en el directorio donde está la imagen descargada):

azcopy copy "$VHD" "https://$CUENTA_ALMACENAMIENTO.blob.core.windows.net/$CONTENEDOR/$VHD?$SAS"

7. Crear el disco a partir del blob:

az disk create -g "$GRUPO_RECURSOS" -n "$DISCO" --source "https://$CUENTA_ALMACENAMIENTO.blob.core.windows.net/$CONTENEDOR/$VHD" --hyper-v-generation V2 --os-type Linux -l "$REGION"

8. Crear la imagen a partir del disco:

ID_DISCO=$(az disk show -g "$GRUPO_RECURSOS" -n "$DISCO" --query id -o tsv)
az image create -g "$GRUPO_RECURSOS" -n "$IMAGEN" --os-type Linux --source "$ID_DISCO" --hyper-v-generation V2 -l "$REGION"

9. En el portal de Azure eliminar la cuenta de almacenamiento y el disco.

10. Para verificar la imagen restaurada, crear una máquina virtual a partir de la imagen.

Notar que al crear la máquina virtual a partir de la imagen restaurada se deberá ingresar el grupo de recursos, nombre de la máquina virtual, el tamaño, el tipo de autenticación, el tamaño del disco del SO, Tipo del disco del sistema operativo,
las opciones de disponibilidad (p.e. "No se requiere redundancia de la infraestructura"), Red virtual, Subred, IP pública, Diagnóstico de arranque.
```

La región, el tipo de seguridad, la arquitectura de VM serán los mismos que la máquina virtual original.

## Notas

- El usuario de la nueva máquina virtual deberá ser el mismo de la máquina virtual original. Si se crea otro usuario y la imagen se creó conservando el usuario, entonces la máquina virtual tendrá dos usuarios, el usurio de la máquina virtual original y el usuario de la nueva máquina virtual.
- Es necesario crear las reglas de puerto que requieren los procesos que ejecutan en la máuinaq virtual (p.e. servidores).
- Si se accederá la máquina virtual utilizando un dominio, es necesario asocial el dominio a la dirección IP pública de la máquina virtual.
