# Tablas agregadas

Ahora veremos cómo utilizar tablas agregadas para ejecutar eficientemente consultas multidimensionales, así mismo, veremos cómo podemos generar las tablas agregadas para un sistema de BI, utilizando técnicas de MapReduce.
Consideremos la base de datos "ejemplo_olap" (modelo estrella) que creamos anteriormente.

Ejecutando la siguiente instrucción SELECT, podemos obtener un cubo OLAP que incluya las ventas acumuladas con respecto a las dimensiones country, category y product:

```sql
SELECT c.country, cat.category, p.product, SUM(f.sales) AS total_sales
FROM fact_table f
JOIN country c ON f.id_country = c.id_country
JOIN product p ON f.id_product = p.id_product
JOIN category cat ON f.id_category = cat.id_category
GROUP BY c.country, cat.category, p.product
ORDER BY c.country, cat.category, p.product;
```

Sin embargo, si la fact table es muy grande, la ejecución de la instrucción SELECT puede tardar mucho tiempo.

Para que las consultas multidimensionales ejecuten más rápido, se utilizan tablas agregadas.

Supongamos que creamos una tabla llamada "country_category_product", incluyendo los siguientes campos: id_country, id_category, id_product, sales. Esta tabla va a conteneer las ventas acumuladas por país, categoría y producto.

Entonces se dice que esta tabla es una tabla agregada, debido a que contiene una métrica acumulada.

Para cargar la tabla agregada "country_category_product" ejecutamos la siguiente instrucción:

```sql
INSERT INTO country_category_product (id_country, id_category, id_product, sales)
SELECT c.id_country, p.id_category, f.id_product, SUM(f.sales) as total_sales
FROM fact_table f
INNER JOIN country c ON f.id_country = c.id_country
INNER JOIN product p ON f.id_product = p.id_product
GROUP BY c.id_country, p.id_category, f.id_product;
```

Entonces podemos utilizar la tabla agregada "country_category_product" (en lugar de la fact_table) para obtener el cubo OLAP con las ventas acumuladas con respecto a las dimensiones country, category y product:

```sql
SELECT c.country, cat.category, p.product, SUM(ccp.sales) AS total_sales
FROM country_category_product ccp
JOIN country c ON ccp.id_country = c.id_country
JOIN category cat ON ccp.id_category = cat.id_category
JOIN product p ON ccp.id_product = p.id_product
GROUP BY c.country, cat.category, p.product
ORDER BY c.country, cat.category, p.product;
```

Podemos ver que el cubo OLAP se genera más rápido, si utilizamos la tabla agregada en lugar de la fact table, ya que la tabla agregada tiene menos registros que la fact table.

De la misma forma, si queremos obtener un cubo OLAP con las dimensiones country y category, acumulando sales, también podemos utilizar la tabla agregada "country_category_product":

```sql
SELECT c.country, cat.category, SUM(ccp.sales) AS total_sales
FROM country_category_product ccp
JOIN country c ON ccp.id_country = c.id_country
JOIN category cat ON ccp.id_category = cat.id_category
GROUP BY c.country, cat.category
ORDER BY c.country, cat.category;
```

También es posible utilizar la tabla agregada "country_category_product", para obtener un cubo OLAP que incluya solo la dimensión country, acumulando sales:

```sql
SELECT c.country, SUM(ccp.sales) AS total_sales
FROM country_category_product ccp
JOIN country c ON ccp.id_country = c.id_country
GROUP BY c.country, cat.category
ORDER BY c.country, cat.category;
```

Y para obtener un cubo con cero dimensiones, también podemos utilizar la tabla agregada "country_category_product":

```sql
SELECT SUM(ccp.sales) AS total_sales
FROM country_category_product ccp;
```

Entonces, para que las consultas OLAP ejecuten rápidamente, los analistas de BI deben saber previamente qué tipo de cubos van a generar los usuarios, de manera que puedan crear y cargar las tablas agregadas necesarias.

El proceso de carga de la fact table, actualización de las tablas de dimensiones, y carga de las tablas agregadas, generalmente se realiza por la noche, de manera que los usuarios del sistema de BI puedan al otro día generar los cubos OLAP que requieran.

Si las fact tables son muy grandes, el proceso de carga de las tablas agregadas puede tardar demasiado.

Para acelerar la carga de las tablas agregadas, los analistas de BI pueden distribuir el proceso en varias computadoras, sin embargo, esto implicaría instalar el DMBS en diferentes computadoras, lo cual requiere más licencias para el DBMS lo cual incrementa considerablemente los costos.

