import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;



public class CanvasApiClient {
	private static String BASE_URL;
	private final String token;
	private final OkHttpClient client;

	public CanvasApiClient(String link, String token) {
		this.token = token;
		this.BASE_URL = link + "/api/v1/";
		this.client = new OkHttpClient.Builder()
				.addInterceptor(new AuthInterceptor(token))
				.build();
	}

	public List<Assignment> getAssignments() throws IOException {
		String url = BASE_URL + "courses/278/assignments";
		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()){
			throw new IOException("Unexpected code " + response);
		}
		String responseBody = response.body().string();
		String cleanedResponse = removeHtmlTags(responseBody);
		Gson gson = new Gson();
		Assignment[] assignments = gson.fromJson(cleanedResponse, Assignment[].class);
		return Arrays.asList(assignments);
	}

	public static String removeHtmlTags(String html) {
		return html.replaceAll("\\\\u003c.*?\\\\u003e", "");
	}
}
