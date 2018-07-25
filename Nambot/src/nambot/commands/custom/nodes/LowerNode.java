package nambot.commands.custom.nodes;

public class LowerNode extends Node {
	private Node val;

	public LowerNode(Node val) {
		this.val = val;
	}

	@Override
	protected String getValue(RunInstance ri) {
		String value = val.getValue(ri);
		if (value != null) {
			return value.toLowerCase();
		}
		return null;
	}
}