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
        super("ec", "/ec <nick>", "");
        setPermission("core.command.ec");
        setOnlyPlayer(true);
    }
    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length == 0) {
            if (CooldownManager.checkDelay(p) == true) {
                return;
            }
            CooldownManager.addColdown(p, "30s");
            p.openInventory(p.getEnderChest());
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aOtworzyłeś swój &eenderchest");
        } else if (args.length == 1) {
            if (!p.hasPermission("core.command.admin")) {
                p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.admin&8) &8<<"));
                return;
            }
            Player p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            }
            p.openInventory(p2.getEnderChest());
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aOtworzyłeś enderchest gracza &e" + p2.getName());
        } else wrongUsage();
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }
}

