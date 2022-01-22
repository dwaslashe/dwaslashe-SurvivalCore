package xyz.dwaslashe.survivalcore.utils;

import me.neznamy.tab.api.chat.rgb.RGBUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Api {

    public static String fixColor(String message) {
        RGBUtils rgbUtils = RGBUtils.getInstance();
        return message == null ? "" : ChatColor.translateAlternateColorCodes('&', rgbUtils.convertToBukkitFormat(message, true))
                .replace(">>", "»")
                .replace("<<", "«");
    }

    public static List<String> fixColor(List<String> message) {
        return message.stream().map(Api::fixColor).collect(Collectors.toList());
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(fixColor(message));
    }

    public static void sendLog(String message) {
        Bukkit.getConsoleSender().sendMessage(fixColor(message));
    }

    public static void sendBroadcast(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> sendMessage(player, fixColor(message)));
    }

    public static int getPing(Player p) {
        String v = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        if (!p.getClass().getName().equals("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer")) {
            p = Bukkit.getPlayer(p.getUniqueId());
        }
        try {
            int ping = p.getPing();
            return ping;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isFloat(String arg) {
        try {
            Float.parseFloat(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInt(String arg) {
        try {
            Integer.parseInt(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<String> startsWith(List<String> subcommands, String start) {
        if (start != null && !start.equals("") && subcommands != null && !subcommands.isEmpty()) {
            ArrayList<String> startingStrings = new ArrayList<>();
            for (String subcommand : subcommands) {
                if (subcommand.regionMatches(true, 0, start, 0, start.length())) {
                    startingStrings.add(subcommand);
                }
            }

            return startingStrings;
        } else {
            return subcommands;
        }
    }

    public static boolean inventoryFull(Player player) {
        if (player.getInventory().contains(Material.AIR)) {
        } else {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cTwój ekwipunek jest pełny! Zwolnij mniejsce!");
        }
        return false;
    }

}
