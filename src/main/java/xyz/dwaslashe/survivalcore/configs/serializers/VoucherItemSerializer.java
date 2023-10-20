package xyz.dwaslashe.survivalcore.configs.serializers;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import xyz.dwaslashe.survivalcore.objects.VoucherItem;

public class VoucherItemSerializer implements ObjectSerializer<VoucherItem> {
    @Override
    public boolean supports(Class<? super VoucherItem> type) {
        return VoucherItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(VoucherItem object, SerializationData data) {
        data.add("icon.name", object.getItem_name());
        data.add("icon.material", object.getItem_material());
        data.add("icon.lore", object.getItem_lore());
        data.add("icon.head.texture", object.getItem_head_texture());
        data.add("icon.head.name", object.getItem_head_name());
        data.add("command", object.getCommandLine());
        data.add("owner", object.getOwner());
    }

    @Override
    public VoucherItem deserialize(DeserializationData data, GenericsDeclaration generics) {
        VoucherItem voucherItem = new VoucherItem();
        voucherItem.setItem_name(data.get("icon.name", String.class));
        voucherItem.setItem_material(data.get("icon.material", Material.class));
        voucherItem.setItem_lore(data.getAsList("icon.lore", String.class));
        voucherItem.setItem_head_texture(data.get("icon.head.texture", String.class));
        voucherItem.setItem_head_name(data.get("icon.head.name", String.class));
        voucherItem.setCommandLine(data.getAsList("command", String.class));
        voucherItem.setOwner(data.get("owner", Boolean.class));
        return voucherItem;
    }
}