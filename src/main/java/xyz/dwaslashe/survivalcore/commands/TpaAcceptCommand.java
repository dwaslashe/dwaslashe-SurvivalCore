package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.TeleportManager;
import xyz.dwaslashe.survivalcore.utils.Api;
import java.util.List;

public class TpaAcceptCommand extends Command {
    public TpaAcceptCommand() {
        super("tpaccept", "/tpaccept", "");
        setOnlyPlayer(true);
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (TpaCommand.currentRequest.containsKey(p.getName())) {
            final Player p2 = Bukkit.getServer().getPlayer((String) TpaCommand.currentRequest.get(p.getName()));
            TpaCommand.currentRequest.remove(p.getName());
            if (p2 != null) {
                TeleportManager.teleport(p2, 5, p.getLocation());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zakecptowałeś teleportacje gracza &e" + p2.getDisplayName());
            } else {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz prośby o teleportacje!");
            }
        } else {
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz prośby o teleportacje!");
        }
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }
}