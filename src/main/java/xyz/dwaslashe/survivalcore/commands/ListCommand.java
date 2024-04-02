package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;

import java.util.List;

public class ListCommand extends Command {
    public ListCommand() {
        super("list", "/list", "", "online");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            VanishCommand.VanishObject vanishObject = VanishCommand.VanishObject.get(onlinePlayer.getName());

            if (vanishObject.isEnable()) continue;

            stringBuilder.append("&r, ").append(ChatApi.getPrefix(onlinePlayer) + onlinePlayer.getDisplayName());
        }
        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Graczy: &f" + Bukkit.getOnlinePlayers().size() + "&8/&7" + Bukkit.getMaxPlayers() + " &8(&7" + stringBuilder.toString().replaceFirst(", ", "") + "&8)");
    }
}