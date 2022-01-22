package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Maps;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.Map;

public class PlayerChatListener implements Listener {
    protected static final Map<Player, Long> delay = Maps.newHashMap();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final String message = e.getMessage();

        //Format chat player
        e.setFormat(format(player).replace("{MESSAGE}", player.hasPermission("core.chat.color") ? Api.fixColor(message) : message).replace("%", "%%"));

        // Cooldown chat player
        if (delay.containsKey(player) && delay.get(player) > System.currentTimeMillis()) {
            Api.sendMessage(player, Main.pluginConfig.getCooldown().getMessage().replace("{TIME}", TimerApi.secondsToString(delay.get(player))));
            e.setCancelled(true);
            return;
        }
        if (delay.containsKey(player))
            e.setCancelled(false);
        delay.remove(player);
        delay.put(player, TimerApi.parseDateDiff(Main.pluginConfig.getCooldown().getTime(), true));
        if (player.hasPermission("core.chat.cooldown.bypass")) {
            delay.remove(player);
            e.setCancelled(false);
        }
    }

    private String format(Player player) {
        String replacedFormat = Main.pluginConfig.getChat().getFormat().replace("{WORLD}", player.getWorld().getName()).replace("{PREFIX}", ChatApi.getPrefix(player)).replace("{PLAYER}", player.getDisplayName()).replace("{SUFFIX}", ChatApi.getSuffix(player));
        return Api.fixColor(PlaceholderAPI.setPlaceholders(player, replacedFormat));
    }
}
