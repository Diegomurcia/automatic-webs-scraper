package webscraping;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Mine Class Launcher
 * This takes Args: webPage url and disk location
 * it creates a dataBase and four treads to perform
 * web scraping of a given page 
 * This class can be easily tested by ItToCsv.class
 * @author Diego Berroterán
 */
public class Launcher {
	
	private static String webPage;
	private static String dirLocal;
	
	public static void main(String[] args) {
		
		try {
			webPage=args[0];
			dirLocal=args[1];
			
			IDataBase db = new DataBaseSQLite();
			db.createNewDatabase(dirLocal);
			
			ExecutorService executorService =
					Executors.newFixedThreadPool(8);
			
			IWebScraping crawler = new WebScrapingImage(
					webPage,
					dirLocal+AutoWebConstat.SLASH+AutoWebConstat.IMAGE_LOOT,
					db,
					executorService);
			
			crawler.seek();
			
			ItToCsv converter = new ImageToCsv(dirLocal);
			converter.toCsv(crawler.getLoot());
		}catch(Exception e) {
			System.out.println("END_ERROR "+e.getMessage());
		}
	}

}
