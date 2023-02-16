package xyz.dwaslashe.survivalcore;

import org.bukkit.Location;
import xyz.dwaslashe.survivalcore.database.db.DatabaseConfiguration;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dwaslashe.survivalcore.cache.*;
import xyz.dwaslashe.survivalcore.commands.*;
import xyz.dwaslashe.survivalcore.commands.lifesteal.CheckProfileCommand;
import xyz.dwaslashe.survivalcore.commands.lifesteal.ReviveCommand;
import xyz.dwaslashe.survivalcore.commands.managers.CommandManager;
import xyz.dwaslashe.survivalcore.configs.PluginCommands;
import xyz.dwaslashe.survivalcore.configs.PluginConfig;
import xyz.dwaslashe.survivalcore.configs.PluginRank;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.helpers.ItemHelper;
import xyz.dwaslashe.survivalcore.helpers.ReflectionHelper;
import xyz.dwaslashe.survivalcore.listeners.*;
import xyz.dwaslashe.survivalcore.model.CustomItem;
import xyz.dwaslashe.survivalcore.model.impl.CustomItemImpl;
import xyz.dwaslashe.survivalcore.parsers.LocationParser;
import xyz.dwaslashe.survivalcore.objects.TestUser;
import xyz.dwaslashe.survivalcore.objects.TestWarp;
import xyz.dwaslashe.survivalcore.placeholder.PlaceholderHooks;
import xyz.dwaslashe.survivalcore.tasks.*;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatBuffer;
import xyz.dwaslashe.survivalcore.utils.LicenseApi;
import xyz.dwaslashe.survivalcore.database.db.DatabaseConnector;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

@Getter @Setter
public class Main extends JavaPlugin {

    private static Economy vaultEconomy;
    public boolean placeholder = false;

    private BukkitAudiences audience;

    public BukkitAudiences getAudience() {
        return audience;
    }

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

    public static PluginRank pluginRank;

    //Others
    private final UserCache userCache = new UserCache();
    private final ItemCache itemCache = new ItemCache();

    public static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    public Main() {
        plugin = this;
    }

    private DatabaseConnector connector;

