package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ElytraGiveCommand extends Command {
    public ElytraGiveCommand() {
        super("elytragive", "/elytragive <gracz>", "");
        setPermission("core.command.elytragive");
    }

    public static ItemStack elytra = new ItemApi(Material.ELYTRA).setAmount(1).setName(Api.fixColor("&#f04de5Elytra")).getItemStack();

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length == 1) {
            Player p2 = Bukkit.getPlayer(args[0]);
            ItemMeta itemMeta = elytra.getItemMeta();
            itemMeta.setLore(Api.fixColor(Arrays.asList(
                    "",
                    " &#E7E7E7Właściciel: &#9DF89F" + p2.getName(),
                    "",
                    " &#fa3d28Elytra może być tylko używana przez gracza",
                    " &#fa3d28który jest właścicielem elytry!")));
            elytra.setItemMeta(itemMeta);
            Api.giveOrDrop(p2, elytra);
        } else sender.sendMessage(getUsage());
    }
}

