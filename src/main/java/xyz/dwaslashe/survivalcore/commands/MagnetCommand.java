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
import xyz.dwaslashe.survivalcore.listeners.OthersListener;
import xyz.dwaslashe.survivalcore.tasks.PlayerTask;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MagnetCommand extends Command implements Listener {
    public MagnetCommand() {
        super("magnet", "/magnet <crafting, give> <gracz>", "", "magnez");
        setPermission("core.command.magnet");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("crafting", "give"), args[0]);
        else if (args.length == 2) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            Api.sendMessage(sender, " &8[ &d&lMAGNEZ - OPIS &8]");
            Api.sendMessage(sender, "&5* &d/magnez crafting &8- &fpokazuje crafting magnezu");
            Api.sendMessage(sender, "");
            Api.sendMessage(sender, "&5* &fMając magnez w ekwipunku możesz rzeczy podnosić od razu do ekwipunku!");
        } else if (args[0].equalsIgnoreCase("crafting")) {
            openGui(0, player);
        } else if (args[0].equalsIgnoreCase("give")) {
            if (!player.hasPermission("core.command.magnet.give")) {
                player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.magnet.give&8) &8<<"));
                return;
            }
            if (args.length == 2) {
                Player secondPlayer = Bukkit.getPlayer(args[1]);
                if (secondPlayer == null) {
                    offlinePlayer();
                    return;
                }
                Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie dałeś graczu &e" + secondPlayer.getName() + " &dMagnez");
                Api.giveOrDrop(secondPlayer, OthersListener.magnet);
            }
        }
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Crafting magnezu", 6);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack air = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Brak itemu"));
                });
            });

            ItemStack redstone = inventoryHelper.prepareItemStack(Material.REDSTONE_BLOCK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Blok redstony"));
                });
            });

            ItemStack stick = inventoryHelper.prepareItemStack(Material.STICK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#c47926Patyk"));
                });
            });

            ItemStack magnet = inventoryHelper.prepareItemStack(Material.LIGHTNING_ROD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF10F0Magnez"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#39FF14Mając magnez w ekwipunku itemy", " &#39FF14które niszczysz idą do twojego ekwipunku!")));
                });
            });

            ItemStack ironblock = inventoryHelper.prepareItemStack(Material.IRON_INGOT, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&fBlok żelaza"));
                });
            });

            ItemStack back = inventoryHelper.prepareItemStack(Material.BARRIER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 49) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 54, glass_black);
            inventoryHelper.setItem(10, redstone);
            inventoryHelper.setItem(11, air);
            inventoryHelper.setItem(12, redstone);
            inventoryHelper.setItem(19, ironblock);
            inventoryHelper.setItem(20, stick);
            inventoryHelper.setItem(21, ironblock);
            inventoryHelper.setItem(24, magnet);
            inventoryHelper.setItem(28, ironblock);
            inventoryHelper.setItem(29, ironblock);
            inventoryHelper.setItem(30, ironblock);
            inventoryHelper.setItem(49, back);

            inventoryHelper.open(player);
        }
    }
}