    //Enable plugin
    @SneakyThrows
    @Override
    public void onEnable() {
        audience = BukkitAudiences.create(this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

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

        pluginRank = ConfigManager.create(PluginRank.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withBindFile(new File(this.getDataFolder(), "rank.yml"));
            it.saveDefaults();
            it.load(true);
        });

        //DatabaseConnector databaseConnector = new DatabaseConnector(pluginConfig);
        //getter = new DatabaseGetter();
        //getter.setDatabaseConnector(databaseConnector);
//
        //getter.createTable("warps", "primary key(name)", Arrays.asList("name varchar(64)", "location varchar(2048)", "permission varchar(64)"));
//
        //getter.createTable("discord", "primary key(uuid)", Arrays.asList("uuid varchar(64)", "discordid varchar(64)"));
//
        //ResultSet rs = databaseConnector.getConnection().prepareStatement("select * from `warps`").executeQuery();
        //while (rs.next()) {
        //    warpCache.addFromSQL(rs);
        //}
        //rs.close();

        connector = new DatabaseConnector(new DatabaseConfiguration("212.23.222.83", "pterodactyluser", "zvJ6rzyP)tNjKOhB", "survival_test", 3306, true));

        pluginConfig.load();
        pluginCommands.load();
        pluginRank.load();

        if (!new LicenseApi(pluginConfig.getCore().getLicense(), "https://buybrain.pl/license/verify.php", this).register())
            return;

        new PlaceholderHooks().register();
        new ReflectionHelper().initialize();
        new OthersListener().EnderpearlCooldown(this);
        loadItems();
        loadTasks();
        loadCommands();
        loadEvents();
        registerPlaceholder();

        if (pluginConfig.getRecipes().isWeed()) {
            Bukkit.getServer().addRecipe((Recipe) OthersListener.getRecipeWeed());
        }
        if (pluginConfig.getRecipes().isKokaina()) {
            Bukkit.getServer().addRecipe((Recipe) OthersListener.getRecipeKokaina());
        }
        if (pluginConfig.getRecipes().isMagnet()) {
            Bukkit.getServer().addRecipe((Recipe) OthersListener.getRecipeMagnet());
        }
        if (pluginConfig.getRecipes().isEnchanted_apple()) {
            Bukkit.getServer().addRecipe((Recipe) OthersListener.getRecipeEnchantedApple());
        }

        if (pluginConfig.getEvents().isAntyafk()) {
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.hasPermission("core.antyafk.bypass")) {
                        return;
                    } else PlayerQuitListener.checkPlayer(all);
                }
            }, 0, 20 * 60 * 4);
        }

        //Database

        connector.getSerializerMap().put(Location.class, new LocationParser());

        connector.registerDataObjectToScan(TestUser.class);
        connector.getScanner(TestUser.class).ifPresent(UserDataObjectScanner -> UserDataObjectScanner.load(TestUserCache.getInstance()));

        connector.registerDataObjectToScan(TestWarp.class);
        connector.getScanner(TestWarp.class).ifPresent(WarpDataObjectScanner -> WarpDataObjectScanner.load(TestWarpCache.getInstance()));

        getServer().getScheduler().runTaskTimer(this, () -> {
            Set<TestUser> UserSet = new HashSet<>(TestUserCache.getInstance().getToUpdate());
            connector.getScanner(TestUser.class).ifPresent(scanner -> {
                UserSet.forEach(scanner::update);
                TestUserCache.getInstance().getToUpdate().removeAll(UserSet);
            });

            Set<TestWarp> WarpSet = new HashSet<>(TestWarpCache.getInstance().getToUpdate());
            connector.getScanner(TestWarp.class).ifPresent(scanner -> {
                WarpSet.forEach(scanner::update);
                TestWarpCache.getInstance().getToUpdate().removeAll(WarpSet);
            });


        }, 20, 20 * 10);
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        //Database
        connector.getScanner(TestUser.class).ifPresent(scanner -> {
            TestUserCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        connector.getScanner(TestWarp.class).ifPresent(scanner -> {
            TestWarpCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        try {
            connector.getConnection().close();
        } catch (SQLException ignore) {}
    }

    private void registerPlaceholder() {
        new PlaceholderHooks().register();
    }

    public void loadCommands() {
        CommandManager.register(new BcCommand(), pluginConfig.getCommands().isBroadcast());
        CommandManager.register(new AboveNameShopCommand(), pluginConfig.getCommands().isAbovename());
        CommandManager.register(new TpCommand(), pluginConfig.getCommands().isTeleport());
        CommandManager.register(new ChatCommand(), pluginConfig.getCommands().isChat());
        CommandManager.register(new ClearCommand(), pluginConfig.getCommands().isClear());
        CommandManager.register(new DayCommand(), pluginConfig.getCommands().isDay());
        CommandManager.register(new DiscordCommand(), pluginConfig.getCommands().isDiscord());
        CommandManager.register(new EcCommand(), pluginConfig.getCommands().isEnderchest());
        CommandManager.register(new FeedCommand(), pluginConfig.getCommands().isFeed());
        CommandManager.register(new FlyCommand(), pluginConfig.getCommands().isFly());
        CommandManager.register(new GammaCommand(), pluginConfig.getCommands().isGamma());
        CommandManager.register(new GmCommand(), pluginConfig.getCommands().isGamemode());
        CommandManager.register(new HatCommand(), pluginConfig.getCommands().isHat());
        CommandManager.register(new HealCommand(), pluginConfig.getCommands().isHeal());
        CommandManager.register(new HelpCommand(), pluginConfig.getCommands().isHelp());
        CommandManager.register(new HelperCommand(), pluginConfig.getCommands().isHelper());
        CommandManager.register(new InvseeCommand(), pluginConfig.getCommands().isInvsee());
        CommandManager.register(new MoreCommand(), pluginConfig.getCommands().isMore());
        CommandManager.register(new ItemCommand(), pluginConfig.getCommands().isItem());
        CommandManager.register(new TrashCommand(), pluginConfig.getCommands().isTrash());
        CommandManager.register(new ListCommand(), pluginConfig.getCommands().isList());
        CommandManager.register(new MeCommand(), pluginConfig.getCommands().isMe());
        CommandManager.register(new PunishmentCommand(), pluginConfig.getCommands().isPunishment());
        CommandManager.register(new MediaCommand(), pluginConfig.getCommands().isSocialmedia());
        CommandManager.register(new AbyssCommand(), pluginConfig.getCommands().isAbyss());
        CommandManager.register(new MsgCommand(), pluginConfig.getCommands().isMsg());
        CommandManager.register(new SocialSpyCommand(), pluginConfig.getCommands().isSocialspy());
        CommandManager.register(new TexturpackCommand(), pluginConfig.getCommands().isTexturpack());
        CommandManager.register(new ReplyCommand(), pluginConfig.getCommands().isReply());
        CommandManager.register(new PurchaseCommand(), pluginConfig.getCommands().isPurchase());
        CommandManager.register(new RepairCommand(), pluginConfig.getCommands().isRepair());
        CommandManager.register(new RulesCommand(), pluginConfig.getCommands().isRules());
        CommandManager.register(new SidebarCommand(), pluginConfig.getCommands().isSidebar());
        CommandManager.register(new SpawnCommand(), pluginConfig.getCommands().isSpawn());
        CommandManager.register(new TntCommand(), pluginConfig.getCommands().isTnt());
        CommandManager.register(new CoreCommand(), true);
        CommandManager.register(new TestCommand(), true);
        CommandManager.register(new TpaAcceptCommand(), pluginConfig.getCommands().isTeleportplayer());
        CommandManager.register(new TpaCommand(), pluginConfig.getCommands().isTeleportplayer());
        CommandManager.register(new TpaDenyCommand(), pluginConfig.getCommands().isTeleportplayer());
        CommandManager.register(new TpHereCommand(), pluginConfig.getCommands().isTeleporthere());
        CommandManager.register(new UpTimeCommand(), pluginConfig.getCommands().isUptime());
        CommandManager.register(new WbCommand(), pluginConfig.getCommands().isWb());
        CommandManager.register(new WebsiteCommand(), pluginConfig.getCommands().isWebsite());
        CommandManager.register(new VanishCommand(), pluginConfig.getCommands().isVanish());
        CommandManager.register(new RankCommand(), pluginConfig.getCommands().isRank());
        CommandManager.register(new YTCommand(), pluginConfig.getCommands().isYt());
        CommandManager.register(new NightCommand(), pluginConfig.getCommands().isNight());
        CommandManager.register(new SunCommand(), pluginConfig.getCommands().isSun());
        CommandManager.register(new StormCommand(), pluginConfig.getCommands().isStorm());
        CommandManager.register(new GlowingCommand(), pluginConfig.getCommands().isGlowing());
        CommandManager.register(new IncognitoCommand(), pluginConfig.getCommands().isIncognito());
        CommandManager.register(new NickColorCommand(), pluginConfig.getCommands().isNickcolor());
        CommandManager.register(new WarpCommand(), pluginConfig.getCommands().isWarp());
        CommandManager.register(new ChatManagerCommand(), pluginConfig.getCommands().isChatmanager());
        CommandManager.register(new ItemGiveCommand(), pluginConfig.getCommands().isItemgive());
        CommandManager.register(new TopCommand(), pluginConfig.getCommands().isTop());
        CommandManager.register(new RewardCommand(), pluginConfig.getCommands().isReward());
        CommandManager.register(new PingCommand(), pluginConfig.getCommands().isPing());
        CommandManager.register(new GodModCommand(), pluginConfig.getCommands().isGodmod());
        CommandManager.register(new MarketCommand(), pluginConfig.getCommands().isMarketaliases());
        CommandManager.register(new MagnetCommand(), pluginConfig.getCommands().isMagnet());
        CommandManager.register(new CheckCommand(), pluginConfig.getCommands().isCheck());
        CommandManager.register(new AdmitsCommand(), pluginConfig.getCommands().isCheck());
        CommandManager.register(new ReviveCommand(), pluginConfig.getCommands().isLifestealaliases());
        CommandManager.register(new CheckProfileCommand(), pluginConfig.getCommands().isLifestealaliases());
        CommandManager.register(new PraceCommand(), pluginConfig.getCommands().isPracealiases());
        CommandManager.register(new IgnoreCommand(), pluginConfig.getCommands().isIgnore());
        CommandManager.register(new UnIgnoreCommand(), pluginConfig.getCommands().isUnignore());
        CommandManager.register(new EnchantCommand(), pluginConfig.getCommands().isEnchant());
    }

    public void loadTasks() {

        new AutoMessageTask(this);
        new AutoBossBarTask(this);
        new AbyssTask(this);
        new PlayerTask(this);
        new VanishCommand.VanishRunnable();

        getServer().getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                CustomItem.get(player.getItemInHand()).ifPresent(customItem -> {
                    player.addPotionEffects(customItem.whenInHand());
                });
                CustomItem.get(player.getEquipment().getHelmet()).ifPresent(customItem -> {
                    player.addPotionEffects(customItem.whenWear());
                });
                CustomItem.get(player.getEquipment().getChestplate()).ifPresent(customItem -> {
                    player.addPotionEffects(customItem.whenWear());
                });
                CustomItem.get(player.getEquipment().getLeggings()).ifPresent(customItem -> {
                    player.addPotionEffects(customItem.whenWear());
                });
                CustomItem.get(player.getEquipment().getBoots()).ifPresent(customItem -> {
                    player.addPotionEffects(customItem.whenWear());
                });
                CustomItem.get(player.getEquipment().getItemInOffHand()).ifPresent(customItem -> {
                    player.addPotionEffects(customItem.whenInSecondHand());
                });
            });
        }, 0, 20);
    }

    public void loadEvents() {
        registerEvent(new PlayerJoinListener(), true);
        registerEvent(new PlayerChatListener(), true);
        registerEvent(new OthersListener(), true);
        registerEvent(new PlayerQuitListener(), true);
        registerEvent(new PlayerCombatListener(), true);
        registerEvent(new PlayerDeathListener(), true);
        registerEvent(new ItemCraftListener(), pluginConfig.getRecipes().isDiamond_set());
        registerEvent(new VanishCommand.VanishEvent(), true);
        registerEvent(new ChatBuffer(), true);
        registerEvent(new CheckCommand(), true);
        InventoryHelper.implement(this);
    }

    private void registerEvent(Listener listener, boolean enable) {
        if (enable) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        } else Api.sendLog("&cEvent " + listener + " has disabled!");
    }

    private void loadItems() {
        //Excalibur
        //ItemHelper itemHelper = new ItemHelper(Material.DIAMOND_SWORD);
        //itemHelper.setDisplayName("&b&lExcalibur");
        //itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        //itemHelper.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        //itemHelper.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        //CustomItemImpl item = new CustomItemImpl(0, itemHelper);
        //item.whenInHand().add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 2));
        //itemCache.register(item);

        //Korona
        ItemHelper itemHelper = new ItemHelper(Material.GOLDEN_HELMET);
        itemHelper.setDisplayName(Api.fixColor("&#FDBD01Korona"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &aItem daje &c5 serc &ai &eSZYBKOŚĆ 3!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        itemHelper.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);

        CustomItemImpl item = new CustomItemImpl(1, itemHelper);
        item.whenWear().add(new PotionEffect(PotionEffectType.SPEED, 60, 2));
        itemCache.register(item);


        //Buty Sonika
        itemHelper = new ItemHelper(Material.DIAMOND_BOOTS);
        itemHelper.setDisplayName(Api.fixColor("&#21F8F6Buty Sonika"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &aItem daje &eSZYBKOŚĆ 3!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(2, itemHelper);
        item.whenWear().add(new PotionEffect(PotionEffectType.SPEED, 60, 2));
        itemCache.register(item);

        //Hełm Orka
        itemHelper = new ItemHelper(Material.DIAMOND_HELMET);
        itemHelper.setDisplayName(Api.fixColor("&#008443Hełm Orka"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &aItem daje &c2 serca &ai &eWIDZENIE W CIEMNOŚCI 3!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        itemHelper.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);

        item = new CustomItemImpl(3, itemHelper);
        item.whenWear().add(new PotionEffect(PotionEffectType.NIGHT_VISION, 80, 2));
        itemCache.register(item);

        //Kilof Górnika
        itemHelper = new ItemHelper(Material.DIAMOND_PICKAXE);
        itemHelper.setDisplayName(Api.fixColor("&#FF5F1FKilof Górnika"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &aItem zabiera &c5 serc&a &ai daje &eSZBYKIE KOPANIE 2, ŚLEPOTA 2!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        itemHelper.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,-10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        item = new CustomItemImpl(4, itemHelper);
        item.whenInHand().add(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 1));
        item.whenInHand().add(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2));
        itemCache.register(item);

        //Pałka Policjanta
        itemHelper = new ItemHelper(Material.STICK);
        itemHelper.setDisplayName(Api.fixColor("&#808080Pałka Policjanta"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &aItem posiada odrzut &e6&a &ai w ręce daje &ePOWOLNOŚĆ 2, ŚLEPOTA 2!")));
        itemHelper.addEnchant(Enchantment.KNOCKBACK, 6);
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(5, itemHelper);
        item.whenInHand().add(new PotionEffect(PotionEffectType.SLOW, 120, 2));
        item.whenInHand().add(new PotionEffect(PotionEffectType.BLINDNESS, 120, 2));
        itemCache.register(item);

        //Tarcza Policjanta
        itemHelper = new ItemHelper(Material.SHIELD);
        itemHelper.setDisplayName(Api.fixColor("&#808080Tarcza Policjanta"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &aItem daje &eODPORNOSC 2!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(6, itemHelper);
        item.whenInSecondHand().add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 1));
        itemCache.register(item);

        //Tarcza Sapera
        itemHelper = new ItemHelper(Material.SHIELD);
        itemHelper.setDisplayName(Api.fixColor("&#023020Tarcza Sapera"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &aItem daje &eODPORNOSC 3!, ODPORNOŚĆ PRZED OGNIEM 1")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(7, itemHelper);
        item.whenInSecondHand().add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 3));
        item.whenInSecondHand().add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 1));
        itemCache.register(item);
    }
}
