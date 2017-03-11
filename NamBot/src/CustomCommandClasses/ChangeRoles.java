package CustomCommandClasses;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.core.entities.Guild;
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
	
	public ChangeRoles(JSONObject obj, Guild g) {
		this.deleteMessage = obj.getBoolean("delete");
		remove = new ArrayList<Role>();
		add = new ArrayList<Role>();
		
		JSONArray a = obj.getJSONArray("add");
		for (int i = 0; i < a.length(); i++) {
			add.add(g.getRoleById(a.getString(i)));
		}
		
		a = obj.getJSONArray("remove");
		for (int i = 0; i < a.length(); i++) {
			remove.add(g.getRoleById(a.getString(i)));
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
		event.getChannel().sendMessage("Roles successfully changed").queue();
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("TYPE", "ChangeRoles");
		
		ArrayList<String> temp = new ArrayList<String>();
		for (Role r : add) {
			temp.add(r.getId());
		}
		obj.put("add", temp);
		
		temp.clear();
		for (Role r : remove) {
			temp.add(r.getId());
		}
		obj.put("remove", temp);
		obj.put("delete", deleteMessage);
		
		return obj;
	}
}
