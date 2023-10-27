package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class OnaMiala10LatCommand extends Command {
    public OnaMiala10LatCommand() {
        super("onamiala10lat", "/onamiala10lat", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        for (String message : Main.pluginCommands.getCommands().getOnaMiala10LatCommand().getOnamiala10lat()) {
            Api.sendMessage(sender, message);
        }
    }
}