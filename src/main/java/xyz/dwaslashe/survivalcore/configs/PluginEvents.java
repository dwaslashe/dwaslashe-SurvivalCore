package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.objects.Case;
import xyz.dwaslashe.survivalcore.utils.ItemApi;

import java.util.Arrays;
import java.util.List;
@Getter @Setter
@Header("#")
@Header("#By dwaslashe")
@Header("#Contact discord dwaslashe v2#5620")
@Header("#")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginEvents extends OkaeriConfig {

    private ListCases listCases = new ListCases();
    private ListItemMeteor listItemMeteor = new ListItemMeteor();

    //List Cases
    @Getter
    @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class ListCases extends OkaeriConfig {

        public List<String> caseRandomList = Arrays.asList("zwykla", "jedzenie");

        @Comment("Add your own cases:")
        public List<Case> caseList = Arrays.asList(new Case("common").entry(aCase -> {
            aCase.addItem(new ItemStack(Material.DIAMOND), 30);
            aCase.addItem(new xyz.dwaslashe.survivalcore.helpers.ItemHelper(Material.DIAMOND_PICKAXE)
                    .withMeta(itemHelper -> {
                        itemHelper.setDisplayName("&cSuper Kilof");
                        itemHelper.setLore(Arrays.asList("1", "2"));
                        //itemHelper.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
                    }), 1);
            aCase.addItem(new xyz.dwaslashe.survivalcore.helpers.ItemHelper(Material.BOOK)
                    .withMeta(itemHelper -> {
                        itemHelper.setDisplayName("adasd");
                        //itemHelper.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }), 5);
        }));

    }

    //List Cases
    @Getter
    @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class ListItemMeteor extends OkaeriConfig {

        @Comment("Add your own items:")
        public List<ItemStack> itemStackList = Arrays.asList(new ItemApi(Material.DIAMOND).setName("FAJNANAZWA").getItemStack(), new ItemApi(Material.PAPER).getItemStack());

    }
}
