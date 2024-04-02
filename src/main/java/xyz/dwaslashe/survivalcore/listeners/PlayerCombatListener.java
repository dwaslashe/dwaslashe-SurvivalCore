package xyz.dwaslashe.survivalcore.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import es.pollitoyeye.vehicles.events.VehicleEnterEvent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pl.minecodes.plots.api.event.entry.PrePlotEntryEvent;
import pl.minecodes.plots.api.event.leave.PrePlotLeaveEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.objects.Logout;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RegionApi;

import java.util.*;

public class PlayerCombatListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Logout logout = Logout.get(e.getPlayer());
        if (logout.getTime() > System.currentTimeMillis() && e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            List<Material> types = Arrays.asList(Material.FURNACE, Material.CHEST, Material.SHULKER_BOX, Material.HOPPER, Material.DROPPER, Material.DISPENSER, Material.BARREL, Material.TRAPPED_CHEST, Material.ENDER_CHEST, Material.NOTE_BLOCK);
            if (types.contains(e.getClickedBlock().getType())) {
                e.getPlayer().closeInventory();
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                Api.sendMessage(e.getPlayer(), Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Interakcja z tym blokiem podczas pvp jest zablokowana");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0F, 1.0F);
            }
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        Logout logout = Logout.get(e.getPlayer());
        if (logout.getTime() > System.currentTimeMillis()) {
            if (e.getBlock().getType() == Material.NOTE_BLOCK) {
                Api.sendMessage(e.getPlayer(), Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Interakcja z tym blokiem podczas pvp jest zablokowana");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0F, 1.0F);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && !e.isCancelled() && (e.getDamager() instanceof Player)) {
            if (((Player) e.getDamager()).getPlayer().getWorld().equals("spawn")) return;
            if (((Player) e.getEntity()).getPlayer().getWorld().equals("spawn")) return;

            Logout logout = Logout.get(((Player) e.getDamager()).getPlayer());
            Logout logout_damager = Logout.get(((Player) e.getEntity()).getPlayer());

            ((Player) e.getDamager()).getPlayer().setFlying(false);
            ((Player) e.getEntity()).getPlayer().setFlying(false);

            logout.setTime("20s");
            logout_damager.setTime("20s");
            if (e.getDamager() instanceof Player) {
                logout.setAttacker(((Player) e.getDamager()).getPlayer());
                logout_damager.setAttacker(((Player) e.getDamager()).getPlayer());
            }
            logout.create();
            logout_damager.create();
        }

        if (e.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player) {
            if (e.getDamager() instanceof Player) {
                if (((Player) e.getDamager()).getPlayer().getWorld().equals("spawn")) return;
                if (((Player) e.getEntity()).getPlayer().getWorld().equals("spawn")) return;
                Logout logout = Logout.get(((Player) e.getDamager()).getPlayer());
                Logout logout_damager = Logout.get(((Player) e.getEntity()).getPlayer());

                ((Player) e.getDamager()).getPlayer().setFlying(false);
                ((Player) e.getEntity()).getPlayer().setFlying(false);

                logout.setTime("20s");
                logout_damager.setTime("20s");

                if (e.getDamager() instanceof Player) {
                    logout.setAttacker(((Player) e.getDamager()).getPlayer());
                    logout_damager.setAttacker(((Player) e.getDamager()).getPlayer());
                }
                logout.create();
                logout_damager.create();
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Logout logout = Logout.get(e.getPlayer());
        String command = e.getMessage().split(" ")[0].toLowerCase();
        if (logout.getTime() > System.currentTimeMillis()) {
            for (String string : Main.pluginConfig.getAntylogout().getCommands()) {
                if (string.toLowerCase().equalsIgnoreCase(command)) {
                    Api.sendMessage(e.getPlayer(), Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Komenda jest wyłączona podczas walki!");
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }

    //@EventHandler
    //public void onEnterVehicle(VehicleEnterEvent event) {
    //    Player player = event.getPlayer();
    //    Logout logout = Logout.get(player);
    //    if (logout.getTime() > System.currentTimeMillis()) {
    //        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz wsiąść do pojazdu podczas walki!");
    //        event.setCancelled(true);
    //    }
    //}

    @EventHandler
    public void onGliding(EntityToggleGlideEvent e){
        Player player = (Player) e.getEntity();
        Logout logout = Logout.get(player);

        if (e.isGliding()) {
            if (logout.getTime() > System.currentTimeMillis()) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz latać podczas walki!");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onListening(PlayerMoveEvent event) {
        Logout logout = Logout.get(event.getPlayer());
        Location locationTo = event.getTo();
        Location locationFrom = event.getFrom();
        int xTo = locationTo.getBlockX();
        int yTo = locationTo.getBlockY();
        int zTo = locationTo.getBlockZ();
        int xFrom = locationFrom.getBlockX();
        int yFrom = locationFrom.getBlockY();
        int zFrom = locationFrom.getBlockZ();
        if (xTo != xFrom || yTo != yFrom || zTo != zFrom) {
            Player player = event.getPlayer();
            if (logout.getTime() > System.currentTimeMillis()) {
                for (String unavailableRegion : Main.pluginConfig.getAntylogout().getRegions()) {
                    if (RegionApi.isInRegion(locationTo, unavailableRegion)) {
                        Api.sendMessage(event.getPlayer(), Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Ten region jest niedostępny podczas walki!");
                        Vector playerDirection = player.getLocation().getDirection();
                        //player.setVelocity(playerDirection.multiply(-15));
                        createBarrier(player, locationTo);
                        player.setVelocity(event.getTo().toVector().subtract(locationFrom.toVector()).multiply(-2));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0F, 1.0F);
                        //event.setCancelled(true);
                    }
                }
            } else {
                removeBarrier(player);
            }
        }
    }

    private Map<UUID, List<Location>> barrierLocations = new HashMap<>();

    private void createBarrier(Player player, Location location) {
        World world = player.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        List<Location> barrierLocationsForPlayer = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Location blockLocation = new Location(world, x, y + i, z);
            if (blockLocation.getBlock().getType() == Material.AIR) {
                player.sendBlockChange(blockLocation, Material.BARRIER.createBlockData());
                // Dodaj lokalizację do listy, aby później usunąć bloki BARRIER
                barrierLocationsForPlayer.add(blockLocation);

                // Dodaj drugą warstwę bloków BARRIER poniżej
                Location blockLocationBelow = new Location(world, x, y - i, z);
                if (blockLocationBelow.getBlock().getType() == Material.AIR) {
                    player.sendBlockChange(blockLocationBelow, Material.BARRIER.createBlockData());
                    barrierLocationsForPlayer.add(blockLocationBelow);
                }
            }
        }

        // Dodaj listę lokalizacji do mapy, aby później usunąć bloki BARRIER
        barrierLocations.putIfAbsent(player.getUniqueId(), new ArrayList<>());
        barrierLocations.get(player.getUniqueId()).addAll(barrierLocationsForPlayer);
    }

    private void removeBarrier(Player player) {
        // Usuń bloki BARRIER, jeśli istnieją dla danego gracza
        if (barrierLocations.containsKey(player.getUniqueId())) {
            for (Location location : barrierLocations.get(player.getUniqueId())) {
                player.sendBlockChange(location, location.getBlock().getBlockData());
            }
            barrierLocations.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onEnterPlot(PrePlotEntryEvent event) {
        Logout logout = Logout.get(event.getPlayer());
        Player player = event.getPlayer();

        if (logout.getTime() > System.currentTimeMillis()) {
            Api.sendMessage(event.getPlayer(), Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Ten region jest niedostępny podczas walki!");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0F, 1.0F);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeavePlot(PrePlotLeaveEvent event) {
        Logout logout = Logout.get(event.getPlayer());
        Player player = event.getPlayer();
        if (logout.getTime() > System.currentTimeMillis()) {
            Api.sendMessage(event.getPlayer(), Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Ten region jest niedostępny podczas walki!");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0F, 1.0F);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Logout logout = Logout.get(e.getPlayer());
        Location locationTo = e.getTo();
        Location locationFrom = e.getFrom();
        int xTo = locationTo.getBlockX();
        int yTo = locationTo.getBlockY();
        int zTo = locationTo.getBlockZ();
        int xFrom = locationFrom.getBlockX();
        int yFrom = locationFrom.getBlockY();
        int zFrom = locationFrom.getBlockZ();
        if (xTo != xFrom || yTo != yFrom || zTo != zFrom) {
            Player player = e.getPlayer();
            if (logout.getTime() > System.currentTimeMillis()) {
                for (String unavailableRegion : Main.pluginConfig.getAntylogout().getRegions()) {
                    if (RegionApi.isInRegion(locationTo, unavailableRegion) && logout.getTime() > System.currentTimeMillis()) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Ten region jest niedostępny podczas walki!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0F, 1.0F);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Logout logout = Logout.get(e.getEntity().getPlayer());
        if (logout.getTime() > System.currentTimeMillis()) {
            logout.getPlayer().getWorld().strikeLightningEffect(logout.getPlayer().getLocation());
            logout.setTime("0s");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Logout logout = Logout.get(e.getPlayer());
        if (logout.getTime() > System.currentTimeMillis()) {
            e.getPlayer().setHealth(0.0D);
            e.setQuitMessage(Api.fixColor("\n        &#FF3131&lANTY-LOGOUT \n \n&8» &#8dfa52Gracz &#FFC42E" + e.getPlayer().getDisplayName() + " &#8dfa52wylogował się podczas walki!\n&8» &#8dfa52Ostatni atakujący to &#f7482d" + (logout.getAttacker() != null && logout.getAttacker().isOnline() ? logout.getAttacker().getName() : "Nie wiadomo kto") + "\n"));
            logout.remove();
        }

    }
}
