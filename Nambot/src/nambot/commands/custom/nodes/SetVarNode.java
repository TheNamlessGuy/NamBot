package nambot.commands.custom.nodes;

public class SetVarNode extends Node {
	private String name;
	private Node value;

	public SetVarNode(Node name, Node value) {
		this.name = ((ConstantNode) name).getValue();
		this.value = value;
	}

	@Override
	protected void run(RunInstance ri) {
		ri.vars.put(name, value.getValue(ri));
	}

	@Override
	protected String getValue(RunInstance ri) {
		return null;
	}

}