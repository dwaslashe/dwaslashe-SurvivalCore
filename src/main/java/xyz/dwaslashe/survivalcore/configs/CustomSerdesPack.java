package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import xyz.dwaslashe.survivalcore.configs.serializers.*;

public class CustomSerdesPack implements OkaeriSerdesPack {
    @Override
    public void register(SerdesRegistry registry) {
        registry.register(new AbovenameShopSerializer());
        registry.register(new VoucherItemSerializer());
        registry.register(new VapeItemSerializer());
        registry.register(new CaseSerializer());
        registry.register(new ItemCaseSerializer());
    }
}
