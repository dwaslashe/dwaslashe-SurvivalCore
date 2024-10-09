package xyz.dwaslashe.survivalcore.commands;

import dev.lone.itemsadder.api.CustomStack;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;

import java.util.Arrays;
import java.util.List;

public class CustomItemCommand extends Command {
    public CustomItemCommand() {
        super("customitem", "/customitem <level> <player>", "");
        setPermission("core.command.customitem");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            wrongUsage();
        } else if (args.length >= 2) {
            CustomStack stack = CustomStack.getInstance("rank_badge:rank_lv" + args[0]);
            if(stack != null) {
                Player secondPlayer = Bukkit.getPlayer(args[1]);
                String level = PlaceholderAPI.setPlaceholders(secondPlayer, "%survivalcore_level%");
                ItemApi rankBadge = new ItemApi(stack.getItemStack()).setName("&#ff9f40Poziom: &#E7E7E7" + level).setLore(Arrays.asList("", " &#E7E7E7Właściciel: &#fcb419" + secondPlayer.getName()));
                Api.giveOrDrop(secondPlayer, rankBadge.getItemStack());
            } else {
                wrongUsage();
            }
        }
    }
}