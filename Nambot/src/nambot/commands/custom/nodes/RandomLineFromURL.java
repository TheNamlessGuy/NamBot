package nambot.commands.custom.nodes;

import static nambot.globals.Vars.random;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RandomLineFromURL extends Node {
	private Node url;

	public RandomLineFromURL(Node url) {
		this.url = url;
	}

	@Override
	protected String getValue(RunInstance ri) {
		Scanner s = null;
		try {
			URL u = new URL(url.getValue(ri)); // "https://pastebin.com/raw/hF2aR41G"
			URLConnection c = u.openConnection();
			c.setConnectTimeout(2000);
			c.setReadTimeout(2000);
			c.connect();
			s = new Scanner(c.getInputStream());
			List<String> result = new ArrayList<>();
			while (s.hasNext()) {
				result.add(s.next());
			}
			return result.get(random.nextInt(result.size()));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return null;
	}
}
