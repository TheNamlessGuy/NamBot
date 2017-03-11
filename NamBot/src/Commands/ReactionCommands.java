package Commands;

import static HelperPackage.HelperFunctions.*;
import static HelperPackage.SendingFunctions.*;

import java.util.Arrays;
import java.util.List;

import static HelperPackage.GlobalVars.*;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ReactionCommands {
	/*
	 * BE COOL
	 */
	public static void becool(MessageReceivedEvent event, String call) {
		sendMsg(event.getChannel(), ":sunglasses:");
	}
	
	/*
	 * BE ANGERY
	 */
	public static void beangery(MessageReceivedEvent event, String call) {
		sendMsg(event.getChannel(), ":rage:");
	}

	/*
	 * LAUGH
	 */
	public static void laugh(MessageReceivedEvent event, String call) {
		sendMsg(event.getChannel(), ":joy:");
	}
	
	/*
	 * CRY
	 */
	public static void cry(MessageReceivedEvent event, String call) {
		sendMsg(event.getChannel(), ":sob:");
	}
	
	/*
	 * BE A NORMIE
	 */
	private static List<String> normieEmotes = Arrays.asList(":joy:",
															 ":100:",
															 ":ok_hand:",
															 ":heart_eyes:");
	public static void beanormie(MessageReceivedEvent event, String call) {
		String msg = "";
		int amount = randomInt(5, 10);
		for (int i = 0; i < amount; i++) {
			msg += normieEmotes.get(randomInt(0, normieEmotes.size() - 1));
		}
		sendMsg(event.getChannel(), msg);
	}
	
	/*
	 * SHUT
	 */
	public static void shut(MessageReceivedEvent event, String call) {
		String msg = "";
		if (event.getMessage().getMentionedUsers().size() > 0) {
			for (User u : event.getMessage().getMentionedUsers()) {
				msg += u.getAsMention() + ", ";
			}
			msg = msg.substring(0, msg.length() - 2) + " ";
		}
		msg += "SHUT";
		
		//event.getMessage().deleteMessage().queue();
		sendMsg(event.getChannel(), msg);
	}
	
	/*
	 * LMAO
	 */
	public static void lmao(MessageReceivedEvent event, String call) {
		//event.getMessage().deleteMessage().queue();
		sendMsg(event.getChannel(), "LMAO");
	}
	
	/*
	 * FEELS BAD MAN
	 */
	public static void feelsbadman(MessageReceivedEvent event, String call) {
		call = convertMentions(event);
		call = call.replace(prefix + "feelsbadman", "").trim();
		if (!call.equals("")) {
			call = "**" + getEffectiveNickname(event, event.getAuthor()) + "**: " + call;
		}
		
		sendImage(event.getChannel(), "feelsbadman.png", call);
		//event.getMessage().deleteMessage().queue();
	}
	
	/*
	 * ARROGANT
	 */
	public static void arrogant(MessageReceivedEvent event, String call) {
		sendImage(event.getChannel(), "arrogant.png");
		//event.getMessage().deleteMessage().queue();
	}
	
	/*
	 * DAB
	 */
	public static void dab(MessageReceivedEvent event, String call) {
		sendImage(event.getChannel(), "dab.gif", "_**intense daberoni**_");
		//event.getMessage().deleteMessage().queue();
	}
	
	/*
	 * SAY
	 */
	public static void say(MessageReceivedEvent event, String call) {
		if (call.equals("")) { return; }
		call = convertMentions(event).replaceFirst(prefix + "say", "").trim();
		for (Emote e : event.getMessage().getEmotes()) {
			call = call.replace(':' + e.getName() + ':', e.getAsMention());
			
		}
		sendMsg(event.getChannel(), call);
	}
	
	/*
	 * SALUTE
	 */
	public static void salute(MessageReceivedEvent event, String call) {
		sendImage(event.getChannel(), "salute.jpg");
	}
	
	/*
	 * REEE
	 */
	public static void reee(MessageReceivedEvent event, String call) {
		sendMsg(event.getChannel(), "REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
	}
	
	/*
	 * WRIST
	 */
	public static void slashwrist(MessageReceivedEvent event, String call) {
		sendMsg(event.getChannel(), ":knife: :hand_splayed: :sweat_drops:");
	}
}
