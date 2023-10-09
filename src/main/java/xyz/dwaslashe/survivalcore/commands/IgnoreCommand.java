package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.*;

public class IgnoreCommand extends Command {
    public IgnoreCommand() {
        super("ignore", "/ignore <all, nick>", "", "ignoruj", "zablokuj", "przestanignorowac", "odblokuj");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("[players]");
        } else if (args.length == 1) {
            return Api.startsWith(Arrays.asList("all"), args[0]);
        }
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length == 1) {
            Player secondPlayer = Bukkit.getPlayer(args[0]);
            User userPlayer = UserCache.getInstance().compute(player.getUniqueId());

            if (args[0].equalsIgnoreCase("all")) {
                if (userPlayer.getIgnoreAllPlayers() == 0) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wyciszono wszystkich graczy w prywatnych wiadomościach!");
                    userPlayer.setIgnoreAllPlayers(1);
                } else {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie odciszono wszystkich graczy w prywatnych wiadomściach!");
                    userPlayer.setIgnoreAllPlayers(0);
                }
            }

            if (secondPlayer == null) {
                offlinePlayer();
                return;
            }

            if (MsgCommand.getInputPlayer(secondPlayer.getName(), userPlayer.getIgnorePlayers())) {
                userPlayer.setIgnorePlayers(removePlayer(secondPlayer.getName(), userPlayer.getIgnorePlayers()));
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie odciszono gracza &e" + secondPlayer.getName());
            } else {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wyciszono gracza &e" + secondPlayer.getName());
                userPlayer.addIgnorePlayers(secondPlayer.getName() + "&");
            }
        }
    }

    public static String removePlayer(String playerToRemove, String input) {
        String[] players = input.split("&");

        StringBuilder result = new StringBuilder();
        boolean firstPlayer = true;

        for (String player : players) {
            if (!player.equals(playerToRemove)) {
                if (!firstPlayer) {
                    result.append("&");
                }
                result.append(player);
                firstPlayer = false;
            }
        }

        return result.toString();
    }
}
