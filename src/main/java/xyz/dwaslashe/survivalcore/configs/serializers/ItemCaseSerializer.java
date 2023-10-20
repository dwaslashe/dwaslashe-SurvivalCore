package xyz.dwaslashe.survivalcore.configs.serializers;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.objects.CaseItem;

public class ItemCaseSerializer implements ObjectSerializer<CaseItem> {
    @Override
    public boolean supports(Class<? super CaseItem> type) {
        return CaseItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(CaseItem object, SerializationData data) {
        data.add("item", object.getItemStack());
        data.add("chance", object.getChance());
    }

    @Override
    public CaseItem deserialize(DeserializationData data, GenericsDeclaration generics) {
        return new CaseItem(data.get("item", ItemStack.class), data.get("chance", double.class));
    }
}
