package xyz.dwaslashe.survivalcore.helpers;

import java.util.Map;
import java.util.regex.Pattern;

public class IconHelper {

    private static final Pattern ICON_PATTERN = Pattern.compile("icon%[0-9]{2}");

    public static String transformIcons(String text, Map<String, Character> iconCharacters){
        var ref = new Object(){
            String transformedText = text;
        };

        ICON_PATTERN.matcher(text).results().forEach(matchResult -> {
            String id = matchResult.group().replace("icon%", "");
            if(!iconCharacters.containsKey(id)) return;
            ref.transformedText = ref.transformedText.replace(matchResult.group(), String.valueOf(iconCharacters.get(id)));
        });
        return ref.transformedText;
    }

}
