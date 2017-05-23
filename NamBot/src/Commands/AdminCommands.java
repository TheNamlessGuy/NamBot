package Commands;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static HelperPackage.HelperFunctions.*;
import static HelperPackage.GlobalVars.*;
import static HelperPackage.SendingFunctions.*;

import java.time.format.DateTimeFormatter;

import CustomCommandClasses.ChangeRoles;
import CustomCommandClasses.PostImageCommand;
import CustomCommandClasses.SayCommand;
import HelperClasses.ServerSettings;

public class AdminCommands {
	/*
	 * CLEANUP
	 */
	public static void cleanup(MessageReceivedEvent event, String call) {
		if (!(getSettings(event.getGuild()).isAdmin(event.getMember()))) {
			sendMsg(event.getChannel(), "You don't have enough badges to train me");
			return;
		}
		
		try {
			int amount;
			if (call.toUpperCase().equals("MAX")) {
				amount = 100;
			} else {
				amount = Integer.parseInt(call) + 1;
				if (amount > 100 || amount < 1) {
					sendMsg(event.getChannel(), "0-99 are the only numbers availabe for cleanup");
					return;
				}
			}
			
			event.getChannel().getHistory().retrievePast(amount).queue((lst) -> {
				for (Message m : lst) {
					m.deleteMessage().queue();
				}
			});
		} catch (Exception e) {
			err(event.getChannel(), e, prefix + "cleanup " + call);
		}
	}
	
	/*
	 * GET INFO
	 */
	public static void getinfo(MessageReceivedEvent event, String call) {
		User u = getFirstMentionOrAuthor(event);
		if (isNambot(u)) {
			sendMsg(event.getChannel(), "https://github.com/TheNamlessGuy/NamBot");
			return;
		}
		Member m = event.getGuild().getMember(u);
		
		String msg = "```\n";
		msg += "USER: " + m.getEffectiveName() + " (" + u.getName() + "#" + u.getDiscriminator() + ")\n";
		msg += "Snowflake: " + u.getId() + "\n";
		msg += "Avatar URL: " + u.getEffectiveAvatarUrl() + "\n";
		msg += "Join date: " + m.getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "\n";
		msg += "Current roles: ";
		for (Role r : m.getRoles()) {
			msg += r.getName() + ", ";
		}
		msg = msg.substring(0, msg.length() - 2) + "\n";
		msg += "Color: " + m.getColor().getRed() + ", " + m.getColor().getGreen() + ", " + m.getColor().getBlue() + "\n";
		msg += "```";
		sendMsg(event.getChannel(), msg);
	}
	
	/*
	 * SET LOGGER CHANNEL
	 */
	public static void setloggerchannel(MessageReceivedEvent event, String call) {
		ServerSettings s = getSettings(event.getGuild());
		
		if (s.isAdmin(event.getMember())) {
			s.loggerChannel = event.getChannel().getId();
			sendMsg(event.getChannel(), "This channel successfully set to logger channel");
		} else {
			sendMsg(event.getChannel(), "You do not have permissions to set this to logger channel");
		}
	}
	
	/*
	 * REMOVE LOGGER CHANNEL
	 */
	public static void removeloggerchannel(MessageReceivedEvent event, String call) {
		ServerSettings s = getSettings(event.getGuild());
		
		if (s.isAdmin(event.getMember())) {
			s.loggerChannel = "";
			sendMsg(event.getChannel(), "Logger channel disabled");
		} else {
			sendMsg(event.getChannel(), "You do not have permissions to remove the logger channel");
		}
	}

	/*
	 * ADD CUSTOM COMMAND
	 */
	public static void addcustom(MessageReceivedEvent event, String call) {
		ServerSettings s = getSettings(event.getGuild());
		if (!s.isAdmin(event.getMember())) {
			sendMsg(event.getChannel(), "You do not have permissions to set custom commands");
			return;
		}
		
		boolean deleteMessage = call.contains("--delete");
		call = call.replace("--delete", "").trim();
		
		if (call.startsWith("help") || call.equals("")) {
			if (call.equals("help") || call.equals("")) {
				sendMsg(event.getChannel(), "Following commands are availabe (run `" + prefix + "addcustom help [command]` for more help):\n```\nroles\nsay\nimage\n```\nAdd `--delete` to any command to have it remove the calling message");
			} else if (call.contains("roles")) {
				sendMsg(event.getChannel(), "Usage: `" + prefix + "addcustom roles [name of command] (+|-)[name of roles] [--delete]`\nExample: `" + prefix + "addcustom roles adminify -@Normal +@Admin`");
			} else if (call.contains("say")) {
				sendMsg(event.getChannel(), "Usage: `" + prefix + "addcustom say [name of command] [what to say] [--delete]`\nExample: `" + prefix + "addcustom say goaway GO AWAY`");
			} else if (call.contains("image")) {
				sendMsg(event.getChannel(), "Usage: `" + prefix + "addcustom image [name of command] [" + prefix + "meme search term] | [optional text to say] [--delete]`\nExample: `" + prefix + "addcustom image dab dab | intense daberoni`\nor: `" + prefix + "addcustom image dab dab`");
			}
			return;
		} else if (call.startsWith("roles")) {
			String name = call.replaceFirst("roles", "").trim().split(" ")[0];
			s.addCustomCommand(name, new ChangeRoles(event, deleteMessage));
		} else if (call.startsWith("say")) {
			call = call.replaceFirst("say", "").trim();
			String name = call.split(" ")[0];
			call = call.replaceFirst(name, "").trim();
			s.addCustomCommand(name, new SayCommand(call, deleteMessage));
		} else if (call.startsWith("image")) {
			call = call.replaceFirst("image", "").trim();
			String name = call.split(" ")[0];
			call = call.replaceFirst(name, "").trim();
			String toSay = "";
			if (call.contains("|")) {
				toSay = call.split("\\|")[1].trim();
				call = call.split("\\|")[0].trim();
			}
			s.addCustomCommand(name, new PostImageCommand(call, toSay, deleteMessage));
		}
		event.getChannel().sendMessage("Command successfully saved").queue();
	}
	
	/*
	 * REMOVE CUSTOM COMMAND
	 */
	public static void removecustom(MessageReceivedEvent event, String call) {
		ServerSettings s = getSettings(event.getGuild());
		if (!s.isAdmin(event.getMember())) {
			sendMsg(event.getChannel(), "You do not have permissions to remove custom commands");
			return;
		}
		
		s.removeCustomCommand(call);
		event.getChannel().sendMessage("Command successfully removed").queue();
	}
	
	/*
	 * CUSTOM LIST
	 */
	public static void customlist(MessageReceivedEvent event, String call) {
		event.getChannel().sendMessage(getSettings(event.getGuild()).getCommandList()).queue();
	}
}
