package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.Location;
import xyz.dwaslashe.survivalcore.cache.PlayerWarpCache;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;
import xyz.dwaslashe.survivalcore.parsers.LocationParser;

import java.sql.ResultSet;
import java.sql.SQLException;

@DataObject(table = "playerwarps")
public class PlayerWarp {

    @PrimaryKey(value = @Value(key = "name", type = "VARCHAR(64)"))
    private final String name;

    @Value(key = "nick", type = "VARCHAR(64)")
    private String nick;

    @Value(key = "lore", type = "VARCHAR(64)")
    private String lore;

    @Value(key = "location", type = "VARCHAR(2048)")
    private Location location;

    public PlayerWarp(String name) {
        this.name = name;
    }

    public PlayerWarp(ResultSet resultSet){
        try {
            this.name = resultSet.getString("name");
            this.nick = resultSet.getString("nick");
            this.lore = resultSet.getString("lore");
            this.location = new LocationParser().serialize(resultSet.getString("location"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    //Nick
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
        PlayerWarpCache.getInstance().getToUpdate().add(this);
    }

    //Lore
    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
        PlayerWarpCache.getInstance().getToUpdate().add(this);
    }

    //Location
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        PlayerWarpCache.getInstance().getToUpdate().add(this);
    }
}
