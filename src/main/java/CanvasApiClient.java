import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CanvasApiClient {private static final String BASE_URL = "https://estuoys.eskisehir.edu.tr/api/v1/";
	private final String token;
	private final OkHttpClient client;

	public CanvasApiClient(String token) {
		this.token = token;
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
		System.out.println(response.headers());
		String responseBody = response.body().string();
		Gson gson = new Gson();
		Assignment[] assignments = gson.fromJson(responseBody, Assignment[].class);
		return Arrays.asList(assignments);
	}
}