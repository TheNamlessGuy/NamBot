package nambot.commands.custom.nodes;

public class IfNode extends Node {
	private Node stmtNode;
	private Node ifNode;
	private Node elseNode;

	public IfNode(Node stmtNode, Node ifNode, Node elseNode) {
		this.stmtNode = stmtNode;
		this.ifNode = ifNode;
		this.elseNode = elseNode;
	}

	@Override
	protected void run(RunInstance ri) {
		if (stmtNode.getValue(ri).equals("true")) {
			ifNode.run(ri);
		} else if (elseNode != null) {
			elseNode.run(ri);
		}
	}

	@Override
	protected String getValue(RunInstance ri) {
		return null;
	}
}