package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.objects.TestUser;
import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;

import java.util.*;
import java.util.function.Consumer;

public class TestUserCache implements DatabaseCache<TestUser> {

    private static TestUserCache instance;

    public static TestUserCache getInstance() {
        if(instance == null) instance = new TestUserCache();
        return instance;
    }

    private final Map<UUID, TestUser> userMap = new HashMap<>();

    private final Set<TestUser> toUpdate = new HashSet<>();

    public Set<TestUser> getToUpdate() {
        return toUpdate;
    }

    public TestUser compute(UUID uuid){
        return userMap.computeIfAbsent(uuid, TestUser::new);
    }

    public TestUser compute(UUID uuid, Consumer<TestUser> consumer){
        TestUser user = userMap.computeIfAbsent(uuid, TestUser::new);
        consumer.accept(user);
        return user;
    }

    public Map<UUID, TestUser> getUserMap() {
        return userMap;
    }

    @Override
    public void add(TestUser user) {
        userMap.put(user.getUuid(), user);
    }
}