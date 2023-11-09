package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class FBICommand extends Command {
    public FBICommand() {
        super("fbi", "/fbi", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aWszystkie informacje o działaniu FBI: &#4680ebchttps://wiki.wywrotkamc.pl/pl/survivaldzialki/fbi");
    }
}
