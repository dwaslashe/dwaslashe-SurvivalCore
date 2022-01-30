package xyz.dwaslashe.survivalcore.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
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
            Api.sendBroadcast(Api.fixColor(Main.pluginConfig.getAuto().getMessages().getMessages().get(id)));
            ++id;
        }
    }
}

