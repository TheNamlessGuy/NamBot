package HelperPackage;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import HelperClasses.ServerSettings;
import MiniGames.FightingPair;
import MiniGames.RockPaperScissorsPair;
import MiniGames.TicTacToePair;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

public class GlobalVars {
	public static JDA nambot;
	public static String prefix = "::";
	public static ArrayList<FightingPair> fightingPairs = new ArrayList<FightingPair>();
	public static ArrayList<TicTacToePair> ticTacToePairs = new ArrayList<TicTacToePair>();
	public static ArrayList<RockPaperScissorsPair> rockPaperScissorsPairs = new ArrayList<RockPaperScissorsPair>();
	
	public static Map<String, String> snowflakes;
	public static Map<String, ServerSettings> serversettings;
	
	/*
	 * INIT FUNCTIONS BELOW
	 */
	public static void setJDA(JDA a) {
		nambot = a;
		init_snowflakes();
		init_serversettings();
	}
	
	private static void init_snowflakes() {
		snowflakes = new HashMap<String, String>();
		
		snowflakes.put("Namless", "115877868113756162");
		snowflakes.put("Nambot", "248928488025882625");
		snowflakes.put("Asspants", "255087181784154114");
	}
	
	private static void init_serversettings() {
		serversettings = new HashMap<String, ServerSettings>();
		
		try {
			String text = new String(Files.readAllBytes(Paths.get("serversettings.json")), StandardCharsets.UTF_8);
			JSONObject obj = new JSONObject(text);
			for (String snowflake : obj.keySet()) {
				serversettings.put(snowflake, new ServerSettings(obj.getJSONObject(snowflake)));
			}
			
			for (Guild g : nambot.getGuilds()) {
				if (!serversettings.containsKey(g.getId())) {
					serversettings.put(g.getId(), new ServerSettings());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
