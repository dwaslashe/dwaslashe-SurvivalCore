package xyz.dwaslashe.survivalcore.cache;

import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.model.Warp;
import xyz.dwaslashe.survivalcore.model.impl.WarpImpl;
import xyz.dwaslashe.survivalcore.model.impl.parsers.LocationParser;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WarpCache {

    private final Map<String, Warp> warpMap = new HashMap<>();

    public void addFromSQL(ResultSet resultSet){
        Warp warp = new WarpImpl(resultSet);
        warpMap.put(warp.name(), warp);
    }

    public Map<String, Warp> getWarpMap() {
        return warpMap;
    }

    public Optional<Warp> getWarp(String warpName){
        return Optional.ofNullable(warpMap.get(warpName.toLowerCase()));
    }

    public void addToSQL(Warp warp){
        Main.getPlugin().getGetter().createOrUpdate("warps", new HashMap<>(){{
            put("name", warp.name());
            put("location", new LocationParser(warp.location()));
            put("permission", warp.permission());
        }});
    }

    public Warp getOrCreate(String warpName){
        return warpMap.computeIfAbsent(warpName, WarpImpl::new);
    }

}
