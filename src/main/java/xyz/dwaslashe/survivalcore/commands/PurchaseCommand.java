package xyz.dwaslashe.survivalcore.commands;

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
        if (args.length == 1) {
            OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[0]);
            Api.sendBroadcast("&e&lDZIĘKUJEMY &8>> &fGracz &e" + p2.getName() + " &fkupił u nas coś w sklepie! &eDziękujemy za wsparcie serwera!");
        }
    }
}
