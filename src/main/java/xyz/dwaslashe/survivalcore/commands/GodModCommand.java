package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;
public class GodModCommand extends Command {
    public GodModCommand() {
        super("godmod", "/godmod", "");
        setPermission("core.command.godmod");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Player p = (Player) sender;
            if (p.isInvulnerable()) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cPomyślnie wyłączyłeś &egodmod!");
                p.setInvulnerable(false);
            } else {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie włączyłeś &egodmod!");
                p.setInvulnerable(true);
            }
        } else wrongUsage();
    }
}
