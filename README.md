**
	Forma de uso
**

	Para abrir el proyecto hay que descomprimir el zip automatic-web-scraper en el disco C en 
	Windows y tener instalado java 19.

	El código fuente puede abrirse como un proyecto maven en C:\automatic-web-scraper 
	y exportar en C:\automatic-web-scraper jar ejecutable con nombre automatic-web-scraper.

	Además, el código fuente puede descargarse desde github mediante:
	https://github.com/Diegomurcia/automatic-web-scraper.git 

	para probar el programa hay que ejecutar C:\automatic-web-scraper\automatic-web-scraper.bat 
	o ejecutar los siguientes comandos por linea de comandos:

	cd C:\automatic-web-scraper
	java -jar automatic-web-scraper.jar https://www.neocine.es C:\\automatic-web-scraper

	El programa va a navegar a la página para traer todo contenido HTML.

	Se cerará una base de datos relacional portable wharehouse.db en C:\automatic-web-scraper\sqlite-db.

	Se creará una tabla loot con el id autoincrementado, la url unica y el directorio de cada imagen.

	Finalmente, se creará un fichero csv con el contenido de la tabla wharehouse sql.

**
	Diseño
**

	Para obtener el contenido web se ha implementado la librería URLConnection, para la base de datos sqlite-jdbc.
	
	El programa implementa ExecutorService del paquete java.util.concurrent para descargar las imagenes
	enlazadas en el DOM de una página web en ocho hilos.
