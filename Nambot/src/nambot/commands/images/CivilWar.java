package nambot.commands.images;

import static nambot.helpers.General.getMemberByMention;
import static nambot.helpers.General.isUserMention;
import static nambot.helpers.ImageProcessing.copyImage;
import static nambot.helpers.ImageProcessing.getAvatar;
import static nambot.helpers.ImageProcessing.imageToBytes;
import static nambot.helpers.ImageProcessing.renderText;
import static nambot.helpers.ImageProcessing.renderTextW;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import nambot.commands.user.Help;
import nambot.main.Send;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CivilWar {
	private static BufferedImage civilwar;

	static {
		try {
			civilwar = ImageIO.read(new File("res/images/civilwar.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void cmd_civilwar(MessageReceivedEvent e, String param) throws MalformedURLException, IOException {
		String[] msg = param.split("\\|");
		if (msg.length != 2) {
			Help.sendCommandUser(e.getChannel(), "civilwar");
			return;
		}

		String[] ta = msg[0].trim().split(" ", 2);
		String[] tr = msg[1].trim().split(" ", 2);
		if (!isUserMention(ta[0]) || !isUserMention(tr[0])) {
			return;
		}

		User ua = getMemberByMention(e.getGuild(), ta[0]).getUser();
		User ur = getMemberByMention(e.getGuild(), tr[0]).getUser();

		Send.typing(e.getChannel());
		Send.image(e.getChannel(), getCivilWarImage(ua, ur, ta[1], tr[1], e.getGuild().getName()), "civilwar.jpg", "");
	}

	private static byte[] getCivilWarImage(User author, User receiver, String ta, String tr, String guildName) throws MalformedURLException, IOException {
		BufferedImage base = copyImage(civilwar);
		BufferedImage aa = getAvatar(author.getAvatarUrl());
		BufferedImage ra = getAvatar(receiver.getAvatarUrl());

		Graphics2D g = (Graphics2D) base.getGraphics();
		g.drawImage(aa, 109, 35, 330, 330, null);
		g.drawImage(ra, 182, 490, 280, 280, null);

		g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 36));
		g.setColor(Color.BLACK);

		renderTextW(g, ta, 20, 435, 589, 43, 3);
		renderTextW(g, tr, 20, 875, 589, 43, 3);
		renderText(g, guildName, 145, 1012, 312, 40, 3);

		return imageToBytes(base, "JPG");
	}
}