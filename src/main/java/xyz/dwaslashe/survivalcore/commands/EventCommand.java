package xyz.dwaslashe.survivalcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.MoneyTargetCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.MoneyTarget;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class EventCommand extends Command {
    public EventCommand() {
        super("event", "/event <case, meteorite, boss, random> <type, random>", "");
        setPermission("core.command.event");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("reset", "set", "setlimit", "title"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("case") || args[0].equalsIgnoreCase("meteorite") || args[0].equalsIgnoreCase("boss") || args[0].equalsIgnoreCase("random")) {
                wrongUsage();
                if (args[1].equalsIgnoreCase("random")) {
                    if (args[0].equalsIgnoreCase("random")) {

                    }
                }
            } else if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 1) {
                    wrongUsage();
                } else if (Api.isInt(args[1])) {
                    int value = Integer.parseInt(args[1]);
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie ustawiono zdobyte pieniądze!");
                } else
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cArgument musi być liczbą!");
            } else if (args[0].equalsIgnoreCase("setlimit")) {
                if (args.length == 1) {
                    wrongUsage();
                } else if (Api.isInt(args[1])) {
                    int value = Integer.parseInt(args[1]);
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie ustawiono limit celu pieniędzy!");
                } else
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cArgument musi być liczbą!");
            } else if (args.length >= 1) {
                if (args.length == 1) {
                    wrongUsage();
                } else if (args[0].equalsIgnoreCase("title")) {
                    String title = StringUtils.join(args, " ", 1, args.length);
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie ustawiono tytuł celu pieniędzy!");
                }
            }
        }
    }
}
