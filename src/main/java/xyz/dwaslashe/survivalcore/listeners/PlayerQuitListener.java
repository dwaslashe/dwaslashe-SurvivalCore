package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.objects.PlayerTime;
import xyz.dwaslashe.survivalcore.tasks.SecondPlayerTask;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerQuitListener implements Listener {
    public static Map<UUID, Double> locYaw = new HashMap<UUID, Double>();

    public static void checkPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        if (locYaw.get(uuid) != null) {
            if ((double) player.getLocation().getYaw() == locYaw.get(uuid)) {
                player.sendTitle(Api.fixColor("&#F23D07&lANTY-AFK"), Api.fixColor("&8>> &fe, śpisz? &8<<"));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 10, 10);
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        if (!(((double) player.getLocation().getYaw()) == locYaw.get(uuid))) return;
                        if (((double) player.getLocation().getYaw()) == locYaw.get(uuid)) {
                            Api.sendPlayerToServer(player, "LOBBYAFK");
                        }
                    }
                }, 20 * 60);
            } else {
                locYaw.put(uuid, (double) player.getLocation().getYaw());
            }
        } else {
            locYaw.put(uuid, (double) player.getLocation().getYaw());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();

        PlayerTime playerTime = PlayerTime.getPlayer(player);
        playerTime.setTime("0s");
        PlayerTime.getUsers().remove(PlayerTime.getPlayer(player));

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), () -> {
            locYaw.remove(player.getUniqueId());
        }, 5);
        SecondPlayerTask.getBarMap().remove(player.getUniqueId());
    }
}
