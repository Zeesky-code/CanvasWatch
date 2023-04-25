import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.util.List;

@Command(
		name = "Canvas Watch",
		mixinStandardHelpOptions = true,
		version = "1.0",
		description = "A program to watch your canvas courses and assignments."
)

public class CanvasWatch implements Runnable{
	@Parameters(index = "0", description = "Canvas access token")
	private String token;

	@Parameters(index = "1", description = "Canvas university link")
	private String link;

	public static void main(String[] args) {
		new CommandLine(new CanvasWatch()).execute(args);
	}

	@Override
	public void run() {
		try {
			CanvasApiClient client = new CanvasApiClient(link, token);
			List<Course> courses = client.getCourses();
			List<Assignment> assignments = client.getAssignments();
			for (Course course : courses) {
				System.out.println(course.getName() + " " + course.getId());
			}
			for (Assignment assignment : assignments) {
				System.out.println(assignment.getName());
				System.out.println(assignment.getDescription());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
