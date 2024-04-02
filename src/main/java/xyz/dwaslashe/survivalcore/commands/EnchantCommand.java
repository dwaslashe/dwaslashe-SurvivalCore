package xyz.dwaslashe.survivalcore.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.EnchantList;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;

import java.util.ArrayList;
import java.util.List;

public class EnchantCommand extends Command {
    public EnchantCommand(){
        super("enchant", "/enchant <enchant> <level>", "", "ench");
        setPermission("core.command.enchant");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> enchants = new ArrayList();
            for (Enchantment value : EnchantList.getEnchants().values()) {
                if(!enchants.contains(value.getName().toLowerCase())) {
                    enchants.add(value.getName().toLowerCase());
                }
            }
            return Api.startsWith(enchants, args[0].toLowerCase());
        }
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
        } else {
            if (args.length >= 1) {
                if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Przedmiotem nie moze byc powietrze!");
                } else {
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("all")) {
                            for (Enchantment value : EnchantList.getEnchants().values()) {
                                p.setItemInHand(new ItemApi(p.getItemInHand()).addEnchant(value, 32765).getItemStack());
                            }
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyslnie dodano wszystkie enchanty o poziomie &b32765");
                        } else {
                            Enchantment enchantment = null;
                            if (Api.isInt(args[0])) {
                                enchantment = Enchantment.getByName(args[0]);
                            } else {
                                enchantment = Enchantment.getByName(args[0].toUpperCase());
                            }
                            if (enchantment == null) {
                                enchantment = EnchantList.get(args[0]);
                                if (enchantment == null) {
                                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie odnaleziono takiego enchantu!");
                                    return;
                                }
                            }
                            int level = 0;
                            if (Api.isInt(args[1])) {
                                level = Integer.parseInt(args[1]);
                            } else {
                                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Poziom musi byc liczba!");
                                return;
                            }
                            ItemMeta meta = p.getItemInHand().getItemMeta();
                            if (level == 0) {
                                meta.removeEnchant(enchantment);
                                p.getItemInHand().setItemMeta(meta);
                                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Usunieto enchant &#fcb419" + enchantment.toString().toUpperCase().split(" ")[1].split("]")[0]);
                            } else {
                                meta.addEnchant(enchantment, level, true);
                                p.getItemInHand().setItemMeta(meta);
                                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Dodano enchant &#fcb419" + enchantment.toString().toUpperCase().split(" ")[1].split("]")[0] + "&#4cf739, poziom zaklecia &#fcb419" + level);
                            }
                        }
                    }
                }
            }
        }
    }
}
