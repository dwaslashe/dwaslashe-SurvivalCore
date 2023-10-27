package xyz.dwaslashe.survivalcore.listeners;

import net.saidora.api.helpers.ItemHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.BoosterCommand;
import xyz.dwaslashe.survivalcore.commands.VoucherCommand;
import xyz.dwaslashe.survivalcore.objects.VoucherItem;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherListener implements Listener {

    @EventHandler
    public void onVoucher(PlayerInteractEvent event) {
        ItemStack itemInHand = event.getPlayer().getItemInHand();
        Player player = event.getPlayer();

        if (event.getAction().isRightClick()) {

            if (itemInHand != null && itemInHand.getType() != Material.AIR && itemInHand.getAmount() > 0) {

                List<VoucherItem> voucherItemsList = Main.pluginVouchers.getItems().getVoucherItems().getItems();

                for (VoucherItem voucherItem : voucherItemsList) {

                    if (itemInHand.getItemMeta().getLore() != null && itemInHand.getItemMeta().getDisplayName() != null) {
                        String itemInHandDisplayName = itemInHand.getItemMeta().getDisplayName();
                        String voucherItemName = voucherItem.getItem_name().replace("%owner%", player.getName());

                        itemInHandDisplayName = itemInHandDisplayName.replace("§x", "&#");
                        itemInHandDisplayName = itemInHandDisplayName.replaceAll("§", "");

                        List<String> itemInHandLore = itemInHand.getItemMeta().getLore();
                        List<String> voucherLore = voucherItem.getItem_lore();

                        itemInHandLore = itemInHandLore.stream()
                                .map(element -> element.replace("§x", "&#").replaceAll("§", ""))
                                .collect(Collectors.toList());
                        System.out.println("-->");
                        System.out.println("Boolean item material: " + itemInHand.getType().equals(voucherItem.getItem_material()));
                        System.out.println("");
                        System.out.println("Boolean voucher item name: " + itemInHandDisplayName.equals(voucherItemName));
                        System.out.println("itemInHandDisplayName: " + itemInHandDisplayName);
                        System.out.println("voucherItemName: " + voucherItemName);
                        System.out.println("");
                        System.out.println("Boolean voucher item lore: " + itemInHandLore.equals(voucherLore));
                        System.out.println("itemInHandLore: " + itemInHandLore);
                        System.out.println("voucherLore: " + voucherLore);
                        System.out.println("");
                        System.out.println("<--");
                        if (itemInHand.getType().equals(voucherItem.getItem_material()) && itemInHandDisplayName.equals(voucherItemName)) {
                            System.out.println("execute Voucher");
                            ItemHelper itemHelper = ItemHelper.edit(itemInHand);
                            itemHelper.editNbtTagCompound(nbtItem -> {
                                if (nbtItem.hasCustomNbtData()) {
                                    if (nbtItem.hasNBTData()) {
                                        if (voucherItem.getOwner()) {
                                            if (nbtItem.hasKey("voucher-owner")) {
                                                if (nbtItem.hasKey("voucher-type")) {
                                                    //System.out.println("booleanHashMap: " + BoosterCommand.booleanHashMap.containsKey(nbtItem.getString("voucher-type")));
                                                    //System.out.println("getString voucher-type: " + nbtItem.getString("voucher-type"));
                                                    if (nbtItem.getString("voucher-owner").equals(player.getName())) {
                                                        if (!BoosterCommand.booleanHashMap.containsKey(nbtItem.getString("voucher-type"))) {
                                                            event.setCancelled(true);
                                                            event.setUseInteractedBlock(Event.Result.DENY);
                                                            event.setUseItemInHand(Event.Result.DENY);
                                                            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                                                            event.setUseInteractedBlock(Event.Result.DENY);
                                                            event.setCancelled(true);
                                                            for (String command : voucherItem.getCommandLine()) {
                                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%owner%", player.getName()));
                                                            }
                                                        } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz użyć tego ulepszenie ponieważ już istnieje taki! Poczekaj aż się skończy!");
                                                    }
                                                }
                                            } else
                                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie jesteś właścicielem tego vouchery aby go odebrać!");
                                        } else {
                                            if (nbtItem.hasKey("voucher")) {
                                                event.setCancelled(true);
                                                event.setUseInteractedBlock(Event.Result.DENY);
                                                event.setUseItemInHand(Event.Result.DENY);
                                                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                                                event.setUseInteractedBlock(Event.Result.DENY);
                                                event.setCancelled(true);
                                                for (String command : voucherItem.getCommandLine()) {
                                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%owner%", player.getName()));
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
    }
}
