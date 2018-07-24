package nambot.main;

import static nambot.globals.Vars.nambot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import nambot.globals.SNOWFLAKES;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class NamBot {
	private static Scanner scanner;

	public static void main(String[] args) throws IOException {
		try {
			scanner = new Scanner(new File("token.txt"));
			String token = scanner.useDelimiter("\\Z").next();
			scanner.close();

			nambot = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(new MessageListener()).buildBlocking();
			SNOWFLAKES.SELF = nambot.getSelfUser().getId();
			IO.load();
			nambot.getPresence().setGame(Game.watching("-help"));
		} catch (FileNotFoundException | LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void exit() {
		try {
			nambot.shutdown();
			IO.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}