package xyz.dwaslashe.survivalcore.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.BossBarApi;

import java.util.Arrays;
import java.util.List;

public class BcCommand extends Command {
    public BcCommand() {
        super("bc", "/bc <chat, title, bar, all, bossbar>", "", "broadcast");
        setPermission("bc.tools");
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("chat")) {
                Api.sendBroadcast("#E8E8E8&lOGŁOSZENIE &8>> &r" + StringUtils.join(args, " ", 1, args.length));
            } else if (args[0].equalsIgnoreCase("bar")) {
                String msg = "";
                for (int lenght = 1; lenght < args.length; lenght++)
                    msg = msg + args[lenght] + " ";
                for (Player all : Bukkit.getOnlinePlayers())
                    all.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Api.fixColor("&8>> &r" + msg + "&8<<")));
            } else if (args[0].equalsIgnoreCase("title")) {
                String msg = "";
                for (int lenght = 1; lenght < args.length; lenght++)
                    msg = msg + args[lenght] + " ";
                for (Player all : Bukkit.getOnlinePlayers())
                    all.sendTitle(Api.fixColor("#E8E8E8lOGŁOSZENIE"), Api.fixColor("&8>> &r" + msg + " &8<<"));
            } else if (args[0].equalsIgnoreCase("bossbar")) {
                BossBarApi.sendGlobalParse(args);
            } else if (args[0].equalsIgnoreCase("all")) {
                BossBarApi.sendGlobalParse(args);
                Api.sendBroadcast("#E8E8E8&lOGŁOSZENIE &8>> &r" + StringUtils.join(args, " ", 1, args.length));String msg = "";
                for (int lenght = 1; lenght < args.length; lenght++)
                    msg = msg + args[lenght] + " ";
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendTitle(Api.fixColor("#E8E8E8&lOGŁOSZENIE"), Api.fixColor("&8>> &r" + msg + " &8<<"));
                    all.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Api.fixColor("&8>> &r" + msg + "&8<<")));
                }
            } else wrongUsage();
        }
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if(args.length == 1) return Api.startsWith(Arrays.asList("chat", "title", "bar", "all", "bossbar"), args[0]);
        return null;
    }
}