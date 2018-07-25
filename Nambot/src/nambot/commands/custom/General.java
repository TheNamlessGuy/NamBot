package nambot.commands.custom;

import static nambot.helpers.General.getGuildSettings;

import nambot.commands.custom.parser.Parser;
import nambot.commands.user.Help;
import nambot.main.Send;
import nambot.settings.GuildSettings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class General {
	public static void cmd_cc(MessageReceivedEvent e, String param) {
		cmd_customcommand(e, param);
	}

	public static void cmd_customcommand(MessageReceivedEvent e, String param) {
		String[] params = param.split(" ", 2);
		if (params.length < 2) {
			params = new String[] { params[0], "" };
		}

		switch (params[0]) {
		case "c":
		case "create":
			create(e, params[1]);
			break;
		case "d":
		case "delete":
			delete(e, params[1]);
			break;
		case "e":
		case "edit":
			edit(e, params[1]);
			break;
		case "s":
		case "show":
			show(e, params[1]);
			break;
		case "t":
		case "test":
			test(e, params[1]);
			break;
		case "h":
		case "help":
			help(e);
			break;
		default:
			Help.sendCommandUser(e.getChannel(), "customcommand");
			break;
		}
	}

	private static void create(MessageReceivedEvent e, String param) {
		String[] params = param.split(" ", 2);
		if (params.length != 2) {
			Help.sendCommandUser(e.getChannel(), "customcommand create");
			return;
		}

		GuildSettings gs = getGuildSettings(e.getGuild());
		if (gs.customCommands.containsKey(params[0])) {
			Send.text(e.getChannel(), "A command of the name '" + params[0] + "' already exists in this server");
			return;
		}

		gs.customCommands.put(params[0], new CustomCommand(params[0], params[1], e.getAuthor().getId(), e.getGuild().getId()));
		Send.text(e.getChannel(), "Command '" + params[0] + "' created");
	}

	private static void delete(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "customcommand delete");
			return;
		}

		GuildSettings gs = getGuildSettings(e.getGuild());
		if (!gs.customCommands.containsKey(param)) {
			Send.text(e.getChannel(), "No command by the name '" + param + "' found");
			return;
		}

		if (!gs.customCommands.get(param).ownerID.equals(e.getAuthor().getId()) && !gs.isAdmin(e.getMember())) {
			Send.text(e.getChannel(), "You are not the owner of command '" + param + "'");
			return;
		}
		gs.customCommands.remove(param);
		Send.text(e.getChannel(), "Command '" + param + "' removed");
	}

	private static void edit(MessageReceivedEvent e, String param) {
		String[] params = param.split(" ", 2);
		if (params.length != 2) {
			Help.sendCommandUser(e.getChannel(), "customcommand edit");
			return;
		}

		GuildSettings gs = getGuildSettings(e.getGuild());
		if (!gs.customCommands.containsKey(params[0])) {
			Send.text(e.getChannel(), "No command by the name '" + params[0] + "' found");
			return;
		}

		if (!gs.customCommands.get(params[0]).ownerID.equals(e.getAuthor().getId()) && !gs.isAdmin(e.getMember())) {
			Send.text(e.getChannel(), "You are not the owner of command '" + params[0] + "'");
			return;
		}

		gs.customCommands.get(params[0]).update(params[1]);
		Send.text(e.getChannel(), "Command '" + param + "' edited");
	}

	private static void show(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "customcommand show");
			return;
		}

		GuildSettings gs = getGuildSettings(e.getGuild());
		if (!gs.customCommands.containsKey(param)) {
			Send.text(e.getChannel(), "No command by the name '" + param + "' found");
			return;
		}

		Send.text(e.getChannel(), gs.customCommands.get(param).contents);
	}

	private static void test(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "customcommand test");
			return;
		}

		Send.text(e.getChannel(), new Parser().parse(param).start(e, "p1 p2 p3 p4"));
	}

	private static void help(MessageReceivedEvent e) {
		Send.text(e.getChannel(), "https://github.com/TheNamlessGuy/NamBot/blob/master/Nambot/src/nambot/commands/custom/Specification.md");
	}
}