package ejercicio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Descargar implements Runnable {
	
	private String pag="", dirLocal="";
	
	public Descargar(String pag, String dirLocal) {
		this.pag=pag;
		this.dirLocal=dirLocal;
	}
	
	@Override
	public void run() {
		
		try {
			
			URL url = new URL(pag);
			BufferedImage img = ImageIO.read(url);
			File file = new File(dirLocal);
			
			if(dirLocal.contains(".png")) {
				ImageIO.write(img, "png", file);
			}
			else if(dirLocal.contains(".jpeg")) {
				ImageIO.write(img, "jpeg", file);
			}
			else if(dirLocal.contains(".jpg")) {
				ImageIO.write(img, "jpg", file);
			}
			
			save(url,dirLocal);
			
		} catch (IOException e) {
			System.out.println("DOWNLOAD_ERROR "+e.getMessage()+" "+pag);
		}
		
	}

	private void save(URL url, String dirLocal) {
		
		BaseDatos db = new BaseDatos();
		
		db.insert(url,dirLocal);
		
	}

}
