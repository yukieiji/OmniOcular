package me.exz.omniocular.command;

import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.network.ConfigMessage;
import me.exz.omniocular.network.ConfigMessageHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class CommandReloadConfig extends CommandBase {
    @Override
    public String getCommandName() {
        return "oor";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(sender);
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/oor";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] array) {
        ConfigHandler.mergeConfig();
        List playerList = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        for (Object player : playerList) {
            ConfigMessageHandler.network.sendTo(new ConfigMessage(ConfigHandler.mergedConfig), (EntityPlayerMP) player);
        }
    }
}