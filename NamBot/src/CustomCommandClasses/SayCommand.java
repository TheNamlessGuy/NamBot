package CustomCommandClasses;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SayCommand extends CustomCommand {
	private String toSay;
	
	public SayCommand(String toSay) {
		this.toSay = toSay;
	}
	
	@Override
	public void execute(MessageReceivedEvent event) {
		event.getChannel().sendMessage(toSay).queue();
	}	
}
