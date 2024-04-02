package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TpaCommand extends Command {
    public static Map<String, Long> tpaCooldown = new HashMap();
    public static Map<String, String> currentRequest = new HashMap();
    public TpaCommand() {
        super("tpa", "/tpa <gracz>", "");
        setOnlyPlayer(true);
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player p = (Player)s;
        if (args.length == 1) {
            final Player target = Bukkit.getServer().getPlayer(args[0]);
            long keepAlive = 500L;
            if (target == null) {
                offlinePlayer();
                return;
            }

            if (target == p) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie mozesz sie tepac do siebie!");
            }

            this.sendRequest(p, target);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                public void run() {
                    TpaCommand.this.killRequest(target.getName());
                }
            }, keepAlive);
            tpaCooldown.put(p.getName(), System.currentTimeMillis());
        } else {
            wrongUsage();
        }

    }

    public void sendRequest(Player p, Player p2) {
        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Wysłano prośbe o teleportacje");
        Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + p.getDisplayName() + " &#4cf739prosi o teleportacje. &#4cf739Aby zakceptować teleportacje wpisz &#fcb419/tpaccept&#4cf739, aby anulować teleportację wpisz &#fcb419/tpadeny");
        currentRequest.put(p2.getName(), p.getName());
    }

    public boolean killRequest(String key) {
        if (currentRequest.containsKey(key)) {
            Player loser = Bukkit.getServer().getPlayer((String)currentRequest.get(key));
            if (loser != null) {
                Api.sendMessage(loser, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Prośba o teleport wygasła!");
            }

            currentRequest.remove(key);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }
}