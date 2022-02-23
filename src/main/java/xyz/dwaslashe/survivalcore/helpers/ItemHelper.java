package xyz.dwaslashe.survivalcore.helpers;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;
import java.util.function.Consumer;

public class ItemHelper extends ItemStack {

    public static ItemHelper get(ItemStack itemStack) {
        return new ItemHelper(itemStack);
    }

    public ItemHelper(Material type) {
        super(type);
    }

    public ItemHelper(Material type, int amount) {
        super(type, amount);
    }

    public ItemHelper(Material type, int amount, short damage) {
        super(type, amount, damage);
    }

    public ItemHelper(Material type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
    }

    protected ItemHelper(ItemStack stack) throws IllegalArgumentException {
        super(stack);
    }

    public ItemHelper withMeta(Consumer<ItemMeta> meta) {
        ItemMeta itemMeta = getItemMeta();
        meta.accept(itemMeta);
        setItemMeta(itemMeta);
        return this;
    }

    public void setDisplayName(String title) {
        withMeta(itemMeta -> itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title)));
    }


    public void addAttributeModifier(Attribute attribute, double value, AttributeModifier.Operation operation, EquipmentSlot equipmentSlot) {
        withMeta(itemMeta -> itemMeta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.getKey().getKey(), value, operation, equipmentSlot)));
    }

    public void removeAttributeModifier(Attribute attribute) {
        withMeta(itemMeta -> itemMeta.removeAttributeModifier(attribute));
    }

    public void setAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        withMeta(itemMeta -> {
            itemMeta.removeAttributeModifier(attribute);
            itemMeta.addAttributeModifier(attribute, attributeModifier);
        });
    }

    public void setRepairCost(int cost) {
        itemStack(itemStack -> itemStack.setRepairCost(cost));
    }

    public ItemHelper itemStack(Consumer<net.minecraft.world.item.ItemStack> itemStackConsumer) {
        itemStackConsumer.accept(net.minecraft.world.item.ItemStack.fromBukkitCopy(this));
        return this;
    }

}
