package xyz.dwaslashe.survivalcore.commands;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.DiscordHelper;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EmergencyNumberCommand extends Command {
    public EmergencyNumberCommand() {
        super("numeralarmowy", "/112 <wiadomość>", "", "112", "numeralarmowy");
        setOnlyPlayer(true);
    }

    protected static final Map<Player, Long> delayHook = Maps.newHashMap();
    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            wrongUsage();
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPrzykład: &e/112 gracz Marcin123 ma postawioną widoczną farme marihuany na kordach, których jestem");
        } else if (args.length >= 1) {
            if (delayHook.containsKey(player) && delayHook.get(player) > System.currentTimeMillis()) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cAby wywołać powiadomienie do służb specjalnych musisz poczekać &e{TIME}".replace("{TIME}", TimerApi.secondsToString(delayHook.get(player))));
                player.closeInventory();
                return;
            }
            delayHook.remove(player);

            String message = StringUtils.join(args, " ", 0, args.length);

            if(message.isEmpty()) {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cWiadomość nie może być pusta");
                return;
            }

            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wysłano zgłoszenie do służb specjalnych. W swoim wolnym czasie służby to sprawdzą!");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            for (Player allPlayer : Bukkit.getOnlinePlayers()) {
                if (allPlayer.hasPermission("core.command.emergencynumber.bypass")) {
                    allPlayer.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 1, 1);
                    Api.sendMessage(allPlayer, "");
                    Api.sendMessage(allPlayer, "        &#004791&lNUMBER ALARMOWY - FBI");
                    Api.sendMessage(allPlayer, "");
                    Api.sendMessage(allPlayer, "&8>> &#8dfa52Zgłaszający: &#46b9f2" + player.getName());
                    Api.sendMessage(allPlayer, "&8>> &#8dfa52Wiadomość: &#4795e6'" + message + "'");
                    Api.sendMessage(allPlayer, "&8>> &#8dfa52Kordy: &#47e65aX: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ());
                    Api.sendMessage(allPlayer, "");
                }
            }

            DiscordHelper discordHelper = new DiscordHelper(Main.pluginConfig.getWebhook().getWebhook_chat());
            discordHelper.setUsername(player.getName() + " (NUMER ALARMOWY)");
            discordHelper.setAvatarUrl("https://minotar.net/avatar/" + player.getName());
            discordHelper.setContent("Gracz **" + player.getName() + "** wysłał zgłoszenie o wiadomości: **'" + message + "'**, kordy gracza **X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockX() + "**");
            try {
                discordHelper.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            delayHook.put(player, TimerApi.parseDateDiff("5m", true));
        }
    }
}
