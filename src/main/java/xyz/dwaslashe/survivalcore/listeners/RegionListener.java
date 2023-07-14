package xyz.dwaslashe.survivalcore.listeners;

import net.saidora.economy.manager.UserManager;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import de.netzkronehd.wgregionevents.events.RegionEnteredEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.*;

public class RegionListener implements Listener {

    public static ArrayList<UUID> afk = new ArrayList<>();

    @EventHandler
    public void onRegionEntered(RegionEnteredEvent e) {
        if (e.getRegion().getId().contains("afk") && !e.getPlayer().isInsideVehicle()) {
            afk.add(e.getPlayer().getUniqueId());
            Main.getPlugin().getServer().getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                if (afk.contains(e.getPlayer().getUniqueId()) && !e.getPlayer().isInsideVehicle()) {
                    Api.sendActionBar(e.getPlayer(), "&8>> <#39FF14>Obecnie jesteś w strefie afk, co minute dostajesz <#FFF88F>2 <#FFC42E>$ &8<<");
                }
            }, 20, 20);

            (new BukkitRunnable() {
                @Override
                public void run() {
                    if (afk.contains(e.getPlayer().getUniqueId()) && !e.getPlayer().isInsideVehicle()) {
                        PlayerQuitListener.LocYaw.remove(e.getPlayer().getUniqueId());
                        e.getPlayer().sendTitle(Api.fixColor("&#F23D07&lAFK"), Api.fixColor("&8>> &aZa spędzenie minuty w strefie afk dosałeś &#FFF88F2 &#FFC42E$&a! &8<<"));
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, 10, 10);
                        UserManager.getInstance().getUser(e.getPlayer()).ifPresent(user -> {
                            user.deposit(2);
                        });
                    } else {
                        this.cancel();
                    }
                }
            }).runTaskTimer(Main.getPlugin(), 20 * 60, 20 * 60);

        }
    }

    @EventHandler
    public void onRegionLeft(RegionLeftEvent e) {
        if (e.getRegion().getId().contains("afk")) {
            afk.remove(e.getPlayer().getUniqueId());
        }
    }
}
