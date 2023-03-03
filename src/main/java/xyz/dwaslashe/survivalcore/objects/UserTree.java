package xyz.dwaslashe.survivalcore.objects;

import xyz.dwaslashe.survivalcore.cache.UserTreeCache;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@DataObject(table = "animationusers")
public class UserTree {

    @PrimaryKey(value = @Value(key = "uniqueID", type = "VARCHAR(64)"))
    private final UUID uuid;

    @Value(key = "type", type = "VARCHAR(16)")
    private String animation;

    public UserTree(UUID uuid) {
        this.uuid = uuid;
    }

    public UserTree(ResultSet resultSet){
        try {
            this.uuid = UUID.fromString(resultSet.getString("uniqueID"));
            this.animation = resultSet.getString("type");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public UUID getUuid() {
        return uuid;
    }

    //Animation
    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
        UserTreeCache.getInstance().getToUpdate().add(this);
    }
}
