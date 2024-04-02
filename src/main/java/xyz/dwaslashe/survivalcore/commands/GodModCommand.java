package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;
public class GodModCommand extends Command {
    public GodModCommand() {
        super("godmod", "/godmod", "");
        setPermission("core.command.godmod");
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
            if (p.isInvulnerable()) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Pomyślnie wyłączyłeś &#fcb419godmod!");
                p.setInvulnerable(false);
            } else {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie włączyłeś &#fcb419godmod!");
                p.setInvulnerable(true);
            }
        } else wrongUsage();
    }
}
