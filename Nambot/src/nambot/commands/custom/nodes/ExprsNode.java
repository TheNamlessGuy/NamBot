package nambot.commands.custom.nodes;

import java.util.ArrayList;
import java.util.List;

public class ExprsNode extends Node {
	private List<Node> nodes;

	public ExprsNode(List<Node> nodes) {
		this.nodes = nodes;
	}

	public ExprsNode(Node node) {
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
}