package xyz.dwaslashe.survivalcore.commands;

import net.saidora.api.helpers.ItemHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.LocationApi;

import java.util.Arrays;
import java.util.List;

public class TestCommand extends Command {

    public TestCommand() {
        super("test", "/test", "", "test2");
        setPermission("core.command.test");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Api.sendMessage(s, "test");
        Api.giveOrDrop((Player) s, ItemHelper.edit(new ItemStack(Material.MAGMA_CUBE_SPAWN_EGG)).editNbtTagCompound(nbtItem -> {
            nbtItem.setString("geyser", "geyser");
        }).getItemStack());



        if (args[0].equalsIgnoreCase("closeinventory")) {
            Player player2 = Bukkit.getPlayer(args[1]);
            if (player2 != null) {
                player2.closeInventory();
            } else offlinePlayer();
        }
    }
}
