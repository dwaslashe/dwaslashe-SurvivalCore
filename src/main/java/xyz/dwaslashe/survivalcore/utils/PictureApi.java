package xyz.dwaslashe.survivalcore.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.listeners.PlayerJoinListener;

public class PictureApi {
    private final Main plugin;
    public PictureApi(Main plugin) {
        this.plugin = plugin;
    }
    private URL newURL(String player_uuid, String player_name) {
        String url = ("https://minepic.org/avatar/8/%pname%").replace("%uuid%" , player_uuid).replace("%pname%", player_name);

        try {
            return new URL(url);
        } catch (Exception e) {
            plugin.getLogger().warning("Could not read url from file.");
            return null;
        }
    }

    private BufferedImage getImage(Player player) {
        URL head_image = newURL(player.getUniqueId().toString(), player.getName());

        // URL Formatted correctly.
        if (head_image != null) {
            try {
                //User-Agent is needed for HTTP requests
                HttpURLConnection connection = (HttpURLConnection) head_image.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                return ImageIO.read(connection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                plugin.getLogger().warning("error_retrieving_avatar");
            }
        }

        String FALLBACK_PATH = plugin.getDataFolder() + File.separator + "fallback.png";
        File image = new File(FALLBACK_PATH);

        try {
            return ImageIO.read(image);
        } catch (Exception e) {
            plugin.getLogger().warning("error_fallback_img");
            return null;
        }
    }

    public ImageMessage createPictureMessage(Player player, List<String> messages) {
        BufferedImage image = getImage(player);

        if (image == null) return null;

        messages.replaceAll((message) -> addPlaceholders(message, player));

        return PlayerJoinListener.getMessage(Api.fixColor(messages), image);
    }

    private String addPlaceholders(String msg, Player player) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);

        msg = msg.replace("%pname%", player.getName());
        msg = msg.replace("%uuid%", player.getUniqueId().toString());
        msg = msg.replace("%online%", String.valueOf(plugin.getServer().getOnlinePlayers().size()));
        msg = msg.replace("%max%", String.valueOf(plugin.getServer().getMaxPlayers()));
        msg = msg.replace("%displayname%", player.getDisplayName());
        msg = PlaceholderAPI.setPlaceholders(player, msg);

        return msg;
    }

    public void clearChat(Player player) {
        for (int i = 0; i < 20; i++) {
            player.sendMessage("");
        }
    }

}
