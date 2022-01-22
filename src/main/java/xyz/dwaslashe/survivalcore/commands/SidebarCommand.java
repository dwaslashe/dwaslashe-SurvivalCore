package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;

import java.util.List;

public class SidebarCommand extends Command {
    public SidebarCommand() {
        super("sidebar", "", "", "scoreboard");
        setOnlyPlayer(true);
    }
    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length >= 0) {
            Player player = (Player)sender;
            player.chat("/sb");
        }
    }
}
