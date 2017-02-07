import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
//import net.dv8tion.jda.core.entities.Guild;

import static HelperPackage.GlobalVars.*;
//import static HelperPackage.HelperFunctions.*;
import static HelperPackage.ConsoleInputParser.*;

import java.io.File;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(new File("token.txt"));
			String token = scanner.useDelimiter("\\Z").next();
			scanner.close();
			
			nambot = new JDABuilder(AccountType.BOT)
					  .setToken(token)
					  .addListener(new MessageListener())
					  .buildBlocking();
			
			nambot.getPresence().setGame(Game.of(prefix + "help"));
			
			scanner = new Scanner(System.in);
			String input;
			boolean running = true;
			
			while(running) {
				System.out.print("> ");
				input = scanner.nextLine();
				parseInput(input);
			}
			scanner.close();
		}
		catch (LoginException e) {
			System.out.println("LoginException");
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
