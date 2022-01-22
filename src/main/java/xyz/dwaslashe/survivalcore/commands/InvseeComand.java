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

public class InvseeComand extends Command {
    public InvseeComand() {
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
        Player p = (Player)sender;
        if (args.length == 1) {
            Player p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            }
            p.openInventory(p2.getInventory());
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aOtworzyłeś ekwipunek gracza &e" + p2.getName());

            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("armor")) {
                    Inventory inv = Bukkit.createInventory(p, 9, "Armor - " + p2.getName());
                    if (p2.getInventory().getHelmet() != null && p2.getInventory().getHelmet().getType() != Material.AIR)
                        inv.setItem(0, p2.getInventory().getHelmet().clone());
                    if (p2.getInventory().getChestplate() != null && p2.getInventory().getChestplate().getType() != Material.AIR)
                        inv.setItem(1, p2.getInventory().getChestplate().clone());
                    if (p2.getInventory().getLeggings() != null && p2.getInventory().getLeggings().getType() != Material.AIR)
                        inv.setItem(2, p2.getInventory().getLeggings().clone());
                    if (p2.getInventory().getBoots() != null && p2.getInventory().getBoots().getType() != Material.AIR)
                        inv.setItem(3, p2.getInventory().getBoots().clone());
                    p.openInventory(inv);
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aOtworzyłeś armor gracza &e" + p2.getName());
                }
            } else wrongUsage();
        } else wrongUsage();
    }
}