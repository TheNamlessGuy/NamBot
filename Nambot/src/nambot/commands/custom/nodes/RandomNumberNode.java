package nambot.commands.custom.nodes;

import static nambot.globals.Vars.random;
import static nambot.helpers.Number.isInt;

public class RandomNumberNode extends Node {
	private Node lval;
	private Node rval;

	public RandomNumberNode(Node lval, Node rval) {
		this.lval = lval;
		this.rval = rval;
	}

	@Override
	protected String getValue(RunInstance ri) {
		String ls = lval.getValue(ri);
		String rs = rval.getValue(ri);

		if (ls != null && rs != null && isInt(ls) && isInt(rs)) {
			int low = Integer.parseInt(ls);
			int high = Integer.parseInt(rs);
			return Integer.toString(random.nextInt(high + 1 - low) + low);
		}
		return null;
	}
}