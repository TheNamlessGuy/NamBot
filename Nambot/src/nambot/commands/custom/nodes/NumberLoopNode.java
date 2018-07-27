package nambot.commands.custom.nodes;

import static nambot.helpers.Number.isInt;

public class NumberLoopNode extends Node {
	private Node amount;
	private Node var;
	private Node exprs;

	public NumberLoopNode(Node amount, Node var, Node exprs) {
		this.amount = amount;
		this.var = var;
		this.exprs = exprs;
	}

	@Override
	protected void run(RunInstance ri) {
		String loops = amount.getValue(ri);
		String varname = var.getValue(ri);
		if (loops != null && isInt(loops) && varname != null) {
			int i = Integer.parseInt(loops);
			for (int j = 0; j < i; ++j) {
				ri.vars.put(varname, Integer.toString(j));
				exprs.run(ri);
			}
		}
	}

	@Override
	protected String getValue(RunInstance ri) {
		return null;
	}
}