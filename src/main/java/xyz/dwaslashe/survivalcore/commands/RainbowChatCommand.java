package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class RainbowChatCommand extends Command {
    public RainbowChatCommand() {
        super("rainbowchat", "/rainbowchat", "", "teczowyczat");
        setPermission("core.command.rainbowchat");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("core.chat.rainbow")) {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cPomyślnie wyłączyłeś pisanie na kolorowo!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission remove core.chat.rainbow");
        } else {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie włączyłeś pisanie na kolorowo!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.chat.rainbow");
        }
    }
}
