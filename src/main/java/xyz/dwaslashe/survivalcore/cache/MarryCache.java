package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;
import xyz.dwaslashe.survivalcore.objects.Marry;

import java.util.*;
import java.util.function.Consumer;

public class MarryCache implements DatabaseCache<Marry> {

    private static MarryCache instance;

    public static MarryCache getInstance() {
        if(instance == null) instance = new MarryCache();
        return instance;
    }

    private final Map<UUID, Marry> userMap = new HashMap<>();

    private final Set<Marry> toUpdate = new HashSet<>();

    public Set<Marry> getToUpdate() {
        return toUpdate;
    }

    public Marry compute(UUID uuid){
        return userMap.computeIfAbsent(uuid, Marry::new);
    }

    public Marry compute(UUID uuid, Consumer<Marry> consumer){
        Marry dragon = userMap.computeIfAbsent(uuid, Marry::new);
        consumer.accept(dragon);
        return dragon;
    }

    public Map<UUID, Marry> getUserMap() {
        return userMap;
    }

    @Override
    public void add(Marry marry) {
        userMap.put(marry.getLeftuuid(), marry);
    }
}


