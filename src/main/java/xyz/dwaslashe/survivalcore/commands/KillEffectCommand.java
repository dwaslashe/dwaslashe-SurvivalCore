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

public class KillEffectCommand extends Command {
    public KillEffectCommand() {
        super("efektyzabojstwa", "/efektyzabojstwa", "");
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
            InventoryHelper inventoryHelper = new InventoryHelper(player, "&fᵩǚ", 5);

            User user = UserCache.getInstance().compute(player.getUniqueId());

            ItemStack glass_lime = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11232);
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Aktywne"));
                });
            });

            ItemStack glass_red = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11218);
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Nieaktywne"));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11196);
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack next = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Następna"));
                    itemMeta.setCustomModelData(11191);
                });
            });

            ItemStack ashestoashes = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eZ prochu w proch"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.ashestoashes") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 1) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack bubblybubble = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eBąbelkowy bąbel"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.bubblybubble") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 2) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });


            ItemStack darkmatter = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eCiemna materia"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.darkmatter") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 3) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack disintegration = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eDezintegracja"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.disintegration") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 4) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack glassshatter = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eRoztrzaskane szkło"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.glassshatter") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 5) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack iceshatter = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eRoztrzaskany lód"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.iceshatter") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 6) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack hologramflicker = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eMigotanie hologramu"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.hologramflicker") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 7) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if(e.getSlot() == 10){
                    if (player.hasPermission("core.killeffect.ashestoashes")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 1) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(1);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 11) {
                    if (player.hasPermission("core.killeffect.bubblybubble")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 2) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(2);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 12) {
                    if (player.hasPermission("core.killeffect.darkmatter")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 3) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(3);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 13) {
                    if (player.hasPermission("core.killeffect.disintegration")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 4) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(4);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 14) {
                    if (player.hasPermission("core.killeffect.glassshatter")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 5) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(5);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 15) {
                    if (player.hasPermission("core.killeffect.iceshatter")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 6) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(6);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 16) {
                    if (player.hasPermission("core.killeffect.hologramflicker")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 7) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(7);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if (e.getSlot() == 41) {
                    openGui(1, player);
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItem(10, ashestoashes);
            inventoryHelper.setItem(11, bubblybubble);
            inventoryHelper.setItem(12, darkmatter);
            inventoryHelper.setItem(13, disintegration);
            inventoryHelper.setItem(14, glassshatter);
            inventoryHelper.setItem(15, iceshatter);
            inventoryHelper.setItem(16, hologramflicker);

            inventoryHelper.setItem(19, (user.getKillEffect() == 1) ? glass_lime : glass_red);
            inventoryHelper.setItem(20, (user.getKillEffect() == 2) ? glass_lime : glass_red);
            inventoryHelper.setItem(21, (user.getKillEffect() == 3) ? glass_lime : glass_red);
            inventoryHelper.setItem(22, (user.getKillEffect() == 4) ? glass_lime : glass_red);
            inventoryHelper.setItem(23, (user.getKillEffect() == 5) ? glass_lime : glass_red);
            inventoryHelper.setItem(24, (user.getKillEffect() == 6) ? glass_lime : glass_red);
            inventoryHelper.setItem(25, (user.getKillEffect() == 7) ? glass_lime : glass_red);

            inventoryHelper.setItem(40, back);
            inventoryHelper.setItem(41, next);



            inventoryHelper.open(player);
        }

        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "&fᵩǚ", 5);

            User user = UserCache.getInstance().compute(player.getUniqueId());

            ItemStack glass_lime = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11232);
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Aktywne"));
                });
            });

            ItemStack glass_red = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11218);
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Nieaktywne"));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11196);
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack previous = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("#FF3131Poprzednia"));
                    itemMeta.setCustomModelData(11189);
                });
            });

            ItemStack holyabsorption = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eŚwięta absorpcja"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.holyabsorption") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 8) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack photosynthesis = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eFotosynteza"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.photosynthesis") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 9) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack rustydecay = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eRdzewiejący rozpad"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.rustydecay") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 10) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack sanddissolve = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eRozpuszczanie się piasku"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.sanddissolve") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 11) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack soundwavedisperse = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eRozpraszanie fal dźwiękowych"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.soundwavedisperse") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 12) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack spectralfade = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eSqpektralne zanikanie"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.spectralfade") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 13) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            ItemStack stonecrumble = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11055);
                    itemMeta.setDisplayName(Api.fixColor("&#41506eKruchość kamienia"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Dostęp: &#4cf739" + (player.hasPermission("core.killeffect.stonecrumble") ? "&#39FF14Tak" : "&#FF3131Nie"), " &#E7E7E7Aktywne: &#4cf739" + ((user.getKillEffect() == 14) ? "&#39FF14Tak" : "&#FF3131Nie"), "", " &f᎘ &#FBFD8CAby włączyć/wyłączyć efekt zabójstwa")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if(e.getSlot() == 10){
                    if (player.hasPermission("core.killeffect.holyabsorption")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 8) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(8);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 11) {
                    if (player.hasPermission("core.killeffect.photosynthesis")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 9) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(9);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 12) {
                    if (player.hasPermission("core.killeffect.rustydecay")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 10) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(10);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 13) {
                    if (player.hasPermission("core.killeffect.sanddissolve")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 11) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(11);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 14) {
                    if (player.hasPermission("core.killeffect.soundwavedisperse")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 12) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(12);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 15) {
                    if (player.hasPermission("core.killeffect.spectralfade")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 13) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(13);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if(e.getSlot() == 16) {
                    if (player.hasPermission("core.killeffect.stonecrumble")) {
                        UserCache.getInstance().compute(player.getUniqueId(), User -> {
                            if (User.getKillEffect() == 14) {
                                User.setKillEffect(0);
                                openGui(0, (Player) sender);
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączono efekt zabójstwa!");
                                return;
                            }
                            User.setKillEffect(14);
                            openGui(0, (Player) sender);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrano efekt zabójstwa!");
                        });
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wybrać tego efektu ponieważ nie masz go kupionego! Zakup go w sklepie &#fcb419/portfel");
                } else if (e.getSlot() == 39) {
                    openGui(0, player);
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItem(10, holyabsorption);
            inventoryHelper.setItem(11, photosynthesis);
            inventoryHelper.setItem(12, rustydecay);
            inventoryHelper.setItem(13, sanddissolve);
            inventoryHelper.setItem(14, soundwavedisperse);
            inventoryHelper.setItem(15, spectralfade);
            inventoryHelper.setItem(16, stonecrumble);

            inventoryHelper.setItem(19, (user.getKillEffect() == 8) ? glass_lime : glass_red);
            inventoryHelper.setItem(20, (user.getKillEffect() == 9) ? glass_lime : glass_red);
            inventoryHelper.setItem(21, (user.getKillEffect() == 10) ? glass_lime : glass_red);
            inventoryHelper.setItem(22, (user.getKillEffect() == 11) ? glass_lime : glass_red);
            inventoryHelper.setItem(23, (user.getKillEffect() == 12) ? glass_lime : glass_red);
            inventoryHelper.setItem(24, (user.getKillEffect() == 13) ? glass_lime : glass_red);
            inventoryHelper.setItem(25, (user.getKillEffect() == 14) ? glass_lime : glass_red);

            inventoryHelper.setItem(39, previous);
            inventoryHelper.setItem(40, back);



            inventoryHelper.open(player);
        }
    }
}
