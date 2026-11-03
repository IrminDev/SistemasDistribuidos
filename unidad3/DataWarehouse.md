# OLAP - Online Analytical Processing

En el área de Inteligencia de Negocios o Business Intelligence (BI), un sistema de procesamiento analítico en línea (OLAP), es una tecnología que permite realizar análisis multidimensional de grandes volúmenes de datos de manera rápida y eficiente.

Los datos utilizados para el análisis mutidimensional, se almacenan en un almacén de datos empresarial (data warehouse), o un almacén de datos departamental (data mart).

## Data warehouse

Un data warehouse es un almacén de datos con una estructura multidimensional, ya que las métricas (indicadores) de negocio, como serían las ventas, se guardan en tablas llamadas tablas de hechos (fact tables) relacionadas con diversas dimensiones, como serían la geografía, el tiempo, los productos, los clientes, los empleados, entre otras.

Las dimensiones pueden dividirse en diferentes niveles jerárquicos, por ejemplo, una dimensión geográfica puede tener los siguientes niveles jerárquicos: colonia, municipio, estado, país y región.
Una dimensión temporal podría tener los siguientes niveles jerárquicos: fecha, día de la semana, mes, trimestre, semestre y año.

Una dimensión de producto podría tener los niveles jerárquicos: producto y categoría.

Los datos en un data warehouse se pueden estructurar en dos tipos de modelos E-R: estrella (star) y copo de nieve (snowflake).

### Modelo estrella
En el esquema estrella la fact table se enlaza con cada tabla de dimensión.

La fact table contiene las métricas (p.e. las ventas), y las llaves foráneas que enlazan la fact table con las tablas de dimensiones.

Cada tabla de dimensión contiene una llave primaria y una descripción.

Por ejemplo, la tabla correspondiente a la dimensión "producto", incluye la llave primaria del producto y el nombre del producto.

La tabla correspondiente a la dimensión "categoría" (categoría de producto), incluye la llave primaria de la categoría y el nombre de la categoría.

En este caso, "producto" y "categoría" son dos niveles jerárquicos de una misma dimensión.

En un esquema estrella todas las tablas de dimensiones se enlazan con la fact table.

Consideremos el siguiente esquema estrella:

En este caso, la fact table contiene los registros correspondientes a las ventas.

Las tablas employee, category, product, order_date, customer y country se enlazan directamente a la tabla fact table.

## Modelo copo de nieve

En el esquema copo de nieve (snowflake), la fact table se enlaza con las tablas correspondientes al primer nivel jerárquico de cada dimensión.

Al igual que en el modelo estrella, en el modelo copo de nieve la fact table contiene las métricas (p.e. las ventas).

Las llaves foráneas en la fact table, enlazan la fact table con las tablas correspondientes al primer nivel jerárquico de cada dimensión.

Si una dimensión tiene varios niveles jerárquicos, cada nivel jerárquico será una tabla de dimensión incluyendo una llave primaria y descripción, y una llave foránea que enlaza la tabla con la tabla correspondiente al nivel jerárquico superior, si lo hay.

Consideremos el siguiente esquema copo de nieve:

En este caso, la fact table contiene los registros correspondientes a las ventas.

Las tablas employee, product, order_date, customer y country se enlazan directamente a la tabla fact table.

La tabla product se enlaza con la tabla category, debido a que product y category son dos niveles jerárquicos de la dimensión "producto".

Una ventaja del modelo copo de nieve comparado con el modelo estrella, es que en el modelo copo de nieve los datos ocupan menos espacio en disco, sin embargo, las consultas en éste modelo requieren enlazar más tablas, lo que hace las consultas mucho más lentas.

En realidad, el espacio en disco que ahorra el modelo copo de nieve es poco, debido a que solo se ahorra el espacio ocupado por llaves foráneas en la fact table; por otra parte, las consultas son más rápidas en el modelo estrella.

Desde luego, un modelo normalizado, como es el caso del modelo copo de nieve, garantiza una mayor calidad en los datos. Considerando que los datos de la base de dato multidimensional son extraídos de una base de datos transaccional, la calidad de los datos la debería garantizar la base de datos transaccional, no la base de datos multidimensional.

## ROLAP

Los modelos estrella y copo de nieve anteriores son modelos Entidad-Relación para una base de datos relacional.

A un sistema de procesamiento analítico en línea sobre una base de datos relacional, se conoce como ROLAP o Relational Online Analytical Processing.

Los sistemas ROLAP tiene las siguientes ventajas:

Permiten a las empresas aprovechar su DBMS relacional, sin la necesidad de una inversión adicional en otro tipo de tecnología OLAP.
Escalan a grandes volúmenes de datos.
El modelado de datos es flexible, a medida que se requieren más métricas, dimensiones, o niveles jerárquicos en las dimensiones, se pueden agregar sin un impacto mayor en el modelo existente.
El desempeño se puede aumentar creando tablas agregadas.
SQL permite realizar consultas complejas de acuerdo a las necesidades de la empresa.

## Cubo OLAP

Un cubo OLAP es el resultado de una consulta a la base de datos mutidimensional.
En un sistema ROLAP, un cubo es el resultado de ejecutar una instrucción SELECT.

Por ejemplo, si consideramos el modelo copo de nieve que vimos anteriormente, podemos obtener un cubo OLAP que incluya las ventas acumuladas para las dimensiones country, category y customer:

Entonces, el cubo OLAP se puede obtener ejecutando la siguiente instrucción SELECT:

```sql
SELECT a.country,b.category,c.customer,sum(e.sales)
FROM country a,category b,customer c,product d,fact_table e
WHERE e.id_country=a.id_country and e.id_product=d.id_product
and d.id_category=b.id_category and e.id_customer=c.id_customer
GROUP BY 1,2,3
ORDER BY 1,2,3;
```

Nota. En las versiones recientes de Excel, a los cubos de datos se les llama tablas dinámicas.
