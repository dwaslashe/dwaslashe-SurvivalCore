package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;
import xyz.dwaslashe.survivalcore.objects.MoneyTarget;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class MoneyTargetCache implements DatabaseCache<MoneyTarget> {

    private static MoneyTargetCache instance;

    public static MoneyTargetCache getInstance() {
        if(instance == null) instance = new MoneyTargetCache();
        return instance;
    }

    private final Map<Integer, MoneyTarget> numberMap = new HashMap<>();

    private final Set<MoneyTarget> toUpdate = new HashSet<>();

    public Set<MoneyTarget> getToUpdate() {
        return toUpdate;
    }

    public MoneyTarget compute(int id){
        return numberMap.computeIfAbsent(id, MoneyTarget::new);
    }

    public MoneyTarget compute(int id, Consumer<MoneyTarget> consumer){
        MoneyTarget dragon = numberMap.computeIfAbsent(id, MoneyTarget::new);
        consumer.accept(dragon);
        return dragon;
    }

    public Map<Integer, MoneyTarget> getNumberMap() {
        return numberMap;
    }

    @Override
    public void add(MoneyTarget number) {
        numberMap.put(number.getId(), number);
    }
}

