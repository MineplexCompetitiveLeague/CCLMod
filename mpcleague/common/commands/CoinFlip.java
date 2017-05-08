package org.mpcleague.common.commands;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CoinFlip extends CommandBase {

    @Override
    public String getCommandName() {
	// TODO Auto-generated method stub
	return "coinflip";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
	// TODO Auto-generated method stub
	return "commands.coinflip.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

	Minecraft mc = Minecraft.getMinecraft();

	IChatComponent header = new ChatComponentText("[CCL] ");
	header.getChatStyle().setColor(EnumChatFormatting.BLUE);

	int rand = new Random().nextInt((2 - 1) + 1);

	if (rand == 1) {

	    mc.thePlayer.addChatMessage(new ChatComponentText(header.getFormattedText() + "Heads!"));

	} else {

	    mc.thePlayer.addChatMessage(new ChatComponentText(header.getFormattedText() + "Tails!"));

	}

	return;

    }

}
