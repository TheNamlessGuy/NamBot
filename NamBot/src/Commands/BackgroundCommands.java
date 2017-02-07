package Commands;

import static HelperPackage.GlobalVars.*;
import static HelperPackage.HelperFunctions.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BackgroundCommands {
	public static void botvote(MessageReceivedEvent event, String call) {
		if (!sentByNambot(event)) {
			return;
		}
		event.getMessage().addReaction("\uD83D\uDC4D").queue(); // thumb up
		event.getMessage().addReaction("\uD83D\uDC4E").queue(); // thumb down
		event.getMessage().editMessage(event.getMessage().getContent().replace(prefix + "bv", "").trim()).queue();
	}
}
