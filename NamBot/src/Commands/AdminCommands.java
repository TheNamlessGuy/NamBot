package Commands;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static HelperPackage.HelperFunctions.*;
import static HelperPackage.GlobalVars.*;
import static HelperPackage.SendingFunctions.*;

import java.time.format.DateTimeFormatter;

import HelperPackage.ServerSettings;

public class AdminCommands {
	/*
	 * CLEANUP
	 */
	public static void cleanup(MessageReceivedEvent event, String call) {
		if (!(sentBy(event, Permission.ADMINISTRATOR) || sentByNamless(event))) {
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
			sendMsg(event.getChannel(), "Couldn't run _::cleanup " + call + "_:\n" + e.getLocalizedMessage());
		}
	}
	
	static boolean sendSpam = true;
	/*
	 * SPAM
	 */
	public static void spam(MessageReceivedEvent event, String call) {
		if (!sentByNambot(event)) {
			debug(event.getAuthor().getName() + " started a spamfest");
		}
		if (sendSpam) {
			sendMsg(event.getChannel(), prefix + "spam");
		}
	}
	
	/*
	 * STOP SPAM
	 */
	public static void stopspam(MessageReceivedEvent event, String call) {
		if (sentByNamless(event) || sentBy(event, Permission.ADMINISTRATOR)) {
			sendSpam = false;
			setTimeout(() -> {
				sendSpam = true;
			}, 5000);
		} else {
			sendMsg(event.getChannel(), "Nope");
		}
	}
	
	/*
	 * GET INFO
	 */
	public static void getinfo(MessageReceivedEvent event, String call) {
		User u = getFirstMentionOrAuthor(event);
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
}
