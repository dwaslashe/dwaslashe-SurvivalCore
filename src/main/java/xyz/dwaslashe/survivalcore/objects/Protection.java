package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Protection {
    private static final HashMap<UUID, Protection> protectionMap = new HashMap<>();

    public static HashMap<UUID, Protection> getProtectionMap() {
        return protectionMap;
    }

    public static Protection get(UUID uuid){
        return protectionMap.get(uuid);
    }

    public static Protection compute(UUID uuid){
        return protectionMap.computeIfAbsent(uuid, Protection::new);
    }

    private final UUID uuid;
    private long protection;

    private long maxTimeProtection;

    private BossBar bar;

    public Protection(UUID uuid) {
        this.uuid = uuid;
        //this.protection = System.currentTimeMillis() + (1000 * 60 * 10);

        this.bar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        this.bar.setVisible(true);
    }

    public void actualize(Player player){
        bar.removeAll();
        bar.addPlayer(player);
    }

    public BossBar getBar() {
        return bar;
    }

    public void setBar(BossBar bar) {
        this.bar = bar;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getProtection() {
        return protection;
    }

    public void setProtection(long protection) {
        this.protection = protection;
    }

    public void setMaxTimeProtection(long maxTimeProtection) {
        this.maxTimeProtection = maxTimeProtection;
    }

    public long getMaxTimeProtection() {
        return maxTimeProtection;
    }
}