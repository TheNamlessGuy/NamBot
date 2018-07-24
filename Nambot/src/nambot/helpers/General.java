package nambot.helpers;

import static nambot.globals.Vars.guildSettings;
import static nambot.globals.Vars.members;
import static nambot.globals.Vars.random;

import java.util.List;

import nambot.globals.SNOWFLAKES;
import nambot.settings.GuildSettings;
import nambot.settings.NamMember;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class General {
	public static GuildSettings getGuildSettings(Guild g) {
		if (guildSettings.containsKey(g.getId())) {
			return guildSettings.get(g.getId());
		}

		GuildSettings gs = new GuildSettings(g);
		guildSettings.put(g.getId(), gs);
		return gs;
	}

	public static boolean isNamless(Member m) {
		return m.getUser().getId().equals(SNOWFLAKES.U_NAMLESS);
	}

	public static String getAuthorOrMentionedMember(int n, MessageReceivedEvent e) {
		List<Member> l = e.getMessage().getMentionedMembers();
		if (l.size() > n) {
			return l.get(n).getUser().getId();
		}
		return e.getAuthor().getId();
	}

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String randomString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	public static NamMember getNamMember(Member m) {
		if (m.getUser().isBot()) {
			return null;
		}

		if (members.containsKey(m.getUser().getId())) {
			return members.get(m.getUser().getId());
		}

		NamMember nm = new NamMember(m.getUser());
		members.put(m.getUser().getId(), nm);
		return nm;
	}
}