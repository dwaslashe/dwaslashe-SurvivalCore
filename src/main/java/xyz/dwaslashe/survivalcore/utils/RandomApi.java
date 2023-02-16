package xyz.dwaslashe.survivalcore.utils;

import org.bukkit.util.Vector;

import java.util.Random;

public class RandomApi {
    private static final Random random = new Random();

    public static int getRandomInt(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public static double randomDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static Vector randomVector(double xMax, double yMax, double zMax) {
        return new Vector(
                randomDouble(xMax, 0),
                randomDouble(yMax, 0),
                randomDouble(zMax, 0)
        );
    }

    public static Vector randomVector(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
        return new Vector(
                randomDouble(xMax, xMin),
                randomDouble(yMax, yMin),
                randomDouble(zMin, zMax)
        );
    }

    public static boolean getChance(double chance){
        return chance >= 100.0 || chance >= randomDouble(0, 100);
    }
}
