package xyz.dwaslashe.survivalcore.tasks;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RegionApi;

import java.util.*;

public class PlayerTask extends BukkitRunnable {

    private static final Map<UUID, BossBar> barMap = new HashMap<>();

    public static Map<UUID, BossBar> getBarMap() {
        return barMap;
    }

    public PlayerTask(Main plugin) {
        runTaskTimer(plugin, 0, 20);
    }

    //protected static BossBar bar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID, new BarFlag[0]);

    private String toUpperFirstCharacter(String s){
        if(s.isEmpty()) return "&#FF3131-";
        char c = s.charAt(0);
        return s.replaceFirst(String.valueOf(c), String.valueOf(c).toUpperCase());
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            //if (player.hasPermission("core.join.freeze")) {
            //    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
            //    player.setFreezeTicks(100);
            //    player.sendTitle(Api.fixColor("&b&lZAMROŻONY"), Api.fixColor("&8>> &bJesteś zamrożony dopóki serwer nie wystartuje! &8<<"));
            //}

            BossBar bar = barMap.get(player.getUniqueId());

            ProtectedRegion region = null;
            if ((region = RegionApi.getRegion(player.getLocation(), "spawn")) != null) {
                if(!bar.getPlayers().contains(player)) bar.addPlayer(player);
                bar.setTitle(PlaceholderAPI.setPlaceholders(player, Api.fixColor("&#FFC42E" + toUpperFirstCharacter(region.getId()).replace("_", " ") + " &8/ &#D3D3D3⌚ &#FBFFFF%world_time_world%")));
            } else bar.removePlayer(player);

        });
    }
}
