package xyz.dwaslashe.survivalcore.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.*;
import java.util.regex.Pattern;

@Getter @Setter
@Header("#")
@Header("#By dwaslashe")
@Header("#Contact discord dwaslashe v2#5620")
@Header("#")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    private Core core = new Core();
    private Database database = new Database();
    private Messages messages = new Messages();
    private Cooldown cooldown = new Cooldown();
    private Chat chat = new Chat();
    private Events events = new Events();
    private Recipes recipes = new Recipes();
    private Commands commands = new Commands();
    private Join join = new Join();
    private Auto auto = new Auto();
    private Antylogout antylogout = new Antylogout();
    private Webhook webhook = new Webhook();

    public static Map<String, Character> IMAGES_CHAT = new HashMap<>(){{
        put("01", '\uE000');
        put("02", '\uE001');
        put("03", '\uE002');
        put("04", '\uE003');
        put("05", '\uE004');
        put("06", '\uE005');
        put("07", '\uE006');
    }};

    //Discord WebHook
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Webhook extends OkaeriConfig {

        private String webhook_chat = "https://discord.com/api/webhooks/1073397959481372716/iRDO2TDr0s5hsJYwQevBU1JK1sfpMOZiJzv_X0ov-R6i5lzpgf52H1VhLQ1HGNxp1vlc";
        private boolean enable_chat = true;

    }

    //Core
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Core extends OkaeriConfig {

        @Comment("Your license:")
        private String license = "VZO0-FJLH-3DQQ-NL3A";

    }

    //Database
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Database extends OkaeriConfig {

        @Comment("MySQL")
        private String host = "1.1.1.1.1.";
        private String table = "survival3_survivalcore";
        private String username = "admin2";
        private String password = "XD";
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
        private String server = "wywrotkamc.pl";
        private String data = "18.02.2022";
    }

    //Events
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Events extends OkaeriConfig {

        private boolean bossbarinfospawn = true;
        private boolean lavagrieffing = true;
        private boolean tabcomplete = true;
        private boolean unknowncommand = true;
        private boolean enderpearlcooldown = true;
        private boolean kelpsmoke = true;
        private boolean nobedexplose = true;
        private boolean signcolor = true;
        private boolean blockwords = true;
        private boolean blockregex = true;
        private boolean antyafk = true;
        private boolean antyxraymessage = true;
        private boolean joinbossbarflesh = true;
        private boolean joinactionbar = true;
        private boolean deathplayerhead = true;
        private boolean deathmessage = true;
        @Comment("#Teleport player to spawn if no set respawn")
        private boolean norespawnteleporttospawn = true;
        @Comment("#Placed blocks turn to air after 50 seconds in the world of Nether and End")
        private boolean blockplacesetair = true;
        private boolean bossbarmsg = false;
        private boolean bossbarunknowncommand = false;
        private boolean samemessagesend = true;
        private boolean cooldownchat = true;
    }

    //Events
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Recipes extends OkaeriConfig {

        private boolean weed = true;
        private boolean kokaina = true;
        private boolean magnet = true;
        private boolean enchanted_apple = false;

        private boolean diamond_set = false;
    }


    //Commands
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Commands extends OkaeriConfig {

        private boolean physics = true;
        private boolean broadcast = true;
        private boolean enchant = true;
        private boolean texturpack = false;
        private boolean abovename = true;
        private boolean teleport = true;
        private boolean chat = true;
        private boolean clear = true;
        private boolean day = true;
        private boolean discord = true;
        private boolean enderchest = true;
        private boolean feed = true;
        private boolean fly = true;
        private boolean gamma = true;
        private boolean gamemode = true;
        private boolean hat = true;
        private boolean heal = true;
        private boolean help = true;
        private boolean helper = true;
        private boolean invsee = true;
        private boolean more = true;
        private boolean item = true;
        private boolean trash = true;
        private boolean list = true;
        private boolean me = true;
        private boolean punishment = true;
        private boolean socialmedia = true;
        private boolean abyss = true;
        private boolean msg = true;
        private boolean socialspy = true;
        private boolean reply = true;
        private boolean purchase = true;
        private boolean repair = true;
        private boolean rules = true;
        private boolean sidebar = true;
        private boolean spawn = true;
        private boolean ignore = true;
        private boolean unignore = true;
        private boolean tnt = true;
        private boolean teleportplayer = true;
        private boolean teleporthere = true;
        private boolean uptime = true;
        private boolean wb = true;
        private boolean website = true;
        private boolean vanish = true;
        private boolean rank = true;
        private boolean yt = true;
        private boolean night = true;
        private boolean sun = true;
        private boolean storm = true;
        private boolean glowing = true;
        private boolean incognito = true;
        private boolean nickcolor = true;
        private boolean warp = true;
        private boolean chatmanager = true;
        private boolean itemgive = true;
        private boolean top = true;
        private boolean reward = true;
        private boolean ping = true;
        private boolean godmod = true;
        private boolean marketaliases = false;
        private boolean magnet = false;
        private boolean check = true;
        private boolean lifestealaliases = false;
        private boolean pracealiases = false;
    }

    //Auto Tasks
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Auto extends OkaeriConfig {

        private BossBarAuto bossbar = new BossBarAuto();

        @Getter @Setter
        public static class BossBarAuto extends OkaeriConfig {

            private int time = 1;
            private List<String> messages = Arrays.asList("Zapraszająć znajomych na serwer wspierasz nas! &c❤ color:green");

        }

        private MessagesAuto messages = new MessagesAuto();

        @Getter @Setter
        public static class MessagesAuto extends OkaeriConfig {

            private int time = 1;
            private List<String> messages = Arrays.asList("&a✉ &8>> &7Sprawdź nasz discord &bhttps://discord.gg/6eacnBS");

        }
    }

    //Join
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Join extends OkaeriConfig {

        @Comment("#Join message, permission core.join.vip")
        private String vipbroadcast = "{PREFIX}&8>> &aGracz &e{PLAYER} &adołączył na serwer! &bDziękujemy za wsparcie!";
        private String message = " \n  &#39ff14&lWywrotkaMC.PL &8- &#39ff14&lSURVIVAL + EKONOMIA \n ";
        @Comment("#Join BossBar Flesh message")
        private String bossbarfleshmessage = "WIELKA ŚWIĄTECZNA AKTULIZACJA SERWERA!";
        private String bossbarfleshcolor1 = "&b&l";
        private String bossbarfleshcolor2 = "&f&l";

    }

    //Chat
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Chat extends OkaeriConfig {

        @Comment("Chat")
        private String format = "{PREFIX}&7{PLAYER} &8>>&f <message>";
        private String on = "&a&lCZAT ZOSTAŁ WŁĄCZONY";
        private String off = "&c&LCZAT ZOSTAŁ WYŁĄCZONY";
        private String clear = "&b&lCZAT ZOSTAŁ WYCZYSZCZONY";

        private List<String> blocktabcommands = Arrays.asList("/br",
                "/brush",
                "/desel",
                "/deselect",
                "/sel",
                "/toggleplace",
                ";",
                "?",
                "alonsotags",
                "artp",
                "aspawner",
                "axerrnicknamer",
                "beastwithdraw",
                "xpbottle",
                "bwithdraw",
                "bellyflop",
                "bpl",
                "bplugins",
                "br",
                "brush",
                "bserverutils",
                "bsu",
                "cc",
                "ccreate",
                "cmil",
                "cmilib",
                "command",
                "insaneshops",
                "core",
                "crate",
                "crates",
                "crazycrate",
                "crazycrates",
                "gbellyflop",
                "gcrawl",
                "glay",
                "gsit",
                "gspin",
                "hd",
                "hdv",
                "holo",
                "hologram",
                "holograms",
                "holographicdisplays",
                "icanhasbukkit",
                "jday",
                "key",
                "keys",
                "lbans",
                "listwarn",
                "listwarnings",
                "litebans",
                "lbans",
                "warninglist",
                "warnlist",
                "lwarning",
                "mineeconomy",
                "minemarriages",
                "minephysics",
                "mineplots",
                "minerandomtp",
                "none",
                "papi",
                "placeholderapi",
                "playerkits",
                "reg",
                "regions",
                "region",
                "rg",
                "shopgui",
                "shopguiplus",
                "viaver",
                "viaversion",
                "vulcan",
                "vvbukkit",
                "worldedit",
                "zauction",
                "about");

        private BlockWordsChat blockwords = new BlockWordsChat();

        @Getter @Setter
        public static class BlockWordsChat extends OkaeriConfig {

            private List<String> words = Arrays.asList("kutas", "kurwa", "chuj");
            private String command = "mute {PLAYER} 15m Słowa";

        }

        private BlockRegexChat blockRegexChat = new BlockRegexChat();

        @Getter @Setter
        public static class BlockRegexChat extends OkaeriConfig {

            private String command = "mute {PLAYER} 30m Reklama";

        }

    }

    //Cooldown
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Cooldown extends OkaeriConfig {

        @Comment("Cooldown")
        private String message = " &8>> &cNastepna wiadomosc mozesz wyslac za &e{TIME}";
        private String time = "3s";

    }

    //Antylogout
    @Getter @Setter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class Antylogout extends OkaeriConfig {

        @Comment("Block commands")
        private List<String> commands = Arrays.asList("/spawn");
        @Comment("Block regions")
        private List<String> regions = Arrays.asList("end", "spawn", "nether");

    }
}
