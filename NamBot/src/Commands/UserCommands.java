package Commands;

import static HelperPackage.HelperFunctions.*;
import static HelperPackage.GlobalVars.*;
import static HelperPackage.SendingFunctions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UserCommands {
	/*
	 * WHO AM I
	 */
	public static void whoami(MessageReceivedEvent event, String call) {
		Member member = event.getGuild().getMember(getFirstMentionOrAuthor(event));
		
		if (isNamless(member.getUser())) {
			sendMsg(event.getChannel(), member.getAsMention() + " is my creator, the coolest guy ever! :sunglasses: <-- Literally him");
		} else if (isNambot(member.getUser())) {
			sendMsg(event.getChannel(), "https://github.com/TheNamlessGuy/NamBot");
		} else {
			String msg = "You are " + member.getEffectiveName() + "(a.k.a. " + member.getUser().getName() + ")";
			if (member.getRoles().size() > 0) {
				msg += ", a" + member.getRoles().get(0).getName();
			}
			sendMsg(event.getChannel(), msg);
		}
	}
	
	/*
	 * RATE COOLNESS
	 */
	private static List<String> coolnessComments = Arrays.asList("You should probably just kill yourself at this point", 										  // 0
																 "Nice glasses, nerd", 																			  // 1
																 "So do you still wet the bed or what", 														  // 2
																 "You're getting there! Keep working on it!", 													  // 3
																 "Just below average... shame", 																  // 4
																 "There is nothing shameful about being average. You are exactly like at least 2 billion others", // 5
																 "Slightly above average? Way to go!", 															  // 6
																 "Hey congrats! Now you can laugh at those below you", 											  // 7
																 "Hey, at least 8 sideways is infinity, so you're close to infinitely cool", 					  // 8
																 "So close yet so far. At least you aren't a 0, right?", 										  // 9
																 "Damn dude"); 																					  // 10
	
	public static void ratecoolness(MessageReceivedEvent event, String call) {
		User mentionedUser = getFirstMentionOrAuthor(event);
		
		if (isNamless(mentionedUser)) {
			sendMsg(event.getChannel(), "My creator, TheNamlessGuy, is always 10/10 on the coolness scale");
		} else if (isNambot(mentionedUser)) {
			sendMsg(event.getChannel(), "I'm pretty cool if I do say so myself :sunglasses:");
		} else if (mentionedUser.getId().equals(snowflakes.get("Asspants"))) {
			sendMsg(event.getChannel(), "You made me put this in, but whatever, you can be 10/10 if you want I guess...");
		} else {
			Random random = new Random(Integer.parseInt(mentionedUser.getDiscriminator()));
			int coolness = random.nextInt(11);
			sendMsg(event.getChannel(), "I'll give " + mentionedUser.getAsMention() + " a coolness rating of "
					+ Integer.toString(coolness) + "/10\n" + coolnessComments.get(coolness));
		}
	}
	
	/*
	 * VOTE
	 */
	public static void vote(MessageReceivedEvent event, String call) {
		call = convertMentions(event).replace(prefix + "vote", "").trim();
		call = "**" + event.getMember().getEffectiveName() + " asks:**\n" + call;
		sendMsg(event.getChannel(), prefix + "bv\n" + call);
		//event.getMessage().deleteMessage().queue();
	}
	
	/*
	 * MEME
	 */
	public static void meme(MessageReceivedEvent event, String call) {
		String msg = "**" + getEffectiveNickname(event, event.getAuthor()) + "** generated this meme:\n";
		File[] memes = new File("res/memes/").listFiles();
		int index = -1;
		if (call.equals("")) {
			index = randomInt(0, memes.length - 1);
		} else if (call.equals("<LIST>")) {
			msg = "```\nAvailable memes:\n\n";
			for (File f : memes) {
				msg += f.getName().replace("." + getFileEnd(f), "") + "\n";
			}
			msg += "```";
			sendMsg(event.getChannel(), msg);
			return;
		} else {
			for (int i = 0; i < memes.length; i++) {
				if (memes[i].getName().contains(call)) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				sendMsg(event.getChannel(), "Could not find meme by the name '" + call + "'");
				return;
			}
		}
		
		if (isImage(memes[index])) {
			sendImage(event.getChannel(), memes[index], msg);
		} else {
			try {
				msg += new String(Files.readAllBytes(memes[index].toPath()), "UTF-8");
				sendMsg(event.getChannel(), msg);
			} catch (IOException e) {
				// no
			}
		}
	}
	
	/*
	 * FLIP
	 */
	private static String[] fliparray = {"ɐ", "q", "ɔ", "p", "ǝ", "ɟ", "ƃ", "ɥ", "ᴉ", "ɾ", "ʞ", "l", "ɯ", "u", "o", "d", "b", "ɹ", "s", "ʇ", "n", "ʌ", "ʍ", "x", "ʎ", "z"};
	public static void flip(MessageReceivedEvent event, String call) {
		if (call.length() == 0) { return; }
		call = call.toLowerCase();
		boolean reverse = false;
		if (call.contains("--reverse")) {
			reverse = true;
			call = call.replace("--reverse", "").trim();
		}
		
		String msg = "";
		for (int i = 0; i < call.length(); i++) {
			int index = ((int) call.charAt(i)) - 97;
			if (index < 0 || index > 25) {
				msg += call.charAt(i);
			} else {
				msg += fliparray[index];
			}
		}
		
		if (reverse) {
			msg = new StringBuilder(msg).reverse().toString();
		}
		
		sendMsg(event.getChannel(), msg);
	}
}
