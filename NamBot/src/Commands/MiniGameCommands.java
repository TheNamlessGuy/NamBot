package Commands;

import static HelperPackage.GlobalVars.*;
import static HelperPackage.HelperFunctions.*;
import static HelperPackage.SendingFunctions.*;

import MiniGames.FightingPair;
import MiniGames.RockPaperScissorsPair;
import MiniGames.TicTacToePair;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class MiniGameCommands {
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
		if (isSame(mentionedUser, author)) {
			sendMsg(event.getChannel(), "How are you gonna fight yourself, " + mentionedUser.getAsMention() + "?");
			return;
		}
		
		for (FightingPair p : fightingPairs) {
			if (p.contains(mentionedUser)) {
				sendMsg(event.getChannel(), mentionedUser.getAsMention() + " is already in a fight, you can't fight two people at once");
				return;
			} else if (p.contains(author)) {
				sendMsg(event.getChannel(), author.getAsMention() + " is already in a fight, you can't fight two people at once");
				return;
			}
		}

		mentionedUser.openPrivateChannel().queue((mentionedChannel) -> {
			author.openPrivateChannel().queue((authorChannel) -> {
				fightingPairs.add(new FightingPair(author, mentionedUser, event.getChannel()));
				authorChannel.sendMessage("You challenged " + mentionedUser.getName() + " to a fight! " +
										  "This system works by each turn, one of you choose to hit high or low, and the other chooses whether to block high or low!\n" +
										  "One of you will win once they land a hit the other didn't block\n" + 
										  "The following commands are available: ```\n" + prefix + "hit [high|low]\n" + prefix + "block [high|low]\n" + prefix + "giveupfight```\n" +
										  "It is currently your turn to hit. This will switch to block next turn, then hit the turn after that, etc").queue();
				mentionedChannel.sendMessage("You were challenged to a fight by " + author.getName() + "! " +
											 "This system works by each turn, one of you choose to hit high or low, and the other chooses whether to block high or low!\n" +
											 "One of you will win once they land a hit the other didn't block\n" + 
											  "The following commands are available: ```\n" + prefix + "hit [high|low]\n" + prefix + "block [high|low]\n" + prefix + "giveupfight```\n" +
											 "It is currently your turn to block. This will switch to hit next turn, then block the turn after that, etc").queue();
			});
		});
	}
	
	/*
	 * TIC TAC TOE
	 */
	public static void tictactoe(MessageReceivedEvent event, String call) {
		if (event.getMessage().getMentionedUsers().size() != 1) {
			sendMsg(event.getChannel(), "You need exactly one opponent");
			return;
		}
		
		User mentionedUser = event.getMessage().getMentionedUsers().get(0);
		User author = event.getAuthor();
		if (isSame(mentionedUser, author)) {
			sendMsg(event.getChannel(), "How are you gonna play with yourself, " + mentionedUser.getAsMention() + "?");
			return;
		}

		for (TicTacToePair p : ticTacToePairs) {
			if (p.contains(mentionedUser)) {
				sendMsg(event.getChannel(), mentionedUser.getAsMention() + " is already playing, you can't play two games at once");
				return;
			} else if (p.contains(author)) {
				sendMsg(event.getChannel(), author.getAsMention() + " is already playing, you can't play two games at once");
				return;
			}
		}
		
		mentionedUser.openPrivateChannel().queue((mentionedChannel) -> {
			author.openPrivateChannel().queue((authorChannel) -> {
				ticTacToePairs.add(new TicTacToePair(author, mentionedUser, event.getChannel()));
				authorChannel.sendMessage("You challenged " + mentionedUser.getName() + " to TicTacToe!\n" +
										  "This is the current board:\n" + TicTacToePair.startingBoard() + "\n" +
										  "You can place tiles by using " + prefix + "place [number of empty tile], or give up using " + prefix + "giveuptic\n" +
										  "You are OO\nIt's your turn").queue();
				mentionedChannel.sendMessage("You were challenged to TicTacToe by " + author.getName() + "!\n" +
						  					 "This is the current board:\n" + TicTacToePair.startingBoard() + "\n" +
						  					 "You can place tiles by using " + prefix + "place [number of empty tile], or give up using " + prefix + "giveuptic\n" +
						  					 "You are XX\nIt's your opponents turn").queue();
			});
		});
	}
	
	/*
	 * ROCK PAPER SCISSORS
	 */
	public static void rpc(MessageReceivedEvent event, String call) {
		if (event.getMessage().getMentionedUsers().size() != 1) {
			sendMsg(event.getChannel(), "You need exactly one opponent");
			return;
		}
		
		User mentionedUser = event.getMessage().getMentionedUsers().get(0);
		User author = event.getAuthor();
		if (isSame(mentionedUser, author)) {
			sendMsg(event.getChannel(), "How are you gonna play with yourself, " + mentionedUser.getAsMention() + "?");
			return;
		}

		for (RockPaperScissorsPair p : rockPaperScissorsPairs) {
			if (p.contains(mentionedUser)) {
				sendMsg(event.getChannel(), mentionedUser.getAsMention() + " is already playing, you can't play two games at once");
				return;
			} else if (p.contains(author)) {
				sendMsg(event.getChannel(), author.getAsMention() + " is already playing, you can't play two games at once");
				return;
			}
		}
		
		mentionedUser.openPrivateChannel().queue((mentionedChannel) -> {
			author.openPrivateChannel().queue((authorChannel) -> {
				rockPaperScissorsPairs.add(new RockPaperScissorsPair(author, mentionedUser, event.getChannel()));
				authorChannel.sendMessage("You challenged " + mentionedUser.getName() + " to Rock Paper Scisssors!\n" +
										  "You can select your action by using either " + prefix + "rock, " + prefix + "paper or " + prefix + "scissors, or use " + prefix + "giveuprpc if you want to forfeit").queue();
				mentionedChannel.sendMessage("You were challenged to Rock Paper Scissors by " + author.getName() + "!\n" +
											 "You can select your action by using either " + prefix + "rock, " + prefix + "paper or " + prefix + "scissors, or use " + prefix + "giveuprpc if you want to forfeit").queue();
			});
		});
	}
}
