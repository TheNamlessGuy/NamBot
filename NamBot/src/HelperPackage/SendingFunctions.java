package HelperPackage;

import java.io.File;
import java.io.IOException;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class SendingFunctions {
	public static void sendMsg(MessageChannel channel, String msg) {
		channel.sendMessage(msg).queue();
	}
	
	public static void sendImage(MessageChannel channel, File file, Message msg) {		
		try {
			channel.sendFile(file, msg).queue();
		} catch (IOException e) {
			// no
		}
	}
	
	public static void sendImage(MessageChannel channel, String file, String msg) {
		Message m = null;
		if (!msg.equals("")) {
			m = new MessageBuilder().append(msg).build();
		}
		
		sendImage(channel, new File("res/images/" + file), m);
	}
	
	public static void sendImage(MessageChannel channel, String file) {
		sendImage(channel, file, "");
	}
	
	public static void sendImage(MessageChannel channel, File file, String msg) {
		Message m = null;
		if (!msg.equals("")) {
			m = new MessageBuilder().append(msg).build();
		}
		
		sendImage(channel, file, m);
	}
	
	public static void sendImage(MessageChannel channel, File file) {
		sendImage(channel, file, "");
	}
}
