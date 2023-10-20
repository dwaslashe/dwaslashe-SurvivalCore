package xyz.dwaslashe.survivalcore.commands;

import net.saidora.api.helpers.ItemHelper;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.VapeItem;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VapeCommand extends Command implements Listener {
    public VapeCommand() {
        super("vape", "/vape <gracz> <id>", "");
        setPermission("core.command.vape");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        else if (args.length == 2) return Api.startsWith(Arrays.asList("0", "1", "2"), args[1]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            Player secondPlayer = Bukkit.getServer().getPlayer(args[0]);
            if (secondPlayer != null) {
                if (Api.isInt(args[1])) {
                    int argument = Integer.parseInt(args[1]);
                    List<VapeItem> vapeItemList = Main.pluginVapes.getItems().getVapeItems().getItems();
                    if (vapeItemList.get(argument) != null) {
                        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie nadano vape o id &e" + argument + "&a, dla gracza &e" + secondPlayer.getName());

                        ItemStack vape = ItemHelper.edit(new ItemStack(vapeItemList.get(argument).getItem_material())).editNbtTagCompound(nbtItem -> {
                            nbtItem.setInteger("vape", vapeItemList.get(argument).getDurability());
                        }).editItemMeta(ItemMeta.class, itemMeta -> {
                            itemMeta.setDisplayName(Api.fixColor(vapeItemList.get(argument).getItem_name().replace("%owner%", secondPlayer.getName())));
                            int durability = vapeItemList.get(argument).getDurability();
                            itemMeta.setLore(Api.fixColor(vapeItemList.get(argument).getItem_lore().stream().map(element -> element.replace("{durability}", String.valueOf(durability))).collect(Collectors.toList())));
                        }).getItemStack();

                        Api.giveOrDrop(secondPlayer, vape);
                    } else wrongUsage();
                } else wrongUsage();
            } else offlinePlayer();
        } else wrongUsage();
    }

    private class VapeTask extends BukkitRunnable {
        private final Player player;
        private int tick = 0;
        private int duration;
        private int power;

        public VapeTask(Player player, int duration, int power) {
            this.player = player;
            this.duration = duration;
            this.power = power;
        }

        @Override
        public void run() {
            if (tick >= duration) {
                this.cancel();
                return;
            }

            Vector direction = player.getLocation().getDirection();

            for (double i = 0; i < power; i++) {
                Vector offset = direction.clone().multiply(i);
                player.getWorld().spawnParticle(Particle.CLOUD, player.getEyeLocation().add(offset), 10, 0.2, 0.2, 0.2, 0);
            }
            tick++;
        }
    }

    @EventHandler
    public void onInteractVape(PlayerInteractEvent event) {
        ItemStack itemInHand = event.getPlayer().getItemInHand();
        Player player = event.getPlayer();

        if (event.getAction().isRightClick()) {

            if (itemInHand != null && itemInHand.getType() != Material.AIR && itemInHand.getAmount() > 0) {

                List<VapeItem> vapeItemsList = Main.pluginVapes.getItems().getVapeItems().getItems();
                for (VapeItem vapeItem : vapeItemsList) {

                    ItemHelper itemHelper = ItemHelper.edit(itemInHand);

                    itemHelper.editNbtTagCompound(nbtItem -> {
                        if (nbtItem.hasCustomNbtData()) {
                            if (nbtItem.hasNBTData()) {
                                if (itemInHand.getType().equals(vapeItem.getItem_material())) {

                                    if (nbtItem.getInteger("vape") > 1) {
                                        event.setCancelled(true);
                                        event.setUseInteractedBlock(Event.Result.DENY);
                                        event.setUseItemInHand(Event.Result.DENY);


                                        int newDurability = nbtItem.getInteger("vape") - 1;

                                        nbtItem.removeKey("vape");
                                        nbtItem.setInteger("vape", newDurability);
                                        nbtItem.applyNBT(itemInHand);

                                        ItemMeta itemMeta = itemInHand.getItemMeta();

                                        List<String> vapeLore = vapeItem.getItem_lore();

                                        vapeLore = vapeLore.stream().map(element -> element.replace("{durability}", String.valueOf(newDurability))).collect(Collectors.toList());

                                        List<String> newVapeLore = vapeLore;

                                        itemMeta.setLore(Api.fixColor(newVapeLore));
                                        player.getItemInHand().setItemMeta(itemMeta);

                                        World world = player.getWorld();
                                        world.playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, 1, 1);
                                        world.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);

                                        new VapeTask(player, vapeItem.getDuration(), vapeItem.getPower()).runTaskTimer(Main.getPlugin(), 0, 1);

                                        Api.sendMessage(player, "&7&oAle chmura, chyba jestem prawdziwym vaperem..");
                                    } else {
                                        new VapeTask(player, vapeItem.getDuration(), vapeItem.getPower()).runTaskTimer(Main.getPlugin(), 0, 1);

                                        event.setCancelled(true);
                                        event.setUseInteractedBlock(Event.Result.DENY);
                                        event.setUseItemInHand(Event.Result.DENY);
                                        player.getInventory().remove(itemInHand);
                                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNiestety twój vape już Ci się skończył!");
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}
