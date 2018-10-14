package nambot.helpers.settings;

import static nambot.helpers.General.isNamless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import nambot.commands.custom.CustomCommand;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

public class GuildSettings {
	public Guild guild;
	public MessageChannel logChannel;
	public MessageChannel reactionChannel;
	public String prefix;

	public Map<String, CustomCommand> customCommands;
	public Map<String, Role> reactToRoles;

	public List<String> adminRoles;

	public boolean isAdmin(Member m) {
		if (m.isOwner() || isNamless(m)) {
			return true;
		}

		for (Role r : m.getRoles()) {
			if (adminRoles.contains(r.getId())) {
				return true;
			}
		}

		return false;
	}

	public String ID() {
		return guild.getId();
	}

	public GuildSettings(Guild g) {
		guild = g;
		logChannel = null;
		reactionChannel = null;
		prefix = "-";
		adminRoles = new ArrayList<>();

		customCommands = new HashMap<>();
		reactToRoles = new HashMap<>();
	}

	public GuildSettings(JSONObject o, Guild g) {
		guild = g;
		if (o.has("logChannel")) {
			logChannel = g.getTextChannelById(o.getString("logChannel"));
		}

		reactToRoles = new HashMap<>();
		if (o.has("reactionChannel")) {
			reactionChannel = g.getTextChannelById(o.getString("reactionChannel"));

			JSONObject tmp = o.getJSONObject("reactToRoles");
			for (String reaction : tmp.keySet()) {
				reactToRoles.put(reaction, g.getRoleById(tmp.getString(reaction)));
			}
		}

		prefix = o.getString("prefix");

		adminRoles = new ArrayList<>();
		if (o.has("adminRoles")) {
			JSONArray a = o.getJSONArray("adminRoles");
			for (int i = 0; i < a.length(); ++i) {
				adminRoles.add(a.getString(i));
			}
		}

		customCommands = new HashMap<>();
	}

	public JSONObject save() {
		JSONObject o = new JSONObject();
		if (logChannel != null) {
			o.put("logChannel", logChannel.getId());
		}

		if (reactionChannel != null) {
			o.put("reactionChannel", reactionChannel.getId());

			JSONObject tmp = new JSONObject();
			for (String reaction : reactToRoles.keySet()) {
				tmp.put(reaction, reactToRoles.get(reaction).getId());
			}
			o.put("reactToRoles", tmp);
		}

		o.put("prefix", prefix);

		JSONArray a = new JSONArray();
		for (String id : adminRoles) {
			a.put(id);
		}
		o.put("adminRoles", a);
		return o;
	}
}