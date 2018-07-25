package nambot.commands.user.store;

import static nambot.globals.Vars.items;
import static nambot.helpers.General.getNamMember;
import static nambot.helpers.Number.isInt;
import static nambot.helpers.Number.lclamp;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nambot.commands.user.Help;
import nambot.helpers.Item;
import nambot.main.Send;
import nambot.settings.NamMember;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class General {
	public static void cmd_buy(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "buy");
			return;
		}

		String[] params = param.split(" ");
		if (params[0].equals("list")) {
			list(e, params);
		} else {
			buy(e, params);
		}
	}

	public static void cmd_use(MessageReceivedEvent e, String param) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "use");
			return;
		}

		NamMember nm = getNamMember(e.getMember());
		String[] params = param.split(" ");

		int amount = 1;
		String item = params[0];
		if (isInt(item)) {
			if (params.length < 2) {
				Help.sendCommandUser(e.getChannel(), "use");
				return;
			}

			amount = lclamp(1, Integer.parseInt(item));
			item = params[1];
			param = param.substring(params[0].length() + params[1].length()).trim();
		} else {
			param = param.substring(item.length()).trim();
		}
		item = item.toLowerCase();

		if (nm.inventory.contains(item)) {
			if (useItem(item, e, amount, param)) {
				nm.removeItem(item, amount);
			}
		} else {
			Send.text(e.getChannel(), "You do not have **" + item + "**");
		}
	}

	public static void cmd_inv(MessageReceivedEvent e, String param) {
		cmd_inventory(e, param);
	}

	public static void cmd_inventory(MessageReceivedEvent e, String param) {
		NamMember nm = getNamMember(e.getMember());

		int page = 1;
		if (isInt(param)) {
			page = Integer.parseInt(param);
		}

		List<String> list = new ArrayList<>();
		Set<String> tmp = new HashSet<>();
		for (String item : nm.inventory) {
			if (tmp.contains(item)) {
				continue;
			}
			tmp.add(item);

			list.add("**" + item + "** x" + Collections.frequency(nm.inventory, item));
		}

		Send.list(e.getChannel(), page, list, "Inventory of " + nm.getName() + " (size " + nm.inventory.size() + "/" + nm.inventorySize + ")", 10, "");
	}

	public static void cmd_drop(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "drop");
			return;
		}

		NamMember nm = getNamMember(e.getMember());
		String[] params = param.split(" ");

		int amount = 1;
		String item = params[0];
		if (item.equals("--all")) {
			item = null;
		} else if (isInt(item)) {
			if (params.length < 2) {
				Help.sendCommandUser(e.getChannel(), "drop");
				return;
			}
			amount = lclamp(1, Integer.parseInt(item));
			item = params[1];
		}
		item = item.toLowerCase();

		if (nm.removeItem(item, amount)) {
			if (item == null) {
				Send.text(e.getChannel(), "Dropped entire inventory");
			} else {
				Send.text(e.getChannel(), "Dropped up to " + Integer.toString(amount) + " **" + item + "**");
			}
		} else {
			Send.text(e.getChannel(), "You don't have a **" + item + "**");
		}
	}

	private static void list(MessageReceivedEvent e, String[] params) {
		int page = 1;
		if (isInt(params[0])) {
			page = Integer.parseInt(params[0]);
		}

		List<String> list = new ArrayList<>();
		for (Item i : items.values()) {
			list.add("**" + i.name + "** (" + i.price + " EXP)\n  " + i.description);
		}

		Send.list(e.getChannel(), page, list, "Store", 5, "");
	}

	private static void buy(MessageReceivedEvent e, String[] params) {
		NamMember nm = getNamMember(e.getMember());

		int amount = 1;
		String item = params[0];
		if (isInt(item)) {
			if (params.length < 2) {
				Help.sendCommandUser(e.getChannel(), "buy");
				return;
			}

			amount = lclamp(1, Integer.parseInt(item));
			item = params[1];
		}
		item = item.toLowerCase();

		if (!items.containsKey(item)) {
			Send.text(e.getChannel(), "**" + item + "** isn't available for purchase");
			return;
		}

		int price = items.get(item).price * amount;
		if (!nm.removeEXP(price)) {
			Send.text(e.getChannel(), "You cannot afford " + amount + " **" + item + "**");
			return;
		}

		if (nm.addItem(item, amount)) {
			Send.text(e.getChannel(), "Bought " + amount + " **" + item + "** for " + price + " EXP");
		} else {
			Send.text(e.getChannel(), amount + " **" + item + "** did not fit in your inventory");
			nm.addEXP(price);
		}
	}

	private static boolean useItem(String item, MessageReceivedEvent e, int amount, String param)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return (boolean) items.get(item).method.invoke(null, e, amount, param);
	}
}