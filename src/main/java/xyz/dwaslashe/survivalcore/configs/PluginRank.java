package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Header("#")
@Header("#By dwaslashe")
@Header("#Contact discord dwaslashe v2#5620")
@Header("#")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginRank extends OkaeriConfig {

    private Vip vip = new Vip();

    private Svip svip = new Svip();

    private Mvp mvp = new Mvp();

    private Mvpplus mvpplus = new Mvpplus();

    private Sponsor sponsor = new Sponsor();

    @Getter
    @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Vip extends OkaeriConfig {

        private boolean enable = true;

        @Comment("VIP: Chestplate")
        private String chestplate_name = "&#21F8F6&lVIP &7{PLAYER}";
        private List<String> chestplate_lore = Arrays.asList(
                " ",
                " &7Cena usługi&8: &b20 zł",
                " &7Czas trwania &dCała edycja!",
                "",
                "&6&l ⭐ &#fcb419Komendy rangi VIP:",
                "",
                " &8>> &f/kit vip &8- &7Zestaw przedmiotów",
                " &8>> &f/sklep vip &8- &7Sklep premium",
                " &8>> &f/hat &8- &7Załóż kapelusz na głowe",
                " &8>> &f/feed &8- &7Zregeneruj swój głód",
                " &8>> &f/wb &8- &7Przenośny crafting",
                " &8>> &f/sun &8- &7Słoneczna pogoda",
                " &8>> &f/storm &8- &7Deszczowa pogoda",
                " &8>> &f/plot close &8- &7Zamyka działke",
                " &8>> &f/plot open &8- &7Otwiera działke",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("VIP: Leggings")
        private String leggings_name = "&#21F8F6&lVIP &7{PLAYER}";
        private List<String> leggings_lore = Arrays.asList(
                "",
                "&6&l ⭐ &#fcb419Przywileje rangi VIP:",
                "",
                " &8>> &7Możliwość wejścia na &#fcb419pełen &7serwer",
                " &8>> &7Limit aukcji zwiększony do &#fcb4195",
                " &8>> &7Limit &#fcb4192 &7domów do stworzenia",
                " &8>> &7Dostęp do &bstrefy VIP",
                " &8>> &7Otrzymuje rangę&b VIP&7 na Discordzie",
                " &8>> &7Może pisać &bk&#fcb419o&dl&2o&6r&#fc2419o&8w&3o&7 na tabliczkach",
                " &8>> &7Posiada większy &dEnderChest&7, 36 slotów",
                " &8>> &7Limit postawionych działek jest zwiększony do &#fcb4192",
                " &8>> &7Może działke mieć do &#fcb41930&7 kratek",
                " &8>> &7Może do działki dodać &#fcb41910&7 osób",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("VIP: Boots")
        private String boots_name = "&#4cf739Kliknij po link!";

        @Comment("VIP: Sword")
        private String sword_name = "&#fcb419Zestaw rangi VIP";
        private List<String> sword_lore = Arrays.asList("", " &#4cf739&nKliknij aby zobaczyć zestaw rangi!");
        private String sword_command = "/kit preview vip";
    }

    @Getter
    @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Svip extends OkaeriConfig {

        private boolean enable = true;

        @Comment("SVIP: Chestplate")
        private String chestplate_name = "&#FFC42E&lS&#FFF01F&lVIP &7{PLAYER}";
        private List<String> chestplate_lore = Arrays.asList(
                "",
                " &7Cena usługi&8: &b30 zł",
                " &7Czas trwania &dCała edycja!",
                "",
                "&6&l ⭐ &#fcb419Komendy rangi SVIP:",
                "",
                " &8>> &f/kit svip &8- &7Zestaw przedmiotów",
                " &8>> &f/kit vip &8- &7Zestaw przedmiotów",
                " &8>> &f/sklep vip &8- &7Sklep premium",
                " &8>> &f/hat &8- &7Załóż kapelusz na głowe",
                " &8>> &f/feed &8- &7Zregeneruj swój głód",
                " &8>> &f/wb &8- &7Przenośny crafting",
                " &8>> &f/sun &8- &7Słoneczna pogoda",
                " &8>> &f/storm &8- &7Deszczowa pogoda",
                " &8>> &f/ec &8- &7Otwiera EnderChest",
                " &8>> &f/repair &8- &7Naprawia narzędzie w ręce",
                " &8>> &f/plot close &8- &7Zamyka działke",
                " &8>> &f/plot open &8- &7Otwiera działke",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("SVIP: Leggings")
        private String leggings_name = "&#FFC42E&lS&#FFF01F&lVIP &7{PLAYER}";
        private List<String> leggings_lore = Arrays.asList(
                "",
                "&6&l ⭐ &#fcb419Przywileje rangi SVIP:",
                "",
                " &8>> &7Możliwość wejścia na &#fcb419pełen &7serwer",
                " &8>> &7Limit aukcji zwiększony do &#fcb41910",
                " &8>> &7Limit &#fcb4193 &7domów do stworzenia",
                " &8>> &7Dostęp do &bstrefy VIP",
                " &8>> &7Otrzymuje rangę&6 S&#fcb419VIP&7 na Discordzie",
                " &8>> &7Może pisać &bk&#fcb419o&dl&2o&6r&#fc2419o&8w&3o&7 na tabliczkach",
                " &8>> &7Posiada większy &dEnderChest&7, 45 slotów",
                " &8>> &7Pisanie na &bk&#fcb419o&dl&2o&6r&#fc2419o&8w&3o",
                " &8>> &7Może pisać na czacie bez opóźnienia",
                " &8>> &7Limit postawionych działek jest zwiększony do &#fcb4193",
                " &8>> &7Może działke mieć do &#fcb41940&7 kratek",
                " &8>> &7Może do działki dodać &#fcb41915&7 osób",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("SVIP: Boots")
        private String boots_name = "&#4cf739Kliknij po link!";

        @Comment("SVIP: Sword")
        private String sword_name = "&#fcb419Zestaw rangi SVIP";
        private List<String> sword_lore = Arrays.asList("", " &#4cf739&nKliknij aby zobaczyć zestaw rangi!");
        private String sword_command = "/kit preview svip";
    }

    @Getter
    @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Mvp extends OkaeriConfig {

        private boolean enable = true;

        @Comment("MVP: Chestplate")
        private String chestplate_name = "&#FFF01F&lMVP &7{PLAYER}";
        private List<String> chestplate_lore = Arrays.asList(
                "",
                " &7Cena usługi&8: &b40 zł",
                " &7Czas trwania &dCała edycja!",
                "",
                "&6&l ⭐ &#fcb419Komendy rangi MVP:",
                "",
                " &8>> &f/kit mvp &8- &7Zestaw przedmiotów",
                " &8>> &f/kit svip &8- &7Zestaw przedmiotów",
                " &8>> &f/kit vip &8- &7Zestaw przedmiotów",
                " &8>> &f/sklep vip &8- &7Sklep premium",
                " &8>> &f/hat &8- &7Załóż kapelusz na głowe",
                " &8>> &f/feed &8- &7Zregeneruj swój głód",
                " &8>> &f/wb &8- &7Przenośny crafting",
                " &8>> &f/sun &8- &7Słoneczna pogoda",
                " &8>> &f/storm &8- &7Deszczowa pogoda",
                " &8>> &f/ec &8- &7Otwiera EnderChest",
                " &8>> &f/repair &8- &7Naprawia narzędzie w ręce",
                " &8>> &f/glowing &8- &7Błyszczenie",
                " &8>> &f/plot close &8- &7Zamyka działke",
                " &8>> &f/plot open &8- &7Otwiera działke",
                " &8>> &f/kolornick &8- &7Otwiera menu wyboru zmiany koloru nicku",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("MVP: Leggings")
        private String leggings_name = "&#FFF01F&lMVP &7{PLAYER}";
        private List<String> leggings_lore = Arrays.asList(
                "",
                "&6&l ⭐ &#fcb419Przywileje rangi MVP:",
                "",
                " &8>> &7Możliwość wejścia na &#fcb419pełen &7serwer",
                " &8>> &7Limit aukcji zwiększony do &#fcb41915",
                " &8>> &7Limit &#fcb4195 &7domów do stworzenia",
                " &8>> &7Dostęp do &bstrefy VIP",
                " &8>> &7Otrzymuje rangę&6 &bMVP&7 na Discordzie",
                " &8>> &7Może pisać &bk&#fcb419o&dl&2o&6r&#fc2419o&8w&3o&7 na tabliczkach",
                " &8>> &7Posiada większy &dEnderChest&7, 54 slotów",
                " &8>> &7Pisanie na &bk&#fcb419o&dl&2o&6r&#fc2419o&8w&3o",
                " &8>> &7Może pisać na czacie bez opóźnienia",
                " &8>> &7Limit postawionych działek jest zwiększony do &#fcb4194",
                " &8>> &7Może działke mieć do &#fcb41950&7 kratek",
                " &8>> &7Może zmieniać kolor swojego nicku",
                " &8>> &7Może do działki dodać &#fcb41920&7 osób",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("MVP: Boots")
        private String boots_name = "&#4cf739Kliknij po link!";

        @Comment("MVP: Sword")
        private String sword_name = "&#fcb419Zestaw rangi MVP";
        private List<String> sword_lore = Arrays.asList("", " &#4cf739&nKliknij aby zobaczyć zestaw rangi!");
        private String sword_command = "/kit preview mvp";
    }

    @Getter
    @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Mvpplus extends OkaeriConfig {

        private boolean enable = true;

        @Comment("MVP+: Chestplate")
        private String chestplate_name = "&#B026FF&lMVP+ &7{PLAYER}";
        private List<String> chestplate_lore = Arrays.asList(
                "",
                " &7Cena usługi&8: &b100 zł",
                " &7Czas trwania &dNa Zawsze!",
                "",
                "&6&l ⭐ &#fcb419Komendy rangi MVP+:",
                "",
                " &8>> &f/kit mvp+ &8- &7Zestaw przedmiotów",
                " &8>> &f/kit mvp &8- &7Zestaw przedmiotów",
                " &8>> &f/kit svip &8- &7Zestaw przedmiotów",
                " &8>> &f/kit vip &8- &7Zestaw przedmiotów",
                " &8>> &f/sklep vip &8- &7Sklep premium",
                " &8>> &f/hat &8- &7Załóż kapelusz na głowe",
                " &8>> &f/feed &8- &7Zregeneruj swój głód",
                " &8>> &f/wb &8- &7Przenośny crafting",
                " &8>> &f/sun &8- &7Słoneczna pogoda",
                " &8>> &f/storm &8- &7Deszczowa pogoda",
                " &8>> &f/ec &8- &7Otwiera EnderChest",
                " &8>> &f/repair &8- &7Naprawia narzędzie w ręce",
                " &8>> &f/repair all &8- &7Naprawia wszystkie narzędzie w equ",
                " &8>> &f/item &8- &7Pozwala zmienić nazwe i lore itemu",
                " &8>> &f/glowing &8- &7Błyszczenie",
                " &8>> &f/heal &8- &7Ulecza",
                " &8>> &f/gamma &8- &7Włącza widzenie w ciemności",
                " &8>> &f/plot close &8- &7Zamyka działke",
                " &8>> &f/plot open &8- &7Otwiera działke",
                " &8>> &f/kolornick &8- &7Otwiera menu wyboru zmiany koloru nicku",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("MVP+: Leggings")
        private String leggings_name = "&#B026FF&lMVP+ &7{PLAYER}";
        private List<String> leggings_lore = Arrays.asList(
                "",
                "&6&l ⭐ &#fcb419Przywileje rangi MVP+:",
                "",
                " &8>> &7Możliwość wejścia na &#fcb419pełen &7serwer",
                " &8>> &7Limit aukcji zwiększony do &#fcb41920",
                " &8>> &7Limit &#fcb41912 &7domów do stworzenia",
                " &8>> &7Dostęp do &bstrefy VIP",
                " &8>> &7Otrzymuje rangę&6 &dMVP+&7 na Discordzie",
                " &8>> &7Może pisać &bk&#fcb419o&dl&2o&6r&#fc2419o&8w&3o&7 na tabliczkach",
                " &8>> &7Posiada większy &dEnderChest&7, 54 slotów",
                " &8>> &7Pisanie na &bk&#fcb419o&dl&2o&6r&#fc2419o&8w&3o",
                " &8>> &7Może pisać na czacie bez opóźnienia",
                " &8>> &7Limit postawionych działek jest zwiększony do &#fcb4195",
                " &8>> &7Może działke mieć do &#fcb41960&7 kratek",
                " &8>> &7Może do działki dodać &#fcb41922&7 osób",
                " &8>> &7Może zmieniać kolor/gradient swojego nicku",
                " &8>> &#FBFD8C&nMoże latać na swojej działce!",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("MVP+: Boots")
        private String boots_name = "&#4cf739Kliknij po link!";

        @Comment("MVP+: Sword")
        private String sword_name = "&#fcb419Zestaw rangi MVP+";
        private List<String> sword_lore = Arrays.asList("", " &#4cf739&nKliknij aby zobaczyć zestaw rangi!");
        private String sword_command = "/kit preview mvpplus";
    }

    @Getter
    @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Sponsor extends OkaeriConfig {

        private boolean enable = true;

        @Comment("SPONSOR: Chestplate")
        private String chestplate_name = "#FF3131&lS#FF5F1F&lP#FFF01F&lO#39FF14&lN#008443&LS#21F8F6&lO#1F51FF&lR &7{PLAYER}";
        private List<String> chestplate_lore = Arrays.asList(
                "",
                " &7Cena usługi&8: &b200 zł",
                " &7Czas trwania &dNa Zawsze!",
                "",
                "&6&l ⭐ &#fcb419Komendy rangi SPONSOR:",
                "",
                " &8>> &f/kit mvp+ &8- &7Zestaw przedmiotów",
                " &8>> &f/kit mvp &8- &7Zestaw przedmiotów",
                " &8>> &f/kit svip &8- &7Zestaw przedmiotów",
                " &8>> &f/kit vip &8- &7Zestaw przedmiotów",
                " &8>> &f/sklep vip &8- &7Sklep premium",
                " &8>> &f/hat &8- &7Załóż kapelusz na głowe",
                " &8>> &f/feed &8- &7Zregeneruj swój głód",
                " &8>> &f/wb &8- &7Przenośny crafting",
                " &8>> &f/sun &8- &7Słoneczna pogoda",
                " &8>> &f/storm &8- &7Deszczowa pogoda",
                " &8>> &f/ec &8- &7Otwiera EnderChest",
                " &8>> &f/repair &8- &7Naprawia narzędzie w ręce",
                " &8>> &f/repair all &8- &7Naprawia wszystkie narzędzie w equ",
                " &8>> &f/item &8- &7Pozwala zmienić nazwe i lore itemu",
                " &8>> &f/glowing &8- &7Błyszczenie",
                " &8>> &f/heal &8- &7Ulecza",
                " &8>> &f/gamma &8- &7Włącza widzenie w ciemności",
                " &8>> &f/plot close &8- &7Zamyka działke",
                " &8>> &f/plot open &8- &7Otwiera działke",
                " &8>> &f/kolornick &8- &7Otwiera menu wyboru zmiany koloru nicku",
                " &8>> &f/incognito &8- &7Zostań incognito na serwerze!",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("SPONSOR: Leggings")
        private String leggings_name = "#FF3131&lS#FF5F1F&lP#FFF01F&lO#39FF14&lN#008443&LS#21F8F6&lO#1F51FF&lR &7{PLAYER}";
        private List<String> leggings_lore = Arrays.asList(
                "",
                "&6&l ⭐ &#fcb419Przywileje rangi SPONSOR",
                "",
                " &8>> &7Możliwość wejścia na &#fcb419pełen &7serwer",
                " &8>> &7Limit aukcji zwiększony do &#fcb419100",
                " &8>> &7Limit &#fcb41914 &7domów do stworzenia",
                " &8>> &7Dostęp do &bstrefy VIP i specjalnej sterfy SPONSOR",
                " &8>> &7Otrzymuje rangę&6 #FF3131S#FF5F1FP#FFF01FO#39FF14N#008443S#21F8F6O#1F51FFR&7 na Discordzie",
                " &8>> &7Może pisać &bk&#fcb419o&dl&2o&6r&#fc2419o&8w&3o&7 na tabliczkach",
                " &8>> &7Posiada większy &dEnderChest&7, 54 slotów",
                " &8>> &7Pisanie na &bk&#fcb419o&dl&2o&6r&#fc2419o&8w&3o",
                " &8>> &7Może pisać na czacie bez opóźnienia",
                " &8>> &7Limit postawionych działek jest zwiększony do &#fcb41914",
                " &8>> &7Może działke mieć do &#fcb41960&7 kratek",
                " &8>> &7Może do działki dodać &#fcb41922&7 osób",
                " &8>> &7Może zmieniać kolor/gradient swojego nicku",
                " &8>> &#FBFD8C&nMoże latać na swojej działce!",
                " &8>> &#FBFD8C&nOdblokowuje wszystkie tytuły!",
                " &8>> &#FBFD8C&nDostaje dostęp do nowych systemów serwera wcześniej!",
                " &8>> &#FBFD8C&nOdblokowuje wszystkie tagi!",
                " &8>> &#FBFD8C&nMa dostęp do systemu incognito!",
                " &8>> &#FBFD8C&nDostaje najszybszy dostęp do administracji!",
                "",
                " &#4cf739&nKliknij prawym żeby podejrzeć link!");

        @Comment("SPONSOR: Boots")
        private String boots_name = "&#4cf739Kliknij po link!";

        @Comment("SPONSOR: Sword")
        private String sword_name = "&#fcb419Zestaw rangi SPONSOR";
        private List<String> sword_lore = Arrays.asList("", " &#4cf739&nKliknij aby zobaczyć zestaw rangi!");
        private String sword_command = "/kit preview sponsor";
    }
}
