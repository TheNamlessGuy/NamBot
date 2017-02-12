package MiniGames;

import static HelperPackage.GlobalVars.*;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RockPaperScissorsFunctions {
	private static short parseCall(String call) {
		if (call.startsWith(prefix + "rock")) {
			return RockPaperScissorsPair.ROCK;
		} else if (call.startsWith(prefix + "paper")) {
			return RockPaperScissorsPair.PAPER;
		} else if (call.startsWith(prefix + "scissors")) {
			return RockPaperScissorsPair.SCISSORS;
		}
		return RockPaperScissorsPair.NONE;
	}
	
	private static RockPaperScissorsPair findRockPaperScissorsPairWith(User u) {
		for (RockPaperScissorsPair p : rockPaperScissorsPairs) {
			if (p.contains(u)) {
				return p;
			}
		}
		return null;
	}
	
	public static void handleRockPaperScissors(MessageReceivedEvent event, String call) {
		User u = event.getAuthor();
		RockPaperScissorsPair p = findRockPaperScissorsPairWith(u);
		if (p == null) { return; }
		
		if (call.startsWith(prefix + "giveuprpc")) {
			p.giveup(u);
			rockPaperScissorsPairs.remove(p);
		} else {
			if (!p.canMakeMove(u)) {
				event.getPrivateChannel().sendMessage("You already took your turn").queue();
				return;
			}
			
			short move = parseCall(call);
			if (move == RockPaperScissorsPair.NONE) {
				event.getPrivateChannel().sendMessage("That is not a valid input! Try " + prefix + "rock, " + prefix + "paper or " + prefix + "scissors").queue();
				return;
			}
			
			if (p.takeTurn(u, move)) {
				rockPaperScissorsPairs.remove(p);
			} else {
				p.getOtherUser(u).openPrivateChannel().queue((channel) -> {
					channel.sendMessage("The other player has chosen their action").queue();
				});
			}
		}
	}
}
