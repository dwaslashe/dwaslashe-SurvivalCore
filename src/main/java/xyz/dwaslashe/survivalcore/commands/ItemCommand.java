package xyz.dwaslashe.survivalcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCommand extends Command {
    public ItemCommand() {
        super("item", "/item <name, lore, info> <text, clear>", "");
        setPermission("core.command.item");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("name", "lore", "info"), args[0]);
        else if (args.length == 2) return Api.startsWith(Arrays.asList("text", "clear"), args[0]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
        }
        if (CooldownManager.checkDelay(p) == true) {
            return;
        }
        CooldownManager.addCooldown(p, "5m");
        if (args.length >= 1) {
            if (p.getItemInHand() != null || p.getItemInHand().getType() != Material.AIR) {
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("name")) {
                        if (args[1].contains("Banknot") && args[1].contains("Butelka doświadczenia") && args[1].contains("AUTO") && args[1].contains("DRILL" ) && args[1].contains("TRAKTOR") && args[1].contains("SPADOCHRON") && args[1].contains("HELIKOPTER") && args[1].contains("ROBOT")) {
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz tego zrobić!");
                            return;
                        }
                        p.setItemInHand(new ItemApi(p.getItemInHand()).setName(StringUtils.join(args, " ", 1, args.length)).getItemStack());
                        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Nazwa przedmiotu została zmieniona");
                        return;
                    } else if (args[0].equalsIgnoreCase("lore")) {
                        if (args[1].equalsIgnoreCase("clear")) {
                            p.setItemInHand(new ItemApi(p.getItemInHand()).setLore(new ArrayList()).getItemStack());
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Opis przedmiotu został usuniety");
                            return;
                        } else if (args[1].contains(p.getName())) {
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz tego zrobić");
                            return;
                        } else if (args[2].contains(p.getName())) {
                            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz tego zrobić");
                            return;
                        } else {
                            if (p.getItemInHand() != null) {
                                String[] texts = StringUtils.join(args, " ", 1, args.length).replace("||", "4414").split("4414");
                                p.setItemInHand(new ItemApi(p.getItemInHand()).setLore(new ArrayList()).getItemStack());
                                for (String text : texts) {
                                    p.setItemInHand(new ItemApi(p.getItemInHand()).addLore(text).getItemStack());
                                }
                                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Opis przedmiotu został zmieniony");
                                return;
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("info")) {
                        Api.sendMessage(p, " &8[ &#4cf739&lINFORMACJE O PRZEDMIOCIE &8]\n" +
                                "&2* &7Nazwa&8: &#4cf739" + p.getItemInHand().getType().toString() + "\n" +
                                "&2* &7ID&8: &#4cf739" + p.getItemInHand().getType().getId() + ":" + p.getItemInHand().getDurability() + "\n" +
                                "&2* &7Ordinal ID&8: &#4cf739" + p.getInventory().getType().ordinal());
                        return;
                    }
                } else Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie mozesz zmienić nazwy/opisu powietrza");
            } else wrongUsage();
        }
    }
}
