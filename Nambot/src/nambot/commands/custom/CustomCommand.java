package nambot.commands.custom;

import org.json.JSONObject;

import nambot.commands.custom.nodes.TopNode;
import nambot.commands.custom.parser.Parser;
import nambot.main.Send;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CustomCommand {
	public final String name;
	protected String contents;

	protected TopNode topNode;

	public final String ownerID;
	public final String guildID;

	public void invoke(MessageReceivedEvent e, String param) {
		Send.text(e.getChannel(), topNode.start(e, param));
	}

	public CustomCommand(String name, String contents, String ownerID, String guildID) {
		this.name = name;
		this.ownerID = ownerID;
		this.guildID = guildID;
		update(contents);
	}

	public CustomCommand(JSONObject o, String name, String guildID) {
		this(name, o.getString("contents"), o.getString("ownerID"), guildID);
	}

	public void update(String newContents) {
		this.contents = newContents;
		this.topNode = new Parser().parse(this.contents);
	}

	public JSONObject save() {
		JSONObject o = new JSONObject();

		o.put("contents", contents);
		o.put("ownerID", ownerID);

		return o;
	}
}