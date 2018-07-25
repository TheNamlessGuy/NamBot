package nambot.commands.custom.nodes;

import java.util.ArrayList;
import java.util.List;

public class ParamListNode extends Node {
	private List<Node> nodes;

	public ParamListNode(List<Node> nodes) {
		this.nodes = nodes;
	}

	public ParamListNode(Node node) {
		this.nodes = new ArrayList<>();
		nodes.add(node);
	}

	@Override
	protected void run(RunInstance ri) {
		for (Node n : nodes) {
			n.run(ri);
		}
	}

	@Override
	protected String getValue(RunInstance ri) {
		return null;
	}

	public void add(int i, Node n) {
		nodes.add(i, n);
	}

	public List<Node> get() {
		return nodes;
	}
}