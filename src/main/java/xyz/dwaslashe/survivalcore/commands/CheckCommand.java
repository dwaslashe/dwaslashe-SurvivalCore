package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.WarpCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.listeners.RegionListener;
import xyz.dwaslashe.survivalcore.objects.Warp;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CheckCommand extends Command implements Listener {
    private static List<String> cmds = Arrays.asList("/rl", "/sprawdz", "/helpop", "/pomoc", "/msg", "/replay", "/message", "/tell", "/r", "/scoreboard", "/sidebar");
    public static List<String> checks = new ArrayList();
    public CheckCommand() {
        super("sprawdz", "/sprawdz <gracz> <info, czysty, wykryto, przyznanie>", "", "check");
        setPermission("core.command.check");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 2) return Api.startsWith(Arrays.asList("info", "czysty", "wykryto", "przyznanie"), args[1]);
        else if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            wrongUsage();
        } else if(args.length >= 1) {
            if (Bukkit.getPlayer(args[0]) == null) {
                offlinePlayer();
            } else {
                Player player = Bukkit.getPlayer(args[0]);
                if (args.length == 1) {
                    if (!checks.contains(player.getName())) {
                        checks.add(player.getName());
                        Api.sendBroadcast("\n        &#FF3131&lSPRAWDZANY\n \n&8>> &#8dfa52Gracz &#FFC42E" + player.getName() + " &#8dfa52jest sprawdzany przez &#f7482d" + sender.getName() + " \n ");
                        Api.sendMessage(player, "");
                        Api.sendMessage(player, "        &#FF3131&lJESTEŚ SPRAWDZANY!");
                        Api.sendMessage(player, "");
                        Api.sendMessage(player, "&8>> &#E7E7E7Logout &8= &#FFC42Eban 5d");
                        Api.sendMessage(player, "&8>> &#E7E7E7Przyznanie sie &8= &#FFC42Eban 2d");
                        Api.sendMessage(player, "&8>> &#E7E7E7Wykrycie &8= &#FFC42Eban 5d");
                        Api.sendMessage(player, "");
                        Api.sendMessage(player, "&8>> &#FF3131Pamietaj, aby się słuchać administratora, inaczej zostaniesz ukarany!");
                        Api.sendMessage(player, "");

                        Warp warp = WarpCache.getInstance().get("sprawdzarka");
                        if (warp != null) {
                            Location loc = new Location(Bukkit.getServer().getWorld(warp.getLocation().getWorld().getKey()), warp.getLocation().getX(), warp.getLocation().getY(), warp.getLocation().getZ(), warp.getLocation().getYaw(), warp.getLocation().getPitch());
                            player.teleport(loc);
                        } else Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aNie ma ustawionej lokalizacji &esprawdzarki");

                        (new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (checks.contains(player.getName())) {
                                    Api.sendActionBar(player, "&8>> <#f00c0c>Jesteś sprawdzany przez <#FDBD01>" + sender.getName() + " &8<<");
                                } else {
                                    this.cancel();
                                }
                            }
                        }).runTaskTimer(Main.getPlugin(), 0, 20);
                    } else {
                        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cGracz &e" + player.getName() + " &cjest już sprawdzany");
                    }
                } else if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("info")) {
                        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aGracz &e" + player.getName() + " &a" + (checks.contains(player.getName()) ? "jest sprawdzany" : "nie jest sprawdzany"));
                    } else if (args[1].equalsIgnoreCase("przyznanie")) {
                        if (checks.contains(player.getName())) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getName() + " 2d przyznanie się do używania niedozwolonego oprogramowania");
                            checks.remove(player.getName());
                        } else {
                            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cGracz &e" + player.getName() + " &cnie jest sprawdzany");
                        }
                    } else if (args[1].equalsIgnoreCase("wykryto")) {
                        if (checks.contains(player.getName())) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getName() + " 5d wykryto niedozwolone oprogramowanie");
                            checks.remove(player.getName());
                        } else {
                            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cGracz &e" + player.getName() + " &cnie jest sprawdzany");
                        }
                    } else if (args[1].equalsIgnoreCase("czysty")) {
                        if (checks.contains(player.getName())) {
                            Warp warp = WarpCache.getInstance().get("spawn");
                            player.teleport(warp.getLocation());
                            checks.remove(player.getName());
                            Api.sendBroadcast(Main.pluginConfig.getMessages().getPrefix() + "&aGracz &e" + player.getName() + " &aokazał sie nie winny, gdyż nie posiada niedozwolonego oprogramowania");
                        } else {
                            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cGracz &e" + player.getName() + " &cnie jest sprawdzany");
                        }
                    } else {
                        wrongUsage();
                    }
                } else {
                    wrongUsage();
                }
            }
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        String name = e.getPlayer().getName();
        if(checks.contains(name)){
            e.setCancelled(true);
            Api.sendMessage(e.getPlayer(), Main.pluginConfig.getMessages().getPrefix() + "&cPodczas sprawdzania dozwolone jest tylko używanie komend &e" + cmds.toString());
        }
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        String name = e.getPlayer().getName();
        if(checks.contains(name)){
            if(!cmds.contains(e.getMessage().split(" ")[0])){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onLogout(PlayerQuitEvent e){
        String name = e.getPlayer().getName();
        if(checks.contains(name)){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + e.getPlayer().getName() + " 5d Logout podczas sprawdzania");
        }
    }
}
