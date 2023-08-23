package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.tasks.PlayerTask;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;
import xyz.dwaslashe.survivalcore.utils.ItemApi;
import xyz.dwaslashe.survivalcore.utils.LocationApi;
import xyz.upperlevel.spigot.book.BookUtil;

public class PlayerJoinListener implements Listener {

    public ItemStack eat = new ItemApi(Material.COOKED_BEEF, (short)0)
            .setAmount(16)
            .setName("&#FFF01FJedzenie na dobry początek!")
            .getItemStack();

    public void firstJoinExecute(Player player) {
        User user = UserCache.getInstance().compute(player.getUniqueId());
        World world = Bukkit.getWorld("world");
        Location loc = LocationApi.getRandomLocation(world);
        user.setHomes("");
        user.setRates("");
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, -50));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 10));
        player.setItemInHand(eat);
        player.teleportAsync(loc);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);

        final Player p = e.getPlayer();

        BossBar bar = PlayerTask.getBarMap().get(p.getUniqueId());

        if(bar == null){
            bar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID);
            PlayerTask.getBarMap().put(p.getUniqueId(), bar);
            bar.setVisible(true);
        } else {
            bar.removeAll();
            bar.addPlayer(p);
        }

        if (p.hasPlayedBefore()) {
        } else firstJoinExecute(p);

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), () -> {

            Api.sendMessage(p, Main.pluginConfig.getJoin().getMessage());

            //Create book in join
            ItemStack book = BookUtil.writtenBook()
                    .author("WywrotkaMC")       
                    .title("Nowosci")
                    .pages(
                            new BookUtil.PageBuilder()
                                    .add(Api.fixColor("&d&lCo dodaliśmy? 03.01.2022"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&5* &8dodaliśmy komende /incognito, dla rang administracyjnych i yt"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&5* &8dodaliśmy komende /kolornick, do wyboru koloru/gradientu nicku dla rang MVP tylko kolor a dla MVP+ tylko kolor i gradient"))
                                    .build(),
                            new BookUtil.PageBuilder()
                                    .add(Api.fixColor("&5* &8dodaliśmy w komendzie /invsee, wygląd armoru gracza"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&a&lCo zmieniliśmy? 03.01.2022"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&2* &8zmieniliśmy wygląd nicku na tabie, sidebarze i w większości wiadomościach na czacie"))
                                    .build(),
                            new BookUtil.PageBuilder()
                                    .add(Api.fixColor("&2* &8zmieniliśmy komendę /list"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&2* &8zmieniliśmy wiadomości gdy ktoś coś kupi w itemshopie"))
                                    .newLine()
                                    .add(Api.fixColor("&b&lCo naprawiliśmy? 30.01.2022"))
                                    .newLine()
                                    .add(Api.fixColor("&3* &8naprawiliśmy wiadomości w /socialspy"))
                                    .build(),
                            new BookUtil.PageBuilder()
                                    .add(Api.fixColor("&3* &8naprawiono literówki w wiadomościach"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&3* &8naprawiono komende /night"))
                                    .build(),
                            new BookUtil.PageBuilder()
                                    .newLine().newLine()
                                    .build()
                    )
                    .build();
            //Open book
            //Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
            //    @Override
            //    public void run() {
            //        //BookUtil.openPlayer(p, book);
            //    }
            //}, 35L);
            //Messages in join
            if (p.hasPermission("core.join.vip")) {
                Api.sendBroadcast(Main.pluginConfig.getJoin().getVipbroadcast().replace("{PLAYER}", p.getDisplayName()).replace("{PREFIX}", ChatApi.getPrefix(p)));
            }
            if (Main.pluginConfig.getEvents().isJoinactionbar()) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    Api.sendActionBar(all, "&8>> <#39FF14>Gracz <#FDBD01>" + p.getName() + " <#39FF14>dołączył na serwer! &8<<");
                }
            }

            if (Main.pluginConfig.getEvents().isJoinbossbarflesh()) {
                BossBar barflesh = Bukkit.createBossBar(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshmessage()), BarColor.WHITE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                barflesh.addPlayer(p.getPlayer());
                barflesh.setProgress(0);
                int[] bar_title = {0};
                Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        if (p.getPlayer() != null && p.getPlayer().isOnline()) {
                            ++bar_title[0];
                            if (bar_title[0] == 1) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 2) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 3) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 4) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 5) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 6) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 7) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 8) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 9) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 10) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 11) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 12) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 13) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 14) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else if (bar_title[0] == 15) {
                                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                            } else {
                                bar_title[0] = 0;
                                barflesh.setVisible(false);
                                barflesh.removePlayer(p.getPlayer());
                            }
                        }
                    }
                }, 0, 6);
            }

        }, 5);
    }

    @EventHandler
    public void OnPlayerLoginEvent(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            Player player = event.getPlayer();
            if (player.hasPermission("core.join.full.bypass")) {
                event.allow();
            }
        }
    }
}