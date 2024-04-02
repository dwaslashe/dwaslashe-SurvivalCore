package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InvseeCommand extends Command {
    public InvseeCommand() {
        super("invsee", "/invsee <nick> <armor>", "");
        setPermission("core.command.invsee");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        else if (args.length == 2) return Api.startsWith(Arrays.asList("armor"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length >= 1) {
            Player secondPlayer = Bukkit.getPlayer(args[0]);
            if (secondPlayer == null) {
                offlinePlayer();
                return;
            }
            player.openInventory(secondPlayer.getInventory());
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Otworzyłeś ekwipunek gracza &#fcb419" + secondPlayer.getName());

            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("armor")) {
                    Inventory inv = Bukkit.createInventory(player, 9, "Armor gracza " + secondPlayer.getName());
                    if (secondPlayer.getInventory().getHelmet() != null && secondPlayer.getInventory().getHelmet().getType() != Material.AIR)
                        inv.setItem(0, secondPlayer.getInventory().getHelmet().clone());
                    if (secondPlayer.getInventory().getChestplate() != null && secondPlayer.getInventory().getChestplate().getType() != Material.AIR)
                        inv.setItem(1, secondPlayer.getInventory().getChestplate().clone());
                    if (secondPlayer.getInventory().getLeggings() != null && secondPlayer.getInventory().getLeggings().getType() != Material.AIR)
                        inv.setItem(2, secondPlayer.getInventory().getLeggings().clone());
                    if (secondPlayer.getInventory().getBoots() != null && secondPlayer.getInventory().getBoots().getType() != Material.AIR)
                        inv.setItem(3, secondPlayer.getInventory().getBoots().clone());
                    player.openInventory(inv);
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Otworzyłeś armor gracza &#fcb419" + secondPlayer.getName());
                } else wrongUsage();
            }
        } else wrongUsage();
    }
}