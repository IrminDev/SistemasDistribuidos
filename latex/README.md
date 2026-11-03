# Fuentes LaTeX

Compilar desde esta carpeta con XeLaTeX:

```sh
xelatex main.tex
xelatex main.tex
```

`main.tex` contiene preámbulo, portada e índice. Cada unidad vive en su propia carpeta y se carga con `\input`.

Para agregar una unidad, crear `unidadN/contenido.tex` y añadir `\input{unidadN/contenido}` a `main.tex`. El archivo de unidad contiene capítulos y secciones, sin `\documentclass` ni `document`.

Las unidades 3 y 4 se convierten desde `unidad3/*.md` y `unidad4/*.md` a `unidad3/contenido.tex` y `unidad4/contenido.tex`. Sus inclusiones están activas en `main.tex`. Al editar Markdown, volver a generar los `.tex` de unidad antes de compilar.
