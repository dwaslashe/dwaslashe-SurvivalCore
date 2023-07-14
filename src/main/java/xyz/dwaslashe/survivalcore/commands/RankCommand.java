package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;

import java.util.Arrays;
import java.util.List;

public class RankCommand extends Command implements Listener {
    public RankCommand() {
        super("rangi", "/rangi", "", "vip", "svip", "mvp", "mvp+", "sponsor");
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
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Lista rang", 6);

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack clock = inventoryHelper.prepareItemStack(Material.CLOCK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EOTWARCIE TRYBU"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(" ", " &6⚠ &#39FF14Data startu&8: &#FFF01F" + Main.pluginConfig.getMessages().getData())));
                });
            });

            ItemStack player_info = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(player.getName());
                    itemMeta.setDisplayName(Api.fixColor(ChatApi.getPrefix(player) + ChatApi.getSuffix(player) + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("")));
                });
            });

            //VIP

            ItemStack vip_chestplate = inventoryHelper.prepareItemStack(Material.IRON_CHESTPLATE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getVip().getChestplate_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getVip().getChestplate_lore()));
                });
            });

            ItemStack vip_leggings = inventoryHelper.prepareItemStack(Material.IRON_LEGGINGS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getVip().getLeggings_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getVip().getLeggings_lore()));
                });
            });

            ItemStack vip_boots = inventoryHelper.prepareItemStack(Material.IRON_BOOTS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getVip().getBoots_name().replace("{PLAYER}", player.getName())));
                });
            });

            ItemStack vip_sword = inventoryHelper.prepareItemStack(Material.IRON_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getVip().getSword_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getVip().getSword_lore()));
                });
            });

            //SVIP

            ItemStack svip_chestplate = inventoryHelper.prepareItemStack(Material.GOLDEN_CHESTPLATE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getSvip().getChestplate_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getSvip().getChestplate_lore()));
                });
            });

            ItemStack svip_leggings = inventoryHelper.prepareItemStack(Material.GOLDEN_LEGGINGS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getSvip().getLeggings_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getSvip().getLeggings_lore()));
                });
            });

            ItemStack svip_boots = inventoryHelper.prepareItemStack(Material.GOLDEN_BOOTS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getSvip().getBoots_name().replace("{PLAYER}", player.getName())));
                });
            });

            ItemStack svip_sword = inventoryHelper.prepareItemStack(Material.GOLDEN_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getSvip().getSword_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getSvip().getSword_lore()));
                });
            });

            //MVP

            ItemStack mvp_chestplate = inventoryHelper.prepareItemStack(Material.DIAMOND_CHESTPLATE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getMvp().getChestplate_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getMvp().getChestplate_lore()));
                });
            });

            ItemStack mvp_leggings = inventoryHelper.prepareItemStack(Material.DIAMOND_LEGGINGS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getMvp().getLeggings_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getMvp().getLeggings_lore()));
                });
            });

            ItemStack mvp_boots = inventoryHelper.prepareItemStack(Material.DIAMOND_BOOTS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getMvp().getBoots_name().replace("{PLAYER}", player.getName())));
                });
            });

            ItemStack mvp_sword = inventoryHelper.prepareItemStack(Material.DIAMOND_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getMvp().getSword_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getMvp().getSword_lore()));
                });
            });

            //MVP+

            ItemStack mvpplus_chestplate = inventoryHelper.prepareItemStack(Material.NETHERITE_CHESTPLATE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getMvpplus().getChestplate_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getMvpplus().getChestplate_lore()));
                });
            });

            ItemStack mvpplus_leggings = inventoryHelper.prepareItemStack(Material.NETHERITE_LEGGINGS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getMvpplus().getLeggings_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getMvpplus().getLeggings_lore()));
                });
            });

            ItemStack mvpplus_boots = inventoryHelper.prepareItemStack(Material.NETHERITE_BOOTS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getMvpplus().getBoots_name().replace("{PLAYER}", player.getName())));
                });
            });

            ItemStack mvpplus_sword = inventoryHelper.prepareItemStack(Material.NETHERITE_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getMvpplus().getSword_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getMvpplus().getSword_lore()));
                });
            });

            //SPONSOR

            ItemStack sponsor_chestplate = inventoryHelper.prepareItemStack(Material.NETHERITE_CHESTPLATE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getSponsor().getChestplate_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getSponsor().getChestplate_lore()));
                });
            });

            ItemStack sponsor_leggings = inventoryHelper.prepareItemStack(Material.NETHERITE_LEGGINGS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getSponsor().getLeggings_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getSponsor().getLeggings_lore()));
                });
            });

            ItemStack sponsor_boots = inventoryHelper.prepareItemStack(Material.NETHERITE_BOOTS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getSponsor().getBoots_name().replace("{PLAYER}", player.getName())));
                });
            });

            ItemStack sponsor_sword = inventoryHelper.prepareItemStack(Material.NETHERITE_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.setDisplayName(Api.fixColor(Main.pluginRank.getSponsor().getSword_name().replace("{PLAYER}", player.getName())));
                    itemMeta.setLore(Api.fixColor(Main.pluginRank.getSponsor().getSword_lore()));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 30) {
                    player.getOpenInventory().close();
                    player.chat("/website");
                } else if (e.getSlot() == 31) {
                    player.closeInventory();
                    player.chat("/website");
                } else if (e.getSlot() == 32) {
                    player.closeInventory();
                    player.chat("/website");
                } else if (e.getSlot() == 33) {
                    player.closeInventory();
                    player.chat("/website");
                } else if (e.getSlot() == 39) {
                    player.closeInventory();
                    player.chat(Main.pluginRank.getVip().getSword_command());
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                    player.chat(Main.pluginRank.getSvip().getSword_command());
                } else if (e.getSlot() == 41) {
                    player.closeInventory();
                    player.chat(Main.pluginRank.getMvp().getSword_command());
                } else if (e.getSlot() == 42) {
                    player.closeInventory();
                    player.chat(Main.pluginRank.getMvpplus().getSword_command());
                } else if (e.getSlot() == 43) {
                    player.closeInventory();
                    player.chat(Main.pluginRank.getSponsor().getSword_command());
                } else if (e.getSlot() == 49) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 54, glass_black);
            inventoryHelper.setItem(10, player_info);
            inventoryHelper.setItem(19, clock);
            inventoryHelper.setItem(49, back);

            if (Main.pluginRank.getVip().isEnable()) {
                inventoryHelper.setItem(12, vip_chestplate);
                inventoryHelper.setItem(21, vip_leggings);
                inventoryHelper.setItem(30, vip_boots);
                inventoryHelper.setItem(39, vip_sword);
            }


            if (Main.pluginRank.getSvip().isEnable()) {
                inventoryHelper.setItem(13, svip_chestplate);
                inventoryHelper.setItem(22, svip_leggings);
                inventoryHelper.setItem(31, svip_boots);
                inventoryHelper.setItem(40, svip_sword);
            }

            if (Main.pluginRank.getMvp().isEnable()) {
                inventoryHelper.setItem(14, mvp_chestplate);
                inventoryHelper.setItem(23, mvp_leggings);
                inventoryHelper.setItem(32, mvp_boots);
                inventoryHelper.setItem(41, mvp_sword);
            }

            if (Main.pluginRank.getMvpplus().isEnable()) {
                inventoryHelper.setItem(15, mvpplus_chestplate);
                inventoryHelper.setItem(24, mvpplus_leggings);
                inventoryHelper.setItem(33, mvpplus_boots);
                inventoryHelper.setItem(42, mvpplus_sword);
            }

            if (Main.pluginRank.getSponsor().isEnable()) {
                inventoryHelper.setItem(16, sponsor_chestplate);
                inventoryHelper.setItem(25, sponsor_leggings);
                inventoryHelper.setItem(34, sponsor_boots);
                inventoryHelper.setItem(43, sponsor_sword);
            }

            inventoryHelper.open(player);
        }
    }
}
