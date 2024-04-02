package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Maps;
import de.tr7zw.nbtapi.NBTItem;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.saidora.api.extension.PlayerExtension;
import net.saidora.api.helpers.ItemHelper;
import net.saidora.api.notifications.NotificationBuilder;
import net.saidora.economy.manager.UserManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.objects.Logout;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RegionApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.text.DecimalFormat;
import java.util.*;

import static xyz.dwaslashe.survivalcore.listeners.OthersListener.*;

public class PlayerInteractListener implements Listener {
    public static final Map<UUID, Integer> playerCooldownMap = new HashMap<>();
    protected static final Map<Player, Long> delayModifyRate = Maps.newHashMap();
    protected static final Map<Player, Long> delayHook = Maps.newHashMap();
    protected static final Map<Player, Long> delayKiss = Maps.newHashMap();
    public static Map<String, Integer> loadingProgress = new HashMap<>();
    public static Map<String, Integer> loadingTime = new HashMap<>();
    public static Map<String, ItemStack> itemStackLoading = new HashMap<>();
    public static Map<String, Integer> itemStackAmountLoading = new HashMap<>();
    public static ItemStack cleaningWaterCloth = ItemHelper.edit(new ItemStack(Material.RABBIT_HIDE)).editItemMeta(ItemMeta.class, itemMeta -> {
        itemMeta.setDisplayName(Api.fixColor("&#ffbd52Nasączona ściereczka z wodą"));
        itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Pozwala na mycie brudnej gotówki klikając prawym.", " &#c72810UWAGA! &#fa4125Pamiętaj, że możesz ją użyć tylko raz do prania jednej gotówki!")));
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }).getItemStack();

