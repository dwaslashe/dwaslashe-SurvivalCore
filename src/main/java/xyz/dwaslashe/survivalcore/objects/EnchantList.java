package xyz.dwaslashe.survivalcore.objects;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class EnchantList {

    private static HashMap<String, Enchantment> enchants = new HashMap();

    static {
        (enchants = new HashMap()).put("efficiency".toUpperCase(), Enchantment.DIG_SPEED);
        enchants.put("protection".toUpperCase(), Enchantment.PROTECTION_ENVIRONMENTAL);
        enchants.put("protection_environmental".toUpperCase(), Enchantment.PROTECTION_ENVIRONMENTAL);
        enchants.put("durability".toUpperCase(), Enchantment.DURABILITY);
        enchants.put("fortune".toUpperCase(), Enchantment.LOOT_BONUS_BLOCKS);
        enchants.put("loot_bonus_blocks".toUpperCase(), Enchantment.LOOT_BONUS_BLOCKS);
        enchants.put("infinity".toUpperCase(), Enchantment.ARROW_INFINITE);
        enchants.put("sharpness".toUpperCase(), Enchantment.DAMAGE_ALL);
        enchants.put("damage_all".toUpperCase(), Enchantment.DAMAGE_ALL);
        enchants.put("fireaspect".toUpperCase(), Enchantment.FIRE_ASPECT);
        enchants.put("fire_aspect".toUpperCase(), Enchantment.FIRE_ASPECT);
        enchants.put("knockback".toUpperCase(), Enchantment.KNOCKBACK);
        enchants.put("knock".toUpperCase(), Enchantment.KNOCKBACK);
        enchants.put("flame".toUpperCase(), Enchantment.ARROW_FIRE);
        enchants.put("punch".toUpperCase(), Enchantment.ARROW_KNOCKBACK);
        enchants.put("power".toUpperCase(), Enchantment.ARROW_DAMAGE);
        enchants.put("depthstrider".toUpperCase(), Enchantment.DEPTH_STRIDER);
        enchants.put("silk_touch".toUpperCase(), Enchantment.SILK_TOUCH);
        enchants.put("silk".toUpperCase(), Enchantment.SILK_TOUCH);
        enchants.put("oxygen".toUpperCase(), Enchantment.OXYGEN);
        enchants.put("lure".toUpperCase(), Enchantment.LURE);
        enchants.put("water_worker".toUpperCase(), Enchantment.WATER_WORKER);
        enchants.put("waterworker".toUpperCase(), Enchantment.WATER_WORKER);
        enchants.put("water".toUpperCase(), Enchantment.WATER_WORKER);
        enchants.put("thorns".toUpperCase(), Enchantment.THORNS);
        enchants.put("protection_fall".toUpperCase(), Enchantment.PROTECTION_FALL);
        enchants.put("water_fall".toUpperCase(), Enchantment.PROTECTION_FALL);
    }
    public static Enchantment get(String enchant){
        return enchants.get(enchant.toUpperCase());
    }

    public static HashMap<String, Enchantment> getEnchants( ) {
        return enchants;
    }
}
