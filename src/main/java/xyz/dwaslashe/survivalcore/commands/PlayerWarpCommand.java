package xyz.dwaslashe.survivalcore.commands;

import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.PlayerWarpCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.TeleportManager;
import xyz.dwaslashe.survivalcore.objects.PlayerWarp;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.List;

public class PlayerWarpCommand extends Command implements Listener {
    public PlayerWarpCommand() {
        super("playerwarp", "/playerwarp", "", "pwarp");
        setPermission("core.command.playerwarp");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if(args.length == 2){
            return Api.startsWith(new ArrayList<>(PlayerWarpCache.getInstance().getWarpMap().keySet()), args[0]);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aLista dostępnych warpów &e" + String.join(", ", new ArrayList<>(PlayerWarpCache.getInstance().getWarpMap().keySet())));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("teleport")) {
            PlayerWarp warp = PlayerWarpCache.getInstance().get(args[1]);
            if(warp == null) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie ma takiego warpa!");
                return;
            }
            if (p.hasPermission("core.command.admin")) {
                p.teleport(warp.getLocation());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowano na warp &e" + args[0] + " &agracza &e" + warp.getNick());
            } else {
                TeleportManager.teleport(p, 5, warp.getLocation());
            }

        } else if (args.length >= 2 && args[0].equalsIgnoreCase("create")) {
            PlayerWarpCache cache = PlayerWarpCache.getInstance();
            PlayerWarp warp = cache.get(args[1]);
            if (warp == null){
                warp = cache.compute(args[1]);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aTwój warp &e" + warp.getName() + " &azostał pomyślnie stworzony!");
                warp.setNick(p.getName());
                warp.setLocation(p.getLocation());
            } else {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz stworzyć takiego samego warpu!");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove") && sender.hasPermission("core.command.admin")) {
            PlayerWarpCache.getInstance().getWarpMap().remove(args[1]);
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie usunięto warp!");
        }
    }
}
