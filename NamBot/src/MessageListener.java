import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Commands.*;

import java.lang.reflect.Method;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static HelperPackage.GlobalVars.*;
//import static HelperPackage.HelperFunctions.*;
import static HelperPackage.FightingFunctions.*;
import static HelperPackage.SendingFunctions.*;

public class MessageListener extends ListenerAdapter {	
	private static Map<String, Method> calls = new HashMap<String, Method>();
	
	private static List<String> hiddenCommands = Arrays.asList(prefix + "bv",
															   prefix + "test",
															   prefix + "help",
															   prefix + "spam",
															   prefix + "stopspam");
	
	public MessageListener() throws Exception {
		@SuppressWarnings("rawtypes")
		Class[] param = { MessageReceivedEvent.class, String.class };
		
		/*
		 * ADMIN COMMANDS
		 */
		calls.put(prefix + "cleanup", AdminCommands.class.getMethod("cleanup", param));
		calls.put(prefix + "spam", AdminCommands.class.getMethod("spam", param));
		calls.put(prefix + "stopspam", AdminCommands.class.getMethod("stopspam", param));
		calls.put(prefix + "getinfo", AdminCommands.class.getMethod("getinfo", param));

		/*
		 * USER COMMANDS
		 */
		calls.put(prefix + "whoami", UserCommands.class.getMethod("whoami", param));
		calls.put(prefix + "ratecoolness", UserCommands.class.getMethod("ratecoolness", param));
		calls.put(prefix + "vote", UserCommands.class.getMethod("vote", param));
		calls.put(prefix + "meme", UserCommands.class.getMethod("meme", param));
		calls.put(prefix + "fight", UserCommands.class.getMethod("fight", param));
		
		/*
		 * REACTION COMMANDS
		 */
		calls.put(prefix + "laugh", ReactionCommands.class.getMethod("laugh", param));
		calls.put(prefix + "becool", ReactionCommands.class.getMethod("becool", param));
		calls.put(prefix + "beangery", ReactionCommands.class.getMethod("beangery", param));
		calls.put(prefix + "beanormie", ReactionCommands.class.getMethod("beanormie", param));
		calls.put(prefix + "feelsbadman", ReactionCommands.class.getMethod("feelsbadman", param));
		calls.put(prefix + "arrogant", ReactionCommands.class.getMethod("arrogant", param));
		calls.put(prefix + "shut", ReactionCommands.class.getMethod("shut", param));
		calls.put(prefix + "lmao", ReactionCommands.class.getMethod("lmao", param));
		calls.put(prefix + "dab", ReactionCommands.class.getMethod("dab", param));
		
		/*
		 * IMAGE COMMANDS
		 */
		calls.put(prefix + "pat", ImageCommands.class.getMethod("pat", param));
		calls.put(prefix + "sorryaboutexisting", ImageCommands.class.getMethod("sorryaboutexisting", param));
		calls.put(prefix + "ship", ImageCommands.class.getMethod("ship", param));
		
		/*
		 * BACKGROUND COMMANDS
		 */
		calls.put(prefix + "bv", BackgroundCommands.class.getMethod("botvote", param));
		
		calls.put(prefix + "help", MessageListener.class.getMethod("help", param));
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String msg = event.getMessage().getContent();
		
		if (!msg.startsWith(prefix)) { return; }
		
		if (event.isFromType(ChannelType.TEXT)) {
			for (String key : calls.keySet()) {
				if (msg.startsWith(key)) {
					try {
						calls.get(key).invoke(null, event, msg.replace(key, "").trim());
					} catch (Exception e) {
						sendMsg(event.getChannel(), "Running call '" + msg + "' failed");
						e.printStackTrace();
					}
					return;
				}
			}
		} else if (event.isFromType(ChannelType.PRIVATE)) {
			if (msg.startsWith(prefix + "hit") || msg.startsWith(prefix + "block") || msg.startsWith(prefix + "giveup")) {
				handleFighting(event, msg);
			}
		}
	}
	
	public static void help(MessageReceivedEvent event, String call) {		
		String str = "```\nAvailable commands:\n\n";
		for (String key : calls.keySet()) {
			if (hiddenCommands.contains(key)) { continue; }
			str += key.substring(2) + "\n";
		}
		str += "```";
		sendMsg(event.getChannel(), str);
	}
}
