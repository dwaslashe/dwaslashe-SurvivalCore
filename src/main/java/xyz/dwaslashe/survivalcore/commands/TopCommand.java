package xyz.dwaslashe.survivalcore.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class TopCommand extends Command {
    public TopCommand() {
        super("top", "/top", "", "topki", "ranking");
    }
    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length >= 0){
            openGui(0, p);
        }
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Ranking", 3);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack player_info = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setOwner(player.getName());
                    itemMeta.setDisplayName(Api.fixColor("&cTwoje Statystyki"));
                    itemMeta.setLore(Api.fixColor(PlaceholderAPI.setPlaceholders(player, Arrays.asList("",
                            " &7Spędzony czas&8: &e%statistic_time_played%",
                            " &7Ilość śmierci&8: &e%statistic_deaths%",
                            " &7Ilość zabitych mobów&8: &e%statistic_mob_kills%",
                            " &7Twoje saldo&8: &e%vault_eco_balance%",
                            " "
                    ))));
                });
            });

            ItemStack top1 = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&eTopka Czasu"));
                    itemMeta.setLore(Api.fixColor(PlaceholderAPI.setPlaceholders(player, Arrays.asList("",
                            " &#FF31311. &e%ajlb_lb_statistic_time_played_1_alltime_name% %ajlb_lb_statistic_time_played_1_alltime_time%",
                            " &#39ff142. &e%ajlb_lb_statistic_time_played_2_alltime_name% %ajlb_lb_statistic_time_played_2_alltime_time%",
                            " &#FFF01F3. &e%ajlb_lb_statistic_time_played_3_alltime_name% %ajlb_lb_statistic_time_played_3_alltime_time%",
                            " &74. &e%ajlb_lb_statistic_time_played_4_alltime_name% %ajlb_lb_statistic_time_played_4_alltime_time%",
                            " &75. &e%ajlb_lb_statistic_time_played_5_alltime_name% %ajlb_lb_statistic_time_played_5_alltime_time%",
                            " &76. &e%ajlb_lb_statistic_time_played_6_alltime_name% %ajlb_lb_statistic_time_played_6_alltime_time%",
                            " &77. &e%ajlb_lb_statistic_time_played_7_alltime_name% %ajlb_lb_statistic_time_played_7_alltime_time%",
                            " &78. &e%ajlb_lb_statistic_time_played_8_alltime_name% %ajlb_lb_statistic_time_played_8_alltime_time%",
                            " &79. &e%ajlb_lb_statistic_time_played_9_alltime_name% %ajlb_lb_statistic_time_played_9_alltime_time%",
                            " &710. &e%ajlb_lb_statistic_time_played_10_alltime_name% %ajlb_lb_statistic_time_played_10_alltime_time%",
                            "",
                            " &f&nKliknij aby zobaczyć na czacie!"
                    ))));
                });
            });

            ItemStack top2 = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&cTopka Śmierci"));
                    itemMeta.setLore(Api.fixColor(PlaceholderAPI.setPlaceholders(player, Arrays.asList(
                            "",
                            " &#FF31311. &e%ajlb_lb_statistic_deaths_1_alltime_name% %ajlb_lb_statistic_deaths_1_alltime_value%",
                            " &#39ff142. &e%ajlb_lb_statistic_deaths_2_alltime_name% %ajlb_lb_statistic_deaths_2_alltime_value%",
                            " &#FFF01F3. &e%ajlb_lb_statistic_deaths_3_alltime_name% %ajlb_lb_statistic_deaths_3_alltime_value%",
                            " &74. &e%ajlb_lb_statistic_deaths_4_alltime_name% %ajlb_lb_statistic_deaths_4_alltime_value%",
                            " &75. &e%ajlb_lb_statistic_deaths_5_alltime_name% %ajlb_lb_statistic_deaths_5_alltime_value%   ",
                            " &76. &e%ajlb_lb_statistic_deaths_6_alltime_name% %ajlb_lb_statistic_deaths_6_alltime_value%",
                            " &77. &e%ajlb_lb_statistic_deaths_7_alltime_name% %ajlb_lb_statistic_deaths_7_alltime_value%",
                            " &78. &e%ajlb_lb_statistic_deaths_8_alltime_name% %ajlb_lb_statistic_deaths_8_alltime_value%",
                            " &79. &e%ajlb_lb_statistic_deaths_9_alltime_name% %ajlb_lb_statistic_deaths_9_alltime_value%",
                            " &710. &e%ajlb_lb_statistic_deaths_10_alltime_name% %ajlb_lb_statistic_deaths_10_alltime_value%",
                            "",
                            " &f&nKliknij aby zobaczyć na czacie!"
                    ))));
                });
            });

            ItemStack top3 = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&bTopka Zabitych Mobów"));
                    itemMeta.setLore(Api.fixColor(PlaceholderAPI.setPlaceholders(player, Arrays.asList(
                            "",
                            " &#FF31311. &e%ajlb_lb_statistic_mob_kills_1_alltime_name% %ajlb_lb_statistic_mob_kills_1_alltime_value%",
                            " &#39ff142. &e%ajlb_lb_statistic_mob_kills_2_alltime_name% %ajlb_lb_statistic_mob_kills_2_alltime_value%",
                            " &#FFF01F3. &e%ajlb_lb_statistic_mob_kills_3_alltime_name% %ajlb_lb_statistic_mob_kills_3_alltime_value%",
                            " &74. &e%ajlb_lb_statistic_mob_kills_4_alltime_name% %ajlb_lb_statistic_mob_kills_4_alltime_value%",
                            " &75. &e%ajlb_lb_statistic_mob_kills_5_alltime_name% %ajlb_lb_statistic_mob_kills_5_alltime_value%   ",
                            " &76. &e%ajlb_lb_statistic_mob_kills_6_alltime_name% %ajlb_lb_statistic_mob_kills_6_alltime_value%",
                            " &77. &e%ajlb_lb_statistic_mob_kills_7_alltime_name% %ajlb_lb_statistic_mob_kills_7_alltime_value%",
                            " &78. &e%ajlb_lb_statistic_mob_kills_8_alltime_name% %ajlb_lb_statistic_mob_kills_8_alltime_value%",
                            " &79. &e%ajlb_lb_statistic_mob_kills_9_alltime_name% %ajlb_lb_statistic_mob_kills_9_alltime_value%",
                            " &710. &e%ajlb_lb_statistic_mob_kills_10_alltime_name% %ajlb_lb_statistic_mob_kills_10_alltime_value%",
                            "",
                            " &f&nKliknij aby zobaczyć na czacie!"
                    ))));
                });
            });

            ItemStack top4 = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&6Topka Pieniędzy"));
                    itemMeta.setLore(Api.fixColor(PlaceholderAPI.setPlaceholders(player, Arrays.asList(
                            "",
                            " &#FF31311. &e%ajlb_lb_vault_eco_balance_1_alltime_name% %ajlb_lb_vault_eco_balance_1_alltime_value%$",
                            " &#39ff142. &e%ajlb_lb_vault_eco_balance_2_alltime_name% %ajlb_lb_vault_eco_balance_2_alltime_value%$",
                            " &#FFF01F3. &e%ajlb_lb_vault_eco_balance_3_alltime_name% %ajlb_lb_vault_eco_balance_3_alltime_value%$",
                            " &74. &e%ajlb_lb_vault_eco_balance_4_alltime_name% %ajlb_lb_vault_eco_balance_4_alltime_value%$",
                            " &75. &e%ajlb_lb_vault_eco_balance_5_alltime_name% %ajlb_lb_vault_eco_balance_5_alltime_value%$",
                            " &76. &e%ajlb_lb_vault_eco_balance_6_alltime_name% %ajlb_lb_vault_eco_balance_6_alltime_value%$",
                            " &77. &e%ajlb_lb_vault_eco_balance_7_alltime_name% %ajlb_lb_vault_eco_balance_7_alltime_value%$",
                            " &78. &e%ajlb_lb_vault_eco_balance_8_alltime_name% %ajlb_lb_vault_eco_balance_8_alltime_value%$",
                            " &79. &e%ajlb_lb_vault_eco_balance_9_alltime_name% %ajlb_lb_vault_eco_balance_9_alltime_value%$",
                            " &710. &e%ajlb_lb_vault_eco_balance_10_alltime_name% %ajlb_lb_vault_eco_balance_10_alltime_value%$",
                            "",
                            " &f&nKliknij aby zobaczyć na czacie!"
                    ))));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    Api.sendMessage(player, "&#FF31311. &e%ajlb_lb_statistic_time_played_1_alltime_name% %ajlb_lb_statistic_time_played_1_alltime_time%");
                    Api.sendMessage(player, "&#39ff142. &e%ajlb_lb_statistic_time_played_2_alltime_name% %ajlb_lb_statistic_time_played_2_alltime_time%");
                    Api.sendMessage(player, "&#FFF01F3. &e%ajlb_lb_statistic_time_played_3_alltime_name% %ajlb_lb_statistic_time_played_3_alltime_time%");
                    Api.sendMessage(player, "&74. &e%ajlb_lb_statistic_time_played_4_alltime_name% %ajlb_lb_statistic_time_played_4_alltime_time%");
                    Api.sendMessage(player, "&75. &e%ajlb_lb_statistic_time_played_5_alltime_name% %ajlb_lb_statistic_time_played_5_alltime_time%");
                    Api.sendMessage(player, "&76. &e%ajlb_lb_statistic_time_played_6_alltime_name% %ajlb_lb_statistic_time_played_6_alltime_time%");
                    Api.sendMessage(player, "&77. &e%ajlb_lb_statistic_time_played_7_alltime_name% %ajlb_lb_statistic_time_played_7_alltime_time%");
                    Api.sendMessage(player, "&78. &e%ajlb_lb_statistic_time_played_8_alltime_name% %ajlb_lb_statistic_time_played_8_alltime_time%");
                    Api.sendMessage(player, "&79. &e%ajlb_lb_statistic_time_played_9_alltime_name% %ajlb_lb_statistic_time_played_9_alltime_time%");
                    Api.sendMessage(player, "&710. &e%ajlb_lb_statistic_time_played_10_alltime_name% %ajlb_lb_statistic_time_played_10_alltime_time%");
                } else if (e.getSlot() == 15) {
                    player.closeInventory();
                    Api.sendMessage(player, "&#FF31311. &e%ajlb_lb_statistic_deaths_1_alltime_name% %ajlb_lb_statistic_deaths_1_alltime_value%");
                    Api.sendMessage(player, "&#39ff142. &e%ajlb_lb_statistic_deaths_2_alltime_name% %ajlb_lb_statistic_deaths_2_alltime_value");
                    Api.sendMessage(player, "&#FFF01F3. &e%ajlb_lb_statistic_deaths_3_alltime_name% %ajlb_lb_statistic_deaths_3_alltime_value%");
                    Api.sendMessage(player, "&74. &e%ajlb_lb_statistic_deaths_4_alltime_name% %ajlb_lb_statistic_deaths_4_alltime_value%");
                    Api.sendMessage(player, "&75. &e%ajlb_lb_statistic_deaths_5_alltime_name% %ajlb_lb_statistic_deaths_5_alltime_value%");
                    Api.sendMessage(player, "&76. &e%ajlb_lb_statistic_deaths_6_alltime_name% %ajlb_lb_statistic_deaths_6_alltime_value%");
                    Api.sendMessage(player, "&77. &e%ajlb_lb_statistic_deaths_7_alltime_name% %ajlb_lb_statistic_deaths_7_alltime_value%");
                    Api.sendMessage(player, "&78. &e%ajlb_lb_statistic_deaths_8_alltime_name% %ajlb_lb_statistic_deaths_8_alltime_value%");
                    Api.sendMessage(player, "&79. &e%ajlb_lb_statistic_deaths_9_alltime_name% %ajlb_lb_statistic_deaths_9_alltime_value%");
                    Api.sendMessage(player, "&710. &e%ajlb_lb_statistic_deaths_10_alltime_name% %ajlb_lb_statistic_deaths_10_alltime_value%");
                } else if (e.getSlot() == 22) {
                    player.closeInventory();
                    Api.sendMessage(player, "&#FF31311. &e%ajlb_lb_statistic_mob_kills_1_alltime_name% %ajlb_lb_statistic_mob_kills_1_alltime_value%");
                    Api.sendMessage(player, "&#39ff142. &e%ajlb_lb_statistic_mob_kills_2_alltime_name% %ajlb_lb_statistic_mob_kills_2_alltime_value%");
                    Api.sendMessage(player, "&#FFF01F3. &e%ajlb_lb_statistic_mob_kills_3_alltime_name% %ajlb_lb_statistic_mob_kills_3_alltime_value%");
                    Api.sendMessage(player, "&74. &e%ajlb_lb_statistic_mob_kills_4_alltime_name% %ajlb_lb_statistic_mob_kills_4_alltime_value%");
                    Api.sendMessage(player, "&75. &e%ajlb_lb_statistic_mob_kills_5_alltime_name% %ajlb_lb_statistic_mob_kills_5_alltime_value%");
                    Api.sendMessage(player, "&76. &e%ajlb_lb_statistic_mob_kills_6_alltime_name% %ajlb_lb_statistic_mob_kills_6_alltime_value%");
                    Api.sendMessage(player, "&77. &e%ajlb_lb_statistic_mob_kills_7_alltime_name% %ajlb_lb_statistic_mob_kills_7_alltime_value%");
                    Api.sendMessage(player, "&78. &e%ajlb_lb_statistic_mob_kills_8_alltime_name% %ajlb_lb_statistic_mob_kills_8_alltime_value%");
                    Api.sendMessage(player, "&79. &e%ajlb_lb_statistic_mob_kills_9_alltime_name% %ajlb_lb_statistic_mob_kills_9_alltime_value%");
                    Api.sendMessage(player, "&710. &e%ajlb_lb_statistic_mob_kills_10_alltime_name% %ajlb_lb_statistic_mob_kills_10_alltime_value%");
                } else if (e.getSlot() == 24) {
                    player.closeInventory();
                    Api.sendMessage(player, "&#FF31311. &e%ajlb_lb_vault_eco_balance_1_alltime_name% %ajlb_lb_vault_eco_balance_1_alltime_value%");
                    Api.sendMessage(player, "&#39ff142. &e%ajlb_lb_vault_eco_balance_2_alltime_name% %ajlb_lb_vault_eco_balance_2_alltime_value%");
                    Api.sendMessage(player, "&#FFF01F3. &e%ajlb_lb_vault_eco_balance_3_alltime_name% %ajlb_lb_vault_eco_balance_3_alltime_value%");
                    Api.sendMessage(player, "&74. &e%ajlb_lb_vault_eco_balance_4_alltime_name% %ajlb_lb_vault_eco_balance_4_alltime_value%");
                    Api.sendMessage(player, "&75. &e%ajlb_lb_vault_eco_balance_5_alltime_name% %ajlb_lb_vault_eco_balance_5_alltime_value%");
                    Api.sendMessage(player, "&76. &e%ajlb_lb_vault_eco_balance_6_alltime_name% %ajlb_lb_vault_eco_balance_6_alltime_value%");
                    Api.sendMessage(player, "&77. &e%ajlb_lb_vault_eco_balance_7_alltime_name% %ajlb_lb_vault_eco_balance_7_alltime_value%");
                    Api.sendMessage(player, "&78. &e%ajlb_lb_vault_eco_balance_8_alltime_name% %ajlb_lb_vault_eco_balance_8_alltime_value%");
                    Api.sendMessage(player, "&79. &e%ajlb_lb_vault_eco_balance_9_alltime_name% %ajlb_lb_vault_eco_balance_9_alltime_value%");
                    Api.sendMessage(player, "&710. &e%ajlb_lb_vault_eco_balance_10_alltime_name% %ajlb_lb_vault_eco_balance_10_alltime_value%");
                }
            });

            inventoryHelper.setItemRange(0, 3 * 9, glass_black);
            inventoryHelper.setItem(11, player_info);
            inventoryHelper.setItem(13, top1);
            inventoryHelper.setItem(15, top2);
            inventoryHelper.setItem(22, top3);
            inventoryHelper.setItem(24, top4);

            inventoryHelper.open(player);
        }
    }
}
