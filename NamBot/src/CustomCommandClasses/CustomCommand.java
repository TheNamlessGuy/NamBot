package CustomCommandClasses;

import org.json.JSONObject;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class CustomCommand {
	protected boolean deleteMessage;
	public abstract void execute(MessageReceivedEvent event);
	public abstract JSONObject toJSON();
}
