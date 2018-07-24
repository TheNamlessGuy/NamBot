package nambot.globals;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import nambot.commands.user.store.actions.Throwables;
import nambot.helpers.Item;
import nambot.settings.GuildSettings;
import nambot.settings.NamMember;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Vars {
	public static JDA nambot;
	public static Map<String, GuildSettings> guildSettings;
	public static Random random;
	public static Map<String, Item> items;
	public static Map<String, NamMember> members;

	static {
		guildSettings = new HashMap<>();
		random = new Random();
		members = new HashMap<>();

		items = new HashMap<>();
		@SuppressWarnings("rawtypes") Class[] c = { MessageReceivedEvent.class, Integer.class };
		try {
			items.put("tomato", new Item("Tomato", 1, Throwables.class.getMethod("throwtomato", c), "Throw a tomato at someone! Target gains 1 EXP"));
			items.put("rock", new Item("Rock", 1, Throwables.class.getMethod("throwrock", c), "Throw a rock at someone! Target loses 1 EXP"));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
}