    private void openGui(int guiID, Player player, Player secondPlayer) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Interakcje " + secondPlayer.getName(), 5);
            User user = UserCache.getInstance().compute(secondPlayer.getUniqueId());

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack previous = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Poprzednia strona"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby przejść do poprzedniej strony!")));
                });
            });

            ItemStack next = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Następna strona"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby przejść do następnej strony!")));
                });
            });

            ItemStack information = inventoryHelper.prepareItemStack(Material.PLAYER_HEAD, itemStack -> {
                inventoryHelper.editSkullMetaForItemStack (itemStack, itemMeta -> {
                    itemMeta.setOwningPlayer(secondPlayer);
                    itemMeta.setDisplayName(Api.fixColor("&#1b9dfaInformacje"));
                    itemMeta.setLore(PlaceholderAPI.setPlaceholders(secondPlayer, Api.fixColor(Arrays.asList(
                            "",
                            " &#E7E7E7Średnia ocena profilu: " + colorAverage(user.getRates()) + "&8/&#54f5425",
                            " &#E7E7E7Twoja ocena profilu: " + colorCountByName(user.getRates(), player.getName()),
                            "",
                            " &#E7E7E7Ping: &#FFC42E" + Api.getPing(secondPlayer) + "ms",
                            " &#E7E7E7Saldo: &#FFF88F%economy_money% &f",
                            " &#E7E7E7Śmierci: &#ff6e6e%statistic_deaths% &#ff4545☠",
                            " &#E7E7E7Zabójstwa: &#4DFFFF%statistic_player_kills% &#1AE6E6⚔",
                            " &#E7E7E7Przegrane godziny: &#ffd56c%statistic_hours_played%g &fᎠ",
                            " &#E7E7E7Wykopane bloki: &#10F70C%statistic_mine_block% &#09b106⛏",
                            " &#E7E7E7Punkty rankingu: &#4eed6e%mineteams_profile_ranking%pkt",
                            " &#E7E7E7Ilość powitanych nowych graczy: &#8eeb6c%Greeter_amount%"
                    ))));
                });
            });

            ItemStack hook = inventoryHelper.prepareItemStack(Material.BELL, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fac21bZaczep"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zaczepić gracza!")));
                });
            });

            ItemStack tradeinvite = inventoryHelper.prepareItemStack(Material.EMERALD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#4efc03Zaproponuj wymiane"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby zaprponować wymiane!")));
                });
            });

            ItemStack sendmsg = inventoryHelper.prepareItemStack(Material.WRITABLE_BOOK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fa7c1bNapisz prywatną wiadomość"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby napisać prywatną wiadomość!")));
                });
            });

            ItemStack rate = inventoryHelper.prepareItemStack(Material.OAK_HANGING_SIGN, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#a1fa1bOceń profil"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Pamiętaj, że oceny profilu nie możesz później zmienić!", "", " &#FBFD8C&nKliknij aby ocenić profil gracza!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 19) {
                    if (delayHook.containsKey(player) && delayHook.get(player) > System.currentTimeMillis()) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby zaczepić gracza musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayHook.get(player))));
                        player.closeInventory();
                        return;
                    }

                    delayHook.remove(player);

                    player.closeInventory();
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zaczepiłeś gracza!");
                    secondPlayer.sendTitle(Api.fixColor("&#95eb34&lHej"), Api.fixColor("&8>> &#4cf739Gracz &#fcb419" + player.getDisplayName() + "&#4cf739 zaczepił Cię!"));
                    secondPlayer.playSound(secondPlayer.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);

                    delayHook.put(player, TimerApi.parseDateDiff("5s", true));
                } else if (e.getSlot() == 21) {
                    player.closeInventory();
                    player.chat("/trade " + secondPlayer.getName());
                } else if (e.getSlot() == 23) {
                    player.closeInventory();
                    NotificationBuilder.of(NotificationBuilder.NotificationType.CHAT,
                            " &8» <hover:show_text:\"<yellow>Kliknij mnie!\"><click:suggest_command:/msg <nick> >&#4cf739Kliknij w wiadomość aby napisać do gracza prywatną wiadomość!</click></hover>"
                    , Placeholder.parsed("nick", secondPlayer.getName())).send(player);
                } else if (e.getSlot() == 25) {
                    openGui(1, player, secondPlayer);
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                } else if (e.getSlot() == 41) {
                    openGui(2, player, secondPlayer);
                }
            });

            inventoryHelper.setItem(4, information);
            inventoryHelper.setItem(19, hook);
            inventoryHelper.setItem(21, tradeinvite);
            inventoryHelper.setItem(23, sendmsg);
            inventoryHelper.setItem(25, rate);

            inventoryHelper.setItem(41, next);
            inventoryHelper.setItem(40, back);


            inventoryHelper.open(player);
        }
        //1
        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Oceń profil", 5);
            User user = UserCache.getInstance().compute(secondPlayer.getUniqueId());

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Wróć"));
                });
            });

            ItemStack information = inventoryHelper.prepareItemStack(Material.PLAYER_HEAD, itemStack -> {
                inventoryHelper.editSkullMetaForItemStack (itemStack, itemMeta -> {
                    itemMeta.setOwningPlayer(secondPlayer);
                    itemMeta.setDisplayName(Api.fixColor("&#1b9dfaInformacje"));
                    itemMeta.setLore(PlaceholderAPI.setPlaceholders(secondPlayer, Api.fixColor(Arrays.asList(
                            "",
                            " &#E7E7E7Średnia ocena profilu: " + colorAverage(user.getRates() + "&8/&#54f5425"),
                            " &#E7E7E7Twoja ocena profilu: " + colorCountByName(user.getRates(), player.getName())
                    ))));
                });
            });

            ItemStack one = inventoryHelper.prepareItemStack(Material.RED_CONCRETE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fa391b1/5"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby ustawić ocene!")));
                });
            });

            ItemStack two = inventoryHelper.prepareItemStack(Material.ORANGE_CONCRETE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fa8b1b2/5"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby ustawić ocene!")));
                });
            });

            ItemStack three = inventoryHelper.prepareItemStack(Material.YELLOW_CONCRETE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fae41b3/5"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby ustawić ocene!")));
                });
            });

            ItemStack four = inventoryHelper.prepareItemStack(Material.GREEN_CONCRETE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#2a82274/5"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby ustawić ocene!")));
                });
            });

            ItemStack five = inventoryHelper.prepareItemStack(Material.LIME_CONCRETE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#24f51d5/5"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby ustawić ocene!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 20) {
                    if (!checkPlayerName(user.getRates(), player.getName())) {
                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                        user.addRates(1 + "-" + player.getName() + "#");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie ustawiono ocene gracza &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4191");
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby zmienić ocene musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "1"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zmieniłeś ocene &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4191");
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 21) {
                    if (!checkPlayerName(user.getRates(), player.getName())) {
                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                        user.addRates(2 + "-" + player.getName() + "#");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie ustawiono ocene gracza &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4192");
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby zmienić ocene musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "2"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zmieniłeś ocene &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4192");
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 22) {
                    if (!checkPlayerName(user.getRates(), player.getName())) {
                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                        user.addRates(3 + "-" + player.getName() + "#");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie ustawiono ocene gracza &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4193");
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby zmienić ocene musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "3"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zmieniłeś ocene &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4193");
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 23) {
                    if (!checkPlayerName(user.getRates(), player.getName())) {
                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                        user.addRates(4 + "-" + player.getName() + "#");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie ustawiono ocene gracza &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4194");
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby zmienić ocene musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "4"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zmieniłeś ocene &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4194");
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 24) {
                    if (!checkPlayerName(user.getRates(), player.getName())) {
                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                        user.addRates(5 + "-" + player.getName() + "#");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie ustawiono ocene gracza &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4195");
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby zmienić ocene musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "5"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zmieniłeś ocene &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739ustawił Ci ocene profilu na &#fcb4195");
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 40) {
                    openGui(0, player, secondPlayer);
                }
            });

            inventoryHelper.setItem(4, information);
            inventoryHelper.setItem(20, one);
            inventoryHelper.setItem(21, two);
            inventoryHelper.setItem(22, three);
            inventoryHelper.setItem(23, four);
            inventoryHelper.setItem(24, five);
            inventoryHelper.setItem(40, back);


            inventoryHelper.open(player);
        }
        //2
        if (guiID == 2) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Interakcje " + secondPlayer.getName(), 5);
            User user = UserCache.getInstance().compute(secondPlayer.getUniqueId());

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack previous = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#39FF14Poprzednia strona"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby przejsc do poprzedniej strony!")));
                });
            });

            ItemStack information = inventoryHelper.prepareItemStack(Material.PLAYER_HEAD, itemStack -> {
                inventoryHelper.editSkullMetaForItemStack (itemStack, itemMeta -> {
                    itemMeta.setOwningPlayer(secondPlayer);
                    itemMeta.setDisplayName(Api.fixColor("&#1b9dfaInformacje"));
                    itemMeta.setLore(PlaceholderAPI.setPlaceholders(secondPlayer, Api.fixColor(Arrays.asList(
                            "",
                            " &#E7E7E7Średnia ocena profilu: " + colorAverage(user.getRates()) + "&8/&#54f5425",
                            " &#E7E7E7Twoja ocena profilu: " + colorCountByName(user.getRates(), player.getName()),
                            "",
                            " &#E7E7E7Saldo: &#FFF88F%economy_money% &f",
                            " &#E7E7E7Śmierci: &#ff6e6e%statistic_deaths% &#ff4545☠",
                            " &#E7E7E7Zabójstwa: &#4DFFFF%statistic_player_kills% &#1AE6E6⚔",
                            " &#E7E7E7Przegrane godziny: &#ffd56c%statistic_hours_played%g &fᎠ",
                            " &#E7E7E7Wykopane bloki: &#10F70C%statistic_mine_block% &#09b106⛏",
                            " &#E7E7E7Punkty rankingu: &#4eed6e%mineteams_profile_ranking%pkt",
                            " &#E7E7E7Ilość powitanych nowych graczy: &#8eeb6c%Greeter_amount%"
                    ))));
                });
            });

            ItemStack kiss = inventoryHelper.prepareItemStack(Material.HEART_POTTERY_SHERD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fa3b2dPocałuj"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby pocałować gracza!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 19) {

                    if (delayKiss.containsKey(player) && delayKiss.get(player) > System.currentTimeMillis()) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby pocałować gracza musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayKiss.get(player))));
                        player.closeInventory();
                        return;
                    }

                    delayKiss.remove(player);

                    if (Api.isNearby(player, secondPlayer, 3)) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wysłano zapytanie o pocałunek! Nie oddalaj się od niego!");
                        openGui(3, secondPlayer, player);
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wysłać mu zapytanie o pocałunek bo nie jesteś w obrębie gracza");

                    player.closeInventory();

                    delayKiss.put(player, TimerApi.parseDateDiff("1m", true));
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                } else if (e.getSlot() == 39) {
                    openGui(0, player, secondPlayer);
                }
            });

            inventoryHelper.setItem(4, information);
            inventoryHelper.setItem(19, kiss);

            inventoryHelper.setItem(39, previous);
            inventoryHelper.setItem(40, back);


            inventoryHelper.open(player);
        }
        //3
        if (guiID == 3) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Pozwolenie o pocałunek od " + secondPlayer.getName(), 3);

            ItemStack yes = inventoryHelper.prepareItemStack(Material.LIME_WOOL, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#4ffa2dTak pozwalam na pocałunek przez &#faa82d" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby pozwolić na pocałunek!")));
                });
            });

            ItemStack no = inventoryHelper.prepareItemStack(Material.RED_WOOL, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fa302dNie pozwalam na pocałunek przez &#faa82d" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby nie pozwolić na pocałunek!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    World world = player.getWorld();

                    if (Api.isNearby(player, secondPlayer, 3)) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie pocałowałeś gracza &#fcb419" + secondPlayer.getName());
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie pocałowałeś gracza &#fcb419" + player.getName());
                        world.spawnParticle(Particle.HEART, player.getLocation().add(0.0D, 2.0D, 0.0D), 10);
                        world.spawnParticle(Particle.HEART, secondPlayer.getLocation().add(0.0D, 2.0D, 0.0D), 10);
                        player.closeInventory();
                        secondPlayer.closeInventory();
                    } else {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Już nie możesz zaakceptować pocałunku bo jesteś za daleko od gracza!");
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Gracz potwierdził pocałunek ale jesteś za daleko od niego!");
                    }
                    player.closeInventory();
                } else if (e.getSlot() == 15) {
                    Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie odrzuciłeś propozycje pocałunku!");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Gracz &#fcb419" + secondPlayer.getName() + "&#fc2419 odrzucił propozycje pocałunku!");
                    player.closeInventory();
                }
            });

            inventoryHelper.setItem(11, yes);
            inventoryHelper.setItem(15, no);

            inventoryHelper.open(player);
        }
        //4
        if (guiID == 4) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Połóż brudną gotówke", 3);

            ItemStack air = inventoryHelper.prepareItemStack(Material.AIR, itemStack -> {
            });

            inventoryHelper.click(e -> {
                if (e.getSlot() == 13) return;
                e.setCancelled(true);
            });

            inventoryHelper.setItem(13, air);

            inventoryHelper.open(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("Połóż brudną gotówke") && event.getSlot() == 13) {
            if (event.getAction().equals(InventoryAction.PLACE_ONE) || event.getAction().equals(InventoryAction.PLACE_ALL) || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                ItemStack currentItem = event.getCursor();

                if (currentItem != null && currentItem.getAmount() > 0) {
                    NBTItem nbtItem = new NBTItem(currentItem);
                    if (nbtItem.hasCustomNbtData()) {
                        if (nbtItem.hasNBTData()) {
                            if (currentItem.getType().equals(Material.MOJANG_BANNER_PATTERN) || currentItem.getType().equals(Material.MAP)) {
                                if (nbtItem.hasKey("dirty-money-value")) {
                                    startLoading(player, currentItem, currentItem.getAmount());
                                    System.out.println("start loading: " + player.getName());
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zacząłeś pranie brudnej gotówki!");
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&4&lUWAGA &#fc2419Wychodząc z serwera pranie zostanie zakończone niepowodzeniem!");
                                    currentItem.setAmount(currentItem.getAmount() - currentItem.getAmount());
                                    player.closeInventory();
                                } else if (currentItem.getItemMeta().getDisplayName().contains("Brudny banknot gotówki")) {
                                    startLoading(player, currentItem, currentItem.getAmount());
                                    System.out.println("start loading second: " + player.getName());
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zacząłeś pranie brudnej gotówki!");
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&4&lUWAGA &#fc2419Wychodząc z serwera pranie zostanie zakończone niepowodzeniem!");
                                    currentItem.setAmount(currentItem.getAmount() - currentItem.getAmount());
                                    player.closeInventory();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void startLoading(Player player, ItemStack itemStack, int amount) {
        if (!loadingProgress.containsKey(player.getName())) {
            loadingProgress.put(player.getName(), 0);
            itemStackLoading.put(player.getName(), itemStack);
            itemStackAmountLoading.put(player.getName(), amount);
            loadingTime.put(player.getName(), 180);
        }
    }

    public static void showLoadingScreen(Player player) {
        int progress = loadingProgress.get(player.getName());
        int time = loadingTime.get(player.getName());

        if (progress > 100 || time <= 0) {
            player.resetTitle();
            loadingProgress.remove(player.getName());
            loadingTime.remove(player.getName());

            if (progress > 100) {
                ItemStack dirtCash = itemStackLoading.get(player.getName());
                float valueMoney = Float.parseFloat(ChatColor.stripColor(dirtCash.getItemMeta().getLore().get(1)).replace('$', ' ').replace("Wartość:", " "));

                System.out.println("[WASHIN DIRTY CASH] Player: " + player.getName() + ", Money: " + valueMoney + ", Amount" + dirtCash.getAmount());
                ItemStack paperCash = makePaper(valueMoney, Arrays.asList(
                        "",
                        " &#E7E7E7Wartość: &#FFF88F" + valueMoney + " &f",
                        " &#E7E7E7Właściciel: &#9DF89F" + player.getName()));
                paperCash.setAmount(itemStackAmountLoading.get(player.getName()));
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Brudna gotówka pomyślnie została wyprana!");
                Api.giveOrDrop(player, paperCash);
                itemStackAmountLoading.remove(player.getName());
                itemStackLoading.remove(player.getName());
            }
            return;
        }

        String title = "&#f58742Mycie gotówki: &#f5da62" + progress + "%";
        String subtitle = getLoadingBar(progress);
        player.sendTitle(Api.fixColor(title), Api.fixColor(subtitle), 0, 20, 0);
        progress++;
        loadingTime.put(player.getName(), time - 1);
        loadingProgress.put(player.getName(), progress);
    }

    public static String getLoadingBar(int progress) {
        int bars = progress / 5;
        int spaces = 20 - bars;

        StringBuilder loadingBar = new StringBuilder();
        for (int i = 0; i < bars; i++) {
            loadingBar.append("&#41FB07█");
        }
        for (int i = 0; i < spaces; i++) {
            loadingBar.append("&#FA2D1E█");
        }

        return Api.fixColor(loadingBar.toString());
    }

    public static String colorAverage(String input) {
        if(input == null) return Api.fixColor("<#03cafc>0");
        double average = calculateAverage(input);
        if (average <= 1.5) {
            return "<gradient:#fa391b:#d11e02>" + average  + "</gradient>";
        } else if (average > 1.5 && average <= 2.5) {
            return "<gradient:#fa8b1b:#e67402>" + average + "</gradient>";
        } else if (average > 2.5 && average <= 3.5) {
            return "<gradient:#fae41b:#ebd405>" + average + "</gradient>";
        } else if (average > 3.5 && average <= 4.5) {
            return "<gradient:#2a8227:#144d12>" + average + "</gradient>";
        } else if (average > 4.5 && average <= 5.0) {
            return "<gradient:#24f51d:#0bfc03>" + average + "</gradient>";
        }
        return Api.fixColor("<#03cafc>0");
    }

    public static String colorCountByName(String input, String name) {
        String count = getValue(input, name);
        if (count == null) {
            return Api.fixColor("&#fc3d1cBrak");
        }

        if (count.equals("1")) {
            return Api.fixColor("<gradient:#fa391b:#d11e02>" + count  + "</gradient>");
        } else if (count.equals("2")) {
            return Api.fixColor("<gradient:#fa8b1b:#e67402>" + count + "</gradient>");
        } else if (count.equals("3")) {
            return Api.fixColor("<gradient:#fae41b:#ebd405>" + count + "</gradient>");
        } else if (count.equals("4")) {
            return Api.fixColor("<gradient:#2a8227:#144d12>" + count + "</gradient>");
        } else if (count.equals("5")) {
            return Api.fixColor("<gradient:#24f51d:#0bfc03>" + count + "</gradient>");
        }
        return Api.fixColor("&#fc3d1cBrak");
    }

    public static String replaceNumber(String input, String nickName, String number) {
        String[] parts = input.split("#");

        for (int i = 0; i < parts.length; i++) {
            String[] subParts = parts[i].split("-");
            if (subParts.length == 2 && subParts[1].equals(nickName)) {
                parts[i] = number + "-" + subParts[1];
            }
        }

        return String.join("#", parts) + "#";
    }

    public static String getValue(String input, String nickName) {
        String[] tokens = input.split("#");

        for (String token : tokens) {
            if (token.endsWith("-" + nickName)) {
                int index = token.lastIndexOf("-");
                return token.substring(0, index);
            }
        }

        return null;
    }

    public static double calculateAverage(String input) {
        String[] tokens = input.split("#");
        int sum = 0;
        int count = 0;

        for (String token : tokens) {
            String[] parts = token.split("-");
            if (parts.length == 2) {
                try {
                    int number = Integer.parseInt(parts[0]);
                    sum += number;
                    count++;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        if (count > 0) {
            return (double) sum / count;
        } else {
            return 0.0;
        }
    }

    public static boolean checkPlayerName(String input, String name) {
        String[] tokens = input.split("#");

        for (String token : tokens) {
            String[] parts = token.split("-");
            if (parts.length == 2 && parts[1].equals(name)) {
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && event.getRightClicked() instanceof Player) {
            Player target = (Player) event.getRightClicked();
            Logout logout = Logout.get(player);
            if (logout.getTime() > System.currentTimeMillis()) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz tego robić podczas walki!");

            } else openGui(0, player, target);
        }
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        int timeCooldownPearl = 20;
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();

        if (event.getAction() != null && event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (itemInHand.isSimilar(cleaningWaterCloth)) {
                if (!loadingProgress.containsKey(player.getName())) {
                    openGui(4, player, player);

                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Twoja ścierka nasączona wodą została zniszczona ponieważ zacząłeś ją używać!");
                    itemInHand.setAmount(itemInHand.getAmount() - 1);
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);

                    event.setCancelled(true);
                    event.setUseInteractedBlock(Event.Result.DENY);
                    event.setUseItemInHand(Event.Result.DENY);
                } else {
                    event.setCancelled(true);
                    event.setUseInteractedBlock(Event.Result.DENY);
                    event.setUseItemInHand(Event.Result.DENY);
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz prać kolejnej brudnej gotówki jak jesteś w trakcie prania!");
                }
            }
        }

        if (event.getClickedBlock() == null && event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (itemInHand.getType().equals(Material.SNOWBALL) && itemInHand.getItemMeta().hasDisplayName() && itemInHand.isSimilar(pokeBall)) {
                CustomItemsListener.shooters.add(player.getUniqueId());
                return;
            }
        }

        if (!player.hasPermission("core.cooldown.enderpearl.use.bypass")) {
            if (Main.pluginConfig.getEvents().isEnderPearlCooldown() && event.getMaterial() == Material.ENDER_PEARL && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                if (isPlayerInCooldown(player) && getTimeRemaining(player).intValue() < timeCooldownPearl) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Perły kolejny raz możesz użyć za &#ffd56c" + getTimeRemaining(player) + "sek &fᎠ");
                    event.setCancelled(true);
                } else {
                    addPlayerToMap(player, Integer.valueOf(timeCooldownPearl));
                }
            }
        }

        if (event.getMaterial() == Material.PAPER && (event.getAction().isRightClick() || event.getAction().isLeftClick())) {
            ItemHelper itemHelper = ItemHelper.edit(itemInHand);
            itemHelper.editNbtTagCompound(nbtItem -> {
                if (nbtItem.hasKey("money-value")) {
                    System.out.println("[NIELEGALNA GOTOWKA] Gracz " + player.getName() + " posiadal nielegalna gotowka i chcial wplacic!");
                    player.getItemInHand().setAmount((player.getItemInHand().getAmount() - 1));
                }
            });
        }


        if (!RegionApi.isInRegion(player.getLocation(), "pvp")) {
            if (event.getMaterial() == Material.PAPER && event.getAction().equals(Action.RIGHT_CLICK_AIR) && Objects.equals(event.getHand(), EquipmentSlot.HAND)) {
                ItemHelper itemHelper = ItemHelper.edit(itemInHand);
                DecimalFormat decimalFormat = new DecimalFormat("##.####");
                itemHelper.editNbtTagCompound(nbtItem -> {
                    if (nbtItem.hasKey("withdraw-money")) {
                        double value = nbtItem.getDouble("withdraw-money");
                        UserManager.getInstance().getUser(player).ifPresent(user -> user.deposit(value));
                        Main.getPlugin().getLogger().info(Api.fixColor("[WITHDRAW] &#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739wplacil banknot o wartosci: &#fcb419$" + decimalFormat.format(value)));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wpłaciłeś na konto &#FFF88F" + decimalFormat.format(value) + " &f");
                        player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                    }
                    //    itemHelper.editItemMeta(ItemMeta.class, itemMeta -> {
                    //        if (itemMeta.getDisplayName().contains("Banknot gotówki")) {
                    //            if (!itemInHand.getItemMeta().hasLore()) {
                    //                return;
                    //            }
                    //            float amount = Float.parseFloat(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(1)).replace('$', ' ').replace("Wartość:", " "));
                    //            UserManager.getInstance().getUser(event.getPlayer()).ifPresent(user -> user.deposit(amount));
                    //            Main.getPlugin().getLogger().info(Api.fixColor("[WITHDRAW] &#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739wplacil banknot o wartosci: &#fcb419$" + amount));
                    //            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wpłaciłeś na konto &#FFF88F" + amount + " &f");
                    //            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                    //        }
                    //    });
                    //}
                });
            } else if ((event.getMaterial() == Material.MAP || event.getMaterial() == Material.MOJANG_BANNER_PATTERN) && Objects.equals(event.getHand(), EquipmentSlot.HAND) && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                ItemHelper itemHelper = ItemHelper.edit(itemInHand);
                itemHelper.editNbtTagCompound(nbtItem -> {
                    if (nbtItem.hasKey("dirty-money-value")) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz użyć brudnej gotówki! Musisz ją wyprać za pomocą przedmiotu nasączonej wody ścieraczki! Więcej o tym przedmiocie znajdziesz na naszej stronie: &#ffa41chttps://wiki.wywrotkamc.pl/pl/survivaldzialki/narkotyki");
                        event.setUseItemInHand(Event.Result.DENY);
                        event.setCancelled(true);
                        event.setUseItemInHand(Event.Result.DENY);
                    } else {
                        itemHelper.editItemMeta(ItemMeta.class, itemMeta -> {
                            if (itemMeta.getDisplayName().contains("Brudny banknot gotówki")) {
                                if (!itemInHand.getItemMeta().hasLore()) {
                                    return;
                                }
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz użyć brudnej gotówki! Musisz ją wyprać za pomocą przedmiotu nasączonej wody ścieraczki! Więcej o tym przedmiocie znajdziesz na naszej stronie: &#ffa41chttps://wiki.wywrotkamc.pl/pl/survivaldzialki/narkotyki");
                                event.setUseItemInHand(Event.Result.DENY);
                                event.setCancelled(true);
                                event.setUseItemInHand(Event.Result.DENY);
                            }
                        });
                    }
                });
            } else if (event.getMaterial() == Material.EXPERIENCE_BOTTLE && event.getAction().isRightClick() && Objects.equals(event.getHand(), EquipmentSlot.HAND)) {
                ItemHelper itemHelper = ItemHelper.edit(itemInHand);
                itemHelper.editNbtTagCompound(nbtItem -> {
                    if (nbtItem.hasKey("exp-value")) {
                        int value = nbtItem.getInteger("exp-value");
                        PlayerExtension.getPlayerExtend(player, extension -> extension.addExperience(value));
                        Main.getPlugin().getLogger().info(Api.fixColor("[XPBOTTLE] &#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739wplacil butelke doswiadczenie wartosci EXP: &#fcb419" + value));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie otrzymałeś &2" + value + " EXP");
                        player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                        event.setCancelled(true);
                        event.setUseInteractedBlock(Event.Result.DENY);
                        event.setUseItemInHand(Event.Result.DENY);
                    } else {
                        itemHelper.editItemMeta(ItemMeta.class, itemMeta -> {
                            if (itemMeta.getDisplayName().contains("Butelka doświadczenia")) {
                                if (!itemInHand.getItemMeta().hasLore()) {
                                    return;
                                }

                                int amount = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(1)).replace("EXP", " ").replace("Doświadczenie:", " ").replace(" ", ""));
                                PlayerExtension.getPlayerExtend(player, extension -> {
                                    extension.addExperience(amount);
                                });
                                Main.getPlugin().getLogger().info(Api.fixColor("[XPBOTTLE] &#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739wplacil butelke doswiadczenie wartosci EXP: &#fcb419" + amount));
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie otrzymałeś &2" + amount + " EXP");
                                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                                event.setCancelled(true);
                                event.setUseInteractedBlock(Event.Result.DENY);
                                event.setUseItemInHand(Event.Result.DENY);
                            }
                        });
                    }
                });
            } else if (event.getAction() == Action.RIGHT_CLICK_AIR && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (Main.pluginConfig.getEvents().isNoBedExplose()) {
                    if (event.getClickedBlock().toString().toLowerCase().contains("BED")) {
                        event.setUseInteractedBlock(Event.Result.DENY);
                        event.setCancelled(true);
                    }
                }
                if (Main.pluginConfig.getEvents().isKelpSmoke()) {
                    if (event.getClickedBlock().getType().equals(Material.DRIED_KELP_BLOCK)) {
                        if (event.getItem().getType() != Material.FLINT_AND_STEEL) return;
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 150, 50));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, -50));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
                    }
                }
            }
        } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz tego używać podczas duela!");
    }

    public void EnderpearlCooldown(Main plugin) {
        (new BukkitRunnable() {
            public void run() {
                for (UUID uuid : playerCooldownMap.keySet()) {
                    if (playerCooldownMap.get(uuid) == 1) {
                        playerCooldownMap.remove(uuid);
                        if (Bukkit.getPlayer(uuid) != null)
                            Bukkit.getPlayer(uuid).sendMessage(Api.fixColor(Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Możesz użyć perły!"));
                        continue;
                    }
                    playerCooldownMap.put(uuid, playerCooldownMap.get(uuid) - 1);
                }
            }
        }).runTaskTimer(plugin, 0L, 20L);
    }

    private Integer getTimeRemaining(Player p) {
        if (!isPlayerInCooldown(p))
            return 0;
        return playerCooldownMap.get(p.getUniqueId());
    }

    private void addPlayerToMap(Player p, Integer time) {
        this.playerCooldownMap.put(p.getUniqueId(), time);
    }

    private boolean isPlayerInCooldown(Player p) {
        return this.playerCooldownMap.containsKey(p.getUniqueId());
    }

    public static ItemStack makePaper(double value, List<String> lore) {
        return ItemHelper.edit(new ItemStack(Material.PAPER)).editNbtTagCompound(nbtItem -> {
            nbtItem.setDouble("withdraw-money",  value);
        }).editItemMeta(ItemMeta.class, itemMeta -> {
            itemMeta.setDisplayName(Api.fixColor("&#3dfc49Banknot gotówki"));
            itemMeta.setLore(Api.fixColor(lore));
            itemMeta.addEnchant(Enchantment.DURABILITY, 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }).getItemStack();
    }

    public static ItemStack makeBottle(int value, List<String> lore) {
        return ItemHelper.edit(new ItemStack(Material.EXPERIENCE_BOTTLE)).editNbtTagCompound(nbtItem -> {
            nbtItem.setInteger("exp-value", value);
        }).editItemMeta(ItemMeta.class, itemMeta -> {
            itemMeta.setDisplayName(Api.fixColor("&#da42f5Butelka doświadczenia"));
            itemMeta.setLore(Api.fixColor(lore));
        }).getItemStack();
    }
}
