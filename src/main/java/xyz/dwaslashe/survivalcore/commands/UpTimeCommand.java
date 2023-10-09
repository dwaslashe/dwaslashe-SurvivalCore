package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.lang.management.ManagementFactory;
import java.util.List;

public class UpTimeCommand extends Command {
    public UpTimeCommand() {
        super("uptime", "/uptime", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aUpTime &#ffd56c" + TimerApi.getDurationBreakdownShort(ManagementFactory.getRuntimeMXBean().getUptime()) + " &#ffc942⌚");
    }
}
