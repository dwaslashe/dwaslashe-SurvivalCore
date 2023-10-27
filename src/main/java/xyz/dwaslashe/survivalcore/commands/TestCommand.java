package xyz.dwaslashe.survivalcore.commands;

import net.saidora.api.helpers.ItemHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import xyz.dwaslashe.survivalcore.Main;
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
    public void commandExecute(CommandSender sender, String[] args) {
        Api.sendMessage(sender, "test");
        Api.giveOrDrop((Player) sender, ItemHelper.edit(new ItemStack(Material.MAGMA_CUBE_SPAWN_EGG)).editNbtTagCompound(nbtItem -> {
            nbtItem.setString("geyser", "geyser");
        }).getItemStack());


        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("closeinventory")) {
                Player player2 = Bukkit.getPlayer(args[1]);
                if (player2 != null) {
                    player2.closeInventory();
                } else offlinePlayer();
            } else if (args[0].equalsIgnoreCase("pirateblock")) {
                Player player = (Player)sender;
                Location location = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
                System.out.println("kordy: " + player.getWorld() + player.getLocation().getBlockX() + player.getLocation().getBlockY() + player.getLocation().getBlockZ());
                location.getChunk().load(true);
                Block block = location.getBlock();
                block.setType(Material.CHEST);
                block.setMetadata("PirateBlockEvent", new FixedMetadataValue(Main.getPlugin(), ""));
            } else if (args[0].equalsIgnoreCase("pirateblock2")) {
                Player player = (Player)sender;
                Location location = new Location(player.getWorld(), -2086, 91, 923);
                System.out.println("kordy: " + location.getWorld() + location.getBlockX() + location.getBlockY() + location.getBlockZ());
                location.getChunk().load(true);
                Block block = location.getBlock();
                block.setType(Material.CHEST);
                block.setMetadata("PirateBlockEvent", new FixedMetadataValue(Main.getPlugin(), ""));
            }
        }
    }
}
