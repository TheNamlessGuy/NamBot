package HelperPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.core.JDA;

public class GlobalVars {
	public static JDA nambot;
	public static String prefix = "::";
	public static ArrayList<FightingPair> fightingPeople = new ArrayList<FightingPair>();
	
	public static Map<String, String> snowflakes = init_snowflakes();
	
	/*
	 * INIT FUNCTIONS BELOW
	 */
	private static Map<String, String> init_snowflakes() {
		Map<String, String> m = new HashMap<String, String>();
		
		m.put("Namless", "115877868113756162");
		m.put("Nambot", "248928488025882625");
		m.put("Asspants", "255087181784154114");
		m.put("S-ASSPANTS", "271260334269005824");
		m.put("S-NAMTEST", "248950421165441025");
		
		return m;
	}
}
