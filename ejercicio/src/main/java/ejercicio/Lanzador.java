package ejercicio;

public class Lanzador {
	
	private static String pagWeb="", dirLocal="";

	public static void main(String[] args) {
		
		try {
			
			pagWeb=args[0];
			
			dirLocal=args[1];
			
			BaseDatos db = new BaseDatos();
			
			db.createNewDatabase(dirLocal);
			
			WebScraping crawler = new WebScraping(pagWeb,dirLocal);
			
			crawler.seek();
			
		}catch(Exception e) {
			System.out.println("END_ERROR "+e.getMessage());
		}
	}

}
