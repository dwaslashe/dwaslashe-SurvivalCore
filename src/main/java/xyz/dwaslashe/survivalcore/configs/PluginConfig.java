package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter @Setter
@Header("#")
@Header("#By dwaslashe")
@Header("#Contact discord dwaslashe v2#5620")
@Header("#")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    private Database database = new Database();
    private Messages messages = new Messages();
    private Cooldown cooldown = new Cooldown();
    private Chat chat = new Chat();
    private Join join = new Join();
    private Auto auto = new Auto();
    private Antylogout antylogout = new Antylogout();
    private Spawn spawn = new Spawn();

    //Cords spawn
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Spawn extends OkaeriConfig {

        @Comment("Cords spawn")
        private int x = 45;
        private int y = 88;
        private int z = 82;
        private int yaw = 179;
        private int pitch = 3;

    }

    //Database
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Database extends OkaeriConfig {

        @Comment("MySQL")
        private String host = "51.83.191.242";
        private String table = "survival2_survivalcore";
        private String username = "dwaslashe";
        private String password = "]EhBEfmhehWoW/_h";
        private int port = 3306;
        private boolean ssl = true;

    }

    //Messages
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Messages extends OkaeriConfig {

        @Comment("Messages")
        private String ip = " <#39ff14>svCore</#32a852>";
        private String prefix = " &8>> &7";
        private String discord = "dc.wywrotkamc.pl";
        private String website = "www.wywrotkamc.pl";
    }

    //Auto Tasks

    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Auto extends OkaeriConfig {

        private BossBarAuto bossbar = new BossBarAuto();

        @Getter @Setter
        public static class BossBarAuto extends OkaeriConfig {

            private int time = 1;
            private List<String> messages = Arrays.asList("&a✉ &8>> &7Sprawdź nasz discord &bhttps://discord.gg/6eacnBS", "");

        }

        private MessagesAuto messages = new MessagesAuto();

        @Getter @Setter
        public static class MessagesAuto extends OkaeriConfig {

            private int time = 1;
            private List<String> messages = Arrays.asList("color:green Zapraszająć znajomych na serwer wspierasz nas! &c❤", "");

        }
    }

    //Join
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Join extends OkaeriConfig {

        @Comment("Join message, permission core.join.vip")
        private String vipbroadcast = "{PREFIX}&8>> &aGracz &e{PLAYER} &adołączył na serwer! &bDziękujemy za wsparcie!";
        private String message = "";
        @Comment("Join BossBar message")
        private String bossbarmessage = "WIELKA ŚWIĄTECZNA AKTULIZACJA SERWERA!";
        private String bossbarcolor1 = "&b&l";
        private String bossbarcolor2 = "&f&l";

    }

    //Chat
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Chat extends OkaeriConfig {

        @Comment("Chat")
        private String format = "{PREFIX}&7{PLAYER} &8>>&f {MESSAGE}";
        private String on = "&a&lCZAT ZOSTAŁ WŁĄCZONY";
        private String off = "&c&LCZAT ZOSTAŁ WYŁĄCZONY";
        private String clear = "&b&lCZAT ZOSTAŁ WYCZYSZCZONY";

        private BlockWordsChat blockwords = new BlockWordsChat();

        @Getter @Setter
        public static class BlockWordsChat extends OkaeriConfig {

            private List<String> words = Arrays.asList("kutas", "kurwa", "chuj");
            private String command = "mute {PLAYER} 5m Słowa";

        }

    }

    //Cooldown
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Cooldown extends OkaeriConfig {

        @Comment("Cooldown")
        private String message = " &8>> &7Nastepna wiadomosc mozesz wyslac za &f{TIME}";
        private String time = "3s";

    }

    //Antylogout
    //Chat
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Antylogout extends OkaeriConfig {

        @Comment("Block commands")
        private List<String> commands = Arrays.asList("/spawn");
        @Comment("Block regions")
        private List<String> regions = Arrays.asList("end", "spawn", "nether");

    }
}
