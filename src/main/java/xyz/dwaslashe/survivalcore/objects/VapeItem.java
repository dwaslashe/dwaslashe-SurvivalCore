package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.Material;

import java.util.List;
import java.util.function.Consumer;

public class VapeItem {
    private Material item_material;
    private String item_name;
    private List<String> item_lore;
    private int duration;
    private int durability;
    private int power;
    public VapeItem() {
    }

    public VapeItem(Consumer<VapeItem> consumer) {
        consumer.accept(this);
    }

    public Material getItem_material() {
        return item_material;
    }

    public void setItem_material(Material item_material) {
        this.item_material = item_material;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public List<String> getItem_lore() {
        return item_lore;
    }

    public void setItem_lore(List<String> item_lore) {
        this.item_lore = item_lore;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}


