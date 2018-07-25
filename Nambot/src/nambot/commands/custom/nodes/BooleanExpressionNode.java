package nambot.commands.custom.nodes;

public class BooleanExpressionNode extends Node {
	private Node lv;
	private String comparator;
	private Node rv;

	public BooleanExpressionNode(Node value, Node comparator, Node value2) {
		this.lv = value;
		this.comparator = ((ConstantNode) comparator).getValue();
		this.rv = value2;
	}

	@Override
	protected String getValue(RunInstance ri) {
		if (comparator.equals("==")) {
			return (lv.getValue(ri).equals(rv.getValue(ri))) ? "true" : "false";
		} else if (comparator.equals("!=")) {
			return (!lv.getValue(ri).equals(rv.getValue(ri))) ? "true" : "false";
		}
		return null;
	}
}