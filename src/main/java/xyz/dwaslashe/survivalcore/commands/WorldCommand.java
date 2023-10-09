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
        Player player = (Player) s;
        if (args.length == 0) {
            List<World> worlds = Bukkit.getWorlds();
            World spawn = Bukkit.getWorlds().stream().filter(world -> world.getName().equals("spawn")).findAny().orElse(null);
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aObecnie znajdujesz się na świecie &e" + player.getWorld().getName() + "&a, lista dostępnych światów &e" + worlds.iterator());
        } else if (args.length == 1) {
            World world = Bukkit.getWorld(args[0]);
            if (world != null) {
                player.teleport(world.getSpawnLocation());
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowano na świat &e" + world.getName());
            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cŚwiat o nazwie &e" + world.getName() + " &cnie istnieje!");
        } else if (args.length == 2) {
            Player secondPlayer = Bukkit.getPlayer(args[1]);
            World world = Bukkit.getWorld(args[0]);
            if (secondPlayer == null) {
                offlinePlayer();
                return;
            } else {
                if (world != null) {
                    Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowałeś gracza &e" + secondPlayer.getName() + "&a do świata &e" + world.getName());
                    Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefix() + "&aZostałeś przeteleportowany przez &e" + s.getName() + "&a do świata &e" + world.getName());
                    secondPlayer.teleport(world.getSpawnLocation());
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
