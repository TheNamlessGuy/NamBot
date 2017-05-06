package HelperPackage;

import org.json.JSONObject;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;

public class ConsoleInputParser {
	public static void parseInput(String in, MessageChannel channel) {
		if (in.startsWith("say")) {
			say(supersplit(in, "say"), channel);
		} else if (in.startsWith("memberinfo")) {
			memberinfo(supersplit(in, "memberinfo"), channel);
		} else if (in.startsWith("guildinfo")) {
			guildinfo(supersplit(in, "guildinfo"), channel);
		} else if (in.startsWith("shutdown")) {
			shutdown(supersplit(in, "shutdown"), channel);
		} else if (in.startsWith("leave")) {
			leave(supersplit(in, "leave"), channel);
		} else if (in.startsWith("save")) {
			save(supersplit(in, "save"), channel);
		} else if (in.startsWith("getsettings")) {
			getsettings(supersplit(in, "getsettings"), channel);
		}
	}
	
	public static void parseInput(String in) {
		parseInput(in, null);
	}
	
	private static void say(String[] in, MessageChannel channel) {
		if (in.length < 3) { return; }
		
		Guild g = GlobalVars.nambot.getGuildById(in[0]);
		if (g == null) { return; }
		
		TextChannel tc = getChannel(g, in[1]);
		if (tc == null) { return; }

		tc.sendMessage(concat(in, 2, " ")).queue();
	}
	
	private static void memberinfo(String[] in, MessageChannel channel) {
		if (in.length != 2) { return; }

		Guild g = GlobalVars.nambot.getGuildById(in[0]);
		if (g == null) { return; }
		
		Member m = getMemberByName(g, in[1].toLowerCase());
		if (m == null) { return; }
		
		if (channel == null)
			HelperFunctions.debug("Snowflake: " + m.getUser().getId());
		else
			channel.sendMessage("Snowflake: " + m.getUser().getId()).queue();
	}
	
	private static void guildinfo(String[] in, MessageChannel channel) {
		if (in.length != 1) { return; }
		
		for (Guild g : GlobalVars.nambot.getGuilds()) {
			if (g.getName().toLowerCase().contains(in[0].toLowerCase())) {
				if (channel == null)
					HelperFunctions.debug(g.getName() + ": " + g.getId());
				else
					channel.sendMessage(g.getName() + ": " + g.getId()).queue();
			}
		}
	}
	
	private static void shutdown(String[] in, MessageChannel channel) {
		GlobalVars.nambot.shutdown(true);
		HelperFunctions.saveSettings();
		System.exit(0);
	}
	
	private static void leave(String[] in, MessageChannel channel) {
		if (in.length != 1) { return; }
		
		Guild g = GlobalVars.nambot.getGuildById(in[0]);
		String msg = g.getName() + " (" + in[0] + ")";
		g.leave().queue((a) -> {
			GlobalVars.serversettings.remove(in[0]);
			if (channel == null)
				HelperFunctions.debug("Successfully left " + msg);
			else
				channel.sendMessage("Successfully left " + msg).queue();
		}, (a) -> {
			if (channel == null)
				HelperFunctions.debug("Failed to leave " + msg + "\n" + a.getMessage());
			else
				channel.sendMessage("Failed to leave " + msg + "\n" + a.getMessage()).queue();
		});
	}
	
	private static void save(String[] in, MessageChannel channel) {
		HelperFunctions.saveSettings();
		if (channel == null)
			HelperFunctions.debug("Settings saved");
		else
			channel.sendMessage("Settings saved").queue();
	}
	
	private static void getsettings(String[] in, MessageChannel channel) {
		JSONObject obj = new JSONObject();
		for (String snowflake : GlobalVars.serversettings.keySet()) {
			obj.put(snowflake, GlobalVars.serversettings.get(snowflake).toJSON());
		}
		
		if (channel == null)
			HelperFunctions.debug("SETTINGS:\n" + obj.toString());
		else
			channel.sendMessage("SETTINGS:\n" + obj.toString()).queue();
	}
	
	/*
	 * HELPER FUNCTIONS
	 */
	private static String[] supersplit(String in, String command) {
		return in.replace(command, "").trim().split(" ");
	}
	
	private static TextChannel getChannel(Guild g, String name) {
		for (TextChannel tc : g.getTextChannels()) {
			if (tc.getName().equals(name)) {
				return tc;
			}
		}
		return null;
	}
	
	private static Member getMemberByName(Guild g, String name) {
		for (Member m : g.getMembers()) {
			if (m.getEffectiveName().toLowerCase().equals(name)) {
				return m;
			}
		}
		return null;
	}
	
	private static String concat(String[] s, int start, String separator) {
		String m = "";
		for (int i = start; i < s.length; i++) {
			m += s[i] + separator;
		}
		return m;
	}
}
