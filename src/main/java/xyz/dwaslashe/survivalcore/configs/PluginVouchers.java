package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import xyz.dwaslashe.survivalcore.objects.VoucherItem;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Header("#")
@Header("#By dwaslashe")
@Header("#Contact discord dwaslashe v2#5620")
@Header("#")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginVouchers extends OkaeriConfig {

    private Items items = new Items();

    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Items extends OkaeriConfig {

        private VoucherItems voucherItems = new VoucherItems();

        @Getter @Setter
        public static class VoucherItems extends OkaeriConfig {

            public List<VoucherItem> items = Arrays.asList(new VoucherItem(voucherItem -> {
                voucherItem.setItem_name("&#39FF14Ranga VIP");
                voucherItem.setItem_material(Material.DIAMOND);
                voucherItem.setItem_lore(Arrays.asList(" ", " &6&l⭐ &#FFF01FTekst tytułu:", "", " &#1F51FFOd małego mówią mi, że jestem poj*bany", " &#1F51FFRobię poj*bany kwit, po dwie bańki od reklamy", ""));
                voucherItem.setItem_head_name("%owner%");
                voucherItem.setItem_head_texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                voucherItem.setCommandLine(Arrays.asList("say no ladnie ladnie %owner%"));
                voucherItem.setOwner(true);
            }));
        }
    }
}

