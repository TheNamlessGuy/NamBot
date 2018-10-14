package nambot.commands.user;

import static nambot.helpers.General.getAuthorOrMentionedMember;
import static nambot.helpers.General.getNamMember;

import java.awt.Color;

import nambot.helpers.settings.NamMember;
import nambot.main.Send;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class General {
	public static void cmd_ping(MessageReceivedEvent e, String params) {
		MessageAction ma = e.getChannel().sendMessage("Pong!");
		long pre = System.currentTimeMillis();
		Message m = ma.complete();
		long post = System.currentTimeMillis();
		m.editMessage("Pong! | " + Long.toString(post - pre) + " ms").queue();
	}

	public static void cmd_avatar(MessageReceivedEvent e, String params) {
		String url = null;
		Color c = null;
		if (params.equals("server")) {
			url = e.getGuild().getIconUrl();
			c = Color.gray;
		} else {
			Member m = e.getGuild().getMemberById(getAuthorOrMentionedMember(0, e));
			url = m.getUser().getAvatarUrl();
			c = m.getColor();
		}

		Send.embed(e.getChannel(), new EmbedBuilder().setTitle("Link to avatar", url).setColor(c).setImage(url).build());
	}

	public static void cmd_level(MessageReceivedEvent e, String params) {
		NamMember nm = getNamMember(e.getGuild().getMemberById(getAuthorOrMentionedMember(0, e)));
		if (nm == null) {
			Send.text(e.getChannel(), "Bots don't have levels");
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Level: ").append(nm.level).append('\n');
		sb.append("EXP: ").append(nm.EXP).append('/').append(nm.EXP_threshold).append('\n');
		sb.append("Total EXP: ").append(nm.getAvailableEXP()).append('\n');

		Send.embed(e.getChannel(), new EmbedBuilder().setColor(nm.getColor(e.getGuild())).setTitle("Level info for " + nm.getName()).setDescription(sb.toString()).build());
	}

	public static void cmd_flip(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "flip");
			return;
		}

		if (param.startsWith("--reverse")) {
			param = reverse(param.substring(9).trim());
		}
		Send.text(e.getChannel(), flip(param));
	}

	public static void cmd_reverse(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "reverse");
			return;
		}

		if (param.startsWith("--flip")) {
			param = flip(param.substring(6).trim());
		}
		Send.text(e.getChannel(), reverse(param));
	}

	public static void cmd_vote(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "vote");
			return;
		}

		Message m = Send.rtext(e.getChannel(), param);
		m.addReaction("👍").complete();
		m.addReaction("👎").complete();
	}

	private static String[] fliparray = { "ɐ", "q", "ɔ", "p", "ǝ", "ɟ", "ƃ", "ɥ", "ᴉ", "ɾ", "ʞ", "l", "ɯ", "u", "o", "d", "b", "ɹ", "s", "ʇ", "n", "ʌ", "ʍ", "x", "ʎ", "z" };

	private static String flip(String s) {
		s = s.toLowerCase();
		StringBuilder retval = new StringBuilder();
		for (char c : s.toCharArray()) {
			int i = c - 97;
			if (i < 0 || i > 25)
				retval.append(c);
			else
				retval.append(fliparray[i]);
		}
		return retval.toString();
	}

	private static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}
}