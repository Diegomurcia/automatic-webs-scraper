# ejercicio-candidato

**
	Forma de uso
**

	Para abrir el proyecto hay que descomprimir el zip ejercicio-candidato en el disco C en 
	Windows y tener instalado java 19.

	El código fuente puede abrirse como un proyecto maven en C:\ejercicio-candidato\ejercicio 
	y exportar en C:\ejercicio-candidato como jar ejecutable con nombre ejercicio-candidato.

	Además, el código fuente puede descargarse desde github mediante:
	https://github.com/Diegomurcia/ejercicio-candidato.git 

	Para probar el programa hay que ejecutar C:\ejercicio-candidato\ejercicio-candidato.bat 
	o ejecutar los siguientes comandos por línea de comandos:

	cd C:\ejercicio-candidato
	java -jar ejercicio-candidato.jar https://www.neocine.es C:\\ejercicio-candidato

	El programa va a navegar a la página y va a cargar un navegador headless que traerá el contenido.

	Se creará una base de datos relacional portable loot.db en C:\ejercicio-candidato\sqlite-db.

	Se creará una tabla wharehouse con el id autoincrementado, la url y el directorio de cada imagen.

	Finalmente, se creará un fichero csv con el contenido de la tabla sql.

**
	Diseño
**

	Para el navegador se ha implementado la librería Playwright, para la base de datos sqlite-jdbc.
	
	El programa implementa ExecutorService del paquete java.util.concurrent para descargar las imágenes
	enlazadas en el DOM de una página web en cuatro hilos.
