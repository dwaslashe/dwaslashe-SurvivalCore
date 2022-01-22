package xyz.dwaslashe.survivalcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;

    public class PlayerTask extends BukkitRunnable {
    public PlayerTask(Main plugin){
        runTaskTimer(plugin, 0, 40);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("core.join.freeze")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                player.setFreezeTicks(100);
                player.sendTitle(Api.fixColor("&b&lZAMROŻONY"), Api.fixColor("&8>> &bJesteś zamrożony dopóki serwer nie wystartuje! &8<<"));
            }
        });
    }
}
