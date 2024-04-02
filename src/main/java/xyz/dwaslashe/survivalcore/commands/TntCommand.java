package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class TntCommand extends Command {
    public TntCommand() {
        super("tnt", "/tnt", "");
        setPermission("core.command.tnt");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zapalaiłeś &#fcb419tnt");
        player.getWorld().spawn(player.getTargetBlock(null, 50).getLocation().add(0.0D, 1.0D, 0.0D), TNTPrimed.class);
    }
}