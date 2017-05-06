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
			sendMsg(event.getChannel(), "You have to mention a user");
			return;
		}
		User mentionedUser = event.getMessage().getMentionedUsers().get(0);
		
		if (isNambot(mentionedUser)) {
			sendMsg(event.getChannel(), "Don't pat me you creepy fucker");
			return;
		} else if (isSame(mentionedUser, event.getAuthor())) {
			sendMsg(event.getChannel(), "Patting yourself is kinda sad, **" + getEffectiveNickname(event, mentionedUser) + "**");
			return;
		}
		
		sendTyping(event.getChannel());
		
		String msg = "**" + getEffectiveNickname(event, mentionedUser) + "**";
		msg += " gets a pat from ";
		msg += "**" + getEffectiveNickname(event, event.getAuthor()) + "**";
		String leftovers = call.replace("@" + getEffectiveNickname(event, mentionedUser), "").trim();
		if (!leftovers.equals("")) {
			msg += ", who says \"" + leftovers + "\"";
		}
		
		sendImage(event.getChannel(), getPatGif(event.getChannel(), mentionedUser.getEffectiveAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl()), "pat.gif", msg);
	}
	
	/*
	 * SORRY ABOUT EXISTING
	 */
	public static void sorryaboutexisting(MessageReceivedEvent event, String call) {
		User u = getFirstMentionOrAuthor(event);
		
		if (isNambot(u)) {
			sendMsg(event.getChannel(), "I will never be sorry about existing!");
			return;
		}
		
		sendTyping(event.getChannel());		
		sendImage(event.getChannel(), getSorryAboutExistingImage(event.getChannel(), u.getEffectiveAvatarUrl()), "sorryaboutexisting.png");
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

		sendTyping(event.getChannel());
		String msg = "**" + getEffectiveNickname(event, event.getAuthor()) + "** shipped **" + getEffectiveNickname(event, jack) + "** with **" + getEffectiveNickname(event, rose) + "**";
		sendImage(event.getChannel(), getShipImage(event.getChannel(), jack.getEffectiveAvatarUrl(), rose.getEffectiveAvatarUrl()), "ship.jpg", msg);
	}
	
	/*
	 * HIGHFIVE
	 */
	public static void highfive(MessageReceivedEvent event, String call) {
		if (event.getMessage().getMentionedUsers().size() == 0) {
			sendMsg(event.getChannel(), "You have to mention a user");
			return;
		}
		
		User u1 = event.getAuthor();
		User u2 = event.getMessage().getMentionedUsers().get(0);
		
		if (isNambot(u2) && !isNamless(u1)) {
			sendMsg(event.getChannel(), "Sorry, I only highfive cool people");
			return;
		} else if (isSame(u1, u2)) {
			sendMsg(event.getChannel(), "You can't highfive yourself dude");
			return;
		}

		sendTyping(event.getChannel());
		
		String msg = "**" + getEffectiveNickname(event, u1) + "** highfives **" + getEffectiveNickname(event, u2) + "**";
		call = call.replace("@" + getEffectiveNickname(event, u2), "").trim();
		if (!call.equals("")) {
			msg += " and says **" + call + "**";
		}
		
		sendImage(event.getChannel(), getHighfiveGif(event.getChannel(), u1.getEffectiveAvatarUrl(), u2.getEffectiveAvatarUrl()), "highfive.gif", msg);
	}
	
	/*
	 * STAB
	 */
	public static void stab(MessageReceivedEvent event, String call) {
		if (event.getMessage().getMentionedUsers().size() == 0) {
			sendMsg(event.getChannel(), "You have to mention a user");
			return;
		}
		
		User u1 = event.getAuthor();
		User u2 = event.getMessage().getMentionedUsers().get(0);
		
		if (isNambot(u2)) {
			sendMsg(event.getChannel(), "Don't stab me you weirdo");
			return;
		} else if (isSame(u1, u2)) {
			sendMsg(event.getChannel(), "Stabbing yourself? You should go see a psychiatrist");
			return;
		}

		sendTyping(event.getChannel());
		
		call = call.replace("@" + getEffectiveNickname(event, u2), "").trim();
		if (call.equals("")) {
			sendImage(event.getChannel(), getStabGif(event.getChannel(), u1.getEffectiveAvatarUrl(), u2.getEffectiveAvatarUrl()), "stab.gif");
		} else {
			call = "**" + getEffectiveNickname(event, u1) + "**: " + call;
			sendImage(event.getChannel(), getStabGif(event.getChannel(), u1.getEffectiveAvatarUrl(), u2.getEffectiveAvatarUrl()), "stab.gif", call);
		}
	}
	
	/*
	 * RIP
	 */
	public static void rip(MessageReceivedEvent event, String call) {
		if (call.length() == 0) {
			sendMsg(event.getChannel(), "You need to mention a user or a short phrase");
			return;
		}
		
		sendTyping(event.getChannel());
		
		if (event.getMessage().getMentionedUsers().size() > 0) {
			User u = event.getMessage().getMentionedUsers().get(0);
			sendImage(event.getChannel(), getRIPImage(event.getChannel(), getEffectiveNickname(event, u), u.getEffectiveAvatarUrl()), "rip.png");
		} else {
			sendImage(event.getChannel(), getRIPImage(event.getChannel(), call, ""), "rip.png");
		}
	}
	
	/*
	 * FREDDYSPIN
	 */
	public static void freddyspin(MessageReceivedEvent event, String call) {
		sendTyping(event.getChannel());
		sendImage(event.getChannel(), "freddy.gif", "<@115469753019138057>");
	}
}
