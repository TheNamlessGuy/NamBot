package MiniGames;

import static HelperPackage.GlobalVars.*;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TicTacToeFunctions {
	private static TicTacToePair findTicTacToePairWith(User u) {
		for (TicTacToePair p : ticTacToePairs) {
			if (p.contains(u)) {
				return p;
			}
		}
		return null;
	}
	
	private static int[] parseCall(String call) {
		int[] retval = new int[2];
		if (!(Character.isDigit(call.charAt(0)) || Character.isDigit(call.charAt(1)))) {
			return null;
		}
		retval[0] = Integer.parseInt(call.substring(0, 1));
		retval[1] = Integer.parseInt(call.substring(1, 2));
		return retval;
	}
	
	public static void handleTicTacToe(MessageReceivedEvent event, String call) {
		User u = event.getAuthor();
		TicTacToePair p = findTicTacToePairWith(u);
		if (p == null) { return; }
		
		if (call.startsWith(prefix + "place")) {
			if (!p.isUsersTurn(u)) {
				event.getPrivateChannel().sendMessage("It is not your turn yet!").queue();
				return;
			}
			
			int[] coords = parseCall(call.replaceFirst(prefix + "place", "").trim());
			if (coords == null) {
				event.getPrivateChannel().sendMessage("That was not the name of an empty tile").queue();
				return;
			}
			if (!p.canPlace(coords[0], coords[1])) {
				event.getPrivateChannel().sendMessage("You can not place a tile there, that is already occupied!").queue();
				return;
			}
			
			if (p.takeTurn(coords[0], coords[1])) {
				ticTacToePairs.remove(p);
			}
		} else if (call.startsWith(prefix + "giveuptic")) {
			p.forfeit();
			ticTacToePairs.remove(p);
		}
	}
}
