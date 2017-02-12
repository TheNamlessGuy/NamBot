package MiniGames;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class RockPaperScissorsPair extends MiniGamePair {
	public static final short NONE = 0;
	public static final short ROCK = 1;
	public static final short PAPER = 2;
	public static final short SCISSORS = 3;

	private short p1Move;
	private short p2Move;
	
	public RockPaperScissorsPair(User first, User second, MessageChannel channel) {
		super(first, second, channel);
		
		p1Move = NONE;
		p2Move = NONE;
	}
	
	public boolean canMakeMove(User u) {
		if (isFirst(u)) {
			return (p1Move == NONE);
		} else {
			return (p2Move == NONE);
		}
	}
	
	public boolean takeTurn(User u, short move) {
		if (isFirst(u)) {
			p1Move = move;
		} else {
			p2Move = move;
		}
		
		if (p1Move == NONE || p2Move == NONE) { return false; }
		
		if (p1Move > p2Move || p1Move == ROCK && p2Move == SCISSORS) {
			// p1 won
			win(true, false);
		} else {
			// p2 won
			win(false, false);
		}
		return true;
	}
	
	public void giveup(User u) {
		if (isFirst(u)) {
			win(false, true);
		} else {
			win(true, true);
		}
	}
	
	public void win(boolean firstWon, boolean forfeit) {
		if (firstWon) {
			win(first, second, forfeit, " in their game of rock paper scissors");
		} else {
			win(second, first, forfeit, " in their game of rock paper scissors");
		}			
	}
}
