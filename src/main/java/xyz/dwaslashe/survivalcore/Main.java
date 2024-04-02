package xyz.dwaslashe.survivalcore;

import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import net.saidora.api.events.EventBuilder;
import net.saidora.api.events.list.PlayerInjectExtensionEvent;
import net.saidora.api.events.list.TaskEvent;
import net.saidora.api.extension.PlayerExtension;
import net.saidora.economy.manager.UserManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.RegisteredServiceProvider;
import pl.minecodes.plots.api.plot.PlotServiceApi;
import xyz.dwaslashe.survivalcore.configs.*;
import xyz.dwaslashe.survivalcore.database.db.DatabaseConfiguration;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dwaslashe.survivalcore.cache.*;
import xyz.dwaslashe.survivalcore.commands.*;
import xyz.dwaslashe.survivalcore.commands.managers.CommandManager;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.helpers.ItemHelper;
import xyz.dwaslashe.survivalcore.helpers.ReflectionHelper;
import xyz.dwaslashe.survivalcore.listeners.*;
import xyz.dwaslashe.survivalcore.model.CustomItem;
import xyz.dwaslashe.survivalcore.model.impl.CustomItemImpl;
import xyz.dwaslashe.survivalcore.objects.*;
import xyz.dwaslashe.survivalcore.parsers.LocationParser;
import xyz.dwaslashe.survivalcore.placeholder.PlaceholderHooks;
import xyz.dwaslashe.survivalcore.tasks.*;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatBuffer;
import xyz.dwaslashe.survivalcore.database.db.DatabaseConnector;
import xyz.dwaslashe.survivalcore.utils.PictureApi;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@Getter @Setter
public class Main extends JavaPlugin {

    public boolean placeholder = false;

    //Configs
    public static PluginConfig pluginConfig;

    public static PluginCommands pluginCommands;

    public static PluginRank pluginRank;

    public static PluginVouchers pluginVouchers;

    public static PluginVapes pluginVapes;

    public static PluginEvents pluginEvents;

    //Others
    private final ItemCache itemCache = new ItemCache();

    public static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    public Main() {
        plugin = this;
    }

    private DatabaseConnector connector;

    private PlotServiceApi plotServiceApi;

    private PictureApi pictureApi;

    public Optional<Case> getCase(String name){
        return pluginEvents.getListCases().caseList.stream().filter(aCase -> aCase.getId().equals(name)).findFirst();
    }
    //Enable plugin
    @SneakyThrows
    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.setupPlotService();

        new EventBuilder<>(PlayerInjectExtensionEvent.class, event -> {
            PlayerExtension extension = event.getExtension();

            BossBar bossBar =  Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
            extension.addPersistentDataObject("layer1", bossBar);

            bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
            extension.addPersistentDataObject("layer2", bossBar);
        });

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
            it.withConfigurer(new YamlBukkitConfigurer(), new CustomSerdesPack());
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

