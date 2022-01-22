package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.TeleportManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class SpawnCommand extends Command {
    public SpawnCommand() {
        super("spawn", "/spawn <nick>", "");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player)sender;
        World world = Bukkit.getWorld("world");
        Location loc = new Location(world, Main.pluginConfig.getSpawn().getX(), Main.pluginConfig.getSpawn().getY(), Main.pluginConfig.getSpawn().getZ(), Main.pluginConfig.getSpawn().getYaw(), Main.pluginConfig.getSpawn().getPitch());
        if (args.length == 0) {
            if (p.isOp()) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowano na spawn");
                p.teleport(loc);
            } else {
                TeleportManager.teleport(p, 5, loc);
            }
        }

        if (args.length == 1) {
            if (!p.hasPermission("core.command.admin")) {
                p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.admin&8) &8<<"));
                return;
            }

            Player p2 = Bukkit.getPlayer(args[0]);
            p2.teleport(loc);
            Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aZostałes przeteleportowany na &espawn");
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowano gracza &a" + p2.getName() + " &ana spawn");
        }
        if (args.length > 1) {
            wrongUsage();
        }
    }
}
