package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserTreeCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.objects.UserTree;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class PhysicsCommand extends Command {
    public PhysicsCommand() {
        super("physics", "/animacja", "", "animacja");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
        UserTree userTree = UserTreeCache.getInstance().compute(player.getUniqueId());

        if (userTree.getAnimation() == null) {
            userTree.setAnimation("sand");
        }

        openGui(0, player);
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Animacja upadku drzewa", 5);

            UserTree userTree = UserTreeCache.getInstance().compute(player.getUniqueId());

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

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack simple = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2YxMzQ2MDkyYzgwZDNkYjIxN2VmZTRjOTM2OTY5MWU2MWM4YWZjMWIyODc0MWZhNTA0ODJjOTJjOWZkM2QxOCJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#c47926Domyślna"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Aktywne: &a" + ((userTree.getAnimation().equals("simple")) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            ItemStack sand = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRiMzA1YjBlNzRiZjljOTIzMzgzYzI5M2UzZjEyMTliM2FlNDY0YzY4OThjN2FkZTYzZmI4ZjM0OTU1MzYwMSJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#C2B280Piaskowa"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Aktywne: &a" + ((userTree.getAnimation().equals("sand")) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            ItemStack quick = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjllOGE3YjM3ZjM0MmU4ZmI0NThhOWM5ODlmNTE5NmMyNjUxYjg3ODExZGU1NGZlMDVlNjFiZjRkMjg3Yjk5YiJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#ed994aSzybka"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Aktywne: &a" + ((userTree.getAnimation().equals("quick")) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            ItemStack animation = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4ZWRjYjAwZTE2NGYxMTRhMTBlZDhjN2ExNjgwNWE2NTVjZDdmOWQwMDY1MDBlOGEyYThkM2Y1M2IzMTVjOCJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39ff14Realna"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Aktywne: &a" + ((userTree.getAnimation().equals("animation")) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 10) {
                    UserTreeCache.getInstance().compute(player.getUniqueId(), User -> {
                        if (!User.getAnimation().contains("simple")) {
                            User.setAnimation("simple");
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono rodzaj animacji na &edomyślną!");
                        }
                    });
                } else if (e.getSlot() == 12) {
                    UserTreeCache.getInstance().compute(player.getUniqueId(), User -> {
                        if (!User.getAnimation().contains("sand")) {
                            User.setAnimation("sand");
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono rodzaj animacji na &epiaskową!");
                        }
                    });
                } else if (e.getSlot() == 14) {
                    UserTreeCache.getInstance().compute(player.getUniqueId(), User -> {
                        if (!User.getAnimation().contains("quick")) {
                            User.setAnimation("quick");
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono rodzaj animacji na &eszybką!");
                        }
                    });
                //} else if (e.getSlot() == 16) {
                //    UserTreeCache.getInstance().compute(player.getUniqueId(), User -> {
                //        if (!User.getAnimation().contains("animation")) {
                //            User.setAnimation("animation");
                //            openGui(0, (Player) sender);
                //            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono rodzaj animacji na &erealną!");
                //        }
                //    });
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);

            inventoryHelper.setItem(10, simple);
            inventoryHelper.setItem(19, (userTree.getAnimation().equals("simple")) ? glass_lime : glass_red);
            inventoryHelper.setItem(12, sand);
            inventoryHelper.setItem(21, (userTree.getAnimation().equals("sand")) ? glass_lime : glass_red);
            inventoryHelper.setItem(14, quick);
            inventoryHelper.setItem(23, (userTree.getAnimation().equals("quick")) ? glass_lime : glass_red);
            //inventoryHelper.setItem(16, animation);
            //inventoryHelper.setItem(25, (userTree.getAnimation().equals("animation")) ? glass_lime : glass_red);
            inventoryHelper.setItem(40, back);


            inventoryHelper.open(player);
        }
    }
}
