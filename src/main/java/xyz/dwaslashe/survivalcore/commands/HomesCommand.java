package xyz.dwaslashe.survivalcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.TeleportManager;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.parsers.LocationParser;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.List;

public class HomesCommand extends Command {
    public HomesCommand() {
        super("home", "/home <nazwa>", "", "homes", "dom", "domy");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        User user = UserCache.getInstance().compute(player.getUniqueId());
        if (args.length == 1) return Api.startsWith(new ArrayList<>(extractPhrases(user.getHomes())), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
        if (args.length >= 1) {
            User user = UserCache.getInstance().compute(player.getUniqueId());
            String nameHome = StringUtils.join(args, " ", 0, args.length);
            if (containsPhrase(user.getHomes(), nameHome)) {
                if (player.hasPermission("core.command.admin")) {
                    player.teleport(new LocationParser().serialize(extractLocationsFromHome(user.getHomes(), nameHome)));
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowano do domu &e" + nameHome);
                } else {
                    TeleportManager.teleport(player, 5, new LocationParser().serialize(extractLocationsFromHome(user.getHomes(), nameHome)));
                }
            } else {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz takiego domu o nazwie &e" + nameHome);
            }
        } else wrongUsage();
    }

    public static String extractLocationsFromHome(String home, String phrase) {
        String[] sections = home.split("#");
        for (String section : sections) {
            if (section.startsWith(phrase + "&")) {
                int startIndex = section.indexOf("&") + 1;
                return section.substring(startIndex);
            }
        }
        return null;
    }

    public static List<String> extractPhrases(String home) {
        List<String> phrases = new ArrayList<>();

        String[] sections = home.split("#");
        for (String section : sections) {
            int ampersandIndex = section.indexOf("&");
            if (ampersandIndex != -1) {
                String phrase = section.substring(0, ampersandIndex);
                phrases.add(phrase);
            }
        }

        return phrases;
    }

    public boolean containsPhrase(String home, String phrase) {
        String[] sections = home.split("#");
        for (String section : sections) {
            if (section.startsWith(phrase + "&")) {
                return true;
            }
        }
        return false;
    }

}