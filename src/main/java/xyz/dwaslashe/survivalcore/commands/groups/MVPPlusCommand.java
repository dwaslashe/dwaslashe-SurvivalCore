package xyz.dwaslashe.survivalcore.commands.groups;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;

import java.util.List;

public class MVPPlusCommand extends Command {
    public MVPPlusCommand() {
        super("mvp+", "/mvp+", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length >= 0) {
            Player player = (Player)sender;
            player.chat("/rangi");
        }
    }
}
