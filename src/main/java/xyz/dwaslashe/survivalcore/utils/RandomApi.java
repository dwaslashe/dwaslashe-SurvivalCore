package xyz.dwaslashe.survivalcore.utils;

import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class RandomApi {
    private static final Random random = new Random();

    public static int getRandomInt(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }
    public static String randomElementList(List<String> lista) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomElement = random.nextInt(lista.size());
        return lista.get(randomElement);
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

    public static int getInt(int min, int max){
        if(min == max) return min;
        else if(min > max) return getInt(max, min);
        else return random.nextInt(max - min + 1) + min;
    }

    public static double getDouble(double min, double max){
        if(min == max) return min;
        else if(min > max) return getDouble(max, min);
        else return random.nextDouble() * (max - min) + min;
    }

    public static float getFloat(float min, float max){
        if(min == max) return min;
        else if(min > max) return getFloat(max, min);
        else return random.nextFloat() * (max - min) + min;
    }

    public static boolean getChance(double chance){
        return chance >= 100.0 || chance >= randomDouble(0, 100);
    }
}
