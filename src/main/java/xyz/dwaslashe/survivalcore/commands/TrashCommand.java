package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class TrashCommand extends Command {
    public TrashCommand() {
        super("kosz", "/kosz", "", "trash");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(null, 54, "Kosz");
        inventory.clear();
        player.openInventory(inventory);
        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie otworzyłeś &ekosz");
    }
}
