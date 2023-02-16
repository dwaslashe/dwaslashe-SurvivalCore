package xyz.dwaslashe.survivalcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HappyVariable extends BukkitRunnable {
    private Main plugin;

    public HappyVariable(Main plugin) {
        this.plugin = plugin;
        runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void run() {
        if (getTime(System.currentTimeMillis()).equalsIgnoreCase("18:00:00")) {
            Api.sendBroadcast("&6[DobraNocka] &eDzieci, juz godzina &7( &618:00:00 &7)&e, administracja &e&lAvi&6&lMC&e zaprasza do ogladania bajki na dobranoc i spania, bo sen to zdrowie! &eA dla urwisow o godzinie &620:00 &euruchomimy turbodrop na 4 godziny!");
            //for (Player p : Bukkit.getOnlinePlayers()) {
            //    if (getTime(p.getOnline()).equalsIgnoreCase("00:05:00") || getTime(u.getOnline()).equalsIgnoreCase("01:05:00") || getTime(u.getOnline()).equalsIgnoreCase("02:05:00")) {
            //        if (u.isPremium()) {
            //            sendTitle(u.getPlayer(), "&6&lMC PREMIUM REWARD", "&eOtrzymales(-as) &650 coinsow &eza granie na serwerze!", 15, 40, 20);
            //            u.addCoins(50);
            //            u.save();
            //        } else {
            //            sendTitle(u.getPlayer(), "&6&lMC PREMIUM REWARD", "&eOtrzymales(-as) &620 coinsow &eza granie na serwerze!", 15, 40, 20);
            //            u.addCoins(20);
            //            u.save();
            //        }
            //    } else if (getTime(u.getOnline()).equalsIgnoreCase("00:30:00") || getTime(u.getOnline()).equalsIgnoreCase("01:30:00") || getTime(u.getOnline()).equalsIgnoreCase("02:30:00")) {
            //        if (u.isPremium()) {
            //            sendTitle(u.getPlayer(), "&6&lMC PREMIUM REWARD", "&eOtrzymales(-as) &650 coinsow &eza granie na serwerze!", 15, 40, 20);
            //            u.addCoins(50);
            //            u.save();
            //        } else {
            //            sendTitle(u.getPlayer(), "&6&lMC PREMIUM REWARD", "&eOtrzymales(-as) &620 coinsow &eza granie na serwerze!", 15, 40, 20);
            //            u.addCoins(20);
            //            u.save();
            //        }
            //    } else if (getTime(u.getOnline()).equalsIgnoreCase("01:00:00") || getTime(u.getOnline()).equalsIgnoreCase("02:00:00") || getTime(u.getOnline()).equalsIgnoreCase("03:00:00")) {
            //        if (u.isPremium()) {
            //            sendTitle(u.getPlayer(), "&6&lMC PREMIUM REWARD", "&eOtrzymales(-as) &650 coinsow &eza granie na serwerze!", 15, 40, 20);
            //            u.addCoins(50);
            //            u.save();
            //        } else {
            //            sendTitle(u.getPlayer(), "&6&lMC PREMIUM REWARD", "&eOtrzymales(-as) &620 coinsow &eza granie na serwerze!", 15, 40, 20);
            //            u.addCoins(20);
            //            u.save();
            //        }
            //    }
            //}
        }

    }

    private String getTime(long time) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(time));
    }
}
