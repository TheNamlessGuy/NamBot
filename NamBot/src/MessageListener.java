import static HelperPackage.ConsoleInputParser.parseInput;
import static HelperPackage.GlobalVars.*;
import static HelperPackage.HelperFunctions.debug;
import static HelperPackage.HelperFunctions.err;
import static HelperPackage.HelperFunctions.getSettings;
import static HelperPackage.HelperFunctions.isNambot;
import static HelperPackage.HelperFunctions.isNamless;
import static HelperPackage.Logger.log;
import static HelperPackage.SendingFunctions.sendMsg;
import static MiniGames.FightingFunctions.handleFighting;
import static MiniGames.RockPaperScissorsFunctions.handleRockPaperScissors;
import static MiniGames.TicTacToeFunctions.handleTicTacToe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import Commands.AdminCommands;
import Commands.BackgroundCommands;
import Commands.ImageCommands;
import Commands.MiniGameCommands;
import Commands.ReactionCommands;
import Commands.UserCommands;
import HelperClasses.ServerSettings;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {	
	private static Map<String, Method> calls = new HashMap<String, Method>();
	
	public MessageListener() throws Exception {
		@SuppressWarnings("rawtypes")
		Class[] param = { MessageReceivedEvent.class, String.class };
		
		/*
		 * ADMIN COMMANDS
		 */
		calls.put(prefix + "cleanup", AdminCommands.class.getMethod("cleanup", param));
		calls.put(prefix + "getinfo", AdminCommands.class.getMethod("getinfo", param));
		calls.put(prefix + "setloggerchannel", AdminCommands.class.getMethod("setloggerchannel", param));
		calls.put(prefix + "removeloggerchannel", AdminCommands.class.getMethod("removeloggerchannel", param));
		calls.put(prefix + "addcustom", AdminCommands.class.getMethod("addcustom", param));
		calls.put(prefix + "removecustom", AdminCommands.class.getMethod("removecustom", param));
		calls.put(prefix + "customlist", AdminCommands.class.getMethod("customlist", param));

		/*
		 * USER COMMANDS
		 */
		calls.put(prefix + "whoami", UserCommands.class.getMethod("whoami", param));
		calls.put(prefix + "ratecoolness", UserCommands.class.getMethod("ratecoolness", param));
		calls.put(prefix + "vote", UserCommands.class.getMethod("vote", param));
		calls.put(prefix + "meme", UserCommands.class.getMethod("meme", param));
		calls.put(prefix + "flip", UserCommands.class.getMethod("flip", param));
		calls.put(prefix + "reverse", UserCommands.class.getMethod("reverse", param));
		
		/*
		 * REACTION COMMANDS
		 */
		calls.put(prefix + "laugh", ReactionCommands.class.getMethod("laugh", param));
		calls.put(prefix + "cry", ReactionCommands.class.getMethod("cry", param));
		calls.put(prefix + "becool", ReactionCommands.class.getMethod("becool", param));
		calls.put(prefix + "beangery", ReactionCommands.class.getMethod("beangery", param));
		calls.put(prefix + "beanormie", ReactionCommands.class.getMethod("beanormie", param));
		calls.put(prefix + "say", ReactionCommands.class.getMethod("say", param));
		calls.put(prefix + "reee", ReactionCommands.class.getMethod("reee", param));
		calls.put(prefix + "/wrist", ReactionCommands.class.getMethod("slashwrist", param));
		
		/*
		 * IMAGE COMMANDS
		 */
		calls.put(prefix + "pat", ImageCommands.class.getMethod("pat", param));
		calls.put(prefix + "sorryaboutexisting", ImageCommands.class.getMethod("sorryaboutexisting", param));
		calls.put(prefix + "ship", ImageCommands.class.getMethod("ship", param));
		calls.put(prefix + "highfive", ImageCommands.class.getMethod("highfive", param));
		calls.put(prefix + "stab", ImageCommands.class.getMethod("stab", param));
		calls.put(prefix + "rip", ImageCommands.class.getMethod("rip", param));
		calls.put(prefix + "freddyspin", ImageCommands.class.getMethod("freddyspin", param));
		
		/*
		 * MINIGAME COMMANDS
		 */
		calls.put(prefix + "fight", MiniGameCommands.class.getMethod("fight", param));
		calls.put(prefix + "tictactoe", MiniGameCommands.class.getMethod("tictactoe", param));
		calls.put(prefix + "rpc", MiniGameCommands.class.getMethod("rpc", param));
		
		/*
		 * GENERAL COMMANDS
		 */
		calls.put(prefix + "help", MessageListener.class.getMethod("help", param));
		calls.put(prefix + "testlog", MessageListener.class.getMethod("testlog", param));
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String msg = event.getMessage().getContent();
		
		if (!msg.startsWith(prefix)) { return; }
		
		if (event.isFromType(ChannelType.TEXT)) {
			String call = msg.split("\\s")[0];
			if (isNambot(event.getAuthor())) {
				if (call.equals(prefix + "bv")) {
					BackgroundCommands.botvote(event, msg.replaceFirst(call, "").trim());
				}
				return;
			}
			try {
				if (calls.get(call) != null) {
					calls.get(call).invoke(null, event, msg.replaceFirst(call, "").trim());
					return;
				}
			} catch (InvocationTargetException e) {
				err(event.getChannel(), e.getCause(), msg);
			} catch (Exception e) {
				err(event.getChannel(), e, msg);
			}
			
			// Check custom commands
			getSettings(event.getGuild()).executeCommand(event, call);
		} else if (event.isFromType(ChannelType.PRIVATE)) {
			if (msg.startsWith(prefix + "hit") || msg.startsWith(prefix + "block") || msg.startsWith(prefix + "giveupfight")) {
				handleFighting(event, msg);
			} else if (msg.startsWith(prefix + "place") || msg.startsWith(prefix + "giveuptic")) {
				handleTicTacToe(event, msg);
			} else if (msg.startsWith(prefix + "rock") || msg.startsWith(prefix + "paper") || msg.startsWith(prefix + "scissors") || msg.startsWith(prefix + "giveuprpc")) {
				handleRockPaperScissors(event, msg);
			} else if (msg.startsWith(prefix + "command") && isNamless(event.getAuthor())) {
				parseInput(event, msg.replaceFirst(prefix + "command", "").trim());
			}
		}
	}
	
	public static void help(MessageReceivedEvent event, String call) {
		sendMsg(event.getChannel(), "For a list of available commands, check:\nhttps://github.com/TheNamlessGuy/NamBot");
	}
	
	public static void testlog(MessageReceivedEvent event, String call) {
		debug("'" + event.getAuthor().getName() + "' on '" + event.getGuild().getName() + "': " + call);
	}
	
	@Override
	public void onGuildJoin(GuildJoinEvent e) { serversettings.put(e.getGuild().getId(), new ServerSettings()); }
	@Override
	public void onGuildLeave(GuildLeaveEvent e) { debug("Left server: " + e.getGuild().getName()); serversettings.remove(e.getGuild().getId()); }
	
	// Log functions
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent e) { log(e); }
	@Override
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent e) { log(e); }
	@Override
	public void onGuildVoiceMove(GuildVoiceMoveEvent e) { log(e); }
	@Override
	public void onGuildBan(GuildBanEvent e) { log(e); }
	@Override
	public void onGuildUnban(GuildUnbanEvent e) { log(e); }
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent e) { log(e); }
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent e) { log(e); }
	@Override
	public void onGuildMemberNickChange(GuildMemberNickChangeEvent e) { log(e); }
	@Override
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent e) { log(e); }
	@Override
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent e) { log(e); }
	@Override
	public void onGuildMessageDelete(GuildMessageDeleteEvent e) { log(e); }
}
