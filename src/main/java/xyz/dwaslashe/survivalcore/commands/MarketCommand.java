package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MarketCommand extends Command {
    public MarketCommand() {
        super("market", "/market <dodaj, usun>", "");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("dodaj", "usun"), args[0]);
        else if (args.length == 2) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            Api.sendMessage(p, " &8[ &e&lMARKET - POMOC &8]");
            Api.sendMessage(p, "&6* &e/market dodaj <gracz> &8- &fdodaje gracza do marketu, na którym stoisz");
            Api.sendMessage(p, "&6* &e/market usun <gracz> &8- &fusuwa gracza do marketu, na którym stoisz");
        } else if (args[0].equalsIgnoreCase("dodaj")) {
            if (args.length == 2) {
                Player p2 = Bukkit.getPlayer(args[1]);
                if (p2 == null) {
                    offlinePlayer();
                    return;
                }
                p.chat("/arm addmember " + p2.getName());
            }
        } else if (args[0].equalsIgnoreCase("usun")) {
            if (args.length == 2) {
                Player p2 = Bukkit.getPlayer(args[1]);
                if (p2 == null) {
                    offlinePlayer();
                    return;
                }
                p.chat("/arm removemember " + p2.getName());
            }
        }
    }
}

