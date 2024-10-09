package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import lombok.Getter;
import lombok.Setter;
import me.badbones69.blockparticles.api.ParticleManager;
import me.dexuby.UltimateDrugs.api.DrugPlantPlantEvent;
import net.brcdev.shopgui.event.ShopPostTransactionEvent;
import net.brcdev.shopgui.shop.ShopManager;
import net.brcdev.shopgui.shop.ShopTransactionResult;
import net.saidora.api.helpers.ItemHelper;
import net.saidora.api.helpers.MathHelper;
import net.saidora.economy.manager.UserManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.maxgamer.quickshop.api.event.ShopPurchaseEvent;
import pl.minecodes.plots.api.event.entry.PrePlotEntryEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.MarryCache;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.cache.WarpCache;
import xyz.dwaslashe.survivalcore.commands.EventCommand;
import xyz.dwaslashe.survivalcore.commands.managers.CommandManager;
import xyz.dwaslashe.survivalcore.objects.*;
import xyz.dwaslashe.survivalcore.utils.*;

import java.time.ZonedDateTime;
import java.util.*;

import static org.bukkit.Bukkit.getServer;

@Getter @Setter
public class OthersListener implements Listener {
    protected String appendDigit(int i) {
        return i <= 9 ? "0" + i : i + "";
    }

    public static final List<Player> cancel = Lists.newArrayList();
    private int id = 0;

    protected static final Map<String, Long> delayHook = Maps.newHashMap();

    public static ItemStack pokeBall = new ItemApi(Material.SNOWBALL)
            .setName("&#ee1515Poke&#f0f0f0Ball")
            .setLore(Arrays.asList("", " &#E7E7E7Masz &#9DF89F20% &#E7E7E7szans na złapanie zwierzęcia w jajko", " &#E7E7E7wyrzucając &#ee1515Poke&#f0f0f0Balla &#E7E7E7prosto w zwierzecie!"))
            .getItemStack();

