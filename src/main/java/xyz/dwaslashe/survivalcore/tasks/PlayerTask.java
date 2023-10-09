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
import xyz.dwaslashe.survivalcore.objects.Logout;
import xyz.dwaslashe.survivalcore.objects.Protection;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RegionApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.*;

public class PlayerTask extends BukkitRunnable {

    private static final Map<UUID, BossBar> barMap = new HashMap<>();

    public static Map<UUID, BossBar> getBarMap() {
        return barMap;
    }

    public PlayerTask(Main plugin) {
        runTaskTimer(plugin, 0, 1);
    }

    private String toUpperFirstCharacter(String s){
        if(s.isEmpty()) return "&#FF3131-";
        char c = s.charAt(0);
        return s.replaceFirst(String.valueOf(c), String.valueOf(c).toUpperCase());
    }

    @Override
    public void run() {
        Protection.getProtectionMap().forEach((uuid, protection) -> {
            if (protection.getProtection() > System.currentTimeMillis()) {
                protection.getBar().addPlayer(Bukkit.getPlayer(uuid));
                protection.getBar().setTitle(Api.fixColor("&8>> &#d3f4f5Twoja &#0394fc\uD83D\uDEE1 &#037bfc&lOCHRONA &#0394fc\uD83D\uDEE1 &#d3f4f5trwać będzie jeszcze &#ffd56c{TIME} &#ffc942⌚ &8<<").replace("{TIME}", TimerApi.secondsToString(protection.getProtection())));
                protection.getBar().setProgress(Api.mapLongToDouble((protection.getProtection() - System.currentTimeMillis()), 0L, protection.getMaxTimeProtection()));
            } else {
                protection.getBar().setVisible(false);
                Protection.getProtectionMap().remove(uuid, protection);
                Protection.getProtectionMap().remove(uuid);
            }
        });

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


            Logout logout = Logout.get(player);
            if (logout.getTime() > System.currentTimeMillis()) {
                if (player.hasPermission("core.command.admin")) return;
                player.setFlying(false);
            }
        });
    }
}
