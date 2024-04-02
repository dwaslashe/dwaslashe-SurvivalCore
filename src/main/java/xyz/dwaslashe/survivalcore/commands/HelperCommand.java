package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.DiscordHelper;
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HelperCommand extends Command implements Listener {
    public HelperCommand() {
        super("helper", "/helper <invsee, tp> <nick>", "");
        setPermission("core.command.helper");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("invsee", "tp"), args[0]);
        else if (args.length == 2) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        DiscordHelper discordHelper = new DiscordHelper("https://discord.com/api/webhooks/1173006612399669388/Lga1sVAKU_RTpdeFNhgGnE5RBy9pA_nYOR1xC0N-9BVs7FJBX-rpDUV16tL2osMQs7yp");

        Player player = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
        } else if (args[0].equalsIgnoreCase("invsee")) {
            if (args.length == 2) {
                Player secondPlayer = Bukkit.getPlayer(args[1]);
                if (secondPlayer == null) {
                    offlinePlayer();
                    return;
                }
                OthersListener.cancel.add(player);
                player.openInventory(secondPlayer.getInventory());

                discordHelper.setUsername(player.getName() + " (INVSEE)");
                discordHelper.setAvatarUrl("https://minotar.net/avatar/" + player.getName());
                discordHelper.setContent("Gracz **" + player.getName() + "** otworzył ekwipunek gracza: **'" + secondPlayer.getName() + "'**");
                try {
                    discordHelper.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Otworzyłeś ekwipunek gracza &#fcb419" + secondPlayer.getName());
            }
        } else if (args[0].equalsIgnoreCase("tp")) {
            if (args.length == 2) {
                Player secondPlayer = Bukkit.getPlayer(args[1]);
                if (secondPlayer == null) {
                    offlinePlayer();
                    return;
                }
                player.teleport(secondPlayer);

                discordHelper.setUsername(player.getName() + " (TELEPORT DO GRACZA)");
                discordHelper.setAvatarUrl("https://minotar.net/avatar/" + player.getName());
                discordHelper.setContent("Gracz **" + player.getName() + "** przeteleportował się do: **'" + secondPlayer.getName() + "'**, kordy gracza **X: " + secondPlayer.getLocation().getBlockX() + ", Y: " + secondPlayer.getLocation().getBlockY() + ", Z: " + secondPlayer.getLocation().getBlockX() + "**");
                try {
                    discordHelper.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Zostałeś przeteleportowany do &#fcb419" + secondPlayer.getName());
            }
        }
    }
}