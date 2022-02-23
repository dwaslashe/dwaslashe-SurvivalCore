package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();

        if (e.getEntity().getKiller() instanceof Player) {
            Player killer = e.getEntity().getKiller();
            e.setDeathMessage(Api.fixColor(Main.pluginConfig.getMessages().getPrefix() + "&cGracz &e{PLAYER} &czostał zabity przez &e{KILLER} &cz przewagą &#E1350B{HEALTH} &4❤"
                    .replace("{PLAYER}", p.getDisplayName())
                    .replace("{KILLER}", killer.getDisplayName())
                    .replace("{HEALTH}", Math.round(killer.getHealth()) + "")));
        } else e.setDeathMessage(Api.fixColor(Main.pluginConfig.getMessages().getPrefix() + "&e{PLAYER} &cumarł".replace("{PLAYER}", p.getDisplayName())));
    }
}
