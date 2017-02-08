package Commands;

import static HelperPackage.HelperFunctions.*;
import static HelperPackage.ImageGenerators.*;
import static HelperPackage.SendingFunctions.*;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ImageCommands {
	/*
	 * PAT
	 */
	public static void pat(MessageReceivedEvent event, String call) {
		if (event.getMessage().getMentionedUsers().size() == 0) {
			if (isNamless(event.getAuthor())) {
				sendMsg(event.getChannel(), "Oh no, master! You have to mention a user as well!");
			} else {
				sendMsg(event.getChannel(), "You have to mention a user, you massive cumstain");
			}
			return;
		}
		User mentionedUser = event.getMessage().getMentionedUsers().get(0);
		
		if (isNambot(mentionedUser)) {
			sendMsg(event.getChannel(), "Don't pat me you creepy fucker");
			return;
		} else if (mentionedUser.getId().equals(event.getAuthor().getId())) {
			sendMsg(event.getChannel(), "Patting yourself is kinda sad, " + mentionedUser.getAsMention());
			event.getMessage().deleteMessage().queue();
			return;
		}
		
		String msg = mentionedUser.getAsMention();
		msg += " gets a pat from ";
		msg += event.getAuthor().getAsMention();
		String leftovers = call.replace("@" + getEffectiveNickname(event, mentionedUser), "").trim();
		if (!leftovers.equals("")) {
			msg += ", who says \"" + leftovers + "\"";
		}
		
		event.getMessage().deleteMessage().queue();
		sendImage(event.getChannel(), getPatGif(mentionedUser.getEffectiveAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl()), "pat.gif", msg);
	}
	
	/*
	 * SORRY FOR EXISTING
	 */
	public static void sorryaboutexisting(MessageReceivedEvent event, String call) {
		User u = getFirstMentionOrAuthor(event);
		
		if (isNambot(u)) {
			sendMsg(event.getChannel(), "I will never be sorry about existing!");
			return;
		}
		
		sendImage(event.getChannel(), getSorryAboutExistingImage(u.getEffectiveAvatarUrl()), "sorryaboutexisting.png");
		//event.getChannel().sendFile(getSorryAboutExistingImage(u.getEffectiveAvatarUrl()), "sorryaboutexisting.png", new MessageBuilder().append("aa").build()).queue();
	}
	
	/*
	 * SHIP
	 */
	public static void ship(MessageReceivedEvent event, String call) {
		if (event.getMessage().getMentionedUsers().size() != 2) {
			sendImage(event.getChannel(), "ship.jpg", "_**boat noises**_");
			return;
		}

		User jack = event.getMessage().getMentionedUsers().get(0);
		User rose = event.getMessage().getMentionedUsers().get(1);
		
		if (isNambot(jack) || isNambot(rose)) {
			sendMsg(event.getChannel(), "I'm a strong independent black bot who don't need no ship");
			return;
		}
		
		String msg = "**" + getEffectiveNickname(event, event.getAuthor()) + "** shipped **" + getEffectiveNickname(event, jack) + "** with **" + getEffectiveNickname(event, rose) + "**";
		sendImage(event.getChannel(), getShipImage(jack.getEffectiveAvatarUrl(), rose.getEffectiveAvatarUrl()), "ship.jpg", msg);
		//sendImage(event.getChannel(), getShipImage(jack.getEffectiveAvatarUrl(), rose.getEffectiveAvatarUrl()), msg);
	}
}
