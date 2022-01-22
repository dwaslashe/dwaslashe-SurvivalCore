package xyz.dwaslashe.survivalcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;

public class AutoBossBarTask extends BukkitRunnable {
    private int id = 0;
    public AutoBossBarTask(Main plugin){
        runTaskTimer(plugin, 0, 20*60*Main.pluginConfig.getAuto().getBossbar().getTime());
    }

    @Override
    public void run() {
        if(Main.pluginConfig.getAuto().getBossbar().getMessages().isEmpty()){
            return;
        } else if(id >= Main.pluginConfig.getAuto().getBossbar().getMessages().size()) id = 0;{
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Api.fixColor(Main.pluginConfig.getAuto().getBossbar().getMessages().get(id)));
            Api.sendLog(Api.fixColor(Main.pluginConfig.getAuto().getBossbar().getMessages().get(id)));
            ++id;
        }
    }
}