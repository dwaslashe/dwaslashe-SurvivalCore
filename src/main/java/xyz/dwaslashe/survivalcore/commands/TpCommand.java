package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.DiscordHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TpCommand extends Command {

    public TpCommand() {
        super("tp", "/tp <x, gracz> <y, gracz> <z> <gracz>", "");
        setPermission("core.command.tp");
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Player secondPlayer;

        DiscordHelper discordHelper = new DiscordHelper("https://discord.com/api/webhooks/1171894824354467841/b5pqy9eszrbCrepGo1Br0lVe69ApdlpulIhpw6MbRc1bvRtVQ8oHhadoFwVITo3wLizC");

        if (args.length == 0) {
            wrongUsage();
            return;
        } else if (args.length == 1) {
            secondPlayer = Bukkit.getPlayer(args[0]);
            if (secondPlayer == null) {
                offlinePlayer();
                return;
            } else {
                Location loc = secondPlayer.getLocation();
                player.teleport(loc);
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aZostales przeteleportowany do &e" + secondPlayer.getName());
                discordHelper.setUsername(player.getName() + " (TELEPORT DO GRACZA)");
                discordHelper.setAvatarUrl("https://minotar.net/avatar/" + player.getName());
                discordHelper.setContent("Gracz **" + player.getName() + "** przeteleportował się do: **'" + secondPlayer.getName() + "'**, kordy gracza **X: " + secondPlayer.getLocation().getBlockX() + ", Y: " + secondPlayer.getLocation().getBlockY() + ", Z: " + secondPlayer.getLocation().getBlockX() + "**");
                try {
                    discordHelper.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (args.length == 2) {
            secondPlayer = Bukkit.getPlayer(args[0]);
            Player thirdPlayer = Bukkit.getPlayer(args[1]);
            if (secondPlayer != null && thirdPlayer != null) {
                Location loc = thirdPlayer.getLocation();
                secondPlayer.teleport(loc);
                Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefix() + "&aGracz &e" + thirdPlayer.getName() + " &aprzeteleportowal Ciebie do siebie!");
                Api.sendMessage(thirdPlayer, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeteleportowałeś do siebie gracza &e" + secondPlayer.getName());
                discordHelper.setUsername(player.getName() + " (TELEPORT GRACZA DO GRACZA)");
                discordHelper.setAvatarUrl("https://minotar.net/avatar/" + player.getName());
                discordHelper.setContent("Gracz **" + player.getName() + "** przeteleportował gracza: **'" + secondPlayer.getName() + "'** do **'" + thirdPlayer.getName() + "'**");
                try {
                    discordHelper.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                offlinePlayer();
                return;
            }
        } else if (args.length == 3) {
            if (Api.isInt(args[0])) {
                final double z = Double.parseDouble(args[args.length - 1]);
                final double y = Double.parseDouble(args[args.length - 2]);
                final double x = Double.parseDouble(args[args.length - 3]);
                Location loc = new Location(player.getWorld(), x, y, z);
                player.teleport(loc);
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zostałeś przeteleportowany na kordynaty!");
                discordHelper.setUsername(player.getName() + " (TELEPORT NA KORDY)");
                discordHelper.setAvatarUrl("https://minotar.net/avatar/" + player.getName());
                discordHelper.setContent("Gracz **" + player.getName() + "** przeteleportował się na kordy: **X: " + x + "**, **Y: " + y + "**, **Z: " + z + "**");
                try {
                    discordHelper.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else wrongUsage();
        } else if (args.length == 4) {
            secondPlayer = Bukkit.getPlayer(args[0]);
            if (secondPlayer == null) {
                offlinePlayer();
                return;
            } else {
                final double z = Double.parseDouble(args[args.length - 1]);
                final double y = Double.parseDouble(args[args.length - 2]);
                final double x = Double.parseDouble(args[args.length - 3]);
                Location loc = new Location(player.getWorld(), x, y, z);
                secondPlayer.teleport(loc);
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie gracz &e" + secondPlayer.getName() + " &azostał przeteleportwany na kordynaty!");
                Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefix() + "&aZostałeś przeteleportowany na kordynaty!");

                discordHelper.setUsername(player.getName() + " (TELEPORT GRACZA NA KORDY)");
                discordHelper.setAvatarUrl("https://minotar.net/avatar/" + player.getName());
                discordHelper.setContent("Gracz **" + player.getName() + "** przeteleportował gracza **'" + secondPlayer.getName() +  "'** na kordy: **X: " + x + "**, **Y: " + y + "**, **Z: " + z + "**");
                try {
                    discordHelper.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else wrongUsage();
    }


    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        if (args.length == 1) return Api.startsWith(Arrays.asList("x"), args[0]);
        if (args.length == 2) return Collections.singletonList("[players]");
        if (args.length == 2) return Api.startsWith(Arrays.asList("y"), args[0]);
        if (args.length == 3) return Collections.singletonList("[players]");
        if (args.length == 3) return Api.startsWith(Arrays.asList("z"), args[0]);
        if (args.length == 4) return Collections.singletonList("[players]");
        return null;
    }
}
