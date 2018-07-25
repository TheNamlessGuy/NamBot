package nambot.commands.custom.nodes;

public class ConstantNode extends Node {
	private String value;

	public ConstantNode(String value) {
		if (value.startsWith("\"") && value.endsWith("\"")) {
			this.value = value.substring(1, value.length() - 1);
		} else {
			this.value = value;
		}
	}

	@Override
	protected void run(RunInstance ri) {
		ri.stdout.append(getValue());
	}

	@Override
	protected String getValue(RunInstance ri) {
		return getValue();
	}

	public String getValue() {
		return value;
	}
}