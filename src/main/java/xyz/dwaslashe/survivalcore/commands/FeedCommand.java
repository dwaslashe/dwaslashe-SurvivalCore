package xyz.dwaslashe.survivalcore.commands;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FeedCommand extends Command {
    public FeedCommand() {
        super("feed", "/feed <nick>", "");
        setPermission("core.command.feed");
        setOnlyPlayer(true);
    }

    protected static final Map<Player, Long> delayHook = Maps.newHashMap();
    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            if (delayHook.containsKey(player) && delayHook.get(player) > System.currentTimeMillis()) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby ponownie użyj tej komendy musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayHook.get(player))));
                player.closeInventory();
                return;
            }
            delayHook.remove(player);
            player.setFoodLevel(20);
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie się najadłeś");
            delayHook.put(player, TimerApi.parseDateDiff("5m", true));
        } else if (args.length == 1) {
            if (!player.hasPermission("core.command.admin")) {
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &#fc2419Nie posiadasz uprawnien &8(&#fcb419core.command.admin&8) &8<<"));
                return;
            }
            Player secondPlayer = Bukkit.getPlayer(args[0]);
            if (secondPlayer == null) {
                offlinePlayer();
                return;
            } else {
                secondPlayer.setFoodLevel(20);
                Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zostałeś najedzony przez &#fcb419" + player.getName());
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie najadałeś gracza &#fcb419" + secondPlayer.getName());
            }
        } else wrongUsage();
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }
}
