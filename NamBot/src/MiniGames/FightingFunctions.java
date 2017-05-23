package MiniGames;

import static HelperPackage.GlobalVars.*;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class FightingFunctions {
	public static FightingPair findPairWith(User u) {
		for (FightingPair p : fightingPairs) {
			if (p.contains(u)) {
				return p;
			}
		}
		return null;
	}
	
	public static short parseCall(String call) {
		if (call.equals("high")) {
			return FightingPair.HIGH;
		} else if (call.equals("low")) {
			return FightingPair.LOW;
		} else {
			return FightingPair.NONE;
		}
	}
	
	public static void handleFighting(MessageReceivedEvent event, String call) {
		User u = event.getAuthor();
		if (call.startsWith(prefix + "hit")) {
			FightingPair p = findPairWith(u);
			if (p == null) {
				event.getPrivateChannel().sendMessage("You aren't fighting anyone").queue();
				return;
			}
			
			if (!p.isUsersTurnToAttack(u)) {
				event.getPrivateChannel().sendMessage("It's not your turn to hit, try using the " + prefix + "block command instead!").queue();
				return;
			}
			
			short action = parseCall(call.replaceFirst(prefix + "hit", "").trim());
			if (action == FightingPair.NONE) {
				event.getPrivateChannel().sendMessage("You have to specify 'high' or 'low' with your " + prefix + "hit command!").queue();
				return;
			}
			
			if (p.takeTurn(p.whichUserIs(u), action, event.getPrivateChannel())) { // Fight is over
				fightingPairs.remove(p);
			}
			
		} else if (call.startsWith(prefix + "giveupfight")) {
			FightingPair p = findPairWith(u);
			if (p == null) {
				event.getPrivateChannel().sendMessage("You aren't fighting anyone").queue();
				return;
			}
			
			p.giveup(u);
			fightingPairs.remove(p);
		} else { // block
			FightingPair p = findPairWith(u);
			if (p == null) {
				event.getPrivateChannel().sendMessage("You aren't fighting anyone").queue();
				return;
			}
			
			if (p.isUsersTurnToAttack(u)) {
				event.getPrivateChannel().sendMessage("It's not your turn to block, try using the " + prefix + "hit command instead!").queue();
				return;
			}
			
			short action = parseCall(call.replaceFirst(prefix + "block", "").trim());
			if (action == FightingPair.NONE) {
				event.getPrivateChannel().sendMessage("You have to specify 'high' or 'low' with your " + prefix + "block command!").queue();
				return;
			}
			
			if (p.takeTurn(p.whichUserIs(u), action, event.getPrivateChannel())) { // Fight is over
				fightingPairs.remove(p);
			}
		}
	}
}
