import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.List;

@Command(
		name = "Canvas Watch",
		description = "It prints the assignments from a given Canvas course."
)

public class CanvasWatch implements Runnable{
	@CommandLine.Option(names = { "-t", "--token" }, required = true, description = "Canvas access token")
	private String token;

	public static void main(String[] args) {
		new CommandLine(new CanvasWatch()).execute(args);
	}

	@Override
	public void run() {
		try {
			CanvasApiClient client = new CanvasApiClient(token);
			List<Assignment> assignments = client.getAssignments();
			for (Assignment assignment : assignments) {
				System.out.println(assignment.getName());
				System.out.println(assignment.getDescription());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
