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
	 * PAT GIF
	 */
	public static byte[] getPatGif(String mentionedAvatarURL, String authorAvatarURL) {
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
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * SORRY ABOUT EXISTING IMAGE
	 */
	public static byte[] getSorryAboutExistingImage(String avatarURL) {
		try {
			BufferedImage avatar = rotateImage(getAvatar(avatarURL), -15);
			BufferedImage baseimage = ImageIO.read(new File("res/images/templates/sorryaboutexisting.png"));
			
			baseimage.getGraphics().drawImage(avatar, 50, 50, 225, 225, null);
			
			return imageToBytes(baseimage, "PNG");
		} catch (Exception e) {
			e.printStackTrace();
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
			
			return imageToBytes(base, "JPG");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * HIGHFIVE
	 */
	public static byte[] getHighfiveGif(String u1, String u2) {
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
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * STAB
	 */
	public static byte[] getStabGif(String u1, String u2) {
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
			e.printStackTrace();
		}
		return null;
	}
}
