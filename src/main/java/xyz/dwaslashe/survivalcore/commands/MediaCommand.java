package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class MediaCommand extends Command implements Listener {
    public MediaCommand() {
        super("media", "/media", "", "socialmedia");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        openGui(0, p);
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Social Media", 5);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });
            ItemStack glass_gray = inventoryHelper.prepareItemStack(Material.GRAY_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack website = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY5MTk2YjMzMGM2Yjg5NjJmMjNhZDU2MjdmYjZlY2NlNDcyZWFmNWM5ZDQ0Zjc5MWY2NzA5YzdkMGY0ZGVjZSJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&eStrona WWW"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby przejś")));
                });
            });
            ItemStack facebook = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGViNDYxMjY5MDQ0NjNmMDdlY2ZjOTcyYWFhMzczNzNhMjIzNTliNWJhMjcxODIxYjY4OWNkNTM2N2Y3NTc2MiJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&bFacebook"));
                });
            });
            ItemStack discord = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&9Discord"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("&r")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    player.chat("/discord");
                } else if (e.getSlot() == 30) {
                    player.closeInventory();
                } else if (e.getSlot() == 31) {
                    player.closeInventory();
                } else if (e.getSlot() == 32) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 12, glass_black);
            inventoryHelper.setItemRange(12, 17, glass_gray);
            inventoryHelper.setItemRange(17, 19, glass_black);
            inventoryHelper.setItemRange(19, 26, glass_gray);
            inventoryHelper.setItemRange(26, 28, glass_black);
            inventoryHelper.setItemRange(28, 30, glass_gray);
            inventoryHelper.setItem(30, discord);
            inventoryHelper.setItem(31, website);
            inventoryHelper.setItem(32, facebook);
            inventoryHelper.setItemRange(33, 35, glass_gray);
            inventoryHelper.setItemRange(35, 39, glass_black);
            inventoryHelper.setItem(39, glass_black);
            inventoryHelper.setItem(40, glass_black);
            inventoryHelper.setItem(41, glass_black);
            inventoryHelper.setItemRange(42, 45, glass_black);

            inventoryHelper.open(player);
        }
    }
}
