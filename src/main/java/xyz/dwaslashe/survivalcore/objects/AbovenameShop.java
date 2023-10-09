package xyz.dwaslashe.survivalcore.objects;

import java.util.List;
import java.util.function.Consumer;

public class AbovenameShop {

    private String gui_item_name;
    private String gui_item_head_texture;
    private List<String> gui_item_lore;
    private List<String> commandLine;
    private double cost;
    private String permission;

    public AbovenameShop() {
    }

    public AbovenameShop(Consumer<AbovenameShop> consumer) {
        consumer.accept(this);
    }

    public List<String> getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(List<String> commandLine) {
        this.commandLine = commandLine;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getGui_item_name() {
        return gui_item_name;
    }

    public void setGui_item_name(String gui_item_name) {
        this.gui_item_name = gui_item_name;
    }

    public String getGui_item_head_texture() {
        return gui_item_head_texture;
    }

    public void setGui_item_head_texture(String gui_item_head_texture) {
        this.gui_item_head_texture = gui_item_head_texture;
    }

    public List<String> getGui_item_lore() {
        return gui_item_lore;
    }

    public void setGui_item_lore(List<String> gui_item_lore) {
        this.gui_item_lore = gui_item_lore;
    }
}
