package HelperPackage;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class ConsoleInputParser {
	public static void parseInput(String in) {
		if (in.startsWith("say")) {
			say(supersplit(in, "say"));		
		} else if (in.startsWith("info")) {
			info(supersplit(in, "info"));
		} else if (in.startsWith("shutdown")) {
			shutdown(supersplit(in, "shutdown"));
		}
	}
	
	private static void say(String[] in) {
		if (in.length < 3) { return; }
		
		Guild g = getGuild(in[0]);
		if (g == null) { return; }
		
		TextChannel tc = getChannel(g, in[1]);
		if (tc == null) { return; }

		tc.sendMessage(concat(in, 2)).queue();
	}
	
	private static void info(String[] in) {
		if (in.length != 2) { return; }

		Guild g = getGuild(in[0]);
		if (g == null) { return; }
		
		Member m = getMemberByName(g, in[1].toLowerCase());
		if (m == null) { return; }
		
		HelperFunctions.debug("Snowflake: " + m.getUser().getId());
	}
	
	private static void shutdown(String[] in) {
		System.exit(0);
	}
	
	/*
	 * HELPER FUNCTIONS
	 */
	private static String[] supersplit(String in, String command) {
		return in.replace(command, "").trim().split(" ");
	}
	
	private static Guild getGuild(String name) {
		for (Guild g : GlobalVars.nambot.getGuilds()) {
			if (g.getId().equals(GlobalVars.snowflakes.get("S-" + name.toUpperCase()))) {
				return g;
			}
		}
		return null;
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
	
	private static String concat(String[] s, int start) {
		String m = "";
		for (int i = start; i < s.length; i++) {
			m += s[i];
		}
		return m;
	}
}
