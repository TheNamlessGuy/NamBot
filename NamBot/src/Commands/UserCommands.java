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

import HelperPackage.FightingPair;
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
			sendMsg(event.getChannel(), "That's me");
		} else {
			sendMsg(event.getChannel(), "You are " + member.getEffectiveName()
					+ " (a.k.a. " + member.getUser().getName() + "), a " + member.getRoles().get(0).getName());
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
		call = "**" + event.getMember().getEffectiveName() + " asks:**\n" + call;
		sendMsg(event.getChannel(), prefix + "bv\n" + call);
		event.getMessage().deleteMessage().queue();
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
	 * FIGHT
	 */
	public static void fight(MessageReceivedEvent event, String call) {
		if (event.getMessage().getMentionedUsers().size() == 0) {
			sendMsg(event.getChannel(), "You can't fight nobody, fuccboi");
			return;
		}
		
		User mentionedUser = event.getMessage().getMentionedUsers().get(0);
		User author = event.getAuthor();
		if (mentionedUser.getId().equals(author.getId())) {
			sendMsg(event.getChannel(), "How are you gonna fight yourself, " + mentionedUser.getAsMention() + "?");
			return;
		}
		
		for (FightingPair p : fightingPeople) {
			if (p.contains(mentionedUser)) {
				sendMsg(event.getChannel(), mentionedUser.getAsMention() + " is already in a fight, let him get it over with");
				return;
			} else if (p.contains(author)) {
				sendMsg(event.getChannel(), author.getAsMention() + " is already in a fight, let him get it over with");
				return;
			}
		}

		mentionedUser.openPrivateChannel().queue((mentionedChannel) -> {
			author.openPrivateChannel().queue((authorChannel) -> {
				fightingPeople.add(new FightingPair(author, mentionedUser, event.getChannel()));
				authorChannel.sendMessage("You challenged " + mentionedUser.getName() + " to a fight! " +
										  "This system works by each turn, one of you choose to hit high or low, and the other chooses whether to block high or low!\n" +
										  "One of you will win once they land a hit the other didn't block\n" + 
										  "The following commands are available: ```\n" + prefix + "hit [high|low]\n" + prefix + "block [high|low]\n" + prefix + "giveup```\n" +
										  "It is currently your turn to hit. This will switch to block next turn, then hit the turn after that, etc").queue();
				mentionedChannel.sendMessage("You were challenged to a fight by " + author.getName() + "! " +
											 "This system works by each turn, one of you choose to hit high or low, and the other chooses whether to block high or low!\n" +
											 "One of you will win once they land a hit the other didn't block\n" + 
											  "The following commands are available: ```\n" + prefix + "hit [high|low]\n" + prefix + "block [high|low]\n" + prefix + "giveup```\n" +
											 "It is currently your turn to block. This will switch to hit next turn, then block the turn after that, etc").queue();
			});
		});
	}
}
