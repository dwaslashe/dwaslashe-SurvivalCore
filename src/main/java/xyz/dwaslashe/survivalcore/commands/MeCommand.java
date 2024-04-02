package xyz.dwaslashe.survivalcore.commands;

import com.google.common.collect.Maps;
import litebans.api.Database;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.List;
import java.util.Map;

public class MeCommand extends Command {
    public MeCommand() {
        super("me", "/me <tresc>", "", "minecraft:me");
        setOnlyPlayer(true);
    }

    protected static final Map<String, Long> delayCommand = Maps.newHashMap();

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length >= 1) {
            if (delayCommand.containsKey(player.getName()) && delayCommand.get(player.getName()) > System.currentTimeMillis()) {
                if (player.hasPermission("core.cooldown.bypass")) return;
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby wpisać ponownie tą komende musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayCommand.get(player.getName()))));
                return;
            }
            delayCommand.remove(player.getName());

                List<Player> playerList = Api.getNearbyPlayers(player, 20);
            Api.sendMessage(sender, "&#8f8f8f* " + player.getName() + " &#E7E7E7" + StringUtils.join(args, " ", 0, args.length));
            for (Player getPlayer : playerList) {
                Api.sendMessage(getPlayer, "&#8f8f8f* " + player.getName() + " &#E7E7E7" + StringUtils.join(args, " ", 0, args.length));
            }

            delayCommand.put(player.getName(), TimerApi.parseDateDiff("3s", true));
        } else wrongUsage();
    }
}
