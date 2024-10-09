package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class RepairCommand extends Command {
    public RepairCommand() {
        super("repair", "/repair <all>", "", "napraw");
        setOnlyPlayer(true);
        setPermission("core.command.repair");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("all"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            if (CooldownManager.checkDelay(p) == true) {
                return;
            }
            CooldownManager.addCooldown(p, "5m");
            ItemStack itemStack = p.getItemInHand();
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz naprawić powietrza");
            } else if (itemStack.getDurability() == 0) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Ten przedmiot jest już naprawiony");
            } else {
                itemStack.setDurability((short) 0);
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Przedmiot &#fcb419" + itemStack.getType().toString().toUpperCase() + " &#4cf739został naprawiony");
            }

        } else if (args[0].equalsIgnoreCase("all")) {
            if (!p.hasPermission("core.command.repair.all")) {
                p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &#fc2419Nie posiadasz uprawnien &8(&#fcb419core.command.repair.all&8) &8<<"));
                return;
            }
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie naprawiono wszystkie przedmioty");
            for (int i = 0; i < p.getInventory().getSize(); ++i) {
                if (p.getInventory().getItem(i) != null && p.getInventory().getItem(i).getType() != Material.AIR && p.getInventory().getItem(i).getDurability() != 0 && p.getInventory().getItem(i).getType() != Material.GOLDEN_APPLE) {
                    p.getInventory().getItem(i).setDurability((short) 0);
                }
            }
        }
    }
}
