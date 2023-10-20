package xyz.dwaslashe.survivalcore.utils;

import me.neznamy.tab.api.chat.rgb.RGBUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.configs.PluginConfig;
import xyz.dwaslashe.survivalcore.enums.ColorEnums;
import xyz.dwaslashe.survivalcore.helpers.IconHelper;
import xyz.dwaslashe.survivalcore.objects.User;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Api {

    public static String fixColor(String message) {
        RGBUtils rgbUtils = RGBUtils.getInstance();
        return message == null ? "" : ChatColor.translateAlternateColorCodes('&', rgbUtils.convertToBukkitFormat(IconHelper.transformIcons(message, PluginConfig.IMAGES_CHAT), true))
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
        String[] lines = message.split("%newline%");
        for (String line : lines) {
            if (sender instanceof Player player) {
                player.sendMessage(fixColor(line));
            } else sender.sendMessage(fixColor(line));
        }
    }

    public static void sendMessage(CommandSender sender, Component component) {
        if(sender instanceof Player player) {
            User user = UserCache.getInstance().compute(player.getUniqueId());
            if (user.getChat() == 0) {
                player.sendMessage(component);
            }
        }
    }

    public static void sendCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void sendCommands(List<String> commands) {
        for (String command : commands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    public static void sendLog(String message) {
        Bukkit.getConsoleSender().sendMessage(fixColor(message));
    }

    public static void sendAbyssNotify(String message){
        for (Player all : Bukkit.getOnlinePlayers()) {
            User user = UserCache.getInstance().compute(all.getUniqueId());
            if (user.getAbyss() == 0) {
                sendMessage(all, message);
            }
        }
    }

    public static void sendDeathNotify(Player player, String message){
        User user = UserCache.getInstance().compute(player.getUniqueId());
        if (user.getDeaths() == 0) {
            sendMessage(player, message);
        }
    }

    public static void sendBroadcast(String message) {
        String fixedMessage = fixColor(message);
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(fixedMessage));
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

    public static void setTotalExperience(final Player player, final int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Experience is negative!");
        }
        player.setExp(0);
        player.setLevel(0);
        player.setTotalExperience(0);

        //This following code is technically redundant now, as bukkit now calulcates levels more or less correctly
        //At larger numbers however... player.getExp(3000), only seems to give 2999, putting the below calculations off.
        int amount = exp;
        while (amount > 0) {
            final int expToLevel = getExpAtLevel(player);
            amount -= expToLevel;
            if (amount >= 0) {
                // give until next level
                player.giveExp(expToLevel);
            } else {
                // give the rest
                amount += expToLevel;
                player.giveExp(amount);
                amount = 0;
            }
        }
    }

    private static int getExpAtLevel(final Player player) {
        return getExpAtLevel(player.getLevel());
    }

    public static int getExpAtLevel(final int level) {
        if (level <= 15) {
            return (2 * level) + 7;
        }
        if ((level >= 16) && (level <= 30)) {
            return (5 * level) - 38;
        }
        return (9 * level) - 158;

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

    public static void sendActionBar(Player player, String message) {
        ((CraftPlayer) player).getHandle().sendActionBarMessage(MiniMessage.miniMessage().deserialize(ColorEnums.translateAlternateColorCodes(message).replace(">> ", "» ").replace("<<", "«")));
    }

    public static void giveOrDrop(Player player, ItemStack itemStack) {
        if (itemStack != null) {
            player.getInventory().addItem(new ItemStack[]{itemStack}).values().forEach((i) -> {
                player.getWorld().dropItem(player.getLocation(), i);
            });
            player.updateInventory();
        }
    }

    public static double mapLongToDouble(long value, long min, long max) {
        if (value < min) {
            value = min;
        } else if (value > max) {
            value = max;
        }

        double scaledValue = (double)(value - min) / (max - min);

        return scaledValue;
    }

    public static boolean isNearby(Player player, Player nearbyPlayer, int maxDistance) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all == player) {
                continue;
            }
            if (all.getName().equalsIgnoreCase(nearbyPlayer.getName())) {
                double distance = player.getLocation().distance(all.getLocation());
                if (distance <= maxDistance) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNearby(Location location1, Location secondLocation, double maxDistance) {
        double distance = location1.distance(secondLocation);
        return distance <= maxDistance;
    }

    public static boolean isBoolean(String str) {
        str = str.toLowerCase().trim();
        return str.equals("true") || str.equals("false");
    }

    public static List<String> replaceInList(List<String> inputList, String target, String replacement) {
        for (int i = 0; i < inputList.size(); i++) {
            String current = inputList.get(i);
            inputList.set(i, current.replace(target, replacement));
        }
        return inputList;
    }
}
