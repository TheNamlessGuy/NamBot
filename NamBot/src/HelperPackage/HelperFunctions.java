package HelperPackage;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import HelperClasses.ServerSettings;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelperFunctions {
	public static Random random = new Random();
	
	public static int randomInt(int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}
	
	public static boolean randomBool() {
		return random.nextBoolean();
	}
	
	public static void setTimeout(Runnable runnable, int delay) {
		new Thread(() -> {
	        try {
	            Thread.sleep(delay);
	            runnable.run();
	        }
	        catch (Exception e){
	        	// no
	        }
	    }).start();
	}
	
	public static String getEffectiveNickname(MessageReceivedEvent event, User u) {
		return event.getGuild().getMember(u).getEffectiveName();
	}
	
	public static void debug(Object msg) {
		System.out.println(msg);
	}
	
	public static boolean isNambot(User u) {
		return u.getId().equals(GlobalVars.snowflakes.get("Nambot"));
	}
	
	public static boolean isNamless(User u) {
		return u.getId().equals(GlobalVars.snowflakes.get("Namless"));
	}
	
	public static boolean isSame(User u1, User u2) {
		return u1.getId().equals(u2.getId());
	}
	
	public static User getFirstMentionOrAuthor(MessageReceivedEvent event) {
		if (event.getMessage().getMentionedUsers().size() == 0) {
			return event.getAuthor();
		} else {
			return event.getMessage().getMentionedUsers().get(0);
		}
	}
	
	public static String convertMentions(MessageReceivedEvent event) {
		String msg = event.getMessage().getContent();
		for (User u : event.getMessage().getMentionedUsers()) {
			msg = msg.replaceAll("@" + event.getGuild().getMember(u).getEffectiveName(), u.getAsMention());
		}
		for (Role r : event.getMessage().getMentionedRoles()) {
			msg = msg.replaceAll("@" + r.getName(), r.getAsMention());
		}
		for (TextChannel tc : event.getMessage().getMentionedChannels()) {
			msg = msg.replaceAll("#" + tc.getName(), tc.getAsMention());
		}
		return msg;
	}
	
	public static String getFileEnd(File f) {
		int i = f.getName().lastIndexOf('.');
		if (i > 0) {
			return f.getName().substring(i + 1).toLowerCase();
		}
		return "";
	}
	
	public static List<String> supportedImageFormats = Arrays.asList("png", "jpg", "jpeg", "gif");
	public static boolean isImage(File f) {
		return supportedImageFormats.contains(getFileEnd(f));
	}
	
	public static void saveSettings() {
		try {
			FileWriter f = new FileWriter("serversettings.json");
			
			JSONObject obj = new JSONObject();
			for (String snowflake : GlobalVars.serversettings.keySet()) {
				obj.put(snowflake, GlobalVars.serversettings.get(snowflake).toJSON());
			}
			
			f.write(obj.toString());
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ServerSettings getSettings(Guild g) {
		return GlobalVars.serversettings.get(g.getId());
	}
}
