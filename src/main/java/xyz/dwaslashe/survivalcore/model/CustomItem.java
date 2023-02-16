package xyz.dwaslashe.survivalcore.model;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import xyz.dwaslashe.survivalcore.Main;

import java.util.List;
import java.util.Optional;

public interface CustomItem {

    static Optional<CustomItem> get(ItemStack itemStack){
        if(itemStack == null || itemStack.getType().isAir()) return Optional.empty();
        NBTItem nbtItem = new NBTItem(itemStack);
        return Main.getPlugin().getItemCache().getItem(nbtItem.hasKey("cid") ? nbtItem.getInteger("cid") : -1);
    }

    int id();

    ItemStack item();

    List<PotionEffect> whenInSecondHand();

    List<PotionEffect> whenWear();

    List<PotionEffect> whenInHand();

    default void give(Player player){
        ItemStack itemStack = item().clone();
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("cid", id());
        nbtItem.applyNBT(itemStack);
        player.getInventory().addItem(itemStack);
    }
}
