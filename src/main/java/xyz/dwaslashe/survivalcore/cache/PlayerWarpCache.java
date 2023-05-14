package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;
import xyz.dwaslashe.survivalcore.objects.PlayerWarp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class PlayerWarpCache implements DatabaseCache<PlayerWarp> {

    private static PlayerWarpCache instance;

    public static PlayerWarpCache getInstance() {
        if(instance == null) instance = new PlayerWarpCache();
        return instance;
    }

    private final Map<String, PlayerWarp> warpMap = new HashMap<>();

    private final Set<PlayerWarp> toUpdate = new HashSet<>();

    public Set<PlayerWarp> getToUpdate() {
        return toUpdate;
    }

    public PlayerWarp get(String name){
        return warpMap.get(name);
    }

    public PlayerWarp compute(String name){
        return warpMap.computeIfAbsent(name, PlayerWarp::new);
    }

    public PlayerWarp compute(String name, Consumer<PlayerWarp> consumer){
        PlayerWarp warp = warpMap.computeIfAbsent(name, PlayerWarp::new);
        consumer.accept(warp);
        return warp;
    }

    public Map<String, PlayerWarp> getWarpMap() {
        return warpMap;
    }

    @Override
    public void add(PlayerWarp warp) {
        warpMap.put(warp.getName(), warp);
    }
}
