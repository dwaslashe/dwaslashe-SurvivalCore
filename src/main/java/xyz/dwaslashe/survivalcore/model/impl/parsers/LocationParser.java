package xyz.dwaslashe.survivalcore.model.impl.parsers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import xyz.dwaslashe.survivalcore.model.ObjectParser;

@Getter
@Setter
public class LocationParser implements ObjectParser<LocationParser> {
    static final long serialVersionUID = 2176319107312484277L;

    private final String worldName;
    private double x, y, z;
    private float yaw, pitch;

    public LocationParser(String data){
        this(ObjectParser.deserialize(data, LocationParser.class));
    }

    private LocationParser(LocationParser parser){
        this(parser.worldName, parser.x, parser.y, parser.z, parser.yaw, parser.pitch);
    }

    public LocationParser(Location location){
        this(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }
    public LocationParser(String worldName, double x, double y, double z, float yaw, float pitch){
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public LocationParser(World world, double x, double y, double z, float yaw, float pitch){
        this(world.getName(), x, y, z, yaw, pitch);
    }

    public Location toBukkitLocation(){
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

    @Override
    public LocationParser getValue() {
        return this;
    }
}
