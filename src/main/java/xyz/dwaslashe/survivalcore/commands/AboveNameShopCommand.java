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
import xyz.dwaslashe.survivalcore.objects.AbovenameShop;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AboveNameShopCommand extends Command {
    public AboveNameShopCommand() {
        super("tytuly", "/tytuly", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length >= 0){
            openGui(p, Main.pluginCommands.getCommands().getAbovenameShop().getItems(), 0);
        }
    }

    private Integer computeSlot(int clickSlot) {
        if (clickSlot >= 19 && clickSlot <= 25) {
            return (clickSlot-19);
        } else if (clickSlot >= 28 && clickSlot <= 34) {
            return (clickSlot-21);
        }
        return 999;
    }

    private void openGui(Player player, List<AbovenameShop> abovenameShopList, Integer page) {
        InventoryHelper inventoryHelper = new InventoryHelper(player, "Tytuły" + " (" + abovenameShopList.size() + ")", 6);

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

        ItemStack next = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
            inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                itemMeta.setDisplayName(Api.fixColor("&#FF3131Następna"));
                itemMeta.setCustomModelData(11191);
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

        int[] servicesslots = new int[]{19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};

        int slots = 0;

        int x = ((page + 1) * 14);

        List<AbovenameShop> tempItemList = new ArrayList<>();

        if (abovenameShopList.size() > 14) {
            for (int i = 0; i < 14; i++) {
                try {
                    tempItemList.add(abovenameShopList.get(i + (page * 14)));
                } catch (Exception s) {

                }
            }
        } else {
            tempItemList = abovenameShopList;
        }

        for (AbovenameShop serviceItem : tempItemList) {
            ItemStack service = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, serviceItem.getGui_item_head_texture());
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor(serviceItem.getGui_item_name()));
                    List<String> itemLore = serviceItem.getGui_item_lore();
                    List<String> addLore = Arrays.asList(player.hasPermission(serviceItem.getPermission()) ? " &#39FF14Masz już zakupiony ten tytuł!" : " &#FBA632Koszt: &#F9731C" + serviceItem.getCost() + " &f\uE094", "", player.hasPermission(serviceItem.getPermission()) ? " &f᎘ &#FBFD8CAby ustawić tytuł" : " &f᎘ &#FBFD8CAby kupić tytuł");
                    List<String> combinedList = new ArrayList<>(itemLore);
                    combinedList.addAll(addLore);
                    itemMeta.setLore(Api.fixColor(combinedList));
                });
            });

            inventoryHelper.setItem(servicesslots[slots], service);

            slots++;

        }

        inventoryHelper.click(e -> {
            e.setCancelled(true);
            if (e.getSlot() == 39) {
                if (page != 0) {
                    openGui(player, abovenameShopList, (page - 1));
                }
            } else if (e.getSlot() == 41) {
                if (abovenameShopList.size() > x) {
                    openGui(player, abovenameShopList, (page + 1));
                }
            } else if (e.getSlot() == 49) {
                player.closeInventory();
            } else if ((e.getSlot() >= 19 && e.getSlot() <= 25) || (e.getSlot() >= 28 && e.getSlot() <= 34)) {
                UserManager.getInstance().getUser(player).ifPresent(user -> {
                    double balance = user.balance();
                    player.closeInventory();
                    if (player.hasPermission(abovenameShopList.get(((computeSlot(e.getSlot()) + (page * 14)))).getPermission())) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zmieniono twój tytuł!");
                        List<String> commands = abovenameShopList.get(((computeSlot(e.getSlot()) + (page * 14)))).getCommandLine();
                        for (String command : commands) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                        }
                    } else if (balance >= abovenameShopList.get(((computeSlot(e.getSlot()) + (page * 14)))).getCost()) {
                        if (balance - abovenameShopList.get(((computeSlot(e.getSlot()) + (page * 14)))).getCost() >= 0) {
                            user.withdraw(abovenameShopList.get(((computeSlot(e.getSlot()) + (page * 14)))).getCost());

                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono tytuł!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + abovenameShopList.get(((computeSlot(e.getSlot()) + (page * 14)))).getPermission());

                            List<String> commands = abovenameShopList.get(((computeSlot(e.getSlot()) + (page * 14)))).getCommandLine();
                            for (String command : commands) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                            }
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                        }
                    } else {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tego tytułu!");
                    }
                });
            }
        });

        inventoryHelper.setItem(4, tag_info);

        if (page != 0) {
            inventoryHelper.setItem(48, previous);
        }

        inventoryHelper.setItem(49, back);

        if (abovenameShopList.size() > x) {
            inventoryHelper.setItem(50, next);
        }


        inventoryHelper.open(player);
    }
}