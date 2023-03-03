package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.WarpCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.TeleportManager;
import xyz.dwaslashe.survivalcore.objects.Warp;
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

        if(sender instanceof Player player) {
            Warp warp = WarpCache.getInstance().get("spawn");
            if (warp == null) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie ma stworzonego spawna!");
                return;
            }
            if (player.hasPermission("core.command.admin")) {
                player.teleport(warp.getLocation());
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowano na &espawn");
            } else {
                TeleportManager.teleport(player, 5, warp.getLocation());
            }
        }
    }
}
