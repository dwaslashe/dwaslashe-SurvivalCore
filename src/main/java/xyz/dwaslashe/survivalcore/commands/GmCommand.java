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
            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                p.setGameMode(GameMode.SURVIVAL);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zmieniłeś tryb gry na &#fcb419survival");
            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                p.setGameMode(GameMode.CREATIVE);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zmieniłeś tryb gry na &#fcb419creative");
            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                p.setGameMode(GameMode.ADVENTURE);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zmieniłeś tryb gry na &#fcb419adventure");
            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spec") || args[0].equalsIgnoreCase("spectator")) {
                p.setGameMode(GameMode.SPECTATOR);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zmieniłeś tryb gry na &#fcb419spectator");
            }
        } else if (args.length == 2) {
            Player p2 = Bukkit.getPlayer(args[1]);
            if (p2 == null) {
                offlinePlayer();
                return;
            } else if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                p2.setGameMode(GameMode.SURVIVAL);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twoj tryb gry zostal zmieniony na &#fcb419survival &#4cf739przez &#fcb419" + p.getName());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zmieniłeś tryb gry na &#fcb419survival &#4cf739dla &#fcb419" + p2.getName());
            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                p2.setGameMode(GameMode.CREATIVE);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twoj tryb gry zostal zmieniony na &#fcb419creative &#4cf739przez &#fcb419" + p.getName());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zmieniłeś tryb gry na &#fcb419creative &#4cf739dla &#fcb419" + p2.getName());
            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                p2.setGameMode(GameMode.ADVENTURE);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twoj tryb gry zostal zmieniony na &#fcb419adventure &#4cf739przez &#fcb419" + p.getName());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zmieniłeś tryb gry na &#fcb419adventure &#4cf739dla &#fcb419" + p2.getName());
            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spec") || args[0].equalsIgnoreCase("spectator")) {
                p2.setGameMode(GameMode.SPECTATOR);
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twoj tryb gry zostal zmieniony na &#fcb419spectator &#4cf739przez &#fcb419" + p.getName());
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zmieniłeś tryb gry na &#fcb419spectator &#4cf739dla &#fcb419" + p2.getName());
            }
        } else wrongUsage();
    }
}

