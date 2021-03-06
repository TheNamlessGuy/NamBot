package nambot.commands.user;

import static nambot.helpers.General.getGuildSettings;
import static nambot.helpers.General.isNamless;
import static nambot.helpers.Number.isInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nambot.helpers.settings.GuildSettings;
import nambot.main.Send;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Help {
	private static Map<String, String> userHelp;
	private static Map<String, String> adminHelp;
	private static Map<String, String> ownerHelp;

	private static List<String> shorthandCommands;

	public static void cmd_help(MessageReceivedEvent e, String param) {
		GuildSettings gs = getGuildSettings(e.getGuild());

		if (param.equals("help")) {
			sendCommand(e.getChannel(), "help <page number>");
		} else if (isNamless(e.getMember())) {
			if (param.equals("")) {
				sendListOwner(e.getChannel(), 1);
			} else if (isInt(param)) {
				sendListOwner(e.getChannel(), Integer.parseInt(param));
			} else {
				sendCommandOwner(e.getChannel(), param);
			}
		} else if (gs.isAdmin(e.getMember())) {
			if (param.equals("")) {
				sendListAdmin(e.getChannel(), 1);
			} else if (isInt(param)) {
				sendListAdmin(e.getChannel(), Integer.parseInt(param));
			} else {
				sendCommandAdmin(e.getChannel(), param);
			}
		} else { // Regular user
			if (param.equals("")) {
				sendListUser(e.getChannel(), 1);
			} else if (isInt(param)) {
				sendListUser(e.getChannel(), Integer.parseInt(param));
			} else {
				sendCommandUser(e.getChannel(), param);
			}
		}
	}

	private static void sendListOwner(MessageChannel mc, int page) {
		List<String> list = new ArrayList<>();
		list.addAll(userHelp.keySet());
		list.addAll(adminHelp.keySet());
		list.addAll(ownerHelp.keySet());
		list.removeAll(shorthandCommands);
		list.sort(null);
		list.add(0, "help");
		list = list.stream().filter((x) -> !x.contains(" ")).collect(Collectors.toList());
		Send.list(mc, page, list, "Command list", 10);
	}

	private static void sendListAdmin(MessageChannel mc, int page) {
		List<String> list = new ArrayList<>();
		list.addAll(userHelp.keySet());
		list.addAll(adminHelp.keySet());
		list.removeAll(shorthandCommands);
		list.sort(null);
		list.add(0, "help");
		list = list.stream().filter((x) -> !x.contains(" ")).collect(Collectors.toList());
		Send.list(mc, page, list, "Command list", 10);
	}

	private static void sendListUser(MessageChannel mc, int page) {
		List<String> list = new ArrayList<>();
		list.addAll(userHelp.keySet());
		list.removeAll(shorthandCommands);
		list.sort(null);
		list.add(0, "help");
		list = list.stream().filter((x) -> !x.contains(" ")).collect(Collectors.toList());
		Send.list(mc, page, list, "Command list", 10);
	}

	public static void sendCommandOwner(MessageChannel mc, String function) {
		if (ownerHelp.containsKey(function)) {
			sendCommand(mc, ownerHelp.get(function));
		} else {
			sendCommandAdmin(mc, function);
		}
	}

	public static void sendCommandAdmin(MessageChannel mc, String function) {
		if (adminHelp.containsKey(function)) {
			sendCommand(mc, adminHelp.get(function));
		} else {
			sendCommandUser(mc, function);
		}
	}

	public static void sendCommandUser(MessageChannel mc, String function) {
		if (userHelp.containsKey(function)) {
			sendCommand(mc, userHelp.get(function));
		} else {
			Send.text(mc, "Could not find help entry for function '" + function + "'");
		}
	}

	private static void sendCommand(MessageChannel mc, String helpText) {
		Send.text(mc, "Usage: `" + helpText + "`\n\n```() => Optional\n<> => What to put\n[] => Choose one (separated by '|')```");
	}

	static {
		userHelp = new HashMap<>();
		adminHelp = new HashMap<>();
		ownerHelp = new HashMap<>();

		shorthandCommands = Arrays.asList("cc", "inv");

		/* User */
		userHelp.put("info", "info ([server|<user>|<role>|<channel>|<custom emote>|role <role name>|user <user name>])");
		userHelp.put("ping", "ping");
		userHelp.put("joke", "joke");
		userHelp.put("vote", "vote <phrase>");
		userHelp.put("avatar", "avatar (<user>|server)");
		userHelp.put("level", "level (<user>)");
		userHelp.put("flip", "flip (--reverse) <phrase>");
		userHelp.put("reverse", "reverse (--flip) <phrase>");
		userHelp.put("generateavatar", "generateavatar [robot|robothead|alien|cat|face]");
		userHelp.put("zalgo", "zalgo (--max|--mid|--min) <phrase>");
		userHelp.put("buy", "buy [list (<page>)|(<amount>) <item>]");
		userHelp.put("inventory", "inventory (<page>)");
		userHelp.put("inv", userHelp.get("inventory"));
		userHelp.put("drop", "drop [(<amount>) <item>|--all]");
		userHelp.put("pat", "pat <user>");
		userHelp.put("stab", "stab <user>");
		userHelp.put("slap", "slap <user>");
		userHelp.put("hug", "hug <user>");
		userHelp.put("ship", "ship <user> <user>");
		userHelp.put("civilwar", "civilwar <user> <user text> | <user> <user text>");
		setCustomCommandHelpTexts();

		userHelp.put("use", "use (<amount>) <item> (<parameters>)");
		userHelp.put("use tomato", "use (<amount>) tomato <user mention>");
		userHelp.put("use rock", "use (<amount>) rock <user mention>");
		userHelp.put("use nicknamechanger", "use nicknamechanger <user mention> <new name>");

		/* Admin */
		adminHelp.put("setprefix", "setprefix <prefix>");
		adminHelp.put("setlogchannel", "setlogchannel");
		adminHelp.put("clear", "clear (<1-100>)");
		adminHelp.put("adminrole", "adminrole [add <role>|remove <role>|list (<page>)]");
		adminHelp.put("reacttorole", "reacttorole [channel [set|remove]|add <role> <ID of message that should have the reaction attached> <reaction>|remove <reaction>]");

		/* Owner */
		ownerHelp.put("exit", "exit");
		ownerHelp.put("save", "save");
		ownerHelp.put("todo", "todo [add <value>|delete <number>|list (<page>)]");
	}

	private static void setCustomCommandHelpTexts() {
		userHelp.put("customcommand", "customcommand [h(elp)|c(reate)|d(elete)|e(dit)|s(how)|t(est)|i(nfo)|l(ist)]");
		userHelp.put("cc", userHelp.get("customcommand"));

		userHelp.put("customcommand create", "customcommand create <command name> <contents>");
		userHelp.put("customcommand c", userHelp.get("customcommand create"));
		userHelp.put("cc create", userHelp.get("customcommand create"));
		userHelp.put("cc c", userHelp.get("customcommand create"));

		userHelp.put("customcommand delete", "customcommand delete <command name>");
		userHelp.put("customcommand d", userHelp.get("customcommand delete"));
		userHelp.put("cc delete", userHelp.get("customcommand delete"));
		userHelp.put("cc d", userHelp.get("customcommand delete"));

		userHelp.put("customcommand edit", "customcommand edit <command name> <contents>");
		userHelp.put("customcommand e", userHelp.get("customcommand edit"));
		userHelp.put("cc edit", userHelp.get("customcommand edit"));
		userHelp.put("cc e", userHelp.get("customcommand edit"));

		userHelp.put("customcommand show", "customcommand show <command name>");
		userHelp.put("customcommand s", userHelp.get("customcommand show"));
		userHelp.put("cc show", userHelp.get("customcommand show"));
		userHelp.put("cc s", userHelp.get("customcommand show"));

		userHelp.put("customcommand test", "customcommand test <contents>");
		userHelp.put("customcommand t", userHelp.get("customcommand test"));
		userHelp.put("cc test", userHelp.get("customcommand test"));
		userHelp.put("cc t", userHelp.get("customcommand test"));

		userHelp.put("customcommand info", "customcommand info <command name>");
		userHelp.put("customcommand i", userHelp.get("customcommand info"));
		userHelp.put("cc info", userHelp.get("customcommand info"));
		userHelp.put("cc i", userHelp.get("customcommand info"));

		userHelp.put("customcommand list", "customcommand list ([server|<user>]) (<page>)");
		userHelp.put("customcommand l", userHelp.get("customcommand list"));
		userHelp.put("cc list", userHelp.get("customcommand list"));
		userHelp.put("cc l", userHelp.get("customcommand list"));

		userHelp.put("customcommand help", "customcommand help");
		userHelp.put("customcommand h", userHelp.get("customcommand help"));
		userHelp.put("cc help", userHelp.get("customcommand help"));
		userHelp.put("cc h", userHelp.get("customcommand help"));
	}
}