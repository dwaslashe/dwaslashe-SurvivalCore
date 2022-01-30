package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;

import java.util.Arrays;
import java.util.List;

public class RankCommand extends Command implements Listener {
    public RankCommand() {
        super("rangi", "/rangi", "", "vip", "svip", "mvp", "mvp+");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length >= 0) {
            openGui(0, p);
        }
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Lista Rang", 6);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack clock = inventoryHelper.prepareItemStack(Material.CLOCK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&aOTWARCIE TRYBU"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(" ", " &6⚠ &eData startu&8: &717.12.2021")));
                });
            });

            ItemStack player_info = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(player.getName());
                    itemMeta.setDisplayName(Api.fixColor(ChatApi.getPrefix(player) + "&7" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby przejś")));
                });
            });

            //VIP

            ItemStack vip_chestplate = inventoryHelper.prepareItemStack(Material.IRON_CHESTPLATE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#21F8F6&lVIP &7" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            " ",
                            " &7Cena usługi&8: &b10 zł",
                            " &7Czas trwania &dCała edycja!",
                            "",
                            " &eKomendy rangi VIP:",
                            " &8>> &f/kit vip &8- &7Zestaw przedmiotów",
                            " &8>> &f/sklep vip &8- &7Sklep premium",
                            " &8>> &f/hat &8- &7Załóż kapelusz na głowe",
                            " &8>> &f/feed &8- &7Zregeneruj swój głód",
                            " &8>> &f/wb &8- &7Przenośny crafting",
                            " &8>> &f/sun &8- &7Słoneczna pogoda",
                            " &8>> &f/storm &8- &7Deszczowa pogoda",
                            " &8>> &f/plot close &8- &7Zamyka działke",
                            " &8>> &f/plot open &8- &7Otwiera działke",
                            "",
                            " &a&nKliknij prawym żeby podejrzeć link!"
                    )));
                });
            });

            ItemStack vip_leggings = inventoryHelper.prepareItemStack(Material.IRON_LEGGINGS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#21F8F6&lVIP &7" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &ePrzywileje rangi VIP:",
                            " &8>> &7Możliwość wejścia na &epełen &7serwer",
                            " &8>> &7Limit aukcji zwiększony do &e5",
                            " &8>> &7Limit &e2 &7domów do stworzenia",
                            " &8>> &7Dostęp do &bstrefy VIP",
                            " &8>> &7Otrzymuje rangę&b VIP&7 na Discordzie",
                            " &8>> &7Może pisać &bk&eo&dl&2o&6r&co&8w&3o&7 na tabliczkach",
                            " &8>> &7Posiada większy &dEnderChest&7, 36 slotów",
                            " &8>> &7Limit postawionych działek jest zwiększony do &e2",
                            " &8>> &7Może działke mieć do &e30&7 kratek",
                            " &8>> &7Może do działki dodać &e10&7 osób",
                            "",
                            " &a&nKliknij prawym żeby podejrzeć link!"
                    )));
                });
            });

            ItemStack vip_boots = inventoryHelper.prepareItemStack(Material.IRON_BOOTS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&aKliknij po link!"));
                });
            });

            ItemStack vip_sword = inventoryHelper.prepareItemStack(Material.IRON_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&eZestaw rangi VIP"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &a&nKliknij aby zobaczyć zestaw rangi!")));
                });
            });

            //SVIP

            ItemStack svip_chestplate = inventoryHelper.prepareItemStack(Material.GOLDEN_CHESTPLATE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42E&lS&#FFF01F&lVIP &7" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                                "",
                                " &7Cena usługi&8: &b20 zł",
                                " &7Czas trwania &dCała edycja!",
                                "",
                                " &eKomendy rangi SVIP:",
                                " &8>> &f/kit svip &8- &7Zestaw przedmiotów",
                                " &8>> &f/kit vip &8- &7Zestaw przedmiotów",
                                " &8>> &f/sklep vip &8- &7Sklep premium",
                                " &8>> &f/hat &8- &7Załóż kapelusz na głowe",
                                " &8>> &f/feed &8- &7Zregeneruj swój głód",
                                " &8>> &f/wb &8- &7Przenośny crafting",
                                " &8>> &f/sun &8- &7Słoneczna pogoda",
                                " &8>> &f/storm &8- &7Deszczowa pogoda",
                                " &8>> &f/day &8- &7Ujawnia dzień",
                                " &8>> &f/ec &8- &7Otwiera EnderChest",
                                " &8>> &f/repair &8- &7Naprawia narzędzie w ręce",
                                " &8>> &f/plot close &8- &7Zamyka działke",
                                " &8>> &f/plot open &8- &7Otwiera działke",
                                "",
                                " &a&nKliknij prawym żeby podejrzeć link!"
                    )));
                });
            });

            ItemStack svip_leggings = inventoryHelper.prepareItemStack(Material.GOLDEN_LEGGINGS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42E&lS&#FFF01F&lVIP &7" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                                "",
                                " &ePrzywileje rangi SVIP:",
                                " &8>> &7Możliwość wejścia na &epełen &7serwer",
                                " &8>> &7Limit aukcji zwiększony do &e10",
                                " &8>> &7Limit &e3 &7domów do stworzenia",
                                " &8>> &7Dostęp do &bstrefy VIP",
                                " &8>> &7Otrzymuje rangę&6 S&eVIP&7 na Discordzie",
                                " &8>> &7Może pisać &bk&eo&dl&2o&6r&co&8w&3o&7 na tabliczkach",
                                " &8>> &7Posiada większy &dEnderChest&7, 45 slotów",
                                " &8>> &7Pisanie na &bk&eo&dl&2o&6r&co&8w&3o",
                                " &8>> &7Może pisać na czacie bez opóźnienia",
                                " &8>> &7Limit postawionych działek jest zwiększony do &e3",
                                " &8>> &7Może działke mieć do &e40&7 kratek",
                                " &8>> &7Może do działki dodać &e15&7 osób",
                                "",
                                " &a&nKliknij prawym żeby podejrzeć link!"
                    )));
                });
            });

            ItemStack svip_boots = inventoryHelper.prepareItemStack(Material.GOLDEN_BOOTS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&aKliknij po link!"));
                });
            });

            ItemStack svip_sword = inventoryHelper.prepareItemStack(Material.GOLDEN_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&eZestaw rangi SVIP"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &a&nKliknij aby zobaczyć zestaw rangi!")));
                });
            });

            //MVP

            ItemStack mvp_chestplate = inventoryHelper.prepareItemStack(Material.DIAMOND_CHESTPLATE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF01F&lMVP &7" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &7Cena usługi&8: &b30 zł",
                            " &7Czas trwania &dCała edycja!",
                            "",
                            " &eKomendy rangi MVP:",
                            " &8>> &f/kit mvp &8- &7Zestaw przedmiotów",
                            " &8>> &f/kit svip &8- &7Zestaw przedmiotów",
                            " &8>> &f/kit vip &8- &7Zestaw przedmiotów",
                            " &8>> &f/sklep vip &8- &7Sklep premium",
                            " &8>> &f/hat &8- &7Załóż kapelusz na głowe",
                            " &8>> &f/feed &8- &7Zregeneruj swój głód",
                            " &8>> &f/wb &8- &7Przenośny crafting",
                            " &8>> &f/sun &8- &7Słoneczna pogoda",
                            " &8>> &f/storm &8- &7Deszczowa pogoda",
                            " &8>> &f/day &8- &7Ujawnia dzień",
                            " &8>> &f/night &8- &7Ujawnia noc",
                            " &8>> &f/ec &8- &7Otwiera EnderChest",
                            " &8>> &f/repair &8- &7Naprawia narzędzie w ręce",
                            " &8>> &f/glowing &8- &7Błyszczenie",
                            " &8>> &f/plot close &8- &7Zamyka działke",
                            " &8>> &f/plot open &8- &7Otwiera działke",
                            "",
                            " &a&nKliknij prawym żeby podejrzeć link!"
                    )));
                });
            });

            ItemStack mvp_leggings = inventoryHelper.prepareItemStack(Material.DIAMOND_LEGGINGS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF01F&lMVP &7" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &ePrzywileje rangi MVP:",
                            " &8>> &7Możliwość wejścia na &epełen &7serwer",
                            " &8>> &7Limit aukcji zwiększony do &e15",
                            " &8>> &7Limit &e5 &7domów do stworzenia",
                            " &8>> &7Dostęp do &bstrefy VIP",
                            " &8>> &7Otrzymuje rangę&6 &bMVP&7 na Discordzie",
                            " &8>> &7Może pisać &bk&eo&dl&2o&6r&co&8w&3o&7 na tabliczkach",
                            " &8>> &7Posiada większy &dEnderChest&7, 54 slotów",
                            " &8>> &7Pisanie na &bk&eo&dl&2o&6r&co&8w&3o",
                            " &8>> &7Może pisać na czacie bez opóźnienia",
                            " &8>> &7Limit postawionych działek jest zwiększony do &e4",
                            " &8>> &7Może działke mieć do &e50&7 kratek",
                            " &8>> &7Może do działki dodać &e20&7 osób",
                            "",
                            " &a&nKliknij prawym żeby podejrzeć link!"
                    )));
                });
            });

            ItemStack mvp_boots = inventoryHelper.prepareItemStack(Material.DIAMOND_BOOTS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&aKliknij po link!"));
                });
            });

            ItemStack mvp_sword = inventoryHelper.prepareItemStack(Material.DIAMOND_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&eZestaw rangi MVP"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &a&nKliknij aby zobaczyć zestaw rangi!")));
                });
            });

            //MVP+

            ItemStack mvpplus_chestplate = inventoryHelper.prepareItemStack(Material.NETHERITE_CHESTPLATE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.setDisplayName(Api.fixColor("&#B026FF&lMVP+ &7" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &7Cena usługi&8: &b100 zł",
                            " &7Czas trwania &dNa Zawsze!",
                            "",
                            " &eKomendy rangi MVP+:",
                            " &8>> &f/kit mvp+ &8- &7Zestaw przedmiotów",
                            " &8>> &f/kit mvp &8- &7Zestaw przedmiotów",
                            " &8>> &f/kit svip &8- &7Zestaw przedmiotów",
                            " &8>> &f/kit vip &8- &7Zestaw przedmiotów",
                            " &8>> &f/sklep vip &8- &7Sklep premium",
                            " &8>> &f/hat &8- &7Załóż kapelusz na głowe",
                            " &8>> &f/feed &8- &7Zregeneruj swój głód",
                            " &8>> &f/wb &8- &7Przenośny crafting",
                            " &8>> &f/sun &8- &7Słoneczna pogoda",
                            " &8>> &f/storm &8- &7Deszczowa pogoda",
                            " &8>> &f/day &8- &7Ujawnia dzień",
                            " &8>> &f/night &8- &7Ujawnia noc",
                            " &8>> &f/ec &8- &7Otwiera EnderChest",
                            " &8>> &f/repair &8- &7Naprawia narzędzie w ręce",
                            " &8>> &f/repair all &8- &7Naprawia wszystkie narzędzie w equ",
                            " &8>> &f/item &8- &7Pozwala zmienić nazwe i lore itemu",
                            " &8>> &f/glowing &8- &7Błyszczenie",
                            " &8>> &f/heal &8- &7Ulecza",
                            " &8>> &f/gamma &8- &7Włącza widzenie w ciemności",
                            " &8>> &f/plot close &8- &7Zamyka działke",
                            " &8>> &f/plot open &8- &7Otwiera działke",
                            "",
                            " &a&nKliknij prawym żeby podejrzeć link!"
                    )));
                });
            });

            ItemStack mvpplus_leggings = inventoryHelper.prepareItemStack(Material.NETHERITE_LEGGINGS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.setDisplayName(Api.fixColor("&#B026FF&lMVP+ &7" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &ePrzywileje rangi MVP+:",
                            " &8>> &7Możliwość wejścia na &epełen &7serwer",
                            " &8>> &7Limit aukcji zwiększony do &e20",
                            " &8>> &7Limit &e12 &7domów do stworzenia",
                            " &8>> &7Dostęp do &bstrefy VIP",
                            " &8>> &7Otrzymuje rangę&6 &dMVP+&7 na Discordzie",
                            " &8>> &7Może pisać &bk&eo&dl&2o&6r&co&8w&3o&7 na tabliczkach",
                            " &8>> &7Posiada większy &dEnderChest&7, 54 slotów",
                            " &8>> &7Pisanie na &bk&eo&dl&2o&6r&co&8w&3o",
                            " &8>> &7Może pisać na czacie bez opóźnienia",
                            " &8>> &7Limit postawionych działek jest zwiększony do &e5",
                            " &8>> &7Może działke mieć do &e60&7 kratek",
                            " &8>> &7Może do działki dodać &e22&7 osób",
                            " &8>> &f&nMoże latać na swojej działce!",
                            "",
                            " &a&nKliknij prawym żeby podejrzeć link!"
                    )));
                });
            });

            ItemStack mvpplus_boots = inventoryHelper.prepareItemStack(Material.NETHERITE_BOOTS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.setDisplayName(Api.fixColor("&aKliknij po link!"));
                });
            });

            ItemStack mvpplus_sword = inventoryHelper.prepareItemStack(Material.NETHERITE_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.setDisplayName(Api.fixColor("&eZestaw rangi MVP+"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &a&nKliknij aby zobaczyć zestaw rangi!")));
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
                    player.chat("/kit preview vip");
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                    player.chat("/kit preview svip");
                } else if (e.getSlot() == 41) {
                    player.closeInventory();
                    player.chat("/kit preview mvp");
                } else if (e.getSlot() == 42) {
                    player.closeInventory();
                    player.chat("/kit preview mvp+");
                }
            });

            inventoryHelper.setItemRange(0, 10, glass_black);
            inventoryHelper.setItem(10, player_info);
            inventoryHelper.setItem(11, glass_black);
            inventoryHelper.setItem(12, vip_chestplate);
            inventoryHelper.setItem(13, svip_chestplate);
            inventoryHelper.setItem(14, mvp_chestplate);
            inventoryHelper.setItem(15, mvpplus_chestplate);
            inventoryHelper.setItemRange(16, 19, glass_black);
            inventoryHelper.setItem(19, clock);
            inventoryHelper.setItem(20, glass_black);
            inventoryHelper.setItem(21, vip_leggings);
            inventoryHelper.setItem(22, svip_leggings);
            inventoryHelper.setItem(23, mvp_leggings);
            inventoryHelper.setItem(24, mvpplus_leggings);
            inventoryHelper.setItemRange(25, 30, glass_black);
            inventoryHelper.setItem(30, vip_boots);
            inventoryHelper.setItem(31, svip_boots);
            inventoryHelper.setItem(32, mvp_boots);
            inventoryHelper.setItem(33, mvpplus_boots);
            inventoryHelper.setItemRange(34, 39, glass_black);
            inventoryHelper.setItem(39, vip_sword);
            inventoryHelper.setItem(40, svip_sword);
            inventoryHelper.setItem(41, mvp_sword);
            inventoryHelper.setItem(42, mvpplus_sword);
            inventoryHelper.setItemRange(43, 54, glass_black);

            inventoryHelper.open(player);
        }
    }
}
