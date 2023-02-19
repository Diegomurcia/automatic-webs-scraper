package webscraping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageTest {
	
	private final Image image = new Image();
	
	@BeforeEach
	void setUp() {
		image.setId(1);
		image.setUrlImg("http");
		image.setDirImg("/directory");
	}
	
	@Test
	void testBasic() throws Exception {
		assertNotNull(image);
		assertEquals(1,image.getId());
		assertEquals("http",image.getUrlImg());
		assertEquals("/directory",image.getDirImg());
	}
	
	@Test
	void testNoArgsConstructor() throws Exception {
		Image newImage = new Image();
		assertNotNull(newImage);
		assertNull(newImage.getId());
	}

}
