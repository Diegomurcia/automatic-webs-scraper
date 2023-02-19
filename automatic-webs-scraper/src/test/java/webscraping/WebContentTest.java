package webscraping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class WebContentTest {
	
	WebContent webContent = new WebContent();
	
	@Test
	void testToPlainString() throws IOException {
		// given
		String content = "HTML";
		
		// when
		String result = webContent.toPlainString("\\test\\java\\webscraping\\mock_web.html");
		
		//then
		assertEquals(result, content, "El contenido es correcto");
	}
	
}
