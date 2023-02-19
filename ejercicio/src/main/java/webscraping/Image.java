package webscraping;

/**
 * Model class Image
 * this describes image resources retrieved from
 * a url, its location in a local directory,
 * and unique id from data base
 * @author Diego Berroterán
 */
public class Image {
	
	private int id;	
	private String urlImg;	
	private String dirImg;

	public int getId() { return id; }	
	public void setId(int id) { this.id = id; }
	
	public String getUrlImg() { return urlImg; }	
	public void setUrlImg(String urlImg) { this.urlImg = urlImg; }
	
	public String getDirImg() { return dirImg; }	
	public void setDirImg(String dirImg) { this.dirImg = dirImg; }
}
