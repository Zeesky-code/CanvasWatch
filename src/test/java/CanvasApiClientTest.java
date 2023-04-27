import io.github.cdimascio.dotenv.Dotenv;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class CanvasApiClientTest {
	//load DB variables
	static Dotenv dotenv = Dotenv.load();
	static final String TOKEN = dotenv.get("TOKEN");
	static final String LINK = dotenv.get("LINK");


	// setting up streams to get console output
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}
	@Test
	public void TestESTUCanvas() throws IOException {
		CanvasApiClient client = new CanvasApiClient(LINK, TOKEN);
		List<Course> result = client.getCourses();
		Assert.assertNotNull(result);
	}
	@Test
	public void TestError(){
		String url = "https://doesn'texist.com";
		CanvasApiClient client = new CanvasApiClient(url, TOKEN);
		//test that exception is thrown for invalid URL
		Assert.assertThrows(IOException.class, () -> {
			List<Course> result = client.getCourses();
		});
	}
	@Test
	public void TestEmptyArgs(){
		String url = "";
		CanvasApiClient client = new CanvasApiClient(url, TOKEN);
		//test that exception is thrown for invalid URL
		Assert.assertThrows(IllegalArgumentException.class, () -> {
			List<Course> result = client.getCourses();
		});
	}
	@Test
	public void TestEmptyToken(){
		String url = "https://canvas.instructure.com";
		CanvasApiClient client = new CanvasApiClient(url, "");
		//test that exception is thrown for invalid URL
		Assert.assertThrows(IOException.class, () -> {
			List<Course> result = client.getCourses();
		});
	}
	@Test
	public void TestErrorMessage(){
		String url = "https://doesn'texist.com";
		CanvasApiClient client = new CanvasApiClient(url, TOKEN);
		//test that exception is thrown for invalid URL and check exception text
		try {
			List<Course> result = client.getCourses();
		} catch (IOException e) {
			Assert.assertThat(e.getMessage(), containsString("No such host is known (doesn'texist.com)"));
		}
	}
}
