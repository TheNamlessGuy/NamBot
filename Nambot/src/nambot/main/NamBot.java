package nambot.main;

import static nambot.globals.Vars.nambot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import nambot.globals.SNOWFLAKES;
import nambot.main.IO.Load;
import nambot.main.IO.Save;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageChannel;

public class NamBot {
	private static Scanner scanner;
	static volatile Thread thread;
	static volatile boolean running;

	public static void main(String[] args) throws IOException {
		try {
			scanner = new Scanner(new File("token.txt"));
			String token = scanner.useDelimiter("\\Z").next();
			scanner.close();

			nambot = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(new MessageListener()).buildBlocking();
			SNOWFLAKES.SELF = nambot.getSelfUser().getId();
			Load.all();
			nambot.getPresence().setGame(Game.watching("-help"));

			running = true;
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					MessageChannel mc = nambot.getGuildById(SNOWFLAKES.S_NTS).getTextChannelById(SNOWFLAKES.C_NTS_LOG);
					int amount = 0;
					while (running) {
						try {
							Thread.sleep(60000);
							amount++;
							if (amount >= 72) {
								Send.text(mc, "Saving...");
								Save.all();
								amount = 0;
							}
						} catch (InterruptedException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			thread.run();
		} catch (FileNotFoundException | LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void exit() {
		try {
			running = false;
			nambot.shutdown();
			Save.all();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}