package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TpCommand extends Command {

    public TpCommand() {
        super("tp", "/tp <x, gracz> <y, gracz> <z> <gracz>", "");
        setPermission("core.command.tp");
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player p = (Player) s;
        Player p2;
        if (args.length == 0) {
            wrongUsage();
            return;
        } else if (args.length == 1) {
            p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            } else {
                Location loc = p2.getLocation();
                p.teleport(loc);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZostales przeteleportowany do &e" + p2.getName());
            }
        } else if (args.length == 2) {
            p2 = Bukkit.getPlayer(args[0]);
            Player p3 = Bukkit.getPlayer(args[1]);
            if (p2 != null && p3 != null) {
                Location loc = p3.getLocation();
                p2.teleport(loc);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aGracz &e" + p3.getName() + " &ateleportowal sie do ciebie!");
                Api.sendMessage(p3, Main.pluginConfig.getMessages().getPrefix() + "&aZostales przeteleportowany do &e" + p2.getName());
            } else {
                offlinePlayer();
                return;
            }
        } else if (args.length == 3) {
            if (Api.isInt(args[0])) {
                final double z = Double.parseDouble(args[args.length - 1]);
                final double y = Double.parseDouble(args[args.length - 2]);
                final double x = Double.parseDouble(args[args.length - 3]);
                Location loc = new Location(p.getWorld(), x, y, z);
                p.teleport(loc);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zostałeś przeteleportowany na kordynaty!");
            } else wrongUsage();
        } else if (args.length == 4) {
            p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            } else {
                final double z = Double.parseDouble(args[args.length - 1]);
                final double y = Double.parseDouble(args[args.length - 2]);
                final double x = Double.parseDouble(args[args.length - 3]);
                Location loc = new Location(p.getWorld(), x, y, z);
                p2.teleport(loc);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie gracz &e" + p2.getName() + " &azostał przeteleportwany na kordynaty!");
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aZostałeś przeteleportowany na kordynaty!");
            }
        } else wrongUsage();
    }


    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        if (args.length == 1) return Api.startsWith(Arrays.asList("x"), args[0]);
        if (args.length == 2) return Collections.singletonList("[players]");
        if (args.length == 2) return Api.startsWith(Arrays.asList("y"), args[0]);
        if (args.length == 3) return Collections.singletonList("[players]");
        if (args.length == 3) return Api.startsWith(Arrays.asList("z"), args[0]);
        if (args.length == 4) return Collections.singletonList("[players]");
        return null;
    }
}
