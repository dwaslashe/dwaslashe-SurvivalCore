package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class EnchantList {

    private static final HashMap<String, Enchantment> enchants = new HashMap<>();

    static {
        for (Enchantment value : Enchantment.values()) {
            enchants.put(value.getKey().value(), value);
        }
        enchants.putIfAbsent("efficiency".toUpperCase(), Enchantment.DIG_SPEED);
        enchants.putIfAbsent("protection".toUpperCase(), Enchantment.PROTECTION_ENVIRONMENTAL);
        enchants.putIfAbsent("protection_environmental".toUpperCase(), Enchantment.PROTECTION_ENVIRONMENTAL);
        enchants.putIfAbsent("durability".toUpperCase(), Enchantment.DURABILITY);
        enchants.putIfAbsent("fortune".toUpperCase(), Enchantment.LOOT_BONUS_BLOCKS);
        enchants.putIfAbsent("loot_bonus_blocks".toUpperCase(), Enchantment.LOOT_BONUS_BLOCKS);
        enchants.putIfAbsent("infinity".toUpperCase(), Enchantment.ARROW_INFINITE);
        enchants.putIfAbsent("sharpness".toUpperCase(), Enchantment.DAMAGE_ALL);
        enchants.putIfAbsent("damage_all".toUpperCase(), Enchantment.DAMAGE_ALL);
        enchants.putIfAbsent("fireaspect".toUpperCase(), Enchantment.FIRE_ASPECT);
        enchants.putIfAbsent("fire_aspect".toUpperCase(), Enchantment.FIRE_ASPECT);
        enchants.putIfAbsent("knockback".toUpperCase(), Enchantment.KNOCKBACK);
        enchants.putIfAbsent("knock".toUpperCase(), Enchantment.KNOCKBACK);
        enchants.putIfAbsent("flame".toUpperCase(), Enchantment.ARROW_FIRE);
        enchants.putIfAbsent("punch".toUpperCase(), Enchantment.ARROW_KNOCKBACK);
        enchants.putIfAbsent("power".toUpperCase(), Enchantment.ARROW_DAMAGE);
        enchants.putIfAbsent("depthstrider".toUpperCase(), Enchantment.DEPTH_STRIDER);
        enchants.putIfAbsent("silk_touch".toUpperCase(), Enchantment.SILK_TOUCH);
        enchants.putIfAbsent("silk".toUpperCase(), Enchantment.SILK_TOUCH);
        enchants.putIfAbsent("oxygen".toUpperCase(), Enchantment.OXYGEN);
        enchants.putIfAbsent("lure".toUpperCase(), Enchantment.LURE);
        enchants.putIfAbsent("water_worker".toUpperCase(), Enchantment.WATER_WORKER);
        enchants.putIfAbsent("waterworker".toUpperCase(), Enchantment.WATER_WORKER);
        enchants.putIfAbsent("water".toUpperCase(), Enchantment.WATER_WORKER);
        enchants.putIfAbsent("thorns".toUpperCase(), Enchantment.THORNS);
        enchants.putIfAbsent("protection_fall".toUpperCase(), Enchantment.PROTECTION_FALL);
        enchants.putIfAbsent("water_fall".toUpperCase(), Enchantment.PROTECTION_FALL);
    }
    public static Enchantment get(String enchant){
        return enchants.get(enchant.toUpperCase());
    }

    public static HashMap<String, Enchantment> getEnchants( ) {
        return enchants;
    }
}
