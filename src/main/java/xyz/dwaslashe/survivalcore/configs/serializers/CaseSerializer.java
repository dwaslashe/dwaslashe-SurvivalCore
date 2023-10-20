package xyz.dwaslashe.survivalcore.configs.serializers;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import xyz.dwaslashe.survivalcore.objects.Case;
import xyz.dwaslashe.survivalcore.objects.CaseItem;

public class CaseSerializer implements ObjectSerializer<Case> {
    @Override
    public boolean supports(Class<? super Case> type) {
        return Case.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Case object, SerializationData data) {
        data.add("id", object.getId());
        data.add("items", object.getCaseItems());
        data.add("spawn", object.isSpawn());
    }

    @Override
    public Case deserialize(DeserializationData data, GenericsDeclaration generics) {
        return new Case(data.get("id", String.class)).entry(aCase -> {
            aCase.setCaseItems(data.getAsList("items", CaseItem.class));
            aCase.setSpawn(data.get("spawn", boolean.class));
        });
    }
}
