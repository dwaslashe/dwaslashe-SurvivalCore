package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.Location;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;
import xyz.dwaslashe.survivalcore.cache.WarpCache;
import xyz.dwaslashe.survivalcore.parsers.LocationParser;

import java.sql.ResultSet;
import java.sql.SQLException;

@DataObject(table = "warps")
public class Warp {

    @PrimaryKey(value = @Value(key = "name", type = "VARCHAR(64)"))
    private final String name;

    @Value(key = "location", type = "VARCHAR(2048)")
    private Location location;

    public Warp(String name) {
        this.name = name;
    }

    public Warp(ResultSet resultSet){
        try {
            this.name = resultSet.getString("name");
            this.location = new LocationParser().serialize(resultSet.getString("location"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    //Location
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        WarpCache.getInstance().getToUpdate().add(this);
    }
}