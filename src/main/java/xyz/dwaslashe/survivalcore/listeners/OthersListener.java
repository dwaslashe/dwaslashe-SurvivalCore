package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Lists;
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
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.WarpCache;
import xyz.dwaslashe.survivalcore.commands.managers.CommandManager;
import xyz.dwaslashe.survivalcore.objects.Abyss;
import xyz.dwaslashe.survivalcore.objects.Warp;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;
import xyz.dwaslashe.survivalcore.utils.RegionApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.time.ZonedDateTime;
import java.util.*;

@Getter @Setter
public class OthersListener implements Listener {
    protected String appendDigit(int i) {
        return i <= 9 ? "0" + i : i + "";
    }

    public static final Map<UUID, Integer> playerCooldownMap = new HashMap<>();
    public static final List<Player> cancel = Lists.newArrayList();
    private int id = 0;

    private static ItemStack enchanted_golden_apple = new ItemApi(Material.ENCHANTED_GOLDEN_APPLE).getItemStack();

    public static ItemStack magnet = new ItemApi(Material.LIGHTNING_ROD)
            .setName("&#FF10F0Magnez")
            .setLore(Arrays.asList("", " &fMając magnez w ekwipunku itemy", " &fktóre niszczysz idą do twojego ekwipunku!"))
            .getItemStack();


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
    public void handleCommandUseEvent(PlayerCommandPreprocessEvent event){
        String commandName = event.getMessage().split(" ")[0].replaceFirst("/", "");
        Command command = CommandManager.commandMap.getCommand(commandName);
        if(command == null) return;
        //if (!command.getPermission().isEmpty()) return;
        if(!command.testPermissionSilent(event.getPlayer())){
            event.getPlayer().sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&e{permission}&8) &8<<".replace("{permission}", command.getPermission())));
            event.setCancelled(true);
        } else return;
    }

    @EventHandler
    public void onPlayerInteractEnderPearl(PlayerInteractEvent event) {
        int time = 5;
        Player p = event.getPlayer();
        if (!p.hasPermission("core.cooldown.enderpearl.use.bypass"))
            if (Main.pluginConfig.getEvents().isEnderpearlcooldown() && event.getMaterial() == Material.ENDER_PEARL && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
                if (isPlayerInCooldown(p) && getTimeRemaining(p).intValue() < time) {
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cPerły kolejny raz możesz użyć za &e" + getTimeRemaining(p) + "sek");
                    event.setCancelled(true);
                } else {
                    addPlayerToMap(p, Integer.valueOf(time));
                }
    }

    public void EnderpearlCooldown(Main plugin) {
        (new BukkitRunnable() {
            public void run() {
                for (UUID uuid : playerCooldownMap.keySet()) {
                    if (((Integer)playerCooldownMap.get(uuid)).intValue() == 1) {
                        playerCooldownMap.remove(uuid);
                        if (Bukkit.getPlayer(uuid) != null)
                            Bukkit.getPlayer(uuid).sendMessage(Api.fixColor(Main.pluginConfig.getMessages().getPrefix() + "&aMożesz użyć perły!"));
                        continue;
                    }
                    playerCooldownMap.put(uuid, Integer.valueOf(((Integer)playerCooldownMap.get(uuid)).intValue() - 1));
                }
            }
        }).runTaskTimer((Plugin)plugin, 0L, 20L);
    }

    public Integer getTimeRemaining(Player p) {
        if (!isPlayerInCooldown(p))
            return Integer.valueOf(0);
        return playerCooldownMap.get(p.getUniqueId());
    }

    public void addPlayerToMap(Player p, Integer time) {
        this.playerCooldownMap.put(p.getUniqueId(), time);
    }

    public boolean isPlayerInCooldown(Player p) {
        return this.playerCooldownMap.containsKey(p.getUniqueId());
    }

    private static ItemStack weed = new ItemApi(Material.LARGE_FERN, 1).setName("<#39ff14>Zioło 1g</#008443>").getItemStack();

    private static ItemStack kokaina = new ItemApi(Material.CLAY_BALL, 1).setName("<#FFFFFF>Kokaina 0.1g</#FFFF00>").getItemStack();

    @EventHandler
    private void redeemWeed(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getMaterial() != null) {
            if (e.getMaterial().equals(Material.LARGE_FERN)) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                    ItemStack item = e.getItem();
                    if (item.isSimilar(weed)) {
                        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zapaliłeś &ezioło");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 550, 50));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 500, -50));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 500, 2));
                        item.setAmount(item.getAmount() - 1);
                    }
                }
            } else if (e.getMaterial().equals(Material.CLAY_BALL)) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                    ItemStack item = e.getItem();
                    if (item.isSimilar(kokaina)) {
                        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wciągnąłeś &ekokaine");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1050, 50));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000, -50));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1500, 2));
                        item.setAmount(item.getAmount() - 1);
                    }
                }
            }
        }
    }

    public static ShapedRecipe getRecipeKokaina() {
        ShapedRecipe rec = new ShapedRecipe(NamespacedKey.minecraft("wywrotkamc_kokaina"), kokaina);
        rec.shape(new String[] { "AAA", "BBB", "AAA" });
        rec.setIngredient('A', Material.SUGAR);
        rec.setIngredient('B', Material.LEGACY_SNOW_BALL);
        return rec;
    }

    public static ShapedRecipe getRecipeWeed() {
        ShapedRecipe rec = new ShapedRecipe(NamespacedKey.minecraft("wywrotkamc_weed"), weed);
        rec.shape(new String[] { "DDD", "DDD", "DDD" });
        rec.setIngredient('D', Material.DRIED_KELP_BLOCK);
        return rec;
    }

    public static ShapedRecipe getRecipeMagnet() {
        ItemStack item = magnet;
        ShapedRecipe rec = new ShapedRecipe(NamespacedKey.minecraft("wywrotkamc_magnet"), item);
        rec.shape(new String[] { "ADA", "BCB", "BBB" });
        rec.setIngredient('A', Material.REDSTONE_BLOCK);
        rec.setIngredient('B', Material.IRON_INGOT);
        rec.setIngredient('C', Material.STICK);
        rec.setIngredient('D', Material.AIR);
        return rec;
    }

    public static ShapedRecipe getRecipeEnchantedApple() {
        ShapedRecipe rec = new ShapedRecipe(NamespacedKey.minecraft("wywrotkamc_enchanted_apple"), enchanted_golden_apple);
        rec.shape(new String[] { "ABA", "BCB", "ABA" });
        rec.setIngredient('A', Material.NETHERITE_INGOT);
        rec.setIngredient('B', Material.GOLD_BLOCK);
        rec.setIngredient('C', Material.GOLDEN_APPLE);
        return rec;
    }

    @EventHandler
        public void onCommand(PlayerCommandPreprocessEvent e){
        if (Main.pluginConfig.getEvents().isUnknowncommand()) {
            if (Bukkit.getHelpMap().getHelpTopic(e.getMessage().split(" ")[0]) == null) {
                Player p = e.getPlayer();
                e.setCancelled(true);
                p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &7Komenda &f" + e.getMessage().split(" ")[0] + " &7nie istnieje &8<<"));
                if (Main.pluginConfig.getEvents().isBossbarunknowncommand()) {
                    BossBar bar = Bukkit.createBossBar(Api.fixColor("&8>> &7Komenda &f" + e.getMessage().split(" ")[0] + " &7nie istnieje &8<<"), BarColor.RED, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
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
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() == null) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Main.pluginConfig.getEvents().isNobedexplose()) {
                if (e.getClickedBlock().toString().toLowerCase().contains("BED")) {
                    e.setUseInteractedBlock(Event.Result.DENY);
                    e.setCancelled(true);
                }
            }
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Main.pluginConfig.getEvents().isKelpsmoke()) {
                if (e.getClickedBlock().getType().equals(Material.DRIED_KELP_BLOCK)) {
                    if (e.getItem().getType() != Material.FLINT_AND_STEEL) return;
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 150, 50));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, -50));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
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

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (cancel.contains(e.getPlayer())) cancel.remove(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClickAbyss(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;
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
                meta.setDisplayName(Api.fixColor("&6Głowa gracza&8: &a" + e.getEntity().getName()));
                meta.setLore(Api.fixColor(Arrays.asList("", " &7Data&8: &e" +                         appendDigit(timeZone.getHour()) + ":" +
                        appendDigit(timeZone.getMinute()) + ", " +

                        appendDigit(timeZone.getDayOfMonth()) + "/" +
                        appendDigit(timeZone.getMonthValue()) + "/" +
                        appendDigit(timeZone.getYear()) + " ", "", " &7" + e.getEntity().getPlayer().getUniqueId())));
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
                    e.setRespawnLocation(warp.getLocation());
                } else Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aNie ma warpa &espawn");
            }
        }
    }

    @EventHandler
    private void noteRedeem(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getMaterial() != null) {
            if (e.getMaterial().equals(Material.PAPER)) {
                ItemStack note = e.getItem();
                if (note.getItemMeta().getDisplayName().equals(Api.fixColor("&f&lBanknot &8(&7prawy przycisk&8)"))) {
                    ItemMeta im = note.getItemMeta();
                    if (!im.hasLore()) {
                        return;
                    }
                    float amount = Float.parseFloat(ChatColor.stripColor((String)note.getItemMeta().getLore().get(0)).replace('$', ' '));
                    Main.getVaultEconomy().depositPlayer(p, (double)amount);
                    System.out.println(Api.fixColor("&8>> &a" + p.getName() + " &7wyplacil banknot o wartosci&8: &e$" + amount));
                    Api.sendMessage(p,  Main.pluginConfig.getMessages().getPrefix() + "&aWpłaciłeś na konto &6" + amount + "$");
                    note.setAmount(note.getAmount() - 1);
                }

            }
        }
    }

    public static ItemStack makePaper(List<String> lore) {
        ItemStack is = new ItemStack(Material.PAPER, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Api.fixColor("&f&lBanknot &8(&7prawy przycisk&8)"));
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (Main.pluginConfig.getEvents().isBlockplacesetair()) {
            if (!player.hasPermission("core.place.nether")) {
                World nether = Bukkit.getWorld("world_nether");
                if (nether.getName().equalsIgnoreCase(event.getBlock().getWorld().getName())) {
                    Api.sendActionBar(player, "&8>> &aBlok zniknie za &e50 sekund &8<<");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            event.getBlock().setType(Material.AIR);
                        }
                    }.runTaskLater(Main.getPlugin(), 20 * 50);
                } else if (!player.hasPermission("core.place.end")) {
                    World end = Bukkit.getWorld("world_the_end");
                    if (end.getName().equalsIgnoreCase(event.getBlock().getWorld().getName())) {
                        Api.sendActionBar(player, "&8>> &aBlok zniknie za &e50 sekund &8<<");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                event.getBlock().setType(Material.AIR);
                            }
                        }.runTaskLater(Main.getPlugin(), 20 * 50);
                    }
                }
            }
        }
    }
}
