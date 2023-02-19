package webscraping;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
		Pattern pattern = Pattern.compile(AutoWebConstat.IMG_SRC_REGX);
		Matcher matcher = pattern.matcher(content);
		
		while(matcher.find()) {
			if ( !matcher.group(1).isEmpty()) {
				imgList.add(matcher.group(4));
			}
		}
		return imgList;
	}

	public List<Image> getLoot() {

		if(Objects.nonNull(this.imgListSaved)){return this.imgListSaved;}

		return new ArrayList<Image>();
	}
}
