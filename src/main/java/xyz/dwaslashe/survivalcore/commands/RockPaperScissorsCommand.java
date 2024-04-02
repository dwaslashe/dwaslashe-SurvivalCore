package xyz.dwaslashe.survivalcore.commands;

import net.saidora.api.notifications.NotificationBuilder;
import net.saidora.economy.manager.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.objects.Logout;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RandomApi;

import java.util.*;

import static xyz.dwaslashe.survivalcore.Main.plugin;

public class RockPaperScissorsCommand extends Command implements Listener {
    public RockPaperScissorsCommand() {
        super("rockpaperscissors", "/kamienpapiernozyczki <gracz> <zakład(max 50000, min 1000), akceptuj, odmow>", "", "kamienpapiernozyczki", "kpn");
        setPermission("core.command.rockpaperscissors");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        else if (args.length == 2) return Api.startsWith(Arrays.asList("1000", "2000", "5000"), args[1]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (Main.pluginConfig.getCommands().isRockPaperScissors()) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("akceptuj") || args[0].equalsIgnoreCase("accept")) {
                    acceptGameRequest(player);
                    return;
                } else if (args[0].equalsIgnoreCase("odmow") || args[0].equalsIgnoreCase("deny")) {
                    if (gameRequests.containsKey(player)) {
                        gameRequests.remove(player);
                        gameRequests.remove(gameRequests.get(player));
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie odrzuciłeś propozycje gry!");
                    } else
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie masz żadnych oczekujących zaproszeń do gry!");
                    return;
                }
                if (args.length >= 2) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && target != player) {
                        if (Api.isInt(args[1])) {
                            if (Integer.valueOf(args[1]) <= 50000) {
                                if (1000 <= Integer.valueOf(args[1])) {
                                    UserManager.getInstance().getUser(player).ifPresent(user -> {
                                        UserManager.getInstance().getUser(target).ifPresent(targetUser -> {
                                            double balanceUser = user.balance();
                                            double balanceTargetUser = targetUser.balance();

                                            if (balanceUser >= Integer.valueOf(args[1])) {
                                                if (balanceTargetUser >= Integer.valueOf(args[1])) {
                                                    if (!hasSentGameRequest(player, target)) {
                                                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wysłano zaproszenie do gry dla &#fcb419" + target.getName() + "&#4cf739, ma &#ffd56c60 sekund &fᎠ &#4cf739aby potwierdzić zaproszenie!");
                                                        sendGameRequest(player, target, Integer.valueOf(args[1]));
                                                    }
                                                } else
                                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Gracz zapraszany do pojedynku nie ma tyle pieniędzy na taki zakład!");
                                            } else
                                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz tyle pieniędzy aby zrobić taki zakład!");
                                        });
                                    });
                                } else wrongUsage();
                            } else wrongUsage();
                        } else wrongUsage();
                    } else offlinePlayer();
                } else wrongUsage();
            } else wrongUsage();

        } else Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Komenda została wyłączona!");
    }

    public static void openGui(int guiID, Player player, Player secondPlayer, int betAmount) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Masz na to 60 sekund! ", 3);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack rock = inventoryHelper.prepareItemStack(Material.COBBLESTONE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#34b4ebKamień"));
                });
            });

            ItemStack paper = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#8ceb34Papier"));
                });
            });

            ItemStack scissors = inventoryHelper.prepareItemStack(Material.SHEARS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#eb6b34Nożyczki"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    choseTypeGame.put(player, "ROCK");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrałeś &#fcb419kamień!");
                    sendChoseTypeGame(player, secondPlayer);
                    System.out.println("player: " + player.getName());
                    System.out.println("secondPlayer: " + secondPlayer.getName());
                    System.out.println("bool: " + choseTypeRequests.containsKey(player) + ", bool2: " + (choseTypeRequests.get(player) == secondPlayer));
                    choseTypeRequests.remove(player);
                    choseTypeRequests.remove(player, secondPlayer);
                    //choseTypeRequests.remove(secondPlayer, player);
                    //choseTypeRequests.remove(secondPlayer);
                } else if (e.getSlot() == 13) {
                    player.closeInventory();
                    choseTypeGame.put(player, "PAPER");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrałeś &#fcb419papier!");
                    sendChoseTypeGame(player, secondPlayer);
                    choseTypeRequests.remove(player);
                    choseTypeRequests.remove(player, secondPlayer);
                    //choseTypeRequests.remove(secondPlayer, player);
                    //choseTypeRequests.remove(secondPlayer);
                } else if (e.getSlot() == 15) {
                    player.closeInventory();
                    choseTypeGame.put(player, "SCISSORS");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wybrałeś &#fcb419nożyczki!");
                    sendChoseTypeGame(player, secondPlayer);
                    choseTypeRequests.remove(player);
                    choseTypeRequests.remove(player, secondPlayer);
                    //choseTypeRequests.remove(secondPlayer, player);
                    //choseTypeRequests.remove(secondPlayer);
                }
            });

            inventoryHelper.setItemRange(0, 11, glass_black);
            inventoryHelper.setItem(11, rock);
            inventoryHelper.setItem(12, glass_black);
            inventoryHelper.setItem(13, paper);
            inventoryHelper.setItem(14, glass_black);
            inventoryHelper.setItem(15, scissors);
            inventoryHelper.setItemRange(16, 27, glass_black);

            inventoryHelper.open(player);
        }
    }

    public static void openResultGui(int guiID, Player player, Player winner, Player loser, String winnerType, String loserType) {
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Wygrał: " + winner.getName(), 5);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack glass_lime = inventoryHelper.prepareItemStack(Material.LIME_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#53f51dWYGRANY"));
                });
            });

            ItemStack glass_red = inventoryHelper.prepareItemStack(Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#f51d11PRZEGRANY"));
                });
            });

            ItemStack rock = inventoryHelper.prepareItemStack(Material.COBBLESTONE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#34b4ebKamień"));
                });
            });

            ItemStack paper = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#8ceb34Papier"));
                });
            });

            ItemStack scissors = inventoryHelper.prepareItemStack(Material.SHEARS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#eb6b34Nożyczki"));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack winnerPlayerHead = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(winner.getName());
                    itemMeta.setDisplayName(Api.fixColor("&#53f51d" + winner.getName()));
                });
            });

            ItemStack loserPlayerHead = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(loser.getName());
                    itemMeta.setDisplayName(Api.fixColor("&#f51d11" + loser.getName()));
                });
            });

            ItemStack arrowWinner = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTk5YWI0NDFmYzk3ZjYwOTA4MWFkM2NlMzNkNTk4MjkxZDUxYmVmOGNiN2FkMjQ4NGI1YzEzODdjN2E4NCJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#53f51d-->"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 40) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);
            inventoryHelper.setItem(10, loserPlayerHead);

            if (loserType.equals("ROCK")) {
                inventoryHelper.setItem(11, rock);
            } else if (loserType.equals("PAPER")) {
                inventoryHelper.setItem(11, paper);
            } else if (loserType.equals("SCISSORS")) {
                inventoryHelper.setItem(11, scissors);
            }

            inventoryHelper.setItem(13, arrowWinner);

            if (winnerType.equals("ROCK")) {
                inventoryHelper.setItem(15, rock);
            } else if (winnerType.equals("PAPER")) {
                inventoryHelper.setItem(15, paper);
            } else if (winnerType.equals("SCISSORS")) {
                inventoryHelper.setItem(15, scissors);
            }

            inventoryHelper.setItem(16, winnerPlayerHead);

            inventoryHelper.setItem(19, glass_red);
            inventoryHelper.setItem(20, glass_red);

            inventoryHelper.setItem(24, glass_lime);
            inventoryHelper.setItem(25, glass_lime);

            inventoryHelper.setItem(40, back);

            inventoryHelper.open(player);
        }

        if (guiID == 1) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Remis!", 5);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack glass_lime = inventoryHelper.prepareItemStack(Material.LIME_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#53f51dWYGRANY"));
                });
            });

            ItemStack glass_red = inventoryHelper.prepareItemStack(Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#f51d11PRZEGRANY"));
                });
            });

            ItemStack rock = inventoryHelper.prepareItemStack(Material.COBBLESTONE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#34b4ebKamień"));
                });
            });

            ItemStack paper = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#8ceb34Papier"));
                });
            });

            ItemStack scissors = inventoryHelper.prepareItemStack(Material.SHEARS, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#eb6b34Nożyczki"));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack winnerPlayerHead = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(winner.getName());
                    itemMeta.setDisplayName(Api.fixColor("&#a8e0e6" + winner.getName()));
                });
            });

            ItemStack loserPlayerHead = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(loser.getName());
                    itemMeta.setDisplayName(Api.fixColor("&#a8e0e6" + loser.getName()));
                });
            });

            ItemStack arrowWinner = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzE5Mzg2YWY2MmU3Njk3NDkzZGQ0YjQyYWYwM2M1ZGU2ZTQ4ZGJkMjZiNzI4NDg4OGQ5NDM1ZjI3ODQwYjkifX19");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#4795f5REMIS"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 40) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 45, glass_black);
            inventoryHelper.setItem(10, loserPlayerHead);

            if (loserType.equals("ROCK")) {
                inventoryHelper.setItem(11, rock);
            } else if (loserType.equals("PAPER")) {
                inventoryHelper.setItem(11, paper);
            } else if (loserType.equals("SCISSORS")) {
                inventoryHelper.setItem(11, scissors);
            }

            inventoryHelper.setItem(13, arrowWinner);

            if (winnerType.equals("ROCK")) {
                inventoryHelper.setItem(15, rock);
            } else if (winnerType.equals("PAPER")) {
                inventoryHelper.setItem(15, paper);
            } else if (winnerType.equals("SCISSORS")) {
                inventoryHelper.setItem(15, scissors);
            }

            inventoryHelper.setItem(16, winnerPlayerHead);

            inventoryHelper.setItem(19, glass_red);
            inventoryHelper.setItem(20, glass_red);

            inventoryHelper.setItem(24, glass_red);
            inventoryHelper.setItem(25, glass_red);

            inventoryHelper.setItem(40, back);

            inventoryHelper.open(player);
        }
    }

    private static Map<Player, Boolean> inGameCheck = new HashMap<>();
    private static Map<Player, Integer> betAmountGame = new HashMap<>();
    private static Map<Player, Player> gameRequests = new HashMap<>();

    private static Map<Player, Player> choseTypeRequests = new HashMap<>();
    private static Map<Player, String> choseTypeGame = new HashMap<>();

    public static void sendGameRequest(Player player, Player target, int betAmount) {
        gameRequests.put(target, player);
        gameRequests.put(player, target);
        betAmountGame.put(target, betAmount);
        betAmountGame.put(player, betAmount);

        Api.sendMessage(target, "");
        Api.sendMessage(target, "        &#f58a42&lGRA - KAMIEŃ PAPIER NOŻYCE");
        Api.sendMessage(target, "");
        Api.sendMessage(target, "&8>> &#8dfa52Otrzymałeś zaproszenie od &#46b9f2" + player.getName());
        Api.sendMessage(target, "&8>> &#8dfa52Masz &#ffd56c60 sekund &fᎠ &#8dfa52na potwierdzenie zaproszenia do gry!");
        Api.sendMessage(target, "&8>> &#8dfa52Zakład: &#FFF88F" + betAmount + " &f");
        Api.sendMessage(target, "");
        NotificationBuilder.of(NotificationBuilder.NotificationType.CHAT,
                "            <hover:show_text:\"<white>Kliknij aby zaakceptować!\"><click:suggest_command:/rockpaperscissors akceptuj><#4BF72D>&l[AKCEPTUJ]</click></hover> <hover:show_text:\"<white>Kliknij aby odmówić!\"><click:suggest_command:/rockpaperscissors odmow><#F7442D>&l[ODMÓW]</click></hover>").send(target);
        Api.sendMessage(target, "");

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (gameRequests.containsKey(target) &&
                            gameRequests.get(target) == player &&
                            betAmountGame.containsKey(target) &&
                    betAmountGame.get(target) == betAmount &&
                    gameRequests.containsKey(player) &&
                    gameRequests.get(player) == target &&
                    betAmountGame.containsKey(player) &&
                    betAmountGame.get(player) == betAmount) {
                gameRequests.remove(target);
                gameRequests.remove(player);
                betAmountGame.remove(target);
                betAmountGame.remove(player);
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Zaproszenie do gry dla gracza &#fcb419" + target.getName() + "&#fc2419 wygasło");
                Api.sendMessage(target, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Zaproszenie do gry od gracza &#fcb419" + player.getName() + "&#fc2419 wygasło");
            }
        }, 60 * 20L);
    }

    public static boolean hasSentGameRequest(Player player, Player target) {
        return gameRequests.containsKey(target) && gameRequests.get(target) == player;
    }
    public static void acceptGameRequest(Player player) {
        if (gameRequests.containsKey(player)) {
            Player secondPlayer = gameRequests.get(player);

            UserManager.getInstance().getUser(player).ifPresent(user -> {
                UserManager.getInstance().getUser(secondPlayer).ifPresent(secondUser -> {
                    double balanceUser = user.balance();
                    double balanceSecondUser = secondUser.balance();
                    if (!(balanceUser >= betAmountGame.get(player))) {
                        gameRequests.remove(player);
                        gameRequests.remove(secondPlayer);
                        betAmountGame.remove(player);
                        betAmountGame.remove(secondPlayer);
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczająco pieniędzy aby zaakceptować gre!");
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Twój przecinik nie posiada wystarczająco pieniędzy aby zaakceptować gre!");
                        return;
                    } else if (!(balanceSecondUser >= betAmountGame.get(secondPlayer))) {
                        gameRequests.remove(player);
                        gameRequests.remove(secondPlayer);
                        betAmountGame.remove(player);
                        betAmountGame.remove(secondPlayer);
                        Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczająco pieniędzy aby zaakceptować gre!");
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Twój przecinik nie posiada wystarczająco pieniędzy aby zaakceptować gre!");
                        return;
                    }
                });
            });

            inGameCheck.put(secondPlayer, true);
            inGameCheck.put(player, true);

            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zaakceptowałeś zaproszenie do gry od gracza &#fcb419" + secondPlayer.getName());
            Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739zaakceptował twoje zaproszenie do gry!");

            (new BukkitRunnable() {
                int time = 7;
                @Override
                public void run() {
                    time -= 1;
                    if (0 < time){

                        if (time == 6) {
                            secondPlayer.sendTitle(Api.fixColor("&#E6B170&l5"), Api.fixColor(""), 0, 20, 5);
                            player.sendTitle(Api.fixColor("&#E6B170&l5"), Api.fixColor(""), 0, 20, 5);
                        } else if (time == 5) {
                            secondPlayer.sendTitle(Api.fixColor("&#EDA853&l4"), Api.fixColor(""), 0, 20, 5);
                            player.sendTitle(Api.fixColor("&#EDA853&l4"), Api.fixColor(""), 0, 20, 5);
                        } else if (time == 4) {
                            secondPlayer.sendTitle(Api.fixColor("&#E8C410&l3"), Api.fixColor(""), 0, 20, 5);
                            player.sendTitle(Api.fixColor("&#E8C410&l3"), Api.fixColor(""), 0, 20, 5);
                        } else if (time == 3) {
                            secondPlayer.sendTitle(Api.fixColor("&#E89D10&l2"), Api.fixColor(""), 0, 20, 5);
                            player.sendTitle(Api.fixColor("&#E89D10&l2"), Api.fixColor(""), 0, 20, 5);
                        } else if (time == 2) {
                            secondPlayer.sendTitle(Api.fixColor("&#E82610&l1"), Api.fixColor(""), 0, 20, 5);
                            player.sendTitle(Api.fixColor("&#E82610&l1"), Api.fixColor(""), 0, 20, 5);
                        } else if (time == 1) {
                            secondPlayer.sendTitle(Api.fixColor("&#26E810&lSTART!"), Api.fixColor(""), 0, 25, 5);
                            player.sendTitle(Api.fixColor("&#26E810&lSTART!"), Api.fixColor(""), 0, 25, 5);
                        }
                    } else {
                        this.cancel();
                        secondPlayer.sendTitle(Api.fixColor("&#26E810&lSTART!"), Api.fixColor("&8>> &#4cf739Masz &#ffd56c60 sekund &fᎠ &#4cf739na wybór! &8<<"), 0, 20, 5);
                        player.sendTitle(Api.fixColor("&#26E810&lSTART!"), Api.fixColor("&8>> &#4cf739Masz &#ffd56c60 sekund &fᎠ &#4cf739na wybór! &8<<"), 0, 20, 5);
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            UserManager.getInstance().getUser(secondPlayer).ifPresent(targetUser -> {
                                user.withdraw(betAmountGame.get(player));
                                targetUser.withdraw(betAmountGame.get(secondPlayer));
                            });
                        });

                        choseTypeRequests.put(secondPlayer, player);
                        choseTypeRequests.put(player, secondPlayer);
                        openGui(0, secondPlayer, player, betAmountGame.get(player));
                        openGui(0, player, secondPlayer, betAmountGame.get(player));

                        List<String> stringList = Arrays.asList("ROCK", "PAPER", "SCISSORS");

                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            if (choseTypeRequests.containsKey(secondPlayer) && choseTypeRequests.get(secondPlayer) == player) {
                                String randomList = RandomApi.randomElementList(stringList);
                                choseTypeRequests.remove(secondPlayer); 
                                secondPlayer.closeInventory();
                                Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefix() +
                                        "&#fc2419Za długo myślisz nad wyborem dlatego system wybrał za Ciebie! Wybrano: &#fcb419" + randomList);

                                choseTypeGame.put(secondPlayer, randomList);
                                sendChoseTypeGame(secondPlayer, player);
                            }
                            if (choseTypeRequests.containsKey(player) && choseTypeRequests.get(player) == secondPlayer) {
                                String randomList = RandomApi.randomElementList(stringList);
                                choseTypeRequests.remove(player);
                                player.closeInventory();
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() +
                                        "&#fc2419Za długo myślisz nad wyborem dlatego system wybrał za Ciebie! Wybrano: &#fcb419" + randomList);

                                choseTypeGame.put(player, randomList);
                                sendChoseTypeGame(player, secondPlayer);
                            }
                        }, 60 * 20L);
                    }
                }
            }).runTaskTimer(Main.getPlugin(), 0, 20);
        } else {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie masz żadnych oczekujących zaproszeń do gry!");
        }
    }

    public static void sendChoseTypeGame(Player player, Player secondPlayer) {
        if (choseTypeGame.get(player) != null && choseTypeGame.get(secondPlayer) != null) {
            gameRequests.remove(player);
            gameRequests.remove(secondPlayer);
            inGameCheck.remove(player);
            inGameCheck.remove(secondPlayer);

            UserManager.getInstance().getUser(player).ifPresent(user -> {
                UserManager.getInstance().getUser(secondPlayer).ifPresent(targetUser -> {
                    if (choseTypeGame.get(player) == choseTypeGame.get(secondPlayer)) {
                        openResultGui(1, player, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));
                        openResultGui(1, secondPlayer, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));

                        user.deposit(betAmountGame.get(player));
                        targetUser.deposit(betAmountGame.get(secondPlayer));
                    } else if (choseTypeGame.get(player).equals("PAPER") && choseTypeGame.get(secondPlayer).equals("SCISSORS")) {
                        openResultGui(0, player, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));
                        openResultGui(0, secondPlayer, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));

                        targetUser.deposit(1.95 * betAmountGame.get(secondPlayer));
                    } else if (choseTypeGame.get(secondPlayer).equals("PAPER") && choseTypeGame.get(player).equals("SCISSORS")) {
                        openResultGui(0, player, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));
                        openResultGui(0, secondPlayer, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));

                        user.deposit(1.95 * betAmountGame.get(player));
                    } else if (choseTypeGame.get(player).equals("PAPER") && choseTypeGame.get(secondPlayer).equals("ROCK")) {
                        openResultGui(0, player, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));
                        openResultGui(0, secondPlayer, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));

                        user.deposit(1.95 * betAmountGame.get(player));
                    } else if (choseTypeGame.get(secondPlayer).equals("PAPER") && choseTypeGame.get(player).equals("ROCK")) {
                        openResultGui(0, player, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));
                        openResultGui(0, secondPlayer, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));

                        targetUser.deposit(1.95 * betAmountGame.get(secondPlayer));
                    } else if (choseTypeGame.get(player).equals("ROCK") && choseTypeGame.get(secondPlayer).equals("PAPER")) {
                        openResultGui(0, player, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));
                        openResultGui(0, secondPlayer, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));

                        targetUser.deposit(1.95 * betAmountGame.get(secondPlayer));
                    } else if (choseTypeGame.get(secondPlayer).equals("ROCK") && choseTypeGame.get(player).equals("PAPER")) {
                        openResultGui(0, player, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));
                        openResultGui(0, secondPlayer, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));

                        user.deposit(1.95 * betAmountGame.get(player));
                    } else if (choseTypeGame.get(player).equals("ROCK") && choseTypeGame.get(secondPlayer).equals("SCISSORS")) {
                        openResultGui(0, player, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));
                        openResultGui(0, secondPlayer, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));

                        user.deposit(1.95 * betAmountGame.get(player));
                    } else if (choseTypeGame.get(secondPlayer).equals("ROCK") && choseTypeGame.get(player).equals("SCISSORS")) {
                        openResultGui(0, player, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));
                        openResultGui(0, secondPlayer, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));

                        targetUser.deposit(1.95 * betAmountGame.get(secondPlayer));
                    } else if (choseTypeGame.get(player).equals("SCISSORS") && choseTypeGame.get(secondPlayer).equals("ROCK")) {
                        openResultGui(0, player, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));
                        openResultGui(0, secondPlayer, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));

                        targetUser.deposit(1.95 * betAmountGame.get(secondPlayer));
                    } else if (choseTypeGame.get(secondPlayer).equals("SCISSORS") && choseTypeGame.get(player).equals("ROCK")) {
                        openResultGui(0, player, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));
                        openResultGui(0, secondPlayer, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));

                        user.deposit(1.95 * betAmountGame.get(player));
                    } else if (choseTypeGame.get(player).equals("SCISSORS") && choseTypeGame.get(secondPlayer).equals("PAPER")) {
                        openResultGui(0, player, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));
                        openResultGui(0, secondPlayer, player, secondPlayer, choseTypeGame.get(player), choseTypeGame.get(secondPlayer));

                        user.deposit(1.95 * betAmountGame.get(player));
                    } else if (choseTypeGame.get(secondPlayer).equals("SCISSORS") && choseTypeGame.get(player).equals("PAPER")) {
                        openResultGui(0, player, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));
                        openResultGui(0, secondPlayer, secondPlayer, player, choseTypeGame.get(secondPlayer), choseTypeGame.get(player));

                        targetUser.deposit(1.95 * betAmountGame.get(secondPlayer));
                    }
                });
            });

            betAmountGame.remove(secondPlayer);
            betAmountGame.remove(player);
        } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Musisz poczekać na swojego przeciwinika aż do jego wyboru!");
    }
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!inGameCheck.containsKey(player)) return;
        if (inGameCheck.get(player) == null) return;

        if (inGameCheck.get(player)) {
            List<String> stringList = Arrays.asList("ROCK", "PAPER", "SCISSORS");
            String randomList = RandomApi.randomElementList(stringList);
            Player secondPlayer = gameRequests.get(player);
            choseTypeRequests.remove(player);
            choseTypeGame.put(player, randomList);
            sendChoseTypeGame(player, secondPlayer);
            System.out.println("InventoryClose in Game, random: " + randomList);
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Wyszedłeś podczas wyboru dlatego system wybrał Ci losowy wybór i jest to &#fcb419" + randomList);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!inGameCheck.containsKey(player)) return;
        if (inGameCheck.get(player) == null) return;

        if (inGameCheck.get(player)) {
            List<String> stringList = Arrays.asList("ROCK", "PAPER", "SCISSORS");
            String randomList = RandomApi.randomElementList(stringList);
            Player secondPlayer = gameRequests.get(player);
            choseTypeRequests.remove(player);
            choseTypeGame.put(player, randomList);
            sendChoseTypeGame(player, secondPlayer);
            System.out.println("Player Quit in Game, random: " + randomList);
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Wyszedłeś podczas wyboru dlatego system wybrał Ci losowy wybór i jest to &#fcb419" + randomList);
            Api.sendMessage(secondPlayer, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Twój przeciwnik wyszedł z gry dlatego system wybrał za niego losowy wybór!");
        }

    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (!inGameCheck.containsKey(player)) return;
        if (inGameCheck.get(player) == null) return;

        if (inGameCheck.get(player)) {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz używać żadnych komend podczas gry!");
            event.setCancelled(true);
        }
    }
}
