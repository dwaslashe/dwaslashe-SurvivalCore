package xyz.dwaslashe.survivalcore.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.List;

public class PokeBallCommand extends Command {
    public PokeBallCommand() {
        super("pokeballgive", "/pokeballgive <gracz>", "");
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
        } else if (args.length == 1) {
            Player p2 = Bukkit.getPlayer(args[0]);
            Api.giveOrDrop(p2, OthersListener.pokeball);
        } else sender.sendMessage(getUsage());
    }
}
