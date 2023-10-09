package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import xyz.dwaslashe.survivalcore.configs.serializers.AbovenameShopSerializer;

public class AbovenameShopSerdesPack implements OkaeriSerdesPack {
    @Override
    public void register(SerdesRegistry registry) {
        registry.register(new AbovenameShopSerializer());
    }
}
