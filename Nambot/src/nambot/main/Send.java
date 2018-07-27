package nambot.main;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nambot.settings.GuildSettings;
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

	public static void log(GuildSettings gs, MessageChannel mc, MessageEmbed embed) {
		if (gs.logChannel != null) {
			Send.embed(gs.logChannel, embed);
		} else {
			Send.embed(mc, embed);
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

	public static void list(MessageChannel mc, int page, List<String> list, String title, int pageSize) {
		list(mc, page, list, title, pageSize, "");
	}

	public static void list(MessageChannel mc, int page, List<String> list, String title, int pageSize, String prefix) {
		list(mc, page, list, title, pageSize, prefix, "\n");
	}

	public static void list(MessageChannel mc, int page, List<String> list, String title, int pageSize, String prefix, String separator) {
		StringBuilder sb = new StringBuilder();
		int entry = (page * pageSize) - pageSize;

		for (int i = entry; i < entry + pageSize; ++i) {
			if (i >= list.size()) {
				break;
			}
			sb.append(prefix).append(list.get(i)).append(separator);
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - separator.length());
		}

		Send.embed(mc, new EmbedBuilder().setColor(Color.gray).setTitle(title + " (" + page + '/' + (((list.size() - 1) / pageSize) + 1) + ")")
				.setDescription(sb.toString()).build());
	}
}