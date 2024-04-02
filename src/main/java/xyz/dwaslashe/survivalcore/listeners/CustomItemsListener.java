package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Maps;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.saidora.api.helpers.ItemHelper;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RandomApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.*;

public class CustomItemsListener implements Listener {

    //Deny interact heads items
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (event.getAction() != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (itemInHand != null && itemInHand.getType() != Material.AIR && itemInHand.getAmount() > 0) {
                ItemHelper itemHelper = ItemHelper.edit(itemInHand);
                itemHelper.editNbtTagCompound(nbtItem -> {
                    if (nbtItem.hasKey("cid")) {
                        if (nbtItem.getInteger("cid") == 10 || nbtItem.getInteger("cid") == 9 || nbtItem.getInteger("cid") == 8) {
                            event.setCancelled(true);
                            event.setUseItemInHand(Event.Result.DENY);
                            event.setUseInteractedBlock(Event.Result.DENY);
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz postawić tego bloku ponieważ jest to specjalny przedmiot!");
                        }
                    }
                });
            }
        }
    }

    //PokeBall
    static Set<UUID> snowballs = new HashSet<>(), shooters = new HashSet<>();
    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (shooters.contains(player.getUniqueId())) {
                shooters.remove(player.getUniqueId());
                snowballs.add(event.getEntity().getUniqueId());
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> snowballs.remove(event.getEntity().getUniqueId()), 20 * 20);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            Player shooter = (Player) snowball.getShooter();
            Entity hitEntity = event.getHitEntity();

            if (hitEntity != null && hitEntity instanceof Animals) {
                Animals animal = (Animals) hitEntity;
                EntityType entityType = animal.getType();

                if (snowballs.contains(snowball.getUniqueId())) {
                    if (RandomApi.getChance(20)) {
                        animal.remove();
                        ItemStack egg = new SpawnEgg(entityType).toItemStack(1);
                        hitEntity.getWorld().dropItem(hitEntity.getLocation(), egg);
                        Api.sendMessage(shooter, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie udało Ci się schować zwierzęcie w jajku!");
                    } else Api.sendMessage(shooter, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Niestety nie miałeś szcześćia, spróbuj następnym razem!");
                }
            }
        }
    }

    //Magnet
    //@EventHandler
    //public void onBreakBlock(BlockDropItemEvent event) {
    //    List<Item> items = event.getItems();
    //    Player player = event.getPlayer();
    //    if (player.getInventory().contains(OthersListener.magnet)) {
    //        if (event.getBlock().getType() == Material.FURNACE) {
    //            event.setCancelled(true);
    //            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz tego zrobić!");
    //        } else for (Item item : items) {
    //            item.remove();
    //            Api.giveOrDrop(event.getPlayer(), item.getItemStack());
    //        }
    //    }
    //}

    //Eksplosion Bow
    protected static final Map<String, Long> delayExplosionBow = Maps.newHashMap();
    @EventHandler
    public void onProjectileArrow(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            Entity shooter = (Entity) arrow.getShooter();
            if (shooter instanceof Player) {
                Player player = (Player) shooter;
                ItemStack itemInHand = player.getItemInHand();
                ItemHelper itemHelper = ItemHelper.edit(itemInHand);
                itemHelper.editNbtTagCompound(nbtItem -> {
                    if (nbtItem.hasCustomNbtData()) {
                        if (nbtItem.hasNBTData()) {
                            if (nbtItem.getInteger("cid") == 13) {
                                if (delayExplosionBow.containsKey(player.getName()) && delayExplosionBow.get(player.getName()) > System.currentTimeMillis()) {
                                    if (player.hasPermission("core.cooldown.bypass")) return;
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby ponownie użyć wybuchowego łuku musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayExplosionBow.get(player.getName()))));
                                    event.setCancelled(true);
                                    player.closeInventory();
                                    return;
                                }
                                delayExplosionBow.remove(player.getName());

                                LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
                                com.sk89q.worldedit.util.Location locationRegion = BukkitAdapter.adapt(arrow.getLocation());
                                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                                RegionQuery query = container.createQuery();

                                if (query.testState(locationRegion, localPlayer, Flags.BUILD)) {
                                    arrow.getWorld().createExplosion(arrow.getLocation(), 2.0f);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Strzała nie może tutaj wybuchnąć ponieważ teren jest chroniony!");

                                delayExplosionBow.put(player.getName(), TimerApi.parseDateDiff("30s", true));
                            }
                        }
                    }
                });
            }
        }
    }

    //Stormbreaker
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (damager instanceof Player) {
            Player player = (Player) damager;
            ItemStack itemInHand = player.getItemInHand();

            if (itemInHand.getType() == Material.DIAMOND_AXE) {
                ItemHelper itemHelper = ItemHelper.edit(itemInHand);
                itemHelper.editNbtTagCompound(nbtItem -> {
                    if (nbtItem.hasCustomNbtData()) {
                        if (nbtItem.hasNBTData()) {
                            if (nbtItem.getInteger("cid") == 14) {
                                if (RandomApi.getChance(40)) {
                                    event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                                    event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                                    event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    //Smocza fajerwerka
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent  event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand != null && itemInHand.getType() != Material.AIR && itemInHand.getAmount() > 0) {
            ItemHelper itemHelper = ItemHelper.edit(itemInHand);
            itemHelper.editNbtTagCompound(nbtItem -> {
                if (nbtItem.hasCustomNbtData()) {
                    if (nbtItem.hasNBTData()) {
                        if (nbtItem.getInteger("cid") == 16) {
                            if (player.isGliding()) {
                                player.setVelocity(player.getLocation().getDirection().multiply(2.0D));
                                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0F, 1.0F);
                            }
                            event.setCancelled(true);
                        }
                    }
                }
            });
        }
    }

    //Froze Bow
    protected static final Map<String, Long> delayFrozeBow = Maps.newHashMap();
    @EventHandler
    public void onProjectileArrowFroze(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            Entity shooter = (Entity) arrow.getShooter();
            if (event.getHitEntity() instanceof Player) {
                if (shooter instanceof Player) {
                    Player hitPlayer = (Player) event.getHitEntity();
                    Player player = (Player) shooter;
                    ItemStack itemInHand = player.getItemInHand();
                    ItemHelper itemHelper = ItemHelper.edit(itemInHand);
                    itemHelper.editNbtTagCompound(nbtItem -> {
                        if (nbtItem.hasCustomNbtData()) {
                            if (nbtItem.hasNBTData()) {
                                if (nbtItem.getInteger("cid") == 17) {
                                    if (delayFrozeBow.containsKey(player.getName()) && delayFrozeBow.get(player.getName()) > System.currentTimeMillis()) {
                                        if (player.hasPermission("core.cooldown.bypass")) return;
                                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby ponownie użyć łuku zamrożenia musisz poczekać &#fcb419{TIME}".replace("{TIME}", TimerApi.secondsToString(delayExplosionBow.get(player.getName()))));
                                        event.setCancelled(true);
                                        player.closeInventory();
                                        return;
                                    }
                                    delayFrozeBow.remove(player.getName());
                                    hitPlayer.setFreezeTicks(100);
                                    hitPlayer.sendTitle(Api.fixColor("&b&lZAMROŻONY"), Api.fixColor("&8>> &bJesteś zamrożony na &#ffd56c2 sekundy &fᎠ &8<<"));
                                    hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 50, false, false, false));
                                    delayFrozeBow.put(player.getName(), TimerApi.parseDateDiff("30s", true));
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}
