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
import xyz.dwaslashe.survivalcore.Main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemCraftListener implements Listener {

    static Set<Material> blockedMaterials = new HashSet<>();
    static {
        List<String> materialNames = Main.pluginConfig.getRecipes().getBlockedMaterials();
        for (String materialName : materialNames) {
            Material material = Material.getMaterial(materialName);
            if (material != null) {
                blockedMaterials.add(material);
            } else {
                Main.getPlugin().getLogger().warning("Niepoprawny materiał w pliku konfiguracyjnym: " + materialName);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerItemCraftEvent(CraftItemEvent event) {
        ItemStack result = event.getRecipe().getResult();
        if (Main.pluginConfig.getRecipes().isBlockMaterials()) {
            if (blockedMaterials.contains(result.getType())) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePrepareItemCraftEvent(PrepareItemCraftEvent event){
        if(event.getRecipe() != null){
            ItemStack result = event.getRecipe().getResult();
            if (Main.pluginConfig.getRecipes().isBlockMaterials()) {
                if (blockedMaterials.contains(result.getType())) {
                    event.getInventory().setResult(new ItemStack(Material.AIR));
                    event.getViewers().stream().map(humanEntity -> (Player) humanEntity).forEach(Player::updateInventory);
                }
            }
        }
    }
}
