package xyz.dwaslashe.survivalcore.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.upperlevel.spigot.book.BookUtil;

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
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        if ((double) p.getLocation().getYaw() == LocYaw.get(uuid)) {
                            p.kickPlayer(Api.fixColor("&#F23D07&lANTY-AFK \n \n &fZostałeś wyrzucony za \nnie ruszanie się przez &e6 minut!"));
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

        LocYaw.remove(p.getUniqueId());
    }
}
