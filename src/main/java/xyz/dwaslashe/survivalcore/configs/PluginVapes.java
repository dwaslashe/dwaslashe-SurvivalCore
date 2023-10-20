package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import xyz.dwaslashe.survivalcore.objects.VapeItem;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Header("#")
@Header("#By dwaslashe")
@Header("#Contact discord dwaslashe v2#5620")
@Header("#")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginVapes extends OkaeriConfig {

    private Items items = new Items();

    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Items extends OkaeriConfig {

        private VapeItems vapeItems = new VapeItems();

        @Getter @Setter
        public static class VapeItems extends OkaeriConfig {

            public List<VapeItem> items = Arrays.asList(new VapeItem(vapeItem -> {
                vapeItem.setItem_name("&#eb4034VOOPOO DRAG 4");
                vapeItem.setItem_material(Material.RED_CANDLE);
                vapeItem.setItem_lore(Arrays.asList(" ", " &#E7E7E7Zakres mocy: &#f7c8455-177W", " &#E7E7E7Ilość użyć: &#9DF89F{durability}", " ", " &#FBFD8C&nKliknij prawym w powietrzu aby zrobić chmure!"));
                vapeItem.setDuration(60);
                vapeItem.setDurability(100);
                vapeItem.setPower(10);
            }));
        }
    }
}
