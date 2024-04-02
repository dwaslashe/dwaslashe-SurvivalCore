package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class HatCommand extends Command {
    public HatCommand() {
        super("hat", "/hat", "");
        setPermission("core.command.hat");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (CooldownManager.checkDelay(p) == true) {
            return;
        }
        CooldownManager.addColdown(p, "30s");
        ItemStack hand = p.getItemInHand();
        ItemStack head = p.getInventory().getHelmet();
        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie założyłeś item na głowe");
        if (hand == null && hand.getType() == Material.AIR) {
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Musisz coś trzymać w ręce");
            return;
        }

        p.getInventory().setHelmet(hand);
        p.getInventory().remove(hand);

        if (head != null && head.getType() != Material.AIR) {
            p.getInventory().addItem(head);
        }
    }
}