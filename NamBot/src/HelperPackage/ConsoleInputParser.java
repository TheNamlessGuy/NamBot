package HelperPackage;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class ConsoleInputParser {
	public static void parseInput(String in) {
		if (in.startsWith("say")) {
			say(supersplit(in, "say"));		
		} else if (in.startsWith("memberinfo")) {
			memberinfo(supersplit(in, "memberinfo"));
		} else if (in.startsWith("guildinfo")) {
			guildinfo(supersplit(in, "guildinfo"));
		} else if (in.startsWith("shutdown")) {
			shutdown(supersplit(in, "shutdown"));
		} else if (in.startsWith("leave")) {
			leave(supersplit(in, "leave"));
		}
	}
	
	private static void say(String[] in) {
		if (in.length < 3) { return; }
		
		Guild g = GlobalVars.nambot.getGuildById(in[0]);
		if (g == null) { return; }
		
		TextChannel tc = getChannel(g, in[1]);
		if (tc == null) { return; }

		tc.sendMessage(concat(in, 2, " ")).queue();
	}
	
	private static void memberinfo(String[] in) {
		if (in.length != 2) { return; }

		Guild g = GlobalVars.nambot.getGuildById(in[0]);
		if (g == null) { return; }
		
		Member m = getMemberByName(g, in[1].toLowerCase());
		if (m == null) { return; }
		
		HelperFunctions.debug("Snowflake: " + m.getUser().getId());
	}
	
	private static void guildinfo(String[] in) {
		if (in.length != 1) { return; }
		
		for (Guild g : GlobalVars.nambot.getGuilds()) {
			if (g.getName().toLowerCase().contains(in[0].toLowerCase())) {
				HelperFunctions.debug(g.getName() + ": " + g.getId());
			}
		}
	}
	
	private static void shutdown(String[] in) {
		GlobalVars.nambot.shutdown(true);
		HelperFunctions.saveSettings();
		System.exit(0);
	}
	
	private static void leave(String[] in) {
		if (in.length != 1) { return; }
		
		Guild g = GlobalVars.nambot.getGuildById(in[0]);
		String msg = g.getName() + " (" + in[0] + ")";
		g.leave().queue((a) -> {
			GlobalVars.serversettings.remove(in[0]);
			HelperFunctions.debug("Successfully left " + msg);
		}, (a) -> {
			HelperFunctions.debug("Failed to leave " + msg + "\n" + a.getMessage());
		});
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
