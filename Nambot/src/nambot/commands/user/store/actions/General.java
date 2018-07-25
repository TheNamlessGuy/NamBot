package nambot.commands.user.store.actions;

import static nambot.helpers.General.isHigherRank;
import static nambot.helpers.General.isUserMention;

import nambot.commands.user.Help;
import nambot.main.Send;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class General {
	public static boolean changenick(MessageReceivedEvent e, Integer amount, String param) {
		String[] params = param.split(" ", 2);
		if (params.length != 2 || !isUserMention(params[0])) {
			Help.sendCommandUser(e.getChannel(), "use nicknamechanger");
			return false;
		}

		Member m = e.getMessage().getMentionedMembers().get(0);
		if (m.getUser().isBot()) {
			Send.text(e.getChannel(), "Items aren't useable on bots");
			return false;
		}
		if (isHigherRank(m)) {
			Send.text(e.getChannel(), "That user is of a higher rank than me, I can't do that");
			return false;
		}

		e.getGuild().getController().setNickname(m, params[1]).queue();
		Send.text(e.getChannel(), "Nickname of " + m.getUser().getName() + " changed to " + params[1]);
		return true;
	}
}