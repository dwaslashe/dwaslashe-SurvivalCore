package xyz.dwaslashe.survivalcore.commands.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;

import java.util.Collections;
import java.util.List;

public class ReviveCommand extends Command {
    public ReviveCommand() {
        super("reanimacja", "/reanimacja <gracz>", "");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length == 1) {
            Player p2 = Bukkit.getPlayer(args[0]);
            p.chat("/lifesteal revive " + p2.getName());
        }
    }
}
