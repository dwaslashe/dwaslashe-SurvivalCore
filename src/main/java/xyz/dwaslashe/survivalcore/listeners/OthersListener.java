package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.SpawnEgg;
import org.bukkit.scheduler.BukkitRunnable;
import pl.minecodes.plots.api.event.entry.PrePlotEntryEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.WarpCache;
import xyz.dwaslashe.survivalcore.commands.managers.CommandManager;
import xyz.dwaslashe.survivalcore.objects.Abyss;
import xyz.dwaslashe.survivalcore.objects.Logout;
import xyz.dwaslashe.survivalcore.objects.Warp;
import xyz.dwaslashe.survivalcore.utils.*;

import java.time.ZonedDateTime;
import java.util.*;

@Getter @Setter
public class OthersListener implements Listener {
    protected String appendDigit(int i) {
        return i <= 9 ? "0" + i : i + "";
    }

    public static final List<Player> cancel = Lists.newArrayList();
    private int id = 0;

    protected static final Map<Player, Long> delayHook = Maps.newHashMap();

    private static ItemStack enchanted_golden_apple = new ItemApi(Material.ENCHANTED_GOLDEN_APPLE).getItemStack();


    public static ItemStack weed = new ItemApi(Material.LARGE_FERN, 1).setName("<#39ff14>Zioło 1g</#008443>").getItemStack();

    public static ItemStack kokaina = new ItemApi(Material.CLAY_BALL, 1).setName("<#FFFFFF>Kokaina 0.1g</#FFFF00>").getItemStack();

    public static ItemStack magnet = new ItemApi(Material.LIGHTNING_ROD)
            .setName("&#FF10F0Magnez")
            .setLore(Arrays.asList("", " &#E7E7E7Mając magnez w ekwipunku itemy", " &#E7E7E7które niszczysz idą do twojego ekwipunku!"))
            .getItemStack();


    public static ItemStack pokeball = new ItemApi(Material.SNOWBALL)
            .setName("&#ee1515Poke&#f0f0f0Ball")
            .setLore(Arrays.asList("", " &#E7E7E7Masz &#9DF89F20% &#E7E7E7szans na złapanie zwierzęcia w jajko", " &#E7E7E7wyrzucając &#ee1515Poke&#f0f0f0Balla &#E7E7E7prosto w zwierzecie!"))
            .getItemStack();

    static Set<UUID> snowballs = new HashSet<>(), shooters = new HashSet<>();

