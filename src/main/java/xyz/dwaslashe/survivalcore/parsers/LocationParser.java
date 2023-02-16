package xyz.dwaslashe.survivalcore.parsers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import xyz.dwaslashe.survivalcore.database.db.api.ItemSerializer;

@Getter
@Setter
public class LocationParser implements ItemSerializer<Location> {

    @Override
    public Class<Location> supportedClass() {
        return Location.class;
    }

    @Override
    public Location serialize(String s) {
        String[] args = s.split("_");
        return args.length == 4 ? new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3])) : new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
    }

    @Override
    public String deserialize(Location location) {
        return location.getWorld().getName() + "_" + location.getX() + "_" + location.getY() + "_" + location.getZ() + "_" + location.getPitch() + "_" + location.getYaw();
    }
}