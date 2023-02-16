package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.helpers.DiscordHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        e.setDeathMessage(null);
        if (Main.pluginConfig.getEvents().isDeathmessage()) {
            if (e.getEntity().getKiller() instanceof Player) {
                Player killer = e.getEntity().getKiller();

                Api.sendDeathNotify(Main.pluginConfig.getMessages().getPrefix() + "&#ff6e6eGracz &#FFF01F{PLAYER} &#ff6e6ezostał zabity przez &#39FF14{KILLER} ({HEALTH-K}❤)"
                        .replace("{PLAYER}", p.getDisplayName())
                        .replace("{KILLER}", killer.getDisplayName())
                        .replace("{HEALTH-K}", Math.round(killer.getHealth()) + "")
                        .replace("{HEALTH-P}", Math.round(p.getHealth()) + ""));
            } else {
                Api.sendDeathNotify(Main.pluginConfig.getMessages().getPrefix() + "&#FFF01F{PLAYER} &#ff6e6eumarł".replace("{PLAYER}", p.getDisplayName()));

            }
        }
    }
}
