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
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Obecnie znajdujesz się na świecie &#fcb419" + player.getWorld().getName() + "&#4cf739, lista dostępnych światów &#fcb419" + worlds.iterator());
        } else if (args.length == 1) {
            World world = Bukkit.getWorld(args[0]);
            if (world != null) {
                player.teleport(world.getSpawnLocation());
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie przeteleportowano na świat &#fcb419" + world.getName());
            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Świat o nazwie &#fcb419" + world.getName() + " &#fc2419nie istnieje!");
        } else if (args.length == 2) {
            Player secondPlayer = Bukkit.getPlayer(args[1]);
            World world = Bukkit.getWorld(args[0]);
            if (secondPlayer == null) {
                offlinePlayer();
                return;
            } else {
                if (world != null) {
                    Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie przeteleportowałeś gracza &#fcb419" + secondPlayer.getName() + "&#4cf739 do świata &#fcb419" + world.getName());
                    Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zostałeś przeteleportowany przez &#fcb419" + s.getName() + "&#4cf739 do świata &#fcb419" + world.getName());
                    secondPlayer.teleport(world.getSpawnLocation());
                } else Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Świat o nazwie &#fcb419" + world.getName() + " &#fc2419nie istnieje!");
            }
        } else wrongUsage();
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if(args.length == 1) return Api.startsWith(Arrays.asList("swiat"), args[0]);
        return null;
    }
}
