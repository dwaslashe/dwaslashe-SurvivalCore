package xyz.dwaslashe.survivalcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.List;

public class DeleteHomeCommand extends Command {
    public DeleteHomeCommand() {
        super("delhome", "/delhome <nazwa>", "", "usundom", "deletehome");
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
            if (user.getHomes().contains(nameHome)) {
                user.removeHomes(nameHome);
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie usuniętego dom &#fcb419" + nameHome);
            } else {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz domu o nazwie &#fcb419" + nameHome);
            }
        } else wrongUsage();
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