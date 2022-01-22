package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GmCommand extends Command {
    public GmCommand() {
        super("gm", "/gm <0, 1, 2, 3> <nick>", "", "gamemode");
        setPermission("core.command.gamemode");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 2) return Collections.singletonList("[players]");
        else if (args.length == 1) return Api.startsWith(Arrays.asList("0", "1", "2", "3"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("0")) {
                p.setGameMode(GameMode.SURVIVAL);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZmieniłeś tryb gry na &esurvival");
            } else if (args[0].equalsIgnoreCase("1")) {
                p.setGameMode(GameMode.CREATIVE);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZmieniłeś tryb gry na &ecreative");
            } else if (args[0].equalsIgnoreCase("2")) {
                p.setGameMode(GameMode.ADVENTURE);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZmieniłeś tryb gry na &eadventure");
            } else if (args[0].equalsIgnoreCase("3")) {
                p.setGameMode(GameMode.SPECTATOR);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZmieniłeś tryb gry na &espectator");
            }
        } else if (args.length == 2) {
            Player p2 = Bukkit.getPlayer(args[1]);
            if (p2 == null) {
                offlinePlayer();
                return;
            } else if (args[0].equalsIgnoreCase("0")) {
                p2.setGameMode(GameMode.SURVIVAL);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aTwoj tryb gry zostal zmieniony na &esurvival &przez &e" + p.getName());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZmieniłeś tryb gry na &esurvival &adla &e" + p2.getName());
            } else if (args[0].equalsIgnoreCase("1")) {
                p2.setGameMode(GameMode.CREATIVE);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aTwoj tryb gry zostal zmieniony na &ecreative &przez &e" + p.getName());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZmieniłeś tryb gry na &ecreative &adla &e" + p2.getName());
            } else if (args[0].equalsIgnoreCase("2")) {
                p2.setGameMode(GameMode.ADVENTURE);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aTwoj tryb gry zostal zmieniony na &eadventure &przez &e" + p.getName());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZmieniłeś tryb gry na &eadventure &adla &e" + p2.getName());
            } else if (args[0].equalsIgnoreCase("3")) {
                p2.setGameMode(GameMode.SPECTATOR);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aTwoj tryb gry zostal zmieniony na &espectator &przez &e" + p.getName());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aZmieniłeś tryb gry na &espectator &adla &e" + p2.getName());
            }
        } else wrongUsage();
    }
}

