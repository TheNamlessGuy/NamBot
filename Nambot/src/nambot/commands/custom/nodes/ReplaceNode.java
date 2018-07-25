package nambot.commands.custom.nodes;

public class ReplaceNode extends Node {
	private Node val;
	private Node toReplace;
	private Node replaceWith;

	public ReplaceNode(Node val, Node toReplace, Node replaceWith) {
		this.val = val;
		this.toReplace = toReplace;
		this.replaceWith = replaceWith;
	}

	@Override
	protected String getValue(RunInstance ri) {
		String value = val.getValue(ri);
		String tr = toReplace.getValue(ri);
		String rw = replaceWith.getValue(ri);
		if (value == null || tr == null || rw == null) {
			return null;
		}
		return value.replaceAll(tr, rw);
	}
}