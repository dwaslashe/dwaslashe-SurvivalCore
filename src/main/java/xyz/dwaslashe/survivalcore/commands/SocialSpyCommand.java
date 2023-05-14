package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.List;

public class SocialSpyCommand extends Command {

    protected static final List<Object> list = new ArrayList<>();
    public static List<Object> getList() {
        return list;
    }

    public SocialSpyCommand() {
        super("socialspy", "/socialspy", "");
        setPermission("core.command.socialspy");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(list.contains(p.getName()))
            list.remove(p.getName());
        else list.add(p.getName());
        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aSocialSpy zostało " + (list.contains(p.getName()) ? "&awłączone" : "&cwyłączone"));
    }
}