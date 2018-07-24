package nambot.commands.admin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import org.json.JSONObject;

public class Get {
	public static String official_joke_api() throws MalformedURLException, IOException {
		JSONObject obj = getJSONContent("https://08ad1pao69.execute-api.us-east-1.amazonaws.com/dev/random_joke");
		return obj.getString("setup") + "\n" + obj.getString("punchline");
	}

	public static String numbersapi(String number) throws MalformedURLException, IOException {
		if (number == null)
			return getStringContent("http://numbersapi.com/random/trivia");
		return getStringContent("http://numbersapi.com/" + number);
	}

	public static String ronswansonquotes() throws MalformedURLException, IOException {
		String s = getStringContent("http://ron-swanson-quotes.herokuapp.com/v2/quotes");
		return s.substring(1, s.length() - 1) + " - Ron Swanson";
	}

	public static byte[] adorableavatars(String ident) throws MalformedURLException, IOException {
		return getImageContent("https://api.adorable.io/avatars/285/" + ident + ".png", "PNG");
	}

	public static byte[] robohash(String set, String ident) throws MalformedURLException, IOException {
		return getImageContent("https://robohash.org/" + ident + "?set=set" + set, "PNG");
	}

	/* Getters */
	private static JSONObject getJSONContent(String URL) throws MalformedURLException, IOException {
		InputStream is = new URL(URL).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			JSONObject obj = new JSONObject(rd.readLine());
			rd.close();
			return obj;
		} finally {
			is.close();
		}
	}

	private static String getStringContent(String URL) throws MalformedURLException, IOException {
		InputStream is = new URL(URL).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String s = rd.readLine();
			rd.close();
			return s;
		} finally {
			is.close();
		}
	}

	private static byte[] getImageContent(String URL, String format) throws MalformedURLException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(ImageIO.read(new URL(URL)), format, baos);
		baos.flush();
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;
	}
}