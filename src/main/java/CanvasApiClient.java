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
	private final int courseID;
	private final OkHttpClient client;

	public CanvasApiClient(String link, String token) {
		this(link, token, 0);
	}

	public CanvasApiClient(String link, String token, int courseID) {
		this.token = token;
		this.courseID = courseID;
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
			throw new IOException("An error occurred, please try with another URL.");
		}
		String responseBody = response.body().string();
		String cleanedResponse = removeHtmlTags(responseBody);
		Gson gson = new Gson();
		Assignment[] assignments = gson.fromJson(cleanedResponse, Assignment[].class);
		return Arrays.asList(assignments);
	}
	public List<Course> getCourses() throws IOException{
		String url = BASE_URL + "courses/"+courseID+"/assignments";
		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()){
			throw new IOException("An error occurred, please try with another URL.");
		}
		String responseBody = response.body().string();
		Gson gson = new Gson();
		Course[] courses = gson.fromJson(responseBody, Course[].class);
		return Arrays.asList(courses);
	}
	public List<Quiz> getQuizzes() throws IOException{
		String url = BASE_URL + "courses/"+courseID+"/quizzes";
		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()){
			throw new IOException("An error occurred, please try with another URL.");
		}
		String responseBody = response.body().string();
		Gson gson = new Gson();
		Quiz[] quizzes = gson.fromJson(responseBody, Quiz[].class);
		return Arrays.asList(quizzes);
	}
	public static String removeHtmlTags(String html) {
		return html.replaceAll("\\\\u003c.*?\\\\u003e", "");
	}
}
