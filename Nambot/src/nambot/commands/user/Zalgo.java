package nambot.commands.user;

import static nambot.globals.Vars.random;
import static nambot.helpers.Number.clamp;
import static nambot.helpers.Number.isInt;

import java.util.HashSet;
import java.util.Set;

import nambot.main.Send;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Zalgo {
	public static void cmd_zalgo(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "zalgo");
			return;
		}

		int fuckery = 5;
		String[] params = param.split(" ", 2);
		if (params[0].startsWith("--")) {
			String n = params[0].substring(2);
			if (isInt(n)) {
				fuckery = clamp(1, Integer.parseInt(n), 10);
				param = param.substring(params[0].length() + 1);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < param.length(); i++) {
			sb.append(param.charAt(i));
			for (int j = 0; j < fuckery; j++) {
				sb.append(zalgoTop[random.nextInt(zalgoTop.length)]);
				sb.append(zalgoBottom[random.nextInt(zalgoTop.length)]);
			}
		}
		Send.text(e.getChannel(), sb.toString());
	}

	private static String[] zalgoTop;
	private static String[] zalgoBottom;

	static {
		Set<String> tmp = new HashSet<>();
		for (int i = 768; i <= 789; i++)
			tmp.add(Character.toString((char) i));
		for (int i = 829; i <= 836; i++)
			tmp.add(Character.toString((char) i));
		tmp.add(Character.toString((char) 794));
		tmp.add(Character.toString((char) 795));
		tmp.add(Character.toString((char) 836));
		tmp.add(Character.toString((char) 838));
		tmp.add(Character.toString((char) 842));
		tmp.add(Character.toString((char) 843));
		tmp.add(Character.toString((char) 844));
		tmp.add(Character.toString((char) 848));
		tmp.add(Character.toString((char) 849));
		tmp.add(Character.toString((char) 850));
		tmp.add(Character.toString((char) 855));
		tmp.add(Character.toString((char) 856));
		tmp.add(Character.toString((char) 859));
		tmp.add(Character.toString((char) 861));
		tmp.add(Character.toString((char) 862));
		tmp.add(Character.toString((char) 864));
		tmp.add(Character.toString((char) 865));
		zalgoTop = tmp.toArray(new String[tmp.size()]);
		tmp.clear();

		for (int i = 790; i <= 819; i++)
			if (i != 794 && i != 795)
				tmp.add(Character.toString((char) i));
		for (int i = 825; i <= 828; i++)
			tmp.add(Character.toString((char) i));
		tmp.add(Character.toString((char) 837));
		tmp.add(Character.toString((char) 839));
		tmp.add(Character.toString((char) 840));
		tmp.add(Character.toString((char) 841));
		tmp.add(Character.toString((char) 845));
		tmp.add(Character.toString((char) 846));
		tmp.add(Character.toString((char) 851));
		tmp.add(Character.toString((char) 852));
		tmp.add(Character.toString((char) 853));
		tmp.add(Character.toString((char) 854));
		tmp.add(Character.toString((char) 857));
		tmp.add(Character.toString((char) 858));
		tmp.add(Character.toString((char) 860));
		tmp.add(Character.toString((char) 863));
		zalgoBottom = tmp.toArray(new String[tmp.size()]);
		tmp.clear();
	}
}
