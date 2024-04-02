package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.List;

public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear", "/clear <nick>", "");
        setPermission("core.command.clear");
        setOnlyPlayer(true);
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length == 0) {
            this.removeArmor(p);
            p.getInventory().clear();
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twój ekwipunek został &#fcb419wyczyszczony");
        } else if (args.length == 1) {
            Player p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            } else {
                Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyczysciłeś equ gracza &#fcb419" + p2.getName());
                Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twój ekwipunek został wyczyszczony przez &#fcb419" + s.getName());
                this.removeArmor(p2);
                p2.getInventory().clear();
            }
        } else wrongUsage();
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    public void removeArmor(Player p) {
        p.getInventory().setHelmet((ItemStack)null);
        p.getInventory().setChestplate((ItemStack)null);
        p.getInventory().setLeggings((ItemStack)null);
        p.getInventory().setBoots((ItemStack)null);
    }
}
