package nambot.main;

import static nambot.helpers.General.getGuildSettings;
import static nambot.helpers.General.getNamMember;
import static nambot.helpers.General.isNamless;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import nambot.commands.admin.Reactions;
import nambot.helpers.settings.GuildSettings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
	public static Map<String, Method> userCommands;
	public static Map<String, Method> adminCommands;
	public static Map<String, Method> ownerCommands;

	static {
		userCommands = new HashMap<String, Method>();
		adminCommands = new HashMap<String, Method>();
		ownerCommands = new HashMap<String, Method>();

		@SuppressWarnings("rawtypes") Class[] userClasses = { nambot.commands.user.Help.class, nambot.commands.user.General.class, nambot.commands.user.Info.class,
				nambot.commands.user.api.General.class, nambot.commands.user.store.General.class, nambot.commands.user.Zalgo.class, nambot.commands.custom.General.class,

				nambot.commands.images.Pat.class, nambot.commands.images.Stab.class, nambot.commands.images.Slap.class, nambot.commands.images.Hug.class,
				nambot.commands.images.CivilWar.class, nambot.commands.images.Ship.class };
		for (@SuppressWarnings("rawtypes") Class c : userClasses) {
			for (Method m : c.getMethods()) {
				if (m.getName().startsWith("cmd_")) {
					userCommands.put(m.getName().substring(4), m);
				}
			}
		}

		@SuppressWarnings("rawtypes") Class[] adminClasses = { nambot.commands.admin.General.class, nambot.commands.admin.Reactions.class };
		for (@SuppressWarnings("rawtypes") Class c : adminClasses) {
			for (Method m : c.getMethods()) {
				if (m.getName().startsWith("cmd_")) {
					adminCommands.put(m.getName().substring(4), m);
				}
			}
		}

		@SuppressWarnings("rawtypes") Class[] ownerClasses = { nambot.commands.owner.General.class };
		for (@SuppressWarnings("rawtypes") Class c : ownerClasses) {
			for (Method m : c.getMethods()) {
				if (m.getName().startsWith("cmd_")) {
					ownerCommands.put(m.getName().substring(4), m);
				}
			}
		}
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot()) {
			return;
		}

		getNamMember(e.getMember()).update();

		GuildSettings gs = getGuildSettings(e.getGuild());
		String message = e.getMessage().getContentRaw();
		if (!message.startsWith(gs.prefix)) {
			return;
		}

		String[] messageArray = message.substring(gs.prefix.length()).trim().split(" ", 2);
		if (messageArray.length > 1) {
			message = messageArray[1];
		} else {
			message = "";
		}

		try {
			if (userCommands.containsKey(messageArray[0])) {
				userCommands.get(messageArray[0]).invoke(null, e, message);
			} else if (gs.isAdmin(e.getMember()) && adminCommands.containsKey(messageArray[0])) {
				adminCommands.get(messageArray[0]).invoke(null, e, message, gs);
			} else if (isNamless(e.getMember()) && ownerCommands.containsKey(messageArray[0])) {
				ownerCommands.get(messageArray[0]).invoke(null, e, message);
			} else if (gs.customCommands.containsKey(messageArray[0])) {
				gs.customCommands.get(messageArray[0]).invoke(e, message);
			}
		} catch (Exception ex) {
			Send.error(e.getChannel(), ex);
		}
	}

	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
		GuildSettings gs = getGuildSettings(e.getGuild());
		String reaction = e.getReactionEmote().getName();
		if (gs.reactionChannel == null || !e.getChannel().getId().equals(gs.reactionChannel.getId()) || e.getReactionEmote().getId() != null
				|| !gs.reactToRoles.containsKey(reaction)) {
			return;
		}
		Reactions.reactionAdded(e, gs, reaction);
	}

	@Override
	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
		GuildSettings gs = getGuildSettings(e.getGuild());
		String reaction = e.getReactionEmote().getName();
		if (gs.reactionChannel == null || !e.getChannel().getId().equals(gs.reactionChannel.getId()) || e.getReactionEmote().getId() != null
				|| !gs.reactToRoles.containsKey(reaction)) {
			return;
		}
		Reactions.reactionRemoved(e, gs, reaction);
	}
}