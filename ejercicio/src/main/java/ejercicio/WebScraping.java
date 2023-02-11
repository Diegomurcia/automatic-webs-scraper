package ejercicio;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.microsoft.playwright.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class WebScraping {
	
	private String pagWeb="", dirLocal="", SRC = "src=";;
	
	public WebScraping(String pagWeb, String dirLocal) {
		this.pagWeb=pagWeb;
		this.dirLocal=dirLocal+"\\\\"+"img-ejercicio-candidato";
	}
	
	public void seek(){
		
		Navegador browserTask = new Navegador();
		
		ExecutorService executorService = 
				Executors.newFixedThreadPool(4);
		
		try {
			
			Future<Page> navTask = 
					executorService.submit(browserTask);
			
			Page page = navTask.get();
            
			page.navigate(pagWeb);
            
            List<String> imgList = getImgList(page.content());
            
            browserTask.closeBrowser();
            
            for(String titulo : imgList) {
            	
            	int nameIndex = titulo.lastIndexOf("/");
            	String imgName = titulo.substring(nameIndex+1);
			
				executorService.execute(new Descargar(
						pagWeb+titulo,
						dirLocal+"\\\\"+imgName));
			}
		
		} catch (InterruptedException e) {
			System.out.println("INTERRUPTION_ERROR "+e.getMessage());
		} catch (ExecutionException e) {
			System.out.println("EXECUTION_ERROR "+e.getMessage());
		}catch (Exception e) {
			System.out.println("WEBSCRAPING_ERROR "+e.getMessage());
		}
		
		executorService.shutdown();
		
		try {
			while(!executorService.awaitTermination(60, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			executorService.shutdownNow();
		}
		
		BaseDatos db = new BaseDatos();
        
        List<Image> imgListSaved = 
				db.select(dirLocal);
		
		csv(imgListSaved);
	}

	private void csv(List<Image> imgListSaved) {
		
		FileWriter file;
		String SEPARATOR = ";";
		StringBuilder str = new StringBuilder();
		
		try {
			file = new FileWriter(this.dirLocal+"-excel.csv");
			
			for(Image img : imgListSaved) {
								
				str.append(img.getId()+SEPARATOR);
				str.append(img.getUrlImg()+SEPARATOR);
				str.append(img.getDirImg()+"\n");
			}
			
			file.write(str.toString());
			
			file.close();
			
			System.out.println("");
			System.out.println("Fin de la ejecución");
			System.out.println("");
			System.out.println("Se ha generado el csv: "
					+"img-ejercicio-candidato-excel");
			
		} catch (IOException e) {
			System.out.println("FILE_ERROR "+e.getMessage());
		}
	}

	private List<String> getImgList(String content) {
		List<String> imgList = new ArrayList<String>();
		String[] lineas = content.split(">");
		String JPG = ".jpg";
		String JPEG = ".jpeg";
		String PNG = ".png";
		
		for(String line : lineas) {
			if(isAimage(JPG, line)){
				imgList.add(addLine(line, line.indexOf(SRC)+5, line.indexOf(JPG)+4));
	        }
			else if(isAimage(JPEG, line)){
				imgList.add(addLine(line, line.indexOf(SRC)+5, line.indexOf(JPEG)+5));
	        }
			else if(isAimage(PNG, line)){
				imgList.add(addLine(line, line.indexOf(SRC)+5, line.indexOf(PNG)+4));
			}
		}
		
		return imgList;
	}

	private boolean isAimage(String ext, String line) {
		return line.contains(SRC) && line.contains(ext);
	}

	private String addLine(String line, int begInd, int lastInd) {
		return line.substring(begInd,lastInd);
	}

}
