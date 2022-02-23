package xyz.dwaslashe.survivalcore.model.impl;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import xyz.dwaslashe.survivalcore.model.CustomItem;

import java.util.ArrayList;
import java.util.List;

public class CustomItemImpl implements CustomItem {

    private final int id;
    private ItemStack itemStack;

    private List<PotionEffect> a = new ArrayList<>(), b = new ArrayList<>();

    public CustomItemImpl(int id, ItemStack itemStack){
        this.id = id;
        this.itemStack = itemStack;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public ItemStack item() {
        return itemStack;
    }

    @Override
    public List<PotionEffect> whenWear() {
        return a;
    }

    @Override
    public List<PotionEffect> whenInHand() {
        return b;
    }
}
