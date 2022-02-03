package xyz.dwaslashe.survivalcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class PurchaseCommand extends Command {
    public PurchaseCommand() {
        super("purchase", "/purchase <nick>", "");
        setPermission("core.command.purchase");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[0]);
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length == 1) {
            Api.sendBroadcast("&#FFF01F&lDZIĘKUJEMY &8>> &fGracz &e" + p2.getName() + " &fkupił u nas coś w sklepie! &#FFF01FDziękujemy za wsparcie serwera!");
        } else if (args.length >= 2) {
            Api.sendBroadcast("&#FFF01F&lDZIĘKUJEMY &8>> &fGracz &e" + p2.getName() + " &fkupił u nas &b" + StringUtils.join(args, " ", 1, args.length) + "&f! &#FFF01FDziękujemy za wsparcie serwera!");
        }
    }
}
