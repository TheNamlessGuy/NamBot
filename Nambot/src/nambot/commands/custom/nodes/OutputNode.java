package nambot.commands.custom.nodes;

public class OutputNode extends Node {
	private String output;

	public OutputNode(String output) {
		this.output = output;
	}

	@Override
	protected String getValue(RunInstance ri) {
		return output;
	}
}