package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;
import xyz.dwaslashe.survivalcore.objects.DragonLevel;

import java.util.*;
import java.util.function.Consumer;

public class DragonLevelCache implements DatabaseCache<DragonLevel> {

    private static DragonLevelCache instance;

    public static DragonLevelCache getInstance() {
        if(instance == null) instance = new DragonLevelCache();
        return instance;
    }

    private final Map<String, DragonLevel> bossMap = new HashMap<>();

    private final Set<DragonLevel> toUpdate = new HashSet<>();

    public Set<DragonLevel> getToUpdate() {
        return toUpdate;
    }

    public DragonLevel compute(String boss){
        return bossMap.computeIfAbsent(boss, DragonLevel::new);
    }

    public DragonLevel compute(String boss, Consumer<DragonLevel> consumer){
        DragonLevel dragon = bossMap.computeIfAbsent(boss, DragonLevel::new);
        consumer.accept(dragon);
        return dragon;
    }

    public Map<String, DragonLevel> getBossMap() {
        return bossMap;
    }

    @Override
    public void add(DragonLevel boss) {
        bossMap.put(boss.getBoss(), boss);
    }
}
