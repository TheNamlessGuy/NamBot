package HelperPackage;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class ServerSettings {
	public String loggerChannel;
	
	private ArrayList<String> adminRoles;
	private ArrayList<String> elevatedUserRoles;
	
	public ServerSettings() {
		adminRoles = new ArrayList<String>();
		elevatedUserRoles = new ArrayList<String>();
		loggerChannel = "";
	}
	
	public ServerSettings(JSONObject obj) {
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
		
		loggerChannel = obj.getString("loggerChannel");
	}
	
	public boolean isAdmin(Member m) {
		if (m.getPermissions().contains(Permission.ADMINISTRATOR)) {
			return true;
		}
		
		for (Role r : m.getRoles()) {
			if (adminRoles.contains(r.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isElevated(Member m) {
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
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		
		obj.put("adminRoles", adminRoles);
		obj.put("elevatedUserRoles", elevatedUserRoles);
		obj.put("loggerChannel", loggerChannel);
		
		return obj;
	}
}
