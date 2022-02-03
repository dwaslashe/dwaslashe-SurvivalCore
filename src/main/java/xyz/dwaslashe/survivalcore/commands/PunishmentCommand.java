package xyz.dwaslashe.survivalcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class PunishmentCommand extends Command implements Listener {

    public PunishmentCommand() {
        super("punishment", "/punishment <gracz>", "", "kara");
        setPermission("core.command.punishment");
        setOnlyPlayer(true);
    }
    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if(args.length == 1){
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if(target == null) {
                offlinePlayer();
                return;
            }
            openGui(0, (Player) sender, target);
        }else wrongUsage();
    }

    private void openGui(int guiID, Player player, OfflinePlayer offlinePlayer) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Kara dla " + offlinePlayer.getName(), 5);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                itemStack.setDurability((short) 15);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });
            ItemStack glass_gray = inventoryHelper.prepareItemStack(Material.GRAY_STAINED_GLASS_PANE, itemStack -> {
                itemStack.setDurability((short) 7);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });
            ItemStack glass_lime = inventoryHelper.prepareItemStack(Material.LIME_STAINED_GLASS_PANE, itemStack -> {
                itemStack.setDurability((short) 5);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });
            ItemStack glass_red = inventoryHelper.prepareItemStack(Material.RED_STAINED_GLASS_PANE, itemStack -> {
                itemStack.setDurability((short) 14);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });
            ItemStack barrier = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> itemMeta.setDisplayName(Api.fixColor("&cZamknij"))));

            ItemStack player_info = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(offlinePlayer.getName());
                    itemMeta.setDisplayName(Api.fixColor(offlinePlayer.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(" ", " &7Ping&8: &a" + (offlinePlayer.isOnline() ? Api.getPing(offlinePlayer.getPlayer()) : "&cnie można pobrać pingu"), " &7UUID&8: &a" + offlinePlayer.getUniqueId())));
                });
            });

            ItemStack player_history = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> inventoryHelper
                    .editMetaForItemStack(itemStack, itemMeta -> {
                        itemMeta.setDisplayName(Api.fixColor("&eHistoria"));
                        itemMeta.setLore(Api.fixColor(Arrays.asList(" ", " &f&nKliknij lewym aby zobaczyc historie gracza!")));
                    }));

            ItemStack ban = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZiMWQ0OTQ3NzYzOTE3ODE0YWUxNjMyYjgyMDY5NjA5ODkyNzg5NWFhYWYxMjRjZDI5ZWIzNTg1NmFhYTViOSJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&cBan"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("&r", player.hasPermission("punishment.ban") ? "&aPomyślnie posiadasz permisje do tej funkcji" : "&cPomyślnie posiadasz permisje do tej funkcji", "", " &f&nKliknij aby przejść dalej!")));
                });
            });
            ItemStack kick = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZiMWQ0OTQ3NzYzOTE3ODE0YWUxNjMyYjgyMDY5NjA5ODkyNzg5NWFhYWYxMjRjZDI5ZWIzNTg1NmFhYTViOSJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&cKick"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("&r", player.hasPermission("punishment.kick") ? "&aPomyślnie posiadasz permisje do tej funkcji" : "&cPomyślnie posiadasz permisje do tej funkcji", "", " &f&nKliknij aby przejść dalej!")));
                });
            });
            ItemStack mute = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZiMWQ0OTQ3NzYzOTE3ODE0YWUxNjMyYjgyMDY5NjA5ODkyNzg5NWFhYWYxMjRjZDI5ZWIzNTg1NmFhYTViOSJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&cMute"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("&r", player.hasPermission("punishment.mute") ? "&aPomyślnie posiadasz permisje do tej funkcji" : "&cPomyślnie posiadasz permisje do tej funkcji", "", " &f&nKliknij aby przejść dalej!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    player.chat("/history " + offlinePlayer.getName());
                } else if (e.getSlot() == 30) {
                    player.closeInventory();
                    if (player.hasPermission("core.command.punishment.ban")) {
                        player.closeInventory();
                        openGui(1, player, offlinePlayer);
                    } else player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.punishment.ban&8) &8<<"));
                } else if (e.getSlot() == 31) {
                    player.closeInventory();
                    if (player.hasPermission("core.command.punishment.kick")) {
                        player.closeInventory();
                        openGui(3, player, offlinePlayer);
                    } else player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.punishment.kick&8) &8<<"));
                } else if (e.getSlot() == 32) {
                    player.closeInventory();
                    if (player.hasPermission("core.command.punishment.mute")) {
                        player.closeInventory();
                        openGui(2, player, offlinePlayer);
                    } else player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.punishment.mute&8) &8<<"));
                } else if (e.getSlot() == 44) {
                    player.getOpenInventory().close();
                }
            });

            inventoryHelper.setItemRange(0, 10, glass_black);
            inventoryHelper.setItem(10, player_info);
            inventoryHelper.setItem(11, player_history);
            inventoryHelper.setItemRange(12, 17, glass_gray);
            inventoryHelper.setItemRange(17, 19, glass_black);
            inventoryHelper.setItemRange(19, 26, glass_gray);
            inventoryHelper.setItemRange(26, 28, glass_black);
            inventoryHelper.setItemRange(28, 30, glass_gray);
            inventoryHelper.setItem(30, ban);
            inventoryHelper.setItem(31, kick);
            inventoryHelper.setItem(32, mute);
            inventoryHelper.setItemRange(33, 35, glass_gray);
            inventoryHelper.setItemRange(35, 39, glass_black);
            inventoryHelper.setItem(39, player.hasPermission("core.command.punishment.ban") ? glass_lime : glass_red);
            inventoryHelper.setItem(40, player.hasPermission("core.command.punishment.kick") ? glass_lime : glass_red);
            inventoryHelper.setItem(41, player.hasPermission("core.command.punishment.mute") ? glass_lime : glass_red);
            inventoryHelper.setItemRange(42, 44, glass_black);
            inventoryHelper.setItem(44, barrier);

            inventoryHelper.open(player);
        }
        //1 bany, powod
        else if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Ban dla " + offlinePlayer.getName(), 4);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                itemStack.setDurability((short) 15);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            Map<Integer, String> banCache = new HashMap<>();
            banCache.put(10, "Reklama serwera");
            banCache.put(11, "Nwordy");
            banCache.put(12, "Wykorzystywanie bogów servera");
            banCache.put(13, "Rajd");
            banCache.put(14, "Groźby");
            banCache.put(15, "Handel");
            banCache.put(16, "Rozpowszechnianie danych osobowych");
            banCache.put(19, "Omijanie kary");
            banCache.put(20, "Oszustwo administracji");
            banCache.put(21, "Wyzywanie administracji");
            banCache.put(22, "Podszywanie się pod administracje");
            banCache.put(23, "Rasizm");
            banCache.put(24, "Cheaty");
            banCache.put(25, "Makro");

            for (Map.Entry<Integer, String> cachedBan : banCache.entrySet()){
                String reason = cachedBan.getValue();
                int slot = cachedBan.getKey();

                ItemStack mute = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                    itemStack.setDurability((short) 3);
                    inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                        itemMeta.setOwner(offlinePlayer.getName());
                        itemMeta.setDisplayName(Api.fixColor("&e" + reason));
                        itemMeta.setLore(Api.fixColor(Arrays.asList("&r", " &f&nKliknij aby przejsc dalej!")));
                    });
                });

                inventoryHelper.setItem(slot, mute);
            }

            inventoryHelper.click(e -> {
                e.setCancelled(true);

                String reason = banCache.get(e.getSlot());
                if(reason == null || reason.isEmpty()) return;



                if (e.getSlot() == 10) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 11) {
                    player.closeInventory();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 12) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 14) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 15) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 16) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 19) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 20) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 21) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 22) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 23) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 24) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 25) {
                    player.getOpenInventory().close();
                    banGui(reason, player, offlinePlayer);
                }
            });

            inventoryHelper.setItemRange(0, 10, glass_black);
            inventoryHelper.setItemRange(17, 19, glass_black);
            inventoryHelper.setItemRange(26, 36, glass_black);

            inventoryHelper.open(player);
        }
        //2 mute, powod
        else if (guiID == 2) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Wyciszenie dla " + offlinePlayer.getName(), 3);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                itemStack.setDurability((short) 15);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            Map<Integer, String> muteCache = new HashMap<>();
            muteCache.put(10, "Obraza, przeklinanie, wyzywanie");
            muteCache.put(11, "Ośmieszanie, poniżanie");
            muteCache.put(12, "Prowokacja");
            muteCache.put(13, "Tematy tabu");
            muteCache.put(14, "Atencjowanie");
            muteCache.put(15, "Wprowadzenie w błąd");
            muteCache.put(16, "Reklama swojego kanłu YT");

            for (Map.Entry<Integer, String> cachedMute : muteCache.entrySet()){
                String reason = cachedMute.getValue();
                int slot = cachedMute.getKey();

                ItemStack mute = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                    itemStack.setDurability((short) 3);
                    inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                        itemMeta.setOwner(offlinePlayer.getName());
                        itemMeta.setDisplayName(Api.fixColor("&e" + reason));
                        itemMeta.setLore(Api.fixColor(Arrays.asList("&r", " &f&nKliknij aby przejsc dalej!")));
                    });
                });

                inventoryHelper.setItem(slot, mute);
            }

            inventoryHelper.click(e -> {
                e.setCancelled(true);

                String reason = muteCache.get(e.getSlot());
                if(reason == null || reason.isEmpty()) return;

                if (e.getSlot() == 10) {
                    player.getOpenInventory().close();
                    muteGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 11) {
                    player.closeInventory();
                    muteGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 12) {
                    player.getOpenInventory().close();
                    muteGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    muteGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 14) {
                    player.getOpenInventory().close();
                    muteGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 15) {
                    player.getOpenInventory().close();
                    muteGui(reason, player, offlinePlayer);
                } else if (e.getSlot() == 16) {
                    player.getOpenInventory().close();
                    muteGui(reason, player, offlinePlayer);
                }
            });

            inventoryHelper.setItemRange(0, 10, glass_black);
            inventoryHelper.setItemRange(17, 27, glass_black);

            inventoryHelper.open(player);
        }
        //3 kick, powod
        else if (guiID == 3) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Wyrzucenie dla " + offlinePlayer.getName(), 3);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                itemStack.setDurability((short) 15);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            Map<Integer, String> kickCache = new HashMap<>();
            kickCache.put(10, "Reklama serwera");
            kickCache.put(11, "Nwordy");
            kickCache.put(12, "Wykorzystywanie bogów servera");
            kickCache.put(13, "Rajd");
            kickCache.put(14, "Groźby");
            kickCache.put(15, "Handel");
            kickCache.put(16, "Rozpowszechnianie danych osobowych");

            for (Map.Entry<Integer, String> cachedKick : kickCache.entrySet()){
                String reason = cachedKick.getValue();
                int slot = cachedKick.getKey();

                ItemStack mute = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                    itemStack.setDurability((short) 3);
                    inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                        itemMeta.setOwner(offlinePlayer.getName());
                        itemMeta.setDisplayName(Api.fixColor("&e" + reason));
                        itemMeta.setLore(Api.fixColor(Arrays.asList("&r", " &f&nKliknij aby przejsc dalej!")));
                    });
                });

                inventoryHelper.setItem(slot, mute);
            }

            inventoryHelper.click(e -> {
                e.setCancelled(true);

                String reason = kickCache.get(e.getSlot());
                if(reason == null || reason.isEmpty()) return;



                if (e.getSlot() == 10) {
                    player.getOpenInventory().close();
                    player.chat("/kick " + offlinePlayer.getName() + " " + reason);
                    player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason));
                } else if (e.getSlot() == 11) {
                    player.closeInventory();
                    player.chat("/kick " + offlinePlayer.getName() + " " + reason);
                    player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason));
                } else if (e.getSlot() == 12) {
                    player.getOpenInventory().close();
                    player.chat("/kick " + offlinePlayer.getName() + " " + reason);
                    player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason));
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    player.chat("/kick " + offlinePlayer.getName() + " " + reason);
                    player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason));
                } else if (e.getSlot() == 14) {
                    player.getOpenInventory().close();
                    player.chat("/kick " + offlinePlayer.getName() + " " + reason);
                    player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason));
                } else if (e.getSlot() == 15) {
                    player.getOpenInventory().close();
                    player.chat("/kick " + offlinePlayer.getName() + " " + reason);
                    player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason));
                } else if (e.getSlot() == 16) {
                    player.getOpenInventory().close();
                    player.chat("/kick " + offlinePlayer.getName() + " " + reason);
                    player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason));
                }
            });

            inventoryHelper.setItemRange(0, 10, glass_black);
            inventoryHelper.setItemRange(17, 27, glass_black);

            inventoryHelper.open(player);
        }
    }

    public void banGui(String reason, Player player, OfflinePlayer offlinePlayer){
        InventoryHelper inventoryHelper = new InventoryHelper(player, "Ustaw czas", 4);

        ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
            itemStack.setDurability((short) 15);
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                itemMeta.setDisplayName(" ");
            });
        });
        ItemStack glass_gray = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
            itemStack.setDurability((short) 7);
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                itemMeta.setDisplayName(" ");
            });
        });

        Map<Integer, String> timeCache = new HashMap<>();
        timeCache.put(10, "10m");
        timeCache.put(11, "15m");
        timeCache.put(12, "30m");
        timeCache.put(13, "1h");
        timeCache.put(14, "2h");
        timeCache.put(15, "6h");
        timeCache.put(16, "1d");
        timeCache.put(20, "2d");
        timeCache.put(21, "7d");
        timeCache.put(22, "14d");
        timeCache.put(23, "21d");
        timeCache.put(24, "28d");

        for (Map.Entry<Integer, String> cachedMute : timeCache.entrySet()){
            String time = cachedMute.getValue();
            int slot = cachedMute.getKey();

            ItemStack mute = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlN2Q0NjMyMjQ3N2Q2MWQ0MWMxODc4OGY1YzFhZmQyNGVkNTI2ZWIzZWQ4NDEyN2YyMTJlMjUxNWIxODgzIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&e" + time));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("&r", " &f&nKliknij aby zbanować gracza!")));
                });
            });

            inventoryHelper.setItem(slot, mute);
        }
        inventoryHelper.click(e -> {
            e.setCancelled(true);
            if (e.getSlot() == 10) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "10m");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 11) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "15m");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 12) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "30m");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 13) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "1h");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 14) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "2h");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 15) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "6h");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 16) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "1d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 20) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "2d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 21) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "7d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 22) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "14d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 23) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "21d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            } else if (e.getSlot() == 24) {
                player.getOpenInventory().close();
                player.chat("/tempban " + offlinePlayer.getName() + " " + reason + " " + "28d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyrzuciłes gracza &e" + offlinePlayer.getName() + " &aza &e" + reason + " &8<<"));
            }
        });

        inventoryHelper.setItemRange(0, 10, glass_black);
        inventoryHelper.setItemRange(17, 19, glass_black);
        inventoryHelper.setItem(19, glass_gray);
        inventoryHelper.setItem(25, glass_gray);
        inventoryHelper.setItemRange(26, 32, glass_black);
        inventoryHelper.setItemRange(32, 36 , glass_black);

        inventoryHelper.open(player);
    }

    public void muteGui(String reason, Player player, OfflinePlayer offlinePlayer){
        InventoryHelper inventoryHelper = new InventoryHelper(player, "Ustaw czas", 4);

        ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
            itemStack.setDurability((short) 15);
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                itemMeta.setDisplayName(" ");
            });
        });
        ItemStack glass_gray = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
            itemStack.setDurability((short) 7);
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                itemMeta.setDisplayName(" ");
            });
        });
        ItemStack anvil = inventoryHelper.prepareItemStack(Material.ANVIL, itemStack -> {
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                itemMeta.setLore(Api.fixColor(Arrays.asList("&r", " &f&nKliknij aby dodać niestandardowy czas!")));
                itemMeta.setDisplayName(Api.fixColor("&bNiestandardowy czas"));
            });
        });

        Map<Integer, String> timeCache = new HashMap<>();
        timeCache.put(10, "10m");
        timeCache.put(11, "15m");
        timeCache.put(12, "30m");
        timeCache.put(13, "1h");
        timeCache.put(14, "2h");
        timeCache.put(15, "6h");
        timeCache.put(16, "1d");
        timeCache.put(20, "2d");
        timeCache.put(21, "7d");
        timeCache.put(22, "14d");
        timeCache.put(23, "21d");
        timeCache.put(24, "28d");

        for (Map.Entry<Integer, String> cachedMute : timeCache.entrySet()){
            String time = cachedMute.getValue();
            int slot = cachedMute.getKey();

            ItemStack mute = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlN2Q0NjMyMjQ3N2Q2MWQ0MWMxODc4OGY1YzFhZmQyNGVkNTI2ZWIzZWQ4NDEyN2YyMTJlMjUxNWIxODgzIn19fQ==");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&e" + time));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("&r", " &f&nKliknij aby wyciszyć gracza!")));
                });
            });

            inventoryHelper.setItem(slot, mute);
        }
        inventoryHelper.click(e -> {
            e.setCancelled(true);
            if (e.getSlot() == 10) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "10m");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e10m &8<<"));
            } else if (e.getSlot() == 11) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "15m");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e15m &8<<"));
            } else if (e.getSlot() == 12) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "30m");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e30m &8<<"));
            } else if (e.getSlot() == 13) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "1h");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e1h &8<<"));
            } else if (e.getSlot() == 14) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "2h");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e2h &8<<"));
            } else if (e.getSlot() == 15) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "6h");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e6h &8<<"));
            } else if (e.getSlot() == 16) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "1d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e1d &8<<"));
            } else if (e.getSlot() == 20) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "2d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e2d &8<<"));
            } else if (e.getSlot() == 21) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "7d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e7d &8<<"));
            } else if (e.getSlot() == 22) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "14d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e14d &8<<"));
            } else if (e.getSlot() == 23) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "21d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e21d &8<<"));
            } else if (e.getSlot() == 24) {
                player.getOpenInventory().close();
                player.chat("/tempmute " + offlinePlayer.getName() + " " + reason + " " + "28d");
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aPomyślnie wyciszono gracza &e" + offlinePlayer.getName() + " &ana &e28d &8<<"));
            } else if (e.getSlot() == 31) {
                player.getOpenInventory().close();
                custommuteGui(reason, player, offlinePlayer);
            }
        });

        inventoryHelper.setItemRange(0, 10, glass_black);
        inventoryHelper.setItemRange(17, 19, glass_black);
        inventoryHelper.setItem(19, glass_gray);
        inventoryHelper.setItem(25, glass_gray);
        inventoryHelper.setItemRange(26, 31, glass_black);
        inventoryHelper.setItem(31, anvil);
        inventoryHelper.setItemRange(32, 36 , glass_black);

        inventoryHelper.open(player);
    }

    public void custommuteGui(String reason, Player player, OfflinePlayer offlinePlayer){
        InventoryHelper inventoryHelper = new InventoryHelper(player, "Niestandardowe wyciszanie", 4);
        AtomicLong time = new AtomicLong();

        ItemStack black_glass = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
            itemStack.setDurability((short) 15);
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> itemMeta.setDisplayName(" "));
        });

        ItemStack orange_glass = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
            itemStack.setDurability((short) 1);
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> itemMeta.setDisplayName(" "));
        });

        ItemStack arrow_up = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
            itemStack.setDurability((short) 3);
            inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWMyNjg3NmE0NTQ4ODQ0ZTI4YTZmN2JhMWYzNzdjODBlNTk0OTVmN2QzMjIxNGJjYzQ5MjgwNGIxNjYxOTMzOSJ9fX0=");
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                itemMeta.setDisplayName(Api.fixColor("&6Dodaj czas"));
                itemMeta.setLore(Api.fixColor(Arrays.asList(
                        "",
                        " &e+1 min &8- &elpm",
                        " &e+10 min &8- &elpm + shift",
                        " &e+1 godz &8- &eppm",
                        " &e+1 dzien &8- &eppm + shift"
                )));
            });
        });

        ItemStack arrow_down = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
            itemStack.setDurability((short) 3);
            inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U4OTE0ZGZjYThjMjA5NDMxNzVjMDUzMzMxYWUzNzNhNDhhZjQ1ZWQ1YmQxNTdjODk0OTVjYWU0NmVjOTgifX19");
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                itemMeta.setDisplayName(Api.fixColor("&6Obniz czas"));
                itemMeta.setLore(Api.fixColor(Arrays.asList(
                        "",
                        " &e-1 min &8- &elpm",
                        " &e-10 min &8- &elpm + shift",
                        " &e-1 godz &8- &eppm",
                        " &e-1 dzien &8- &eppm + shift"
                )));
            });
        });
        inventoryHelper.setItemRange(0, 4, black_glass);
        inventoryHelper.setItemRange(5, 10, black_glass);
        inventoryHelper.setItemRange(10, 17, orange_glass);
        inventoryHelper.setItemRange(17, 19, black_glass);
        inventoryHelper.setItemRange(19, 21, orange_glass);
        inventoryHelper.setItem(22, orange_glass);
        inventoryHelper.setItemRange(24, 26, orange_glass);
        inventoryHelper.setItemRange(26, 36, black_glass);

        inventoryHelper.setItem(21, arrow_down);
        inventoryHelper.setItem(23, arrow_up);

        inventoryHelper.setItem(4, meta -> {
            meta.setDisplayName(Api.fixColor("&bInformacja"));
            meta.setLore(Api.fixColor(
                    Arrays.asList(
                            "",
                            " &7Gracz &a" + offlinePlayer.getName(),
                            " &7Aktualny czas wyciszenia&8: &anic",
                            "",
                            " &f&nKliknij aby wyciszyć gracza"
                    )
            ));
        }, new ItemStack(Material.ANVIL));

        inventoryHelper.click(event -> {
            event.setCancelled(true);
            if(event.getSlot() == 4){
                if(time.get() <= 10000){
                    Api.sendMessage(player, " &8>> &cCzas musi być większy niż 10 sekund!");
                    return;
                }
                player.chat("/tempmute " + offlinePlayer.getName() + " " + TimeUnit.MILLISECONDS.toSeconds(time.get()) + "s " + reason);
                player.closeInventory();
            } else if (event.getSlot() == 21){
                ClickType clickType = event.getClick();

                if(clickType.isShiftClick() && clickType.isLeftClick())
                    time.addAndGet(-TimeUnit.MINUTES.toMillis(10));
                else if(clickType.isShiftClick() && clickType.isRightClick())
                    time.addAndGet(-TimeUnit.DAYS.toMillis(1));
                else if(clickType.isRightClick())
                    time.addAndGet(-TimeUnit.HOURS.toMillis(1));
                else if(clickType.isLeftClick())
                    time.addAndGet(-TimeUnit.MINUTES.toMillis(1));

                if(time.get() < 0) time.set(0);

                inventoryHelper.setItem(4, meta -> {
                    meta.setDisplayName(Api.fixColor("&bInformacja"));
                    meta.setLore(Api.fixColor(
                            Arrays.asList(
                                    "",
                                    " &7Gracz &a" + offlinePlayer.getName(),
                                    " &7Aktualny czas wyciszenia&8: &a" + (time.get() < 0 ? "nic" : getTime(time.get())),
                                    "",
                                    " &f&nKliknij aby wyciszyć gracza"
                            )
                    ));
                }, new ItemStack(Material.ANVIL));
            } else if (event.getSlot() == 23){
                ClickType clickType = event.getClick();

                if(clickType.isShiftClick() && clickType.isLeftClick())
                    time.addAndGet(TimeUnit.MINUTES.toMillis(10));
                else if(clickType.isShiftClick() && clickType.isRightClick())
                    time.addAndGet(TimeUnit.DAYS.toMillis(1));
                else if(clickType.isRightClick())
                    time.addAndGet(TimeUnit.HOURS.toMillis(1));
                else if(clickType.isLeftClick())
                    time.addAndGet(TimeUnit.MINUTES.toMillis(1));

                if(time.get() < 0) time.set(0);

                inventoryHelper.setItem(4, meta -> {
                    meta.setDisplayName(Api.fixColor("&bInformacja"));
                    meta.setLore(Api.fixColor(
                            Arrays.asList(
                                    "",
                                    " &7Gracz &a" + offlinePlayer.getName(),
                                    " &7Aktualny czas wyciszenia&8: &a" + (time.get() < 0 ? "nic" : getTime(time.get())),
                                    "",
                                    " &f&nKliknij aby wyciszyć gracza"
                            )
                    ));
                }, new ItemStack(Material.ANVIL));
            }
        });
        inventoryHelper.open(player);
    }

    private String getTime(long time){
        StringBuilder builder = new StringBuilder();

        long days = 0;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        days = TimeUnit.MILLISECONDS.toDays(time);
        if(days > 0){
            builder.append(days).append("d");
            time -= TimeUnit.DAYS.toMillis(days);
        }
        hours = TimeUnit.MILLISECONDS.toHours(time);
        if(hours > 0){
            builder.append(" ").append(hours).append("h");
            time -= TimeUnit.HOURS.toMillis(hours);
        }
        minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        if(minutes > 0){
            builder.append(" ").append(minutes).append("min");
            time -= TimeUnit.MINUTES.toMillis(minutes);
        }
        seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        if(seconds > 0){
            builder.append(" ").append(seconds).append("sek");
            time -= TimeUnit.SECONDS.toMillis(seconds);
        }
        if(time > 0){
            builder.append(" ").append(time).append("ms");
        }

        if(builder.toString().isEmpty()) builder.append("1ms");
        String format = builder.toString();
        return format.startsWith(" ") ? format.replaceFirst(" ", "") : format;
    }
}
