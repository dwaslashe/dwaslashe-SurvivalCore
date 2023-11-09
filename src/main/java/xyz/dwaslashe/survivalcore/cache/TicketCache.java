package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.database.db.api.DatabaseCache;
import xyz.dwaslashe.survivalcore.objects.Ticket;

import java.util.*;
import java.util.function.Consumer;

public class TicketCache implements DatabaseCache<Ticket> {

    private static TicketCache instance;

    public static TicketCache getInstance() {
        if(instance == null) instance = new TicketCache();
        return instance;
    }

    private final Map<UUID, Ticket> userMap = new HashMap<>();

    private final Set<Ticket> toUpdate = new HashSet<>();

    public Set<Ticket> getToUpdate() {
        return toUpdate;
    }

    public Ticket compute(UUID uuid){
        return userMap.computeIfAbsent(uuid, Ticket::new);
    }

    public Ticket compute(UUID uuid, Consumer<Ticket> consumer){
        Ticket user = userMap.computeIfAbsent(uuid, Ticket::new);
        consumer.accept(user);
        return user;
    }

    public Map<UUID, Ticket> getUserMap() {
        return userMap;
    }

    @Override
    public void add(Ticket user) {
        userMap.put(user.getUuid(), user);
    }
}
