package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.TeleportManager;
import xyz.dwaslashe.survivalcore.model.impl.WarpImpl;
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

        if(sender instanceof Player player){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("set")){
                    Main.getPlugin().getWarpCache().getWarp("spawn").ifPresentOrElse(warp -> {
                        ((WarpImpl)warp).entry(warp1 -> warp1.setLocation(player.getLocation())).addToSQL();
                    }, () -> ((WarpImpl)Main.getPlugin().getWarpCache().getOrCreate("spawn")).entry(warp -> warp.setLocation(player.getLocation())).addToSQL());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aNowa lokalizacja &espawnu &azostała ustawiona");
                } else sender.sendMessage(getUsage());
            } else {
                Main.getPlugin().getWarpCache().getWarp("spawn").ifPresentOrElse(warp -> {
                    if (player.hasPermission("core.command.admin")) {
                        player.teleport(warp.location());
                    } else {
                        TeleportManager.teleport(player, 5, warp.location());
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowano na &espawn");
                    }
                }, () -> Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aNie ma warpa &espawn"));
            }
        }
    }
}
