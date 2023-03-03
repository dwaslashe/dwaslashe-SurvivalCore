package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;

import java.util.*;
import java.util.function.Consumer;

public class UserCache implements DatabaseCache<User> {

    private static UserCache instance;

    public static UserCache getInstance() {
        if(instance == null) instance = new UserCache();
        return instance;
    }

    private final Map<UUID, User> userMap = new HashMap<>();

    private final Set<User> toUpdate = new HashSet<>();

    public Set<User> getToUpdate() {
        return toUpdate;
    }

    public User compute(UUID uuid){
        return userMap.computeIfAbsent(uuid, User::new);
    }

    public User compute(UUID uuid, Consumer<User> consumer){
        User user = userMap.computeIfAbsent(uuid, User::new);
        consumer.accept(user);
        return user;
    }

    public Map<UUID, User> getUserMap() {
        return userMap;
    }

    @Override
    public void add(User user) {
        userMap.put(user.getUuid(), user);
    }
}