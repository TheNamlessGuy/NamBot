package nambot.commands.custom.nodes;

import static nambot.helpers.Number.isInt;

public class ArrayAccessNode extends Node {
	private Node val;
	private Node index;
	private boolean characcess;

	public ArrayAccessNode(Node val, Node index, boolean characcess) {
		this.val = val;
		this.index = index;
		this.characcess = characcess;
	}

	@Override
	protected String getValue(RunInstance ri) {
		String value = val.getValue(ri);
		String in = index.getValue(ri);

		if (value != null && isInt(in)) {
			String[] values = value.split((characcess ? "" : " "));
			int i = Integer.parseInt(in);
			if (i < values.length) {
				return values[i];
			}
		}
		return null;
	}
}