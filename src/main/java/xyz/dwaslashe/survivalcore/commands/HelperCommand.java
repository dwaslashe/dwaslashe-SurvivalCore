package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HelperCommand extends Command implements Listener {
    public HelperCommand() {
        super("helper", "/helper <invsee, tp> <nick>", "");
        setPermission("core.command.helper");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("invsee", "tp"), args[0]);
        else if (args.length == 2) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            wrongUsage();
        } else if (args[0].equalsIgnoreCase("invsee")) {
            if (args.length == 2) {
                Player p = (Player) sender;
                Player p2 = Bukkit.getPlayer(args[1]);
                if (p2 == null) {
                    offlinePlayer();
                    return;
                }
                OthersListener.cancel.add(p);
                p.openInventory(p2.getInventory());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aOtworzyłeś ekwipunek gracza &e" + p2.getName());
            }
        } else if (args[0].equalsIgnoreCase("tp")) {
            if (args.length == 2) {
                Player p = (Player) sender;
                Player p2 = Bukkit.getPlayer(args[1]);
                if (p2 == null) {
                    offlinePlayer();
                    return;
                }
                p.teleport(p2);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZostałeś przeteleportowany do &e" + p2.getName());
            }
        }
    }
}