Ahora veremos cómo podemos generar las tablas agregadas para un sistema de BI, utilizando técnicas de MapReduce.

## MapReduce

MapReduce es un modelo de programación utilizado para procesar grandes cantidades de datos en el área de Big Data.

Este modelo de programación fue introducido originalmente por Google para indexar la WWW, no obstante, la implementación más popular de MapReduce es actualmente Apache Hadoop.

MapReduce consta de dos operaciones, la operación "Map" (mapear) y la operación "Reduce" (reducir).

- Map. En la fase de Map, se lee un conjunto de datos y se genera una salida consistente en parejas "clave-valor" (parejas "key-value").
- Reduce. En la fase Reduce, se lee las parejas de clave-valor creadas en la fase Map, entonces se genera un conjunto más pequeño de parejas clave-valor. La "reducción" puede implicar sumar los valores para una misma clave, obtener el mayor o menor valor para una misma clave, obtener el promedio de los valores para una misma llave, etc.

Por ejemplo, supongamos que tenemos un archivo CSV con los siguientes datos de entrada:

1,2,1,200.0
2,3,1,100.0
1,2,1,50.0
2,3,1,200.0
1,2,3,100.0

Supongamos que el proceso Map obtiene la clave concatenando los tres primeros números separándolos con una coma.

Entonces las parejas clave-valor que produce el proceso Map son las siguientes:

"1,2,1"  200.0
"1,2,1"  50.0
"2,3,1"  100.0
"2,3,1"  200.0
"1,2,3"  100.0

Ahora bien, la salida del proceso Map no entra directamente al proceso Reduce, primero es necesario crear una colección con los valores asociados a cada clave:

"1,2,1"  200.0 50.0
"2,3,1"  100.0 200.0
"1,2,3"  100.0

El proceso Reduce recibe una colección de valores para cada clave. En este caso, el proceso Reduce va a acumular los valores para cada clave, entoncesd el proceso Reduce finalmente produce las siguientes parejas clave-valor:

"1,2,1"  250.0
"2,3,1"  300.0
"1,2,3"  100.0

### Job MapReduce

Un job MapReduce es la ejecución de un programa que realiza los procesos Map y Reduce sobre una arquitectura master-slave.

Los datos de entrada se colocan inicialmente en un sistema de archivos distribuido llamado HDFS (Hadoop Distributed File System).

En un job MapReduce se realizan las siguientes tareas:

1. Split (dividir). El HDFS distribuye los datos de entrada en los nodos slave que forman un cluster.
2. Map (mapear). Cada nodo slave ejecuta el proceso Map, el resultado intermedio consistente en parejas clave-valor.
3. Shuffling (repartir). Los resultados intermedios son enviados a diferentes nodos slave, de acuerdo a la clave, de manera que los valores asociados a una misma clave sean puestos en el mismo nodo.
4. Merge (unir). Los resultados intermedios son agrupados por la clave.
5. Reduce (reducir). Cada nodo slave ejecuta el procedo Reduce, el resultado se escribe en el HDFS.

## Cómo programar un proceso MapReduce en Java

Ahora veremos cómo programar en Java un proceso MapReduce utilizando Apache Hadoop.

Hadoop es una herramienta utilizada en al área de Big Data para almacenar y procesar grandes volúmenes de datos. Los datos se distribuyen en clusters de nodos utilizando el sistema de archivos HDFS (Hadoop Distributed File System).

Supongamos que tenemos datos de entrada en un archivo CSV.

Cada registro en el archivo contiene tres números enteros y un valor punto flotante.

Entonces queremos acumular los valores punto flotante utilizando como clave la combinación de los tres números enteros.

Primeramente, necesitamos crear una clase "Mapper":

```java
// Generado por ChatGPT, 2024
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
public class AggregationMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
private Text dimensionKey = new Text();
private DoubleWritable metricValue = new DoubleWritable();
@Override
public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
String[] parts = value.toString().split(",");
if (parts.length == 4) {
    String d1 = parts[0].trim();
    String d2 = parts[1].trim();
    String d3 = parts[2].trim();
    double m1 = Double.parseDouble(parts[3].trim());
    dimensionKey.set(d1 + "," + d2 + "," + d3);
    metricValue.set(m1);
    context.write(dimensionKey, metricValue);
}
}
}
```

El método "map" es invocado por cada registro (row) en el archivo de datos de entrada.

El método "map" recibe como parámetros:

