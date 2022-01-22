package xyz.dwaslashe.survivalcore.commands;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class WithdrawCommand extends Command {
    public WithdrawCommand() {
        super("withdraw", "/withdraw <ilosc>", "", "wyplac");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length > 0) {
            if (args.length == 1) {
                try {
                    if (args[0].startsWith("NaN") && args[0].startsWith("Infinity")) {
                        Api.sendMessage(p, "2");
                    }
                    float dollarAmount = Float.parseFloat(args[0]);
                    EconomyResponse response = Main.getVaultEconomy().withdrawPlayer(p, dollarAmount);
                    ItemStack paper = OthersListener.makePaper(Arrays.asList(ChatColor.YELLOW + "$" + dollarAmount));
                    if (response.type.equals(EconomyResponse.ResponseType.SUCCESS)) {
                        p.getInventory().addItem(new ItemStack[]{paper});
                    } else {
                        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz tyle pieniedzy!");
                    }
                } catch (NumberFormatException var9) {
                    if (args[0].startsWith("NaN") && args[0].startsWith("Infinity")) {
                        Api.sendMessage(p, "3");
                    }
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cTo nie jest liczba!");
                    return;
                }
            }
        } else {
            wrongUsage();
        }
    }
}
