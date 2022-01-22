package xyz.dwaslashe.survivalcore.commands.groups;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;

import java.util.List;

public class VipCommand extends Command {
    public VipCommand() {
        super("vip", "/vip", "");
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
            player.chat("/rangi");
        }
    }
}
