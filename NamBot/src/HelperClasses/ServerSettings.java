package HelperClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import CustomCommandClasses.CustomCommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static HelperPackage.GlobalVars.*;

public class ServerSettings {
	public String loggerChannel;
	
	private ArrayList<String> adminRoles;
	private ArrayList<String> elevatedUserRoles;
	
	private Map<String, CustomCommand> customCommands;
	
	public ServerSettings() {
		loggerChannel = "";
		
		adminRoles = new ArrayList<String>();
		elevatedUserRoles = new ArrayList<String>();
		
		customCommands = new HashMap<String, CustomCommand>();
	}
	
	public ServerSettings(JSONObject obj) {
		loggerChannel = obj.getString("loggerChannel");
		
		adminRoles = new ArrayList<String>();
		JSONArray arr = obj.getJSONArray("adminRoles");
		for (int i = 0; i < arr.length(); i++) {
			adminRoles.add(arr.getString(i));
		}
		
		elevatedUserRoles = new ArrayList<String>();
		arr = obj.getJSONArray("elevatedUserRoles");
		for (int i = 0; i < arr.length(); i++) {
			elevatedUserRoles.add(arr.getString(i));
		}
		
		customCommands = new HashMap<String, CustomCommand>();
	}
	
	public boolean isAdmin(Member m) {
		if (m.getPermissions().contains(Permission.ADMINISTRATOR)) { return true; }
		
		for (Role r : m.getRoles()) {
			if (adminRoles.contains(r.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isElevated(Member m) {
		if (isAdmin(m)) { return true; }
		
		for (Role r : m.getRoles()) {
			if (elevatedUserRoles.contains(r.getId())) {
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
	
	public boolean addElevatedUserRole(Role r, Member m) {
		if (m.getPermissions().contains(Permission.ADMINISTRATOR) || isAdmin(m)) {
			elevatedUserRoles.add(r.getId());
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
		obj.put("elevatedUserRoles", elevatedUserRoles);
		obj.put("loggerChannel", loggerChannel);
		
		return obj;
	}
}
