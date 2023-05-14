package xyz.dwaslashe.survivalcore.objects;

import xyz.dwaslashe.survivalcore.cache.DragonLevelCache;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;

import java.sql.ResultSet;
import java.sql.SQLException;

@DataObject(table = "dragonlevel")
public class DragonLevel {

    @PrimaryKey(value = @Value(key = "boss", type = "VARCHAR(32)"))
    private String boss;

    @Value(key = "level", type = "INT(16)")
    private int level;

    public DragonLevel(String boss) {
        this.boss = boss;
    }

    public DragonLevel(ResultSet resultSet){
        try {
            this.boss = resultSet.getString("boss");
            this.level = resultSet.getInt("level");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBoss() {
        return boss;
    }

    //Level

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        DragonLevelCache.getInstance().getToUpdate().add(this);
    }

}
