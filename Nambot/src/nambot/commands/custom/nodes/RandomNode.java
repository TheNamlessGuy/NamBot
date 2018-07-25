package nambot.commands.custom.nodes;

import static nambot.globals.Vars.random;

import java.util.ArrayList;
import java.util.List;

public class RandomNode extends Node {
	private List<Node> nodes;
	private boolean randomvalue;
	private boolean characcess;

	public RandomNode(Node node, boolean characcess) {
		if (node instanceof ParamListNode) {
			this.nodes = ((ParamListNode) node).get();
			randomvalue = false;
		} else {
			this.nodes = new ArrayList<>();
			this.nodes.add(node);
			randomvalue = true;
		}
		this.characcess = characcess;
	}

	@Override
	protected String getValue(RunInstance ri) {
		if (randomvalue) {
			String value = nodes.get(0).getValue(ri);
			if (value != null) {
				String[] values = value.split((characcess ? "" : " "));
				return values[random.nextInt(values.length)];
			}
			return null;
		}
		return nodes.get(random.nextInt(nodes.size())).getValue(ri);
	}
}