package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RockPaperScissorsCommand extends Command {
    public RockPaperScissorsCommand() {
        super("rockpaperscissors", "/rockpaperscissors <create> <zakład>", "", "kamienpapiernozyczki");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        else if (args.length == 2) return Api.startsWith(Arrays.asList("1000", "2000", "5000"), args[1]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length >= 0){
            openGui(0, p);
        }
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Wybierz", 4);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack rock = inventoryHelper.prepareItemStack(Material.COBBLESTONE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#34b4ebKamień"));
                });
            });

            ItemStack paper = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#8ceb34Papier"));
                });
            });

            ItemStack scissors = inventoryHelper.prepareItemStack(Material.SHEARS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#eb6b34Nożyczki"));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    player.chat("/discord");
                } else if (e.getSlot() == 13) {
                    player.closeInventory();
                    player.chat("/website");
                } else if (e.getSlot() == 15) {
                    player.closeInventory();
                    player.chat("/facebook");
                } else if (e.getSlot() == 31) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 11, glass_black);
            inventoryHelper.setItem(11, rock);
            inventoryHelper.setItem(12, glass_black);
            inventoryHelper.setItem(13, paper);
            inventoryHelper.setItem(14, glass_black);
            inventoryHelper.setItem(15, scissors);
            inventoryHelper.setItemRange(16, 36, glass_black);

            inventoryHelper.setItem(31, back);

            inventoryHelper.open(player);
        }
    }
}

