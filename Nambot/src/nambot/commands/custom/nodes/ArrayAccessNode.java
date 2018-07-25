package nambot.commands.custom.nodes;

public class ArrayAccessNode extends Node {
	private Node var;
	private int index;
	private boolean characcess;

	public ArrayAccessNode(String value) {
		String varname = value.replaceAll("\\d", "");
		this.index = Integer.parseInt(value.replaceAll("\\D", ""));

		if (varname.endsWith("~")) {
			characcess = true;
			varname = varname.substring(0, varname.length() - 1);
		}

		var = new GetVarNode(varname);
	}

	@Override
	protected String getValue(RunInstance ri) {
		String value = var.getValue(ri);
		if (value != null) {
			String[] values = value.split((characcess ? "" : " "));
			if (index < values.length) {
				return values[index];
			}
		}
		return null;
	}
}