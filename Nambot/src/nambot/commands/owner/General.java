package nambot.commands.owner;

import static nambot.helpers.General.getNamMember;
import static nambot.helpers.Number.isInt;

import java.io.IOException;

import nambot.commands.user.Help;
import nambot.main.IO;
import nambot.main.NamBot;
import nambot.main.Send;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class General {
	public static void cmd_exit(MessageReceivedEvent e, String params) {
		Send.text(e.getChannel(), "Exiting...");
		NamBot.exit();
	}

	public static void cmd_save(MessageReceivedEvent e, String params) throws IOException {
		Send.text(e.getChannel(), "Saving...");
		IO.save();
	}

	public static void cmd_addexp(MessageReceivedEvent e, String params) {
		if (!isInt(params)) {
			Help.sendCommandUser(e.getChannel(), "addexp");
			return;
		}

		getNamMember(e.getMember()).addEXP(Integer.parseInt(params));
		nambot.commands.user.General.cmd_level(e, params);
	}
}