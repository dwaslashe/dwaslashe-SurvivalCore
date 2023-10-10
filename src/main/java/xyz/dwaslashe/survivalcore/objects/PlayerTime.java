package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.ArrayList;
import java.util.List;

public class PlayerTime {

    public static List<PlayerTime> users = new ArrayList<>();

    protected final String name;

    public static List<PlayerTime> getUsers() {
        return users;
    }

    public static PlayerTime getPlayer(Player player) {
        for (PlayerTime user : users) {
            if (user.getName().equals(player.getName()))
                return user;
        }
        return new PlayerTime(player.getName());
    }

    private long time = System.currentTimeMillis();

    public PlayerTime(String name) {
        this.name = name;
        users.add(this);
    }

    public String getName() {
        return this.name;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = TimerApi.parseDateDiff(time, true);
    }
}