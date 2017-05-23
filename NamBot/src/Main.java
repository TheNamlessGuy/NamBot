import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import static HelperPackage.GlobalVars.*;
import static HelperPackage.ConsoleInputParser.*;

import java.io.File;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(new File("token.txt"));
			String token = scanner.useDelimiter("\\Z").next();
			scanner.close();
			
			setJDA(new JDABuilder(AccountType.BOT)
					  .setToken(token)
					  .addListener(new MessageListener())
					  .buildBlocking());
			
			nambot.getPresence().setGame(Game.of(prefix + "help"));
			
			scanner = new Scanner(System.in);
			String input;
			boolean running = true;
			
			while(running) {
				input = scanner.nextLine();
				parseInput(input);
			}
			scanner.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
