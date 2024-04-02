package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.List;

public class EcCommand extends Command {
    public EcCommand() {
        super("ec", "/ec <nick>", "", "enderchest");
        setPermission("core.command.ec");
        setOnlyPlayer(true);
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player player = (Player) s;
        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie otworzyłeś swój &#fcb419enderchest");
        } else if (args.length == 1) {
            if (!player.hasPermission("core.command.admin")) {
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &#fc2419Nie posiadasz uprawnien &8(&#fcb419core.command.admin&8) &8<<"));
                return;
            }
            Player secondPlayer = Bukkit.getPlayer(args[0]);
            if (secondPlayer == null) {
                offlinePlayer();
                return;
            }
            player.openInventory(secondPlayer.getEnderChest());
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie otworzyłeś enderchest gracza &#fcb419" + secondPlayer.getName());
        } else wrongUsage();
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }
}

