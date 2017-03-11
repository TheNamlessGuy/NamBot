package HelperClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import CustomCommandClasses.ChangeRoles;
import CustomCommandClasses.CustomCommand;
import CustomCommandClasses.SayCommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static HelperPackage.GlobalVars.*;

public class ServerSettings {
	public String loggerChannel;	
	private ArrayList<String> adminRoles;	
	private Map<String, CustomCommand> customCommands;
	
	public ServerSettings() {
		loggerChannel = "";		
		adminRoles = new ArrayList<String>();		
		customCommands = new HashMap<String, CustomCommand>();
	}
	
	public ServerSettings(JSONObject obj, Guild g) {
		loggerChannel = obj.getString("loggerChannel");
		
		adminRoles = new ArrayList<String>();
		JSONArray arr = obj.getJSONArray("adminRoles");
		for (int i = 0; i < arr.length(); i++) {
			adminRoles.add(arr.getString(i));
		}
		
		customCommands = new HashMap<String, CustomCommand>();
		JSONArray a = obj.getJSONArray("customcommands");
		for (int i = 0; i < a.length(); i++) {
			JSONObject o = a.getJSONObject(i);
			switch(o.getString("TYPE")) {
			case "ChangeRoles":
				customCommands.put(o.getString("NAME"), new ChangeRoles(o, g));
				break;
			case "SayCommand":
				customCommands.put(o.getString("NAME"), new SayCommand(o));
				break;
			default:
				break;
			}
		}
	}
	
	public boolean isAdmin(Member m) {
		if (m.getPermissions().contains(Permission.ADMINISTRATOR) || m.isOwner()) { return true; }
		
		for (Role r : m.getRoles()) {
			if (adminRoles.contains(r.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addAdminRole(Role r, Member m) {
		if (m.getPermissions().contains(Permission.ADMINISTRATOR) || isAdmin(m)) {
			adminRoles.add(r.getId());
			return true;
		}
		return false;
	}
	
	public void addCustomCommand(String name, CustomCommand c) {
		customCommands.put(name, c);
	}
	
	public void removeCustomCommand(String name) {
		customCommands.remove(name);
	}
	
	public void executeCommand(MessageReceivedEvent event, String call) {
		for (String s : customCommands.keySet()) {
			if (call.startsWith(prefix + s)) {
				customCommands.get(s).execute(event);
				return;
			}
		}
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		
		obj.put("adminRoles", adminRoles);
		obj.put("loggerChannel", loggerChannel);
		
		ArrayList<JSONObject> temp = new ArrayList<JSONObject>();
		for (String name : customCommands.keySet()) {
			temp.add(customCommands.get(name).toJSON());
			temp.get(temp.size() - 1).put("NAME", name);
		}
		obj.put("customcommands", temp);
		
		return obj;
	}
}
