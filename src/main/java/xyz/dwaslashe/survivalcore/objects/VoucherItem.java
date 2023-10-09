package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.Material;

import java.util.List;
import java.util.function.Consumer;

public class VoucherItem {
    private Material item_material;
    private String item_name;
    private String item_head_texture;

    private String item_head_name;
    private List<String> item_lore;
    private List<String> commandLine;

    private boolean owner;
    public VoucherItem() {
    }

    public VoucherItem(Consumer<VoucherItem> consumer) {
        consumer.accept(this);
    }

    public Material getItem_material() {
        return item_material;
    }

    public void setItem_material(Material item_material) {
        this.item_material = item_material;
    }


    public List<String> getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(List<String> commandLine) {
        this.commandLine = commandLine;
    }

    public String getItem_head_name() {
        return item_head_name;
    }

    public void setItem_head_name(String item_head_name) {
        this.item_head_name = item_head_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_head_texture() {
        return item_head_texture;
    }

    public void setItem_head_texture(String item_head_texture) {
        this.item_head_texture = item_head_texture;
    }

    public List<String> getItem_lore() {
        return item_lore;
    }

    public void setItem_lore(List<String> item_lore) {
        this.item_lore = item_lore;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}

