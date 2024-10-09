package xyz.dwaslashe.survivalcore.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemApi {
    private ItemStack itemStack;

    public ItemApi(Material material, int amount){
        this.itemStack = new ItemStack(material, amount);
    }
    public ItemApi(Material material){
        this.itemStack = new ItemStack(material, 1);
    }
    public ItemApi(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }
    public ItemApi(ItemStack itemStack, int amount){
        ItemStack is = itemStack.clone();
        is.setAmount(amount);
        this.itemStack = is;
    }
    public ItemApi(ItemStack itemStack, short data){
        this.itemStack = itemStack.clone();
        this.itemStack.setDurability(data);
    }

    public ItemApi(Material material, int amount, short durability){
        this(new ItemStack(material, amount, durability));
    }

    public ItemApi(ItemStack itemStack, int amount, short durability){
        ItemStack is = itemStack.clone();
        is.setAmount(amount);
        is.setAmount(amount);
        is.setDurability(durability);
        this.itemStack = new ItemStack(is);
    }
    public ItemApi setName(String name){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(Api.fixColor(name));
        this.itemStack.setItemMeta(meta);
        return this;
    }
    public ItemApi setColor(Color color){
        LeatherArmorMeta meta = (LeatherArmorMeta)itemStack.getItemMeta();
        meta.setColor(color);
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemApi setLore(List<String> lore){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(Api.fixColor(lore));
        this.itemStack.setItemMeta(meta);
        return this;
    }
    public ItemApi setCustomModelData(int customModelData){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setCustomModelData(customModelData);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    public ItemApi addItemFlag(ItemFlag flag){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(flag);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    public ItemApi setLeatherArmorColor(Color color){
        LeatherArmorMeta meta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        meta.setColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    public ItemApi addLore(String lore){
        List<String> line = new ArrayList();
        ItemMeta meta = this.itemStack.getItemMeta();
        if(meta.hasLore()){
            line = new ArrayList(meta.getLore());
        }
        line.add(Api.fixColor(lore));
        meta.setLore(line);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    public List<String> getLore(){
        ItemMeta meta = this.itemStack.getItemMeta();
        return meta.getLore();
    }
    public ItemApi clearLore(){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(new ArrayList());
        this.itemStack.setItemMeta(meta);
        return this;
    }
    public ItemApi setOwnerURL(String texture) {

        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
        gameProfile.getProperties().put("textures", new Property("textures", texture));
        try {
            Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, gameProfile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemApi setOwner(String owner) {
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(owner);
        itemStack.setItemMeta(meta);
        return this;
    }

    private void meta(Class<SkullMeta> skullMetaClass, Object o) {
    }

    public String getOwner(){
        SkullMeta meta = (SkullMeta) this.itemStack.getItemMeta();
        return meta.getOwner();
    }
    public ItemApi addEnchant(Enchantment enchantment, int level){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        this.itemStack.setItemMeta(meta);
        return this;
    }
    public ItemApi removeEnchant(Enchantment enchantment){
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }
    public ItemApi setAmount(int amount){
        this.itemStack.setAmount(amount);
        return this;
    }
    public ItemApi setDurability(int durability){
        this.itemStack.setDurability((short)durability);
        return this;
    }
    public ItemStack getItemStack() {
        return itemStack;
    }
    public MaterialData getMaterialData(){
        return itemStack.getData();
    }
}

