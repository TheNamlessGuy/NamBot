package HelperPackage;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import HelperClasses.ServerSettings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
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
		sendMessage(e.getGuild(), new EmbedBuilder()
				.setTitle(getTitle(e.getUser()))
				.setDescription(e.getUser().getAsMention() + " was banned from the server")
				.setColor(Color.red)
				.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
				.build());
	}
	
	public static void log(GuildUnbanEvent e) {
		sendMessage(e.getGuild(), new EmbedBuilder()
				.setTitle(getTitle(e.getUser()))
				.setDescription(e.getUser().getAsMention() + " was unbanned")
				.setColor(Color.white)
				.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
				.build());
	}
	
	public static void log(GuildMemberJoinEvent e) {
		sendMessage(e.getGuild(), new EmbedBuilder()
				.setTitle(getTitle(e.getMember()))
				.setDescription(e.getMember().getAsMention() + " joined the server")
				.setColor(Color.green)
				.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
				.build());
	}
	
	public static void log(GuildMemberLeaveEvent e) {
		if (HelperFunctions.isNambot(e.getMember().getUser())) { return; }
		sendMessage(e.getGuild(), new EmbedBuilder()
				.setTitle(getTitle(e.getMember()))
				.setDescription(e.getMember().getAsMention() + " left the server")
				.setColor(Color.orange)
				.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
				.build());
	}
	
	public static void log(GuildMemberNickChangeEvent e) {
		sendMessage(e.getGuild(), new EmbedBuilder()
				.setTitle(getTitle(e.getMember()))
				.setDescription(e.getMember().getAsMention() + " changed their nickname from `" + e.getPrevNick() + "` to `" + e.getNewNick() + "`")
				.setColor(Color.pink)
				.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
				.build());
	}
	
	public static void log(GuildMemberRoleAddEvent e) {
		String roles = "";
		for (Role r : e.getRoles()) {
			roles += r.getAsMention() + ", ";
		}
		roles = roles.substring(0, roles.length() - 2);
		
		if (e.getRoles().size() == 1) {
			sendMessage(e.getGuild(), new EmbedBuilder()
					.setTitle(getTitle(e.getMember()))
					.setDescription(e.getMember().getAsMention() + " was added to the role " + roles)
					.setColor(Color.gray)
					.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
					.build());
		} else {
			sendMessage(e.getGuild(), new EmbedBuilder()
					.setTitle(getTitle(e.getMember()))
					.setDescription(e.getMember().getAsMention() + " was added to the roles " + roles)
					.setColor(Color.gray)
					.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
					.build());
		}
	}
	
	public static void log(GuildMemberRoleRemoveEvent e) {
		String roles = "";
		for (Role r : e.getRoles()) {
			roles += r.getAsMention() + ", ";
		}
		roles = roles.substring(0, roles.length() - 2);
		
		if (e.getRoles().size() == 1) {
			sendMessage(e.getGuild(), new EmbedBuilder()
					.setTitle(getTitle(e.getMember()))
					.setDescription(e.getMember().getAsMention() + " was removed from the role " + roles)
					.setColor(Color.lightGray)
					.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
					.build());
		} else {
			sendMessage(e.getGuild(), new EmbedBuilder()
					.setTitle(getTitle(e.getMember()))
					.setDescription(e.getMember().getAsMention() + " was removed from the roles " + roles)
					.setColor(Color.lightGray)
					.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
					.build());
		}
	}
	
	/*
	 * TEXT
	 */
	public static void log(GuildMessageDeleteEvent e) {
		// http://home.dv8tion.net:8080/job/JDA/Promoted%20Build/javadoc/net/dv8tion/jda/core/events/message/guild/GuildMessageDeleteEvent.html
		/*
		if (HelperFunctions.isNambot(e.getAuthor())) { return; }
		sendMessage(e.getGuild(), new EmbedBuilder()
				.setTitle(getTitle(e.getMember()))
				.setDescription(e.getMember().getAsMention() + " deleted message:\n```\n" + e.getMessage().getContent() + "\n```")
				.setColor(Color.cyan)
				.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
				.build());
		*/
	}
	
	/*
	 * VOICE
	 */
	public static void log(GuildVoiceJoinEvent e) {
		sendMessage(e.getGuild(), new EmbedBuilder()
				.setTitle(getTitle(e.getMember()))
				.setDescription(e.getMember().getAsMention() + " joined voice channel `" + e.getChannelJoined().getName() + '`')
				.setColor(Color.cyan)
				.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
				.build());
	}
	
	public static void log(GuildVoiceLeaveEvent e) {
		sendMessage(e.getGuild(), new EmbedBuilder()
				.setTitle(getTitle(e.getMember()))
				.setDescription(e.getMember().getAsMention() + " left voice channel `" + e.getChannelLeft().getName() + '`')
				.setColor(Color.magenta)
				.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
				.build());
	}
	
	public static void log(GuildVoiceMoveEvent e) {
		sendMessage(e.getGuild(), new EmbedBuilder()
				.setTitle(getTitle(e.getMember()))
				.setDescription(e.getMember().getAsMention() + " moved from voice channel `" + e.getChannelLeft().getName() + "` to `" + e.getChannelJoined().getName() + '`')
				.setColor(Color.blue)
				.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC))
				.build());
	}
	
	/*
	 * HelperFunctions
	 */
	private static void sendMessage(Guild g, MessageEmbed me) {
		ServerSettings s = HelperFunctions.getSettings(g);
		if (s == null || s.loggerChannel.equals("")) { return; }
		
		TextChannel tc = g.getTextChannelById(s.loggerChannel);
		if (tc == null) { return; }
		
		tc.sendMessage(me).queue();
	}
	
	private static String getTitle(User u) {
		return u.getName() + "#" + u.getDiscriminator();
	}
	
	private static String getTitle(Member m) {
		return getTitle(m.getUser());
	}
}
