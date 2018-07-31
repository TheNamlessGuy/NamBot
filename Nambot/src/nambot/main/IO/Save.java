package nambot.main.IO;

import static nambot.globals.Vars.guildSettings;
import static nambot.globals.Vars.members;
import static nambot.globals.Vars.todo;
import static nambot.main.IO.Helpers.getFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

import nambot.commands.custom.CustomCommand;
import nambot.helpers.settings.GuildSettings;
import nambot.helpers.settings.NamMember;

public class Save {
	public static void all() throws IOException {
		guildSettings();
		users();
		customCommands();
		todo();
	}

	public static void guildSettings() throws IOException {
		JSONObject o = new JSONObject();
		for (GuildSettings gs : guildSettings.values()) {
			o.put(gs.ID(), gs.save());
		}

		Path path = getFile("guildsettings.json");
		OutputStreamWriter f = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);
		f.write(o.toString());
		f.close();
	}

	public static void users() throws IOException {
		JSONObject o = new JSONObject();
		for (NamMember nm : members.values()) {
			o.put(nm.getID(), nm.save());
		}

		Path path = getFile("users.json");
		OutputStreamWriter f = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);
		f.write(o.toString());
		f.close();
	}

	public static void customCommands() throws IOException {
		JSONObject o = new JSONObject();
		JSONObject tmp = null;
		for (GuildSettings gs : guildSettings.values()) {
			tmp = new JSONObject();
			o.put(gs.ID(), tmp);
			for (CustomCommand cc : gs.customCommands.values()) {
				tmp.put(cc.name, cc.save());
			}
		}

		Path path = getFile("customcommands.json");
		OutputStreamWriter f = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);
		f.write(o.toString());
		f.close();
	}

	public static void todo() throws IOException {
		JSONArray a = new JSONArray();
		for (String t : todo) {
			a.put(t);
		}

		Path path = getFile("todo.json");
		OutputStreamWriter f = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);
		f.write(a.toString());
		f.close();
	}
}