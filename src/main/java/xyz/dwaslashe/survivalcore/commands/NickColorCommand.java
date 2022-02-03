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
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Wybierz menu kolorów", 3);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack rainbow = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRiMDM3OTRiOWIzZTNiNWQwN2UzYmU2OGI5NmFmODdkZjIxNWMzNzUyZTU0NzM2YzgwZjdkNTBiZDM0MzdhNCJ9fX0=");
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&eInformacje"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(" ", " &7Twój nick&8: &e" + player.getDisplayName(), " &7Twój prawdziwy nick&8: &e" + player.getName(), "", " &f&nKliknij aby zresetować kolor nicku!")));
                });
            });

            ItemStack nametag_color = inventoryHelper.prepareItemStack(Material.NAME_TAG, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&eWybierz Kolor"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &7Wybór gradientu jest tylko od rangi &#FFF01F&lMVP", " &7Posiadasz permisje&8: &e" + (player.hasPermission("core.command.nickcolor.color") ? "&aTak" : "&cNie"), "", " &f&nKliknij aby przejść do menu wyboru!")));
                });
            });

            ItemStack nametag_gradient = inventoryHelper.prepareItemStack(Material.NAME_TAG, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.setDisplayName(Api.fixColor("&dWybierz Gradient"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &7Wybór gradientu jest tylko od rangi &#B026FF&lMVP+", " &7Posiadasz permisje&8: &e" + (player.hasPermission("core.command.nickcolor.gradient") ? "&aTak" : "&cNie"), "", " &f&nKliknij aby przejść do menu wyboru!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    if (player.hasPermission("core.command.nickcolor.color")) {
                        openGui(1, player);
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz do tego permisji!");
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " " + player.getName());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zrestartowałeś kolor nicku!");
                } else if (e.getSlot() == 15) {
                    player.closeInventory();
                    if (player.hasPermission("core.command.nickcolor.gradient")) {
                        openGui(2, player);
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz do tego permisji!");
                }
            });

            inventoryHelper.setItemRange(0, 11, glass_black);
            inventoryHelper.setItem(11, nametag_color);
            inventoryHelper.setItem(12, glass_black);
            inventoryHelper.setItem(13, rainbow);
            inventoryHelper.setItem(14, glass_black);
            inventoryHelper.setItem(15, nametag_gradient);
            inventoryHelper.setItemRange(16, 27, glass_black);

            inventoryHelper.open(player);
        }
        //1
        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Wybierz kolor nicku", 4);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack orange = inventoryHelper.prepareItemStack(Material.ORANGE_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FE5000" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack magenta = inventoryHelper.prepareItemStack(Material.MAGENTA_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#EA00FF" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack blue = inventoryHelper.prepareItemStack(Material.LIGHT_BLUE_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#21F8F6" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack yellow = inventoryHelper.prepareItemStack(Material.YELLOW_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF01F" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack lime = inventoryHelper.prepareItemStack(Material.LIME_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39ff14" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack gray = inventoryHelper.prepareItemStack(Material.GRAY_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#808080" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack white = inventoryHelper.prepareItemStack(Material.WHITE_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#F5F5F5" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby zmienić kolor nicku!")));
                });
            });

            ItemStack pink = inventoryHelper.prepareItemStack(Material.PINK_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fe019a" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby zmienić kolor nicku!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
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
                }
            });

            inventoryHelper.setItemRange(0, 11, glass_black);
            inventoryHelper.setItem(11, orange);
            inventoryHelper.setItem(12, magenta);
            inventoryHelper.setItem(13, blue);
            inventoryHelper.setItem(14, yellow);
            inventoryHelper.setItem(15, lime);
            inventoryHelper.setItemRange(16, 21, glass_black);
            inventoryHelper.setItem(21, gray);
            inventoryHelper.setItem(22, white);
            inventoryHelper.setItem(23, pink);
            inventoryHelper.setItemRange(24, 36, glass_black);

            inventoryHelper.open(player);
        }
        //2
        if (guiID == 2) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Wybierz pierwszy kolor gradientu", 4);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack orange = inventoryHelper.prepareItemStack(Material.ORANGE_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FE5000" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać pierwszy kolor gradientu!")));
                });
            });

            ItemStack magenta = inventoryHelper.prepareItemStack(Material.MAGENTA_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#EA00FF" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać pierwszy kolor gradientu!")));
                });
            });

            ItemStack blue = inventoryHelper.prepareItemStack(Material.LIGHT_BLUE_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#21F8F6" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać pierwszy kolor gradientu!")));
                });
            });

            ItemStack yellow = inventoryHelper.prepareItemStack(Material.YELLOW_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF01F" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać pierwszy kolor gradientu!")));
                });
            });

            ItemStack lime = inventoryHelper.prepareItemStack(Material.LIME_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39ff14" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać pierwszy kolor gradientu!")));
                });
            });

            ItemStack gray = inventoryHelper.prepareItemStack(Material.GRAY_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#808080" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać pierwszy kolor gradientu!")));
                });
            });

            ItemStack white = inventoryHelper.prepareItemStack(Material.WHITE_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#F5F5F5" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać pierwszy kolor gradientu!")));
                });
            });

            ItemStack pink = inventoryHelper.prepareItemStack(Material.PINK_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fe019a" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać pierwszy kolor gradientu!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
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
                }
            });

            inventoryHelper.setItemRange(0, 11, glass_black);
            inventoryHelper.setItem(11, orange);
            inventoryHelper.setItem(12, magenta);
            inventoryHelper.setItem(13, blue);
            inventoryHelper.setItem(14, yellow);
            inventoryHelper.setItem(15, lime);
            inventoryHelper.setItemRange(16, 21, glass_black);
            inventoryHelper.setItem(21, gray);
            inventoryHelper.setItem(22, white);
            inventoryHelper.setItem(23, pink);
            inventoryHelper.setItemRange(24, 36, glass_black);

            inventoryHelper.open(player);
        }
    }

    private void nextColorGradientGui(int guiID, Player player, String color) {
        //1
        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Wybierz drugi kolor gradientu", 4);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack orange = inventoryHelper.prepareItemStack(Material.ORANGE_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FE5000" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać drugi kolor gradientu!")));
                });
            });

            ItemStack magenta = inventoryHelper.prepareItemStack(Material.MAGENTA_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#EA00FF" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać drugi kolor gradientu!")));
                });
            });

            ItemStack blue = inventoryHelper.prepareItemStack(Material.LIGHT_BLUE_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#21F8F6" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać drugi kolor gradientu!")));
                });
            });

            ItemStack yellow = inventoryHelper.prepareItemStack(Material.YELLOW_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF01F" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać drugi kolor gradientu!")));
                });
            });

            ItemStack lime = inventoryHelper.prepareItemStack(Material.LIME_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39ff14" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać drugi kolor gradientu!")));
                });
            });

            ItemStack gray = inventoryHelper.prepareItemStack(Material.GRAY_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#808080" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać drugi kolor gradientu!")));
                });
            });

            ItemStack white = inventoryHelper.prepareItemStack(Material.WHITE_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#F5F5F5" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać drugi kolor gradientu!")));
                });
            });

            ItemStack pink = inventoryHelper.prepareItemStack(Material.PINK_DYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fe019a" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f&nKliknij aby wybrać drugi kolor gradientu!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <" + color + ">" + player.getName() + "</#FE5000>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 12) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <" + color + ">" + player.getName() + "</#EA00FF>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <" + color + ">" + player.getName() + "</#21F8F6>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 14) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <" + color + ">" + player.getName() + "</#FFF01F>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 15) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <" + color + ">" + player.getName() + "</#39ff14>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 21) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <" + color + ">" + player.getName() + "</#808080>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 22) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <" + color + ">" + player.getName() + "</#F5F5F5>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                } else if (e.getSlot() == 23) {
                    player.getOpenInventory().close();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " <" + color + ">" + player.getName() + "</#fe019a>");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś nick na &e" + player.getDisplayName());
                }
            });

            inventoryHelper.setItemRange(0, 11, glass_black);
            inventoryHelper.setItem(11, orange);
            inventoryHelper.setItem(12, magenta);
            inventoryHelper.setItem(13, blue);
            inventoryHelper.setItem(14, yellow);
            inventoryHelper.setItem(15, lime);
            inventoryHelper.setItemRange(16, 21, glass_black);
            inventoryHelper.setItem(21, gray);
            inventoryHelper.setItem(22, white);
            inventoryHelper.setItem(23, pink);
            inventoryHelper.setItemRange(24, 36, glass_black);

            inventoryHelper.open(player);
        }
    }
}
