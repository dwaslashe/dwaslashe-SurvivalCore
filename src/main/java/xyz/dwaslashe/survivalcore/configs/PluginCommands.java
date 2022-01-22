package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
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

    //Auto Tasks

    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Commands extends OkaeriConfig {


        private HelpCommand help = new HelpCommand();

        @Getter @Setter
        public static class HelpCommand extends OkaeriConfig {

            private List<String> help = List.of(" &8[ &a&LSURVIVAL - LISTA KOMEND &8]", "&2* &a/kosz &8- &fotwiera kosz", "&2* &a/list &8- &fwysyła liste graczy", "&2* &a/money &8- &fwysyła twoje saldo", "&2* &a/ah &8- &fotwiera aukcje");

        }

        private YtCommand yt = new YtCommand();

        @Getter @Setter
        public static class YtCommand extends OkaeriConfig {

            private List<String> yt = List.of(" &8[ &a&LSURVIVAL - LISTA KOMEND &8]", "&2* &a/kosz &8- &fotwiera kosz", "&2* &a/list &8- &fwysyła liste graczy", "&2* &a/money &8- &fwysyła twoje saldo", "&2* &a/ah &8- &fotwiera aukcje");

        }
    }
}
