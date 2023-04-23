import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.List;

@Command(
		name = "Canvas Watch",
		mixinStandardHelpOptions = true,
		version = "1.0",
		description = "A program to watch your canvas courses and assignments."
)

public class CanvasWatch implements Runnable{
	@CommandLine.Option(names = {"-t", "--token"}, description = "Canvas access token", required = true)
	private String token;

	@CommandLine.Option(names = {"-l", "--link"}, description = "Canvas university link", required = true)
	private String link;


	public static void main(String[] args) {
		new CommandLine(new CanvasWatch()).execute(args);
	}

	@Override
	public void run() {
		try {
			CanvasApiClient client = new CanvasApiClient(link, token);
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
