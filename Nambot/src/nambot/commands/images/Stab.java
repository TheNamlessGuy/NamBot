package nambot.commands.images;

import static nambot.commands.images.ImageProcessing.copyImage;
import static nambot.commands.images.ImageProcessing.getAvatar;
import static nambot.commands.images.ImageProcessing.gifToBytes;
import static nambot.commands.images.ImageProcessing.rotateImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import nambot.commands.user.Help;
import nambot.main.Send;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Stab {
	private static BufferedImage stab0;
	private static BufferedImage stab1;
	private static BufferedImage stab2;
	private static BufferedImage stab3;
	private static BufferedImage stab4;

	static {
		try {
			stab0 = ImageIO.read(new File("res/images/stab/0.png"));
			stab1 = ImageIO.read(new File("res/images/stab/1.png"));
			stab2 = ImageIO.read(new File("res/images/stab/2.png"));
			stab3 = ImageIO.read(new File("res/images/stab/3.png"));
			stab4 = ImageIO.read(new File("res/images/stab/4.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void cmd_stab(MessageReceivedEvent e, String param) throws MalformedURLException, IOException {
		if (e.getMessage().getMentionedMembers().size() != 1) {
			Help.sendCommandUser(e.getChannel(), "stab");
			return;
		}

		User u = e.getMessage().getMentionedMembers().get(0).getUser();
		if (e.getAuthor().getId().equals(u.getId())) {
			Send.text(e.getChannel(), "Self harm? Please call your local suicide prevention line");
			return;
		}

		Send.typing(e.getChannel());
		Send.image(e.getChannel(), getStabGif(e.getAuthor(), u), "stab.gif", e.getAuthor().getName() + " stabs " + u.getName());
	}

	private static byte[] getStabGif(User author, User receiver) throws MalformedURLException, IOException {
		BufferedImage a1 = getAvatar(author.getAvatarUrl());
		BufferedImage a2 = rotateImage(getAvatar(receiver.getAvatarUrl()), 90);
		BufferedImage[] gif = new BufferedImage[5];
		gif[0] = copyImage(stab0);
		gif[1] = copyImage(stab1);
		gif[2] = copyImage(stab2);
		gif[3] = copyImage(stab3);
		gif[4] = copyImage(stab4);

		for (int i = 0; i < 5; i++) {
			gif[i].getGraphics().drawImage(a1, 73, 28, 70, 70, null);
			gif[i].getGraphics().drawImage(a2, 177, 149, 62, 62, null);
		}

		return gifToBytes(gif, 100);
	}
}