package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.model.User;
import xyz.dwaslashe.survivalcore.model.impl.UserImpl;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerQuitListener implements Listener {
    public static Map<UUID, Double> LocYaw = new HashMap<UUID, Double>();

    public static void checkPlayer(Player p) {
        UUID uuid = p.getUniqueId();

        if (LocYaw.get(uuid) != null) {
            if ((double) p.getLocation().getYaw() == LocYaw.get(uuid)) {
                p.sendTitle(Api.fixColor("&#F23D07&lANTY-AFK"), Api.fixColor("&8>> &fe, śpisz? &8<<"));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 10, 10);
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        if (((double) p.getLocation().getYaw()) == LocYaw.get(uuid)) {
                            Api.sendPlayerToServer(p, "LOBBYAFK");
                        }
                    }
                }, 20 * 60);
            } else {
                LocYaw.put(uuid, (double) p.getLocation().getYaw());
            }
        } else {
            LocYaw.put(uuid, (double) p.getLocation().getYaw());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), () -> {
            LocYaw.remove(p.getUniqueId());
            Main.getPlugin().getUserCache().getOnline(p.getName()).ifPresent(user -> user.setOnline(false));
        }, 5);
    }
}
