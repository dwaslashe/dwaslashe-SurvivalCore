package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.List;

public class TpHereCommand extends Command {
    public TpHereCommand() {
        super("tphere", "/tphere <nick>", "");
        setPermission("core.command.tphere");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player)sender;
        Location loc = p.getLocation();
        if (args.length == 0) {
            wrongUsage();

        } else if (args.length == 1) {
            Player p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null || !p2.isOnline()) {
                offlinePlayer();
                return;
            }
            p2.teleport(loc);
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aGracz &e" + p2.getName() + " &aprzeteleportował się do ciebie!");
            Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aZostałeś przeteleportowany do &e" + p.getName());
        }
    }
}
