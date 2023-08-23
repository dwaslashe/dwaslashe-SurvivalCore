package xyz.dwaslashe.survivalcore.commands;

import net.saidora.economy.manager.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.listeners.PlayerInteractListener;
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
                    UserManager.getInstance().getUser(p).ifPresent(user -> {
                        double dollarAmount = 0;
                        if(args[0].equalsIgnoreCase("all")) dollarAmount = user.balance();
                        else dollarAmount = Double.parseDouble(args[0]);
                        double balance = user.balance();
                        if(dollarAmount < 1){
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cPodana wartość musi zaczynać się od &e1&c!");
                        } else if (balance >= dollarAmount && !Double.isInfinite(dollarAmount) && Double.isFinite(dollarAmount)) {
                            user.withdraw(dollarAmount);
                            ItemStack paper = PlayerInteractListener.makePaper(dollarAmount, Arrays.asList(
                                    "",
                                    " &#E7E7E7Wartość: &#FFF88F" + dollarAmount + " &#FFC42E$",
                                    " &#E7E7E7Właściciel: &#9DF89F" + p.getName()));
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wypłaciłeś z konta &#FFF88F" + dollarAmount + " &#FFC42E$");
                            Api.giveOrDrop(p, paper);

                        } else
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz tyle pieniedzy!");
                    });
                } catch (NumberFormatException e) {
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cŹle określono kwote, którą chcesz wypłacić.\n&cDostępny format: &e&n1001.032");
                }
            } else wrongUsage();
        } else wrongUsage();
    }
}
