package xyz.dwaslashe.survivalcore.utils;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import xyz.dwaslashe.survivalcore.Main;

public class LocationApi {

    public static boolean isSafe(org.bukkit.Location loc) {
        Block feet = loc.getBlock();
        if (!feet.getType().isTransparent() &&
                !feet.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().getType().isTransparent()) {
            return false;
        }
        Block head = feet.getRelative(BlockFace.UP);
        if (!head.getType().isTransparent()) {
            return false;
        }
        Block ground = feet.getRelative(BlockFace.DOWN);
        if (!ground.getType().isSolid()) {
            return false;
        }
        Biome biome = loc.getBlock().getBiome();
        if (biome == Biome.OCEAN || biome == Biome.DEEP_OCEAN || biome == Biome.COLD_OCEAN || biome == Biome.DEEP_COLD_OCEAN || biome == Biome.DEEP_FROZEN_OCEAN || biome == Biome.DEEP_LUKEWARM_OCEAN || biome == Biome.DEEP_OCEAN || biome == Biome.FROZEN_OCEAN || biome == Biome.LUKEWARM_OCEAN || biome == Biome.WARM_OCEAN || biome == Biome.RIVER || biome == Biome.FROZEN_RIVER) {
            return false;
        }
        return true;
    }

    public static org.bukkit.Location getRandomLocation(World world) {
        int xMax = 1650;
        int zMax = -1650;
        int xMin = 1000;
        int zMin = -1000;
        int x = RandomApi.getRandomInt(xMin, xMax);
        int z = RandomApi.getRandomInt(zMin, zMax);
        int y = world.getHighestBlockYAt(x, z) + 1;
        org.bukkit.Location loc = new org.bukkit.Location(world, x, y, z);
        if (!isSafe(loc))
            return getRandomLocation(world);
        return loc;
    }
}
