package org.mpcleague.common.test;

import java.awt.image.BufferedImage;
import java.io.File;

import org.mpcleague.common.ImgurUploader;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ScreenShotHelper;

public class TestCommand extends CommandBase {

    @Override
    public String getCommandName() {
	// TODO Auto-generated method stub
	return "test";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
	// TODO Auto-generated method stub
	return "commands.test.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

	Minecraft mc = Minecraft.getMinecraft();
	
	IChatComponent i = new ChatComponentText("It does nothing!");
	i.getChatStyle().setColor(EnumChatFormatting.RED);
	
	mc.thePlayer.addChatMessage(i);
	
    }

}
