package CustomCommandClasses;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

public class ChangeRoles extends CustomCommand {
	private ArrayList<Role> remove;
	private ArrayList<Role> add;
	
	public ChangeRoles(MessageReceivedEvent event, boolean deleteMessage) {
		this.deleteMessage = deleteMessage;
		remove = new ArrayList<Role>();
		add = new ArrayList<Role>();
		
		String call = event.getMessage().getContent();
		for (Role r : event.getMessage().getMentionedRoles()) {
			char prefix = call.charAt(call.indexOf("@" + r.getName()) - 1);
			if (prefix == '+') {
				add.add(r);
			} else if (prefix == '-') {
				remove.add(r);
			} else {
				event.getChannel().sendMessage("The role " + r.getName() + " was prefixed by '" + prefix + "', which is unspecified behavior. Skipped").queue();
			}
		}
	}

	@Override
	public void execute(MessageReceivedEvent event) {
		GuildController gc = new GuildController(event.getGuild());
		for (User u : event.getMessage().getMentionedUsers()) {
			gc.modifyMemberRoles(event.getGuild().getMember(u), add, remove).queue();
		}
		
		if (deleteMessage)
			event.getMessage().deleteMessage().queue();
	}
}
