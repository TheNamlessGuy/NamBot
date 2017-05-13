package CustomCommandClasses;

import java.io.File;

import org.json.JSONObject;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import static HelperPackage.SendingFunctions.*;

public class PostImageCommand extends CustomCommand {
	private String URL;
	private String toSay;
	
	public PostImageCommand(String imagesearch, String toSay, boolean deleteMessage) {
		this.deleteMessage = deleteMessage;
		this.toSay = toSay;
		this.URL = findMeme(imagesearch);
	}
	
	public PostImageCommand(JSONObject obj) {
		URL = obj.getString("URL");
		toSay = obj.getString("toSay");
		deleteMessage = obj.getBoolean("delete");
	}
	
	private String findMeme(String search) {
		File[] memes = new File("res/memes/").listFiles();
		for (int i = 0; i < memes.length; i++) {
			if (memes[i].getName().contains(search)) {
				return "res/memes/" + memes[i].getName();
			}
		}
		return "";
	}
	
	@Override
	public void execute(MessageReceivedEvent event) {
		if (URL.equals("")) {
			sendMsg(event.getChannel(), "File associated with command not found");
			return;
		}
		sendImage(event.getChannel(), new File(URL), toSay);
		
		if (deleteMessage)
			event.getMessage().deleteMessage().queue();
	}

	@Override
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("TYPE", "PostImageCommand");
		
		obj.put("URL", URL);
		obj.put("toSay", toSay);
		obj.put("delete", deleteMessage);
		
		return obj;
	}
}
