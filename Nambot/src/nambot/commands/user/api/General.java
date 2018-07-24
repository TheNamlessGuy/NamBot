package nambot.commands.user.api;

import static nambot.globals.Vars.random;
import static nambot.helpers.General.randomString;

import java.io.IOException;
import java.net.MalformedURLException;

import nambot.commands.admin.Get;
import nambot.commands.user.Help;
import nambot.main.Send;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class General {
	public static void cmd_joke(MessageReceivedEvent e, String params) throws MalformedURLException, IOException {
		Send.text(e.getChannel(), Get.official_joke_api());
	}

	public static void cmd_generateavatar(MessageReceivedEvent e, String params) throws MalformedURLException, IOException {
		Send.typing(e.getChannel());
		if (params.equals("robot"))
			Send.image(e.getChannel(), Get.robohash("1", randomString(random.nextInt(9) + 1)), "robohash.png", null);
		else if (params.equals("robothead"))
			Send.image(e.getChannel(), Get.robohash("3", randomString(random.nextInt(9) + 1)), "robohash.png", null);
		else if (params.equals("alien"))
			Send.image(e.getChannel(), Get.robohash("2", randomString(random.nextInt(9) + 1)), "robohash.png", null);
		else if (params.equals("cat"))
			Send.image(e.getChannel(), Get.robohash("4", randomString(random.nextInt(9) + 1)), "robohash.png", null);
		else if (params.equals("face"))
			Send.image(e.getChannel(), Get.adorableavatars(randomString(random.nextInt(9) + 1)), "adorableavatars.png", null);
		else
			Help.sendCommandUser(e.getChannel(), "generateavatar");
	}
}