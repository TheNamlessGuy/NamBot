package Commands;

import static HelperPackage.HelperFunctions.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BackgroundCommands {
	public static void botvote(MessageReceivedEvent event, String call) {
		if (!isNambot(event.getAuthor())) {
			return;
		}
		
		event.getMessage().addReaction("\uD83D\uDC4D").queue(); // thumb up
		event.getMessage().addReaction("\uD83D\uDC4E").queue(); // thumb down
		event.getMessage().editMessage(call).queue();
	}
}
