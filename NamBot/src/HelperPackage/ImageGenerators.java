package HelperPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import net.dv8tion.jda.core.entities.MessageChannel;

public class ImageGenerators {
	/*
	 * ROTATE IMAGE
	 */
	public static BufferedImage rotateImage(BufferedImage i, int degrees) {
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(degrees), i.getWidth() / 2, i.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(i, null);
	}
	
	/*
	 * IMAGE TO BYTES
	 */
	public static byte[] imageToBytes(BufferedImage image, String type) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, type, baos);
		baos.flush();
		byte[] arr = baos.toByteArray();
		baos.close();
		return arr;
	}
	
	/*
	 * GIF TO BYTES
	 */
	public static byte[] gifToBytes(BufferedImage[] images, int frameTime) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageOutputStream out = ImageIO.createImageOutputStream(baos);
		
		GifSequenceWriter gif = new GifSequenceWriter(out, images[0].getType(), frameTime, true);
		for (BufferedImage i : images) {
			gif.writeToSequence(i);
		}
		gif.close();
		out.flush();
		byte[] arr = baos.toByteArray();
		out.close();
		baos.close();
		return arr;
	}
	
	/*
	 * READ FOLDER
	 */
	public static BufferedImage[] readFolder(String url) throws IOException {
		File[] files = new File(url).listFiles();
		BufferedImage[] array = new BufferedImage[files.length];
		
		for (int i = 0; i < files.length; i++) {
			array[i] = ImageIO.read(files[i]);
		}
		
		return array;
	}
	
	/*
	 * GET AVATAR
	 */
	public static BufferedImage getAvatar(String avatarURL) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(avatarURL).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
		return ImageIO.read(connection.getInputStream());
	}
	
	/*
	 * RENDER TEXT
	 */
	public static Graphics2D renderText(Graphics2D g, String t, int x, int y, int w, int h, int margin) {
		Rectangle rect = new Rectangle(x + margin, y + margin, w - margin, h - margin);
		TextLayout tl = new TextLayout(t, g.getFont(), g.getFontRenderContext());
		AffineTransform transform = new AffineTransform();
		transform.setToTranslation(rect.getX(), rect.getY());
		double scaleY = rect.getHeight() / (double) (tl.getOutline(null).getBounds().getMaxY() - tl.getOutline(null).getBounds().getMinY());
		transform.scale(rect.getWidth() / (double) g.getFontMetrics().stringWidth(t), scaleY);
		Shape shape = tl.getOutline(transform);
		g.setClip(shape);
		g.fill(shape.getBounds());
		return g;
	}
	
	/*
	 * PAT GIF
	 */
	public static byte[] getPatGif(MessageChannel c, String mentionedAvatarURL, String authorAvatarURL) {
		try {
			BufferedImage mentionedAvatar = getAvatar(mentionedAvatarURL);
			BufferedImage writingAvatar = getAvatar(authorAvatarURL);
			BufferedImage[] gif = readFolder("res/images/templates/pat");
			
			gif[0].getGraphics().drawImage(mentionedAvatar, 70, 160, 50, 50, null);
			gif[1].getGraphics().drawImage(mentionedAvatar, 70, 160, 50, 50, null);
			
			gif[0].getGraphics().drawImage(writingAvatar, 150, 80, 50, 50, null);
			gif[1].getGraphics().drawImage(writingAvatar, 150, 80, 50, 50, null);
			
			return gifToBytes(gif, 150);
		} catch (Exception e) {
			HelperFunctions.err(c, e, "");
			//e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * SORRY ABOUT EXISTING IMAGE
	 */
	public static byte[] getSorryAboutExistingImage(MessageChannel c, String avatarURL) {
		try {
			BufferedImage avatar = rotateImage(getAvatar(avatarURL), -15);
			BufferedImage baseimage = ImageIO.read(new File("res/images/templates/sorryaboutexisting.png"));
			
			baseimage.getGraphics().drawImage(avatar, 50, 50, 225, 225, null);
			
			return imageToBytes(baseimage, "PNG");
		} catch (Exception e) {
			HelperFunctions.err(c, e, "");
			//e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * SHIP
	 */
	public static byte[] getShipImage(MessageChannel c, String jackURL, String roseURL) {
		try {
			BufferedImage jack = getAvatar(jackURL);
			BufferedImage rose = getAvatar(roseURL);
			BufferedImage base = ImageIO.read(new File("res/images/templates/ship.jpg"));

			base.getGraphics().drawImage(jack, 325, 85, 100, 100, null);
			base.getGraphics().drawImage(rose, 425, 95, 100, 100, null);
			
			return imageToBytes(base, "JPG");
		} catch (Exception e) {
			HelperFunctions.err(c, e, "");
			//e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * HIGHFIVE
	 */
	public static byte[] getHighfiveGif(MessageChannel c, String u1, String u2) {
		try {
			BufferedImage a1 = getAvatar(u1);
			BufferedImage a2 = getAvatar(u2);
			BufferedImage[] gif = readFolder("res/images/templates/highfive");

			gif[0].getGraphics().drawImage(a1, 70, 50, 80, 80, null);
			gif[0].getGraphics().drawImage(a2, 365, 90, 60, 60, null);
			
			gif[1].getGraphics().drawImage(a1, 68, 48, 80, 80, null);
			gif[1].getGraphics().drawImage(a2, 366, 90, 60, 60, null);
			
			gif[2].getGraphics().drawImage(a1, 66, 46, 80, 80, null);
			gif[2].getGraphics().drawImage(a2, 367, 90, 60, 60, null);
			
			gif[3].getGraphics().drawImage(a1, 60, 40, 80, 80, null);
			gif[3].getGraphics().drawImage(a2, 370, 90, 60, 60, null);
			
			for (int i = 4; i < 10; i++) {
				gif[i].getGraphics().drawImage(a1, 59, 39, 80, 80, null);
				gif[i].getGraphics().drawImage(a2, 371, 90, 60, 60, null);
			}
			
			return gifToBytes(gif, 100);
		} catch (Exception e) {
			HelperFunctions.err(c, e, "");
			//e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * STAB
	 */
	public static byte[] getStabGif(MessageChannel c, String u1, String u2) {
		try {
			BufferedImage a1 = getAvatar(u1);
			BufferedImage a2 = rotateImage(getAvatar(u2), 90);
			BufferedImage[] gif = readFolder("res/images/templates/stab");
			
			for (int i = 0; i < 5; i++) {
				gif[i].getGraphics().drawImage(a1, 73, 28, 70, 70, null);
				gif[i].getGraphics().drawImage(a2, 177, 149, 62, 62, null);
			}
			
			return gifToBytes(gif, 100);
		} catch (Exception e) {
			HelperFunctions.err(c, e, "");
			//e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * RIP
	 */
	public static byte[] getRIPImage(MessageChannel c, String text, String aURL) {
		try {
			BufferedImage base = ImageIO.read(new File("res/images/templates/rip.png"));
			
			Graphics2D g = (Graphics2D) base.getGraphics();
			int width = g.getFontMetrics().stringWidth(text);
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 36));
			g.setColor(Color.black);
			g.drawString(text, 200 - (width / 2), 325);
			if (!aURL.equals("")) {
				g.drawImage(getAvatar(aURL), 140, 370, 180, 180, null);
			}
			
			return imageToBytes(base, "PNG");
		} catch (IOException e) {
			HelperFunctions.err(c, e, "");
		}
		return null;
	}
	
	/*
	 * CIVIL WAR
	 */
	public static byte[] getCivilWarImage(MessageChannel c, String a1, String t1, String a2, String t2, String serverName) {
		try {
			BufferedImage base = ImageIO.read(new File("res/images/templates/civilwar.jpg"));
			
			Graphics2D g = (Graphics2D) base.getGraphics();
			g.drawImage(getAvatar(a1), 109, 35, 330, 330, null);
			g.drawImage(getAvatar(a2), 182, 490, 280, 280, null);
			
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 36));
			g.setColor(Color.BLACK);
			g = renderText(g, t1, 20, 435, 589, 43, 3);
			g = renderText(g, t2, 20, 875, 589, 43, 3);
			g = renderText(g, serverName, 145, 1010, 315, 40, 3);
			
			return imageToBytes(base, "JPG");
		} catch (IOException e) {
			HelperFunctions.err(c, e, "");
		}
		return null;
	}
}
