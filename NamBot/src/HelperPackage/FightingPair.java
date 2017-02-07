package HelperPackage;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;

public class FightingPair extends Pair<User, User> {
	public static final short NONE = 0;
	public static final short HIGH = 1;
	public static final short LOW = 2;
	
	public static final boolean FIRST = false;
	public static final boolean SECOND = true;
	
	private boolean turn;
	private MessageChannel channel;
	private short firstAction;
	private short secondAction;
	
	public FightingPair(User first, User second, MessageChannel originalChannel) {
		super(first, second);
		turn = true;
		channel = originalChannel;
		firstAction = NONE;
		secondAction = NONE;
	}
	
	public boolean isUsersTurnToAttack(User u) {
		if (turn && u.getId().equals(first.getId())) {
			return true;
		} else if (!turn && u.getId().equals(second.getId())) {
			return true;
		}
		return false;
	}
	
	public boolean whichUserIs(User u) {
		if (u.getId().equals(first.getId())) {
			return FIRST;
		} else if (u.getId().equals(second.getId())) {
			return SECOND;
		}
		return false;
	}
	
	public boolean takeTurn(boolean whichUser, short action, PrivateChannel c) {
		if (whichUser == FIRST) {
			firstAction = action;
		} else { // SECOND
			secondAction = action;
		}
		
		if (firstAction != NONE && secondAction != NONE) {
			if (checkWinner()) {
				win("You won the fight!", "You lost the fight!", false);
				return true;
			} else {
				nextTurn();
				first.openPrivateChannel().queue((channel) -> {
					channel.sendMessage("The attack was blocked successfully! Next turn!").queue();
				});
				second.openPrivateChannel().queue((channel) -> {
					channel.sendMessage("The attack was blocked successfully! Next turn!").queue();
				});
			}
		} else {
			// Notify other player of action taken
			if (whichUser == FIRST) {
				second.openPrivateChannel().queue((channel) -> {
					channel.sendMessage("Other user has selected their action for this turn").queue();
				});
			} else {
				first.openPrivateChannel().queue((channel) -> {
					channel.sendMessage("Other user has selected their action for this turn").queue();
				});
			}
		}
		return false;
	}
	
	public boolean checkWinner() {
		return (firstAction != secondAction);
	}
	
	public void nextTurn() {
		turn = !turn;
		firstAction = NONE;
		secondAction = NONE;
	}
	
	public boolean contains(User u) {
		return (first.getId().equals(u.getId()) || second.getId().equals(u.getId()));
	}
	
	public void giveup(User u) {
		// If first user gives up, second user wins (turn = false), else (turn = true)
		if (whichUserIs(u) == FIRST) {
			if (turn) { turn = false; }
		} else {
			if (!turn) { turn = true; }
		}
		win("You won by forfeit", "You forfeited", true);
	}
	
	public void win(String winmessage, String losemessage, boolean forfeit) {
		if (turn) {
			first.openPrivateChannel().queue((channel) -> {
				channel.sendMessage(winmessage).queue();
			});
			second.openPrivateChannel().queue((channel) -> {
				channel.sendMessage(losemessage).queue();
			});
			if (forfeit)
				channel.sendMessage(first.getAsMention() + " won the fight between him/her and " + second.getAsMention() + ", since " + second.getAsMention() + " forfeited!").queue();
			else
				channel.sendMessage(first.getAsMention() + " won the fight between him/her and " + second.getAsMention() + "!").queue();				
		} else {
			first.openPrivateChannel().queue((channel) -> {
				channel.sendMessage(losemessage).queue();
			});
			second.openPrivateChannel().queue((channel) -> {
				channel.sendMessage(winmessage).queue();
			});
			if (forfeit)
				channel.sendMessage(second.getAsMention() + " won the fight between him/her and " + first.getAsMention() + ", since " + first.getAsMention() + " forfeited!").queue();
			else
				channel.sendMessage(second.getAsMention() + " won the fight between him/her and " + first.getAsMention() + "!").queue();
		}
	}
}
