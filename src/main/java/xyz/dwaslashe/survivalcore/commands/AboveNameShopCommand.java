package xyz.dwaslashe.survivalcore.commands;

import net.saidora.economy.manager.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class AboveNameShopCommand extends Command {
    public AboveNameShopCommand() {
        super("tytuly", "/tytuly", "");
        setPermission("core.command.abovenameshop");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
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
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Tytuły", 6);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack tag_1 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 1"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#FF3131B E D E O S I A R A",
                            " &#FF3131wie co chce i będzie to miała",
                            "",
                            player.hasPermission("core.abovenameshop.tag1") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag1") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_2 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 2"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#FF10F0Hej randka?",
                            " &#FF10F0Pięknie dziś wyglądasz <3",
                            "",
                            player.hasPermission("core.abovenameshop.tag2") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag2") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_3 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 3"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#FFC42ES E X O H O L I K",
                            " &#c930aegrzeczne dupy nie chcą ze mną chodzić",
                            "",
                            player.hasPermission("core.abovenameshop.tag3") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag3") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_4 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 4"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#21F8F6Na górze róże na dole fiołki",
                            " &#21F8F6my się kochamy jak dwa aniołki",
                            "",
                            player.hasPermission("core.abovenameshop.tag4") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag4") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_5 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 5"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#FF10F0My girl ->",
                            "",
                            player.hasPermission("core.abovenameshop.tag5") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag5") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_6 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 6"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#0394fc<- My boy",
                            "",
                            player.hasPermission("core.abovenameshop.tag6") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag6") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_7 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 7"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#0394fcGOMBAO33 MATA",
                            " &#0394fcpomiot liryczny..",
                            "",
                            player.hasPermission("core.abovenameshop.tag7") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag7") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_8 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 8"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#FDBD01Midas, Midas, Midas!",
                            " &#FDBD01Wszystko, czego tylko dotknę",
                            " &#FDBD01To staje się złotem",
                            "",
                            player.hasPermission("core.abovenameshop.tag8") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag8") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_9 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 9"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#FF3131Mordo wiertara jakbym był Skrillexem",
                            " &#FF3131Muza napierdala przez głośnik USB",
                            "",
                            player.hasPermission("core.abovenameshop.tag9") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag9") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_10 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 10"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#0394fcMłody jeżyk bo chciałbym tylko przeżyć",
                            " &#0394fcSuszyć jedynki po prostu zęby szczerzyć",
                            "",
                            player.hasPermission("core.abovenameshop.tag10") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag10") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_11 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 11"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#FF5F1FRuda tańczy jak szalona",
                            " &#FF5F1FKrzyczy piszczy to jest ona",
                            "",
                            player.hasPermission("core.abovenameshop.tag11") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag11") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_12 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 12"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#008443Rzucam worki w tłum tłum",
                            " &#008443Kto łapie ten jara",
                            "",
                            player.hasPermission("core.abovenameshop.tag12") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag12") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_13 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 13"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#feb1e0Jeśli będzie trzeba, zrobię to co będzie trzeba",
                            " &#feb1e0Sprzedam delfinowi wodę, jeśli będzie trzeba",
                            "",
                            player.hasPermission("core.abovenameshop.tag13") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag13") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_14 = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Tytuł 14"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            "",
                            " &6&l⭐ &#FFF01FTekst tytułu:",
                            "",
                            " &#1F51FFOd małego mówią mi, że jestem poj*bany",
                            " &#1F51FFRobię poj*bany kwit, po dwie bańki od reklamy",
                            "",
                            player.hasPermission("core.abovenameshop.tag14") ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C20000$",
                            "",
                            player.hasPermission("core.abovenameshop.tag14") ? " &#FBFD8C&nKliknij aby ustawić tytuł!" : " &#FBFD8C&nKliknij aby kupić tytuł!")));
                });
            });

            ItemStack tag_coming = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&cZa niedługo.."));
                });
            });

            ItemStack tag_info = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(player.getName());
                    itemMeta.setDisplayName(Api.fixColor("&#92f734Informacja"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Tytuł to jest napis, który pojawia się", " &#E7E7E7nad twoim nickiem i widzą tylko inni gracze")));
                });
            });

            inventoryHelper.click(e -> {
                UserManager.getInstance().getUser(player).ifPresent(user -> {
                    double balance = user.balance();
                    e.setCancelled(true);
                    if (e.getSlot() == 19) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag1")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF3131B E D E O S I A R A%newline%&#FF3131wie co chce i będzie to miała");
                        } else if (balance >= 20000.0) {
                            if (balance - 20000.0 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag1");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF3131B E D E O S I A R A%newline%&#FF3131wie co chce i będzie to miała");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 20) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag2")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF10F0Hej randka?%newline%&#FF10F0Pięknie dziś wyglądasz <3");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag2");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF10F0Hej randka?%newline%&#FF10F0Pięknie dziś wyglądasz <3");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 21) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag3")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FFC42ES E X O H O L I K%newline%&#c930aegrzeczne dupy nie chcą ze mną chodzić");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag3");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FFC42ES E X O H O L I K%newline%&#c930aegrzeczne dupy nie chcą ze mną chodzić");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 22) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag4")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#21F8F6Na górze róże na dole fiołki%newline%&#21F8F6my się kochamy jak dwa aniołki");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag4");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#21F8F6Na górze róże na dole fiołki%newline%&#21F8F6my się kochamy jak dwa aniołki");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 23) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag5")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF10F0My girl ->");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag5");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF10F0My girl ->");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 24) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag6")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#0394fc<- My boy");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag6");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#0394fc<- My boy");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 25) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag7")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#0394fcGOMBAO33 MATA%newline%&#0394fcpomiot liryczny..");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag7");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#0394fcGOMBAO33 MATA%newline%&#0394fcpomiot liryczny...");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 28) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag8")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FDBD01Midas, Midas, Midas!%newline%&#FDBD01Wszystko, czego tylko dotknę%newline%&#FDBD01To staje się złotem");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag8");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FDBD01Midas, Midas, Midas!%newline%&#FDBD01Wszystko, czego tylko dotknę%newline%&#FDBD01To staje się złotem");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 29) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag9")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF3131Mordo wiertara jakbym był Skrillexem%newline%&#FF3131Muza napierdala przez głośnik USB");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag9");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF3131Mordo wiertara jakbym był Skrillexem%newline%&#FF3131Muza napierdala przez głośnik USB");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 30) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag10")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#0394fcMłody jeżyk bo chciałbym tylko przeżyć%newline%&#0394fcSuszyć jedynki po prostu zęby szczerzyć");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag10");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#0394fcMłody jeżyk bo chciałbym tylko przeżyć%newline%&#0394fcSuszyć jedynki po prostu zęby szczerzyć");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 31) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag11")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF5F1FRuda tańczy jak szalona%newline%&#FF5F1FKrzyczy piszczy to jest ona");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag11");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#FF5F1FRuda tańczy jak szalona%newline%&#FF5F1FKrzyczy piszczy to jest ona");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 32) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag12")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#008443Rzucam worki w tłum tłum%newline%&#008443Kto łapie ten jara");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag12");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#008443Rzucam worki w tłum tłum%newline%&#008443Kto łapie ten jara");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 33) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag13")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#feb1e0Jeśli będzie trzeba, zrobię to co będzie trzeba%newline%&#feb1e0Sprzedam delfinowi wodę, jeśli będzie trzeba");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag13");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#feb1e0Jeśli będzie trzeba, zrobię to co będzie trzeba%newline%&#feb1e0Sprzedam delfinowi wodę, jeśli będzie trzeba");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 34) {
                        player.closeInventory();
                        if (player.hasPermission("core.abovenameshop.tag14")) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono twój tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#1F51FFOd małego mówią mi, że jestem poj*bany%newline%&#1F51FFRobię poj*bany kwit, po dwie bańki od reklamy");
                        } else if (balance >= 20000) {
                            if (balance - 20000 >= 0) {
                                user.withdraw(20000.0);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie kupiono tytuł!");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.abovenameshop.tag14");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " &#1F51FFOd małego mówią mi, że jestem poj*bany%newline%&#1F51FFRobię poj*bany kwit, po dwie bańki od reklamy");
                            } else {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else if (e.getSlot() == 4) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wyczyściłeś swój tytuł!");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "admintitletag " + player.getName() + " clear");
                        player.closeInventory();
                    } else if (e.getSlot() == 49) {
                        player.closeInventory();
                    }
                });

            });

            inventoryHelper.setItemRange(0, 54, glass_black);

            inventoryHelper.setItem(19, tag_1);
            inventoryHelper.setItem(20, tag_2);
            inventoryHelper.setItem(21, tag_3);
            inventoryHelper.setItem(22, tag_4);
            inventoryHelper.setItem(23, tag_5);
            inventoryHelper.setItem(24, tag_6);
            inventoryHelper.setItem(25, tag_7);

            inventoryHelper.setItem(28, tag_8);
            inventoryHelper.setItem(29, tag_9);
            inventoryHelper.setItem(30, tag_10);
            inventoryHelper.setItem(31, tag_11);
            inventoryHelper.setItem(32, tag_12);
            inventoryHelper.setItem(33, tag_13);
            inventoryHelper.setItem(34, tag_14);

            inventoryHelper.setItem(4, tag_info);
            inventoryHelper.setItem(49, back);



            inventoryHelper.open(player);
        }
    }
}