        pluginVouchers = ConfigManager.create(PluginVouchers.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new CustomSerdesPack());
            it.withBindFile(new File(this.getDataFolder(), "vouchers.yml"));
            it.saveDefaults();
            it.load(true);
        });

        pluginVapes = ConfigManager.create(PluginVapes.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new CustomSerdesPack());
            it.withBindFile(new File(this.getDataFolder(), "vapes.yml"));
            it.saveDefaults();
            it.load(true);
        });

        pluginEvents = ConfigManager.create(PluginEvents.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new CustomSerdesPack(), new SerdesBukkit());
            it.withBindFile(new File(this.getDataFolder(), "events.yml"));
            it.saveDefaults();
            it.load(true);
        });
        //Furnace Recipe
        //new RecipeChoice.ExactChoice(OthersListener.driedChestnut)
        //FurnaceRecipe furnaceRecipe = new FurnaceRecipe(NamespacedKey.minecraft("wywrotkamc_chestnut"), OthersListener.chestnut, Material.DARK_OAK_BOAT, 5F, 60);
        //Bukkit.addRecipe(furnaceRecipe);
        if (pluginConfig.getRecipes().isMagnet()) {
            Bukkit.addRecipe(OthersListener.getRecipeMagnet());
        }
        if (pluginConfig.getRecipes().isEnchantedApple()) {
            Bukkit.addRecipe(OthersListener.getRecipeEnchantedApple());
        }

        connector = new DatabaseConnector(new DatabaseConfiguration(pluginConfig.getDatabase().getHost(), pluginConfig.getDatabase().getUsername(), pluginConfig.getDatabase().getPassword(), pluginConfig.getDatabase().getTable(), pluginConfig.getDatabase().getPort(), pluginConfig.getDatabase().isSsl()));

        pluginConfig.load();
        pluginCommands.load();
        pluginRank.load();
        pluginVouchers.load();
        pluginVapes.load();
        pluginEvents.load();

        pictureApi = new PictureApi(this);

        //if (!new LicenseApi(pluginConfig.getCore().getLicense(), "https://buybrain.pl/license/verify.php", this).register())
        //    return;

        new PlaceholderHooks().register();
        new ReflectionHelper().initialize();
        new PlayerInteractListener().EnderpearlCooldown(this);
        loadItems();
        loadTasks();
        loadCommands();
        loadEvents();
        registerPlaceholder();

        if (pluginConfig.getEvents().isAntyAfk()) {
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.hasPermission("core.antyafk.bypass") || CheckCommand.checks.contains(all.getName())) {
                        return;
                    } else PlayerQuitListener.checkPlayer(all);
                }
            }, 0, 20 * 60 * 4);
        }

        //Pirate Chest

        Location location = new Location(Bukkit.getWorld("spawn"), -2086, 91, 923);
        location.getChunk().load(true);
        Block block = location.getBlock();
        block.setType(Material.CHEST);
        block.setMetadata("PirateBlockEvent", new FixedMetadataValue(Main.getPlugin(), ""));

        //Database

        connector.getSerializerMap().put(Location.class, new LocationParser());

        connector.registerDataObjectToScan(User.class);
        connector.getScanner(User.class).ifPresent(UserDataObjectScanner -> UserDataObjectScanner.load(UserCache.getInstance()));

        connector.registerDataObjectToScan(UserTree.class);
        connector.getScanner(UserTree.class).ifPresent(UserDataObjectScanner -> UserDataObjectScanner.load(UserTreeCache.getInstance()));

        connector.registerDataObjectToScan(Warp.class);
        connector.getScanner(Warp.class).ifPresent(WarpDataObjectScanner -> WarpDataObjectScanner.load(WarpCache.getInstance()));

        connector.registerDataObjectToScan(PlayerWarp.class);
        connector.getScanner(PlayerWarp.class).ifPresent(WarpDataObjectScanner -> WarpDataObjectScanner.load(PlayerWarpCache.getInstance()));

        connector.registerDataObjectToScan(DragonLevel.class);
        connector.getScanner(DragonLevel.class).ifPresent(DragonLevelDataObjectScanner -> DragonLevelDataObjectScanner.load(DragonLevelCache.getInstance()));

        connector.registerDataObjectToScan(MoneyTarget.class);
        connector.getScanner(MoneyTarget.class).ifPresent(MoneyTargetDataObjectScanner -> MoneyTargetDataObjectScanner.load(MoneyTargetCache.getInstance()));

        connector.registerDataObjectToScan(Ticket.class);
        connector.getScanner(Ticket.class).ifPresent(TicketDataObjectScanner -> TicketDataObjectScanner.load(TicketCache.getInstance()));

        connector.registerDataObjectToScan(Marry.class);
        connector.getScanner(Marry.class).ifPresent(MarryDataObjectScanner -> MarryDataObjectScanner.load(MarryCache.getInstance()));

        AtomicLong notify = new AtomicLong();
        AtomicLong admit = new AtomicLong();

        new EventBuilder<>(TaskEvent.class, taskEvent -> {
            Consumer<Player> notifyConsumer = player -> {};
            Consumer<Player> admitConsumer = player -> {};
            if (notify.get() < System.currentTimeMillis()) {
                notify.set(System.currentTimeMillis() + 1000);
                notifyConsumer = player -> {
                    if(RegionListener.afk.contains(player.getUniqueId()) && !player.isInsideVehicle()){
                        Api.sendActionBar(player, "&8>> <#39FF14>Obecnie jesteś w strefie afk, co minute dostajesz <#FFF88F>2 <#FFC42E>$ &8<<");
                    }
                };
            }

            if(admit.get() < System.currentTimeMillis()){
                admit.set(System.currentTimeMillis() + 60000);
                admitConsumer = player -> {
                    if(RegionListener.afk.contains(player.getUniqueId()) && !player.isInsideVehicle()){{
                        User userPlayer = UserCache.getInstance().compute(player.getUniqueId());
                        userPlayer.addTimeAfk(60000);
                        PlayerQuitListener.locYaw.remove(player.getUniqueId());
                        player.sendTitle(Api.fixColor("&#F23D07&lAFK"), Api.fixColor("&8>> &#4cf739Za spędzenie minuty w strefie afk dostałeś &#FFF88F2 &f&#4cf739! &8<<"));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, 10, 10);
                        UserManager.getInstance().getUser(player).ifPresent(user -> user.deposit(2));
                    }}
                };
            }

            if(admit.get() < System.currentTimeMillis() && notify.get() < System.currentTimeMillis()) return;
            Consumer<Player> finalNotifyConsumer = notifyConsumer;
            Consumer<Player> finalAdmitConsumer = admitConsumer;
            taskEvent.execute(player -> {
                finalAdmitConsumer.accept(player);
                finalNotifyConsumer.accept(player);
            });
        });

        getServer().getScheduler().runTaskTimer(this, () -> {
            Set<User> UserSet = new HashSet<>(UserCache.getInstance().getToUpdate());
            connector.getScanner(User.class).ifPresent(scanner -> {
                UserSet.forEach(scanner::update);
                UserCache.getInstance().getToUpdate().removeAll(UserSet);
            });

            Set<UserTree> UserTreeSet = new HashSet<>(UserTreeCache.getInstance().getToUpdate());
            connector.getScanner(UserTree.class).ifPresent(scanner -> {
                UserTreeSet.forEach(scanner::update);
                UserTreeCache.getInstance().getToUpdate().removeAll(UserTreeSet);
            });

            Set<Warp> WarpSet = new HashSet<>(WarpCache.getInstance().getToUpdate());
            connector.getScanner(Warp.class).ifPresent(scanner -> {
                WarpSet.forEach(scanner::update);
                WarpCache.getInstance().getToUpdate().removeAll(WarpSet);
            });

            Set<PlayerWarp> PlayerWarpSet = new HashSet<>(PlayerWarpCache.getInstance().getToUpdate());
            connector.getScanner(PlayerWarp.class).ifPresent(scanner -> {
                PlayerWarpSet.forEach(scanner::update);
                PlayerWarpCache.getInstance().getToUpdate().removeAll(PlayerWarpSet);
            });

            Set<DragonLevel> DragonLevelSet = new HashSet<>(DragonLevelCache.getInstance().getToUpdate());
            connector.getScanner(DragonLevel.class).ifPresent(scanner -> {
                DragonLevelSet.forEach(scanner::update);
                DragonLevelCache.getInstance().getToUpdate().removeAll(DragonLevelSet);
            });

            Set<MoneyTarget> MoneyTargetSet = new HashSet<>(MoneyTargetCache.getInstance().getToUpdate());
            connector.getScanner(MoneyTarget.class).ifPresent(scanner -> {
                MoneyTargetSet.forEach(scanner::update);
                MoneyTargetCache.getInstance().getToUpdate().removeAll(MoneyTargetSet);
            });

            Set<Marry> MarrySet = new HashSet<>(MarryCache.getInstance().getToUpdate());
            connector.getScanner(Marry.class).ifPresent(scanner -> {
                MarrySet.forEach(scanner::update);
                MarryCache.getInstance().getToUpdate().removeAll(MarrySet);
            });

            Set<Ticket> TicketSet = new HashSet<>(TicketCache.getInstance().getToUpdate());
            connector.getScanner(Ticket.class).ifPresent(scanner -> {
                TicketSet.forEach(scanner::update);
                TicketCache.getInstance().getToUpdate().removeAll(TicketSet);
            });

        }, 20, 20 * 10);
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        //Database
        connector.getScanner(User.class).ifPresent(scanner -> {
            UserCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        connector.getScanner(UserTree.class).ifPresent(scanner -> {
            UserTreeCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        connector.getScanner(Warp.class).ifPresent(scanner -> {
            WarpCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        connector.getScanner(PlayerWarp.class).ifPresent(scanner -> {
            PlayerWarpCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        connector.getScanner(DragonLevel.class).ifPresent(scanner -> {
            DragonLevelCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        connector.getScanner(MoneyTarget.class).ifPresent(scanner -> {
            MoneyTargetCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        connector.getScanner(Marry.class).ifPresent(scanner -> {
            MarryCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        connector.getScanner(Ticket.class).ifPresent(scanner -> {
            TicketCache.getInstance().getToUpdate().forEach(scanner::update);
        });

        try {
            connector.getConnection().close();
        } catch (SQLException ignore) {}
    }

    public void setupPlotService() {
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("minePlots")) {
            Bukkit.getLogger().severe("Disabled due to no minePlots dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        RegisteredServiceProvider<PlotServiceApi> serviceProvider = Bukkit.getServicesManager().getRegistration(PlotServiceApi.class);
        Objects.requireNonNull(serviceProvider, "Service provider is null!");

        plotServiceApi = serviceProvider.getProvider();
    }

    private void registerPlaceholder() {
        new PlaceholderHooks().register();
    }

    public void loadCommands() {
        CommandManager.register(new TestCommand(), true);
        CommandManager.register(new FBICommand(), pluginConfig.getCommands().isFbi());
        CommandManager.register(new PayTicketCommand(), pluginConfig.getCommands().isTicket());
        CommandManager.register(new TicketCommand(), pluginConfig.getCommands().isTicket());
        CommandManager.register(new RainbowChatCommand(), pluginConfig.getCommands().isRainbowChat());
        CommandManager.register(new WikiCommand(), pluginConfig.getCommands().isWiki());
        CommandManager.register(new YouTubeCommand(), pluginConfig.getCommands().isYouTube());
        CommandManager.register(new OnaMiala10LatCommand(), pluginConfig.getCommands().isOnamiala10lat());
        CommandManager.register(new FaQCommand(), pluginConfig.getCommands().isFaq());
        CommandManager.register(new EmergencyNumberCommand(), pluginConfig.getCommands().isEmergencyNumber());
        //CommandManager.register(new ZielarzCommand(), pluginConfig.getCommands().isZielarz());
        CommandManager.register(new EventCommand(), pluginConfig.getCommands().isEvent());
        CommandManager.register(new VapeCommand(), pluginConfig.getCommands().isVape());
        CommandManager.register(new RockPaperScissorsCommand(), pluginConfig.getCommands().isRockPaperScissors());
        CommandManager.register(new VoucherCommand(), pluginConfig.getCommands().isVoucher());
        CommandManager.register(new AnvilCommand(), pluginConfig.getCommands().isAnvil());
        CommandManager.register(new ProtectionCommand(), pluginConfig.getCommands().isProtection());
        CommandManager.register(new AdminProtectionCommand(), pluginConfig.getCommands().isProtection());
        CommandManager.register(new BoosterCommand(), pluginConfig.getCommands().isBooster());
        CommandManager.register(new MarryCommand(), pluginConfig.getCommands().isMarry());
        CommandManager.register(new MoneyTargetCommand(), pluginConfig.getCommands().isMoneyTarget());
        CommandManager.register(new DeleteHomeCommand(), pluginConfig.getCommands().isHomes());
        CommandManager.register(new SetHomeCommand(), pluginConfig.getCommands().isHomes());
        CommandManager.register(new HomesCommand(), pluginConfig.getCommands().isHomes());
        CommandManager.register(new PokeBallCommand(), pluginConfig.getCommands().isPokeBall());
        CommandManager.register(new BcCommand(), pluginConfig.getCommands().isBroadcast());
        CommandManager.register(new AboveNameShopCommand(), pluginConfig.getCommands().isAboveNameShop());
        CommandManager.register(new TpCommand(), pluginConfig.getCommands().isTeleport());
        CommandManager.register(new ChatCommand(), pluginConfig.getCommands().isChat());
        CommandManager.register(new ClearCommand(), pluginConfig.getCommands().isClear());
        CommandManager.register(new DayCommand(), pluginConfig.getCommands().isDay());
        CommandManager.register(new DiscordCommand(), pluginConfig.getCommands().isDiscord());
        CommandManager.register(new EcCommand(), pluginConfig.getCommands().isEnderChest());
        CommandManager.register(new FeedCommand(), pluginConfig.getCommands().isFeed());
        CommandManager.register(new FlyCommand(), pluginConfig.getCommands().isFly());
        CommandManager.register(new GammaCommand(), pluginConfig.getCommands().isGamma());
        CommandManager.register(new GmCommand(), pluginConfig.getCommands().isGameMode());
        CommandManager.register(new HatCommand(), pluginConfig.getCommands().isHat());
        CommandManager.register(new HealCommand(), pluginConfig.getCommands().isHeal());
        CommandManager.register(new HelpCommand(), pluginConfig.getCommands().isHelp());
        CommandManager.register(new HelperCommand(), pluginConfig.getCommands().isHelper());
        CommandManager.register(new InvseeCommand(), pluginConfig.getCommands().isInvSee());
        CommandManager.register(new MoreCommand(), pluginConfig.getCommands().isMore());
        CommandManager.register(new ItemCommand(), pluginConfig.getCommands().isItem());
        CommandManager.register(new TrashCommand(), pluginConfig.getCommands().isTrash());
        CommandManager.register(new ListCommand(), pluginConfig.getCommands().isList());
        CommandManager.register(new MeCommand(), pluginConfig.getCommands().isMe());
        CommandManager.register(new PunishmentCommand(), pluginConfig.getCommands().isPunishment());
        CommandManager.register(new MediaCommand(), pluginConfig.getCommands().isSocialMedia());
        CommandManager.register(new AbyssCommand(), pluginConfig.getCommands().isAbyss());
        CommandManager.register(new MsgCommand(), pluginConfig.getCommands().isMsg());
        CommandManager.register(new SocialSpyCommand(), pluginConfig.getCommands().isSocialSpy());
        CommandManager.register(new TexturpackCommand(), pluginConfig.getCommands().isTexturpack());
        CommandManager.register(new ReplyCommand(), pluginConfig.getCommands().isReply());
        CommandManager.register(new PurchaseCommand(), pluginConfig.getCommands().isPurchase());
        CommandManager.register(new RepairCommand(), pluginConfig.getCommands().isRepair());
        CommandManager.register(new RulesCommand(), pluginConfig.getCommands().isRules());
        CommandManager.register(new SidebarCommand(), pluginConfig.getCommands().isSidebar());
        CommandManager.register(new SpawnCommand(), pluginConfig.getCommands().isSpawn());
        CommandManager.register(new TntCommand(), pluginConfig.getCommands().isTnt());
        CommandManager.register(new CoreCommand(), true);
        CommandManager.register(new TpaAcceptCommand(), pluginConfig.getCommands().isTeleportPlayer());
        CommandManager.register(new TpaCommand(), pluginConfig.getCommands().isTeleportPlayer());
        CommandManager.register(new TpaDenyCommand(), pluginConfig.getCommands().isTeleportPlayer());
        CommandManager.register(new TpHereCommand(), pluginConfig.getCommands().isTeleportHere());
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
        CommandManager.register(new NickColorCommand(), pluginConfig.getCommands().isNickColor());
        CommandManager.register(new WarpCommand(), pluginConfig.getCommands().isWarp());
        CommandManager.register(new ChatManagerCommand(), pluginConfig.getCommands().isChatManager());
        CommandManager.register(new ItemGiveCommand(), pluginConfig.getCommands().isItemGive());
        CommandManager.register(new TopCommand(), pluginConfig.getCommands().isTop());
        CommandManager.register(new RewardCommand(), pluginConfig.getCommands().isReward());
        CommandManager.register(new PingCommand(), pluginConfig.getCommands().isPing());
        CommandManager.register(new GodModCommand(), pluginConfig.getCommands().isGodMod());
        //CommandManager.register(new PlayerWarpCommand(), pluginConfig.getCommands().isPlayerwarp());
        CommandManager.register(new MagnetCommand(), pluginConfig.getCommands().isMagnet());
        CommandManager.register(new CheckCommand(), pluginConfig.getCommands().isCheck());
        CommandManager.register(new AdmitsCommand(), pluginConfig.getCommands().isCheck());
        CommandManager.register(new PraceCommand(), pluginConfig.getCommands().isPracealiases());
        CommandManager.register(new IgnoreCommand(), pluginConfig.getCommands().isIgnore());
        CommandManager.register(new EnchantCommand(), pluginConfig.getCommands().isEnchant());
        CommandManager.register(new PhysicsCommand(), pluginConfig.getCommands().isPhysics());
        CommandManager.register(new WithdrawCommand(), pluginConfig.getCommands().isWithdraw());
        CommandManager.register(new XPBottleCommand(), pluginConfig.getCommands().isXpBottle());
        CommandManager.register(new TikTokCommand(), pluginConfig.getCommands().isTiktok());
        CommandManager.register(new WorldCommand(), pluginConfig.getCommands().isWorld());
        CommandManager.register(new ElytraGiveCommand(), pluginConfig.getCommands().isElytraGive());
    }

    public void loadTasks() {

        new AutoMessageTask(this);
        new AutoBossBarTask(this);
        new AbyssTask(this);
        new PlayerTask(this);
        new SecondPlayerTask(this);
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
        //registerEvent(new TicketCommand(), pluginConfig.getCommands().isTicket());
        //registerEvent(new RockPaperScissorsCommand(), pluginConfig.getCommands().isRockPaperScissors());
        registerEvent(new VapeCommand(), pluginConfig.getCommands().isVape());
        registerEvent(new VoucherListener(), pluginConfig.getCommands().isVoucher());
        registerEvent(new BoosterCommand(), pluginConfig.getCommands().isBooster());
        //registerEvent(new DragonLevelListener(), pluginConfig.getEvents().isDragonLevel());
        registerEvent(new CustomItemsListener(), true);
        //registerEvent(new RegionListener(), true);
        registerEvent(new PlayerJoinListener(this), true);
        registerEvent(new BlockBreakListener(), pluginConfig.getCommands().isPhysics());
        registerEvent(new PlayerChatListener(), true);
        registerEvent(new OthersListener(), true);
        registerEvent(new PlayerQuitListener(), true);
        //registerEvent(new PlayerCombatListener(), true);
        registerEvent(new PlayerDeathListener(), true);
        registerEvent(new ItemCraftListener(), true);
        registerEvent(new VanishCommand.VanishEvent(), true);
        registerEvent(new ChatBuffer(), true);
        registerEvent(new CheckCommand(), true);
        registerEvent(new PlayerInteractListener(), true);
        //registerEvent(new PlotSellWandListener(plotServiceApi), true);
        InventoryHelper.implement(this);
    }

    private void registerEvent(Listener listener, boolean enable) {
        if (enable) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        } else Api.sendLog("&#fc2419Event " + listener + " has disabled!");
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
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Item daje &#fc24195 serc &#E7E7E7i &bSZYBKOŚĆ 3!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        itemHelper.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemHelper.addAttributeModifier(Attribute.GENERIC_ARMOR,2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);

        CustomItemImpl item = new CustomItemImpl(1, itemHelper);
        item.whenWear().add(new PotionEffect(PotionEffectType.SPEED, 60, 2));
        itemCache.register(item);


        //Buty Sonika
        itemHelper = new ItemHelper(Material.DIAMOND_BOOTS);
        itemHelper.setDisplayName(Api.fixColor("&#21F8F6Buty Sonika"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Item daje &bSZYBKOŚĆ 3!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        itemHelper.addAttributeModifier(Attribute.GENERIC_ARMOR,3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);

        item = new CustomItemImpl(2, itemHelper);
        item.whenWear().add(new PotionEffect(PotionEffectType.SPEED, 60, 2));
        itemCache.register(item);

        //Hełm Orka
        itemHelper = new ItemHelper(Material.DIAMOND_HELMET);
        itemHelper.setDisplayName(Api.fixColor("&#008443Hełm Orka"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Item daje &#fc24192 serca &#E7E7E7i &bWIDZENIE W CIEMNOŚCI 3!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        itemHelper.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemHelper.addAttributeModifier(Attribute.GENERIC_ARMOR,3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);

        item = new CustomItemImpl(3, itemHelper);
        item.whenWear().add(new PotionEffect(PotionEffectType.NIGHT_VISION, 300, 3));
        itemCache.register(item);

        //Kilof Górnika
        itemHelper = new ItemHelper(Material.DIAMOND_PICKAXE);
        itemHelper.setDisplayName(Api.fixColor("&#FF5F1FKilof Górnika"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Item zabiera &#fc24195 serc &#E7E7E7i daje &bSZBYKIE KOPANIE 2, ŚLEPOTA 2!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        itemHelper.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,-10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        item = new CustomItemImpl(4, itemHelper);
        item.whenInHand().add(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 1));
        item.whenInHand().add(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2));
        itemCache.register(item);

        //Pałka Policjanta
        itemHelper = new ItemHelper(Material.STICK);
        itemHelper.setDisplayName(Api.fixColor("&#808080Pałka Policjanta"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Item posiada odrzut &b6&#E7E7E7 i daje &bPOWOLNOŚĆ 2, ŚLEPOTA 2!")));
        itemHelper.addEnchant(Enchantment.KNOCKBACK, 6);
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(5, itemHelper);
        item.whenInHand().add(new PotionEffect(PotionEffectType.SLOW, 120, 2));
        item.whenInHand().add(new PotionEffect(PotionEffectType.BLINDNESS, 120, 2));
        itemCache.register(item);

        //Tarcza Policjanta
        itemHelper = new ItemHelper(Material.SHIELD);
        itemHelper.setDisplayName(Api.fixColor("&#808080Tarcza Policjanta"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Item daje &bODPORNOSC 2!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(6, itemHelper);
        item.whenInSecondHand().add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 1));
        itemCache.register(item);

        //Tarcza Sapera
        itemHelper = new ItemHelper(Material.SHIELD);
        itemHelper.setDisplayName(Api.fixColor("&#023020Tarcza Sapera"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Item daje &bODPORNOSC 3!, ODPORNOŚĆ PRZED OGNIEM 1")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(7, itemHelper);
        item.whenInSecondHand().add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 3));
        item.whenInSecondHand().add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 1));
        itemCache.register(item);

        //NEW ITEMS

        //Pirates
        itemHelper = new ItemHelper(Material.PLAYER_HEAD);
        itemHelper.setOwnerURL("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTYzNDNmYmQ4YWZlNzcxN2Y0MmM4MTgzYTNlNGJmYjA1ZjkzMzYyZWE3ODkyYjUwYWY0NDQ2NTQwZjQ0MiJ9fX0=");
        itemHelper.setDisplayName(Api.fixColor("&#fa540cPirat"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Gdy masz ją na sobie dostajesz &#fa7b14oddychanie pod wodą&#E7E7E7 i", " &#E7E7E7dostajesz &#fa1c145❤ &#E7E7E7serc!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(8, itemHelper);
        item.whenWear().add(new PotionEffect(PotionEffectType.WATER_BREATHING, 60, 2));
        itemHelper.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);

        itemHelper.addAttributeModifier(Attribute.GENERIC_ARMOR,3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemHelper.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemCache.register(item);

        //Angel
        itemHelper = new ItemHelper(Material.PLAYER_HEAD);
        itemHelper.setOwnerURL("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVjY2RhNzBiZWFkOWY2N2IzOWRjZThiMDQwYWQwZjA4ZWZjMjMwNWMxZjY4NDYxMTY0N2EwMThhNjY0NTJjMiJ9fX0=");
        itemHelper.setDisplayName(Api.fixColor("&#fad014Anioł"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Gdy masz ją na sobie jesteś w stanie", " &#E7E7E7dostajesz &#E7E7E7efekt &#fa7b14szybkiego kopania III")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(9, itemHelper);
        item.whenWear().add(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 2));

        itemHelper.addAttributeModifier(Attribute.GENERIC_ARMOR,3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemHelper.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemCache.register(item);

        //Chainsaw man
        itemHelper = new ItemHelper(Material.PLAYER_HEAD);
        itemHelper.setOwnerURL("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZkODJhM2NiM2M1YmZhODVhY2M3MzA3OGE2ZmIwY2Q4M2UxY2ViN2NmYTU4NGI5ZDFlMjllMzQ0ZTU5NDY1MyJ9fX0=");
        itemHelper.setDisplayName(Api.fixColor("&#fa2f14Głowa Piły Łancuchowej"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Gdy masz ją na sobie jesteś w stanie", " &#E7E7E7zadawać &#14a9fa1.5x szybciej&#E7E7E7 obrażenia i", " &#E7E7E7dostajesz &#fa1c142❤ &#E7E7E7serca!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(10, itemHelper);
        itemHelper.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemHelper.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,0.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);

        itemHelper.addAttributeModifier(Attribute.GENERIC_ARMOR,3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemHelper.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemCache.register(item);

        //Szabla
        itemHelper = new ItemHelper(Material.GOLDEN_SWORD);
        itemHelper.setDisplayName(Api.fixColor("&#f5a742Szabla"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Jesteś w stanie zadawać &#14a9fa1.5x szybciej", " &#E7E7E7i &#14a9fa1.5x więcej&#E7E7E7 obrażenia!")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(11, itemHelper);
        itemHelper.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemHelper.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,0.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        itemCache.register(item);

        //Miecz odrzutu
        itemHelper = new ItemHelper(Material.DIAMOND_SWORD);
        itemHelper.setDisplayName(Api.fixColor("<#3ba4f5>Miecz odrzutu</#1b64f5>"));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));
        itemHelper.addEnchant(Enchantment.KNOCKBACK, 5);
        itemHelper.addEnchant(Enchantment.DAMAGE_ALL, 5);
        itemHelper.addEnchant(Enchantment.DURABILITY, 3);

        item = new CustomItemImpl(12, itemHelper);
        itemCache.register(item);

        //Wybuchowy łuk
        itemHelper = new ItemHelper(Material.BOW);
        itemHelper.setDisplayName(Api.fixColor("<#f59b42>Wybuchowy łuk</#f53220>"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Wystrzelona strzała tworzy", " &#E7E7E7eksplozje przy uderzeniu", " &#E7E7E7Czas odnowienia &#ffd56c30 sekund &fᎠ")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(13, itemHelper);
        itemCache.register(item);

        //Niszczyciel burzy
        itemHelper = new ItemHelper(Material.DIAMOND_AXE);
        itemHelper.setDisplayName(Api.fixColor("<#ffe32b>Niszczyciel burzy</#ffb52b>"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Przy uderzeniu przyzywa piorun", " &#E7E7E7Szansa na pojawienie się piorunu &#f58e2740%")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(14, itemHelper);
        itemCache.register(item);

        //Miecz ognia
        itemHelper = new ItemHelper(Material.DIAMOND_SWORD);
        itemHelper.setDisplayName(Api.fixColor("<#ff6b2b>Miecz ognia</#ff2b2b>"));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));
        itemHelper.addEnchant(Enchantment.FIRE_ASPECT, 3);
        itemHelper.addEnchant(Enchantment.DAMAGE_ALL, 5);
        itemHelper.addEnchant(Enchantment.DURABILITY, 3);

        item = new CustomItemImpl(15, itemHelper);
        itemCache.register(item);

        //Smocza fajerewerka
        itemHelper = new ItemHelper(Material.FIREWORK_ROCKET);
        itemHelper.setDisplayName(Api.fixColor("<#b342f5>Smocza fajerwerka</#6d28ed>"));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));
        itemHelper.addEnchant(Enchantment.DURABILITY, 3);
        itemHelper.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item = new CustomItemImpl(16, itemHelper);
        itemCache.register(item);

        //Łuk zamrożenia
        itemHelper = new ItemHelper(Material.BOW);
        itemHelper.setDisplayName(Api.fixColor("<#43e5fa>Łuk zamrożenia</#27a6f5>"));
        itemHelper.setLore(Api.fixColor(Arrays.asList("", " &#E7E7E7Wystrzelona strzała w gracza", " &#E7E7E7daje mu zamrożenia na &#ffd56c2 sekundy &fᎠ", " &#E7E7E7Czas odnowienia &#ffd56c30 sekund &fᎠ")));
        itemHelper.withMeta(itemMeta -> itemMeta.setCustomModelData(1));

        item = new CustomItemImpl(17, itemHelper);
        itemCache.register(item);
    }
}
