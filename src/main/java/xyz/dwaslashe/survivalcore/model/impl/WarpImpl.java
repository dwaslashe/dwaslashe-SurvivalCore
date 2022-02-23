package xyz.dwaslashe.survivalcore.model.impl;

import lombok.SneakyThrows;
import org.bukkit.Location;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.model.Warp;
import xyz.dwaslashe.survivalcore.model.impl.parsers.LocationParser;

import java.sql.ResultSet;
import java.util.function.Consumer;

public class WarpImpl implements Warp {

    private final String name;

    private Location location;
    private String permission = "";

    public WarpImpl(String name){
        this.name = name;
    }

    @SneakyThrows
    public WarpImpl(ResultSet rs){
        this.name = rs.getString("name");
        this.location = new LocationParser(rs.getString("location")).toBukkitLocation();
        this.permission = rs.getString("permission");
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Location location() {
        return location;
    }

    @Override
    public String permission() {
        return permission;
    }

    public WarpImpl entry(Consumer<WarpImpl> consumer){
        consumer.accept(this);
        return this;
    }

    public WarpImpl addToSQL(){
        Main.getPlugin().getWarpCache().addToSQL(this);
        return this;
    }

    @Override
    public String toString() {
        return "WarpImpl{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", permission='" + permission + '\'' +
                '}';
    }
}
