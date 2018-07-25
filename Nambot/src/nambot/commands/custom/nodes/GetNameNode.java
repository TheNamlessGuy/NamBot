package nambot.commands.custom.nodes;

import static nambot.helpers.General.isUserMention;

import net.dv8tion.jda.core.entities.Member;

public class GetNameNode extends Node {
	private Node val;
	private boolean nickname;

	public GetNameNode(Node val, boolean nickname) {
		this.val = val;
		this.nickname = nickname;
	}

	@Override
	protected String getValue(RunInstance ri) {
		String value = val.getValue(ri);
		if (value != null && isUserMention(value)) {
			value = value.replaceAll("\\D", "");
			Member m = ri.guild.getMemberById(value);
			if (m != null) {
				if (nickname) {
					return m.getEffectiveName();
				}
				return m.getUser().getName();
			}
		}
		return null;
	}
}