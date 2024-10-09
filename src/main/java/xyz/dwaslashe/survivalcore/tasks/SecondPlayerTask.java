package xyz.dwaslashe.survivalcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.objects.*;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.*;

public class SecondPlayerTask extends BukkitRunnable {
    private static final Map<UUID, BossBar> barMap = new HashMap<>();

    public static Map<UUID, BossBar> getBarMap() {
        return barMap;
    }
    public SecondPlayerTask(Main plugin) {
        runTaskTimer(plugin, 0, 20);
    }

    private String toUpperFirstCharacter(String s){
        if(s.isEmpty()) return "&#FF3131-";
        char c = s.charAt(0);
        return s.replaceFirst(String.valueOf(c), String.valueOf(c).toUpperCase());
    }
    @Override
    public void run() {
        List<UUID> keysToRemove = new ArrayList<>();
        Protection.getProtectionMap().forEach((uuid, protection) -> {
            if (protection.getProtection() > System.currentTimeMillis()) {
                if (Bukkit.getPlayer(uuid) != null) protection.getBar().addPlayer(Bukkit.getPlayer(uuid));
                protection.getBar().setTitle(Api.fixColor("&8>> &#d3f4f5Twoja &#0394fc\uD83D\uDEE1 &#037bfc&lOCHRONA &#0394fc\uD83D\uDEE1 &#d3f4f5trwać będzie jeszcze &#ffd56c{TIME} &fᎠ &8<<").replace("{TIME}", TimerApi.secondsToString(protection.getProtection())));
                protection.getBar().setProgress(Api.mapLongToDouble((protection.getProtection() - System.currentTimeMillis()), 0L, protection.getMaxTimeProtection()));
            } else {
                keysToRemove.add(uuid);
                protection.getBar().setVisible(false);
                Protection.getProtectionMap().remove(uuid, protection);
                Protection.getProtectionMap().remove(uuid);
            }
        });

        for (UUID key : keysToRemove) {
            Protection.getProtectionMap().remove(key);
        }

        //Bukkit.getOnlinePlayers().forEach(player -> {
        //    PlayerTime user = PlayerTime.getPlayer(player);
        //    long time = System.currentTimeMillis();
        //    long playTime = time - user.getTime();
        //    if (Main.pluginConfig.getEvents().getBossBarSpawn().isEnable()) {
        //        BossBar bar = barMap.get(player.getUniqueId());
        //        ProtectedRegion region = null;
        //        if ((region = RegionApi.getRegion(player.getLocation(), "spawn")) != null) {
        //            if (!bar.getPlayers().contains(player)) bar.addPlayer(player);
        //            String regionName = PlaceholderAPI.setPlaceholders(player, "%worldguard_region_name%");
        //            bar.setTitle(Api.fixColor(Main.pluginConfig.getEvents().getBossBarSpawn().getTitle()).replace("{region}", toUpperFirstCharacter(regionName).replace("_", " ")).replace("{playTime}", TimerApi.getDurationBreakdownShort(playTime)));
        //        } else bar.removePlayer(player);
        //    }
        //});
    }
}