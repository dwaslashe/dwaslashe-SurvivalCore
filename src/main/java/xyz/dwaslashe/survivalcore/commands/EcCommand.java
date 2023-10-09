package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.List;

public class EcCommand extends Command {
    public EcCommand() {
        super("ec", "/ec <nick>", "", "enderchest");
        setPermission("core.command.ec");
        setOnlyPlayer(true);
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player player = (Player) s;
        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie otworzyłeś swój &eenderchest");
        } else if (args.length == 1) {
            if (!player.hasPermission("core.command.admin")) {
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.admin&8) &8<<"));
                return;
            }
            Player secondPlayer = Bukkit.getPlayer(args[0]);
            if (secondPlayer == null) {
                offlinePlayer();
                return;
            }
            player.openInventory(secondPlayer.getEnderChest());
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie otworzyłeś enderchest gracza &e" + secondPlayer.getName());
        } else wrongUsage();
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }
}

