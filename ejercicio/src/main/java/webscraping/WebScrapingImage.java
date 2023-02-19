package webscraping;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**Class WebScraping
 * this seeks for images linked into a web from a given url and
 * download each image concurrently into a database and given dest
 * Args: webPage url and disk location
 * This class can be easily tested by ItToCsv.class
 * @author Diego Berroterán
 */
public class WebScrapingImage implements IWebScraping{
	
	private String webPage;
	private String dirLocal;
	private IDataBase dataBase;
	private ExecutorService executorService;
	private List<Image> imgListSaved;
	private WebContent webContent = new WebContent();
	private static final String SRC = "src=";
	private static final String JPG = ".jpg";
	private static final String JPEG = ".jpeg";
	private static final String PNG = ".png";
	WebScrapingImage(String webPage,
					   String dirLocal,
					   IDataBase dataBase,
					   ExecutorService executorService) {
		this.webPage=webPage;
		this.dirLocal=dirLocal;
		this.dataBase=dataBase;
		this.executorService=executorService;
	}

	@SuppressWarnings("unchecked")
	public void seek(){
		
		try{

			List<String> imgList = getImgList(
					webContent.toPlainString(webPage));
            
            for(String imgFile : imgList) {
            	
            	int nameIndex = imgFile.lastIndexOf("/");
            	String imgName = imgFile.substring(nameIndex+1);
			
				executorService.execute(new DownloadImage(
						webPage+imgFile,
						dirLocal+AutoWebConstat.SLASH+imgName,
						dataBase));
			}
		
		}catch (IOException e) {
			System.out.println("WEBSCRAPING_ERROR "+e.getMessage());
		}

		try {
			executorService.shutdown();

			while(!executorService.awaitTermination(60, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			executorService.shutdownNow();
			System.out.println("WEBSCRAPING_ERROR "+e.getMessage());
		}

		this.imgListSaved = (List<Image>) dataBase.select(dirLocal);
	}

	private List<String> getImgList(String content) {
		List<String> imgList = new ArrayList<String>();
		String[] lineas = content.split(">")   ;
		
		for(String line : lineas) {
			if(isAimage(JPG, line)){
				imgList.add(addLine(line, line.indexOf(SRC)+5, line.lastIndexOf(JPG)+4));
	        }
			else if(isAimage(JPEG, line)){
				imgList.add(addLine(line, line.indexOf(SRC)+5, line.lastIndexOf(JPEG)+5));
	        }
			else if(isAimage(PNG, line)){
				imgList.add(addLine(line, line.indexOf(SRC)+5, line.lastIndexOf(PNG)+4));
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

	public List<Image> getLoot() {

		if(Objects.nonNull(this.imgListSaved)){return this.imgListSaved;}

		return new ArrayList<Image>();
	}
}
