package nambot.commands.custom.nodes;

import static nambot.globals.Vars.random;

import java.util.List;

public class RandomNode extends Node {
	private List<Node> nodes;

	public RandomNode(Node node) {
		this.nodes = ((ParamListNode) node).get();
	}

	@Override
	protected String getValue(RunInstance ri) {
		return nodes.get(random.nextInt(nodes.size())).getValue(ri);
	}
}