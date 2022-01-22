package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class TpaDenyCommand extends Command {
    public TpaDenyCommand() {
        super("tpadeny", "/tpadeny", "");
        setOnlyPlayer(true);
    }
    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player p = (Player)s;
        if (TpaCommand.currentRequest.containsKey(p.getName())) {
            Player p2 = Bukkit.getServer().getPlayer((String) TpaCommand.currentRequest.get(p.getName()));
            TpaCommand.currentRequest.remove(p.getName());
            Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&cProśba o teleport została odrzucona");
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cProśba o teleport została odrzucona");
        } else {
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz zadnej prosby o &eteleportacje");
        }
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }
}
