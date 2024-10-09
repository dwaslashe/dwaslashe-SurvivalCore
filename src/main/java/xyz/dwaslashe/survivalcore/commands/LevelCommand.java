package xyz.dwaslashe.survivalcore.commands;

import dev.lone.itemsadder.api.CustomStack;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.listeners.PlayerInteractListener;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;
import xyz.dwaslashe.survivalcore.utils.ItemApi;

import java.util.Arrays;
import java.util.List;

public class LevelCommand extends Command {
    public LevelCommand() {
        super("poziom", "/level", "", "poziom");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        openGui(0, player, player);
    }

    public static void openGui(int guiID, Player player, Player secondPlayer) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "&fᵩ" + titleGui(player), 5);
            String level = PlaceholderAPI.setPlaceholders(player, "%clv_player_level%");
            int integerNextLevel = Integer.parseInt(level) + 1;
            ItemStack information = inventoryHelper.prepareItemStack(Material.PLAYER_HEAD, itemStack -> {
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(1);
                    itemMeta.setOwningPlayer(player);
                    itemMeta.setDisplayName(Api.fixColor(ChatApi.getPrefix(player).replace("<", "").replace(">", "") + player.getName()));
                    itemMeta.setLore(PlaceholderAPI.setPlaceholders(player, Api.fixColor(Arrays.asList(
                            "",
                            " &#E7E7E7Procentowe doświadczenie: &#1f93d0%clv_player_exp_percent%",
                            " &#E7E7E7Poziom: &f" + level + " %img_rank" + level + "%",
                            "",
                            " &#FF31311. &#E7E7E7%clv_leaderboard_name_1% &#6a6d72%clv_leaderboard_level_1%lvl",
                            " &#39ff142. &#E7E7E7%clv_leaderboard_name_2% &#6a6d72%clv_leaderboard_level_2%lvl",
                            " &#FFF01F3. &#E7E7E7%clv_leaderboard_name_3% &#6a6d72%clv_leaderboard_level_3%lvl",
                            " &#9c98984. &#E7E7E7%clv_leaderboard_name_4% &#6a6d72%clv_leaderboard_level_4%lvl",
                            " &#9c98985. &#E7E7E7%clv_leaderboard_name_5% &#6a6d72%clv_leaderboard_level_5%lvl"
                    ))));
                });
            });

            CustomStack stackIconLevel = CustomStack.getInstance("rank_badge:rank_lv" + level);
            CustomStack stackNextIconLevel = CustomStack.getInstance("rank_badge:rank_lv" + integerNextLevel);

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 13) {
                    PlayerInteractListener.openGui(0, secondPlayer, player);
                }
            });

            inventoryHelper.setItem(13, information);
            if(stackIconLevel != null) {
                ItemApi rankBadge = new ItemApi(stackIconLevel.getItemStack()).setName("&#ff9f40Poziom: &#E7E7E7" + level).setLore(Arrays.asList("", " &#E7E7E7Doświadczenie: &#1f93d0" + PlaceholderAPI.setPlaceholders(player, "%clv_player_exp%")));
                inventoryHelper.setItem(11, rankBadge.getItemStack());
            }
            if (stackNextIconLevel != null) {
                ItemApi rankBadge = new ItemApi(stackNextIconLevel.getItemStack()).setName("&#ff9f40Natępny poziom: &#E7E7E7" + integerNextLevel).setLore(Arrays.asList("", " &#E7E7E7Brakujące doświadczenie: &#1f93d0" + PlaceholderAPI.setPlaceholders(player, "%clv_player_exp_required%")));
                inventoryHelper.setItem(15, rankBadge.getItemStack());
            }

            inventoryHelper.open(player);
        }
    }

    private static String titleGui(Player player) {
        String percentageLevel = PlaceholderAPI.setPlaceholders(player, "%clv_player_exp_percent%");
        double percentage = Double.parseDouble(percentageLevel);
        if (percentage >= 80 && percentage <= 100) {
            return "Ǒ";
        } else if (percentage >= 60 && percentage < 80) {
            return "ǐ";
        } else if (percentage >= 40 && percentage < 60) {
            return "Ǐ";
        } else if (percentage >= 20 && percentage < 40) {
            return "ǎ";
        } else if (percentage >= 0 && percentage < 20) {
            return "Ǎ";
        } else {
            return "Ǎ";
        }
    }
}