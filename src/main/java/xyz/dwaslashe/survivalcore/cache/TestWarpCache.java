package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;
import xyz.dwaslashe.survivalcore.objects.TestWarp;

import java.util.*;
import java.util.function.Consumer;

public class TestWarpCache implements DatabaseCache<TestWarp> {

    private static TestWarpCache instance;

    public static TestWarpCache getInstance() {
        if(instance == null) instance = new TestWarpCache();
        return instance;
    }

    private final Map<String, TestWarp> warpMap = new HashMap<>();

    private final Set<TestWarp> toUpdate = new HashSet<>();

    public Set<TestWarp> getToUpdate() {
        return toUpdate;
    }

    public TestWarp get(String name){
        return warpMap.get(name);
    }

    public TestWarp compute(String name){
        return warpMap.computeIfAbsent(name, TestWarp::new);
    }

    public TestWarp compute(String name, Consumer<TestWarp> consumer){
        TestWarp warp = warpMap.computeIfAbsent(name, TestWarp::new);
        consumer.accept(warp);
        return warp;
    }

    public Map<String, TestWarp> getWarpMap() {
        return warpMap;
    }

    @Override
    public void add(TestWarp warp) {
        warpMap.put(warp.getName(), warp);
    }
}