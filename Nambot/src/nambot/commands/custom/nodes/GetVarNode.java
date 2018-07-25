package nambot.commands.custom.nodes;

public class GetVarNode extends Node {
	private String name;

	public GetVarNode(Node name) {
		this.name = ((ConstantNode) name).getValue();
	}

	GetVarNode(String name) {
		this.name = name;
	}

	@Override
	protected String getValue(RunInstance ri) {
		if (ri.vars.containsKey(name)) {
			return ri.vars.get(name);
		}
		return null;
	}
}