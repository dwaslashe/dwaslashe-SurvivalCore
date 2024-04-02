package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.listeners.PlayerInteractListener;
import xyz.dwaslashe.survivalcore.utils.Api;

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

        if (args.length == 1) {
            Player player = (Player)sender;
            if (args[0].equalsIgnoreCase("closeinventory")) {
                Player player2 = Bukkit.getPlayer(args[1]);
                if (player2 != null) {
                    player2.closeInventory();
                } else offlinePlayer();
            } else if (args[0].equalsIgnoreCase("pirateblock")) {
                Location location = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
                System.out.println("kordy: " + player.getWorld() + player.getLocation().getBlockX() + player.getLocation().getBlockY() + player.getLocation().getBlockZ());
                location.getChunk().load(true);
                Block block = location.getBlock();
                block.setType(Material.CHEST);
                block.setMetadata("PirateBlockEvent", new FixedMetadataValue(Main.getPlugin(), ""));
            } else if (args[0].equalsIgnoreCase("pirateblock2")) {
                Location location = new Location(player.getWorld(), -2086, 91, 923);
                System.out.println("kordy: " + location.getWorld() + location.getBlockX() + location.getBlockY() + location.getBlockZ());
                location.getChunk().load(true);
                Block block = location.getBlock();
                block.setType(Material.CHEST);
                block.setMetadata("PirateBlockEvent", new FixedMetadataValue(Main.getPlugin(), ""));
            } else if (args[0].equalsIgnoreCase("scierka")) {
                player.getInventory().addItem(PlayerInteractListener.cleaningWaterCloth);
                player.getInventory().addItem(OthersListener.cleanAmphetamine);
                player.getInventory().addItem(OthersListener.cleanCocaine);
            }
        } else if (args.length >= 2) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer != null) {
                if (args[1].equalsIgnoreCase("piniata")) {
                    Api.giveOrDrop(targetPlayer, OthersListener.elementPinata);
                } else if (args[1].equalsIgnoreCase("dragon")) {
                    Api.giveOrDrop(targetPlayer, OthersListener.elementEnderDragon);
                }
            }
        }
    }
}
