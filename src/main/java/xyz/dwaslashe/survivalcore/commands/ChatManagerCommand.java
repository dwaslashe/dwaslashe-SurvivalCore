package xyz.dwaslashe.survivalcore.commands;

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

public class ChatManagerCommand extends Command {
    public ChatManagerCommand() {
        super("manager", "/manager", "", "chatmanager");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        openGui(0, (Player) sender);
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Zarządzanie czatem", 5);

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

            ItemStack abyss = inventoryHelper.prepareItemStack(Material.ENDER_EYE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF10F0Informacje o otchłani"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FFC42EAktywne&8: &a" + ((user.getAbyss() == 0) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            ItemStack automsg = inventoryHelper.prepareItemStack(Material.BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#008443Informacje o auto wiadomości"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FFC42EAktywne&8: &a" + ((user.getAutomsg() == 0) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            ItemStack autobossbar = inventoryHelper.prepareItemStack(Material.BEACON, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#00FFFFInformacje o auto wiadomości bossbar"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FFC42EAktywne&8: &a" + ((user.getAutobossbar() == 0) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            ItemStack deaths = inventoryHelper.prepareItemStack(Material.SKELETON_SKULL, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#b51919Informacje o śmierciach"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FFC42EAktywne&8: &a" + ((user.getDeaths() == 0) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if(e.getSlot() == 10){
                    UserCache.getInstance().compute(player.getUniqueId(), User -> {
                        if (User.getAbyss() == 0) {
                            User.setAbyss(1);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        } else {
                            User.setAbyss(0);
                            openGui(0, (Player) sender);
                            inventoryHelper.getPlayer().updateInventory();
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        }
                    });
                } else if(e.getSlot() == 12) {
                    UserCache.getInstance().compute(player.getUniqueId(), User -> {
                        if (User.getAutomsg() == 0) {
                            User.setAutomsg(1);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        } else {
                            User.setAutomsg(0);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        }
                    });
                } else if(e.getSlot() == 14) {
                    UserCache.getInstance().compute(player.getUniqueId(), User -> {
                        if (User.getAutobossbar() == 0) {
                            User.setAutobossbar(1);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        } else {
                            User.setAutobossbar(0);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        }
                    });
                } else if(e.getSlot() == 16) {
                    UserCache.getInstance().compute(player.getUniqueId(), User -> {
                        if (User.getDeaths() == 0) {
                            User.setDeaths(1);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        } else {
                            User.setDeaths(0);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        }
                    });
                } else if (e.getSlot() == 41) {
                    openGui(1, player);
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);

            inventoryHelper.setItem(10, abyss);
            inventoryHelper.setItem(19, (user.getAbyss() == 0) ? glass_lime : glass_red);
            inventoryHelper.setItem(12, automsg);
            inventoryHelper.setItem(21, (user.getAutomsg() == 0) ? glass_lime : glass_red);
            inventoryHelper.setItem(14, autobossbar);
            inventoryHelper.setItem(23, (user.getAutobossbar() == 0) ? glass_lime : glass_red);
            inventoryHelper.setItem(16, deaths);
            inventoryHelper.setItem(25, (user.getDeaths() == 0) ? glass_lime : glass_red);
            inventoryHelper.setItem(40, back);
            inventoryHelper.setItem(41, next);



            inventoryHelper.open(player);
        }

        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Zarządzanie czatem", 5);

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

            ItemStack discord = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#7289daInformacje o wiadomościach przez discord"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FFC42EAktywne&8: &a" + ((user.getDiscordchat() == 0) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            ItemStack chat = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjAyYWYzY2EyZDVhMTYwY2ExMTE0MDQ4Yjc5NDc1OTQyNjlhZmUyYjFiNWVjMjU1ZWU3MmI2ODNiNjBiOTliOSJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#18aff0Informacje o wiadomościach wysyłanych przez graczy"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FFC42EAktywne&8: &a" + ((user.getChat() == 0) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &#FBFD8C&nKliknij aby włączyć/wyłączyć informacje!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if(e.getSlot() == 10) {
                    UserCache.getInstance().compute(player.getUniqueId(), User -> {
                        if (User.getDiscordchat() == 0) {
                            User.setDiscordchat(1);
                            openGui(1, (Player) sender);
                            inventoryHelper.getPlayer().updateInventory();
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        } else {
                            User.setDiscordchat(0);
                            openGui(1, (Player) sender);
                            inventoryHelper.getPlayer().updateInventory();
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        }
                    });
                } else if(e.getSlot() == 12) {
                    UserCache.getInstance().compute(player.getUniqueId(), User -> {
                        if (User.getChat() == 0) {
                            User.setChat(1);
                            openGui(1, (Player) sender);
                            inventoryHelper.getPlayer().updateInventory();
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        } else {
                            User.setChat(0);
                            openGui(1, (Player) sender);
                            inventoryHelper.getPlayer().updateInventory();
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniono aktywność informacji!");
                        }
                    });
                } else if (e.getSlot() == 39) {
                    openGui(0, player);
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);

            inventoryHelper.setItem(10, discord);
            inventoryHelper.setItem(19, (user.getDiscordchat() == 0) ? glass_lime : glass_red);
            inventoryHelper.setItem(12, chat);
            inventoryHelper.setItem(21, (user.getChat() == 0) ? glass_lime : glass_red);
            inventoryHelper.setItem(39, previous);
            inventoryHelper.setItem(40, back);



            inventoryHelper.open(player);
        }
    }
}
