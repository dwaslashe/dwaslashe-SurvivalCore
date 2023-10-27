package xyz.dwaslashe.survivalcore.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;
import xyz.dwaslashe.survivalcore.cache.UserTreeCache;
import xyz.dwaslashe.survivalcore.objects.UserTree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class BlockBreakListener implements Listener {
    private final List<Material> logs = Arrays.asList(Material.OAK_LOG, Material.SPRUCE_LOG, Material.DARK_OAK_LOG, Material.ACACIA_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.CHERRY_LOG);

    private final List<Material> leaves = Arrays.asList(Material.OAK_LEAVES, Material.SPRUCE_LEAVES, Material.DARK_OAK_LEAVES, Material.ACACIA_LEAVES, Material.BIRCH_LEAVES, Material.JUNGLE_LEAVES, Material.CHERRY_LEAVES);

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        UserTree userTree = UserTreeCache.getInstance().compute(event.getPlayer().getUniqueId());

        if (event.isCancelled()) return;

        if (userTree.getAnimation() == null) {
            userTree.setAnimation("sand");
        }

        if (userTree.getAnimation().equals("simple")) {
            return;
        }
        //if (userTree.getAnimation().equals("animation")) {
        //    HashSet<Block> treeBlocks = new HashSet();
        //    HashSet<Block> blocksToSearch = new HashSet();
        //    HashSet<Block> searched = new HashSet();
        //    Block startingPoint = event.getBlock().getRelative(BlockFace.UP);
        //    blocksToSearch.add(startingPoint);
        //    searched.add(event.getBlock());
        //    int i = 0;
        //    for (i = 0; i < 1000 && !blocksToSearch.isEmpty(); ++i) {
        //        Block block = (Block) blocksToSearch.iterator().next();
        //        blocksToSearch.remove(block);
        //        searched.add(block);
        //        if (!logs.contains(block.getType()) && !leaves.contains(block.getType())) {
        //            if (!block.getType().isTransparent()) {
        //                return;
        //            }
        //        } else {
        //            treeBlocks.add(block);
        //            if (!searched.contains(block.getRelative(BlockFace.UP))) {
        //                blocksToSearch.add(block.getRelative(BlockFace.UP));
        //            }
//
        //            if (!searched.contains(block.getRelative(BlockFace.DOWN))) {
        //                blocksToSearch.add(block.getRelative(BlockFace.DOWN));
        //            }
//
        //            if (!searched.contains(block.getRelative(BlockFace.WEST))) {
        //                blocksToSearch.add(block.getRelative(BlockFace.WEST));
        //            }
//
        //            if (!searched.contains(block.getRelative(BlockFace.EAST))) {
        //                blocksToSearch.add(block.getRelative(BlockFace.EAST));
        //            }
//
        //            if (!searched.contains(block.getRelative(BlockFace.NORTH))) {
        //                blocksToSearch.add(block.getRelative(BlockFace.NORTH));
        //            }
//
        //            if (!searched.contains(block.getRelative(BlockFace.SOUTH))) {
        //                blocksToSearch.add(block.getRelative(BlockFace.SOUTH));
        //            }
        //        }
        //    }
        //    if (i < 1000) {
        //        Iterator var = treeBlocks.iterator();
//
        //        while (var.hasNext()) {
        //            Block block = (Block) var.next();
        //            FallingBlock sand = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
        //            sand.setVelocity(new Vector(0.15D + (double) (block.getY() - startingPoint.getY()) * 0.03D, (double) (block.getX() - startingPoint.getX()) * 0.1D, 0.0D));
        //            block.setType(Material.AIR);
        //        }
//
        //        if (treeBlocks.size() > 7) {
        //            startingPoint.getWorld().playSound(startingPoint.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 0.5F);
        //        }
        //    }
        //}

        Block relative = null;
        do {
            relative = relative == null ? event.getBlock().getRelative(BlockFace.UP) : relative.getRelative(BlockFace.UP);
            if (!this.logs.contains(relative.getType())) {
                return;
            }

            if (userTree.getAnimation().equals("sand") || userTree.getAnimation() == null) {
                FallingBlock fallingBlock = event.getBlock().getWorld().spawnFallingBlock(relative.getLocation().add(0.5D, 0.0D, 0.5D), relative.getBlockData());
                fallingBlock.setVelocity(new Vector(0.0D, (double)(relative.getY() - (event.getBlock().getY() + 1)) * 0.01D, 0.0D));
            } else if (userTree.getAnimation().equals("quick")) {
                Player player = event.getPlayer();
                player.breakBlock(relative);
            }

            relative.setType(Material.AIR);
        } while(relative.getRelative(BlockFace.UP).getType() != Material.AIR && !this.leaves.contains(relative.getRelative(BlockFace.UP).getType()));
    }
}
