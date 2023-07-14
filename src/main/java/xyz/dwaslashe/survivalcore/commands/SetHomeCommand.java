package xyz.dwaslashe.survivalcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.parsers.LocationParser;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetHomeCommand extends Command {
    public SetHomeCommand() {
        super("sethome", "/sethome <nazwa>", "", "ustawdom");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("nazwa"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
        if (args.length >= 1) {
            User user = UserCache.getInstance().compute(player.getUniqueId());
            String nameHome = StringUtils.join(args, " ", 0, args.length);
            int countHomes = extractPhrases(user.getHomes()).size();

            if (nameHome.contains("&") && nameHome.contains("#")) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz stworzyć domu z znakiem &e& &ci &e#&c!");
                return;
            }
            if (countHomes >= maxHomes(player)) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz stworzyć więcej domów ponieważ twój limit na to nie pozwala! &e" + countHomes + "&c/&e" + maxHomes(player));
                return;
            }
            if (nameHome.length() > 16) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz stworzyć dom, który posiada więcej niż 16 cyfr w nazwie!");
                return;
            }
            if (user.getHomes().contains(nameHome)) {
                user.removeHomes(nameHome);
                user.addHomes(nameHome + "&" + new LocationParser().deserialize(player.getLocation()) + "#");
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zaktualizowałeś dom o nazwie &e" + nameHome);
                return;
            }

            user.addHomes(nameHome + "&" + new LocationParser().deserialize(player.getLocation()) + "#");
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie stworzyłeś nowy dom o nazwie &e" + nameHome);
        } else wrongUsage();
    }

    public int maxHomes(Player player) {
        int homes = 0;
        for (PermissionAttachmentInfo perms : player.getEffectivePermissions()) {
            if (perms.getPermission().startsWith("core.command.homes.")) homes = Math.max(homes, Integer.parseInt(perms.getPermission().split("homes.")[1]));
        }
        return homes;
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
}
