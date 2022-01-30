package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.help.HelpTopic;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.objects.Abyss;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class OthersListener implements Listener {
    protected String appendDigit(int i){
        return i <= 9 ? "0" + i : i+"";
    }
    ZonedDateTime timeZone = TimerApi.getZoneDate("UTC+1");
    public static final List<Player> cancel = Lists.newArrayList();
    private int id = 0;

    @EventHandler
    public void onUnknownCommand(PlayerCommandPreprocessEvent e) {
        if (!(e.isCancelled())) {
            Player p = e.getPlayer();
            String msg = e.getMessage().split(" ")[0];
            HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(msg);
            if (topic == null) {
                p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &7Komenda &f" + msg + " &7nie istnieje &8<<"), 10, 40, 10);
                e.setCancelled(true);
                if (topic == null) {
                    BossBar bar = Bukkit.createBossBar(Api.fixColor("&8>> &7Komenda &f" + msg + " &7nie istnieje &8<<"), BarColor.RED, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                    bar.addPlayer(p.getPlayer());
                    bar.setProgress(1);
                    int[] bar_color = {0};
                    Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
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
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.DRIED_KELP_BLOCK)) {
                if (e.getItem().getType() != Material.FLINT_AND_STEEL) return;
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 150, 50));
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, -50));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
            } else if (e.getClickedBlock().toString().toLowerCase().contains("bed")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSignColor(SignChangeEvent e) {
        Player p = e.getPlayer();
        String[] lines = e.getLines();
        for (int n = 0; n <= 3; n++)
            if (p.hasPermission("core.sign.color") == true) {
                e.setLine(n, Api.fixColor(lines[n]));
            }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        message = message.toLowerCase();
        List<String> wordsInMessage = Arrays.asList(message.split(" "));
        for (String word : Main.pluginConfig.getChat().getBlockwords().getWords()) {
            if (wordsInMessage.contains(word.toLowerCase())) {
                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Main.pluginConfig.getChat().getBlockwords().getCommand().replace("{PLAYER}", p.getName()));
                });
                break;
            } else if (p.hasPermission("core.chat.block.bypass")) {
                e.setCancelled(false);
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
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("core.join.freeze")) {
            Location to = e.getFrom();
            to.setPitch(e.getTo().getPitch());
            to.setYaw(e.getTo().getYaw());
            e.setTo(to);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("core.join.freeze")) {
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&bJesteś zamrożony! Nie możesz używac komend!");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (e.getBlock().getType() == Material.DIAMOND_ORE) {
            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.hasPermission("core.xray.read")) {
                    op.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Api.fixColor("&4&lANTY-XRAY: &7Gracz &a" + p.getName() + "&7 zniszczyl rude &eDiamentu")));
                }
            }
        } else if (e.getBlock().getType() == Material.ANCIENT_DEBRIS) {
            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.hasPermission("core.xray.read")) {
                    op.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Api.fixColor("&4&lANTY-XRAY: &7Gracz &a" + p.getName() + "&7 zniszczyl rude &eDiamentu")));
                }
            }
        }
    }

    @EventHandler
    public void onFromTo(BlockFromToEvent e) {
        Material type = e.getBlock().getType();
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
        if (e.getEntity() instanceof Player) {
            ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(e.getEntity().getName());
            meta.setDisplayName(Api.fixColor("&6Głowa gracza&8: &a" + e.getEntity().getName()));
            meta.setLore(Api.fixColor(Arrays.asList("", " &7Data&8: &e" +
                    appendDigit(timeZone.getDayOfMonth()) + "/" +
                    appendDigit(timeZone.getMonthValue()) + "/" +
                    appendDigit(timeZone.getYear()) + " " +
                    appendDigit(timeZone.getHour()) + ":" +
                    appendDigit(timeZone.getMinute()) + ":" + appendDigit(timeZone.getSecond()), "", " &7" + e.getEntity().getPlayer().getUniqueId())));
            item.setItemMeta((ItemMeta) meta);
            e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
            e.setDeathMessage(null);
        }
    }

    @EventHandler
    public void onReSpawnPlayer(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        World world = Bukkit.getWorld("world");
        if (p.getBedSpawnLocation() == null) {
            Location loc = new Location(world, Main.pluginConfig.getSpawn().getX(), Main.pluginConfig.getSpawn().getY(), Main.pluginConfig.getSpawn().getZ(), Main.pluginConfig.getSpawn().getYaw(), Main.pluginConfig.getSpawn().getPitch());
            e.setRespawnLocation(loc);
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
        if (!player.hasPermission("core.place.nether")) {
            World nether = Bukkit.getWorld("world_nether");
            if (nether.getName().equalsIgnoreCase(event.getBlock().getWorld().getName())) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Api.fixColor("&8>> &aBlok zniknie za &e50 sekund &8<<")));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        event.getBlock().setType(Material.AIR);
                    }
                }.runTaskLater(Main.getPlugin(), 20 * 50);
            } else if (!player.hasPermission("core.place.end")) {
                World end = Bukkit.getWorld("world_the_end");
                if (end.getName().equalsIgnoreCase(event.getBlock().getWorld().getName())) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Api.fixColor("&8>> &aBlok zniknie za &e50 sekund &8<<")));
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