- key. La posición del registro (línea de texto) en el archivo de entrada. El primer registro es 0, el segundo registro es 1, y así sucesivamente.
- value. El contenido del registro de entrada (línea de texto), de tipo Text.
- context. El contexto donde se escribirá la salida (parejas clave-valor).

En este caso, el método "map" divide el registro de entrada utilizando la coma como separador.

Entonces obtiene la clave, de tipo Text, formada por los tres primeros números concatenados mediante una coma.

Finalmente escribe a la salida la clave y el valor de tipo DoubleWritable.

Ahora necesitamos crear una clase "Reducer":

```java
// Generado por ChatGPT, 2024
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
public class AggregationReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable>{
  private DoubleWritable result = new DoubleWritable();
  @Override
  public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
double sum = 0.0;
for (DoubleWritable val : values) {
  sum += val.get();
}
result.set(sum);
context.write(key, result);
  }
}
```

El método "reduce" es invocado para cada clave diferente.

El método "reduce" recibe como parámetros:

- key. La clave creada por el proceso Map, de tipo Text.
- values. Una colección de valores asociados con la clave, en este caso los valores son de tipo DoubleWritable.
- context. El contexto donde se escribirá la salida (parejas clave-valor).

En este caso, el método "reduce" acumula los valores asociados con la clave, entonces escribe a la salida la clave y la suma de los valores.

El programa principal que ejecuta el proceso MapReduce es el siguiente:

```java
// Generado por ChatGPT, 2024.
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class AggregationDriver {
public static void main(String[] args) throws Exception {
if (args.length != 2) {
    System.err.println("Usage: AggregationDriver <input path> <output path>");
    System.exit(-1);
}
Configuration conf = new Configuration();
Job job = Job.getInstance(conf, "Data Aggregation");
job.setJarByClass(AggregationDriver.class);
job.setMapperClass(AggregationMapper.class);
job.setReducerClass(AggregationReducer.class);
job.setOutputKeyClass(Text.class);
job.setOutputValueClass(DoubleWritable.class);
FileInputFormat.addInputPath(job, new Path(args[0]));
FileOutputFormat.setOutputPath(job, new Path(args[1]));
System.exit(job.waitForCompletion(true) ? 0 : 1);
}
}
```

El programa principal realiza lo siguiente:

- Crea un job MapReduce, en este caso el nombre del job es "Data Aggregation".
- Define la clase correspondiente al programa principal AggregationDriver.class).
- Define la clase Mapper (AggregationMapper.class).
- Define la clase Reducer (AggregationReducer.class).
- Define el tipo de datos de la clave de salida (Text.class).
- Define el tipo de dato del valor de salida (DoubleWriteble.class).
- Define el nombre del directorio donde se encuentran los archivos con los datos de entrada (InputPath).
- Define el nombre del directorio donde se colocarán los archivos de salida (OutputPath).
- Espera que termine el job MapReduce.

## Azure HDInsight

Azure HDInsight es un servicio para análisis de datos empresariales, open-source y completamente administrado.

Está diseñado para manejar grandes volúmenes de datos rápida y eficientemente.

En HDInsight se puede utilizar diferentes fraeworks open-source como Apache Hadoop, entre otros.

HDInsight permite reducir costos, así mismo, es un servicio elástico ya que permite escalar en forma automática la carga de trabajo hacia arriba o hacia abajo.

Algunos ejemplos de uso de HDInsight:

- Procesamiento ETL (Extract-Transform-Load), el cual se utiliza para extraer datos (estructurados o no estructurados) de fuentes heterogeneas, realizar alguna transformación a los datos y cargar los datos en un almacén de datos. Los datos no estructurados pueden provenir de las redes sociales, Internet, o en general, de archivos con información que se desee procesar.
- Procesamiento de datos para Data warehouse, donde es necesario crear tablas agregadas para optimizar las consultas que se realizan sobre los modelos multidimensionales. En este caso, se utiliza un cluster Hadopp.
- Internet de las cosas (IoT), donde es necesario procesar en tiempo real flujos de datos (stream processing) de diferentes dispositivos conectados a Internet, entonces almacenar los datos procesados, enviarlos a aplicaciones en tiempo real, o enviarlos a paneles de control (dashboards) en tiempo real. En este caso, se utiliza un cluster Kafka. Apache Kafka es una plataforma distribuida de streaming de eventos en base a una arquitectura publisher-suscriber.

En HDInsight se puede programar en Java, Python, .NET y Go.
