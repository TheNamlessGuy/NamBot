package nambot.main;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nambot.helpers.settings.GuildSettings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Send {
	public static void typing(MessageChannel mc) {
		mc.sendTyping().queue();
	}

	public static void text(MessageChannel mc, String msg) {
		if (msg.length() < 1) {
			mc.sendMessage("`null`").queue();
		} else if (msg.length() > 1999) {
			mc.sendMessage("Resulting message is over 2000 characters in length, cannot be sent").queue();
		} else {
			mc.sendMessage(msg).queue();
		}
	}

	public static Message rtext(MessageChannel mc, String msg) {
		if (msg.length() < 1) {
			return mc.sendMessage("`null`").complete();
		} else if (msg.length() > 1999) {
			return mc.sendMessage("Resulting message is over 2000 characters in length, cannot be sent").complete();
		}
		return mc.sendMessage(msg).complete();
	}

	public static void embed(MessageChannel mc, MessageEmbed embed) {
		mc.sendMessage(embed).queue();
	}

	public static void image(MessageChannel mc, byte[] data, String file, String msg) {
		Message m = null;
		if (msg != null && !msg.equals("")) {
			m = new MessageBuilder().append(msg).build();
		}
		mc.sendFile(data, file, m).queue();
	}

	public static void log(GuildSettings gs, MessageEmbed embed) {
		if (gs.logChannel != null) {
			Send.embed(gs.logChannel, embed);
		}
	}

	public static void error(MessageChannel mc, Throwable e) {
		if (e instanceof InvocationTargetException) {
			Send.error(mc, e.getCause());
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("```").append(e.getClass().getName()).append(": ").append(e.getLocalizedMessage()).append('\n');
			short n = 0;
			for (StackTraceElement ste : e.getStackTrace()) {
				if (ste.getClassName().startsWith("nambot.")) {
					sb.append("  at ").append(ste.getClassName()).append('.').append(ste.getMethodName()).append('(').append(ste.getFileName()).append(':')
							.append(ste.getLineNumber()).append(")\n");
					if (++n > 2) {
						break;
					}
				}
			}
			sb.append("```");
			Send.text(mc, sb.toString());
		}
	}

	public static void reaction(MessageChannel mc, String messageID, String reaction) {
		mc.getMessageById(messageID).complete().addReaction(reaction).queue();
	}

	public static void list(MessageChannel mc, int page, List<String> list, String title, int pageSize) {
		list(mc, page, list, title, pageSize, (sb, s, i) -> sb.append(s), "\n");
	}

	public static void list(MessageChannel mc, int page, List<String> list, String title, int pageSize, String separator) {
		list(mc, page, list, title, pageSize, (sb, s, i) -> sb.append(s), separator);
	}

	public static void list(MessageChannel mc, int page, List<String> list, String title, int pageSize, ListFunction lf) {
		list(mc, page, list, title, pageSize, lf, "\n");
	}

	public static void list(MessageChannel mc, int page, List<String> list, String title, int pageSize, ListFunction lf, String separator) {
		StringBuilder sb = new StringBuilder();
		int entry = (page * pageSize) - pageSize;

		for (int i = entry; i < entry + pageSize; ++i) {
			if (i >= list.size()) {
				break;
			}
			lf.apply(sb, list.get(i), i).append(separator);
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - separator.length());
		}

		Send.embed(mc,
				new EmbedBuilder().setColor(Color.gray).setTitle(title + " (" + page + '/' + (((list.size() - 1) / pageSize) + 1) + ")").setDescription(sb.toString()).build());
	}

	public static interface ListFunction {
		StringBuilder apply(StringBuilder sb, String s, int i);
	}
}