    @EventHandler
    public void onEnterPlot(PrePlotEntryEvent event) {
        Player player = event.getPlayer();
        if (event.getPlot().isClosed()) {
            player.leaveVehicle();
        }
    }

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
                        Api.sendMessage(shooter, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie udało Ci się schować zwierzęcie w jajku!");
                    } else Api.sendMessage(shooter, Main.pluginConfig.getMessages().getPrefix() + "&cNiestety nie miałeś szcześćia, spróbuj następnym razem!");
                }
            }
        }
    }

    @EventHandler
    public void onBreakBlock(BlockDropItemEvent event) {
        List<Item> items = event.getItems();
        Player player = event.getPlayer();
        if (player.getInventory().contains(magnet)) {
            if (event.getBlock().getType() == Material.FURNACE) {
                event.setCancelled(true);
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz tego zrobić!");
            } else for (Item item : items) {
                item.remove();
                Api.giveOrDrop(event.getPlayer(), item.getItemStack());
            }
        }
    }

    @EventHandler
    public void onCommandTabSend(PlayerCommandSendEvent event) {
        Player p = event.getPlayer();
        if (Main.pluginConfig.getEvents().isTabcomplete()) {
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
            event.getPlayer().sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &#FF3131Nie posiadasz uprawnien &8(&#FFC42E{permission}&8) &8<<".replace("{permission}", command.getPermission())));
            event.setCancelled(true);
        } else return;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerDamageEvent(EntityDamageByEntityEvent event){
        Player player = ((Player) event.getDamager()).getPlayer();
        if(event.getEntity() instanceof Player victim && event.getDamager() instanceof Player attacker) {
            if (victim.getLocation().getWorld().getName().equals("spawn") && !RegionApi.isInRegion(victim.getLocation(), "pvp")) {
                if (delayHook.containsKey(player) && delayHook.get(player) > System.currentTimeMillis()) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cAby zaczepić gracza musisz poczekać &e{TIME}".replace("{TIME}", TimerApi.secondsToString(delayHook.get(player))));
                    player.closeInventory();
                    return;
                }

                delayHook.remove(player);

                Api.sendMessage(player,  Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zaczepiłeś gracza");
                victim.sendTitle(Api.fixColor("&#95eb34&lHej"), Api.fixColor("&8>> &aGracz &e" + attacker.getDisplayName() + "&a zaczepił Cię!"));
                victim.playSound(victim.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);

                delayHook.put(player, TimerApi.parseDateDiff("5s", true));
            }
        }
    }

    public static ShapedRecipe getRecipeKokaina() {
        ShapedRecipe rec = new ShapedRecipe(NamespacedKey.minecraft("wywrotkamc_kokaina"), kokaina);
        rec.shape(new String[]{"AAA", "BBB", "AAA"});
        rec.setIngredient('A', Material.SUGAR);
        rec.setIngredient('B', Material.LEGACY_SNOW_BALL);
        return rec;
    }

    public static ShapedRecipe getRecipeWeed() {
        ShapedRecipe rec = new ShapedRecipe(NamespacedKey.minecraft("wywrotkamc_weed"), weed);
        rec.shape(new String[]{"DDD", "DDD", "DDD"});
        rec.setIngredient('D', Material.DRIED_KELP_BLOCK);
        return rec;
    }

    public static ShapedRecipe getRecipeMagnet() {
        ItemStack item = magnet;
        ShapedRecipe rec = new ShapedRecipe(NamespacedKey.minecraft("wywrotkamc_magnet"), item);
        rec.shape(new String[]{"ADA", "BCB", "BBB"});
        rec.setIngredient('A', Material.REDSTONE_BLOCK);
        rec.setIngredient('B', Material.IRON_INGOT);
        rec.setIngredient('C', Material.STICK);
        rec.setIngredient('D', Material.AIR);
        return rec;
    }

    public static ShapedRecipe getRecipeEnchantedApple() {
        ShapedRecipe rec = new ShapedRecipe(NamespacedKey.minecraft("wywrotkamc_enchanted_apple"), enchanted_golden_apple);
        rec.shape(new String[]{"ABA", "BCB", "ABA"});
        rec.setIngredient('A', Material.NETHERITE_INGOT);
        rec.setIngredient('B', Material.GOLD_BLOCK);
        rec.setIngredient('C', Material.GOLDEN_APPLE);
        return rec;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (Main.pluginConfig.getEvents().isUnknowncommand()) {
            if (Bukkit.getHelpMap().getHelpTopic(e.getMessage().split(" ")[0]) == null) {
                Player p = e.getPlayer();
                e.setCancelled(true);
                p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &#FF3131Komenda &#FFC42E" + e.getMessage().split(" ")[0] + " &#FF3131nie istnieje &8<<"));
                if (Main.pluginConfig.getEvents().isBossbarunknowncommand()) {
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
        if (Main.pluginConfig.getEvents().isSigncolor()) {
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

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (Main.pluginConfig.getEvents().isAntyxraymessage()) {
            if (e.getBlock().getType() == Material.DIAMOND_ORE) {
                for (Player op : Bukkit.getOnlinePlayers()) {
                    if (op.hasPermission("core.xray.read")) {
                        Api.sendActionBar(op, "&4&lANTY-XRAY: &7Gracz &a" + p.getName() + "&7 zniszczyl rude &eDiamentu");
                    }
                }
            } else if (e.getBlock().getType() == Material.ANCIENT_DEBRIS) {
                for (Player op : Bukkit.getOnlinePlayers()) {
                    if (op.hasPermission("core.xray.read")) {
                        Api.sendActionBar(op, "&4&lANTY-XRAY: &7Gracz &a" + p.getName() + "&7 zniszczyl rude &eDiamentu");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFromTo(BlockFromToEvent e) {
        Material type = e.getBlock().getType();
        if (Main.pluginConfig.getEvents().isLavagrieffing()) {
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
        if (Main.pluginConfig.getEvents().isDeathplayerhead()) {
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
                        appendDigit(timeZone.getYear()) + " &#ffc942⌚", "", " &#9c9898" + e.getEntity().getPlayer().getUniqueId())));
                item.setItemMeta((ItemMeta) meta);
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
            }
        }
    }

    @EventHandler
    public void onReSpawnPlayer(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (Main.pluginConfig.getEvents().isNorespawnteleporttospawn()) {
            if (p.getBedSpawnLocation() == null) {
                Warp warp = WarpCache.getInstance().get("spawn");
                if (warp != null) {
                    Location loc = new Location(Bukkit.getServer().getWorld(warp.getLocation().getWorld().getKey()), warp.getLocation().getX(), warp.getLocation().getY(), warp.getLocation().getZ(), warp.getLocation().getYaw(), warp.getLocation().getPitch());
                    e.setRespawnLocation(loc);
                } else Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aNie ma warpa &espawn");
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (Main.pluginConfig.getEvents().isBlockplacesetair()) {
            if (!player.hasPermission("core.place.nether")) {
                World nether = Bukkit.getWorld("world_nether");
                if (nether.getName().equalsIgnoreCase(event.getBlock().getWorld().getName())) {
                    Api.sendActionBar(player, "&8>> <#f23518>Postawiony blok zniknie za  <#FDBD01>60 sekund &8<<");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            event.getBlock().setType(Material.AIR);
                        }
                    }.runTaskLater(Main.getPlugin(), 20 * 60);
                } else if (!player.hasPermission("core.place.end")) {
                    World end = Bukkit.getWorld("world_the_end");
                    if (end.getName().equalsIgnoreCase(event.getBlock().getWorld().getName())) {
                        Api.sendActionBar(player, "&8>> <#f23518>Postawiony blok zniknie za <#FDBD01>60 sekund &8<<");
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
}
