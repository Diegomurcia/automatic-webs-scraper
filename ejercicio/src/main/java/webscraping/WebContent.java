package webscraping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class WebContent
 * This simple class with 
 * a single method that returns
 * a String with the HTML content of 
 * a given web page url
 * @author Diego Berroterán
 */
public class WebContent {

    private String inputLine;
    private String content="";

    public String toPlainString(String webPage) throws IOException {

        try {
            URL url = new URL(webPage);
            URLConnection conn = url.openConnection();

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))){

                while ((inputLine = br.readLine()) != null) {
                	content+=inputLine;
                }

            } catch (MalformedURLException e) {
                System.out.println("WEB_CONTENT_ERROR"+e.getMessage());
            }
        }catch(IOException e ){
            System.out.println("WEB_CONTENT_ERROR"+e.getMessage());
        }
        return content;
    }
}
