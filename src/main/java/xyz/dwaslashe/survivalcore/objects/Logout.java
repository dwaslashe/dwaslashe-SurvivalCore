package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.managers.LogoutManager;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.Iterator;

public class Logout {
    private Player player;
    private long time;
    private Player attacker;

    public static Logout get(Player player) {
        Iterator var1 = LogoutManager.getLogouts().iterator();

        Logout logout;
        do {
            if (!var1.hasNext()) {
                return new Logout(player);
            }

            logout = (Logout)var1.next();
        } while(!logout.getPlayer().getName().equalsIgnoreCase(player.getName()));

        return logout;
    }

    public Player getAttacker() {
        return this.attacker;
    }

    public void setAttacker(Player attacker) {
        this.attacker = attacker;
    }

    private boolean exists() {
        return LogoutManager.getLogouts().contains(this);
    }

    public Logout(Player player) {
        this.player = player;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = TimerApi.parseDateDiff(time, true);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void create() {
        if (!this.exists()) {
            LogoutManager.getLogouts().add(this);
            final BossBar bar = Bukkit.createBossBar(Api.fixColor("&8>> &#FF3131Nie logaj się przez &#ffd56c" + this.time + " &8&l-" + Main.pluginConfig.getMessages().getIp() + " &8<<"), BarColor.RED, BarStyle.SOLID, new BarFlag[0]);
            bar.addPlayer(this.player);
            (new BukkitRunnable() {
                public void run() {
                    if (Logout.this.player != null && Logout.this.player.isOnline()) {

                        //if(!PlayerUtility.isNull(player)) {
                        //    return;
                        //}
                        //if (PlayerUtility.getState(player).equals(Nokaut.CARRY)) {
                        //    Logout.this.remove();
                        //    bar.setVisible(false);
                        //    bar.removePlayer(Logout.this.player);
                        //    this.cancel();
                        //    return;
                        //}

                        if (Logout.this.time > System.currentTimeMillis()) {
                            bar.setTitle(Api.fixColor("&8>> &#FF3131Nie logaj się przez &#ffd56c" + TimerApi.secondsToString(Logout.this.getTime()) + " &8&l- " + Main.pluginConfig.getMessages().getIp() + " &8<<"));
                        } else {
                            bar.setTitle(Api.fixColor("&8>> &#8dfa52Skończyłeś już walke! Teraz możesz się wylogować! &8<<"));
                            bar.setColor(BarColor.GREEN);
                            bar.setProgress(1);
                            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
                                public void run() {
                                    bar.setVisible(false);
                                    bar.removePlayer(Logout.this.player);
                                    Logout.this.remove();
                                }
                            }, 50L);
                            this.cancel();
                        }
                    } else {
                        Logout.this.remove();
                        this.cancel();
                    }

                }
            }).runTaskTimer(Main.getPlugin(), 0L, 20L);
        }

    }

    public void remove() {
        if (this.exists()) {
            LogoutManager.getLogouts().remove(this);
        }

    }
}