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

public class Slap {
	private static BufferedImage slap0;
	private static BufferedImage slap1;
	private static BufferedImage slap2;
	private static BufferedImage slap3;
	private static BufferedImage slap4;
	private static BufferedImage slap5;
	private static BufferedImage slap6;
	private static BufferedImage slap7;
	private static BufferedImage slap8;
	private static BufferedImage slap9;
	private static BufferedImage slap10;
	private static BufferedImage slap11;
	private static BufferedImage slap12;
	private static BufferedImage slap13;
	private static BufferedImage slap14;
	private static BufferedImage slap15;
	private static BufferedImage slap16;
	private static BufferedImage slap17;

	static {
		try {
			slap0 = ImageIO.read(new File("res/images/slap/00.jpg"));
			slap1 = ImageIO.read(new File("res/images/slap/01.jpg"));
			slap2 = ImageIO.read(new File("res/images/slap/02.jpg"));
			slap3 = ImageIO.read(new File("res/images/slap/03.jpg"));
			slap4 = ImageIO.read(new File("res/images/slap/04.jpg"));
			slap5 = ImageIO.read(new File("res/images/slap/05.jpg"));
			slap6 = ImageIO.read(new File("res/images/slap/06.jpg"));
			slap7 = ImageIO.read(new File("res/images/slap/07.jpg"));
			slap8 = ImageIO.read(new File("res/images/slap/08.jpg"));
			slap9 = ImageIO.read(new File("res/images/slap/09.jpg"));
			slap10 = ImageIO.read(new File("res/images/slap/10.jpg"));
			slap11 = ImageIO.read(new File("res/images/slap/11.jpg"));
			slap12 = ImageIO.read(new File("res/images/slap/12.jpg"));
			slap13 = ImageIO.read(new File("res/images/slap/13.jpg"));
			slap14 = ImageIO.read(new File("res/images/slap/14.jpg"));
			slap15 = ImageIO.read(new File("res/images/slap/15.jpg"));
			slap16 = ImageIO.read(new File("res/images/slap/16.jpg"));
			slap17 = ImageIO.read(new File("res/images/slap/17.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void cmd_slap(MessageReceivedEvent e, String param) throws MalformedURLException, IOException {
		if (e.getMessage().getMentionedMembers().size() != 1) {
			Help.sendCommandUser(e.getChannel(), "slap");
			return;
		}

		User u = e.getMessage().getMentionedMembers().get(0).getUser();
		if (e.getAuthor().getId().equals(u.getId())) {
			Send.text(e.getChannel(), "Why would you slap yourself?");
			return;
		}

		Send.typing(e.getChannel());
		Send.image(e.getChannel(), getSlapGif(e.getAuthor(), u), "slap.gif", e.getAuthor().getName() + " slaps " + u.getName());
	}

	private static byte[] getSlapGif(User author, User receiver) throws MalformedURLException, IOException {
		BufferedImage aa = getAvatar(author.getAvatarUrl());
		BufferedImage ar = rotateImage(getAvatar(receiver.getAvatarUrl()), 90);
		BufferedImage[] gif = new BufferedImage[18];
		gif[0] = copyImage(slap0);
		gif[1] = copyImage(slap1);
		gif[2] = copyImage(slap2);
		gif[3] = copyImage(slap3);
		gif[4] = copyImage(slap4);
		gif[5] = copyImage(slap5);
		gif[6] = copyImage(slap6);
		gif[7] = copyImage(slap7);
		gif[8] = copyImage(slap8);
		gif[9] = copyImage(slap9);
		gif[10] = copyImage(slap10);
		gif[11] = copyImage(slap11);
		gif[12] = copyImage(slap12);
		gif[13] = copyImage(slap13);
		gif[14] = copyImage(slap14);
		gif[15] = copyImage(slap15);
		gif[16] = copyImage(slap16);
		gif[17] = copyImage(slap17);

		ar = rotateImage(ar, 15);
		int arx = 185;
		int ary = 200;
		int size = 45;
		gif[0].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[0].getGraphics().drawImage(aa, 114, 130, size, size, null);

		gif[1].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[1].getGraphics().drawImage(aa, 112, 128, size, size, null);

		gif[2].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[2].getGraphics().drawImage(aa, 111, 125, size, size, null);

		gif[3].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[3].getGraphics().drawImage(aa, 110, 118, size, size, null);

		gif[4].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[4].getGraphics().drawImage(aa, 106, 105, size, size, null);

		gif[5].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[5].getGraphics().drawImage(aa, 99, 95, size, size, null);

		gif[6].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[6].getGraphics().drawImage(aa, 95, 85, size, size, null);

		gif[7].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[7].getGraphics().drawImage(aa, 102, 88, size, size, null);

		gif[8].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[8].getGraphics().drawImage(aa, 105, 90, size, size, null);

		gif[9].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[9].getGraphics().drawImage(aa, 108, 92, size, size, null);

		gif[10].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[10].getGraphics().drawImage(aa, 123, 95, size, size, null);

		gif[11].getGraphics().drawImage(ar, arx, ary, size, size, null);
		gif[11].getGraphics().drawImage(aa, 130, 100, size, size, null);

		gif[12].getGraphics().drawImage(aa, 157, 68, size, size, null);
		gif[13].getGraphics().drawImage(aa, 175, 88, size, size, null);
		gif[14].getGraphics().drawImage(aa, 175, 88, size, size, null);
		gif[15].getGraphics().drawImage(aa, 175, 92, size, size, null);
		gif[16].getGraphics().drawImage(aa, 173, 96, size, size, null);
		gif[17].getGraphics().drawImage(aa, 171, 100, size, size, null);

		return gifToBytes(gif, 80);
	}
}