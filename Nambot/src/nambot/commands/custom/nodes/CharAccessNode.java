package nambot.commands.custom.nodes;

public class CharAccessNode extends GetVarNode {
	public CharAccessNode(String value) {
		super(value.substring(0, value.length() - 1));
	}
}