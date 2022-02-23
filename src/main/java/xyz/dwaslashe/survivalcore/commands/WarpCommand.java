package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.managers.TeleportManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class WarpCommand extends Command implements Listener {
    public WarpCommand() {
        super("warp", "/warp", "", "waprs", "warpy");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        System.out.println("1");
        Player p = (Player) sender;
        if (args.length >= 0){
            System.out.println("2");
            openGui(1, p);
        }
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Lista warpów", 4);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack kasyno = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&dKasyno"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby przeteleportować się na warp!")));
                });
            });

            ItemStack skrzynie = inventoryHelper.prepareItemStack(Material.CHEST, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&eSkrzynie"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby przeteleportować się na warp!")));
                });
            });

            ItemStack end = inventoryHelper.prepareItemStack(Material.END_PORTAL_FRAME, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&dEnd"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby przeteleportować się na warp!")));
                });
            });

            inventoryHelper.click(e -> {
                World world = Bukkit.getWorld("world");
                World worldend = Bukkit.getWorld("world_the_end");
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    Location loc = new Location(world, 3464, 72, 1773, -154, 3);
                    TeleportManager.teleport(player, 5, loc);
                } else if (e.getSlot() == 12) {
                    player.getOpenInventory().close();
                    Location loc = new Location(world, 24, 87, 39, 0, 4);
                    TeleportManager.teleport(player, 5, loc);
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    Location loc = new Location(worldend, 88, 58, 0, 90, 2);
                    TeleportManager.teleport(player, 5, loc);
                }
            });

            inventoryHelper.setItemRange(0, 11, glass_black);
            //inventoryHelper.setItem(11, kasyno);
            //inventoryHelper.setItem(12, skrzynie);
            //inventoryHelper.setItem(13, end);
            inventoryHelper.setItemRange(16, 21, glass_black);
            inventoryHelper.setItemRange(24, 36, glass_black);

            inventoryHelper.open(player);
        }
    }
}
