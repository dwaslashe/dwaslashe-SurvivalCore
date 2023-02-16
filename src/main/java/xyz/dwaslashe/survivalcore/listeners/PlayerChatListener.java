package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Maps;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.configs.PluginConfig;
import xyz.dwaslashe.survivalcore.enums.ColorEnums;
import xyz.dwaslashe.survivalcore.helpers.DiscordHelper;
import xyz.dwaslashe.survivalcore.helpers.IconHelper;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.awt.*;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public class PlayerChatListener implements Listener {

    private static final Pattern colorPattern = Pattern.compile("&[a-fA-F0-9]|&#[A-Fa-f0-9]{6}");
    private static final Pattern pingPattern = Pattern.compile("<@[0-9]+|@everyone|@here");

    ZonedDateTime timeZone = TimerApi.getZoneDate("GMT+1");

    protected static final Map<Player, Long> delay = Maps.newHashMap();

    protected String appendDigit(int i) {
        return i <= 9 ? "0" + i : i + "";
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handleAsyncChatEvent(AsyncChatEvent event){

        event.setCancelled(true);

        Player player = event.getPlayer();

        var ref = new Object() {
            String message = event.signedMessage().message();
            String color = ChatApi.getSuffix(player);
        };

        String f = "<#f47e07>@", split = ref.message.split(f)[0];
        colorPattern.matcher(split).results().forEach(matchResult -> ref.color = matchResult.group());

        for (Player everyPlayer : Bukkit.getOnlinePlayers()) {
            if (ref.message.contains(everyPlayer.getName())) {

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<hover:show_text:\"&fKliknij w oznaczoną wiadomość, aby\n&fnapisać do użytkownika <#f47e07>");
                stringBuilder.append(everyPlayer.getName());
                stringBuilder.append("\n\n&aPiekny czas twojej starej chuj ci w cyce: ")
                        .append(appendDigit(timeZone.getHour()))
                        .append(":").append(appendDigit(timeZone.getMinute()));
                stringBuilder.append("\"><click:suggest_command:/msg ");
                stringBuilder.append(everyPlayer.getName());
                stringBuilder.append(">");
                stringBuilder.append(f);
                stringBuilder.append(everyPlayer.getName());
                stringBuilder.append(ref.color.contains("#") ? ref.color.replace("&", "<") + ">" : ref.color);
                stringBuilder.append("</click></hover>");

                ref.message = ref.message.replace(everyPlayer.getName(), stringBuilder.toString());
            }
        }

        String finalMessage = ref.message;
        Component component = MiniMessage.miniMessage().deserialize(ColorEnums.translateAlternateColorCodes(format(player)
                        .replace("§", "&")
                        .replace(">>", "»")
                        .replace("<<", "«")),
                StandardTags.color(), StandardTags.rainbow(), StandardTags.gradient(),
                StandardTags.hoverEvent(),
                TagResolver.resolver("message", (argumentQueue, context) -> Tag.inserting(player.hasPermission("core.chat.color") ?
                        MiniMessage.miniMessage().deserialize(ColorEnums.translateAlternateColorCodes(finalMessage), StandardTags.color(), StandardTags.gradient(), StandardTags.rainbow()) : MiniMessage.miniMessage().deserialize(finalMessage))));

        event.message(component);

        Bukkit.getOnlinePlayers().forEach(p -> Main.getPlugin().getAudience().sender(p).sendMessage(component));


        if (Main.pluginConfig.getWebhook().isEnable_chat()) {
            DiscordHelper discordHelper = new DiscordHelper(Main.pluginConfig.getWebhook().getWebhook_chat());
            discordHelper.setUsername(player.getName());
            discordHelper.setAvatarUrl("https://minotar.net/avatar/" + player.getName());
            pingPattern.matcher(ref.message).results().forEach(matchResult -> ref.message = ref.message.replace(matchResult.group(), ""));
            discordHelper.setContent(ref.message);
            try {
                discordHelper.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        // Cooldown chat player
        if (delay.containsKey(player) && delay.get(player) > System.currentTimeMillis()) {
            Api.sendMessage(player, Main.pluginConfig.getCooldown().getMessage().replace("{TIME}", TimerApi.secondsToString(delay.get(player))));
            e.setCancelled(true);
            return;
        }

        delay.remove(player);

        if (player.hasPermission("core.chat.cooldown.bypass")) e.setCancelled(false);
        else delay.put(player, TimerApi.parseDateDiff(Main.pluginConfig.getCooldown().getTime(), true));

    }


    private static Pattern hexPattern = Pattern.compile("&#[A-F0-9a-f]{6}");

    private String format(Player player) {
        var ref = new Object() {
            String replacedFormat = Main.pluginConfig.getChat().getFormat().replace("{WORLD}", player.getWorld().getName()).replace("{PREFIX}", ChatApi.getPrefix(player)).replace("{PLAYER}", player.getDisplayName()).replace("{SUFFIX}", ChatApi.getSuffix(player));
        };
        ref.replacedFormat = ref.replacedFormat.replace("§", "&");
        hexPattern.matcher(ref.replacedFormat).results().forEach(matchResult -> {
            ref.replacedFormat = ref.replacedFormat.replace(matchResult.group(), "<" + matchResult.group().replace("&", "")+ ">");
        });
        return IconHelper.transformIcons(PlaceholderAPI.setPlaceholders(player, ref.replacedFormat), PluginConfig.IMAGES_CHAT);
    }
}
