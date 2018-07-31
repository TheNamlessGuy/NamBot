package nambot.commands.images;

import static nambot.commands.images.ImageProcessing.copyImage;
import static nambot.commands.images.ImageProcessing.getAvatar;
import static nambot.commands.images.ImageProcessing.gifToBytes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import nambot.commands.user.Help;
import nambot.main.Send;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Hug {
	private static BufferedImage hug0;
	private static BufferedImage hug1;
	private static BufferedImage hug2;
	private static BufferedImage hug3;
	private static BufferedImage hug4;
	private static BufferedImage hug5;
	private static BufferedImage hug6;
	private static BufferedImage hug7;
	private static BufferedImage hug8;
	private static BufferedImage hug9;
	private static BufferedImage hug10;
	private static BufferedImage hug11;
	private static BufferedImage hug12;
	private static BufferedImage hug13;
	private static BufferedImage hug14;
	private static BufferedImage hug15;
	private static BufferedImage hug16;
	private static BufferedImage hug17;
	private static BufferedImage hug18;
	private static BufferedImage hug19;
	private static BufferedImage hug20;
	private static BufferedImage hug21;
	private static BufferedImage hug22;
	private static BufferedImage hug23;
	private static BufferedImage hug24;
	private static BufferedImage hug25;

	private static BufferedImage hug10_2;
	private static BufferedImage hug11_2;
	private static BufferedImage hug12_2;
	private static BufferedImage hug13_2;
	private static BufferedImage hug14_2;
	private static BufferedImage hug15_2;
	private static BufferedImage hug16_2;

	static {
		try {
			hug0 = ImageIO.read(new File("res/images/hug/00.jpg"));
			hug1 = ImageIO.read(new File("res/images/hug/01.jpg"));
			hug2 = ImageIO.read(new File("res/images/hug/02.jpg"));
			hug3 = ImageIO.read(new File("res/images/hug/03.jpg"));
			hug4 = ImageIO.read(new File("res/images/hug/04.jpg"));
			hug5 = ImageIO.read(new File("res/images/hug/05.jpg"));
			hug6 = ImageIO.read(new File("res/images/hug/06.jpg"));
			hug7 = ImageIO.read(new File("res/images/hug/07.jpg"));
			hug8 = ImageIO.read(new File("res/images/hug/08.jpg"));
			hug9 = ImageIO.read(new File("res/images/hug/09.jpg"));
			hug10 = ImageIO.read(new File("res/images/hug/10.jpg"));
			hug11 = ImageIO.read(new File("res/images/hug/11.jpg"));
			hug12 = ImageIO.read(new File("res/images/hug/12.jpg"));
			hug13 = ImageIO.read(new File("res/images/hug/13.jpg"));
			hug14 = ImageIO.read(new File("res/images/hug/14.jpg"));
			hug15 = ImageIO.read(new File("res/images/hug/15.jpg"));
			hug16 = ImageIO.read(new File("res/images/hug/16.jpg"));
			hug17 = ImageIO.read(new File("res/images/hug/17.jpg"));
			hug18 = ImageIO.read(new File("res/images/hug/18.jpg"));
			hug19 = ImageIO.read(new File("res/images/hug/19.jpg"));
			hug20 = ImageIO.read(new File("res/images/hug/20.jpg"));
			hug21 = ImageIO.read(new File("res/images/hug/21.jpg"));
			hug22 = ImageIO.read(new File("res/images/hug/22.jpg"));
			hug23 = ImageIO.read(new File("res/images/hug/23.jpg"));
			hug24 = ImageIO.read(new File("res/images/hug/24.jpg"));
			hug25 = ImageIO.read(new File("res/images/hug/25.jpg"));

			hug10_2 = ImageIO.read(new File("res/images/hug/10_2.png"));
			hug11_2 = ImageIO.read(new File("res/images/hug/11_2.png"));
			hug12_2 = ImageIO.read(new File("res/images/hug/12_2.png"));
			hug13_2 = ImageIO.read(new File("res/images/hug/13_2.png"));
			hug14_2 = ImageIO.read(new File("res/images/hug/14_2.png"));
			hug15_2 = ImageIO.read(new File("res/images/hug/15_2.png"));
			hug16_2 = ImageIO.read(new File("res/images/hug/16_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void cmd_hug(MessageReceivedEvent e, String param) throws MalformedURLException, IOException {
		if (e.getMessage().getMentionedMembers().size() != 1) {
			Help.sendCommandUser(e.getChannel(), "hug");
			return;
		}

		User u = e.getMessage().getMentionedMembers().get(0).getUser();
		if (e.getAuthor().getId().equals(u.getId())) {
			Send.text(e.getChannel(), "Hugging yourself? That's sad...");
			return;
		}

		Send.typing(e.getChannel());
		Send.image(e.getChannel(), getHugGif(e.getAuthor(), u), "hug.gif", e.getAuthor().getName() + " hugs " + u.getName());
	}

	private static byte[] getHugGif(User author, User receiver) throws MalformedURLException, IOException {
		BufferedImage[] gif = new BufferedImage[26];
		BufferedImage ra = getAvatar(receiver.getAvatarUrl());
		BufferedImage aa = getAvatar(author.getAvatarUrl());
		BufferedImage[] overlay = new BufferedImage[7];

		gif[0] = copyImage(hug0);
		gif[1] = copyImage(hug1);
		gif[2] = copyImage(hug2);
		gif[3] = copyImage(hug3);
		gif[4] = copyImage(hug4);
		gif[5] = copyImage(hug5);
		gif[6] = copyImage(hug6);
		gif[7] = copyImage(hug7);
		gif[8] = copyImage(hug8);
		gif[9] = copyImage(hug9);
		gif[10] = copyImage(hug10);
		gif[11] = copyImage(hug11);
		gif[12] = copyImage(hug12);
		gif[13] = copyImage(hug13);
		gif[14] = copyImage(hug14);
		gif[15] = copyImage(hug15);
		gif[16] = copyImage(hug16);
		gif[17] = copyImage(hug17);
		gif[18] = copyImage(hug18);
		gif[19] = copyImage(hug19);
		gif[20] = copyImage(hug20);
		gif[21] = copyImage(hug21);
		gif[22] = copyImage(hug22);
		gif[23] = copyImage(hug23);
		gif[24] = copyImage(hug24);
		gif[25] = copyImage(hug25);

		overlay[0] = copyImage(hug10_2);
		overlay[1] = copyImage(hug11_2);
		overlay[2] = copyImage(hug12_2);
		overlay[3] = copyImage(hug13_2);
		overlay[4] = copyImage(hug14_2);
		overlay[5] = copyImage(hug15_2);
		overlay[6] = copyImage(hug16_2);

		for (BufferedImage b : gif) {
			b.getGraphics().drawImage(ra, 162, 155, 65, 65, null);
		}

		// aa not in gif[0]
		gif[1].getGraphics().drawImage(aa, -55, 149, 60, 60, null);
		gif[2].getGraphics().drawImage(aa, -30, 149, 60, 60, null);
		gif[3].getGraphics().drawImage(aa, -11, 149, 60, 60, null);
		gif[4].getGraphics().drawImage(aa, 7, 149, 60, 60, null);
		gif[5].getGraphics().drawImage(aa, 24, 149, 60, 60, null);
		gif[6].getGraphics().drawImage(aa, 43, 149, 60, 60, null);
		gif[7].getGraphics().drawImage(aa, 61, 149, 60, 60, null);
		gif[8].getGraphics().drawImage(aa, 79, 149, 60, 60, null);
		gif[9].getGraphics().drawImage(aa, 97, 149, 60, 60, null);
		gif[10].getGraphics().drawImage(overlay[0], 0, 0, 369, 328, null);
		gif[10].getGraphics().drawImage(aa, 115, 149, 60, 60, null);

		gif[11].getGraphics().drawImage(overlay[1], 0, 0, 369, 328, null);
		gif[11].getGraphics().drawImage(aa, 143, 156, 60, 60, null);
		gif[12].getGraphics().drawImage(overlay[2], 0, 0, 369, 328, null);
		gif[12].getGraphics().drawImage(aa, 143, 156, 60, 60, null);
		gif[13].getGraphics().drawImage(overlay[3], 0, 0, 369, 328, null);
		gif[13].getGraphics().drawImage(aa, 143, 156, 60, 60, null);

		gif[14].getGraphics().drawImage(overlay[4], 0, 0, 369, 328, null);
		gif[14].getGraphics().drawImage(aa, 182, 149, 60, 60, null);
		gif[15].getGraphics().drawImage(overlay[5], 0, 0, 369, 328, null);
		gif[15].getGraphics().drawImage(aa, 200, 149, 60, 60, null);
		gif[16].getGraphics().drawImage(overlay[6], 0, 0, 369, 328, null);
		gif[16].getGraphics().drawImage(aa, 218, 149, 60, 60, null);
		gif[17].getGraphics().drawImage(aa, 235, 149, 60, 60, null);
		gif[18].getGraphics().drawImage(aa, 253, 149, 60, 60, null);
		gif[19].getGraphics().drawImage(aa, 271, 149, 60, 60, null);
		gif[20].getGraphics().drawImage(aa, 289, 149, 60, 60, null);
		gif[21].getGraphics().drawImage(aa, 307, 149, 60, 60, null);
		gif[22].getGraphics().drawImage(aa, 325, 149, 60, 60, null);
		gif[23].getGraphics().drawImage(aa, 343, 149, 60, 60, null);
		gif[24].getGraphics().drawImage(aa, 361, 149, 60, 60, null);
		// aa not in gif[25]

		return gifToBytes(gif, 150);
	}
}