package xyz.dwaslashe.survivalcore;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dwaslashe.survivalcore.commands.*;
import xyz.dwaslashe.survivalcore.commands.groups.*;
import xyz.dwaslashe.survivalcore.commands.managers.CommandManager;
import xyz.dwaslashe.survivalcore.configs.PluginCommands;
import xyz.dwaslashe.survivalcore.configs.PluginConfig;
import xyz.dwaslashe.survivalcore.database.DatabaseConnector;
import xyz.dwaslashe.survivalcore.enums.CustomHand;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.listeners.*;
import xyz.dwaslashe.survivalcore.tasks.AbyssTask;
import xyz.dwaslashe.survivalcore.tasks.AutoBossBarTask;
import xyz.dwaslashe.survivalcore.tasks.AutoMessageTask;
import xyz.dwaslashe.survivalcore.tasks.PlayerTask;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.CustomItemApi;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter @Setter
public class Main extends JavaPlugin {
    protected static final List<CustomItemApi> items = Arrays.asList();
    public static String ip = Api.fixColor("&c&lHOTMC");

    //Api depends plugins
    public static Collection<CustomItemApi> getItems() {
        return items;
    }

    public static void addItem(CustomItemApi item) {
        if (!items.contains(item))
            items.add(item);
    }

    private static Economy vaultEconomy;
    public boolean placeholder = false;

    public static Economy getVaultEconomy() {
        return Main.vaultEconomy;
    }

    private boolean isVaultLoaded() {
        return Bukkit.getPluginManager().isPluginEnabled("Vault");
    }

    private boolean setupVault() {
        try {
            RegisteredServiceProvider<Economy> economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            if (economy == null)
                throw new Throwable("Vault's Economy provider is not available");
            vaultEconomy = economy.getProvider();
            return true;
        } catch (Throwable throwable) {
            getServer().getPluginManager().disablePlugin(this);
            getLogger().severe("Could not enable plugin, error message: " + throwable.getMessage());
            return false;
        }
    }
    //Configs
    public static PluginConfig pluginConfig;

    public static PluginCommands pluginCommands;

    //Others
    public static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }
    public Main() {
        plugin = this;
    }

    //Enable plugin
    @Override
    public void onEnable() {
        if (!this.isVaultLoaded()) {
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!this.setupVault()) {
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholder = true;
        }

        pluginConfig = ConfigManager.create(PluginConfig.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.saveDefaults();
            it.load(true);
        });

        pluginCommands = ConfigManager.create(PluginCommands.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withBindFile(new File(this.getDataFolder(), "commands.yml"));
            it.saveDefaults();
            it.load(true);
        });

        DatabaseConnector databaseConnector = new DatabaseConnector(pluginConfig);

        pluginConfig.load();
        pluginCommands.load();

        loadTasks();
        loadCommands();
        loadEvents();

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    PlayerQuitListener.checkPlayer(all);
                }
            }
        }, 0, 20 * 60 * 4);
    }

    //Disaable plugin
    @Override
    public void onDisable() {
        //It's nothing to add
    }

    public void loadCommands() {
        CommandManager.register(new BcCommand(), true);
        CommandManager.register(new TpCommand(), true);
        CommandManager.register(new ChatCommand(), true);
        CommandManager.register(new ClearCommand(), true);
        //CommandManager.register(new WithdrawCommand(), true);
        CommandManager.register(new DayCommand(), true);
        CommandManager.register(new DiscordCommand(), true);
        CommandManager.register(new EcCommand(), true);
        CommandManager.register(new FeedCommand(), true);
        CommandManager.register(new FlyCommand(), true);
        CommandManager.register(new GammaCommand(), true);
        CommandManager.register(new GmCommand(), true);
        CommandManager.register(new HatCommand(), true);
        CommandManager.register(new HealCommand(), true);
        CommandManager.register(new HelpCommand(), true);
        CommandManager.register(new HelperCommand(), true);
        CommandManager.register(new InvseeComand(), true);
        CommandManager.register(new MoreCommand(), true);
        CommandManager.register(new ItemCommand(), true);
        CommandManager.register(new TrashCommand(), true);
        CommandManager.register(new ListCommand(), true);
        CommandManager.register(new MeCommand(), true);
        CommandManager.register(new PunishmentCommand(), true);
        CommandManager.register(new MediaCommand(), true);
        CommandManager.register(new AbyssCommand(), true);
        CommandManager.register(new MsgCommand(), true);
        CommandManager.register(new SocialSpyCommand(), true);
        CommandManager.register(new TexturpackCommand(), true);
        CommandManager.register(new ReplyCommand(), true);
        CommandManager.register(new PurchaseCommand(), true);
        CommandManager.register(new RepairCommand(), true);
        CommandManager.register(new RulesCommand(), true);
        CommandManager.register(new SidebarCommand(), true);
        CommandManager.register(new SpawnCommand(), true);
        CommandManager.register(new TntCommand(), true);
        CommandManager.register(new CoreCommand(), true);
        CommandManager.register(new TpaAcceptCommand(), true);
        CommandManager.register(new TpaCommand(), true);
        CommandManager.register(new TpaDenyCommand(), true);
        CommandManager.register(new TpHereCommand(), true);
        CommandManager.register(new UpTimeCommand(), true);
        CommandManager.register(new WbCommand(), true);
        CommandManager.register(new WebsiteCommand(), true);
        CommandManager.register(new VanishCommand(), true);
        CommandManager.register(new MVPCommand(), true);
        CommandManager.register(new MVPPlusCommand(), true);
        CommandManager.register(new SVipCommand(), true);
        CommandManager.register(new VipCommand(), true);
        CommandManager.register(new YTCommand(), true);
        CommandManager.register(new SunCommand(), true);
        CommandManager.register(new StormCommand(), true);
        CommandManager.register(new PraceCommand(), true);
        CommandManager.register(new PrefixCommand(), true);
    }

    public void loadTasks() {
        new AutoMessageTask(this);
        new AutoBossBarTask(this);
        new AbyssTask(this);
        new PlayerTask(this);
        new VanishCommand.VanishRunnable();
    }

    public void loadEvents() {
        registerEvent(new PlayerJoinListener(), true);
        registerEvent(new PlayerChatListener(), true);
        registerEvent(new OthersListener(), true);
        registerEvent(new MediaCommand(), true);
        registerEvent(new PunishmentCommand(), true);
        registerEvent(new PlayerQuitListener(), true);
        registerEvent(new PlayerCombatListener(), true);
        registerEvent(new VanishCommand.VanishEvent(), true);
        //registerEvent(new CustomItemListener(), true);
        InventoryHelper.implement(this);
    }

    private void registerEvent(Listener listener, boolean enable) {
        if (enable) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        } else Api.sendLog("&cEvent " + listener + " has disabled!");
    }

    private static void ItemsLoader() {
        Main.addItem((new CustomItemApi(Material.DIAMOND_SWORD, CustomHand.MAIN))
                .setName("&b&lExcalibur")
                .setPotionEffects(Arrays.asList(new PotionEffect[] { new PotionEffect(PotionEffectType.BLINDNESS, 140, 99), new PotionEffect(PotionEffectType.CONFUSION, 140, 99) }))
                .setCustomModelData(1));
        Main.addItem((new CustomItemApi(Material.DIAMOND, CustomHand.HAT)
                .setName("&6&lKorona")
                .setPotionEffects(Arrays.asList(new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, 40, 2)}))
                .setCustomModelData(1)));
    }
}
