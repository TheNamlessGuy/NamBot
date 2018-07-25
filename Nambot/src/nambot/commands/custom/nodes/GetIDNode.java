package nambot.commands.custom.nodes;

import static nambot.helpers.General.isUserMention;

public class GetIDNode extends Node {
	private Node val;

	public GetIDNode(Node val) {
		this.val = val;
	}

	@Override
	protected String getValue(RunInstance ri) {
		String value = val.getValue(ri);
		if (value != null && isUserMention(value)) {
			return value.replaceAll("\\D", "");
		}
		return null;
	}
}
