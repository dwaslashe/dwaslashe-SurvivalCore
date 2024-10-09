package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.tasks.AbyssTask;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Abyss {

    private static Map<String, Abyss> openAbyssMap = new HashMap<>();

    public static Map<String, Abyss> getOpenAbyssMap() {
        return openAbyssMap;
    }

    private int page;
    private Inventory inventory;

    public Abyss(int page) {
        this.page = page;
        this.inventory = Bukkit.createInventory(null, 5 * 9, Api.fixColor("&fᵩɇ"));
        update();
    }
    private void update() {
        if (AbyssTask.abyssList.size() > page + 1) {
            this.inventory.setItem(inventory.getSize() - 4, new ItemApi(Material.PAPER, 1, (short) 3)
                    .setCustomModelData(11191)
                    .setName("&#FF3131Następna")
                    .getItemStack());
        } else {
            this.inventory.setItem(inventory.getSize() - 4, new ItemApi(Material.AIR, 1, (short) 3).getItemStack());
        }
        if (page > 0) {
            this.inventory.setItem(inventory.getSize() - 6, new ItemApi(Material.PAPER, 1, (short) 3)
                    .setCustomModelData(11189)
                    .setName("&#FF3131Poprzednia")
                    .getItemStack());
        } else {
            this.inventory.setItem(inventory.getSize() - 6, new ItemApi(Material.AIR, 1, (short) 3).getItemStack());
        }
    }

    public void open(Player player){
        Abyss.getOpenAbyssMap().put(player.getName(), this);
        update();
        player.openInventory(inventory);
    }

    public void onAbyssClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        if(event.getSlot() < inventory.getSize()-9) return;
        event.setCancelled(true);
        if(event.getSlot() == inventory.getSize()-4){
            if(exists(page+1)) {
                event.getWhoClicked().closeInventory();
                get(page + 1).open((Player) event.getWhoClicked());
            }
        } else if(event.getSlot() == inventory.getSize()-6){
            if(exists(page-1)){
                event.getWhoClicked().closeInventory();
                get(page - 1).open((Player) event.getWhoClicked());
            }
        } else if(event.getSlot() == inventory.getSize()-5){
            event.getWhoClicked().closeInventory();
        }
    }

    public static Abyss createFirst(){
        return AbyssTask.abyssList.computeIfAbsent(0, Abyss::new);
    }

    public static boolean exists(int page){
        return AbyssTask.abyssList.containsKey(page);
    }

    public static Abyss get(int page){
        return AbyssTask.abyssList.get(page);
    }

    public void addItem(ItemStack itemStack) {
        inventory.addItem(itemStack).forEach((slot, stack) -> AbyssTask.abyssList.computeIfAbsent(page + 1, Abyss::new).addItem(itemStack));
    }

    public int getPage() {
        return page;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
