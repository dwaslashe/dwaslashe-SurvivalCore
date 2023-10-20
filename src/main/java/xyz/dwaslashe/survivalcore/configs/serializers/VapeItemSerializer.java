package xyz.dwaslashe.survivalcore.configs.serializers;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import xyz.dwaslashe.survivalcore.objects.VapeItem;

public class VapeItemSerializer implements ObjectSerializer<VapeItem> {
    @Override
    public boolean supports(Class<? super VapeItem> type) {
        return VapeItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(VapeItem object, SerializationData data) {
        data.add("vape.name", object.getItem_name());
        data.add("vape.material", object.getItem_material());
        data.add("vape.lore", object.getItem_lore());
        data.add("vape.duration", object.getDuration());
        data.add("vape.durability", object.getDurability());
        data.add("vape.power", object.getPower());
    }

    @Override
    public VapeItem deserialize(DeserializationData data, GenericsDeclaration generics) {
        VapeItem vapeItem = new VapeItem();
        vapeItem.setItem_name(data.get("vape.name", String.class));
        vapeItem.setItem_material(data.get("vape.material", Material.class));
        vapeItem.setItem_lore(data.getAsList("vape.lore", String.class));
        vapeItem.setDuration(data.get("vape.duration", Integer.class));
        vapeItem.setDurability(data.get("vape.durability", Integer.class));
        vapeItem.setPower(data.get("vape.power", Integer.class));
        return vapeItem;
    }
}
