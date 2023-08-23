package xyz.dwaslashe.survivalcore.objects;

import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@DataObject(table = "users")
public class User {

    @PrimaryKey(value = @Value(key = "uniqueID", type = "VARCHAR(64)"))
    private final UUID uuid;

    @Value(key = "nickName", type = "VARCHAR(32)")
    private String nickName = "";

    @Value(key = "discordIdAccount", type = "VARCHAR(32)")
    private String discordIdAccount = "";

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

    @Value(key = "chat", type = "INT(16)")
    private int chat;

    @Value(key = "msgbossbar", type = "INT(16)")
    private int msgbossbar;

    @Value(key = "homes", type = "TEXT")
    private String homes = "";

    @Value(key = "rates", type = "TEXT")
    private String rates = "";

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public User(ResultSet resultSet){
        try {
            this.uuid = UUID.fromString(resultSet.getString("uniqueID"));
            this.nickName = resultSet.getString("nickName");
            this.discordIdAccount = resultSet.getString("discordIdAccount");
            this.abyss = resultSet.getInt("abyss");
            this.automsg = resultSet.getInt("automsg");
            this.autobossbar = resultSet.getInt("autobossbar");
            this.deaths = resultSet.getInt("deaths");
            this.discordchat = resultSet.getInt("discordchat");
            this.chat = resultSet.getInt("chat");
            this.msgbossbar = resultSet.getInt("msgbossbar");
            this.homes = resultSet.getString("homes");
            this.rates = resultSet.getString("rates");
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
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Discord Account ID
    public String getDiscordIdAccount() {
        return discordIdAccount;
    }

    public void setDiscordIdAccount(String discordIdAccount) {
        this.discordIdAccount = discordIdAccount;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Abyss
    public int getAbyss() {
        return abyss;
    }

    public void setAbyss(int abyss) {
        this.abyss = abyss;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //AutoMsg
    public int getAutomsg() {
        return automsg;
    }

    public void setAutomsg(int automsg) {
        this.automsg = automsg;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //AutoBossBar
    public int getAutobossbar() {
        return autobossbar;
    }

    public void setAutobossbar(int autobossbar) {
        this.autobossbar = autobossbar;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Deaths
    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //DiscordChat
    public int getDiscordchat() {
        return discordchat;
    }

    public void setDiscordchat(int discordchat) {
        this.discordchat = discordchat;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Chat
    public int getChat() {
        return chat;
    }

    public void setChat(int chat) {
        this.chat = chat;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Msg BossBar
    public int getMsgbossbar() {
        return msgbossbar;
    }

    public void setMsgbossbar(int msgbossbar) {
        this.msgbossbar = msgbossbar;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Homes
    public String getHomes() {
        return homes;
    }

    public void setHomes(String homes) {
        this.homes = homes;
        UserCache.getInstance().getToUpdate().add(this);
    }

    public void addHomes(String homes) {
        this.homes = this.homes + homes;
        UserCache.getInstance().getToUpdate().add(this);
    }

    public void removeHomes(String homes) {
        String sectionToRemove = homes + "&";
        int startIndex = this.homes.indexOf(sectionToRemove);
        if (startIndex != -1) {
            int endIndex = this.homes.indexOf("#", startIndex);
            if (endIndex != -1) {
                endIndex = endIndex + 1;
                String substringToRemove = this.homes.substring(startIndex, endIndex);
                this.homes = this.homes.replace(substringToRemove, "");
            }
        }
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Rates
    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
        UserCache.getInstance().getToUpdate().add(this);
    }

    public void addRates(String rates) {
        this.rates = this.rates + rates;
        UserCache.getInstance().getToUpdate().add(this);
    }

}