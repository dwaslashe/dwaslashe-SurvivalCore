package xyz.dwaslashe.survivalcore.tasks;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.TicketCache;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.cache.WarpCache;
import xyz.dwaslashe.survivalcore.listeners.PlayerInteractListener;
import xyz.dwaslashe.survivalcore.objects.*;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RegionApi;
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

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            Ticket ticketPlayer = TicketCache.getInstance().compute(offlinePlayer.getUniqueId());

            if (ticketPlayer.getTime() > ticketPlayer.getMaxTime()) {
                ticketPlayer.setEnable(0);
                ticketPlayer.setEnableJail(1);
                ticketPlayer.setOnlineTimeOut(0);
                ticketPlayer.setTimeMaxJail(ticketPlayer.getValue() * TimerApi.getTime("1s"));
            }

            if (ticketPlayer.getEnable() == 1) {
                ticketPlayer.addTime(TimerApi.getTime("1s"));
            }
        }

        for (Player allPlayers : Bukkit.getOnlinePlayers()) {

            if (PlayerInteractListener.loadingProgress.containsKey(allPlayers.getName())) {
                PlayerInteractListener.showLoadingScreen(allPlayers);
            }

            Ticket ticketPlayer = TicketCache.getInstance().compute(allPlayers.getUniqueId());

            if ((ticketPlayer.getEnableJail() == 1) || (ticketPlayer.getEnableJail() == 1 && ticketPlayer.getOnlineTimeOut() == 0)) {
                if (ticketPlayer.getTimeJail() > ticketPlayer.getTimeMaxJail()) {

                    allPlayers.playSound(allPlayers.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                    allPlayers.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 50, false, false, false));
                    allPlayers.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 50, false, false, false));
                    allPlayers.sendTitle(Api.fixColor("&#c91212&lUNIEWAŻNIENIE MANDATU"), Api.fixColor("&8>> &#4cf739Spędziłeś cały czas w więzieniu! Zostałeś uwolniony! &8<<"));

                    ticketPlayer.setEnableJail(0);
                    ticketPlayer.setEnable(0);
                    ticketPlayer.setTime(0);
                    ticketPlayer.setMaxTime(0);
                    ticketPlayer.setValue(0);
                    ticketPlayer.setTimeJail(0);
                    ticketPlayer.setTimeMaxJail(0);
                }

                Api.sendActionBar(allPlayers, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Jeseteś w więzieniu musisz odczekać: <#ffd56c>" + TimerApi.getDurationBreakdownShort(ticketPlayer.getTimeMaxJail() - ticketPlayer.getTimeJail()) + " <#ffc942>⌚ &8<<");
                ticketPlayer.addTimeJail(TimerApi.getTime("1s"));
            }

            if (ticketPlayer.getEnable() == 1) {

                if (ticketPlayer.getTime() > ticketPlayer.getMaxTime()) {
                    Warp warp = WarpCache.getInstance().get("wiezienie");
                    allPlayers.teleport(warp.getLocation());

                    ticketPlayer.setEnable(0);
                    ticketPlayer.setEnableJail(1);
                    ticketPlayer.setOnlineTimeOut(1);
                    ticketPlayer.setTimeMaxJail(ticketPlayer.getValue() * TimerApi.getTime("1s"));

                    allPlayers.playSound(allPlayers.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                    allPlayers.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 50, false, false, false));
                    allPlayers.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 50, false, false, false));
                    allPlayers.sendTitle(Api.fixColor("&#c91212&lBRAK OPŁATY MANDATU"), Api.fixColor("&8>> &#4cf739Zostałeś przeniesiony do więzienia na czas: &#ffd56c" + TimerApi.getDurationBreakdownShort(ticketPlayer.getTimeMaxJail()) + " &fᎠ &8<<"));
                }

                if (Integer.valueOf(String.valueOf(ticketPlayer.getTime())) % 6000 == 0) {
                    allPlayers.playSound(allPlayers.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                    allPlayers.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 50, false, false, false));
                    allPlayers.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 50, false, false, false));
                    allPlayers.sendTitle(Api.fixColor("&#c91212&lPRZYPOMNIENIE MANDATU"), Api.fixColor("&8>> &#4cf739Pozostało: &#ffd56c" + TimerApi.getDurationBreakdownShort(ticketPlayer.getMaxTime() - ticketPlayer.getTime()) + " &fᎠ &8<<"));
                }
                ticketPlayer.addTime(TimerApi.getTime("1s"));
            }

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