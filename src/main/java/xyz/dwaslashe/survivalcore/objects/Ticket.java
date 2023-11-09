package xyz.dwaslashe.survivalcore.objects;

import xyz.dwaslashe.survivalcore.cache.TicketCache;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@DataObject(table = "tickets")
public class Ticket {

    @PrimaryKey(value = @Value(key = "uniqueID", type = "VARCHAR(64)"))
    private final UUID uuid;

    @Value(key = "value", type = "INT(32)")
    private int value;

    @Value(key = "time", type = "INT(64)")
    private long time;

    @Value(key = "maxtime", type = "INT(64)")
    private long maxTime;

    @Value(key = "enable", type = "INT(2)")
    private int enable;

    @Value(key = "online", type = "INT(2)")
    private int online;

    @Value(key = "onlinetimeout", type = "INT(2)")
    private int onlineTimeOut;

    @Value(key = "enablejail", type = "INT(2)")
    private int enableJail;

    @Value(key = "timejail", type = "INT(64)")
    private long timeJail;
    @Value(key = "timemaxjail", type = "INT(64)")
    private long timeMaxJail;

    public Ticket(UUID uuid) {
        this.uuid = uuid;
    }

    public Ticket(ResultSet resultSet){
        try {
            this.uuid = UUID.fromString(resultSet.getString("uniqueID"));
            this.time = resultSet.getLong("time");
            this.maxTime = resultSet.getLong("maxtime");
            this.value = resultSet.getInt("value");
            this.enable = resultSet.getInt("enable");
            this.online = resultSet.getInt("online");
            this.onlineTimeOut = resultSet.getInt("onlinetimeout");
            this.enableJail = resultSet.getInt("enablejail");
            this.timeJail = resultSet.getLong("timejail");
            this.timeMaxJail = resultSet.getLong("timemaxjail");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public UUID getUuid() {
        return uuid;
    }

    //Time
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    public void addTime(long time) {
        this.time = this.time + time;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    //Max Time
    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    public void addMaxTime(long maxTime) {
        this.maxTime = this.maxTime + maxTime;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    //Value
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    public void addBlockBreak(int value) {
        this.value = this.value + value;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    //Enable
    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    //Online
    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    //Online Time Out
    public int getOnlineTimeOut() {
        return onlineTimeOut;
    }

    public void setOnlineTimeOut(int onlineTimeOut) {
        this.onlineTimeOut = onlineTimeOut;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    //Enable Jail
    public int getEnableJail() {
        return enableJail;
    }

    public void setEnableJail(int enableJail) {
        this.enableJail = enableJail;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    //Time Jail
    public long getTimeJail() {
        return timeJail;
    }

    public void setTimeJail(long timeJail) {
        this.timeJail = timeJail;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    public void addTimeJail(long timeJail) {
        this.timeJail = this.timeJail + timeJail;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    //Max Time Jail
    public long getTimeMaxJail() {
        return timeMaxJail;
    }

    public void setTimeMaxJail(long timeMaxJail) {
        this.timeMaxJail = timeMaxJail;
        TicketCache.getInstance().getToUpdate().add(this);
    }

    public void addTimeMaxJail(long maxTime) {
        this.timeMaxJail = this.timeMaxJail + timeMaxJail;
        TicketCache.getInstance().getToUpdate().add(this);
    }
}
