package nambot.main.IO;

import static nambot.globals.Vars.guildSettings;
import static nambot.globals.Vars.members;
import static nambot.globals.Vars.nambot;
import static nambot.globals.Vars.todo;
import static nambot.helpers.General.getGuildSettings;
import static nambot.main.IO.Helpers.getFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import nambot.commands.custom.CustomCommand;
import nambot.helpers.settings.GuildSettings;
import nambot.helpers.settings.NamMember;
import net.dv8tion.jda.core.entities.Guild;

public class Load {
	public static void all() throws IOException {
		guildSettings();
		users();
		customCommands();
		todo();
	}

	public static void guildSettings() throws IOException {
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

	public static void users() throws IOException {
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

	public static void customCommands() throws IOException {
		Path path = getFile("customcommands.json");
		if (!Files.exists(path)) {
			return;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), StandardCharsets.UTF_8));
		JSONObject o = new JSONObject(new JSONTokener(in));
		in.close();

		JSONObject tmp = null;
		for (String id : o.keySet()) {
			GuildSettings gs = getGuildSettings(nambot.getGuildById(id));
			tmp = o.getJSONObject(id);
			for (String name : tmp.keySet()) {
				gs.customCommands.put(name, new CustomCommand(tmp.getJSONObject(name), name, gs.ID()));
			}
		}
	}

	public static void todo() throws IOException {
		Path path = getFile("todo.json");
		if (!Files.exists(path)) {
			return;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), StandardCharsets.UTF_8));
		JSONArray a = new JSONArray(new JSONTokener(in));
		in.close();

		for (int i = 0; i < a.length(); ++i) {
			todo.add(a.getString(i));
		}
	}
}