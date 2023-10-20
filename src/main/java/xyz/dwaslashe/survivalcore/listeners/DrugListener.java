package xyz.dwaslashe.survivalcore.listeners;

import me.dexuby.UltimateDrugs.api.DrugPlantBreakEvent;
import me.dexuby.UltimateDrugs.api.DrugPlantReplantEvent;
import me.dexuby.UltimateDrugs.drugs.growing.Plant;
import me.dexuby.UltimateDrugs.managers.PlantManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.UUID;

public class DrugListener implements Listener {
    //@EventHandler
    //public void blockPistonExtend(BlockPistonExtendEvent event) {
    //    System.out.println("piston execute");
    //    for (org.bukkit.block.Block block : event.getBlocks()) {
    //        System.out.println("block: " + block.getType());
    //        System.out.println("getMetadata: " + block.getMetadata("ultimatedrugs-plant"));
    //        System.out.println("hasMetadata: " + block.hasMetadata("ultimatedrugs-plant"));
    //        if (block.hasMetadata("ultimatedrugs-plant")) {
    //            System.out.println("break Piston drug plant");
    //            UUID uuid = UUID.fromString(((MetadataValue) block.getMetadata("ultimatedrugs-plant").get(0)).asString());
//
    //            Plant plantByUUID = new PlantManager().getPlantByUUID(uuid);
    //            new DrugPlantReplantEvent(plantByUUID, (Player) Bukkit.getOfflinePlayer("Gacusiowaty"));
    //        }
    //    }
    //}
//
    //@EventHandler
    //public void drugPlantBreak(DrugPlantBreakEvent event) {
    //    System.out.println("plantOwner: " + event.getPlant().getOwner());
    //    System.out.println("baseBlockType: " + event.getPlant().getBaseBlock().getType());
    //    System.out.println("parentDrugId: " + event.getPlant().getParentDrugId());
    //    System.out.println("currentBlocks: " + event.getPlant().getCurrentBlocks());
    //    System.out.println("foundationBlock: " + event.getPlant().getFoundationBlock());
    //    System.out.println("placeDirection: " + event.getPlant().getPlaceDirection());
    //    System.out.println("growingStage: " + event.getPlant().getCurrentGrowingStage());
    //    System.out.println("timeGrown: " + event.getPlant().getTimeGrown());
    //}
}
