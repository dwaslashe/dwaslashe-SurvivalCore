package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.model.User;
import xyz.dwaslashe.survivalcore.model.impl.UserImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserCache {

    private final Map<String, User> userMap = new HashMap<>();
    private final Map<String, User> onlineUserMap = new HashMap<>();

    public Map<String, User> getOnlineUserMap() {
        return onlineUserMap;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public User getOrCreate(String name){
        return userMap.computeIfAbsent(name, UserImpl::new);
    }

    public Optional<User> get(String name){
        return Optional.ofNullable(userMap.get(name));
    }

    public Optional<User> getOnline(String name){
        return Optional.ofNullable(onlineUserMap.get(name));
    }

    public void setOnline(User user, boolean activity){
        onlineUserMap.remove(user.name());
        if(activity) onlineUserMap.put(user.name(), user);
    }

}
