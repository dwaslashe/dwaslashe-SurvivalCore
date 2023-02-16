package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.Abyss;
import xyz.dwaslashe.survivalcore.tasks.AbyssTask;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class AbyssCommand extends Command {
    public AbyssCommand() {
        super("otchlan", "/otchlan", "", "otchłań");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if(!AbyssTask.isOpened()){
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cOtchłań jest zamknięta");
            return;
        }
        if(AbyssTask.abyssList.isEmpty()){
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cOtchłań jest pusta");
            return;
        }
        Player p = (Player) sender;
        Abyss.get(0).open(p);
    }

}
