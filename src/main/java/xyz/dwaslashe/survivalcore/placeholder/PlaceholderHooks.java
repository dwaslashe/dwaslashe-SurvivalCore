package xyz.dwaslashe.survivalcore.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.listeners.PlayerQuitListener;
import xyz.dwaslashe.survivalcore.listeners.RegionListener;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.text.DecimalFormat;

public class PlaceholderHooks extends PlaceholderExpansion {
    private static final DecimalFormat format = new DecimalFormat("#,###.##");

    public String getAuthor() {
        return "dwaslashe";
    }

    public String getIdentifier() {
        return "survivalcore";
    }

    public String getVersion() {
        return "1.0";
    }

    public boolean persist() {
        return true;
    }
    
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equals("afk")) {
            if (RegionListener.afk.contains(player.getUniqueId())) {
                return String.valueOf(Api.fixColor(" &#F23D07ᴀꜰᴋ"));
            } else return "";
        }
        if (params.equals("afkchat")) {
            if (RegionListener.afk.contains(player.getUniqueId())) {
                return "<hover:show_text:\" <#f3501f>Strefa <#F23D07>AFK\n\n <#E7E7E7>Gracz aktualnie przebywa w strefie afk.\n <#E7E7E7>Kliknij, aby przeteleportować się do\n <#E7E7E7>strefy afk! Każda minuta w\n <#E7E7E7>strefie to <#FFF88F>2 <#FFC42E>$<#E7E7E7> więcej!\n\"><click:suggest_command:/warp afk><#F23D07>ᴀꜰᴋ </click></hover>";
            } else return "";
        }
        if (params.equals("icon_01")) {
            return String.valueOf(Api.fixColor("icon%01"));
        }
        if (params.equals("icon_02")) {
            return String.valueOf(Api.fixColor("icon%02"));
        }
        if (params.equals("icon_03")) {
            return String.valueOf(Api.fixColor("icon%03"));
        }
        if (params.equals("icon_04")) {
            return String.valueOf(Api.fixColor("icon%04"));
        }
        if (params.equals("icon_05")) {
            return String.valueOf(Api.fixColor("icon%05"));
        }
        if (params.equals("icon_06")) {
            return String.valueOf(Api.fixColor("icon%06"));
        }
        if (params.equals("icon_07")) {
            return String.valueOf(Api.fixColor("icon%07"));
        }
        if (params.equals("money")) {
            return String.valueOf(format.format(Main.getVaultEconomy().getBalance(player)));
        }
        return null;
    }
}
