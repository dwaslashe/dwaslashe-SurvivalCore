package xyz.dwaslashe.survivalcore.commands;

import net.saidora.economy.manager.UserManager;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.TicketCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.objects.Ticket;
import xyz.dwaslashe.survivalcore.utils.Api;
import java.util.Arrays;
import java.util.List;

public class PayTicketCommand extends Command {
    public PayTicketCommand() {
        super("oplacmandat", "/oplacmandat", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Ticket ticketPlayer = TicketCache.getInstance().compute(player.getUniqueId());
        if (ticketPlayer.getEnableJail() == 1) {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz już opłacić mandatu, za późno na to teraz musisz odczekać swoją karę!");
        } else if (ticketPlayer.getEnable() == 1) {
            UserManager.getInstance().getUser(player).ifPresent(user -> {
                double balanceUser = user.balance();
                if (balanceUser >= ticketPlayer.getValue()) {
                    openGui(0, player, ticketPlayer.getValue());
                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz tyle pieniędzy aby opłacić mandat w wysokości &#FFF88F" + ticketPlayer.getValue() + " &f");
            });
        } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz opłacić mandatu ponieważ nie posiadasz żadnego mandatu!");
    }

    private void openGui(int guiID, Player player, int value) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Potwierdź opłate mandatu", 4);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack yes = inventoryHelper.prepareItemStack(Material.LIME_WOOL, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#ffff00Zaakceptuj"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij, aby zaakceptować opłate mandatu!")));
                });
            });
            ItemStack no = inventoryHelper.prepareItemStack(Material.RED_WOOL, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#f2391dOdrzuć"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij, aby odrzucić opłate mandatu!")));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 12) {
                    UserManager.getInstance().getUser(player).ifPresent(user -> {
                        double balanceUser = user.balance();
                        if (balanceUser >= value) {
                            user.withdraw(value);
                            Ticket ticketPlayer = TicketCache.getInstance().compute(player.getUniqueId());

                            ticketPlayer.setEnable(0);
                            ticketPlayer.setEnableJail(0);
                            ticketPlayer.setTime(0);
                            ticketPlayer.setTimeJail(0);
                            ticketPlayer.setTimeMaxJail(0);
                            ticketPlayer.setValue(0);
                            ticketPlayer.setOnlineTimeOut(0);
                            ticketPlayer.setMaxTime(0);
                            ticketPlayer.setOnline(0);

                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie opłacono mandat!");
                            player.closeInventory();
                        } else {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz tyle pieniędzy aby opłacić mandat w wysokości &#FFF88F" + value + " &f");
                            player.closeInventory();
                        }
                    });
                } else if (e.getSlot() == 14) {
                    player.closeInventory();
                } else if (e.getSlot() == 31) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 36, glass_black);
            inventoryHelper.setItem(12, yes);
            inventoryHelper.setItem(14, no);

            inventoryHelper.setItem(31, back);

            inventoryHelper.open(player);
        }
    }
}
