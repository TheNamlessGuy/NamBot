package HelperPackage;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;

public class Logger {
	/*
	 * GENERAL
	 */
	public static void log(GuildBanEvent e) {
		sendMessage(e.getUser().getAsMention() + " was banned", e.getGuild());
	}
	
	public static void log(GuildUnbanEvent e) {
		sendMessage(e.getUser().getAsMention() + " was unbanned", e.getGuild());
	}
	
	public static void log(GuildMemberJoinEvent e) {
		sendMessage(e.getMember().getAsMention() + " joined the server", e.getGuild());
	}
	
	public static void log(GuildMemberLeaveEvent e) {
		if (HelperFunctions.isNambot(e.getMember().getUser())) { return; }
		sendMessage(e.getMember().getAsMention() + " left the server", e.getGuild());
	}
	
	public static void log(GuildMemberNickChangeEvent e) {
		sendMessage(e.getMember().getAsMention() + " changed their nickname from `" + e.getPrevNick() + "` to `" + e.getNewNick() + '`', e.getGuild());
	}
	
	public static void log(GuildMemberRoleAddEvent e) {
		String roles = "";
		for (Role r : e.getRoles()) {
			roles += "`" + r.getName() + "`, ";
		}
		roles = roles.substring(0, roles.length() - 2);
		
		if (e.getRoles().size() == 1) {
			sendMessage(e.getMember().getAsMention() + " was added to the role " + roles, e.getGuild());
		} else {
			sendMessage(e.getMember().getAsMention() + " was added to the roles " + roles, e.getGuild());
		}
	}
	
	public static void log(GuildMemberRoleRemoveEvent e) {
		String roles = "";
		for (Role r : e.getRoles()) {
			roles += "`" + r.getName() + "`, ";
		}
		roles = roles.substring(0, roles.length() - 2);
		
		if (e.getRoles().size() == 1) {
			sendMessage(e.getMember().getAsMention() + " was removed from the role " + roles, e.getGuild());
		} else {
			sendMessage(e.getMember().getAsMention() + " was removed from the roles " + roles, e.getGuild());
		}
	}
	
	public static void log(String msg, Guild g) {
		sendMessage(msg, g);
	}
	
	/*
	 * TEXT
	 */
	public static void log(GuildMessageDeleteEvent e) {
		//if (HelperFunctions.isNambot(e.getMember().getUser())) { return; }
		//sendMessage(e.getMember().getAsMention() + " deleted message '" + e.getMessage().getContent() + "' in channel " + e.getChannel().getName(), e.getGuild());
	}
	
	/*
	 * VOICE
	 */
	public static void log(GuildVoiceJoinEvent e) {
		sendMessage(e.getMember().getAsMention() + " joined voice channel `" + e.getChannelJoined().getName() + '`', e.getGuild());
	}
	
	public static void log(GuildVoiceLeaveEvent e) {
		sendMessage(e.getMember().getAsMention() + " left voice channel `" + e.getChannelLeft().getName() + '`', e.getGuild());
	}
	
	public static void log(GuildVoiceMoveEvent e) {
		sendMessage(e.getMember().getAsMention() + " moved from voice channel `" + e.getChannelLeft().getName() + "` to `" + e.getChannelJoined().getName() + '`', e.getGuild());
	}
	
	/*
	 * HelperFunctions
	 */
	private static void sendMessage(String msg, Guild g) {
		ServerSettings s = HelperFunctions.getSettings(g);
		if (s.loggerChannel.equals("")) { return; }
		
		TextChannel tc = g.getTextChannelById(s.loggerChannel);
		if (tc == null) { return; }
		
		tc.sendMessage(msg).queue();
	}
}
