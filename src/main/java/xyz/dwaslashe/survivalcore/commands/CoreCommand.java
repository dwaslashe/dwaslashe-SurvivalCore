package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class CoreCommand extends Command {
    public CoreCommand() {
        super("core", "/core <author, server, reload>", "", "tools");
        setPermission("core.command.core");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("author", "server", "reload"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
        } else {
            if (args[0].equalsIgnoreCase("author")) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&7Plugin wykonany przez dwaslashe, kontakt discord #FF500Bdwaslashe v2#5620");
            } else {
                if (args[0].equalsIgnoreCase("server")) {
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aCały ram&8: &f" + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + "&8/&7" + (Runtime.getRuntime().maxMemory() / 1024 / 1024));
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aUżyty ram&8: &f" + ((Runtime.getRuntime().totalMemory() / 1024 / 1024) - (Runtime.getRuntime().freeMemory() / 1024 / 1024)));
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aNie użyty ram&8: &f" + (Runtime.getRuntime().freeMemory() / 1024 / 1024));
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aProcesory&8: &f" + (Runtime.getRuntime().availableProcessors()));
                } else {
                    if (args[0].equalsIgnoreCase("reload")) {
                        Api.sendMessage(p,Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeładowano Plugin!");
                        Main.pluginConfig.load();
                        Main.pluginCommands.load();
                    }
                }
            }
        }
    }
}
