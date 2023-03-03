package xyz.dwaslashe.survivalcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;

public class AutoMessageTask extends BukkitRunnable {
    private int id = 0;
    public AutoMessageTask(Main plugin){
        runTaskTimer(plugin, 0, 20*60*Main.pluginConfig.getAuto().getMessages().getTime());
    }

    @Override
    public void run() {
        if(Main.pluginConfig.getAuto().getMessages().getMessages().isEmpty()){
            return;
        } else if(id >= Main.pluginConfig.getAuto().getMessages().getMessages().size()) id = 0;{
            for (Player all : Bukkit.getOnlinePlayers()) {
                User user = UserCache.getInstance().compute(all.getUniqueId());
                if (user.getAutomsg() == 0) {
                    Api.sendMessage(all, Main.pluginConfig.getAuto().getMessages().getMessages().get(id));
                }
            }
            ++id;
        }
    }
}

