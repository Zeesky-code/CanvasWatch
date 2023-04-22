import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
		name = "Canvas Watch",
		description = "It prints the assignments from a given Canvas course."
)

public class CanvasWatch implements Runnable{

	public static void main(String[] args) {
		new CommandLine(new CanvasWatch()).execute(args);
	}

	@Override
	public void run() {
		System.out.println("Hello World!");
	}
}
