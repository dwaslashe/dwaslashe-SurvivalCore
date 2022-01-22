package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.CustomItemsCommand;
import xyz.dwaslashe.survivalcore.utils.CustomItemApi;

public class CustomItemListener implements Listener {
    protected static final Map<UUID, CustomItemApi> uuid = Maps.newHashMap();

    @EventHandler
    public void onShot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player) {
            Player player = (Player)event.getEntity().getShooter();
            CustomItemApi item = new CustomItemApi(player.getItemInHand());
            if (item.getCustomModelData() != 0) {
                item = CustomItemsCommand.getItemByID(item.getCustomModelData());
                if (item != null)
                    uuid.put(event.getEntity().getUniqueId(), item);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player)event.getEntity();
            Player attacker = (Player)event.getDamager();
            CustomItemApi item = new CustomItemApi(attacker.getItemInHand());
            if (isFromThisPlugin(item.getCustomModelData())) {
                item = CustomItemsCommand.getItemByID(item.getCustomModelData());
                if (item != null && item.getItemStack().getType() != Material.BOW && item.getItemStack().getType() != Material.CROSSBOW)
                    item.getPotionEffects().forEach(victim::addPotionEffect);
            }
        } else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow)event.getDamager();
            if (uuid.containsKey(arrow.getUniqueId()) && arrow.getShooter() instanceof Player) {
                CustomItemApi item = uuid.get(arrow.getUniqueId());
                ((Player)event.getEntity()).addPotionEffects(item.getPotionEffects());
                uuid.remove(arrow.getUniqueId());
            }
        }
    }

    protected boolean isFromThisPlugin(int id) {
        for (CustomItemApi item : Main.getItems()) {
            if (item.getCustomModelData() == id)
                return true;
        }
        return false;
    }
}
