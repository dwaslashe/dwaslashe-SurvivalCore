package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class NickColorCommand extends Command implements Listener {
    public NickColorCommand() {
        super("nickcolor", "/nickcolor", "", "kolornick", "colornick", "nickkolor", "kolor", "color");
        setPermission("core.command.nickcolor");
        setOnlyPlayer(true);
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
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Kolor nicku", 5);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack glass_lime = inventoryHelper.prepareItemStack(Material.LIME_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14POSIADASZ PERMISJE"));
                });
            });

            ItemStack glass_red = inventoryHelper.prepareItemStack(Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131BRAK PERMISJI"));
                });
            });

            ItemStack rainbow = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(player.getName());
                    itemMeta.setDisplayName(Api.fixColor("&#00FFFFInformacje"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(" ",
                            " &#39FF14Zmieniony nick&8: &#FFF01F" + player.getDisplayName(),
                            " &#39FF14Prawdziwy nick&8: &#FFF01F" + player.getName(),
                            "",
                            " &#FBFD8C&nKliknij aby zresetować kolor nicku!")));
                });
            });

            ItemStack nametag_color = inventoryHelper.prepareItemStack(Material.NAME_TAG, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EKolor"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("" +
                                    "",
                            " #39FF14Zmień swój kolor nicku na #f79459ładniejszy #39FF14kolor!",
                            "",
                            " &#FBFD8C&nKliknij aby przejść do menu wyboru!")));
                });
            });

            ItemStack nametag_gradient = inventoryHelper.prepareItemStack(Material.NAME_TAG, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("<gradient:#5e4fa2:#f79459>Gradient</gradient>"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("" +
                                    "",
                            " #39FF14Zmień swój kolor nicku na <gradient:#5e4fa2:#f79459>gradientowy</gradient> kolor!",
                            "",
                            " &#FBFD8C&nKliknij aby przejść do menu wyboru!")));
                });
            });

            ItemStack nametag_gradient2 = inventoryHelper.prepareItemStack(Material.NAME_TAG, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        itemMeta.setDisplayName(Api.fixColor("<gradient:#DFFF00:#9FE2BF:#CCCCFF>Lepszy gradient</gradient>"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("" +
                                    "",
                            " #39FF14Zmień swój kolor nicku na <gradient:#DFFF00:#9FE2BF:#CCCCFF>gradientowy</gradient> kolor!",
                            "",
                            " &#FBFD8C&nKliknij aby przejść do menu wyboru!")));
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
                    if (player.hasPermission("core.command.nickcolor.color")) {
                        openGui(1, player);
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz do tego permisji!");
                } else if (e.getSlot() == 15) {
                    player.closeInventory();
                    if (player.hasPermission("core.command.nickcolor.gradient")) {
                        openGui(2, player);
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz do tego permisji!");
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " " + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zrestartowałeś kolor nicku!");
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);

            inventoryHelper.setItem(11, nametag_color);
            if (player.hasPermission("core.command.nickcolor.color")) {
                inventoryHelper.setItem(20, glass_lime);
            } else {
                inventoryHelper.setItem(20, glass_red);
            }

            inventoryHelper.setItem(15, nametag_gradient);
            if (player.hasPermission("core.command.nickcolor.gradient")) {
                inventoryHelper.setItem(24, glass_lime);
            } else {
                inventoryHelper.setItem(24, glass_red);
            }

            inventoryHelper.setItem(13, rainbow);

            inventoryHelper.setItem(40, back);
            inventoryHelper.open(player);
        }
        //1
        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Kolor nicku", 5);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack orange = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FE5000" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack magenta = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#EA00FF" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack blue = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#21F8F6" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack dark_blue = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#098AFA" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack yellow = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF01F" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack lime = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39ff14" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack dark_lime = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#008443" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack gray = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#808080" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack white = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#F5F5F5" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack pink = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fe019a" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack turkusowy = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#09FADE" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack brown = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#D47E07" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack purple = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#A907EC" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack light_pink = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#F6ADEC" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 10) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#008443" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#FE5000" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 12) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#EA00FF" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#21F8F6" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 14) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#FFF01F" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 15) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#39ff14" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 16) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#098AFA" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 19) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#D47E07" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 20) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#09FADE" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 21) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#808080" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 22) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#F5F5F5" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 23) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#fe019a" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 24) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#A907EC" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 25) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " &#F6ADEC" + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 40) {
                    player.getOpenInventory().close();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);
            inventoryHelper.setItem(10, dark_lime);
            inventoryHelper.setItem(11, orange);
            inventoryHelper.setItem(12, magenta);
            inventoryHelper.setItem(13, blue);
            inventoryHelper.setItem(14, yellow);
            inventoryHelper.setItem(15, lime);
            inventoryHelper.setItem(16, dark_blue);


            inventoryHelper.setItem(19, brown);
            inventoryHelper.setItem(20, turkusowy);
            inventoryHelper.setItem(21, gray);
            inventoryHelper.setItem(22, white);
            inventoryHelper.setItem(23, pink);
            inventoryHelper.setItem(24, purple);
            inventoryHelper.setItem(25, light_pink);

            inventoryHelper.setItem(40, back);

            inventoryHelper.open(player);
        }
        //2
        if (guiID == 2) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Pierwszy kolor gradientu", 5);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack orange = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FE5000" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack magenta = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#EA00FF" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack blue = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#21F8F6" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack dark_blue = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#098AFA" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack yellow = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF01F" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack lime = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39ff14" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack dark_lime = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#008443" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack gray = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#808080" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack white = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#F5F5F5" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack pink = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fe019a" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack turkusowy = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#09FADE" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack brown = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#D47E07" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack purple = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#A907EC" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack light_pink = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#F6ADEC" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 10) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#008443");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#FE5000");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 12) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#EA00FF");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#21F8F6");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 14) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#FFF01F");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 15) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#39ff14");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 16) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#098AFA");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 19) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#D47E07");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 20) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#09FADE");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 21) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#808080");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 22) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#F5F5F5");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 23) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#fe019a");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 24) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#A907EC");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 25) {
                    player.getOpenInventory().close();
                    nextColorGradientGui(1, player, "#F6ADEC");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wybrałeś pierwszy kolory gradientu!");
                } else if (e.getSlot() == 40) {
                    player.getOpenInventory().close();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);
            inventoryHelper.setItem(10, dark_lime);
            inventoryHelper.setItem(11, orange);
            inventoryHelper.setItem(12, magenta);
            inventoryHelper.setItem(13, blue);
            inventoryHelper.setItem(14, yellow);
            inventoryHelper.setItem(15, lime);
            inventoryHelper.setItem(16, dark_blue);


            inventoryHelper.setItem(19, brown);
            inventoryHelper.setItem(20, turkusowy);
            inventoryHelper.setItem(21, gray);
            inventoryHelper.setItem(22, white);
            inventoryHelper.setItem(23, pink);
            inventoryHelper.setItem(24, purple);
            inventoryHelper.setItem(25, light_pink);

            inventoryHelper.setItem(40, back);

            inventoryHelper.open(player);
        }
    }

    private void nextColorGradientGui(int guiID, Player player, String color) {
        //1
        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Drugi kolor gradientu", 5);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack orange = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FE5000" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack magenta = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#EA00FF" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack blue = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#21F8F6" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack dark_blue = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#098AFA" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack yellow = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF01F" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack lime = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39ff14" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack dark_lime = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#008443" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack gray = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#808080" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack white = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#F5F5F5" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack pink = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fe019a" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack turkusowy = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#09FADE" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack brown = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#D47E07" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack purple = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#A907EC" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack light_pink = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#F6ADEC" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });
            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 10) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#008443>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#FE5000:>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 12) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#EA00FF>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#21F8F6>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 14) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#FFF01F>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 15) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#39ff14>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 16) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#098AFA>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 19) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#D47E07>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 20) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#09FADE>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 21) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#808080>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 22) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#F5F5F5>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 23) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#fe019a>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 24) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#A907EC>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 25) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <gradient:" + color + ":#F6ADEC>" + player.getName() + "</gradient>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 40) {
                    player.getOpenInventory().close();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);
            inventoryHelper.setItem(10, dark_lime);
            inventoryHelper.setItem(11, orange);
            inventoryHelper.setItem(12, magenta);
            inventoryHelper.setItem(13, blue);
            inventoryHelper.setItem(14, yellow);
            inventoryHelper.setItem(15, lime);
            inventoryHelper.setItem(16, dark_blue);


            inventoryHelper.setItem(19, brown);
            inventoryHelper.setItem(20, turkusowy);
            inventoryHelper.setItem(21, gray);
            inventoryHelper.setItem(22, white);
            inventoryHelper.setItem(23, pink);
            inventoryHelper.setItem(24, purple);
            inventoryHelper.setItem(25, light_pink);

            inventoryHelper.setItem(40, back);

            inventoryHelper.open(player);
        }
    }
}
