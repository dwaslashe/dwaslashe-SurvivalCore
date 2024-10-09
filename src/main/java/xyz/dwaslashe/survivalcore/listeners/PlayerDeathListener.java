package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.utils.Api;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();
        event.setDeathMessage(null);
        if (Main.pluginConfig.getEvents().isDeathMessage()) {
            if (killer instanceof Player) {
                player.sendTitle(Api.fixColor("#fc2003&lUMARŁEŚ"), Api.fixColor("&8>> &#fc2419Umarłeś przez &#fcb419" + killer.getName() + " &8<<"));
                if (killer.getName().equals(player.getName())) return;
                killer.sendTitle(Api.fixColor("#85fc23&lZABIŁEŚ"), Api.fixColor("&8>> &#4cf739Zabiłeś gracza &#fcb419" + player.getName() + " &8<<"));
            } else {
                player.sendTitle(Api.fixColor("#fc2003&lUMARŁEŚ"), Api.fixColor("&8>> &#fc2419Umarłeś. &8<<"));
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

        UserCache.getInstance().compute(killer.getUniqueId(), User -> {
            if (User.getKillEffect() == 14) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-StoneCrumble-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 13) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-SpectralFade-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 12) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-SoundWaveDisperse-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 11) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-SandDissolve-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 10) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-RustyDecay-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 9) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-Photosynthesis-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 8) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-HolyAbsorption-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 7) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-HologramFlicker-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 6) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-IceShatter-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 5) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-GlassShatter-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 4) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-Disintegration-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 3) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-DarkMatter-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 2) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-BubblyBubble-Cast-MainHand " + player.getName());
            } else if (User.getKillEffect() == 1) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-AshesToAshes-Cast-MainHand " + player.getName());
            }
        });

        //VOLUME 1

        //if (killer.hasPermission("core.killeffect.DarkMatter")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-DarkMatter-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.BubblyBubble")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-BubblyBubble-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.Disintegration")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-Disintegration-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.AshesToAshes")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-AshesToAshes-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.GlassShatter")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-GlassShatter-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.IceShatter")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-IceShatter-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.SandDissolve")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v1-SandDissolve-Cast-MainHand " + player.getName());
        //}

        //VOLUME 2

        //if (killer.hasPermission("core.killeffect.HologramFlicker")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-HologramFlicker-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.HolyAbsorption")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-HolyAbsorption-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.Photosynthesis")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-Photosynthesis-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.RustyDecay")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-RustyDecay-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.StoneCrumble")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-StoneCrumble-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.VoidCollapse")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-VoidCollapse-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.SpectralFade")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-SpectralFade-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.SoundWaveDisperse")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-SoundWaveDisperse-Cast-MainHand " + player.getName());
        //}
        //if (killer.hasPermission("core.killeffect.WaterEvaporation")) {
        //    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "goop mythicmobs runSkillAs KFX_v2-WaterEvaporation-Cast-MainHand " + player.getName());
        //}
    }
}
