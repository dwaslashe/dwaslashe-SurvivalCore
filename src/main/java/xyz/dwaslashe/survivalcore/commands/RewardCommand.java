package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class    RewardCommand extends Command implements Listener {
    public RewardCommand() {
        super("rewards", "/rewards", "", "nagroda");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        openGui(0, player);
        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Serwer-Minecraft: &#FFF01Fhttps://serwery-minecraft.pl/serwer/1870-wywrotkamc-pl");
        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739McList: &#FFF01Fhttps://mclist.pl/serwer/" + Main.pluginConfig.getMessages().getServer());
        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Discord: &#21F8F6" + Main.pluginConfig.getMessages().getDiscord());
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Nagrody", 4);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack sm = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZiYjlmYjk3YmE4N2NiNzI3Y2QwZmY0NzdmNzY5MzcwYmVhMTljY2JmYWZiNTgxNjI5Y2Q1NjM5ZjJmZWMyYiJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#0eab5dNagroda za polubienie Serwery-Minecraft"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            " ",
                            " &#E7E7E7Aby dostać nagrodę musisz polubić serwer",
                            " &#E7E7E7na stronie &#FFF01Fhttps://serwery-minecraft.pl/serwer/1870-wywrotkamc-pl",
                            " &#E7E7E7pomaga nam to w rozwoju serwera!",
                            "",
                            " &6&l⭐ &#FFF01FNagroda:",
                            "",
                            " &#E7E7E7- 1x Antyczny klucz!",
                            "",
                            " &#FBFD8C&nKliknij aby polubić!"
                    )));
                });
            });

            ItemStack mclist = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZiYjlmYjk3YmE4N2NiNzI3Y2QwZmY0NzdmNzY5MzcwYmVhMTljY2JmYWZiNTgxNjI5Y2Q1NjM5ZjJmZWMyYiJ9fX0=");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#008443Nagroda za polubienie McList"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            " ",
                            " &#E7E7E7Aby dostać nagrodę musisz polubić serwer",
                            " &#E7E7E7na stronie &#FFF01Fhttps://mclist.pl/serwer/" + Main.pluginConfig.getMessages().getServer(),
                            " &#E7E7E7pomaga nam to w rozwoju serwera!",
                            "",
                            " &6&l⭐ &#FFF01FNagroda:",
                            "",
                            " &#E7E7E7- 1x Antyczny klucz!",
                            "",
                            " &#FBFD8C&nKliknij aby polubić!"
                    )));
                });
            });

            ItemStack discord = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19");
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#7289daNagroda za wejście na Discord"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(
                            " ",
                            " &#E7E7E7Aby dostać nagrode musisz wejśc na serwerowy",
                            " &#E7E7E7discord &#21F8F6" + Main.pluginConfig.getMessages().getDiscord(),
                            " &#E7E7E7i napisać na kanale &#FFF01F#nagroda&#E7E7E7 swój nick!",
                            "",
                            " &6&l⭐ &#FFF01FNagroda:",
                            "",
                            " &#E7E7E7- 1x Starożytny klucz!",
                            "",
                            " &#FBFD8C&nKliknij aby wejśc na discord!"
                    )));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    player.getOpenInventory().close();
                    player.chat("/serweryminecraftlist");
                } else if (e.getSlot() == 13) {
                    player.getOpenInventory().close();
                    player.chat("/mclist");
                } else if (e.getSlot() == 15) {
                    player.closeInventory();
                    player.chat("/discord");
                } else if (e.getSlot() == 31) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 36, glass_black);
            inventoryHelper.setItem(31, back);
            inventoryHelper.setItem(11, sm);
            inventoryHelper.setItem(13, mclist);
            inventoryHelper.setItem(15, discord);

            inventoryHelper.open(player);
        }
    }
}