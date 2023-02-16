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
        super("glowing", "/glowing", "");
        setPermission("core.command.glowing");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Player p = (Player) sender;
            if (p.isGlowing()) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cPomyślnie wyłączyłeś &eglowing!");
                p.setGlowing(false);
            } else {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie włączyłeś &eglowing!");
                p.setGlowing(true);
            }
        } else wrongUsage();
    }
}