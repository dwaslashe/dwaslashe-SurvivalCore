package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import xyz.dwaslashe.survivalcore.objects.AbovenameShop;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Header("#")
@Header("#By dwaslashe")
@Header("#Contact discord dwaslashe v2#5620")
@Header("#")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginCommands extends OkaeriConfig {

    private Commands commands = new Commands();

    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Commands extends OkaeriConfig {


        private HelpCommand help = new HelpCommand();

        @Getter @Setter
        public static class HelpCommand extends OkaeriConfig {

            private List<String> help = List.of(" &8[ &a&LSURVIVAL - LISTA KOMEND &8]", "&2* &a/kosz &8- &fotwiera kosz", "&2* &a/list &8- &fwysyła liste graczy", "&2* &a/money &8- &fwysyła twoje saldo", "&2* &a/ah &8- &fotwiera aukcje");

        }

        private FaQCommand faQCommand = new FaQCommand();

        @Getter @Setter
        public static class FaQCommand extends OkaeriConfig {

            private List<String> faq = List.of(" ");
            private List<String> tab = Arrays.asList("Jak zdobyć nasiona narkotyków?", "Gdzie można sprzedać narkotyki?", "Czemu woda nie działa?", "Co to jest FBI?");

        }

        private OnaMiala10LatCommand onaMiala10LatCommand = new OnaMiala10LatCommand();

        @Getter @Setter
        public static class OnaMiala10LatCommand extends OkaeriConfig {

            private List<String> onamiala10lat = List.of(" ");

        }

        private YtCommand yt = new YtCommand();

        @Getter @Setter
        public static class YtCommand extends OkaeriConfig {

            private List<String> yt = List.of(" &8[ &a&LSURVIVAL - LISTA KOMEND &8]", "&2* &a/kosz &8- &fotwiera kosz", "&2* &a/list &8- &fwysyła liste graczy", "&2* &a/money &8- &fwysyła twoje saldo", "&2* &a/ah &8- &fotwiera aukcje");

        }

        private AbovenameShopCommand abovenameShop = new AbovenameShopCommand();

        @Getter @Setter
        public static class AbovenameShopCommand extends OkaeriConfig {

            public List<AbovenameShop> items = Arrays.asList(new AbovenameShop(abovenameShop -> {
                abovenameShop.setGui_item_name("&#39FF14Tytuł 1");
                abovenameShop.setGui_item_lore(Arrays.asList(" ", " &6&l⭐ &#FFF01FTekst tytułu:", "", " &#1F51FFOd małego mówią mi, że jestem poj*bany", " &#1F51FFRobię poj*bany kwit, po dwie bańki od reklamy", ""));
                abovenameShop.setGui_item_head_texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
                abovenameShop.setCommandLine(Arrays.asList("admintitletag %player% &#1F51FFOd małego mówią mi, że jestem poj*bany%newline%&#1F51FFRobię poj*bany kwit, po dwie bańki od reklamy"));
                abovenameShop.setCost(20000.0);
                abovenameShop.setPermission("core.abovenameshop.tag1");
            }));
        }
    }
}
