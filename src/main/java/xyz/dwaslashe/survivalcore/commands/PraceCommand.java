package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;

import java.util.List;

public class PraceCommand extends Command {
    public PraceCommand() {
        super("prace", "/prace", "", "praca");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length >= 0) {
            Player p = (Player)sender;
            p.chat("/jobs browse");
        }
    }
}
