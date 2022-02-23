package xyz.dwaslashe.survivalcore.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.neznamy.tab.api.chat.rgb.RGBUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.model.impl.UserImpl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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

    public static void sendPlayerToServer(Player player, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(Main.getPlugin(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        }
        catch (Exception e) {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz się dołączyć do serwera &e" + server);
        }
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, fixColor(message)));
    }

    public static void sendLog(String message) {
        Bukkit.getConsoleSender().sendMessage(fixColor(message));
    }

    public static void sendAbyssNotify(String message){
        Main.getPlugin().getUserCache().getOnlineUserMap().forEach((name, user) -> {
            if(user.abyss()) sendMessage(((UserImpl)user).getPlayer(), message);
        });
    }

    public static void sendBroadcast(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> sendMessage(player, fixColor(message)));
    }

    public static int getPing(Player p) {
        return p.getPing();
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
