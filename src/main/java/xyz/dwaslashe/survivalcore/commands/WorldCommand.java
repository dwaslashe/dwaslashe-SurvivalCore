package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class WorldCommand extends Command {
    public WorldCommand() {
        super("world", "/world <świat>", "");
        setPermission("core.command.world");
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length == 0) {
            List<World> worlds = Bukkit.getWorlds();
            World spawn = Bukkit.getWorlds().stream().filter(world -> world.getName().equals("spawn")).findAny().orElse(null);
            System.out.println(spawn);
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aObecnie znajdujesz się na świecie &e" + p.getWorld().getName() + "&a, lista dostępnych światów &e" + worlds);
        } else if (args.length == 1) {
            World world = Bukkit.getWorld(args[0]);
            if (world != null) {
                p.teleport(world.getSpawnLocation());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowano na świat &e" + world.getName());
            } else Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cŚwiat o nazwie &e" + world.getName() + " &cnie istnieje!");
        } else if (args.length == 2) {
            Player p2 = Bukkit.getPlayer(args[1]);
            World world = Bukkit.getWorld(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            } else {
                if (world != null) {
                    Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowałeś gracza &e" + p2.getName() + "&a do świata &e" + world.getName());
                    Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aZostałeś przeteleportowany przez &e" + s.getName() + "&a do świata &e" + world.getName());
                    p2.teleport(world.getSpawnLocation());
                } else Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefix() + "&cŚwiat o nazwie &e" + world.getName() + " &cnie istnieje!");
            }
        } else wrongUsage();
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if(args.length == 1) return Api.startsWith(Arrays.asList("swiat"), args[0]);
        return null;
    }
}
