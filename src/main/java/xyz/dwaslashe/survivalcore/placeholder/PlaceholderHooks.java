package xyz.dwaslashe.survivalcore.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import xyz.dwaslashe.survivalcore.Main;
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
