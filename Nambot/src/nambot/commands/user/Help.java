package nambot.commands.user;

import static nambot.helpers.General.getGuildSettings;
import static nambot.helpers.General.isNamless;
import static nambot.helpers.Number.isInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nambot.main.Send;
import nambot.settings.GuildSettings;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Help {
	public static Map<String, String> userHelp;
	public static Map<String, String> adminHelp;
	public static Map<String, String> ownerHelp;

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
		list.sort(null);
		list.add(0, "help");
		list = list.stream().filter((x) -> !x.contains(" ")).collect(Collectors.toList());
		Send.list(mc, page, list, "Command list", 10, "");
	}

	private static void sendListAdmin(MessageChannel mc, int page) {
		List<String> list = new ArrayList<>();
		list.addAll(userHelp.keySet());
		list.addAll(adminHelp.keySet());
		list.sort(null);
		list.add(0, "help");
		list = list.stream().filter((x) -> !x.contains(" ")).collect(Collectors.toList());
		Send.list(mc, page, list, "Command list", 10, "");
	}

	private static void sendListUser(MessageChannel mc, int page) {
		List<String> list = new ArrayList<>();
		list.addAll(userHelp.keySet());
		list.sort(null);
		list.add(0, "help");
		list = list.stream().filter((x) -> !x.contains(" ")).collect(Collectors.toList());
		Send.list(mc, page, list, "Command list", 10, "");
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

		/* User */
		userHelp.put("info", "info [server|<user>|<role>|<channel>|<custom emote>|role <role name>|user <user name>]");
		userHelp.put("ping", "ping");
		userHelp.put("joke", "joke");
		userHelp.put("vote", "vote <phrase>");
		userHelp.put("avatar", "avatar (<user>)");
		userHelp.put("level", "level (<user>)");
		userHelp.put("flip", "flip (--reverse) <phrase>");
		userHelp.put("reverse", "reverse (--flip) <phrase>");
		userHelp.put("generateavatar", "generateavatar [robot|robothead|alien|cat|face]");
		userHelp.put("zalgo", "zalgo (--max|--mid|--min) <phrase>");
		userHelp.put("buy", "buy [list (<page>)|(<amount>) <item>]");
		userHelp.put("inv", "inv (<page>)");
		userHelp.put("inventory", "inventory (<page>)");
		userHelp.put("drop", "drop [(<amount>) <item>|--all]");
		userHelp.put("pat", "pat <user>");
		userHelp.put("stab", "stab <user>");
		userHelp.put("slap", "slap <user>");
		userHelp.put("hug", "hug <user>");
		userHelp.put("civilwar", "civilwar <user> <user text> | <user> <user text>");

		userHelp.put("use", "use (<amount>) <item> (<parameters>)");
		userHelp.put("use tomato", "use (<amount>) tomato <user mention>");
		userHelp.put("use rock", "use (<amount>) rock <user mention>");
		userHelp.put("use nicknamechanger", "use nicknamechanger <user mention> <new name>");

		userHelp.put("cc", "cc [h(elp)|c(reate)|d(elete)|e(dit)|s(how)]");
		userHelp.put("customcommand", "customcommand [h(elp)|c(reate)|d(elete)|e(dit)|s(how)|t(est)]");
		userHelp.put("customcommand create", "customcommand create <command name> <contents>");
		userHelp.put("customcommand delete", "customcommand delete <command name>");
		userHelp.put("customcommand edit", "customcommand edit <command name> <contents>");
		userHelp.put("customcommand show", "customcommand show <command name>");
		userHelp.put("customcommand test", "customcommand test <contents>");
		userHelp.put("customcommand help", "customcommand help");

		/* Admin */
		adminHelp.put("setprefix", "setprefix <prefix>");
		adminHelp.put("setlogchannel", "setlogchannel");
		adminHelp.put("clear", "clear (<1-100>)");

		/* Owner */
		ownerHelp.put("exit", "exit");
		ownerHelp.put("save", "save");
	}
}