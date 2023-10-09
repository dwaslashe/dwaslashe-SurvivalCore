package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import de.netzkronehd.wgregionevents.events.RegionEnteredEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;

import java.util.*;

public class RegionListener implements Listener {

    public static ArrayList<UUID> afk = new ArrayList<>();

    @EventHandler
    public void onRegionEntered(RegionEnteredEvent e) {
        if (e.getRegion().getId().contains("afk") && !e.getPlayer().isInsideVehicle() && !afk.contains(e.getPlayer().getUniqueId())) {
            afk.add(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onRegionLeft(RegionLeftEvent e) {
        if (e.getRegion().getId().contains("afk")) {
            afk.remove(e.getPlayer().getUniqueId());
        }
    }
}
