package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.*;

public class IgnoreCommand extends Command {
    static HashMap<Player, Player> ignoreMsg = new HashMap();

    public static ArrayList<Player> blockMsg = new ArrayList<>();

    public IgnoreCommand() {
        super("ignore", "/ignore <all, nick>", "", "ignoruj", "zablokuj");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        //if (args.length == 1) return Api.startsWith(Arrays.asList("all"), args[0]);
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("all")) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zignorowałeś wszystkich graczy!");
                blockMsg.add(p);
                return;
            }

            Player p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            }

            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zignorowano gracza &e" + p2.getName());
            ignoreMsg.put(p, p2);
        }
    }

    public static HashMap<Player, Player> getIgnoreMsg() {
        return ignoreMsg;
    }
}
