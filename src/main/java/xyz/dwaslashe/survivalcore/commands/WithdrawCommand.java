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
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.time.ZonedDateTime;
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
                if (Api.isFloat(args[0]) && !args[0].contains("NaN") && !args[0].contains("Infinity") && (Integer.parseInt(args[0]) > 0)) {
                    try {
                        float dollarAmount = Float.parseFloat(args[0]);
                        EconomyResponse response = Main.getVaultEconomy().withdrawPlayer(p, dollarAmount);
                        ItemStack paper = OthersListener.makePaper(Arrays.asList(
                                "",
                                " &#E7E7E7Wartość: &#FFF88F" + dollarAmount + " &#FFC42E$",
                                " &#E7E7E7Właściciel: &#9DF89F" + p.getName()));
                        if (response.type.equals(EconomyResponse.ResponseType.SUCCESS)) {
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wypłaciłeś z konta &#FFF88F" + dollarAmount + " &#FFC42E$");
                            p.getInventory().addItem(new ItemStack[]{paper});
                        } else Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz tyle pieniedzy!");
                    } catch (IllegalArgumentException var9) {
                        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz tyle pieniedzy!");
                    }
                } else Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cPodana wartość musi zaczynać się od &e1&c!");
            } else wrongUsage();
        } else wrongUsage();
    }
}
