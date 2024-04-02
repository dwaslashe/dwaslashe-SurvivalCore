package xyz.dwaslashe.survivalcore.commands;

import net.saidora.economy.manager.UserManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.MoneyTargetCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.objects.MoneyTarget;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyTargetCommand extends Command {
    public MoneyTargetCommand() {
        super("moneytarget", "/moneytarget <reset, set, setlimit, title> <number>", "");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("reset", "set", "setlimit", "title"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        MoneyTarget moneyTarget = MoneyTargetCache.getInstance().compute(1);
        if (args.length == 0) {
            if (moneyTarget.getMoney() >= moneyTarget.getLimitMoney()) {
                player.sendTitle(Api.fixColor("&#eb9f34&lCEL PIENIĘDZY"), Api.fixColor("&f楸 &#4cf739Cel został już osiągniety! &f楸"));
                return;
            }
            openGui(0, player);
        } else if (args.length >= 1) {
            if (player.hasPermission("core.command.moneytarget")) {
                if (args[0].equalsIgnoreCase("reset")) {
                    moneyTarget.setTransactions("");
                    moneyTarget.setMoney(0);
                    moneyTarget.setLimitMoney(0);
                    moneyTarget.setTitle("");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zrestartowano statystyki celu pieniędzy!");
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (args.length == 1) {
                        wrongUsage();
                    } else if (Api.isInt(args[1])) {
                        int value = Integer.parseInt(args[1]);
                        moneyTarget.setMoney(value);
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie ustawiono zdobyte pieniądze!");
                    } else
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Argument musi być liczbą!");
                } else if (args[0].equalsIgnoreCase("setlimit")) {
                    if (args.length == 1) {
                        wrongUsage();
                    } else if (Api.isInt(args[1])) {
                        int value = Integer.parseInt(args[1]);
                        moneyTarget.setLimitMoney(value);
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie ustawiono limit celu pieniędzy!");
                    } else
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Argument musi być liczbą!");
                } else if (args.length >= 1) {
                    if (args.length == 1) {
                        wrongUsage();
                    } else if (args[0].equalsIgnoreCase("title")) {
                        String title = StringUtils.join(args, " ", 1, args.length);
                        moneyTarget.setTitle(title);
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie ustawiono tytuł celu pieniędzy!");
                    }
                }
            } else player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &#fc2419Nie posiadasz uprawnien &8(&#fcb419core.command.moneytarget&8) &8<<"));
        }
    }

    public static void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Cel pieniędzy", 6);
            MoneyTarget moneyTarget = MoneyTargetCache.getInstance().compute(1);

            ItemStack diamond = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11211);
                    itemMeta.setDisplayName(Api.fixColor("&#03befc#1"));
                });
            });


            ItemStack oneplayer = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    if (moneyTarget.getTransactions() == null) {
                        itemMeta.setOwner("null");
                        itemMeta.setDisplayName(Api.fixColor("&#fc3826Brak"));
                        return;
                    }

                    String result = getHighestNumber(moneyTarget.getTransactions());

                    if (!result.isEmpty()) {
                        String[] parts = result.split("&");
                        int highestNumber = Integer.parseInt(parts[0]);
                        String highestPlayer = parts[1];
                        itemMeta.setOwner(highestPlayer);
                        itemMeta.setDisplayName(Api.fixColor("&#03befc#1. &#f7e53b" + highestPlayer + " &8- &#FFF88F" + highestNumber + " &f"));
                        itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Aktualna ilość wydanych twoich pieniędzy na cel: &#FFF88F" + getNumberPlayer(moneyTarget.getTransactions(), player.getName()) + " &f")));
                    } else {
                        itemMeta.setOwner("null");
                        itemMeta.setDisplayName(Api.fixColor("&#fc3826Brak"));
                    }
                });
            });

            ItemStack gold = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11229);
                    itemMeta.setDisplayName(Api.fixColor("&#f5bf1d#2"));
                });
            });

            ItemStack twoplayer = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    if (moneyTarget.getTransactions() == null) {
                        itemMeta.setOwner("null");
                        itemMeta.setDisplayName(Api.fixColor("&#fc3826Brak"));
                        return;
                    }

                    String result = getSecondHighestNumber(moneyTarget.getTransactions());

                    if (!result.isEmpty()) {
                        String[] parts = result.split("&");
                        int secondhighestNumber = Integer.parseInt(parts[0]);
                        String secondhighestPlayer = parts[1];
                        itemMeta.setOwner(secondhighestPlayer);
                        itemMeta.setDisplayName(Api.fixColor("&#f5bf1d#2. &#f7e53b" + secondhighestPlayer + " &8- &#FFF88F" + secondhighestNumber + " &f"));
                        itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Aktualna ilość wydanych twoich pieniędzy na cel: &#FFF88F" + getNumberPlayer(moneyTarget.getTransactions(), player.getName()) + " &f")));
                    } else {
                        itemMeta.setOwner("null");
                        itemMeta.setDisplayName(Api.fixColor("&#fc3826Brak"));
                    }
                });
            });

            ItemStack iron = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11201);
                    itemMeta.setDisplayName(Api.fixColor("&#aba89f#3"));
                });
            });

            ItemStack threeplayer = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    if (moneyTarget.getTransactions() == null) {
                        itemMeta.setOwner("null");
                        itemMeta.setDisplayName(Api.fixColor("&#fc3826Brak"));
                        return;
                    }

                    String result = getThirdHighestNumber(moneyTarget.getTransactions());

                    if (!result.isEmpty()) {
                        String[] parts = result.split("&");
                        int thidhighestNumber = Integer.parseInt(parts[0]);
                        String thidhighestPlayer = parts[1];
                        itemMeta.setOwner(thidhighestPlayer);
                        itemMeta.setDisplayName(Api.fixColor("&#aba89f#3. &#f7e53b" + thidhighestPlayer + " &8- &#FFF88F" + thidhighestNumber + " &f"));
                        itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Aktualna ilość wydanych twoich pieniędzy na cel: &#FFF88F" + getNumberPlayer(moneyTarget.getTransactions(), player.getName()) + " &f")));
                    } else {
                        itemMeta.setOwner("null");
                        itemMeta.setDisplayName(Api.fixColor("&#fc3826Brak"));
                    }
                });
            });

            ItemStack onecash = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjI2ODExNTI0NDdhM2RiZDc1ODQxYTk2ZTNmN2FjNTJhODhjMDcxNWM4NzdjZWE0YmVjZDliNWQwMzgwZGM4NiJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF88F500 &f"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f᎘ Aby dodać pieniądze do celu")));
                });
            });

            ItemStack twocash = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjI2ODExNTI0NDdhM2RiZDc1ODQxYTk2ZTNmN2FjNTJhODhjMDcxNWM4NzdjZWE0YmVjZDliNWQwMzgwZGM4NiJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF88F1000 &f"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f᎘ Aby dodać pieniądze do celu")));
                });
            });

            ItemStack threecash = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjI2ODExNTI0NDdhM2RiZDc1ODQxYTk2ZTNmN2FjNTJhODhjMDcxNWM4NzdjZWE0YmVjZDliNWQwMzgwZGM4NiJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF88F5000 &f"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f᎘ Aby dodać pieniądze do celu")));
                });
            });

            ItemStack fourcash = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjI2ODExNTI0NDdhM2RiZDc1ODQxYTk2ZTNmN2FjNTJhODhjMDcxNWM4NzdjZWE0YmVjZDliNWQwMzgwZGM4NiJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFF88F10000 &f"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &f᎘ Aby dodać pieniądze do celu")));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11196);
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                UserManager.getInstance().getUser(player).ifPresent(user -> {
                    double balance = user.balance();

                    if (e.getSlot() == 28) {
                        if (balance >= 500) {
                            user.withdraw(500);
                            moneyTarget.addMoney(500);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wpłaciłeś na cel pieniędzy!");
                            player.closeInventory();

                            if (moneyTarget.getTransactions() == null) {
                                moneyTarget.setTransactions("500&" + player.getName() + "@");
                                return;
                            }

                            if (isExistPlayer(moneyTarget.getTransactions(), player.getName())) {
                                moneyTarget.setTransactions(modifyNumberPlayer(moneyTarget.getTransactions(), player.getName(), String.valueOf(getNumberPlayer(moneyTarget.getTransactions(), player.getName()) + 500)));
                            } else {
                                moneyTarget.addTransactions("500&" + player.getName() + "@");
                            }
                        } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz tyle pieniędzy!");
                    } else if (e.getSlot() == 30) {
                        if (balance >= 1000) {
                            user.withdraw(1000);
                            moneyTarget.addMoney(1000);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wpłaciłeś na cel pieniędzy!");
                            player.closeInventory();

                            if (moneyTarget.getTransactions() == null) {
                                moneyTarget.setTransactions("1000&" + player.getName() + "@");
                                return;
                            }

                            if (isExistPlayer(moneyTarget.getTransactions(), player.getName())) {
                                moneyTarget.setTransactions(modifyNumberPlayer(moneyTarget.getTransactions(), player.getName(), String.valueOf(getNumberPlayer(moneyTarget.getTransactions(), player.getName()) + 1000)));
                            } else {
                                moneyTarget.addTransactions("1000&" + player.getName() + "@");
                            }
                        } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz tyle pieniędzy!");
                    } else if (e.getSlot() == 32) {
                        if (balance >= 5000) {
                            user.withdraw(5000);
                            moneyTarget.addMoney(5000);
                            player.closeInventory();

                            if (moneyTarget.getTransactions() == null) {
                                moneyTarget.setTransactions("5000&" + player.getName() + "@");
                                return;
                            }

                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wpłaciłeś na cel pieniędzy!");
                            if (isExistPlayer(moneyTarget.getTransactions(), player.getName())) {
                                moneyTarget.setTransactions(modifyNumberPlayer(moneyTarget.getTransactions(), player.getName(), String.valueOf(getNumberPlayer(moneyTarget.getTransactions(), player.getName()) + 5000)));
                            } else {
                                moneyTarget.addTransactions("5000&" + player.getName() + "@");
                            }
                        } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz tyle pieniędzy!");
                    } else if (e.getSlot() == 34) {
                        if (balance >= 10000) {
                            user.withdraw(10000);
                            moneyTarget.addMoney(10000);
                            player.closeInventory();

                            if (moneyTarget.getTransactions() == null) {
                                moneyTarget.setTransactions("10000&" + player.getName() + "@");
                                return;
                            }

                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wpłaciłeś na cel pieniędzy!");
                            if (isExistPlayer(moneyTarget.getTransactions(), player.getName())) {
                                moneyTarget.setTransactions(modifyNumberPlayer(moneyTarget.getTransactions(), player.getName(), String.valueOf(getNumberPlayer(moneyTarget.getTransactions(), player.getName()) + 10000)));
                            } else {
                                moneyTarget.addTransactions("10000&" + player.getName() + "@");
                            }
                        } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz tyle pieniędzy!");
                    } else if (e.getSlot() == 49) {
                        player.closeInventory();
                    }
                });
            });

            inventoryHelper.setItem(3, oneplayer);
            inventoryHelper.setItem(12, diamond);
            inventoryHelper.setItem(4, twoplayer);
            inventoryHelper.setItem(13, gold);
            inventoryHelper.setItem(5, threeplayer);
            inventoryHelper.setItem(14, iron);

            inventoryHelper.setItem(28, onecash);
            inventoryHelper.setItem(30, twocash);
            inventoryHelper.setItem(32, threecash);
            inventoryHelper.setItem(34, fourcash);

            inventoryHelper.setItem(49, back);

            inventoryHelper.open(player);
        }
    }

    public static boolean isExistPlayer(String input, String player) {
        String regex = "\\d+&" + player + "@";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }

    public static int getNumberPlayer(String input, String player) {
        String regex = "(\\d+)&" + player + "@";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String numberString = matcher.group(1);
            try {
                return Integer.parseInt(numberString);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }

    public static String modifyNumberPlayer(String input, String player, String number) {
        String regex = "(\\d+)&" + player + "@";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String currentNumberString = matcher.group(1);
            String modifiedInput = input.replaceFirst(currentNumberString, number);
            return modifiedInput;
        }

        return input;
    }

    public static String getHighestNumber(String input) {
        String regex = "(\\d+)&([^@]+)@";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        int maxNumber = Integer.MIN_VALUE;
        String maxPlayer = "";

        while (matcher.find()) {
            int currentNumber = Integer.parseInt(matcher.group(1));
            if (currentNumber > maxNumber) {
                maxNumber = currentNumber;
                maxPlayer = matcher.group(2);
            }
        }

        if (!maxPlayer.isEmpty()) {
            return maxNumber + "&" + maxPlayer;
        }

        return "";
    }

    public static String getSecondHighestNumber(String input) {
        String regex = "(\\d+)&([^@]+)@";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        int maxNumber = Integer.MIN_VALUE;
        String maxPlayer = "";
        int secondMaxNumber = Integer.MIN_VALUE;
        String secondMaxPlayer = "";

        while (matcher.find()) {
            int currentNumber = Integer.parseInt(matcher.group(1));
            String currentPlayer = matcher.group(2);

            if (currentNumber > maxNumber) {
                secondMaxNumber = maxNumber;
                secondMaxPlayer = maxPlayer;
                maxNumber = currentNumber;
                maxPlayer = currentPlayer;
            } else if (currentNumber > secondMaxNumber && currentNumber != maxNumber) {
                secondMaxNumber = currentNumber;
                secondMaxPlayer = currentPlayer;
            }
        }

        if (!secondMaxPlayer.isEmpty()) {
            return secondMaxNumber + "&" + secondMaxPlayer;
        }

        return "";
    }

    public static String getThirdHighestNumber(String input) {
        String regex = "(\\d+)&([^@]+)@";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        int maxNumber = Integer.MIN_VALUE;
        String maxPlayer = "";
        int secondMaxNumber = Integer.MIN_VALUE;
        String secondMaxPlayer = "";
        int thirdMaxNumber = Integer.MIN_VALUE;
        String thirdMaxPlayer = "";

        while (matcher.find()) {
            int currentNumber = Integer.parseInt(matcher.group(1));
            String currentPlayer = matcher.group(2);

            if (currentNumber > maxNumber) {
                thirdMaxNumber = secondMaxNumber;
                thirdMaxPlayer = secondMaxPlayer;
                secondMaxNumber = maxNumber;
                secondMaxPlayer = maxPlayer;
                maxNumber = currentNumber;
                maxPlayer = currentPlayer;
            } else if (currentNumber > secondMaxNumber && currentNumber != maxNumber) {
                thirdMaxNumber = secondMaxNumber;
                thirdMaxPlayer = secondMaxPlayer;
                secondMaxNumber = currentNumber;
                secondMaxPlayer = currentPlayer;
            } else if (currentNumber > thirdMaxNumber && currentNumber != maxNumber && currentNumber != secondMaxNumber) {
                thirdMaxNumber = currentNumber;
                thirdMaxPlayer = currentPlayer;
            }
        }

        if (!thirdMaxPlayer.isEmpty()) {
            return thirdMaxNumber + "&" + thirdMaxPlayer;
        }

        return "";
    }
}
