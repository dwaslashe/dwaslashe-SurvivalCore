package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.model.CustomItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemCache {

    private final Map<Integer, CustomItem> itemMap = new HashMap<>();

    public Optional<CustomItem> getItem(int id){
        return Optional.ofNullable(itemMap.get(id));
    }

    public void register(CustomItem customItem){
        itemMap.put(customItem.id(), customItem);
    }

}
