package nambot.commands.admin;

import static nambot.helpers.DiscordTransformers.getAsMention;
import static nambot.helpers.Number.clamp;
import static nambot.helpers.Number.isInt;
import static nambot.helpers.Number.lclamp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import nambot.commands.user.Help;
import nambot.helpers.settings.GuildSettings;
import nambot.main.Send;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class General {
	public static void cmd_setprefix(MessageReceivedEvent e, String param, GuildSettings gs) {
		if (param.equals("") || param.contains(" ") || param.length() > 5) {
			Help.sendCommandAdmin(e.getChannel(), "setprefix");
			return;
		}

		String prev = gs.prefix;
		gs.prefix = param;

		MessageEmbed embed = new EmbedBuilder().setColor(Color.green).setTitle("Prefix change").setDescription("Prefix changed from `" + prev + "` to `" + param + "`").build();
		if (gs.logChannel != null) {
			Send.log(gs, embed);
		}
		Send.embed(e.getChannel(), embed);
	}

	public static void cmd_setlogchannel(MessageReceivedEvent e, String param, GuildSettings gs) {
		gs.logChannel = e.getChannel();
		Send.log(gs, new EmbedBuilder().setColor(Color.green).setTitle("Log channel change (" + e.getChannel().getName() + ")")
				.setDescription("Set log channel to " + getAsMention(e.getChannel())).build());
	}

	public static void cmd_clear(MessageReceivedEvent e, String param, GuildSettings gs) {
		int amount = 26;
		if (!param.equals("") && isInt(param)) {
			amount = clamp(1, Integer.parseInt(param) + 1, 101);
		}

		int i = 0;
		for (Message m : e.getChannel().getIterableHistory()) {
			m.delete().queue();
			if (++i >= amount) {
				break;
			}
		}

		MessageEmbed embed = new EmbedBuilder().setColor(Color.yellow).setTitle("Clear command (" + e.getChannel().getName() + ")")
				.setDescription("Cleared " + (amount - 1) + " messages in channel " + getAsMention(e.getChannel())).build();
		if (gs.logChannel != null) {
			Send.log(gs, embed);
		} else {
			Send.embed(e.getChannel(), embed);
		}
	}

	public static void cmd_adminrole(MessageReceivedEvent e, String param, GuildSettings gs) {
		String[] params = param.split(" ", 2);
		if (params[0].equals("add")) {
			adminrole_add(e, gs, (params.length == 1 ? "" : params[1]));
		} else if (params[0].equals("remove")) {
			adminrole_remove(e, gs, (params.length == 1 ? "" : params[1]));
		} else if (params[0].equals("list")) {
			adminrole_list(e, gs, (params.length == 1 ? "" : params[1]));
		} else {
			Help.sendCommandAdmin(e.getChannel(), "adminrole");
		}
	}

	public static void adminrole_add(MessageReceivedEvent e, GuildSettings gs, String param) {
		String roleName = null;
		String roleID = null;
		if (e.getMessage().getMentionedRoles().size() > 0) {
			Role r = e.getMessage().getMentionedRoles().get(0);
			roleName = r.getName();
			roleID = r.getId();
		} else {
			for (Role r : gs.guild.getRoles()) {
				if (r.getName().equals(param)) {
					roleName = r.getName();
					roleID = r.getId();
					break;
				}
			}

			if (roleName == null || roleID == null) {
				Send.text(e.getChannel(), "Could not find role `" + param + "`");
				return;
			}
		}

		if (gs.adminRoles.contains(roleID)) {
			Send.text(e.getChannel(), "The role `" + roleName + "` is already an admin role");
			return;
		}
		gs.adminRoles.add(roleID);
		Send.text(e.getChannel(), "The role `" + roleName + "` added as admin role");

		if (gs.logChannel != null) {
			Send.log(gs, new EmbedBuilder().setColor(Color.green).setTitle("Admin role added").setDescription("Role `" + roleName + "` added as admin role").build());
		}
	}

	public static void adminrole_remove(MessageReceivedEvent e, GuildSettings gs, String param) {
		String roleName = null;
		String roleID = null;
		if (e.getMessage().getMentionedRoles().size() > 0) {
			Role r = e.getMessage().getMentionedRoles().get(0);
			roleName = r.getName();
			roleID = r.getId();
		} else {
			for (Role r : gs.guild.getRoles()) {
				if (r.getName().equals(param)) {
					roleName = r.getName();
					roleID = r.getId();
					break;
				}
			}

			if (roleName == null || roleID == null) {
				Send.text(e.getChannel(), "Could not find role `" + param + "`");
				return;
			}
		}

		if (!gs.adminRoles.contains(roleID)) {
			Send.text(e.getChannel(), "The role `" + roleName + "` is not an admin role");
			return;
		}
		gs.adminRoles.remove(roleID);
		Send.text(e.getChannel(), "The role `" + roleName + "` removed as admin role");

		if (gs.logChannel != null) {
			Send.log(gs, new EmbedBuilder().setColor(Color.red).setTitle("Admin role removed").setDescription("Role `" + roleName + "` removed as admin role").build());
		}
	}

	public static void adminrole_list(MessageReceivedEvent e, GuildSettings gs, String param) {
		int page = 1;
		if (isInt(param)) {
			page = lclamp(1, Integer.parseInt(param));
		}

		List<String> list = new ArrayList<String>();
		for (String id : gs.adminRoles) {
			list.add(gs.guild.getRoleById(id).getName());
		}

		Send.list(e.getChannel(), page, list, "Admin roles in " + gs.guild.getName(), 10);
	}
}