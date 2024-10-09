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
public class PluginCommands extends OkaeriConfig {

    private Commands commands = new Commands();

    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Commands extends OkaeriConfig {


        private HelpCommand help = new HelpCommand();

        @Getter @Setter
        public static class HelpCommand extends OkaeriConfig {

            private List<String> help = List.of(" &8[ &#4cf739&LSURVIVAL - LISTA KOMEND &8]", "&2* &#4cf739/kosz &8- &fotwiera kosz", "&2* &#4cf739/list &8- &fwysyła liste graczy", "&2* &#4cf739/money &8- &fwysyła twoje saldo", "&2* &#4cf739/ah &8- &fotwiera aukcje");

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
    }
}
