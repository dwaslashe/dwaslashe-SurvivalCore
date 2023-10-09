package xyz.dwaslashe.survivalcore.objects;

import xyz.dwaslashe.survivalcore.cache.MoneyTargetCache;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;

import java.sql.ResultSet;
import java.sql.SQLException;

@DataObject(table = "moneytarget")
public class MoneyTarget {

    @PrimaryKey(value = @Value(key = "id", type = "INT(16)"))
    private int id;

    @Value(key = "money", type = "INT(16)")
    private int money;

    @Value(key = "limitmoney", type = "INT(16)")
    private int limitmoney;

    @Value(key = "title", type = "TEXT")
    private String title;

    @Value(key = "transactions", type = "TEXT")
    private String transactions;

    public MoneyTarget(int id) {
        this.id = id;
    }

    public MoneyTarget(ResultSet resultSet){
        try {
            this.id = resultSet.getInt("id");
            this.money = resultSet.getInt("money");
            this.limitmoney = resultSet.getInt("limitmoney");
            this.title = resultSet.getString("title");
            this.transactions = resultSet.getString("transactions");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }

    //Money

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        MoneyTargetCache.getInstance().getToUpdate().add(this);
    }

    public void addMoney(int money) {
        this.money = this.money + money;
        MoneyTargetCache.getInstance().getToUpdate().add(this);
    }

    //Limit Money

    public int getLimitMoney() {
        return limitmoney;
    }

    public void setLimitMoney(int limitmoney) {
        this.limitmoney = limitmoney;
        MoneyTargetCache.getInstance().getToUpdate().add(this);
    }

    public void addLimitMoney(int limitmoney) {
        this.limitmoney = this.limitmoney + limitmoney;
        MoneyTargetCache.getInstance().getToUpdate().add(this);
    }

    //Title

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        MoneyTargetCache.getInstance().getToUpdate().add(this);
    }

    //Transactions

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
        MoneyTargetCache.getInstance().getToUpdate().add(this);
    }

    public void addTransactions(String transactions) {
        this.transactions = this.transactions + transactions;
        MoneyTargetCache.getInstance().getToUpdate().add(this);
    }

}
