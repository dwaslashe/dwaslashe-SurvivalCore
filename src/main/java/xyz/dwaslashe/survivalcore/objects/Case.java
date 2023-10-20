package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.RandomApi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Case {

    private final String id;

    private boolean spawn;

    private List<CaseItem> caseItems = new ArrayList<>();

    public Case(String id){
        this.id = id;
    }

    public Case entry(Consumer<Case> entryCase){
        entryCase.accept(this);
        return this;
    }

    public void addItem(ItemStack itemStack, double chance){
        this.caseItems.add(new CaseItem(itemStack, chance));
    }

    public List<CaseItem> getCaseItems() {
        return caseItems;
    }

    public void setCaseItems(List<CaseItem> caseItems) {
        this.caseItems = caseItems;
    }

    public String getId() {
        return id;
    }

    public boolean isSpawn() {
        return spawn;
    }

    public void setSpawn(boolean spawn) {
        this.spawn = spawn;
    }

    public void spawn(Location location){
        location.getChunk().load(true);

        Block block = location.getBlock();
        block.setType(Material.CHEST);

        BlockState state = block.getState();
        Chest chest = (Chest) state;
        block.setMetadata("CaseBlockEvent", new FixedMetadataValue(Main.getPlugin(), ""));
        for (int i = 0; i < chest.getBlockInventory().getSize(); i++) {
            for (CaseItem caseItem : caseItems) {
                if(RandomApi.getChance(caseItem.getChance()))
                    chest.getBlockInventory().setItem(i, caseItem.getItemStack());
            }
        }
    }
}