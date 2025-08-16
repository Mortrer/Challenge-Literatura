# Challenge Literatura - AluraLatam & Oracle Next

Este proyecto fue desarrollado como parte del **Challenge de AluraLatam y Oracle Next**. Su objetivo es consumir información de libros desde la API de **Gutendex**, almacenar los datos en una base de datos PostgreSQL y ofrecer distintas opciones de consulta a través de la consola.

## Descripción del Proyecto

El sistema permite:

- Consultar libros por título usando la API de Gutendex.
- Guardar libros y autores en la base de datos.
- Listar libros registrados.
- Listar autores registrados.
- Listar autores vivos en un año específico.
- Listar libros por idioma (actualmente soporta Español e Inglés).

## Características Principales

- **Consumo de API:** Obtiene información de libros y autores directamente desde Gutendex.  
- **Base de Datos:** PostgreSQL, con conexión configurada mediante variables de entorno.  
- **Gestión de Autores y Libros:** Guarda autores y libros, incluyendo fechas de nacimiento y muerte de los autores.  
- **Opciones Interactivas:** Permite al usuario interactuar mediante un menú en consola para consultar y listar información.

## Configuración de Base de Datos

Se utilizan variables de entorno para definir la conexión a la base de datos, evitando exponer datos sensibles en el proyecto:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
