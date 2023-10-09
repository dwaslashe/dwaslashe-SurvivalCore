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
    private int autoMsg;

    @Value(key = "autobossbar", type = "INT(16)")
    private int autoBossBar;

    @Value(key = "deaths", type = "INT(16)")
    private int deaths;

    @Value(key = "discordchat", type = "INT(16)")
    private int discordChat;

    @Value(key = "chat", type = "INT(16)")
    private int chat;

    @Value(key = "msgbossbar", type = "INT(16)")
    private int msgBossBar;

    @Value(key = "homes", type = "TEXT")
    private String homes = "";

    @Value(key = "rates", type = "TEXT")
    private String rates = "";

    @Value(key = "timeafk", type = "INT(32)")
    private int timeAfk;

    @Value(key = "blockbreak", type = "INT(32)")
    private int blockBreak;

    @Value(key = "ignorePlayers", type = "TEXT")
    private String ignorePlayers;

    @Value(key = "ignoreAllPlayers", type = "INT(16)")
    private int ignoreAllPlayers;
    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public User(ResultSet resultSet){
        try {
            this.uuid = UUID.fromString(resultSet.getString("uniqueID"));
            this.nickName = resultSet.getString("nickName");
            this.discordIdAccount = resultSet.getString("discordIdAccount");
            this.abyss = resultSet.getInt("abyss");
            this.autoMsg = resultSet.getInt("automsg");
            this.autoBossBar = resultSet.getInt("autobossbar");
            this.deaths = resultSet.getInt("deaths");
            this.discordChat = resultSet.getInt("discordchat");
            this.chat = resultSet.getInt("chat");
            this.msgBossBar = resultSet.getInt("msgbossbar");
            this.homes = resultSet.getString("homes");
            this.rates = resultSet.getString("rates");
            this.timeAfk = resultSet.getInt("timeafk");
            this.blockBreak = resultSet.getInt("blockbreak");
            this.ignorePlayers = resultSet.getString("ignorePlayers");
            this.ignoreAllPlayers = resultSet.getInt("ignoreAllPlayers");
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
    public int getAutoMsg() {
        return autoMsg;
    }

    public void setAutoMsg(int autoMsg) {
        this.autoMsg = autoMsg;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //AutoBossBar
    public int getAutoBossBar() {
        return autoBossBar;
    }

    public void setAutoBossBar(int autoBossBar) {
        this.autoBossBar = autoBossBar;
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
    public int getDiscordChat() {
        return discordChat;
    }

    public void setDiscordChat(int discordChat) {
        this.discordChat = discordChat;
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
    public int getMsgBossBar() {
        return msgBossBar;
    }

    public void setMsgBossBar(int msgBossBar) {
        this.msgBossBar = msgBossBar;
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

    //Time Afk
    public int getTimeAfk() {
        return timeAfk;
    }

    public void setTimeAfk(int timeAfk) {
        this.timeAfk = timeAfk;
        UserCache.getInstance().getToUpdate().add(this);
    }

    public void addTimeAfk(int timeAfk) {
        this.timeAfk = this.timeAfk + timeAfk;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Block Break
    public int getBlockBreak() {
        return blockBreak;
    }

    public void setBlockBreak(int blockBreak) {
        this.blockBreak = blockBreak;
        UserCache.getInstance().getToUpdate().add(this);
    }

    public void addBlockBreak(int blockBreak) {
        this.blockBreak = this.blockBreak + blockBreak;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Ignore Players
    public String getIgnorePlayers() {
        return ignorePlayers;
    }

    public void setIgnorePlayers(String ignorePlayers) {
        this.ignorePlayers = ignorePlayers;
        UserCache.getInstance().getToUpdate().add(this);
    }

    public void addIgnorePlayers(String ignorePlayers) {
        this.ignorePlayers = this.ignorePlayers + ignorePlayers;
        UserCache.getInstance().getToUpdate().add(this);
    }

    //Ignor All Players
    public int getIgnoreAllPlayers() {
        return ignoreAllPlayers;
    }

    public void setIgnoreAllPlayers(int ignoreAllPlayers) {
        this.ignoreAllPlayers = ignoreAllPlayers;
        UserCache.getInstance().getToUpdate().add(this);
    }

}