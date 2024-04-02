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
import xyz.dwaslashe.survivalcore.cache.MarryCache;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.cache.WarpCache;
import xyz.dwaslashe.survivalcore.enums.ImageChar;
import xyz.dwaslashe.survivalcore.objects.*;
import xyz.dwaslashe.survivalcore.tasks.SecondPlayerTask;
import xyz.dwaslashe.survivalcore.utils.*;
import xyz.upperlevel.spigot.book.BookUtil;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class PlayerJoinListener implements Listener {
    private PictureApi pictureApi;
    private Main plugin;
    public PlayerJoinListener(Main plugin) {
        this.plugin = plugin;
        this.pictureApi = plugin.getPictureApi();
    }

    public void firstJoinExecute(Player player) {
        ItemStack food = new ItemApi(Material.COOKED_BEEF, (short)0)
                .setAmount(16)
                .setName("&#E7E7E7Cześć &#f5be1b%player% &#76f51bB)".replace("%player%", player.getName()))
                .setLore(Arrays.asList("", " &#E7E7E7Strona: &#e6cf3cwww.wywrotkamc.pl", " &#E7E7E7Discord: &#7289dadc.wywrotkamc.pl"))
                .getItemStack();

        Protection protection = Protection.compute(player.getUniqueId());
        protection.setProtection(System.currentTimeMillis() + TimerApi.getTime("10m"));
        protection.setMaxTimeProtection(TimerApi.getTime("10m"));

        User user = UserCache.getInstance().compute(player.getUniqueId());

        Marry marry = MarryCache.getInstance().compute(player.getUniqueId());
        marry.setRightuuid(player.getUniqueId());

        user.setHomes("");
        user.setIgnorePlayers("");
        user.setRates("");
        user.setBlockBreak(0);

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, -50));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 10));
        player.setItemInHand(food);

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), () -> {
            Warp warp = WarpCache.getInstance().get("spawn");

            if (warp == null) {
                Location randomLocation = LocationApi.getRandomLocation(Bukkit.getWorld("world"));
                player.teleportAsync(randomLocation);
                return;
            }

            Location spawnLocation = new Location(Bukkit.getServer().getWorld(warp.getLocation().getWorld().getKey()), warp.getLocation().getX(), warp.getLocation().getY(), warp.getLocation().getZ(), warp.getLocation().getYaw(), warp.getLocation().getPitch());
            player.teleportAsync(spawnLocation);
        }, 5L);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        final Player player = event.getPlayer();

        PlayerTime playerTime = PlayerTime.getPlayer(player);
        playerTime.setTime("0s");
        PlayerTime.getUsers().add(PlayerTime.getPlayer(player));

        if (Main.pluginConfig.getEvents().getBossBarSpawn().isEnable()) {
            BossBar bar = SecondPlayerTask.getBarMap().get(player.getUniqueId());

            if (bar == null) {
                bar = Bukkit.createBossBar("", BarColor.YELLOW, BarStyle.SOLID);
                SecondPlayerTask.getBarMap().put(player.getUniqueId(), bar);
                bar.setProgress(0);
                bar.setVisible(true);
            } else {
                bar.removeAll();
                bar.addPlayer(player);
            }
        }

        if (player.hasPlayedBefore()) {
        } else firstJoinExecute(player);

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), () -> {
            sendImage(player);
            //Create book in join
            //ItemStack book = BookUtil.writtenBook()
            //        .author("WywrotkaMC")
            //        .title("Nowosci")
            //        .pages(
            //                new BookUtil.PageBuilder()
            //                        .add(Api.fixColor("&d&lCo dodaliśmy? 03.01.2022"))
            //                        .newLine().newLine()
            //                        .add(Api.fixColor("&5* &8dodaliśmy komende /incognito, dla rang administracyjnych i yt"))
            //                        .newLine().newLine()
            //                        .add(Api.fixColor("&5* &8dodaliśmy komende /kolornick, do wyboru koloru/gradientu nicku dla rang MVP tylko kolor a dla MVP+ tylko kolor i gradient"))
            //                        .build(),
            //                new BookUtil.PageBuilder()
            //                        .add(Api.fixColor("&5* &8dodaliśmy w komendzie /invsee, wygląd armoru gracza"))
            //                        .newLine().newLine()
            //                        .add(Api.fixColor("&#4cf739&lCo zmieniliśmy? 03.01.2022"))
            //                        .newLine().newLine()
            //                        .add(Api.fixColor("&2* &8zmieniliśmy wygląd nicku na tabie, sidebarze i w większości wiadomościach na czacie"))
            //                        .build(),
            //                new BookUtil.PageBuilder()
            //                        .add(Api.fixColor("&2* &8zmieniliśmy komendę /list"))
            //                        .newLine().newLine()
            //                        .add(Api.fixColor("&2* &8zmieniliśmy wiadomości gdy ktoś coś kupi w itemshopie"))
            //                        .newLine()
            //                        .add(Api.fixColor("&b&lCo naprawiliśmy? 30.01.2022"))
            //                        .newLine()
            //                        .add(Api.fixColor("&3* &8naprawiliśmy wiadomości w /socialspy"))
            //                        .build(),
            //                new BookUtil.PageBuilder()
            //                        .add(Api.fixColor("&3* &8naprawiono literówki w wiadomościach"))
            //                        .newLine().newLine()
            //                        .add(Api.fixColor("&3* &8naprawiono komende /night"))
            //                        .build(),
            //                new BookUtil.PageBuilder()
            //                        .newLine().newLine()
            //                        .build()
            //        )
            //        .build();
            //Open book
            //Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
            //    @Override
            //    public void run() {
            //        //BookUtil.openPlayer(p, book);
            //    }
            //}, 35L);
            //Messages in join

            if (player.hasPermission("core.join.vip")) {
                Api.sendBroadcast(Main.pluginConfig.getJoin().getVipbroadcast().replace("{PLAYER}", player.getDisplayName()).replace("{PREFIX}", ChatApi.getPrefix(player)));
            }

            if (Main.pluginConfig.getEvents().isJoinActionBar()) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    Api.sendActionBar(all, "&8>> <#39FF14>Gracz <#FDBD01>" + player.getName() + " <#39FF14>dołączył na serwer! &8<<");
                }
            }

            if (Main.pluginConfig.getEvents().isJoinBossBarFlesh()) {
                //BossBar barflesh = Bukkit.createBossBar(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshmessage()), BarColor.WHITE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                //barflesh.addPlayer(player.getPlayer());
                //barflesh.setProgress(0);
                //int[] bar_title = {0};
                //Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
                //    @Override
                //    public void run() {
                //        if (player.getPlayer() != null && player.getPlayer().isOnline()) {
                //            ++bar_title[0];
                //            if (bar_title[0] == 1) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 2) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 3) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 4) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 5) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 6) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 7) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 8) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 9) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 10) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 11) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 12) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 13) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 14) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor2() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else if (bar_title[0] == 15) {
                //                barflesh.setTitle(Api.fixColor(Main.pluginConfig.getJoin().getBossbarfleshcolor1() + Main.pluginConfig.getJoin().getBossbarfleshmessage()));
                //            } else {
                //                bar_title[0] = 0;
                //                barflesh.setVisible(false);
                //                barflesh.removePlayer(player.getPlayer());
                //            }
                //        }
                //    }
                //}, 0, 6);
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

    private ImageMessage getMessage(Player player) {
        return pictureApi.createPictureMessage(player, Api.fixColor(Main.pluginConfig.getJoin().getMessage()));
    }

    private void sendImage(Player player) {
        ImageMessage pictureMessage = getMessage(player);

        if (pictureMessage == null) return;
        pictureApi.clearChat(player);
        pictureMessage.sendToPlayer(player);
    }

    public static ImageMessage getMessage(List<String> messages, BufferedImage image) {
        int imageDimensions = 8, count = 0;
        ImageMessage imageMessage = new ImageMessage(image, imageDimensions, ImageChar.BLOCK.getChar());
        String[] msg = new String[imageDimensions];

        for (String message : messages) {
            if (count > msg.length) break;
            msg[count++] = message;
        }

        while (count < imageDimensions) {
            msg[count++] = "";
        }

        return imageMessage.appendText(msg);
    }

}