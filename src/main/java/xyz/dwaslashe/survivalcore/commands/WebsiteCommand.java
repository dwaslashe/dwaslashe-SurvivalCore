package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class WebsiteCommand extends Command {
    public WebsiteCommand() {
        super("website", "/strona", "", "strona", "itemshop");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length >= 0) {
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aStrona WWW &#e6cf3c" + Main.pluginConfig.getMessages().getWebsite());
        }
    }
}
