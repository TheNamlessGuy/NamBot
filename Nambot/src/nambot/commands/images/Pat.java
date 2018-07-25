package nambot.commands.images;

import static nambot.helpers.ImageProcessing.copyImage;
import static nambot.helpers.ImageProcessing.getAvatar;
import static nambot.helpers.ImageProcessing.gifToBytes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import nambot.commands.user.Help;
import nambot.main.Send;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Pat {
	private static BufferedImage pat0;
	private static BufferedImage pat1;

	static {
		try {
			pat0 = ImageIO.read(new File("res/images/pat/0.jpg"));
			pat1 = ImageIO.read(new File("res/images/pat/1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void cmd_pat(MessageReceivedEvent e, String param) throws MalformedURLException, IOException {
		if (e.getMessage().getMentionedMembers().size() != 1) {
			Help.sendCommandUser(e.getChannel(), "pat");
			return;
		}

		User u = e.getMessage().getMentionedMembers().get(0).getUser();
		if (e.getAuthor().getId().equals(u.getId())) {
			Send.text(e.getChannel(), "You can't pat yourself. That's sad");
			return;
		}

		Send.typing(e.getChannel());
		Send.image(e.getChannel(), getPatGif(e.getAuthor(), u), "pat.gif", e.getAuthor().getName() + " pats " + u.getName());
	}

	private static byte[] getPatGif(User author, User receiver) throws MalformedURLException, IOException {
		BufferedImage mentionedAvatar = getAvatar(receiver.getAvatarUrl());
		BufferedImage writingAvatar = getAvatar(author.getAvatarUrl());
		BufferedImage[] gif = new BufferedImage[2];
		gif[0] = copyImage(pat0);
		gif[1] = copyImage(pat1);

		gif[0].getGraphics().drawImage(mentionedAvatar, 70, 160, 50, 50, null);
		gif[0].getGraphics().drawImage(writingAvatar, 150, 80, 50, 50, null);

		gif[1].getGraphics().drawImage(mentionedAvatar, 70, 160, 50, 50, null);
		gif[1].getGraphics().drawImage(writingAvatar, 150, 80, 50, 50, null);

		return gifToBytes(gif, 150);
	}
}