# Codificación de texto

Cuando estudiamos el tema de archivos en la clase de Sistemas Operativos, vemos que hay dos tipos de archivos: archivos binarios y archivos con formato texto.

## Archivos binarios

Los bytes en un archivo binario pueden tomar cualquier valor entre 0 y 255.

Ejemplos de archivos binarios son: imágenes (fotografías), videos, archivos de Office (Word, Excel, etc.), archivos de Libre Office, programas ejecutables, etc.

## Archivos con formato texto

Los archivos con formato texto son aquellos producidos por editores de texto como el Bloc de notas, Visual Studio Code, vi, nano, entre otros.

Debido a que las computadoras solo procesan y almacenan números (no texto), para procesar y guardar un archivo con formato texto es necesario traducir cada carácter a un número. Para ello, se utiliza una tabla de codificación.

## Código ASCII

El código ASCII fue una de las primeras codificaciones utilizadas. En ASCII, cada carácter se traduce a un número entre 0 y 127, lo que permite representar 128 caracteres.

Esta codificación fue diseñada para computadoras de 7 bits y permite representar caracteres del inglés, pero no incluye acentos, ñ, o caracteres de otros idiomas.

## Código ANSI

Debido a las limitantes del código ASCII, surgió la necesidad de representar más caracteres, especialmente para idiomas con caracteres latinos (como el español).

El código ANSI extendió el código ASCII utilizando 8 bits, lo que permitió representar hasta 256 caracteres.

Sin embargo, esta codificación no incluía caracteres de idiomas como el chino, japonés, árabe, ruso, griego, entre otros.

## Unicode

Para resolver las limitaciones de ASCII y ANSI, se desarrolló Unicode, un estándar que asigna un número único a cada carácter de todos los idiomas del mundo.

Unicode utiliza 16 bits, lo que permite representar hasta 65,536 caracteres. Esto incluye caracteres de prácticamente todos los idiomas, símbolos matemáticos, emojis, y más.

Sin embargo, una desventaja de Unicode es que duplica el tamaño de los archivos de texto en comparación con ANSI, ya que utiliza dos bytes para cada carácter en lugar de uno.

## UTF-8

Para optimizar el uso de espacio, se creó UTF-8, una codificación de longitud variable que utiliza 1, 2, 3 o 4 bytes para codificar cada carácter.

UTF-8 es compatible con ASCII y ANSI, ya que los caracteres ASCII (0-127) se representan con un solo byte, igual que en ANSI.

Los caracteres adicionales utilizan 2, 3 o 4 bytes según sea necesario. Esto permite que UTF-8 sea eficiente en términos de espacio y compatible con muchos idiomas.

Actualmente, UTF-8 es la codificación más utilizada en las aplicaciones web, lenguajes de programación y sistemas operativos.

# Archivos texto en Windows y en Unix (Linux)

Los archivos con formato texto en Windows, terminan cada línea con los caracteres carriage return (código ASCII 13) y line feed (código ASCII 10). Para representar estos caracteres en un programa se utilizan las secuencias de escape "\r\n".

Los archivos con formato texto en Unix o Linux, terminan cada línea con el carácter line feed (código ASCII 10). Para representar este carácter en un programa se utiliza la secuencia de escape "\n".

Para convertir un archivo texto creado en Unix o Linux para ser utilizado en Windows, podemos utilizar el siguiente comando en Unix (Linux):

```sh
unix2dos nombre-del-archivo
```

Para convertir un archivo texto creado en Windows para ser utilizado en Unix o Linux, podemos utilizar el siguiente comando en Unix (Linux):

```sh
dos2unix nombre-del-archivo
```

# Programas en Java

Los programas en Java pueden ser escritos en diferentes codificaciones, lo que permite que los nombres de variables, funciones y cadenas de caracteres puedan escribirse en cualquier idioma.

Por ejemplo, el siguiente programa calcula el factorial, los nombres de las variables y funciones están en chino. El código fuente se deberá guardar con codificación UTF-8 y se deberá compilar con la opción `-encoding UTF-8: javac -encoding UTF-8 Factorial.java`

```java
public class Factorial
{
    // 方法: 计算阶乘 (factorial)
    public static long 阶乘(int 数字)
    {
        if (数字 <= 1)
            return 1;
        else
            return 数字 * 阶乘(数字 - 1);
    }
    public static void main(String[] 参数)
    {
        int 数字 = 5;
        long 结果 = 阶乘(数字);
        System.out.println("数字 " + 数字 + " 的阶乘是: " + 结果);
    }
}
```

# Editores de texto

Cuando se crea un archivo de texto con un editor como el Bloc de notas, Visual Studio Code, o cualquier otro, se debe seleccionar la codificación que se utilizará para guardar el archivo.

# Archivos CSV

Cuando se exporta una tabla de Excel a un archivo CSV (valores separados por comas), se debe seleccionar la codificación que se utilizará para guardar el archivo.

De igual manera, al abrir un archivo CSV, es necesario especificar la codificación que se utilizó para guardar el archivo, de modo que el texto se muestre adecuadamente.

Es importante destacar que "formato texto" no es lo mismo que "extensión .txt".

Archivos con extensiones como .java, .html, .py, entre otros, también son archivos con formato texto, aunque no tengan la extensión .txt