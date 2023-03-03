package xyz.dwaslashe.survivalcore.commands;

import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.WarpCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.TeleportManager;
import xyz.dwaslashe.survivalcore.objects.Warp;
import xyz.dwaslashe.survivalcore.utils.Api;
import java.util.ArrayList;
import java.util.List;

public class WarpCommand extends Command implements Listener {
    public WarpCommand() {
        super("warp", "/warp", "", "warps", "warpy");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if(args.length == 1){
            return Api.startsWith(new ArrayList<>(WarpCache.getInstance().getWarpMap().keySet()), args[0]);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aLista dostępnych warpów &e" + String.join(", ", new ArrayList<>(WarpCache.getInstance().getWarpMap().keySet())));
        } else if (args.length == 1) {
            Warp warp = WarpCache.getInstance().get(args[0]);
            if(warp == null){
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie ma takiego warpa!");
                return;
            } else if(warp.getName().equalsIgnoreCase("sprawdzarka")){
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cTen warp jest zablokowany!");
                return;
            }
            if (p.hasPermission("core.command.admin")) {
                p.teleport(warp.getLocation());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowano na &e" + args[0]);
            } else {
                TeleportManager.teleport(p, 5, warp.getLocation());
            }

        } else if (args.length == 2 && args[0].equalsIgnoreCase("set") && sender.hasPermission("core.command.admin")) {
            WarpCache cache = WarpCache.getInstance();
            Warp warp = cache.get(args[1]);
            if(warp == null){
                warp = cache.compute(args[1]);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aWarp &e" + warp.getName() + " &azostał pomyślnie stworzony!");
            } else {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zaktulizowano istniejący warp!");
            }
            warp.setLocation(p.getLocation());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove") && sender.hasPermission("core.command.admin")) {
            WarpCache.getInstance().getWarpMap().remove(args[1]);
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie usunięto warp!");
        }
    }
}
