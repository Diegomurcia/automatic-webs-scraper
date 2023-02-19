package webscraping;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Class DownloadImage
 * implements Runnable
 * This task download an image from a given url
 * into a given directory location
 * and uses insert method from a given database
 * @author Diego Berroterán
 */
public class DownloadImage implements Runnable {
	
	private String urlImage;
	private String dirLocal;
	private IDataBase dataBase;
	
	public DownloadImage(
			String urlImage, 
			String dirLocal,
			IDataBase dataBase) {
		this.urlImage=urlImage;
		this.dirLocal=dirLocal;
		this.dataBase=dataBase;
	}
	
	@Override
	public void run() {
		
		try {
			
			URL url = new URL(urlImage);
			BufferedImage img = ImageIO.read(url);
			File file = new File(dirLocal);
			int extesionIndex = dirLocal.lastIndexOf(".");
			
			if(ifNotExist(dirLocal)) {
				ImageIO.write(
						img,
						dirLocal.substring(extesionIndex+1),
						file);
				
				save(url, dirLocal);
			}
		} catch (IOException e) {
			System.out.println(
					"DOWNLOAD_ERROR "+e.getMessage()+" "+urlImage);
		}
		
	}

	private void save(URL url, String dirLocal) {
		this.dataBase.insert(url,dirLocal);
	}
	private boolean ifNotExist(String dirLocal) {
		return this.dataBase.ifNotExist(dirLocal);
	}
}
