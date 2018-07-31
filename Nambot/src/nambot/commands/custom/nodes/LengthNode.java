package nambot.commands.custom.nodes;

public class LengthNode extends Node {
	private Node value;
	private boolean characcess;

	public LengthNode(Node value, boolean characcess) {
		this.value = value;
		this.characcess = characcess;
	}

	@Override
	protected String getValue(RunInstance ri) {
		String v = value.getValue(ri);
		if (v != null) {
			if (characcess) {
				return Integer.toString(v.length());
			}
			return Integer.toString(v.split(" ").length);
		}

		return "0";
	}
}