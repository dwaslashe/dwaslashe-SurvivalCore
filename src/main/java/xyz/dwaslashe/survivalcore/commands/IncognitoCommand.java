package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class IncognitoCommand extends Command implements Listener {
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private static final SecureRandom RANDOM = new SecureRandom();
    public IncognitoCommand() {
        super("incognito", "/incognito", "");
        setPermission("core.command.incognito");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length >= 0) {
            openGui(0, p);
        }
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Incognito", 4);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack on = inventoryHelper.prepareItemStack(Material.LIME_CONCRETE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#008443Włącz incognito"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby włączyć lub zmienić incognito!")));
                });
            });

            ItemStack off = inventoryHelper.prepareItemStack(Material.RED_CONCRETE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Wyłącz incognito"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby wyłączyć incognito!")));
                });
            });

            ItemStack info = inventoryHelper.prepareItemStack(Material.LEGACY_SKULL_ITEM, itemStack -> {
                itemStack.setDurability((short) 3);
                inventoryHelper.editSkullMetaWithProperty(itemStack, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjI3NGUxNjA1MjMzNDI1MDkxZjdiMjgzN2E0YmI4ZjRjODA0ZGFjODBkYjllNGY1OTlmNTM1YzAzYWZhYjBmOCJ9fX0=");
                inventoryHelper.editSkullMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EInformacje"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList(" ", " &#39FF14Twój nick&8: &#FFF01F" + player.getDisplayName(), " &#39FF14Twój prawdziwy nick&8: &#FFF01F" + player.getName())));
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
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie włączyłeś &eincognito!");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " " + generate(10) + " nickonly");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set prefix.1000.");
                } else if (e.getSlot() == 15) {
                    player.closeInventory();
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cPomyślnie wyłączyłeś &eincognito!");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminnick " + player.getName() + " " + player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset prefix.1000.");
                } else if (e.getSlot() == 31) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 36, glass_black);
            inventoryHelper.setItem(10, glass_black);
            inventoryHelper.setItem(11, on);
            inventoryHelper.setItem(12, glass_black);
            inventoryHelper.setItem(13, info);
            inventoryHelper.setItem(14, glass_black);
            inventoryHelper.setItem(15, off);
            inventoryHelper.setItem(16, glass_black);
            inventoryHelper.setItem(31, back);

            inventoryHelper.open(player);
        }
    }

    public static String generate(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
