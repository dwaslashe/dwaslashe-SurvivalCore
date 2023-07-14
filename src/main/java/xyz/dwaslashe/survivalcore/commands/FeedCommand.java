package xyz.dwaslashe.survivalcore.commands;

import net.saidora.api.helpers.glowing.GlowingEntities;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.List;

public class FeedCommand extends Command {
    public FeedCommand() {
        super("feed", "/feed <nick>", "");
        setPermission("core.command.feed");
        setOnlyPlayer(true);
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            if (CooldownManager.checkDelay(p) == true) {
                return;
            }
            CooldownManager.addColdown(p, "10m");
            p.setFoodLevel(20);
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie się najadłeś");
        } else if (args.length == 1) {
            if (!p.hasPermission("core.command.admin")) {
                p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.admin&8) &8<<"));
                return;
            }
            Player p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            } else {
                p2.setFoodLevel(20);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aZostałeś najedzony przez &e" + p.getName());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie najadałeś gracza &e" + p2.getName());
            }
        } else wrongUsage();
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }
}
