package nambot.commands.admin;

import static nambot.helpers.DiscordTransformers.getAsMention;
import static nambot.helpers.Number.clamp;
import static nambot.helpers.Number.isInt;

import java.awt.Color;

import nambot.commands.user.Help;
import nambot.main.Send;
import nambot.settings.GuildSettings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class General {
	public static void cmd_setprefix(MessageReceivedEvent e, String param, GuildSettings gs) {
		if (param.equals("") || param.contains(" ") || param.length() > 5) {
			Help.sendCommandAdmin(e.getChannel(), "setprefix");
			return;
		}

		Send.log(gs, e.getChannel(), new EmbedBuilder().setColor(Color.green).setTitle("Prefix change")
				.setDescription("Prefix changed from '" + gs.prefix + "' to '" + param + "'").build());
		gs.prefix = param;
	}

	public static void cmd_setlogchannel(MessageReceivedEvent e, String param, GuildSettings gs) {
		gs.logChannel = e.getChannel();
		Send.log(gs, e.getChannel(), new EmbedBuilder().setColor(Color.green).setTitle("Log channel change (" + e.getChannel().getName() + ")")
				.setDescription("Set log channel to " + getAsMention(e.getChannel())).build());
	}

	public static void cmd_clear(MessageReceivedEvent e, String param, GuildSettings gs) {
		int amount = 26;
		if (!param.equals("") && isInt(param)) {
			amount = clamp(1, Integer.parseInt(param) + 1, 101);
		}

		int i = 0;
		for (Message m : e.getChannel().getIterableHistory()) {
			m.delete().queue();
			if (++i >= amount) {
				break;
			}
		}

		Send.log(gs, e.getChannel(), new EmbedBuilder().setColor(Color.yellow).setTitle("Clear command (" + e.getChannel().getName() + ")")
				.setDescription("Cleared " + (amount - 1) + " messages in channel " + getAsMention(e.getChannel())).build());
	}
}