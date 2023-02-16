package xyz.dwaslashe.survivalcore.model.impl;

import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.model.User;

public class UserImpl implements User {

    private final String name;
    private Player player;

    private boolean autobar = true, autochat = true, chat = true, death = true, abyss = true, online;

    public UserImpl(String name){
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }

    public void setAbyss(boolean abyss) {
        this.abyss = abyss;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public void setAutochat(boolean autochat) {
        this.autochat = autochat;
    }

    public void setAutobar(boolean autobar) {
        this.autobar = autobar;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean abyss() {
        return abyss;
    }

    @Override
    public boolean death() {
        return death;
    }

    @Override
    public boolean msg() {
        return chat;
    }

    @Override
    public boolean autochat() {
        return autochat;
    }

    public boolean autobar() {
        return autobar;
    }

    @Override
    public boolean online() {
        return online;
    }

    @Override
    public void setOnline(boolean online) {
        this.online = online;
        UserCache cache = Main.getPlugin().getUserCache();
        cache.getOnlineUserMap().remove(name);
        if(online) cache.getOnlineUserMap().put(name, this);
    }

    public boolean isAutobar() {
        return autobar;
    }

    public boolean isAutochat() {
        return autochat;
    }
}
