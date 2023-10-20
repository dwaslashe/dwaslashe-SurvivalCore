package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.inventory.ItemStack;

public class CaseItem {

    private final ItemStack itemStack;
    private double chance;

    public CaseItem(ItemStack itemStack, double chance) {
        this.itemStack = itemStack;
        this.chance = chance;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }
}