    @EventHandler
    public void onDamageHusband(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = ((Player) event.getDamager()).getPlayer();
            Player husband = ((Player) event.getEntity()).getPlayer();
            Marry marry = MarryCache.getInstance().compute(player.getUniqueId());

            if (marry.getRightuuid() == null) return;
            if (marry.getPvp() == null) return;

            if (marry.getRightuuid().equals(husband.getUniqueId()) && marry.getPvp().equals("NO")) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz uderzyć swojego małżonka bo masz wyłączoną walke między wami. Aby ja włączyć wpisz &#fcb419/slub pvp");
                event.setCancelled(true);
                event.setDamage(0);
            }
        }

    }

    @EventHandler
    public void onEnterPlot(PrePlotEntryEvent event) {
        Player player = event.getPlayer();
        if (event.getPlot().isClosed()) {
            player.leaveVehicle();
        }
    }

    @EventHandler
    public void handleCommandTabSent(PlayerCommandSendEvent event) {
        Player p = event.getPlayer();
        if (Main.pluginConfig.getEvents().isTabComplete()) {
            if (!p.hasPermission("core.command.tabcomplete.bypass")) {
                for (String string : Main.pluginConfig.getChat().getBlocktabcommands()) {
                    event.getCommands().remove(string);
                }
            }
        }
    }

    @EventHandler
    public void handleCommandUseEvent(PlayerCommandPreprocessEvent event) {
        String commandName = event.getMessage().split(" ")[0].replaceFirst("/", "");
        Command command = CommandManager.commandMap.getCommand(commandName);
        if (command == null) return;
        //if (!command.getPermission().isEmpty()) return;
        if (!command.testPermissionSilent(event.getPlayer())) {
            if (command.getPermission() == null) return;
            event.getPlayer().sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&f楹 &#FF3131Nie posiadasz uprawnień &8(&#FFC42E{permission}&8) &f楹".replace("{permission}", command.getPermission())));
            event.setCancelled(true);
        } else return;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerDamageEvent(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();
            if (victim.getLocation().getWorld().getName().equals("spawn") && !RegionApi.isInRegion(victim.getLocation(), "pvp")) {
                Player player = ((Player) event.getDamager()).getPlayer();
                if (delayHook.containsKey(player.getName()) && delayHook.get(player.getName()) > System.currentTimeMillis()) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby zaczepić gracza musisz poczekać &#fcb419{TIME} &fᎠ".replace("{TIME}", TimerApi.secondsToString(delayHook.get(player.getName()))));
                    player.closeInventory();
                    return;
                }

                delayHook.remove(player.getName());

                Api.sendMessage(player,  Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zaczepiłeś gracza");
                victim.sendTitle(Api.fixColor("&#95eb34&lHej &f\uE214"), Api.fixColor("&f楸 &#4cf739Gracz &#fcb419" + player.getDisplayName() + "&#4cf739 zaczepił Cię! &f楸"));
                victim.playSound(victim.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);

                delayHook.put(player.getName(), TimerApi.parseDateDiff("5s", true));
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (Main.pluginConfig.getEvents().isUnknownCommand()) {
            if (Bukkit.getHelpMap().getHelpTopic(e.getMessage().split(" ")[0]) == null) {
                Player p = e.getPlayer();
                e.setCancelled(true);
                p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&f楹 &#FF3131Komenda &#FFC42E" + e.getMessage().split(" ")[0] + " &#FF3131nie istnieje &f楹"));
                if (Main.pluginConfig.getEvents().isBossBarUnknownCommand()) {
                    BossBar bar = Bukkit.createBossBar(Api.fixColor("&8>> &#FF3131Komenda &#FFC42E" + e.getMessage().split(" ")[0] + " &#FF3131nie istnieje &8<<"), BarColor.RED, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                    bar.addPlayer(p.getPlayer());
                    bar.setProgress(1);
                    int[] bar_color = {0};
                    Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                        if (p.getPlayer() != null && p.getPlayer().isOnline()) {
                            if (bar.getProgress() > 0.02) {
                                bar.setProgress(bar.getProgress() - 0.02);
                                ++bar_color[0];
                                if (bar_color[0] == 1) {
                                    bar.setColor(BarColor.WHITE);
                                } else if (bar_color[0] == 2) {
                                    bar.setColor(BarColor.BLUE);
                                } else if (bar_color[0] == 3) {
                                    bar.setColor(BarColor.GREEN);
                                } else if (bar_color[0] == 4) {
                                    bar.setColor(BarColor.PINK);
                                } else if (bar_color[0] == 5) {
                                    bar.setColor(BarColor.PURPLE);
                                } else if (bar_color[0] == 6) {
                                    bar.setColor(BarColor.RED);
                                } else {
                                    bar_color[0] = 0;
                                }
                            } else {
                                bar.setVisible(false);
                                bar.removePlayer(p.getPlayer());
                            }
                        } else {
                            bar.removePlayer(p.getPlayer());
                        }
                    }, 0, 2);
                }
            }
        }
    }

    @EventHandler
    public void onSignColor(SignChangeEvent e) {
        Player p = e.getPlayer();
        String[] lines = e.getLines();
        if (Main.pluginConfig.getEvents().isSignColor()) {
            for (int n = 0; n <= 3; n++)
                if (p.hasPermission("core.sign.color") == true) {
                    e.setLine(n, Api.fixColor(lines[n]));
                }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (cancel.contains(event.getWhoClicked()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePrepareAnvilEvent(PrepareAnvilEvent event){
        AnvilInventory inventory = event.getInventory();
        if(inventory.getFirstItem() == null || inventory.getSecondItem() == null) return;

        inventory.setMaximumRepairCost(99);

        ItemStack first = inventory.getFirstItem(), second = inventory.getSecondItem();

        int cost = 0;

        if(first.getItemMeta() instanceof Repairable repairable){
            cost = repairable.getRepairCost();
        }
        if(second.getItemMeta() instanceof Repairable repairable){
            cost = repairable.getRepairCost();
        }

        if(cost <= 0) cost = 5;
        if(cost > 39) cost = 39;
        inventory.setRepairCost(cost);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        cancel.remove(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClickAbyss(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        Optional.ofNullable(Abyss.getOpenAbyssMap().get(e.getWhoClicked().getName())).ifPresent(abyss -> abyss.onAbyssClick(e));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCloseAbyss(InventoryCloseEvent e) {
        Optional.ofNullable(Abyss.getOpenAbyssMap().get(e.getPlayer().getName())).ifPresent(abyss -> Abyss.getOpenAbyssMap().remove(e.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!event.isCancelled()) {
            if (Main.pluginConfig.getEvents().getOpenChatBlockBreak().isEnable()) {
                User user = UserCache.getInstance().compute(player.getUniqueId());
                if (user.getBlockBreak() != (Main.pluginConfig.getEvents().getOpenChatBlockBreak().getBreakMaxBlocks() + 2)) {
                    user.addBlockBreak(1);
                }

            }

            if (Main.pluginConfig.getEvents().isAntyXrayMessage()) {
                if (event.getBlock().getType() == Material.DIAMOND_ORE || event.getBlock().getType() == Material.GOLD_ORE || event.getBlock().getType() == Material.IRON_ORE || event.getBlock().getType() == Material.DEEPSLATE_DIAMOND_ORE || event.getBlock().getType() == Material.DEEPSLATE_GOLD_ORE || event.getBlock().getType() == Material.DEEPSLATE_IRON_ORE || event.getBlock().getType() == Material.ANCIENT_DEBRIS) {
                    for (Player permissionPlayers : Bukkit.getOnlinePlayers()) {
                        if (permissionPlayers.hasPermission("core.xray.read")) {
                            Api.sendActionBar(permissionPlayers, "&f楹 <#ba2e22>&lANTY-XRAY: <reset><#39FF14>Gracz <reset><#FDBD01>" + player.getName() + " <reset><#39FF14>zniszczył rude <reset><#3ec7ed>" + event.getBlock().getType() + " <reset>&f楹");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFromTo(BlockFromToEvent e) {
        Material type = e.getBlock().getType();
        if (Main.pluginConfig.getEvents().isLavaGrieffing()) {
            if (e.getBlock().getY() >= 50) {
                if (type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER || type == Material.LAVA || type == Material.LEGACY_STATIONARY_LAVA) {
                    Block b = e.getToBlock();
                    if (b.getType() == Material.AIR) {
                        if (generatesCobble(type, b)) {
                            e.setCancelled(true);
                        }
                    }
                } else if (type == Material.LAVA || type == Material.LEGACY_STATIONARY_LAVA) {
                    Block b = e.getToBlock();
                    if (b.getType() == Material.AIR) {
                        if (lavaFlow(type, b)) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    private final BlockFace[] faces = new BlockFace[]{
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    public boolean generatesCobble(Material type, Block b) {
        Material mirrorID1 = (type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER ? Material.LAVA : Material.WATER);
        Material mirrorID2 = (type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER ? Material.LEGACY_STATIONARY_LAVA : Material.LEGACY_STATIONARY_WATER);
        for (BlockFace face : faces) {
            Block r = b.getRelative(face, 1);
            if (r.getType() == mirrorID1 || r.getType() == mirrorID2) {
                return true;
            }
        }
        return false;
    }

    public boolean lavaFlow(Material type, Block b) {
        for (BlockFace face : faces) {
            Block r = b.getRelative(face, 1);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (Main.pluginConfig.getEvents().isDeathPlayerHead()) {
            ZonedDateTime timeZone = TimerApi.getZoneDate("GMT+1");
            if (e.getEntity() instanceof Player) {
                ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setOwner(e.getEntity().getName());
                meta.setDisplayName(Api.fixColor("&#f5b042Głowa gracza: &#eef743" + e.getEntity().getName()));
                meta.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Data: &#ffd56c" + appendDigit(timeZone.getHour()) + ":" +
                        appendDigit(timeZone.getMinute()) + ", " +
                        appendDigit(timeZone.getDayOfMonth()) + "/" +
                        appendDigit(timeZone.getMonthValue()) + "/" +
                        appendDigit(timeZone.getYear()) + " &fᎠ", "", " &#9c9898" + e.getEntity().getPlayer().getUniqueId())));
                item.setItemMeta((ItemMeta) meta);
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
            }
        }
    }

    //Teleport player to spawn when don't have bed spawn location

    @EventHandler
    public void onReSpawnPlayer(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (Main.pluginConfig.getEvents().isRespawnTeleportSpawn()) {
            if (p.getBedSpawnLocation() == null) {
                Warp warp = WarpCache.getInstance().get("spawn");
                if (warp != null) {
                    Location loc = new Location(getServer().getWorld(warp.getLocation().getWorld().getKey()), warp.getLocation().getX(), warp.getLocation().getY(), warp.getLocation().getZ(), warp.getLocation().getYaw(), warp.getLocation().getPitch());
                    e.setRespawnLocation(loc);
                } else Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Nie ma warpa &#fcb419spawn");
            }
        }
    }

    //Place block in Nether & End

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (Main.pluginConfig.getEvents().isBlockPlaceSetAir()) {
            if (!player.hasPermission("core.place.nether")) {
                World nether = Bukkit.getWorld("world_nether");
                if (nether.getName().equalsIgnoreCase(event.getBlock().getWorld().getName())) {
                    Api.sendActionBar(player, "&f楹 <#fc2419>Postawiony blok zniknie za  <#FDBD01>60 sekund &fᎠ &f楹");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            event.getBlock().setType(Material.AIR);
                        }
                    }.runTaskLater(Main.getPlugin(), 20 * 60);
                } else if (!player.hasPermission("core.place.end")) {
                    World end = Bukkit.getWorld("world_the_end");
                    if (end.getName().equalsIgnoreCase(event.getBlock().getWorld().getName())) {
                        Api.sendActionBar(player, "&f楹 <#fc2419>Postawiony blok zniknie za  <#FDBD01>60 sekund &fᎠ &f楹");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                event.getBlock().setType(Material.AIR);
                            }
                        }.runTaskLater(Main.getPlugin(), 20 * 60);
                    }
                }
            }
        }
    }

    //Protection player

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Protection protection;
        if(event.getDamager() instanceof Player damager){
            protection = Protection.get(damager.getUniqueId());

            if(protection != null && protection.getProtection() > System.currentTimeMillis()){
                if (RegionApi.isInRegion(damager.getLocation(), "pvp")) return;

                Api.sendMessage(damager, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz uderzać mając ochrone!");
                event.setCancelled(true);
            } else if(event.getEntity() instanceof Player victim){
                if (RegionApi.isInRegion(victim.getLocation(), "pvp")) return;

                protection = Protection.get(victim.getUniqueId());
                if(protection != null && protection.getProtection() > System.currentTimeMillis()){
                    Api.sendMessage(damager, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz uderzyć graczy, który ma ochrone!");
                    event.setCancelled(true);
                }
            }
        }
        if(event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player damager){
            protection = Protection.get(damager.getUniqueId());
            if(protection != null && protection.getProtection() > System.currentTimeMillis()){
                Api.sendMessage(damager, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz uderzać mając ochrone!");
                event.setDamage(0);
            } else if(event.getEntity() instanceof Player victim){
                protection = Protection.get(victim.getUniqueId());
                if(protection != null && protection.getProtection() > System.currentTimeMillis()){
                    Api.sendMessage(damager, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz uderzyć graczy, który ma ochrone!");
                    event.setDamage(0);
                }
            }
        }
        if(event.getDamager() instanceof Monster monster && event.getEntity() instanceof Player victim){
            protection = Protection.get(victim.getUniqueId());
            if(protection != null && protection.getProtection() > System.currentTimeMillis()) {
                monster.setTarget(null);
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void handleTargetEvent(EntityTargetLivingEntityEvent event){
        Entity target = event.getTarget(), entity = event.getEntity();
        if(entity instanceof Mob && target instanceof Player player){
            Protection protection = Protection.get(player.getUniqueId());
            if(protection != null && protection.getProtection() > System.currentTimeMillis()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreakMeteor(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.MAGMA_BLOCK && event.getBlock().hasMetadata("MeteorBlock")) {
            Player player = event.getPlayer();
            event.setCancelled(true);

            int health = event.getBlock().getMetadata("MeteorBlock").get(0).asInt();
            health--;


            if (DHAPI.getHologram("meteor").isEnabled()) {
                List<String> lines = Arrays.asList("&#8334eb&lEVENT METEORYT", "", "&#a06ee0Pozostałe życie &#f02f22{health}❤".replace("{health}", String.valueOf(health)));
                int finalHealth = health;
                lines.stream().map(element -> element.replace("{health}", String.valueOf(finalHealth)));

                Hologram hologram = DHAPI.getHologram("meteor");
                DHAPI.setHologramLines(hologram, lines);
                hologram.save();
            }

            if (health <= 0) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie znisczyłeś meteoryt!");
                event.getBlock().setType(Material.AIR);
                EventCommand.eventMap.remove("METEORYT");
                ParticleManager.getInstance().removeParticle("meteorBlock");
                DHAPI.removeHologram("meteor");
                Location dropLocation = event.getBlock().getLocation().clone().add(0, 1, 0);
                dropLocation.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                for (ItemStack itemStack : Main.pluginEvents.getListItemMeteor().itemStackList) {
                    dropLocation.getWorld().dropItemNaturally(dropLocation, itemStack).setVelocity(new Vector(
                            MathHelper.getRandomDouble(-0.2, 0.2),
                            MathHelper.getRandomDouble(0.1, 0.4),
                            MathHelper.getRandomDouble(-0.2, 0.2)));
                }
            } else {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zadałeś obrażenia metorytowi, pozostałe życie &#f02f22" + health + "❤");
                event.getBlock().removeMetadata("MeteorBlock", Main.getPlugin());
                event.getBlock().setMetadata("MeteorBlock", new FixedMetadataValue(Main.getPlugin(), health));
            }
        }
    }

}
