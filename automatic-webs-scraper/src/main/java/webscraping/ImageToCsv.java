package webscraping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**Testing Class ImageToCsv
 * this download to a given dest a csv file
 * with each image scraped into a database
 * Args: webPage url and disk location
 * @author Diego Berroterán
 */
public class ImageToCsv implements ItToCsv{

    private String dirLocal;

    public ImageToCsv(String dirLocal){
        this.dirLocal= dirLocal;
    }

    private void imageListToCsv(List<Image> imgList) {

        StringBuilder str = new StringBuilder();
        int sizeControl = 0;

        try (FileWriter file = new FileWriter(
        		this.dirLocal+
        		AutoWebConstat.SLASH+
        		AutoWebConstat.LOOT_EXCEL_CSV)){

            for(Image img : imgList) {
            	sizeControl++;
                str.append(img.getId());
                str.append(AutoWebConstat.SEMICOLOM);
                str.append(img.getUrlImg());
                str.append(AutoWebConstat.SEMICOLOM);
                str.append(img.getDirImg()+"\n");
                if(sizeControl>1000) {
                	file.write(str.toString());
                	str = new StringBuilder();
                	sizeControl=0;
                }
            }

            file.write(str.toString());

            System.out.println("");
            System.out.println("Fin de la ejecución");
            System.out.println("");
            System.out.println("Se ha generado el csv: "
                    +AutoWebConstat.LOOT_EXCEL_CSV);

        } catch (IOException e) {
            System.out.println("FILE_ERROR "+e.getMessage());
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public void toCsv(Object it) { imageListToCsv((List<Image>) it); }
}
