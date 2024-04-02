package xyz.dwaslashe.survivalcore.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.List;

public class PokeBallCommand extends Command {
    public PokeBallCommand() {
        super("pokeballgive", "/pokeballgive <gracz> <amount>", "");
        setPermission("core.command.pokeballgive");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length >= 2) {
            Player secondPlayer = Bukkit.getPlayer(args[0]);
            if (secondPlayer != null) {
                if (Api.isInt(args[1])) {
                    OthersListener.pokeBall.setAmount(Integer.valueOf(args[1]));
                    Api.giveOrDrop(secondPlayer, OthersListener.pokeBall);
                    Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie dałeś &#fcb419" + args[1] + "x &#4cf739pokeball graczowi &#fcb419" + secondPlayer.getName());
                } else wrongUsage();
            } else offlinePlayer();
        } else if (args.length == 1) {
            Player secondPlayer = Bukkit.getPlayer(args[0]);
            if (secondPlayer != null) {
                OthersListener.pokeBall.setAmount(Integer.valueOf(args[1]));
                Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie dałeś &#fcb4191x &#4cf739pokeball graczowi &#fcb419" + secondPlayer.getName());
                Api.giveOrDrop(secondPlayer, OthersListener.pokeBall);
            } else offlinePlayer();
        } else wrongUsage();
    }
}
