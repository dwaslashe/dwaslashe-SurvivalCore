package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class GlowingCommand extends Command {
    public GlowingCommand() {
        super("glowing", "/glowing <on, off>", "");
        setPermission("core.command.glowing");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("on", "off"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length == 1) {
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("on")) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie włączyłeś &eglowing!");
                p.setGlowing(true);
            } else if (args[0].equalsIgnoreCase("off")) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cPomyślnie wyłączyłeś &eglowing!");
                p.setGlowing(false);
            }
        }
    }
}