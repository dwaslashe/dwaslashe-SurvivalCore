package xyz.dwaslashe.survivalcore.utils;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class ItemHelper extends ItemStack {

    public ItemHelper() {
    }

    public ItemHelper(Material type) {
        super(type);
    }

    public ItemHelper(Material type, int amount) {
        super(type, amount);
    }

    public ItemHelper(Material type, int amount, short data){
        this(type, amount);
        setDurability(data);
    }

    public ItemHelper(ItemStack stack) {
        super(stack);
    }

    public ItemHelper setDisplayName(String displayName){
        editMeta(itemMeta -> itemMeta.setDisplayName(Api.fixColor(displayName)));
        return this;
    }

    public void setLore(List<String> lore){
        editMeta(itemMeta -> itemMeta.setLore(Api.fixColor(lore)));
    }

    public ItemHelper entry(Consumer<ItemHelper> entryItem){
        entryItem.accept(this);
        return this;
    }

}
