package nambot.commands.admin;

import java.awt.Color;

import nambot.commands.user.Help;
import nambot.helpers.settings.GuildSettings;
import nambot.main.Send;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;

public class Reactions {
	public static void cmd_reacttorole(MessageReceivedEvent e, String param, GuildSettings gs) {
		if (param.equals("channel set") || param.equals("channel remove")) {
			reacttorole_channel(e, gs, param.split(" ")[1]);
		} else if (param.startsWith("add ") && nambot.helpers.General.countChars("\\s", param) == 3 && e.getMessage().getMentionedRoles().size() == 1) {
			String[] split = param.split(" ");
			reacttorole_add(e, gs, split[1], split[2], split[3]);
		} else if (param.startsWith("remove ") && nambot.helpers.General.countChars("\\s", param) == 1) {
			reacttorole_remove(e, gs, param.split(" ")[1]);
		} else {
			Help.sendCommandAdmin(e.getChannel(), "reacttorole");
		}
	}

	private static void reacttorole_channel(MessageReceivedEvent e, GuildSettings gs, String todo) {
		MessageEmbed embed = null;
		if (todo.equals("set")) {
			gs.reactionChannel = e.getChannel();
			embed = new EmbedBuilder().setColor(Color.green).setTitle("ReactToRole channel selected").setDescription("ReactToRole channel set to <#" + e.getChannel().getId() + ">")
					.build();
		} else if (todo.equals("remove")) {
			gs.reactionChannel = null;
			embed = new EmbedBuilder().setColor(Color.red).setTitle("ReactToRole channel removed").build();
		}
		Send.log(gs, embed);
		Send.embed(e.getChannel(), embed);
	}

	private static void reacttorole_add(MessageReceivedEvent e, GuildSettings gs, String role, String id, String reaction) {
		if (reaction.startsWith("<") && reaction.endsWith(">")) {
			Send.text(e.getChannel(), "Cannot use custom reactions for role selection");
			return;
		}
		if (gs.reactionChannel == null) {
			Send.text(e.getChannel(), "No reaction channel set for this server");
			return;
		}
		if (gs.reactToRoles.containsKey(reaction)) {
			Send.text(e.getChannel(), "A ReactToRole with the reaction " + reaction + " already exists");
			return;
		}

		try {
			gs.reactionChannel.getMessageById(id).complete();
		} catch (@SuppressWarnings("unused") Exception ex) {
			Send.text(e.getChannel(), "No message of the ID " + id + " could be found in the reaction channel");
			return;
		}

		Role r = e.getMessage().getMentionedRoles().get(0);
		gs.reactToRoles.put(reaction, r);
		Send.reaction(gs.reactionChannel, id, reaction);

		MessageEmbed embed = new EmbedBuilder().setColor(Color.green).setTitle("New ReactToRole")
				.setDescription("ReactToRole bind " + reaction + " -> " + r.getAsMention() + " added").build();
		Send.log(gs, embed);
		Send.embed(e.getChannel(), embed);
	}

	private static void reacttorole_remove(MessageReceivedEvent e, GuildSettings gs, String reaction) {
		if (gs.reactToRoles.containsKey(reaction)) {
			gs.reactToRoles.remove(reaction);
			Send.text(e.getChannel(), "ReactToRole " + reaction + " removed. Don't forget to remove the reactions.");
		} else {
			Send.text(e.getChannel(), "No ReactToRole found with the reaction " + reaction);
		}
	}

	public static void reactionAdded(GuildMessageReactionAddEvent e, GuildSettings gs, String reaction) {
		e.getGuild().getController().addSingleRoleToMember(e.getMember(), gs.reactToRoles.get(reaction)).queue();
	}

	public static void reactionRemoved(GuildMessageReactionRemoveEvent e, GuildSettings gs, String reaction) {
		e.getGuild().getController().removeSingleRoleFromMember(e.getMember(), gs.reactToRoles.get(reaction)).queue();
	}
}
