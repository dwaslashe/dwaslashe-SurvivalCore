package xyz.dwaslashe.survivalcore.configs.serializers;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import xyz.dwaslashe.survivalcore.objects.AbovenameShop;

public class AbovenameShopSerializer implements ObjectSerializer<AbovenameShop> {
    @Override
    public boolean supports(Class<? super AbovenameShop> type) {
        return AbovenameShop.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(AbovenameShop object, SerializationData data) {
        data.add("icon.name", object.getGui_item_name());
        data.add("icon.lore", object.getGui_item_lore());
        data.add("icon.head", object.getGui_item_head_texture());
        data.add("command", object.getCommandLine());
        data.add("cost", object.getCost());
        data.add("permission", object.getPermission());
    }

    @Override
    public AbovenameShop deserialize(DeserializationData data, GenericsDeclaration generics) {
        AbovenameShop itemShop = new AbovenameShop();
        itemShop.setGui_item_name(data.get("icon.name", String.class));
        itemShop.setGui_item_lore(data.getAsList("icon.lore", String.class));
        itemShop.setGui_item_head_texture(data.get("icon.head", String.class));
        itemShop.setCommandLine(data.getAsList("command", String.class));
        itemShop.setCost(data.get("cost", double.class));
        itemShop.setPermission(data.get("permission", String.class));
        return itemShop;
    }
}
