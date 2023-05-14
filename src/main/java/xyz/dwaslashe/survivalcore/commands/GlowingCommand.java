package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class GlowingCommand extends Command {
    public GlowingCommand() {
        super("glowing", "/glowing", "");
        setPermission("core.command.glowing");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Player p = (Player) sender;
            if (p.isGlowing()) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cPomyślnie wyłączyłeś &eglowing!");
                p.setGlowing(false);
            } else {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie włączyłeś &eglowing!");
                p.setGlowing(true);
            }
        } else wrongUsage();
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Glowing", 5);

            User user = UserCache.getInstance().compute(player.getUniqueId());

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack glass_lime = inventoryHelper.prepareItemStack(Material.LIME_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14AKTYWNE"));
                });
            });

            ItemStack glass_red = inventoryHelper.prepareItemStack(Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131NIEAKTYWNE"));
                });
            });

            ItemStack previous = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Poprzednia strona"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby przejsc do poprzedniej strony!")));
                });
            });

            ItemStack next = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Nastepna strona"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby przejsc do nastepnej strony!")));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack light_purple = inventoryHelper.prepareItemStack(Material.LEATHER_CHESTPLATE, itemStack -> {
                inventoryHelper.setColor(itemStack, Color.PURPLE);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF10F0Jasno fioletowy"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Aktywne: &a", "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });
            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 10) {
                } else if (e.getSlot() == 11) {
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);

            inventoryHelper.setItem(10, light_purple);
            inventoryHelper.setItem(19, (user.getAbyss() == 0) ? glass_lime : glass_red);

            inventoryHelper.setItem(40, back);
            inventoryHelper.setItem(41, next);


            inventoryHelper.open(player);
        }
    }
}