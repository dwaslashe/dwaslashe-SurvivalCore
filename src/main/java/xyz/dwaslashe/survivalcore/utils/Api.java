package xyz.dwaslashe.survivalcore.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.neznamy.tab.api.chat.rgb.RGBUtils;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R2.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.configs.PluginConfig;
import xyz.dwaslashe.survivalcore.helpers.IconHelper;
import xyz.dwaslashe.survivalcore.objects.User;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        if(sender instanceof Player player){
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, fixColor(message)));
        } else sender.sendMessage(fixColor(message));
    }

    public static void sendMessage(CommandSender sender, Component component) {
        if(sender instanceof Player player) {
            User user = UserCache.getInstance().compute(player.getUniqueId());
            if (user.getChat() == 0) {
                Main.getPlugin().getAudience().sender(player).sendMessage(component);
            }
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
        IChatBaseComponent iChatBaseComponent = CraftChatMessage.fromString(fixColor(message))[0];
        ClientboundSystemChatPacket clientboundSystemChatPacket;
        try {
            Class<?> clazz = Class.forName(ClientboundSystemChatPacket.class.getName());
            Constructor<?> constructor = clazz.getConstructor(IChatBaseComponent.class, int.class);

            clientboundSystemChatPacket = (ClientboundSystemChatPacket) constructor.newInstance(iChatBaseComponent, 2);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            clientboundSystemChatPacket = new ClientboundSystemChatPacket(iChatBaseComponent, true);
        }
        ((CraftPlayer) player).getHandle().b.a(clientboundSystemChatPacket);
    }

    public static void giveOrDrop(Player player, ItemStack itemStack) {
        if (itemStack != null) {
            player.getInventory().addItem(new ItemStack[]{itemStack}).values().forEach((i) -> {
                player.getWorld().dropItem(player.getLocation(), i);
            });
            player.updateInventory();
        }
    }

}
