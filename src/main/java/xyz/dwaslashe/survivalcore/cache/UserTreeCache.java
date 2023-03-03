package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;
import xyz.dwaslashe.survivalcore.objects.UserTree;

import java.util.*;
import java.util.function.Consumer;

public class UserTreeCache implements DatabaseCache<UserTree> {

    private static UserTreeCache instance;

    public static UserTreeCache getInstance() {
        if(instance == null) instance = new UserTreeCache();
        return instance;
    }

    private final Map<UUID, UserTree> userMap = new HashMap<>();

    private final Set<UserTree> toUpdate = new HashSet<>();

    public Set<UserTree> getToUpdate() {
        return toUpdate;
    }

    public UserTree compute(UUID uuid){
        return userMap.computeIfAbsent(uuid, UserTree::new);
    }

    public UserTree compute(UUID uuid, Consumer<UserTree> consumer){
        UserTree user = userMap.computeIfAbsent(uuid, UserTree::new);
        consumer.accept(user);
        return user;
    }

    public Map<UUID, UserTree> getUserMap() {
        return userMap;
    }

    @Override
    public void add(UserTree user) {
        userMap.put(user.getUuid(), user);
    }
}
