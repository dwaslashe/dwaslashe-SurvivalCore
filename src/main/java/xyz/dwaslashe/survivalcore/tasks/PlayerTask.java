package xyz.dwaslashe.survivalcore.tasks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;
import xyz.dwaslashe.survivalcore.utils.RegionApi;

import java.util.*;

public class PlayerTask extends BukkitRunnable {

    private static final Map<UUID, BossBar> barMap = new HashMap<>();

    public static Map<UUID, BossBar> getBarMap() {
        return barMap;
    }

    public PlayerTask(Main plugin){
        runTaskTimer(plugin, 0, 20);
    }

    public static ArrayList<Player> containsmagnet = new ArrayList<>();

    //protected static BossBar bar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID, new BarFlag[0]);

    private String toUpperFirstCharacter(String s){
        if(s.isEmpty()) return "&#FF3131-";
        char c = s.charAt(0);
        return s.replaceFirst(String.valueOf(c), String.valueOf(c).toUpperCase());
    }

    public static ItemStack magnet = new ItemApi(Material.LIGHTNING_ROD)
            .setName("&dMagnez")
            .setLore(Arrays.asList("", " &7Mając magnez w ekwipunku itemy", " &7które niszczysz idą do twojego ekwipunku!"))
            .getItemStack();

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("core.join.freeze")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                player.setFreezeTicks(100);
                player.sendTitle(Api.fixColor("&b&lZAMROŻONY"), Api.fixColor("&8>> &bJesteś zamrożony dopóki serwer nie wystartuje! &8<<"));
            }

            if (player.getInventory().contains(magnet)) {
                containsmagnet.add(player);
            } else if (!player.getInventory().contains(magnet)) {
                containsmagnet.remove(player);
            }

            BossBar bar = barMap.get(player.getUniqueId());
            bar.setProgress(1);
            if (RegionApi.getRegion(player.getLocation(), "spawn")) {
                bar.addPlayer(player);
                String region = PlaceholderAPI.setPlaceholders(player, "%worldguard_region_name%");
                bar.setTitle(PlaceholderAPI.setPlaceholders(player, Api.fixColor("&#FFC42E" + toUpperFirstCharacter(region).replace("_", " ") + " &8/ &#D3D3D3⌚ &#FBFFFF%world_time_world%")));
            } else bar.removePlayer(player);

        });
    }
}
