package xyz.dwaslashe.survivalcore.helpers;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;

import java.util.List;
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

    public ItemHelper setLore(List<String> lore){
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(Api.fixColor(lore));
        setItemMeta(itemMeta);
        return this;
    }

    public void setDisplayName(String title) {
        withMeta(itemMeta -> itemMeta.setDisplayName(Api.fixColor(title)));
    }
    public void addEnchant(Enchantment enchantment, int level){
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        setItemMeta(itemMeta);
        return;
    }

    public void addAttributeModifier(Attribute attribute, double value, AttributeModifier.Operation operation, EquipmentSlot equipmentSlot) {
        withMeta(itemMeta -> itemMeta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.getKey().getKey(), value, operation, equipmentSlot)));
    }

    public void setAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        withMeta(itemMeta -> {
            itemMeta.removeAttributeModifier(attribute);
            itemMeta.addAttributeModifier(attribute, attributeModifier);
        });
    }

}
