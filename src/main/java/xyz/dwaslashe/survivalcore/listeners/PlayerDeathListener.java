package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();
        event.setDeathMessage(null);
        if (Main.pluginConfig.getEvents().isDeathmessage()) {
            if (killer instanceof Player) {
                player.sendTitle(Api.fixColor("#fc2003&lUMARŁEŚ"), Api.fixColor("&8>> &cUmarłeś przez &e" + killer.getName() + " &8<<"));
                if (killer.getName().equals(player.getName())) return;
                killer.sendTitle(Api.fixColor("#85fc23&lZABIŁEŚ"), Api.fixColor("&8>> &aZabiłeś gracza &e" + player.getName() + " &8<<"));
            } else {
                player.sendTitle(Api.fixColor("#fc2003&lUMARŁEŚ"), Api.fixColor("&8>> &cUmarłeś. &8<<"));
            }

            //for (Player all : Bukkit.getOnlinePlayers()) {
            //    if (e.getEntity().getKiller() instanceof Player) {
            //        Api.sendDeathNotify(all, Main.pluginConfig.getMessages().getPrefix() + "&#ff6e6eGracz &#FFF01F{PLAYER} &#ff6e6ezostał zabity przez &#39FF14{KILLER} ({HEALTH-K}❤)"
            //                .replace("{PLAYER}", p.getDisplayName())
            //                .replace("{KILLER}", killer.getDisplayName())
            //                .replace("{HEALTH-K}", Math.round(killer.getHealth()) + "")
            //                .replace("{HEALTH-P}", Math.round(p.getHealth()) + ""));
            //    } else {
            //        Api.sendDeathNotify(all, Main.pluginConfig.getMessages().getPrefix() + "&#FFF01F{PLAYER} &#ff6e6eumarł".replace("{PLAYER}", p.getDisplayName()));
            //    }
            //}
        }
    }
}
