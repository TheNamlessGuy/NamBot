package CustomCommandClasses;

import org.json.JSONObject;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SayCommand extends CustomCommand {
	private String toSay;
	
	public SayCommand(String toSay, boolean deleteMessage) {
		this.deleteMessage = deleteMessage;
		this.toSay = toSay;
	}
	
	public SayCommand(JSONObject obj) {
		toSay = obj.getString("say");
		deleteMessage = obj.getBoolean("delete");
	}
	
	@Override
	public void execute(MessageReceivedEvent event) {
		event.getChannel().sendMessage(toSay).queue();
		
		if (deleteMessage)
			event.getMessage().deleteMessage().queue();
	}

	@Override
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("TYPE", "SayCommand");
		
		obj.put("say", toSay);
		obj.put("delete", deleteMessage);
		
		return obj;
	}
}
