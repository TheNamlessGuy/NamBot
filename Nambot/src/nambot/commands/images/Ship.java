package nambot.commands.images;

import static nambot.helpers.ImageProcessing.copyImage;
import static nambot.helpers.ImageProcessing.getAvatar;
import static nambot.helpers.ImageProcessing.imageToBytes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import nambot.commands.user.Help;
import nambot.main.Send;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Ship {
	private static BufferedImage ship;

	static {
		try {
			ship = ImageIO.read(new File("res/images/ship.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void cmd_ship(MessageReceivedEvent e, String param) throws MalformedURLException, IOException {
		if (e.getMessage().getMentionedUsers().size() != 2) {
			Help.sendCommandUser(e.getChannel(), "ship");
			return;
		}

		Member jack = e.getMessage().getMentionedMembers().get(0);
		Member rose = e.getMessage().getMentionedMembers().get(1);

		Send.typing(e.getChannel());
		String msg = "**" + e.getMember().getEffectiveName() + "** shipped **" + jack.getEffectiveName() + "** with **" + rose.getEffectiveName() + "**";
		Send.image(e.getChannel(), getShipImage(jack.getUser(), rose.getUser()), "ship.jpg", msg);
	}

	private static byte[] getShipImage(User jack, User rose) throws MalformedURLException, IOException {
		BufferedImage jackAvatar = getAvatar(jack.getEffectiveAvatarUrl());
		BufferedImage roseAvatar = getAvatar(rose.getEffectiveAvatarUrl());
		BufferedImage base = copyImage(ship);

		base.getGraphics().drawImage(jackAvatar, 325, 85, 100, 100, null);
		base.getGraphics().drawImage(roseAvatar, 425, 95, 100, 100, null);

		return imageToBytes(base, "JPG");
	}
}