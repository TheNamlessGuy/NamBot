package nambot.helpers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ImageProcessing {
	/*
	 * COPY BUFFEREDIMAGE
	 */
	public static BufferedImage copyImage(BufferedImage i) {
		BufferedImage b = new BufferedImage(i.getWidth(), i.getHeight(), i.getType());
		Graphics g = b.getGraphics();
		g.drawImage(i, 0, 0, null);
		g.dispose();
		return b;
	}

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
	 * GET AVATAR
	 */
	public static BufferedImage getAvatar(String avatarURL) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(avatarURL).openConnection();
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
		return ImageIO.read(connection.getInputStream());
	}

	/*
	 * RENDER TEXT
	 */
	public static void renderTextW(Graphics2D g, String[] t, int x, int y, int w, int h, int margin, int lineMargin) {
		for (int i = 0; i < t.length; i++) {
			renderTextW(g, t[i], x, y + ((g.getFont().createGlyphVector(g.getFontRenderContext(), t[i]).getPixelBounds(null, x, y).height + lineMargin) * i), w,
					h, margin);
		}
	}

	public static void renderTextW(Graphics2D g, String t, int x, int y, int w, int h, int margin) {
		Rectangle rect = new Rectangle(x + margin, y + margin, w - margin, h - margin);
		int stringWidth = g.getFontMetrics().stringWidth(t);
		TextLayout tl = new TextLayout(t, g.getFont(), g.getFontRenderContext());
		AffineTransform transform = new AffineTransform();
		transform.setToTranslation(rect.getX(), rect.getY());
		if (stringWidth >= rect.getWidth()) {
			transform.scale(rect.getWidth() / stringWidth, 1);
		}
		Shape shape = tl.getOutline(transform);
		g.setClip(shape);
		g.fill(shape.getBounds());
	}

	public static void renderText(Graphics2D g, String[] t, int x, int y, int w, int h, int margin) {
		for (int i = 0; i < t.length; i++) {
			renderText(g, t[i], x, y + (g.getFont().createGlyphVector(g.getFontRenderContext(), t[i]).getPixelBounds(null, x, y).height * i), w, h, margin);
		}
	}

	public static void renderText(Graphics2D g, String t, int x, int y, int w, int h, int margin) {
		Rectangle rect = new Rectangle(x + margin, y + margin, w - margin, h - margin);
		TextLayout tl = new TextLayout(t, g.getFont(), g.getFontRenderContext());
		AffineTransform transform = new AffineTransform();
		transform.setToTranslation(rect.getX(), rect.getY());
		double scaleY = rect.getHeight() / (tl.getOutline(null).getBounds().getMaxY() - tl.getOutline(null).getBounds().getMinY());
		transform.scale(rect.getWidth() / g.getFontMetrics().stringWidth(t), scaleY);
		Shape shape = tl.getOutline(transform);
		g.setClip(shape);
		g.fill(shape.getBounds());
	}
}