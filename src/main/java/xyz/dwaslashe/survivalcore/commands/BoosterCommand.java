package xyz.dwaslashe.survivalcore.commands;

import net.saidora.api.helpers.ItemHelper;
import net.saidora.economy.manager.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ItemApi;
import xyz.dwaslashe.survivalcore.utils.RandomApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import javax.print.DocFlavor;
import java.text.DecimalFormat;
import java.util.*;

public class BoosterCommand extends Command implements Listener {
    public BoosterCommand() {
        super("booster", "/booster <global, remove> <mobcoin, speed, exp, haste> <multiplication> <time(max 1d)>", "");
        setPermission("core.command.booster");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("global", "remove"), args[0]);
        else if (args.length == 2) return Api.startsWith(Arrays.asList("mobcoin", "speed", "exp", "haste"), args[1]);
        else if (args.length == 3) return Api.startsWith(Arrays.asList("2", "3"), args[2]);
        else if (args.length == 4) return Api.startsWith(Arrays.asList("1h"), args[3]);
        else if (args.length == 5) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("global")) {
            if (args.length >= 5) {
                if (args[1].equalsIgnoreCase("mobcoin") || args[1].equalsIgnoreCase("speed") || args[1].equalsIgnoreCase("exp") || args[1].equalsIgnoreCase("haste")) {
                    if (Api.isInt(args[2])) {
                        if (!args[3].isEmpty() || (TimerApi.getTime("1d") < TimerApi.getTime(args[3]))) {
                            Player secondPlayer = Bukkit.getServer().getPlayer(args[4]);
                            if (secondPlayer != null) {
                                if (args[1].equalsIgnoreCase("mobcoin")) {
                                    Main.getPlugin().getLogger().info("[BOOSTER] MOBCOIN, " + TimerApi.getTime(args[3]) + ", " + secondPlayer + ", " + Integer.valueOf(args[2]));
                                    boosterExecute("MOBCOIN", TimerApi.getTime(args[3]), secondPlayer, Integer.valueOf(args[2]));
                                } else if (args[1].equalsIgnoreCase("speed")) {
                                    Main.getPlugin().getLogger().info("[BOOSTER] SPEED, " + TimerApi.getTime(args[3]) + ", " + secondPlayer + ", " + Integer.valueOf(args[2]));
                                    boosterExecute("SPEED", TimerApi.getTime(args[3]), secondPlayer, Integer.valueOf(args[2]));
                                } else if (args[1].equalsIgnoreCase("exp")) {
                                    Main.getPlugin().getLogger().info("[BOOSTER] EXP, " + TimerApi.getTime(args[3]) + ", " + secondPlayer + ", " + Integer.valueOf(args[2]));
                                    boosterExecute("EXP", TimerApi.getTime(args[3]), secondPlayer, Integer.valueOf(args[2]));
                                } else if (args[1].equalsIgnoreCase("haste")) {
                                    Main.getPlugin().getLogger().info("[BOOSTER] HASTE, " + TimerApi.getTime(args[3]) + ", " + secondPlayer + ", " + Integer.valueOf(args[2]));
                                    boosterExecute("HASTE", TimerApi.getTime(args[3]), secondPlayer, Integer.valueOf(args[2]));
                                }
                            } else offlinePlayer();
                        } else wrongUsage();
                    } else wrongUsage();
                } else wrongUsage();
            } else wrongUsage();
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (args.length >= 3) {
                if (booleanHashMap.containsKey(args[1])) {
                    if (booleanHashMap.get(args[1]) == Integer.valueOf(args[2])) {
                        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Pomyślnie usunąłeś ulepszenie!");
                        booleanHashMap.remove(args[1]);
                    } else wrongUsage();
                } else wrongUsage();
            } else wrongUsage();
        }
    }

    public static HashMap<String, Integer> booleanHashMap = new HashMap<>();
    public static void boosterExecute(String type, long time, Player player, int multiplication) {
        if (booleanHashMap.containsKey(type)) {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz tego zrobić ponieważ te ulepszenie trwa na serwerze!");
            return;
        }

        booleanHashMap.put(type, multiplication);
        long[] taskTime = {0};
        long[] barTime = {time};
        Api.sendBroadcast("\n        &#3a92f0&lGLOBALNE ULEPSZENIE\n \n&8>> &#bcd8ebGracz &#FFC42E" + player.getName() + " &#bcd8ebaktywował ulepszenie &#75e810&l" + type + " &#a6fc5b" + multiplication + "x\n&8>> &#bcd8ebNa czas &#ffd56c" + TimerApi.getDurationBreakdownShort(barTime[0]) + " &fᎠ \n ");
        BossBar bar = Bukkit.createBossBar(Api.fixColor("&8>> &#bcd8ebUlepszenie &#75e810&l" + type + " &#a6fc5b" + multiplication + "x &#bcd8ebod &#e8af10" + player.getName() + " &#bcd8ebbędzie trwać jeszcze &#ffd56c" + TimerApi.getDurationBreakdownShort(barTime[0]) + " &fᎠ &8<<"), BarColor.BLUE, BarStyle.SEGMENTED_10, new BarFlag[0]);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (taskTime[0] < time) {
                    if (!booleanHashMap.containsKey(type)) return;

                    taskTime[0] = taskTime[0] + 1000L;

                    barTime[0] -= 1000L;

                    bar.setTitle(Api.fixColor("&8>> &#bcd8ebUlepszenie &#75e810&l" + type + " &#a6fc5b" + multiplication + "x &#bcd8ebod &#e8af10" + player.getName() + " &#bcd8ebbędzie trwać jeszcze &#ffd56c" + TimerApi.getDurationBreakdownShort(barTime[0]) + " &fᎠ &8<<"));
                    bar.setProgress(Api.mapLongToDouble(barTime[0], 0L, time));

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        bar.addPlayer(all);
                        if (type.equals("SPEED")) {
                            //float speed = (float) (0.20000000298023224D * multiplication);
                            //if (multiplication > 5) {
                            //    all.setWalkSpeed((float) (0.20000000298023224D * 5));
                            //} else all.setWalkSpeed(speed);
                            all.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, (multiplication - 1)));
                        } else if (type.equals("HASTE")) {
                            all.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, (multiplication - 1)));
                        }
                    }
                } else {
                    booleanHashMap.remove(type);

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        bar.setVisible(false);
                        bar.removePlayer(all);
                        if (type.equals("SPEED")) {
                            all.setWalkSpeed((float) 0.20000000298023224D);
                        } else if (type.equals("HASTE")) {
                            all.removePotionEffect(PotionEffectType.FAST_DIGGING);
                        }
                    }

                    if (player.isOnline()) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Twoje ulepszenie zostało wyłączone!");
                    }

                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getPlugin(), 0L, 20L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (booleanHashMap.containsKey("SPEED")) {
            event.getPlayer().setWalkSpeed((float) 0.20000000298023224D);
        } else if (booleanHashMap.containsKey("HASTE")) {
            event.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (booleanHashMap.containsKey("MOBCOIN")) {
            if (event.getEntity() instanceof Mob) {
                ItemStack mobCoin = ItemHelper.edit(new ItemStack(Material.ENDER_EYE)).editNbtTagCompound(nbtItem -> {
                    nbtItem.setBoolean("MobCoin", true);
                }).editItemMeta(ItemMeta.class, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&6Mob Coin"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &fJest to waluta, która dropi od zabicia moba przez", " &fgracza. Waluta możesz ulepszać spawner czy zmieniać mob!", "")));
                }).getItemStack();

                if (RandomApi.getChance(2.0 * booleanHashMap.get("MOBCOIN") - 2.0)) {
                    event.getDrops().add(mobCoin);
                }
            }
        }
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        if (booleanHashMap.containsKey("EXP")) {
            event.setAmount(event.getAmount() * booleanHashMap.get("EXP"));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (event.getMaterial() == Material.PAPER && event.getAction().equals(Action.RIGHT_CLICK_AIR) && Objects.equals(event.getHand(), EquipmentSlot.HAND)) {
            ItemHelper itemHelper = ItemHelper.edit(itemInHand);
            itemHelper.editNbtTagCompound(nbtItem -> {
                if (nbtItem.hasKey("booster")) {


                    float amount = Float.parseFloat(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(1)).replace('$', ' ').replace("Wartość:", " "));

                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie włączyłeś ulepszenie!");
                    player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                }
            });
        }
    }
}