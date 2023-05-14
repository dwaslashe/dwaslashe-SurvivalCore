package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.utils.Api;
import java.util.Arrays;
import java.util.List;

public class XPBottleCommand extends Command {
    public XPBottleCommand() {
        super("xpbottle", "/xpbottle <level>", "", "butelkaxp");
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
                if (Api.isInt(args[0]) && !args[0].contains("NaN") && !args[0].contains("Infinity") && (Integer.parseInt(args[0]) > 0)) {
                    double balance = Main.getVaultEconomy().getBalance(p);
                    if (balance >= 500.0) {
                        if (balance - 500.0 >= 0) {
                            try {
                                if (args[0].startsWith("NaN") && args[0].startsWith("Infinity")) {
                                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cPodana wartość nie jest liczba!");
                                    return;
                                }
                                int xpAmount = Integer.parseInt(args[0]);
                                ItemStack bottle = OthersListener.makeBottle(Arrays.asList(
                                        "",
                                        " &#E7E7E7Doświadczenie: &#aa42f5" + xpAmount + " lvl",
                                        " &#E7E7E7Właściciel: &#9DF89F" + p.getName()));
                                if (0 < (p.getExpToLevel() - xpAmount)) {
                                    p.setLevel(p.getLevel() - xpAmount);
                                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wypłaciłeś &#aa42f5" + xpAmount + " lvl &adoświadczenia");
                                    p.getInventory().addItem(new ItemStack[]{bottle});
                                } else
                                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz tyle doświadczenia aby wypłacić!");
                            } catch (IllegalArgumentException var9) {
                                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz tyle doświadczenia aby wypłacić!");
                            }
                        } else {
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zrobienia butelki z xp! Potrzebujesz &#FFF88F500 &#FFC42E$ &caby stworzyć butelke xp!");
                        }
                    } else {
                        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz wystarczającą kwotę do zrobienia butelki z xp! Potrzebujesz &#FFF88F500 &#FFC42E$ &caby stworzyć butelke xp!");
                    }
                } else Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cPodana wartość musi zaczynać się od &e1&c!");
            } else wrongUsage();
        } else wrongUsage();
    }
}
