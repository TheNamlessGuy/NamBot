package nambot.commands.custom.nodes;

public class ErrorNode extends Node {
	private String msg;

	public ErrorNode(String msg) {
		this.msg = msg;
	}

	@Override
	protected void run(RunInstance ri) {
		ri.stdout.setLength(0);
		ri.stdout.append(msg);
	}

	@Override
	protected String getValue(RunInstance ri) {
		return msg;
	}
}