package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class WbCommand extends Command {
    public WbCommand() {
        super("wb", "/craft", "", "craft");
        setPermission("core.command.wb");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length >= 0) {
            p.openWorkbench((Location) null, true);
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyśłnie otworzyłeś &ecrafting");
        }
    }
}