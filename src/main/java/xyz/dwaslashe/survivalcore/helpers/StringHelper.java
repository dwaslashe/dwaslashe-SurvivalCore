package xyz.dwaslashe.survivalcore.helpers;

import java.util.Map;

public class StringHelper {

    public static <K, V> String join(BiReplaceHelper<K, V> biReplaceHelper, CharSequence delimiter, Map<K, V> objectMap){
        StringBuilder builder = new StringBuilder();
        objectMap.forEach((key, value) -> {
            if(!builder.isEmpty()) builder.append(delimiter);
            builder.append(biReplaceHelper.accept(key, value).toString());
        });
        return builder.toString();
    }

}
