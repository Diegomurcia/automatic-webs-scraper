package ejercicio;

import java.util.concurrent.Callable;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Navegador implements Callable<Page> {
	
	Playwright playwright;
	
	@Override
    public Page call() throws Exception {
		
		this.playwright = Playwright.create();
		Browser browser = playwright.chromium().launch();
		Page page = browser.newPage();
		
		return page;
    }
	
	public void closeBrowser() {
		this.playwright.close();
	}
}
