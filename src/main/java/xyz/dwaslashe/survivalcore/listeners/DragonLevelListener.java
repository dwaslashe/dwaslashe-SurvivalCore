package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.*;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.DragonLevelCache;
import xyz.dwaslashe.survivalcore.objects.DragonLevel;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;

import java.util.Arrays;

public class DragonLevelListener implements Listener {

    public static ItemStack elytra = new ItemApi(Material.ELYTRA).setAmount(1).setName(Api.fixColor("&#f04de5Elytra")).getItemStack();

    @EventHandler
    public void deathDragon(EntityDeathEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.EnderDragon) {
            LivingEntity livingEntity = event.getEntity();
            Location dragondeathlocation = livingEntity.getLocation();
            Item item = Bukkit.getWorld("world_the_end").dropItem(dragondeathlocation, elytra);
            item.setCustomNameVisible(true);
            item.setCustomName(Api.fixColor("&#f04de5Elytra"));
            item.setVisualFire(true);
            item.setGlowing(true);
            item.setGravity(false);
            item.setPickupDelay(220);
            Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
                public void run() {
                    item.setGravity(true);
                }
            },  200L, 60L);
        }
    }

    @EventHandler
    public void dragonSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.EnderDragon) {
            DragonLevel dragonLevel = DragonLevelCache.getInstance().compute("ender_dragon");
            Entity dragon = event.getEntity();
            int level = dragonLevel.getLevel();
            dragonLevel.setLevel(level + 1);
            int health = level * 20 + 200;
            ((Attributable) dragon).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
            dragon.setCustomName(Api.fixColor("Ender Dragon &d(poziom {LEVEL})").replace("{LEVEL}", String.valueOf(level + 1)));
            ((Damageable) dragon).setHealth(health);

            Api.sendBroadcast(
                    "\n        &#f04de5&lENDER DRAGON\n \n&8>> &#8dfa52Smok się odrodził, życie smoka &#f2482e{HEALTH} &d(poziom {LEVEL})\n "
                            .replace("{HEALTH}", ((Damageable) dragon).getHealth() + "").replace("{LEVEL}", String.valueOf(dragonLevel.getLevel())));
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void removeElytras(ChunkPopulateEvent event) {
        if (event.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
            Entity[] var2 = event.getChunk().getEntities();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Entity entity = var2[var4];
                if (entity instanceof ItemFrame) {
                    ItemStack item = ((ItemFrame)entity).getItem();
                    if (item.getType().equals(Material.ELYTRA)) {
                        entity.remove();
                    }
                }
            }

        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null) return;
        String owner = item.getItemMeta().getLore().get(1).replace("Właściciel:", "").replace(" ", "").replace("§x§E§7§E§7§E§7§x§9§D§F§8§9§F", "");
        if (item.getType() == Material.ELYTRA && item.hasItemMeta()) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                if (!player.getName().equalsIgnoreCase(owner)) {
                    event.setCancelled(true);
                    player.sendMessage(Api.fixColor(" &8>> &cNie możesz użyć tej elytry bo nie jest twoja!"));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null) return;
        String owner = item.getItemMeta().getLore().get(1).replace("Właściciel:", "").replace(" ", "").replace("§x§E§7§E§7§E§7§x§9§D§F§8§9§F", "");
        if (item.getType() == Material.ELYTRA) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                if (!player.getName().equalsIgnoreCase(owner)) {
                    event.setCancelled(true);
                    player.sendMessage(Api.fixColor(" &8>> &cNie możesz użyć tej elytry bo nie jest twoja!"));
                }
            }
        }
    }

    @EventHandler
    public void pickupItem(PlayerPickupItemEvent event) {
        Item item = event.getItem();
        Player player = event.getPlayer();
        if (item.getItemStack().isSimilar(elytra)) {
            lock(item.getItemStack(), player);
        }
    }

    private void lock(ItemStack item, Player owner) {
        DragonLevel dragonLevel = DragonLevelCache.getInstance().compute("ender_dragon");
        int level = dragonLevel.getLevel();
        ItemMeta itemMeta = item.getItemMeta();
        String ownerName = owner.getName();
        itemMeta.setLore(Api.fixColor(Arrays.asList(
                "",
                " &#E7E7E7Właściciel: &#9DF89F" + ownerName,
                " &#E7E7E7Poziom: &d" + level,
                "",
                " &#fa3d28Elytra może być używana przez gracza",
                " &#fa3d28który po zabiciu smoka pierwszy raz",
                " &#fa3d28ją podniesie!")));
        item.setItemMeta(itemMeta);
    }
}
