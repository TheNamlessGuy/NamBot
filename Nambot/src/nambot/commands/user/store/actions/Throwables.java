package nambot.commands.user.store.actions;

import static nambot.helpers.General.getNamMember;

import nambot.commands.user.Help;
import nambot.main.Send;
import nambot.settings.NamMember;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Throwables {
	public static boolean throwtomato(MessageReceivedEvent e, Integer amount) {
		if (e.getMessage().getMentionedMembers().size() != 1) {
			Help.sendCommandUser(e.getChannel(), "use tomato");
			return false;
		}

		NamMember nm = getNamMember(e.getMessage().getMentionedMembers().get(0));
		if (nm == null) {
			Send.text(e.getChannel(), "Items aren't useable on bots");
			return false;
		}

		nm.addEXP(amount);
		Send.text(e.getChannel(), e.getAuthor().getName() + " threw " + ((amount == 1) ? "a **tomato**" : amount + " **tomatoes**") + " at " + nm.getName()
				+ ".\n" + nm.getName() + " gained " + amount + " EXP!");
		return true;
	}

	public static boolean throwrock(MessageReceivedEvent e, Integer amount) {
		if (e.getMessage().getMentionedMembers().size() != 1) {
			Help.sendCommandUser(e.getChannel(), "use rock");
			return false;
		}

		NamMember nm = getNamMember(e.getMessage().getMentionedMembers().get(0));
		if (nm == null) {
			Send.text(e.getChannel(), "Items aren't useable on bots");
			return false;
		}

		nm.removeEXP(amount);
		Send.text(e.getChannel(), e.getAuthor().getName() + " threw " + ((amount == 1) ? "a **rock**" : amount + " **rocks**") + " at " + nm.getName() + ".\n"
				+ nm.getName() + " lost " + amount + " EXP!");
		return true;
	}
}