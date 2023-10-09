package xyz.dwaslashe.survivalcore.commands;

import net.saidora.api.helpers.ItemHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.VoucherItem;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VoucherCommand extends Command {
    public VoucherCommand() {
        super("voucher", "/voucher <gracz> <id>", "");
        setPermission("core.command.voucher");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        else if (args.length == 2) return Api.startsWith(Arrays.asList("0", "1", "2"), args[1]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            Player secondPlayer = Bukkit.getServer().getPlayer(args[0]);
            if (secondPlayer != null) {
                if (Api.isInt(args[1])) {
                    int argument = Integer.parseInt(args[1]);
                    List<VoucherItem> voucherItemsList = Main.pluginVouchers.getItems().getVoucherItems().getItems();
                    if (voucherItemsList.get(argument) != null) {
                        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie nadano voucher o id &e" + argument + "&a, dla gracza &e" + secondPlayer.getName());

                        ItemStack voucher = ItemHelper.edit(new ItemStack(voucherItemsList.get(argument).getItem_material())).editNbtTagCompound(nbtItem -> {
                            if (voucherItemsList.get(argument).getOwner()) {
                                nbtItem.setString("voucher-owner", secondPlayer.getName());
                            } else {
                                nbtItem.setBoolean("voucher", true);
                            }
                        }).editItemMeta(ItemMeta.class, itemMeta -> {
                            itemMeta.setDisplayName(Api.fixColor(voucherItemsList.get(argument).getItem_name().replace("%owner%", secondPlayer.getName())));
                            itemMeta.setLore(Api.fixColor(voucherItemsList.get(argument).getItem_lore()));
                        }).getItemStack();

                        Api.giveOrDrop(secondPlayer, voucher);
                    } else wrongUsage();
                } else wrongUsage();
            } else offlinePlayer();
        } else wrongUsage();
    }
}

