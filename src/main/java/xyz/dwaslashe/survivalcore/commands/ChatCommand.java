package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class ChatCommand extends Command {
    public ChatCommand() {
        super("chat", "/chat <on, off, clear>", "");
        setPermission("core.command.chat");
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("on")) {
                Api.sendBroadcast(Main.pluginConfig.getChat().getOn());
                Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wyłączyłeś czat");
            } else if (args[0].equalsIgnoreCase("off")) {
                Api.sendBroadcast(Main.pluginConfig.getChat().getOff());
                Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wyłączyłeś czat");
            } else if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("cc") || args[0].equalsIgnoreCase("c")) {
                Bukkit.getOnlinePlayers().forEach(ChatCommand::clear);
                Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wyczyściłeś czat");
                Api.sendBroadcast(Main.pluginConfig.getChat().getClear());
            }
        }
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if(args.length == 1) return Api.startsWith(Arrays.asList("on", "off", "clear"), args[0]);
        return null;
    }
    private static void clear(Player player) {
        for (int i = 0; i < 150; ++i) {
            player.sendMessage("");
        }
    }
}
