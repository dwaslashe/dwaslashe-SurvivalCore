package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;
import xyz.dwaslashe.survivalcore.objects.Warp;

import java.util.*;
import java.util.function.Consumer;

public class WarpCache implements DatabaseCache<Warp> {

    private static WarpCache instance;

    public static WarpCache getInstance() {
        if(instance == null) instance = new WarpCache();
        return instance;
    }

    private final Map<String, Warp> warpMap = new HashMap<>();

    private final Set<Warp> toUpdate = new HashSet<>();

    public Set<Warp> getToUpdate() {
        return toUpdate;
    }

    public Warp get(String name){
        return warpMap.get(name);
    }

    public Warp compute(String name){
        return warpMap.computeIfAbsent(name, Warp::new);
    }

    public Warp compute(String name, Consumer<Warp> consumer){
        Warp warp = warpMap.computeIfAbsent(name, Warp::new);
        consumer.accept(warp);
        return warp;
    }

    public Map<String, Warp> getWarpMap() {
        return warpMap;
    }

    @Override
    public void add(Warp warp) {
        warpMap.put(warp.getName(), warp);
    }
}