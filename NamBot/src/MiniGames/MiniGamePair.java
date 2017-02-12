package MiniGames;

import HelperClasses.Pair;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class MiniGamePair extends Pair<User, User> {
	protected MessageChannel channel;
	
	public MiniGamePair(User first, User second, MessageChannel channel) {
		super(first, second);
		this.channel = channel;
	}

	public boolean contains(User u) {
		return (first.getId().equals(u.getId()) || second.getId().equals(u.getId()));
	}
	
	public boolean isFirst(User u) {
		return (first.getId().equals(u.getId()));
	}
	
	public User getOtherUser(User u) {
		if (isFirst(u)) {
			return second;
		}
		return first;
	}
	
	protected void win(User winner, User loser, boolean forfeit, String endMessage) {
		winner.openPrivateChannel().queue((ch) -> {
			if (forfeit) { ch.sendMessage("You won by forfeit!").queue(); }
			else { ch.sendMessage("You won!").queue(); }
		});
		loser.openPrivateChannel().queue((ch) -> {
			if (forfeit) { ch.sendMessage("You forfeited").queue(); }
			else { ch.sendMessage("You lost").queue(); }
		});
		
		String msg = winner.getAsMention() + " won over " + loser.getAsMention() + endMessage;
		if (forfeit) { msg += " due to " + loser.getAsMention() + " forfeiting"; }
		channel.sendMessage(msg).queue();
	}
}
