package xyz.dwaslashe.survivalcore.commands;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.badbones69.blockparticles.api.ParticleManager;
import net.saidora.api.events.EventBuilder;
import net.saidora.api.events.list.PlayerInjectExtensionEvent;
import net.saidora.api.extension.PlayerExtension;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RandomApi;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EventCommand extends Command {
    public EventCommand() {
        super("event", "/event <case, meteor, random> <cases>", "");
        setPermission("core.command.event");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("case", "meteor", "random"), args[0]);
        if (args.length == 2) return Api.startsWith(Arrays.asList(Main.pluginEvents.getListCases().getCaseList().toArray().toString()), args[01]);
        return null;
    }

    public static Map<String, Boolean> eventMap = new HashMap<>();

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("case")) {
                if (eventMap.containsKey("SKRZYNIA")) {
                    Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz wywołać tego eventu ponieważ aktualnie on trwa!");
                    return;
                }

                Main.getPlugin().getCase(args[1]).ifPresentOrElse(aCase -> {
                    Location caseLocation = generateLocation();
                    List<String> lines = Arrays.asList("&#FDBD01&lEVENT SKRZYNIA", "", "&#e3c97dOtwórz zrzut i zbierz przedmioty!");
                    Location hologramLocation = new Location(caseLocation.getWorld(), caseLocation.getX(), caseLocation.getY() + 3, caseLocation.getZ(), caseLocation.getYaw(), caseLocation.getPitch());
                    Hologram hologram = DHAPI.createHologram("case", hologramLocation.toCenterLocation(), lines);
                    hologram.save();

                    ParticleManager.getInstance().getParticleControl().playFireSpew(caseLocation, "caseBlock");

                    eventMap.put("SKRZYNIA", true);
                    Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie stworzono skrzynie!");
                    Api.sendBroadcast("\n        &#FDBD01&lEVENT SKRZYNIA\n \n&8>> &#e3c97dSkrzynia pojawiła się na &#7d5af2X: " + caseLocation.getBlockX() + " Y: " + caseLocation.getBlockY() + " Z: " + caseLocation.getBlockZ() + "\n&8>> &#e3c97dKto pierwszy ten lepszy! \n ");

                    AtomicInteger counter = new AtomicInteger(0);
                    new BukkitRunnable() {
                        boolean shouldCancel = false;
                        @Override
                        public void run() {
                            if(shouldCancel) this.cancel();
                            Bukkit.getOnlinePlayers().stream().map(PlayerExtension::getPlayerExtend).filter(Objects::nonNull).forEach(playerExtension -> {
                                BossBar bossBar = playerExtension.getPersistentDataObject("layer2", BossBar.class);

                                if (eventMap.get("SKRZYNIA") == null) {
                                    bossBar.removeAll();
                                    bossBar.setVisible(false);
                                    this.shouldCancel = true;
                                    return;
                                } else if(!bossBar.getPlayers().contains(playerExtension.getPlayer())) {
                                    bossBar.addPlayer(playerExtension.getPlayer());
                                    bossBar.setVisible(true);
                                }

                                if (eventMap.get("SKRZYNIA")) {
                                    if (counter.get() % 10 == 0) {
                                        updateBossBarProgress(playerExtension.getPlayer(), caseLocation, "&#FDBD01&lEVENT SKRZYNIA: #e3c97dTwoja ilość bloków do pokonania #bfe37d{blocks}", "SKRZYNIA", bossBar, BarColor.YELLOW);
                                    } else {
                                        updateBossBarProgress(playerExtension.getPlayer(), caseLocation, "&#FDBD01&lEVENT SKRZYNIA: #e3c97dSkrzynia pojawiła się na &#e3a77dX: {x} Y: {y} Z: {z}", "SKRZYNIA", bossBar, BarColor.YELLOW);
                                    }
                                    counter.incrementAndGet();
                                } else {
                                    bossBar.removeAll();
                                    this.shouldCancel = true;
                                }
                            });

                        }
                    }.runTaskTimer(Main.getPlugin(), 0L, 20L);
                    aCase.spawn(caseLocation);
                }, () -> Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cNie istnieja taka skrzynia!"));
            }
        }
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("meteor")) {
                if (eventMap.containsKey("METEORYT")) {
                    Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz wywołać tego eventu ponieważ aktualnie on trwa!");
                    return;
                }

                Location meteorLocation = generateLocation();

                List<String> lines = Arrays.asList("&#8334eb&lEVENT METEORYT", "", "&#a06ee0Zniszcz go aby dowiedzieć się ile ma życia!");
                Location hologramLocation = new Location(meteorLocation.getWorld(), meteorLocation.getX(), meteorLocation.getY() + 2, meteorLocation.getZ(), meteorLocation.getYaw(), meteorLocation.getPitch());
                Hologram hologram = DHAPI.createHologram("meteor", hologramLocation.toCenterLocation(), lines);
                hologram.save();

                ParticleManager.getInstance().getParticleControl().playSoulWell(meteorLocation, "meteorBlock");

                eventMap.put("METEORYT", true);
                Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie stworzono meteoryt!");
                Api.sendBroadcast("\n        &#8334eb&lEVENT METEORYT\n \n&8>> &#a06ee0Meteoryt pojawił się na &#7d5af2X: " + meteorLocation.getBlockX() + " Y: " + meteorLocation.getBlockY() + " Z: " + meteorLocation.getBlockZ() + " \n ");

                AtomicInteger counter = new AtomicInteger(0);
                new BukkitRunnable() {
                    boolean shouldCancel = false;
                    @Override
                    public void run() {
                        if(shouldCancel) this.cancel();
                        Bukkit.getOnlinePlayers().stream().map(PlayerExtension::getPlayerExtend).filter(Objects::nonNull).forEach(playerExtension -> {
                            BossBar bossBar = playerExtension.getPersistentDataObject("layer1", BossBar.class);

                            if (eventMap.get("METEORYT") == null) {
                                bossBar.removeAll();
                                bossBar.setVisible(false);
                                this.shouldCancel = true;
                            } else {
                                if(!bossBar.getPlayers().contains(playerExtension.getPlayer())) {
                                    bossBar.addPlayer(playerExtension.getPlayer());
                                    bossBar.setVisible(true);
                                }

                                if (counter.get() % 10 == 0) updateBossBarProgress(playerExtension.getPlayer(), meteorLocation, "&#8334eb&lEVENT METEORYT: #a06ee0Twoja ilość bloków do pokonania #bfe37d{blocks}", "METEORYT", bossBar, BarColor.PURPLE);
                                else updateBossBarProgress(playerExtension.getPlayer(), meteorLocation, "&#8334eb&lEVENT METEORYT: #a06ee0Meteoryt pojawił się na &#7d5af2X: {x} Y: {y} Z: {z}", "METEORYT", bossBar, BarColor.PURPLE);

                                counter.incrementAndGet();
                            }

                        });

                    }
                }.runTaskTimer(Main.getPlugin(), 0L, 20L);

                meteorLocation.getBlock().setType(Material.MAGMA_BLOCK);
                meteorLocation.getBlock().setMetadata("MeteorBlock", new FixedMetadataValue(Main.getPlugin(), Bukkit.getOnlinePlayers().size() * 50));
            } else if (args[0].equalsIgnoreCase("random")) {
                Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zrespiono losowy event!");
                List<String> stringList = Arrays.asList("event case " + RandomApi.randomElementList(Main.pluginEvents.getListCases().getCaseRandomList()), "event meteor", "pinata spawn spawn");
                String command = RandomApi.randomElementList(stringList);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }
    }

    private Location generateLocation(){
        int x = RandomApi.getInt(-2000, 2000);
        int z = RandomApi.getInt(-2000, 2000);

        World world = Bukkit.getWorlds().get(0);
        Block block = world.getHighestBlockAt(x, z);

        return block.getType().equals(Material.WATER) || block.getType().equals(Material.LAVA) ? generateLocation() : block.getLocation().add(0, 1, 0);
    }

    public void updateBossBarProgress(Player player, Location targetLocation, String titleBossBar, String typeEvent, BossBar bossBar, BarColor barColor) {
        Location playerLocation = player.getLocation();

        if (targetLocation.getWorld() != playerLocation.getWorld()) {
            bossBar.setTitle(Api.fixColor("#cc4b31&lEVENT " + typeEvent + ": #c4897eNie jesteś na odpowiednim świecie gdzie event występuje!"));
            bossBar.setColor(BarColor.RED);
            bossBar.setProgress(1.0);
            return;
        }

        double distance = targetLocation.distance(playerLocation);
        int blocksToGoal = (int) Math.ceil(distance);


        double maxDistance = 2500.0;
        double distanceToTarget = player.getLocation().distance(targetLocation);
        double progress = Math.max(0, 1 - (distanceToTarget / maxDistance));

        if (progress < 0.0) {
            progress = 0.0;
        } else if (progress > 1.0) {
            progress = 1.0;
        }

        bossBar.setTitle(Api.fixColor(titleBossBar)
                .replace("{blocks}", String.valueOf(blocksToGoal))
                .replace("{x}", String.valueOf(targetLocation.getBlockX()))
                .replace("{y}", String.valueOf(targetLocation.getBlockY()))
                .replace("{z}", String.valueOf(targetLocation.getBlockZ())));

        if (progress == 0) {
            bossBar.setProgress(1.0);
        } else bossBar.setProgress(progress);

        bossBar.setColor(barColor);
    }
}
