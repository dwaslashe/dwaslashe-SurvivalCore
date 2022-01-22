package xyz.dwaslashe.survivalcore.utils;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import xyz.dwaslashe.survivalcore.enums.CustomHand;

public class CustomItemApi {
    private final ItemStack itemStack;

    private List<PotionEffect> potionEffects = Lists.newArrayList();

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public List<PotionEffect> getPotionEffects() {
        return this.potionEffects;
    }

    public CustomItemApi setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
        return this;
    }

    public CustomItemApi(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public CustomItemApi setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public CustomItemApi setDurability(short data) {
        this.itemStack.setDurability(data);
        return this;
    }

    public CustomItemApi(Material material, CustomHand hand) {
        this.itemStack = new ItemStack(material);
        setLore(Arrays.asList(new String[] { "", "&8Hand: " + hand.name() }));
    }

    public CustomItemApi setCustomModelData(int data) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setCustomModelData(Integer.valueOf(data));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public int getCustomModelData() {
        return this.itemStack.getItemMeta().hasCustomModelData() ? this.itemStack.getItemMeta().getCustomModelData() : 0;
    }

    public CustomItemApi setName(String text) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(Api.fixColor(text));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public CustomItemApi setLore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(Api.fixColor(lore));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public String getName() {
        return this.itemStack.getItemMeta().hasDisplayName() ? "" : this.itemStack.getItemMeta().getDisplayName();
    }

    public Collection<String> getLore() {
        return this.itemStack.getItemMeta().hasLore() ? Lists.newArrayList() : this.itemStack.getItemMeta().getLore();
    }
}
