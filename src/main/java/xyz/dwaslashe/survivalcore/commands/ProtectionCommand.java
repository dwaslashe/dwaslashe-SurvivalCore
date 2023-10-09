package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.Protection;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProtectionCommand extends Command {
    public ProtectionCommand() {
        super("ochrona", "/ochrona <off>", "");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("off"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            Protection protection = Protection.get(player.getUniqueId());
            if (protection != null && protection.getProtection() > System.currentTimeMillis()) {
                Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aTwoja ochrona trwa jeszcze &#ffd56c" + TimerApi.secondsToString(protection.getProtection()) + " &#ffc942⌚&a! &aAby ją wyłączyć wpisz &e/ochrona off");
            } else {
                Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz włączonej ochrony!");
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("off")) {
                Protection protection = Protection.get(player.getUniqueId());
                if(protection != null && protection.getProtection() > System.currentTimeMillis()) {
                    Protection.getProtectionMap().get(player.getUniqueId()).setProtection(0);
                    Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wyłączono ochronę!");
                } else {
                    Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz włączonej ochrony!");
                }
            }

        }
    }
}
