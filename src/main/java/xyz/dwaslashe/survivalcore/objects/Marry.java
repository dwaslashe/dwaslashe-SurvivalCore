package xyz.dwaslashe.survivalcore.objects;

import xyz.dwaslashe.survivalcore.cache.MarryCache;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@DataObject(table = "marry")
public class Marry {

    @PrimaryKey(value = @Value(key = "leftuuid", type = "VARCHAR(64)"))
    private final UUID leftuuid;

    @Value(key = "rightuuid", type = "VARCHAR(64)")
    private UUID rightuuid;

    @Value(key = "pvp", type = "VARCHAR(4)")
    private String pvp;

    public Marry(UUID uuid) {
        this.leftuuid = uuid;
    }

    public Marry(ResultSet resultSet){
        try {
            this.leftuuid = UUID.fromString(resultSet.getString("leftuuid"));
            this.rightuuid = UUID.fromString(resultSet.getString("rightuuid"));
            this.pvp = resultSet.getString("pvp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID getLeftuuid() {
        return leftuuid;
    }

    public UUID getRightuuid() {
        return rightuuid;
    }

    public String getPvp() {
        return pvp;
    }

    public void setRightuuid(UUID rightuuid) {
        this.rightuuid = rightuuid;
        MarryCache.getInstance().getToUpdate().add(this);
    }

    public void setPvp(String pvp) {
        this.pvp = pvp;
        MarryCache.getInstance().getToUpdate().add(this);
    }

}
