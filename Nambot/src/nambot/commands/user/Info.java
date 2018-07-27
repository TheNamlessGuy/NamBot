package nambot.commands.user;

import static nambot.helpers.DiscordTransformers.getNameIndicator;
import static nambot.helpers.DiscordTransformers.getTimeFormattedString;
import static nambot.helpers.DiscordTransformers.getTimePassedString;
import static nambot.helpers.DiscordTransformers.getTimeString;

import java.awt.Color;
import java.time.OffsetDateTime;

import nambot.globals.SNOWFLAKES;
import nambot.main.Send;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Info {
	public static void cmd_info(MessageReceivedEvent e, String param) {
		MessageEmbed embed = null;
		String[] params = param.split(" ", 2);

		if (param.equals("")) {
			embed = info_user(e.getMember());
		} else if (params[0].equals("server")) {
			embed = info_server(e);
		} else if (params[0].equals("user") && params.length > 1) {
			embed = info_user(e, params[1]);
		} else if (e.getMessage().getMentionedMembers().size() > 0) {
			embed = info_user(e.getMessage().getMentionedMembers().get(0));
		} else if (params[0].equals("role") && params.length > 1) {
			embed = info_role(e, params[1]);
		} else if (e.getMessage().getMentionedRoles().size() > 0) {
			embed = info_role(e.getMessage().getMentionedRoles().get(0));
		} else if (e.getMessage().getMentionedChannels().size() > 0) {
			embed = info_channel(e);
		} else if (e.getMessage().getEmotes().size() > 0) {
			embed = info_emote(e);
		}

		if (embed == null) {
			embed = new EmbedBuilder().setColor(Color.gray).setTitle("Could not get info for '" + param + "'").build();
		}

		Send.embed(e.getChannel(), embed);
	}

	public static MessageEmbed info_server(MessageReceivedEvent e) {
		Guild g = e.getGuild();
		StringBuilder msg = new StringBuilder();
		msg.append("Owner: ").append(getNameIndicator(e.getMember())).append('\n');
		msg.append("Created: ").append(getTimeString(g.getCreationTime())).append('\n');
		msg.append("ID: ").append(g.getId()).append("\n\n");

		msg.append("Members: ").append(g.getMembers().size()).append('\n');
		msg.append("Roles: ").append(g.getRoles().size()).append('\n');
		msg.append("Text chats: ").append(g.getTextChannels().size()).append('\n');
		msg.append("Voice chats: ").append(g.getVoiceChannels().size()).append('\n');

		return new EmbedBuilder().setColor(Color.gray).setTitle("Server info for " + g.getName()).setThumbnail(g.getIconUrl()).appendDescription(msg.toString())
				.build();
	}

	private static MessageEmbed info_user(MessageReceivedEvent e, String name) {
		for (Member m : e.getGuild().getMembers()) {
			if (m.getUser().getName().equals(name)) {
				return info_user(m);
			}
		}
		return null;
	}

	private static MessageEmbed info_user(Member m) {
		EmbedBuilder eb = new EmbedBuilder().setColor(m.getColor()).setTitle("User info for " + getNameIndicator(m)).setThumbnail(m.getUser().getAvatarUrl());
		eb.addField("ID", m.getUser().getId(), false);

		OffsetDateTime creation = m.getUser().getCreationTime();
		String timePassed = getTimePassedString(m.getJoinDate(), creation);
		if (timePassed == null)
			timePassed = "the same day";
		else
			timePassed += " later";
		eb.addField("Account created", getTimeFormattedString(creation) + " (joined " + timePassed + ")", false);

		eb.addField("Joined", getTimeString(m.getJoinDate()), false);
		if (m.getUser().getId().equals(SNOWFLAKES.SELF))
			eb.addField("GitHub", "https://github.com/TheNamlessGuy/NamBot", false);

		return eb.build();
	}

	private static MessageEmbed info_role(MessageReceivedEvent e, String name) {
		for (Role r : e.getGuild().getRoles()) {
			if (r.getName().equals(name)) {
				return info_role(r);
			}
		}

		return null;
	}

	private static MessageEmbed info_role(Role r) {
		StringBuilder msg = new StringBuilder();
		msg.append("Created: ").append(getTimeString(r.getCreationTime())).append('\n');
		msg.append("ID: ").append(r.getId()).append('\n');
		msg.append("Position: ").append(r.getPosition()).append("\n\n");

		msg.append("Permissions:\n");
		for (Permission p : r.getPermissions()) {
			msg.append(p.getName()).append(", ");
		}
		msg.setLength(msg.length() - 2);
		msg.append('\n');

		return new EmbedBuilder().setColor(r.getColor()).setTitle("Role info for " + r.getName()).appendDescription(msg.toString()).build();
	}

	private static MessageEmbed info_channel(MessageReceivedEvent e) {
		TextChannel c = e.getMessage().getMentionedChannels().get(0);
		StringBuilder msg = new StringBuilder();
		msg.append("Created: ").append(getTimeString(c.getCreationTime())).append('\n');
		msg.append("ID: ").append(c.getId()).append('\n');

		return new EmbedBuilder().setColor(Color.gray).setTitle("Channel info for #" + c.getName()).appendDescription(msg.toString()).build();
	}

	private static MessageEmbed info_emote(MessageReceivedEvent e) {
		Emote em = e.getMessage().getEmotes().get(0);
		return new EmbedBuilder().setColor(Color.gray).setTitle(em.getName() + " (URL)", em.getImageUrl())
				.setDescription("ID: " + em.getId() + "\nCreated: " + getTimeString(em.getCreationTime())).setImage(em.getImageUrl()).build();
	}
}