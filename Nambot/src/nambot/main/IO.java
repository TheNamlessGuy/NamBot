package nambot.main;

import static nambot.globals.Vars.guildSettings;
import static nambot.globals.Vars.members;
import static nambot.globals.Vars.nambot;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONTokener;

import nambot.settings.GuildSettings;
import nambot.settings.NamMember;
import net.dv8tion.jda.core.entities.Guild;

public class IO {
	public static void save() throws IOException {
		saveGuildSettings();
		saveUsers();
	}

	private static void saveGuildSettings() throws IOException {
		JSONObject o = new JSONObject();
		for (GuildSettings gs : guildSettings.values()) {
			o.put(gs.ID(), gs.save());
		}

		Path path = getFile("guildsettings.json");
		OutputStreamWriter f = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);
		f.write(o.toString());
		f.close();
	}

	private static void saveUsers() throws IOException {
		JSONObject o = new JSONObject();
		for (NamMember nm : members.values()) {
			o.put(nm.getID(), nm.save());
		}

		Path path = getFile("users.json");
		OutputStreamWriter f = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);
		f.write(o.toString());
		f.close();
	}

	public static void load() throws IOException {
		loadGuildSettings();
		loadUsers();
	}

	private static void loadGuildSettings() throws IOException {
		Path path = getFile("guildsettings.json");
		if (!Files.exists(path)) {
			return;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), StandardCharsets.UTF_8));
		JSONObject o = new JSONObject(new JSONTokener(in));
		in.close();

		Guild g = null;
		for (String id : o.keySet()) {
			g = nambot.getGuildById(id);
			if (g != null) {
				guildSettings.put(id, new GuildSettings(o.getJSONObject(id), g));
			}
		}
	}

	private static void loadUsers() throws IOException {
		Path path = getFile("users.json");
		if (!Files.exists(path)) {
			return;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), StandardCharsets.UTF_8));
		JSONObject o = new JSONObject(new JSONTokener(in));
		in.close();

		for (String id : o.keySet()) {
			members.put(id, new NamMember(id, o.getJSONObject(id)));
		}
	}

	private static Path getFile(String filename) throws IOException {
		Path path = Paths.get("res", "saves", filename);
		Files.createDirectories(path.getParent());
		return path;
	}
}