package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class TikTokCommand extends Command {
    public TikTokCommand() {
        super("tiktok", "/tiktok", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aLink do naszego porfilu na TikTok &#ff0050" + Main.pluginConfig.getMessages().getTiktok());
    }
}

