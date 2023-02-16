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

import static xyz.dwaslashe.survivalcore.commands.IgnoreCommand.blockMsg;

public class UnIgnoreCommand extends Command {

    public UnIgnoreCommand() {
        super("unignore", "/unignore <all, nick>", "", "przestanignorowac", "odblokuj");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        else if (args.length == 1) return Api.startsWith(Arrays.asList("all"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("all")) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przestano ignorować wszystkich graczy!");
                blockMsg.remove(p);
                return;
            }
            Player p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            }

            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przestano ignorować gracza &e" + p2.getName());
            IgnoreCommand.ignoreMsg.remove(p, p2);
        } else wrongUsage();
    }

}
