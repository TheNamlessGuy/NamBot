package nambot.settings;

import static nambot.helpers.General.isNamless;

import org.json.JSONObject;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class GuildSettings {
	public Guild guild;
	public MessageChannel logChannel;
	public String prefix;

	public boolean isAdmin(Member m) {
		if (m.isOwner() || isNamless(m)) {
			return true;
		}
		return false;
	}

	public String ID() {
		return guild.getId();
	}

	public GuildSettings(Guild g) {
		guild = g;
		logChannel = null;
		prefix = "-";
	}

	public GuildSettings(JSONObject o, Guild g) {
		guild = g;

		if (o.has("logChannel")) {
			logChannel = g.getTextChannelById(o.getString("logChannel"));
		}

		prefix = o.getString("prefix");
	}

	public JSONObject save() {
		JSONObject o = new JSONObject();

		if (logChannel != null) {
			o.put("logChannel", logChannel.getId());
		}

		o.put("prefix", prefix);
		return o;
	}
}