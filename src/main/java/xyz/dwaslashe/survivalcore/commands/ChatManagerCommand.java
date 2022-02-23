package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.model.impl.UserImpl;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class ChatManagerCommand extends Command {
    public ChatManagerCommand() {
        super("manager", "/manager", "", "chatmanager");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if(sender instanceof Player player) {
            Main.getPlugin().getUserCache().getOnline(player.getName()).ifPresent(user -> {
                InventoryHelper inventoryHelper = new InventoryHelper(player, "Zarządzanie czatem", 3);

                inventoryHelper.setItemRange(0, 3 * 9, itemMeta -> itemMeta.setDisplayName(" "), new ItemStack(Material.BLACK_STAINED_GLASS_PANE));

                inventoryHelper.setItem(11, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&dInformacje o otchłani"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &7Aktywne&8: &a" + (user.abyss() ? "&aTak" : "&cNie"))));
                }, new ItemStack(Material.ENDER_EYE));

                inventoryHelper.setItem(13, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&eInformacje o auto wiadomości"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &7Aktywne&8: &a" + (user.autochat() ? "&aTak" : "&cNie"))));
                }, new ItemStack(Material.BOOK));

                inventoryHelper.setItem(15, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&bInformacje o auto wiadomości bossbar"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &7Aktywne&8: &a" + (user.autobar() ? "&aTak" : "&cNie"))));
                }, new ItemStack(Material.BEACON));

                inventoryHelper.click(event -> {
                    event.setCancelled(true);
                    if(event.getSlot() == 11){
                        ((UserImpl)user).setAbyss(!user.abyss());
                        inventoryHelper.setItem(11, itemMeta -> {
                            itemMeta.setDisplayName(Api.fixColor("&dInformacje o otchłani"));
                            itemMeta.setLore(Api.fixColor(Arrays.asList("", " &7Aktywne&8: &a" + (user.abyss() ? "&aTak" : "&cNie"))));
                        }, new ItemStack(Material.ENDER_EYE));
                    } else if(event.getSlot() == 13) {
                        ((UserImpl) user).setAutochat(!user.autochat());
                        inventoryHelper.setItem(13, itemMeta -> {
                            itemMeta.setDisplayName(Api.fixColor("&eInformacje o auto wiadomości"));
                            itemMeta.setLore(Api.fixColor(Arrays.asList("", " &7Aktywne&8: &a" + (user.autochat() ? "&aTak" : "&cNie"))));
                        }, new ItemStack(Material.BOOK));
                    } else if(event.getSlot() == 15) {
                        ((UserImpl) user).setAutobar(!user.autobar());
                        inventoryHelper.setItem(15, itemMeta -> {
                            itemMeta.setDisplayName(Api.fixColor("&bInformacje o auto wiadomości bossbar"));
                            itemMeta.setLore(Api.fixColor(Arrays.asList("", " &7Aktywne&8: &a" + (user.autobar() ? "&aTak" : "&cNie"))));
                        }, new ItemStack(Material.BEACON));
                    }
                });
                inventoryHelper.open(player);
            });
        }
    }
}
