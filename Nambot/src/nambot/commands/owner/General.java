package nambot.commands.owner;

import static nambot.globals.Vars.todo;
import static nambot.helpers.General.getNamMember;
import static nambot.helpers.Number.isInt;
import static nambot.helpers.Number.lclamp;

import java.io.IOException;

import nambot.commands.user.Help;
import nambot.main.NamBot;
import nambot.main.Send;
import nambot.main.IO.Save;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class General {
	public static void cmd_exit(MessageReceivedEvent e, String params) {
		Send.text(e.getChannel(), "Exiting...");
		NamBot.exit();
	}

	public static void cmd_save(MessageReceivedEvent e, String params) throws IOException {
		Send.text(e.getChannel(), "Saving...");
		Save.all();
	}

	public static void cmd_addexp(MessageReceivedEvent e, String params) {
		if (!isInt(params)) {
			Help.sendCommandUser(e.getChannel(), "addexp");
			return;
		}

		getNamMember(e.getMember()).addEXP(Integer.parseInt(params));
		nambot.commands.user.General.cmd_level(e, params);
	}

	public static void cmd_todo(MessageReceivedEvent e, String param) {
		String[] params = param.split(" ", 2);
		if (params[0].equals("add") && params.length > 1) {
			todo_add(e.getChannel(), params[1]);
		} else if (params[0].equals("delete") && params.length > 1) {
			todo_del(e.getChannel(), params[1]);
		} else if (params[0].equals("list")) {
			todo_list(e.getChannel(), (params.length == 1 ? "" : params[1]));
		} else {
			Help.sendCommandOwner(e.getChannel(), "todo");
		}
	}

	private static void todo_add(MessageChannel mc, String t) {
		todo.add(t);
		Send.text(mc, "TODO added");
	}

	private static void todo_del(MessageChannel mc, String t) {
		if (!isInt(t)) {
			Send.text(mc, "Specify a number on the todo list");
			return;
		}
		int i = Integer.parseInt(t) - 1;
		if (i < 0 || i > todo.size() - 1) {
			Send.text(mc, "Specify a number on the todo list");
			return;
		}
		todo.remove(i);
		Send.text(mc, "TODO removed");
	}

	private static void todo_list(MessageChannel mc, String t) {
		int page = 1;
		if (isInt(t)) {
			page = lclamp(1, Integer.parseInt(t));
		}
		Send.list(mc, page, todo, "TODO list", 5, (sb, s, i) -> sb.append(i + 1).append(". ").append(s));
	}
}