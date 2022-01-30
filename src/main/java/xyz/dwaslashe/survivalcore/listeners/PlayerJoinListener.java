package xyz.dwaslashe.survivalcore.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;
import xyz.dwaslashe.survivalcore.utils.LocationApi;
import xyz.upperlevel.spigot.book.BookUtil;

public class PlayerJoinListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), () -> {
            final Player p = e.getPlayer();
            final World world = Bukkit.getWorld("world");
            final Location loc = LocationApi.getRandomLocation(world);

            //First join
            if (p.hasPlayedBefore()) {
            } else p.teleport(loc);

            if (p.hasPlayedBefore()) {
            } else p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor("&8>> &aZostałeś przeteleportowany na &erandomowe &akordynaty! &8<<"));

            if (p.hasPlayedBefore()) {
            } else Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pa give " + p.getName());
            //Create book in join
            ItemStack book = BookUtil.writtenBook()
                    .author("WywrotkaMC")
                    .title("Nowosci")
                    .pages(
                            new BookUtil.PageBuilder()
                                    .add(Api.fixColor("&d&lCo dodaliśmy? 30.01.2022"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&5* &8dodaliśmy komende /glowing, dla rangi premium"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&a&lCo zmieniliśmy? 30.01.2022"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&2* &8zmieniliśmy regulamin"))
                                    .build(),
                            new BookUtil.PageBuilder()
                                    .add(Api.fixColor("&2* &8zmieniliśmy antylogout i teraz druga osoba też dostaje antyloga"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&2* &8zmieniliśmy komendę /rangi"))
                                    .build(),
                            new BookUtil.PageBuilder()
                                    .add(Api.fixColor("&b&lCo naprawiliśmy? 30.01.2022"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&3* &8naprawiono format pieniędzy na tabie i sidebarze"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&3* &8naprawiono literówki w wiadomościach"))
                                    .build(),
                            new BookUtil.PageBuilder()
                                    .newLine().newLine()
                                    .add(Api.fixColor("&3* &8naprawiono panel od vanisha"))
                                    .newLine().newLine()
                                    .add(Api.fixColor("&3* &8naprawiono komende /media"))
                                    .newLine().newLine()
                                    .build()
                    )
                    .build();
            //Open book
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    BookUtil.openPlayer(p, book);
                }
            }, 35L);
            //Messages in join

            //Api.sendMessage(p,
            //        " \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n              &7Witaj na serwerze <#FF3131>&lHOTMC</#ba1c11> \n \n    &7Dołączyłeś do trybu &#10f70c&lSURVIVAL + DZIAŁKI V2".replace("{PLAYER}", p.getName()).replace("{PREFIX}", ChatApi.getPrefix(p)).replace("{ONLINE}", Bukkit.getOnlinePlayers().size() + ""));

            if (p.hasPermission("core.join.vip")) {
                Api.sendBroadcast(Main.pluginConfig.getJoin().getVipbroadcast().replace("{PLAYER}", p.getName()).replace("{PREFIX}", ChatApi.getPrefix(p)));
            }
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Api.fixColor("&8>> &aGracz &e" + p.getName() + " &adołączył na serwer! &8<<")));
            }
            BossBar bar = Bukkit.createBossBar(Api.fixColor(Main.pluginConfig.getJoin().getBossbarmessage()), BarColor.WHITE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
            bar.addPlayer(p.getPlayer());
            bar.setProgress(0);
            int[] bar_title = {0};
            Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    if (p.getPlayer() != null && p.getPlayer().isOnline()) {
                        ++bar_title[0];
                        if (bar_title[0] == 1) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 2) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 3) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 4) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 5) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 6) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 7) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 8) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 9) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 10) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 11) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 12) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 13) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 14) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else if (bar_title[0] == 15) {
                            bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
                        } else {
                            bar_title[0] = 0;
                            bar.setVisible(false);
                            bar.removePlayer(p.getPlayer());
                        }
                    }
                }
            }, 0, 6);

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