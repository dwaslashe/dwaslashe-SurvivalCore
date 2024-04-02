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
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Lista dostępnych warpów &#fcb419" + String.join(", ", new ArrayList<>(PlayerWarpCache.getInstance().getWarpMap().keySet())));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("teleport")) {
            PlayerWarp warp = PlayerWarpCache.getInstance().get(args[1]);
            if(warp == null) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie ma takiego warpa!");
                return;
            }
            if (p.hasPermission("core.command.admin")) {
                p.teleport(warp.getLocation());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie przeteleportowano na warp &#fcb419" + args[0] + " &#4cf739gracza &#fcb419" + warp.getNick());
            } else {
                TeleportManager.teleport(p, 5, warp.getLocation());
            }

        } else if (args.length >= 2 && args[0].equalsIgnoreCase("create")) {
            PlayerWarpCache cache = PlayerWarpCache.getInstance();
            PlayerWarp warp = cache.get(args[1]);
            if (warp == null){
                warp = cache.compute(args[1]);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twój warp &#fcb419" + warp.getName() + " &#4cf739został pomyślnie stworzony!");
                warp.setNick(p.getName());
                warp.setLocation(p.getLocation());
            } else {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz stworzyć takiego samego warpu!");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove") && sender.hasPermission("core.command.admin")) {
            PlayerWarpCache.getInstance().getWarpMap().remove(args[1]);
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie usunięto warp!");
        }
    }
}
