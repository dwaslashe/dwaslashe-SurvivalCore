package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Maps;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.saidora.api.extension.PlayerExtension;
import net.saidora.api.notifications.NotificationBuilder;
import net.saidora.economy.manager.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.*;

import static xyz.dwaslashe.survivalcore.listeners.OthersListener.*;

public class PlayerInteractListener implements Listener {

    public static final Map<UUID, Integer> playerCooldownMap = new HashMap<>();

    protected static final Map<Player, Long> delayModifyRate = Maps.newHashMap();

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && event.getRightClicked() instanceof Player) {
            Player target = (Player) event.getRightClicked();
            openGui(0, player, target);
        }
    }

    private void openGui(int guiID, Player player, Player secondPlayer) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Interakcje " + secondPlayer.getName(), 5);
            User user = UserCache.getInstance().compute(secondPlayer.getUniqueId());

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

            ItemStack information = inventoryHelper.prepareItemStack(Material.PLAYER_HEAD, itemStack -> {
                inventoryHelper.editSkullMetaForItemStack (itemStack, itemMeta -> {
                    itemMeta.setOwningPlayer(secondPlayer);
                    itemMeta.setDisplayName(Api.fixColor("&#1b9dfaInformacje"));
                    itemMeta.setLore(PlaceholderAPI.setPlaceholders(secondPlayer, Api.fixColor(Arrays.asList(
                            "",
                            " &#E7E7E7Średnia ocena profilu: " + colorAverage(user.getRates()) + "&8/&#54f5425",
                            "",
                            " &#E7E7E7Saldo: &#FFF88F%economy_money% &#FFC42E$",
                            " &#E7E7E7Śmierci: &#ff6e6e%statistic_deaths% &#ff4545☠",
                            " &#E7E7E7Zabójstwa: &#4DFFFF%statistic_player_kills% &#1AE6E6⚔",
                            " &#E7E7E7Przegrane godziny: &#ffd56c%statistic_hours_played%g &#ffc942⌚",
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
                    player.closeInventory();
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zaczepiłeś gracza!");
                    secondPlayer.sendTitle(Api.fixColor("&#95eb34&lHej"), Api.fixColor("&8>> &aGracz &e" + player.getDisplayName() + "&a zaczepił Cię!"));
                    secondPlayer.playSound(secondPlayer.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
                } else if (e.getSlot() == 21) {
                    player.closeInventory();
                    player.chat("/trade " + secondPlayer.getName());
                } else if (e.getSlot() == 23) {
                    player.closeInventory();
                    NotificationBuilder.of(NotificationBuilder.NotificationType.CHAT,
                            " &8» <hover:show_text:\"<yellow>Kliknij mnie!\"><click:suggest_command:/msg <nick> >&aKliknij w wiadomość aby napisać do gracza prywatną wiadomość!</click></hover>"
                    , Placeholder.parsed("nick", secondPlayer.getName())).send(player);
                } else if (e.getSlot() == 25) {
                    openGui(1, player, secondPlayer);
                } else if (e.getSlot() == 40) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);

            inventoryHelper.setItem(4, information);
            inventoryHelper.setItem(19, hook);
            inventoryHelper.setItem(21, tradeinvite);
            inventoryHelper.setItem(23, sendmsg);
            inventoryHelper.setItem(25, rate);
            inventoryHelper.setItem(40, back);


            inventoryHelper.open(player);
        }
        //1
        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Oceń profil", 5);
            User user = UserCache.getInstance().compute(secondPlayer.getUniqueId());

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

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
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie ustawiono ocene gracza &e" + secondPlayer.getName());
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cAby zmienić ocene musisz poczekać &e{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "1"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś ocene &e" + secondPlayer.getName());
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 21) {
                    if (!checkPlayerName(user.getRates(), player.getName())) {
                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                        user.addRates(2 + "-" + player.getName() + "#");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie ustawiono ocene gracza &e" + secondPlayer.getName());
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cAby zmienić ocene musisz poczekać &e{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "2"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś ocene &e" + secondPlayer.getName());
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 22) {
                    if (!checkPlayerName(user.getRates(), player.getName())) {
                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                        user.addRates(3 + "-" + player.getName() + "#");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie ustawiono ocene gracza &e" + secondPlayer.getName());
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cAby zmienić ocene musisz poczekać &e{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "3"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś ocene &e" + secondPlayer.getName());
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 23) {
                    if (!checkPlayerName(user.getRates(), player.getName())) {
                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                        user.addRates(4 + "-" + player.getName() + "#");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie ustawiono ocene gracza &e" + secondPlayer.getName());
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cAby zmienić ocene musisz poczekać &e{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "4"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś ocene &e" + secondPlayer.getName());
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 24) {
                    if (!checkPlayerName(user.getRates(), player.getName())) {
                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                        user.addRates(5 + "-" + player.getName() + "#");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie ustawiono ocene gracza &e" + secondPlayer.getName());
                        player.closeInventory();
                    } else {
                        if (delayModifyRate.containsKey(player) && delayModifyRate.get(player) > System.currentTimeMillis()) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cAby zmienić ocene musisz poczekać &e{TIME}".replace("{TIME}", TimerApi.secondsToString(delayModifyRate.get(player))));
                            player.closeInventory();
                            return;
                        }

                        delayModifyRate.remove(player);

                        user.setRates(replaceNumber(user.getRates(), player.getName(), "5"));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zmieniłeś ocene &e" + secondPlayer.getName());
                        player.closeInventory();

                        delayModifyRate.put(player, TimerApi.parseDateDiff("12h", true));
                    }
                } else if (e.getSlot() == 40) {
                    openGui(0, player, secondPlayer);
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);

            inventoryHelper.setItem(4, information);
            inventoryHelper.setItem(20, one);
            inventoryHelper.setItem(21, two);
            inventoryHelper.setItem(22, three);
            inventoryHelper.setItem(23, four);
            inventoryHelper.setItem(24, five);
            inventoryHelper.setItem(40, back);


            inventoryHelper.open(player);
        }
    }

    public static String colorAverage(String input) {
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
    private void onPlayerInteract(PlayerInteractEvent event) {
        int timeCooldownPearl = 5;
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();

        if (!player.hasPermission("core.cooldown.enderpearl.use.bypass")) {
            if (Main.pluginConfig.getEvents().isEnderpearlcooldown() && event.getMaterial() == Material.ENDER_PEARL && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                if (isPlayerInCooldown(player) && getTimeRemaining(player).intValue() < timeCooldownPearl) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cPerły kolejny raz możesz użyć za &e" + getTimeRemaining(player) + "sek");
                    event.setCancelled(true);
                } else {
                    addPlayerToMap(player, Integer.valueOf(timeCooldownPearl));
                }
            }
        }

        if (event.getMaterial() == Material.PAPER) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (event.getHand().equals(EquipmentSlot.HAND)) {
                    if (itemInHand.getItemMeta().getDisplayName().contains("Banknot gotówki")) {
                        if (itemInHand.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) == 11) {
                            if (!itemInHand.getItemMeta().hasLore()) {
                                return;
                            }
                            float amount = Float.parseFloat(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(1)).replace('$', ' ').replace("Wartość:", " "));
                            UserManager.getInstance().getUser(event.getPlayer()).ifPresent(user -> {
                                user.deposit(amount);
                            });
                            System.out.println(Api.fixColor("[WITHDRAW] &aGracz &e" + player.getName() + " &awplacil banknot o wartosci: &e$" + amount));
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wpłaciłeś na konto &#FFF88F" + amount + " &#FFC42E$");
                            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                        } else if (itemInHand.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) == 10) {
                            float amount = Float.parseFloat(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(1)).replace('$', ' ').replace("Wartość:", " "));
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cUżywasz starą wersję gotówki, która jest nie ważna usuń ją pod komendą &e/kosz");
                            System.out.println(Api.fixColor("[WITHDRAW-EXPIRE] &cGracz &e" + player.getName() + " &cuzywa stara wersje banknotu o wartosci: &e$" + amount));
                        }
                    }
                }
            }
        } else if (event.getMaterial() == Material.EXPERIENCE_BOTTLE) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (event.getHand().equals(EquipmentSlot.HAND)) {
                    if (itemInHand.getItemMeta().getDisplayName().contains("Butelka doświadczenia")) {
                        if (itemInHand.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) == 10) {
                            if (!itemInHand.getItemMeta().hasLore()) {
                                return;
                            }

                            int amount = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(1)).replace("EXP", " ").replace("Doświadczenie:", " "). replace(" ", ""));
                            PlayerExtension.getPlayerExtend(player, extension -> {
                                extension.addExperience(amount);
                            });
                            System.out.println(Api.fixColor("[XPBOTTLE] &aGracz &e" + player.getName() + " &awplacil butelke doswiadczenie wartosci EXP: &e" + amount));
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie otrzymałeś &#aa42f5" + amount + " EXP");
                            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                            event.setCancelled(true);
                            event.setUseInteractedBlock(Event.Result.DENY);
                            event.setUseItemInHand(Event.Result.DENY);
                        }
                    }
                }
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getHand().equals(EquipmentSlot.HAND)) {
                    if (itemInHand.getItemMeta().getDisplayName().contains("Butelka doświadczenia")) {
                        if (itemInHand.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) == 10) {
                            if (!itemInHand.getItemMeta().hasLore()) {
                                return;
                            }

                            int amount = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(1)).replace("EXP", " ").replace("Doświadczenie:", " "). replace(" ", ""));
                            PlayerExtension.getPlayerExtend(player, extension -> {
                                extension.addExperience(amount);
                            });
                            System.out.println(Api.fixColor("[XPBOTTLE] &aGracz &e" + player.getName() + " &awplacil butelke doswiadczenie wartosci EXP: &e" + amount));
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie otrzymałeś &#aa42f5" + amount + " EXP");
                            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                            event.setCancelled(true);
                            event.setUseInteractedBlock(Event.Result.DENY);
                            event.setUseItemInHand(Event.Result.DENY);
                        }
                    }
                }
            } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (event.getMaterial().equals(Material.LARGE_FERN)) {
                    if (itemInHand.isSimilar(weed)) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zapaliłeś &ezioło");
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 550, 50));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 500, -50));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 500, 2));
                        itemInHand.setAmount(itemInHand.getAmount() - 1);
                    }
                } else if (event.getMaterial().equals(Material.CLAY_BALL)) {
                    if (itemInHand.isSimilar(kokaina)) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wciągnąłeś &ekokaine");
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1050, 50));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000, -50));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1500, 2));
                        itemInHand.setAmount(itemInHand.getAmount() - 1);
                    }
                }
            } else if (event.getAction() == Action.RIGHT_CLICK_AIR && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (Main.pluginConfig.getEvents().isNobedexplose()) {
                    if (event.getClickedBlock().toString().toLowerCase().contains("BED")) {
                        event.setUseInteractedBlock(Event.Result.DENY);
                        event.setCancelled(true);
                    }
                }
                if (Main.pluginConfig.getEvents().isKelpsmoke()) {
                    if (event.getClickedBlock().getType().equals(Material.DRIED_KELP_BLOCK)) {
                        if (event.getItem().getType() != Material.FLINT_AND_STEEL) return;
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 150, 50));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, -50));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
                    }
                }
            } else if (event.getClickedBlock() == null && event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (itemInHand.getType().equals(Material.SNOWBALL) && itemInHand.getItemMeta().hasDisplayName() && itemInHand.isSimilar(pokeball)) {
                    shooters.add(player.getUniqueId());
                }
            }
        }
    }

    public void EnderpearlCooldown(Main plugin) {
        (new BukkitRunnable() {
            public void run() {
                for (UUID uuid : playerCooldownMap.keySet()) {
                    if (((Integer) playerCooldownMap.get(uuid)).intValue() == 1) {
                        playerCooldownMap.remove(uuid);
                        if (Bukkit.getPlayer(uuid) != null)
                            Bukkit.getPlayer(uuid).sendMessage(Api.fixColor(Main.pluginConfig.getMessages().getPrefix() + "&aMożesz użyć perły!"));
                        continue;
                    }
                    playerCooldownMap.put(uuid, Integer.valueOf(((Integer) playerCooldownMap.get(uuid)).intValue() - 1));
                }
            }
        }).runTaskTimer((Plugin) plugin, 0L, 20L);
    }

    private Integer getTimeRemaining(Player p) {
        if (!isPlayerInCooldown(p))
            return Integer.valueOf(0);
        return playerCooldownMap.get(p.getUniqueId());
    }

    private void addPlayerToMap(Player p, Integer time) {
        this.playerCooldownMap.put(p.getUniqueId(), time);
    }

    private boolean isPlayerInCooldown(Player p) {
        return this.playerCooldownMap.containsKey(p.getUniqueId());
    }

    public static ItemStack makePaper(List<String> lore) {
        ItemStack cash = new ItemApi(Material.PAPER)
                .setName("&#3dfc49Banknot gotówki")
                .addEnchant(Enchantment.DURABILITY, 11)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .setLore(Api.fixColor(lore))
                .getItemStack();
        return cash;
    }

    public static ItemStack makeBottle(List<String> lore) {
        ItemStack xpbottle = new ItemApi(Material.EXPERIENCE_BOTTLE)
                .setName("&#da42f5Butelka doświadczenia")
                .addEnchant(Enchantment.DURABILITY, 10)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .setLore(Api.fixColor(lore))
                .getItemStack();
        return xpbottle;
    }
}
