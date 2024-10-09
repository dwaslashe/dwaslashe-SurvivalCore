package xyz.dwaslashe.survivalcore.managers;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Map;

public class TeleportManager {



    protected static final Map<Player, BukkitTask> teleportMap = Maps.newHashMap();

    public static void teleport(Player user, int delay, Location location) {

        if (teleportMap.containsKey(user))
            removeTeleport(user);


        teleportMap.put(user, new BukkitRunnable() {

            int toEnd = delay;
            final Location start = user.getLocation();

            @Override
            public void run() {
                Location current = user.getLocation();
                if (start.getBlockX() != current.getBlockX() || start.getBlockY() != current.getBlockY() || start.getBlockZ() != current.getBlockZ()) {
                    user.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&f楹 &#fc2419Teleportacja została przerwana.. Poruszono się! &f楹"));
                    teleportMap.remove(user);
                    this.cancel();
                    return;
                }
                if (toEnd <= 0) {
                    user.teleport(location, PlayerTeleportEvent.TeleportCause.NETHER_PORTAL);
                    user.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&f楸 &#4cf739Teleportacja przebiegła pomyślnie! &f楸"));
                    teleportMap.remove(user);
                    this.cancel();
                    return;
                }
                toEnd -= 1;
                user.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&f楸 &#4cf739Teleportacja nastąpi za &#ffd56c{seconds}sek &fᎠ &#4cf739do końca! &f楸".replace("{seconds}", toEnd + "")));
            }
        }.runTaskTimer(Main.getPlugin(), 0, 20));
    }

    protected static void removeTeleport(Player user){
        teleportMap.get(user).cancel();
        teleportMap.remove(user);
        user.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&f楹 &#fc2419Teleportacja została anulowana! &f楹"));
    }
}
