package org.mpcleague.common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent.Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class IRCClient implements Runnable {

    public boolean running = false;
    private boolean irc = false;

    private BufferedReader in;
    private PrintWriter out;
    private Minecraft mc = Minecraft.getMinecraft();

    private ChatComponentText obrack = new ChatComponentText("[");
    private ChatComponentText cbrack = new ChatComponentText("]");
    private ChatComponentText c = new ChatComponentText("CCL");

    public static IRCClient instance;

    public IRCClient() {

	instance = this;

	obrack.getChatStyle().setColor(EnumChatFormatting.GRAY);
	cbrack.getChatStyle().setColor(EnumChatFormatting.GRAY);

	c.getChatStyle().setChatHoverEvent(
		new HoverEvent(Action.SHOW_TEXT, new ChatComponentText("This is a message from the CCL IRC chat.")));
	c.getChatStyle().setColor(EnumChatFormatting.BLUE);
    }

    public void run() {

	String serverAddress = "127.0.0.1";

	try {

	    Socket socket = new Socket(serverAddress, 9003);
	    Minecraft mc = Minecraft.getMinecraft();

	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out = new PrintWriter(socket.getOutputStream(), true);

	    running = true;

	    // Process all messages from server, according to the protocol.
	    while (running) {
		String line = in.readLine();
		if (line.startsWith("SUBMITNAME")) {
		    out.println(mc.thePlayer.getName());

		} else if (line.startsWith("MESSAGE")) {
		    
		    mc.thePlayer.addChatMessage(new ChatComponentText(obrack.getFormattedText() + c.getFormattedText() + cbrack.getFormattedText() + line.substring(8).replace("$", "")));
		    continue;
		}
	    }

	} catch (Exception e) {
	    running = false;
	    e.printStackTrace();
	}
    }

    public void sendIRCMessage(String message) {

	out.println(message);

    }

}
