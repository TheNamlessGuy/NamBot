package nambot.commands.custom.nodes;

public class LoopNode extends Node {
	private Node var;
	private Node lvar;
	private Node exprs;
	private boolean characcess;

	public LoopNode(Node var, Node lvar, Node exprs) {
		this.var = var;
		this.characcess = (var instanceof CharAccessNode);
		this.lvar = lvar;
		this.exprs = exprs;
	}

	@Override
	protected void run(RunInstance ri) {
		String[] value = var.getValue(ri).split((characcess ? "" : " "));
		String lvarname = lvar.getValue(ri);
		for (String s : value) {
			ri.vars.put(lvarname, s);
			exprs.run(ri);
		}
		ri.vars.remove(lvarname);
	}

	@Override
	protected String getValue(RunInstance ri) {
		return null;
	}
}