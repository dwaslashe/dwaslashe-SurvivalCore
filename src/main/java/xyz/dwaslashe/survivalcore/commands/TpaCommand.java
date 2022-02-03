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
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie mozesz sie tepac do siebie!");
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
        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aWysłano prośbe o teleportacje");
        Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aGracz &e" + p.getDisplayName() + " &aprosi o teleportacje. &aAby zakceptować teleportacje wpisz &e/tpaccept&a, aby anulować teleportację wpisz &e/tpadeny");
        currentRequest.put(p2.getName(), p.getName());
    }

    public boolean killRequest(String key) {
        if (currentRequest.containsKey(key)) {
            Player loser = Bukkit.getServer().getPlayer((String)currentRequest.get(key));
            if (loser != null) {
                Api.sendMessage(loser, Main.pluginConfig.getMessages().getPrefix() + "&cProśba o teleport wygasła!");
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