package CustomCommandClasses;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class CustomCommand {	
	public abstract void execute(MessageReceivedEvent event);
}
