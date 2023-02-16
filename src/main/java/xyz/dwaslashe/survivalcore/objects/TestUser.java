package xyz.dwaslashe.survivalcore.objects;

import xyz.dwaslashe.survivalcore.cache.TestUserCache;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@DataObject(table = "users")
public class TestUser {

    @PrimaryKey(value = @Value(key = "uniqueID", type = "VARCHAR(64)"))
    private final UUID uuid;

    @Value(key = "nickName", type = "VARCHAR(32)")
    private String nickName;

    @Value(key = "discordIdAccount", type = "VARCHAR(32)")
    private String discordIdAccount;

    @Value(key = "abyss", type = "INT(16)")
    private int abyss;

    @Value(key = "automsg", type = "INT(16)")
    private int automsg;

    @Value(key = "autobossbar", type = "INT(16)")
    private int autobossbar;

    @Value(key = "deaths", type = "INT(16)")
    private int deaths;

    @Value(key = "discordchat", type = "INT(16)")
    private int discordchat;

    public TestUser(UUID uuid) {
        this.uuid = uuid;
    }

    public TestUser(ResultSet resultSet){
        try {
            this.uuid = UUID.fromString(resultSet.getString("uniqueID"));
            this.nickName = resultSet.getString("nickName");
            this.discordIdAccount = resultSet.getString("discordIdAccount");
            this.abyss = resultSet.getInt("abyss");
            this.automsg = resultSet.getInt("automsg");
            this.autobossbar = resultSet.getInt("autobossbar");
            this.deaths = resultSet.getInt("deaths");
            this.discordchat = resultSet.getInt("discordchat");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public UUID getUuid() {
        return uuid;
    }

    //Nickname
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        TestUserCache.getInstance().getToUpdate().add(this);
    }

    //Discord Account ID
    public String getDiscordIdAccount() {
        return discordIdAccount;
    }

    public void setDiscordIdAccount(String discordIdAccount) {
        this.discordIdAccount = discordIdAccount;
        TestUserCache.getInstance().getToUpdate().add(this);
    }

    //Abyss
    public int getAbyss() {
        return abyss;
    }

    public void setAbyss(int abyss) {
        this.abyss = abyss;
        TestUserCache.getInstance().getToUpdate().add(this);
    }

    //AutoMsg
    public int getAutomsg() {
        return automsg;
    }

    public void setAutomsg(int automsg) {
        this.automsg = automsg;
        TestUserCache.getInstance().getToUpdate().add(this);
    }

    //AutoBossBar
    public int getAutobossbar() {
        return autobossbar;
    }

    public void setAutobossbar(int autobossbar) {
        this.autobossbar = autobossbar;
        TestUserCache.getInstance().getToUpdate().add(this);
    }

    //Deaths
    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        TestUserCache.getInstance().getToUpdate().add(this);
    }

    //DiscordChat
    public int getDiscordchat() {
        return discordchat;
    }

    public void setDiscordchat(int discordchat) {
        this.discordchat = discordchat;
        TestUserCache.getInstance().getToUpdate().add(this);
    }
}