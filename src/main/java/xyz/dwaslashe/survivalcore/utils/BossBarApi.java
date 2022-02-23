package xyz.dwaslashe.survivalcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.model.impl.UserImpl;

public class BossBarApi {

    public static void sendGlobal(BarColor color, BarStyle style, int time, String message) {
        org.bukkit.boss.BossBar bar = Bukkit.createBossBar(message, color, style, new BarFlag[0]);
        Main.getPlugin().getUserCache().getOnlineUserMap().forEach((name, user) -> {
            UserImpl impl = (UserImpl) user;
            if(impl.isAutobar()) bar.addPlayer(impl.getPlayer());
        });
        bar.setProgress(1);
        Main.getPlugin().getServer().getScheduler().runTaskLater(Main.getPlugin(), bar::removeAll, time * 20);
        Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if (bar.getProgress() > 0.02) {
                    bar.setProgress(bar.getProgress() - 0.02);
                }
            }
        }, 0, 2);
    }

    public static void sendGlobalParse(String[] args) {
        StringBuilder message = new StringBuilder();
        BarColor color = null;
        for (int i = 1; i < args.length; i++) {
            if (!args[i].startsWith("color:") && !args[i].startsWith("c:")) {
                message.append(args[i]).append(" ");
            } else {
                color = parseBarColor(args[i].replaceAll("(c|color):", ""));
                sendGlobal(color, BarStyle.SOLID, 5, Api.fixColor(message.toString()));
                if (color == null) {
                    Api.sendLog("&cNie ma takiego koloru! [BossBar]");
                    return;
                }
            }
        }
    }

    private static BarColor parseBarColor(String message) {
        try {
            return BarColor.valueOf(message.toUpperCase());
        } catch (Exception var2) {
            return null;
        }
    }
}
