package xyz.dwaslashe.survivalcore.commands;

import net.saidora.api.extension.PlayerExtension;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.listeners.PlayerInteractListener;
import xyz.dwaslashe.survivalcore.utils.Api;
import java.util.Arrays;
import java.util.List;

public class XPBottleCommand extends Command {
    public XPBottleCommand() {
        super("xpbottle", "/xpbottle <level>", "", "butelkaxp");
        setPermission("core.command.xpbottle");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if(sender instanceof Player player){
            if(args.length == 1){
                try {

                    int expToWithdraw = Integer.parseInt(args[0]);
                    if (expToWithdraw <= 0) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cPodana wartość musisz być większa niż 0!");
                        return;
                    }
                    PlayerExtension.getPlayerExtend(player, extension -> {

                        int totalExp = extension.getExperience();
                        if (totalExp < expToWithdraw) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie posiadasz tyle doświadczenia aby wypłacić! &#aa42f5" + totalExp + "&8/&c" + expToWithdraw);
                            return;
                        }

                        extension.removeExperience(expToWithdraw);

                        ItemStack bottle = PlayerInteractListener.makeBottle(Arrays.asList(
                                "",
                                " &#E7E7E7Doświadczenie: &#aa42f5" + expToWithdraw + " EXP",
                                " &#E7E7E7Właściciel: &#9DF89F" + player.getName()));
                        Api.giveOrDrop(player, bottle);
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wypłaciłeś &#aa42f5" + expToWithdraw + " EXP");
                    });
                } catch (NumberFormatException e){
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cPodana wartość nie jest liczba!");
                }
            } else wrongUsage();
        }
    }
}
