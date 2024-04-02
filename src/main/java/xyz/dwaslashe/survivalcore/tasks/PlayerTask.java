package xyz.dwaslashe.survivalcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.objects.Logout;

public class PlayerTask extends BukkitRunnable {

    public PlayerTask(Main plugin) {
        runTaskTimer(plugin, 0, 1);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            //if (player.hasPermission("core.join.freeze")) {
            //    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
            //    player.setFreezeTicks(100);
            //    player.sendTitle(Api.fixColor("&b&lZAMROŻONY"), Api.fixColor("&8>> &bJesteś zamrożony dopóki serwer nie wystartuje! &8<<"));
            //}


            Logout logout = Logout.get(player);
            if (logout.getTime() > System.currentTimeMillis()) {
                if (player.hasPermission("core.command.admin")) return;
                player.setFlying(false);
            }
        });
    }
}
