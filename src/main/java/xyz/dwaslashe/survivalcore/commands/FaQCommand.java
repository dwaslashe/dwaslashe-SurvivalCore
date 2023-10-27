package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class FaQCommand extends Command {
    public FaQCommand() {
        super("faq", "/faq", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Main.pluginCommands.getCommands().getFaQCommand().getTab(), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length >= 0) {
            for (String message : Main.pluginCommands.getCommands().getFaQCommand().getFaq()) {
                Api.sendMessage(sender, message);
            }
        }
    }
}
