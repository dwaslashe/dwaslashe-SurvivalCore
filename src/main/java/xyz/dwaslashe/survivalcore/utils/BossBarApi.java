package xyz.dwaslashe.survivalcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.objects.User;

public class BossBarApi {

    public static void sendGlobal(Player player, BarColor color, BarStyle style, int time, String message) {
        org.bukkit.boss.BossBar bar = Bukkit.createBossBar(message, color, style, new BarFlag[0]);

        User user = UserCache.getInstance().compute(player.getUniqueId());
        if (user.getAutoBossBar() == 0) {
            bar.addPlayer(player);
        }

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

    public static void sendGlobal2(Player player, BarColor color, BarStyle style, int time, String message) {
        org.bukkit.boss.BossBar bar = Bukkit.createBossBar(message, color, style, new BarFlag[0]);
        bar.setProgress(0);

        User user = UserCache.getInstance().compute(player.getUniqueId());
        if (user.getAutoBossBar() == 0) {
            bar.addPlayer(player);
        }

        Main.getPlugin().getServer().getScheduler().runTaskLater(Main.getPlugin(), bar::removeAll, time * 20);
        Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if (bar.getProgress() < 0.98) {
                    bar.setProgress(bar.getProgress() + 0.02);
                }
            }
        }, 0, 2);
    }

    public static void sendGlobal3(Player player, BarColor color, BarStyle style, int time, String message) {
        org.bukkit.boss.BossBar bar = Bukkit.createBossBar(message, color, style, new BarFlag[0]);
        bar.setProgress(0);

        User user = UserCache.getInstance().compute(player.getUniqueId());
        if (user.getAutoBossBar() == 0) {
            bar.addPlayer(player);
        }

        Main.getPlugin().getServer().getScheduler().runTaskLater(Main.getPlugin(), bar::removeAll, time * 39);
        Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
            double progress = 0.0;
            double progress2 = 0.0;
            @Override
            public void run() {
                if(progress < 0.98) {
                    progress = progress + 0.02;
                } else if (progress > 0.02) {
                    progress2 = progress2 - 0.02;
                    if (progress2 == -0.98) {
                        return;
                    }
                }
                if (progress + progress2 <= 0.01) {
                    return;
                } else bar.setProgress(progress + progress2);
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
                for (Player all : Bukkit.getOnlinePlayers()) {
                    sendGlobal(all, color, BarStyle.SOLID, 5, Api.fixColor(message.toString()));
                }
                if (color == null) {
                    Api.sendLog("&cNie ma takiego koloru! [BossBar]");
                    return;
                }
            }
        }
    }

    public static void sendGlobalParse2(String[] args) {
        StringBuilder message = new StringBuilder();
        BarColor color = null;
        for (int i = 1; i < args.length; i++) {
            if (!args[i].startsWith("color:") && !args[i].startsWith("c:")) {
                message.append(args[i]).append(" ");
            } else {
                color = parseBarColor(args[i].replaceAll("(c|color):", ""));
                for (Player all : Bukkit.getOnlinePlayers()) {
                    sendGlobal2(all, color, BarStyle.SOLID, 5, Api.fixColor(message.toString()));
                }
                if (color == null) {
                    Api.sendLog("&cNie ma takiego koloru! [BossBar]");
                    return;
                }
            }
        }
    }

    public static void sendGlobalParse3(String[] args) {
        StringBuilder message = new StringBuilder();
        BarColor color = null;
        for (int i = 1; i < args.length; i++) {
            if (!args[i].startsWith("color:") && !args[i].startsWith("c:")) {
                message.append(args[i]).append(" ");
            } else {
                color = parseBarColor(args[i].replaceAll("(c|color):", ""));
                for (Player all : Bukkit.getOnlinePlayers()) {
                    sendGlobal3(all, color, BarStyle.SOLID, 5, Api.fixColor(message.toString()));
                }
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
