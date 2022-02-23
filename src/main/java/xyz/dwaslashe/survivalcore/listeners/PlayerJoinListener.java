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
import xyz.dwaslashe.survivalcore.model.impl.UserImpl;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;
import xyz.upperlevel.spigot.book.BookUtil;

public class PlayerJoinListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), () -> {
            final Player p = e.getPlayer();

            UserImpl user = (UserImpl) Main.getPlugin().getUserCache().getOrCreate(p.getName());
            user.setPlayer(p);
            user.setOnline(true);
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
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    //BookUtil.openPlayer(p, book);
                }
            }, 35L);
            //Messages in join
            if (p.hasPermission("core.join.vip")) {
                Api.sendBroadcast(Main.pluginConfig.getJoin().getVipbroadcast().replace("{PLAYER}", p.getDisplayName()).replace("{PREFIX}", ChatApi.getPrefix(p)));
            }
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Api.fixColor("&8>> &aGracz &e" + p.getDisplayName() + " &adołączył na serwer! &8<<")));
            }
            //BossBar bar = Bukkit.createBossBar(Api.fixColor(Main.pluginConfig.getJoin().getBossbarmessage()), BarColor.WHITE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
            //bar.addPlayer(p.getPlayer());
            //bar.setProgress(0);
            //int[] bar_title = {0};
            //Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
            //    @Override
            //    public void run() {
            //        if (p.getPlayer() != null && p.getPlayer().isOnline()) {
            //            ++bar_title[0];
            //            if (bar_title[0] == 1) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 2) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 3) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 4) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 5) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 6) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 7) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 8) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 9) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 10) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 11) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 12) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 13) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 14) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor2() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else if (bar_title[0] == 15) {
            //                bar.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarcolor1() + Main.pluginConfig.getJoin().getBossbarmessage()));
            //            } else {
            //                bar_title[0] = 0;
            //                bar.setVisible(false);
            //                bar.removePlayer(p.getPlayer());
            //            }
            //        }
            //    }
            //}, 0, 6);

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