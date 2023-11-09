package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.TicketCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.Ticket;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TicketCommand extends Command implements Listener {
    public TicketCommand() {
        super("ticket", "/ticket <gracz> <kwota, check> <czas(2h, 1d, optional)>", "");
        setPermission("core.command.ticket");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        else if (args.length == 2) return Api.startsWith(Arrays.asList("1000", "10000", "check"), args[1]);
        else if (args.length == 3) return Api.startsWith(Arrays.asList("1h"), args[2]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
        if (args.length >= 3) {
            OfflinePlayer secondPlayer = Bukkit.getServer().getPlayer(args[0]);

            if (Api.isInt(args[1])) {
                int value = Integer.valueOf(args[1]);
                if (!args[2].isEmpty() || (TimerApi.getTime("1d") < TimerApi.getTime(args[2]))) {
                    Ticket ticketPlayer = TicketCache.getInstance().compute(secondPlayer.getUniqueId());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie nałożyłeś karę na gracza &e" + secondPlayer.getName() + "&a o kwocie &e" + value + "$ &ai czasie &e" + args[2]);
                    if (secondPlayer.isOnline()) {
                        System.out.println("online");
                        ticketPlayer.setOnline(1);
                        ((Player) secondPlayer).playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                        ((Player) secondPlayer).sendTitle(Api.fixColor("&#c91212&lMANDAT"), Api.fixColor("&8>> &aZostałeś ukarany o kwocie &#FFF88F" + value + " &#FFC42E$&a, czas na spłacenie &#ffd56c" + args[2] + " &#ffc942⌚ &8<<"));
                        ((Player) secondPlayer).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 50, false, false, false));
                        ((Player) secondPlayer).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 50, false, false, false));
                    } else {
                        System.out.println("offline");
                        ticketPlayer.setOnline(0);
                    }

                    ticketPlayer.setEnable(1);
                    ticketPlayer.setEnableJail(0);
                    ticketPlayer.setTime(0);
                    ticketPlayer.setTimeJail(0);
                    ticketPlayer.setTimeMaxJail(0);
                    ticketPlayer.setValue(value);
                    ticketPlayer.setOnlineTimeOut(0);
                    ticketPlayer.setMaxTime(TimerApi.getTime(String.valueOf(args[2])));
                } else wrongUsage();
            } else wrongUsage();
        } else if (args.length == 2) {
            OfflinePlayer secondPlayer = Bukkit.getServer().getPlayer(args[0]);

            if (args[1].equalsIgnoreCase("check")) {
                Ticket ticketPlayer = TicketCache.getInstance().compute(secondPlayer.getUniqueId());
                if (ticketPlayer.getEnable() == 1) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aGracz: " + secondPlayer.getName() + "&a, kara &e" + ticketPlayer.getValue() + "$&a, maksymalny czas na spłacenie: &e" + TimerApi.getDurationBreakdownShort(ticketPlayer.getMaxTime()) + "&a, czas upłynięty: &e" + TimerApi.getDurationBreakdownShort(ticketPlayer.getTime()));
                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cGracz nie posiada żadnego mandatu!");
            } else wrongUsage();
        } else wrongUsage();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Ticket ticketPlayer = TicketCache.getInstance().compute(player.getUniqueId());
        if (ticketPlayer.getEnable() == 1) {
            if (ticketPlayer.getOnline() == 0) {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 50, false, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 50, false, false, false));
                player.sendTitle(Api.fixColor("&#c91212&lMANDAT"), Api.fixColor("&8>> &aZostałeś ukarany w kwocie &#FFF88F" + ticketPlayer.getValue() + " &#FFC42E$&a, czas na spłacenie &#ffd56c" + TimerApi.getDurationBreakdownShort(ticketPlayer.getMaxTime()) + " &#ffc942⌚ &8<<"));
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        Ticket ticketPlayer = TicketCache.getInstance().compute(player.getUniqueId());
        if (ticketPlayer.getEnableJail() == 1) {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz używać żadnych komend będąc w więzieniu!");
            event.setCancelled(true);
        }
    }

}
