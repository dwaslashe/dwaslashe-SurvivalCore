package xyz.dwaslashe.survivalcore.listeners;

import net.saidora.api.helpers.ItemHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.objects.VoucherItem;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherListener implements Listener {

    @EventHandler
    public void onVoucher(PlayerInteractEvent event) {
        ItemStack itemInHand = event.getPlayer().getItemInHand();
        Player player = event.getPlayer();

        List<VoucherItem> voucherItemsList = Main.pluginVouchers.getItems().getVoucherItems().getItems();

        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            for (VoucherItem voucherItem : voucherItemsList) {

                String itemInHandDisplayName = itemInHand.getItemMeta().getDisplayName();
                String voucherItemName = voucherItem.getItem_name().replace("%owner%", player.getName());

                itemInHandDisplayName = itemInHandDisplayName.replace("§x", "&#");
                itemInHandDisplayName = itemInHandDisplayName.replaceAll("§", "");

                if (itemInHand.getItemMeta().getLore() == null) return;

                List<String> itemInHandLore = itemInHand.getItemMeta().getLore();
                List<String> voucherLore = voucherItem.getItem_lore();

                itemInHandLore = itemInHandLore.stream()
                        .map(element -> element.replace("§x", "&#").replaceAll("§", ""))
                        .collect(Collectors.toList());

                if (itemInHand.getType().equals(voucherItem.getItem_material()) && itemInHandDisplayName.equals(voucherItemName) && itemInHandLore.equals(voucherLore)) {

                        ItemHelper itemHelper = ItemHelper.edit(itemInHand);
                        itemHelper.editNbtTagCompound(nbtItem -> {
                            if (voucherItem.getOwner()) {
                                if (nbtItem.hasKey("voucher-owner")) {
                                    if (nbtItem.getString("voucher-owner").equals(player.getName())) {
                                        player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                                        event.setUseInteractedBlock(Event.Result.DENY);
                                        event.setCancelled(true);
                                        for (String command : voucherItem.getCommandLine()) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%owner%", player.getName()));
                                        }
                                    }
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie jesteś właścicielem tego vouchery aby go odebrać!");
                            } else {
                                if (nbtItem.hasKey("voucher")) {
                                    player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                                    event.setUseInteractedBlock(Event.Result.DENY);
                                    event.setCancelled(true);
                                    for (String command : voucherItem.getCommandLine()) {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%owner%", player.getName()));
                                    }
                                }
                            }
                        });
                }
            }
        }
    }
}
