package HelperPackage;

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

public class ImageGenerators {
	/*
	 * GET AVATAR
	 */
	public static BufferedImage getAvatar(String avatarURL) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(avatarURL).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
		return ImageIO.read(connection.getInputStream());
	}
	
	/*
	 * PAT GIF
	 */
	public static byte[] getPatGif(String mentionedAvatarURL, String authorAvatarURL) {
		try {
			BufferedImage pat0 = ImageIO.read(new File("res/images/templates/pat0.jpg"));
			BufferedImage pat1 = ImageIO.read(new File("res/images/templates/pat1.jpg"));
			BufferedImage mentionedAvatar = getAvatar(mentionedAvatarURL);
			BufferedImage writingAvatar = getAvatar(authorAvatarURL);
			
			pat0.getGraphics().drawImage(mentionedAvatar, 70, 160, 50, 50, null);
			pat1.getGraphics().drawImage(mentionedAvatar, 70, 160, 50, 50, null);
			
			pat0.getGraphics().drawImage(writingAvatar, 150, 80, 50, 50, null);
			pat1.getGraphics().drawImage(writingAvatar, 150, 80, 50, 50, null);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageOutputStream out = ImageIO.createImageOutputStream(baos);

			GifSequenceWriter writer = new GifSequenceWriter(out, pat0.getType(), 150, true);
			writer.writeToSequence(pat0);
			writer.writeToSequence(pat1);
			
			writer.close();
			out.flush();
			byte[] arr = baos.toByteArray();
			out.close();
			baos.close();
			return arr;
		} catch (Exception e) {
			HelperFunctions.debug(e.getMessage());
		}
		return null;
	}
	
	/*
	 * SORRY ABOUT EXISTING IMAGE
	 */
	public static byte[] getSorryAboutExistingImage(String avatarURL) {
		try {
			BufferedImage avatar = getAvatar(avatarURL);
			BufferedImage baseimage = ImageIO.read(new File("res/images/templates/sorryaboutexisting.png"));
			
			AffineTransform at = new AffineTransform();
			// #2: rotate
			at.rotate(Math.toRadians(-15), avatar.getWidth() / 2, avatar.getHeight() / 2);
			// #1: scale
			at.scale(1.5, 1.5);
			AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			baseimage.getGraphics().drawImage(op.filter(avatar, null), 50, 50, null);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(baseimage, "PNG", baos);
			baos.flush();
			byte[] arr = baos.toByteArray();
			baos.close();
			return arr;
		} catch (Exception e) {
			HelperFunctions.debug(e.getMessage());
		}
		return null;
	}
	
	/*
	 * SHIP
	 */
	public static byte[] getShipImage(String jackURL, String roseURL) {
		try {
			BufferedImage jack = getAvatar(jackURL);
			BufferedImage rose = getAvatar(roseURL);
			BufferedImage base = ImageIO.read(new File("res/images/templates/ship.jpg"));

			base.getGraphics().drawImage(jack, 325, 85, 100, 100, null);
			base.getGraphics().drawImage(rose, 425, 95, 100, 100, null);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(base, "JPG", baos);
			baos.flush();
			byte[] arr = baos.toByteArray();
			baos.close();
			return arr;
		} catch (Exception e) {
			HelperFunctions.debug(e.getMessage());
		}
		return null;
	}
}
