package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class ItemCraftListener implements Listener {

    static Set<Material> blockedMaterials = new HashSet<>();
    static {
        blockedMaterials.add(Material.DIAMOND_HELMET);
        blockedMaterials.add(Material.DIAMOND_CHESTPLATE);
        blockedMaterials.add(Material.DIAMOND_LEGGINGS);
        blockedMaterials.add(Material.DIAMOND_BOOTS);
        blockedMaterials.add(Material.DIAMOND_AXE);
        blockedMaterials.add(Material.DIAMOND_SWORD);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerItemCraftEvent(CraftItemEvent event) {
        ItemStack result = event.getRecipe().getResult();
        if(blockedMaterials.contains(result.getType())) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePrepareItemCraftEvent(PrepareItemCraftEvent event){
        if(event.getRecipe() != null){
            ItemStack result = event.getRecipe().getResult();
            if(blockedMaterials.contains(result.getType())) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
                event.getViewers().stream().map(humanEntity -> (Player)humanEntity).forEach(Player::updateInventory);
            }
        }
    }
}
