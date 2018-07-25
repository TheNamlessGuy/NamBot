package nambot.commands.custom.nodes;

import java.util.List;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TopNode extends Node {
	private List<Node> nodes;

	public TopNode(List<Node> nodes) {
		this.nodes = nodes;
	}

	@Override
	public void run(RunInstance ri) {
	}

	public String start(MessageReceivedEvent e, String param) {
		RunInstance ri = initializeRunInstance(e, param);

		for (Node n : nodes) {
			n.run(ri);
		}

		return ri.stdout.toString();
	}

	@Override
	protected String getValue(RunInstance ri) {
		return null;
	}

	private RunInstance initializeRunInstance(MessageReceivedEvent e, String param) {
		RunInstance ri = new RunInstance();
		ri.vars.put("args", param);

		if (e == null) {
			return ri;
		}

		ri.guild = e.getGuild();
		ri.vars.put("caller", e.getAuthor().getAsMention());

		StringBuilder sb = new StringBuilder();
		for (Member m : e.getMessage().getMentionedMembers()) {
			sb.append(m.getAsMention()).append(' ');
		}
		ri.vars.put("mentions", sb.toString().trim());

		return ri;
	}
}
