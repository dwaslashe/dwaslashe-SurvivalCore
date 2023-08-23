package xyz.dwaslashe.survivalcore.listeners;

import com.google.common.collect.Maps;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.axerr.nicknamer.AxerrNicknamer;
import me.axerr.nicknamer.user.UserManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.intellij.lang.annotations.Subst;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.commands.ChatCommand;
import xyz.dwaslashe.survivalcore.configs.PluginConfig;
import xyz.dwaslashe.survivalcore.enums.ColorEnums;
import xyz.dwaslashe.survivalcore.helpers.DiscordHelper;
import xyz.dwaslashe.survivalcore.helpers.IconHelper;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.ChatApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class PlayerChatListener implements Listener {

    private static final Pattern colorPattern = Pattern.compile("&[a-fA-F0-9]|&#[A-Fa-f0-9]{6}");
    private static final Pattern pingPattern = Pattern.compile("<@[0-9]+|@everyone|@here");

    protected static HashMap<Player, Component> previousMessages = new HashMap<>();

    protected static final Map<Player, Long> delay = Maps.newHashMap();

    protected String appendDigit(int i) {
        return i <= 9 ? "0" + i : i + "";
    }

    private static final Set<BiFunction<String, AsyncChatEvent, Boolean>> events = new HashSet<>();

    static {
        events.add((s, event) -> {
            List<String> wordsInMessage = Arrays.asList(s.split(" "));
            if (Main.pluginConfig.getEvents().isBlockwords()) {
                for (String word : Main.pluginConfig.getChat().getBlockwords().getWords()) {
                    if (wordsInMessage.contains(word.toLowerCase())) {
                        if (event.getPlayer().hasPermission("core.chat.block.bypass")) {
                            break;
                        }
                        Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Main.pluginConfig.getChat().getBlockwords().getCommand().replace("{PLAYER}", event.getPlayer().getName()));
                        });
                        return false;
                    }
                }
            }
           return true;
        });
        events.add((s, event) -> {
            if (ChatCommand.disablechat.get(ChatCommand.TYPE.AVAILABLE)) {
                if (!event.getPlayer().hasPermission("core.chat.bypass")) {
                    Api.sendMessage(event.getPlayer(), Main.pluginConfig.getMessages().getPrefix() + "&cCzat jest wyłączony!");
                    return false;
                }
            }
            return true;
        });
        events.add((s, event) -> {
            Player player = event.getPlayer();
            if (Main.pluginConfig.getEvents().isCooldownchat()) {
                if (delay.containsKey(player) && delay.get(player) > System.currentTimeMillis()) {
                    Api.sendMessage(player, Main.pluginConfig.getCooldown().getMessage().replace("{TIME}", TimerApi.secondsToString(delay.get(player))));
                    return false;
                }
                //Continue cooldown chat player

                delay.remove(player);

                if (!player.hasPermission("core.chat.cooldown.bypass")) {
                    delay.put(player, TimerApi.parseDateDiff(Main.pluginConfig.getCooldown().getTime(), true));
                }
            }
            return true;
        });
        events.add((s, event) -> {
            if (event.signedMessage().message().trim().startsWith("@")) {
                return false;
            }
            return true;
        });
        events.add((s, event) -> {
            Player player = event.getPlayer();
            //Check if player send twice same message
            if (Main.pluginConfig.getEvents().isSamemessagesend()) {
                if (!player.hasPermission("core.chat.samemessage.bypass")) {
                    if (previousMessages.containsKey(player)) {
                        if (event.message().equals(previousMessages.get(player))) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz wysłać znowu takiej samej wiadomości!");
                            return false;
                        }
                    }
                    previousMessages.put(player, event.message());
                }
            }
            return true;
        });
    }

    MiniMessage messageBuilder = MiniMessage.builder().tags(TagResolver.builder().build()).build();

    @EventHandler(priority = EventPriority.HIGH)
    public void handleAsyncChatEvent(AsyncChatEvent event){
        event.setCancelled(true);
        Player player = event.getPlayer();

        var ref = new Object() {
            String message = event.signedMessage().message();
            String color = null;
            boolean close = false;
        };

        String f = "<#f47e07>@";

        Component message$component;

        List<TagResolver> resolvers = new ArrayList<>();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (ref.message.contains(onlinePlayer.getName())) {
                onlinePlayer.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2, 2);

                String name = onlinePlayer.getName().toLowerCase();
                ref.message = ref.message.replace(onlinePlayer.getName(), "<mention-" + name + ">");
                String finalMessage = ref.message;

                resolvers.add(TagResolver.builder().tag("mention-" + name,

                                (argumentQueue, context) -> {

                                    if (ref.color == null) {
                                        String split = finalMessage.split(f)[0];
                                        colorPattern.matcher(split).results().forEach(matchResult -> ref.color = matchResult.group());
                                    }

                                    if (ref.color == null) {
                                        ref.color = "&f";
                                    }
                                    ZonedDateTime timeZone = TimerApi.getZoneDate("GMT+1");
                                    Component component = MiniMessage.miniMessage().deserialize(
                                            "<hover:show_text:\" <#F79B2E>Oznaczenie <#f47e07>@" + onlinePlayer.getName() + "\n\n <#E7E7E7>Kliknij w oznaczoną wiadomość, aby\n <#E7E7E7>napisać do użytkownika\n\n <#77d916>Wiadomość wysłana: <#ffd56c>" + appendDigit(timeZone.getHour()) + ":" + appendDigit(timeZone.getMinute()) +  " <#ffc942>⌚\"><click:suggest_command:/msg " + onlinePlayer.getName() + " >" + f + onlinePlayer.getName() + "</click></hover>" + ColorEnums.translateAlternateColorCodes(ref.color));
                                    return Tag.inserting(Component.empty().append(component));

                                }

                        )

                        .build());
            }
        }


        //Create message player and send
        Component component;

        ItemStack itemStack = player.getItemInHand();

        TagResolver item = TagResolver.builder().tag("item", (argumentQueue, context) -> Tag.inserting(Component.empty().append(MiniMessage.miniMessage().deserialize(ColorEnums.translateAlternateColorCodes("<show_item><#1ff0a0>[" + itemStack.getI18NDisplayName() + "<#1ff0a0>]</show_item>"), TagResolver.resolver("show_item", (argumentQueue1, context1) -> Tag.styling(b -> b.hoverEvent(player.getItemInHand().asHoverEvent()))))))).build();

        resolvers.add(item);

        if (player.hasPermission("core.chat.admin")) {
            resolvers.add(StandardTags.color());
            resolvers.add(StandardTags.gradient());
            resolvers.add(StandardTags.rainbow());
            resolvers.add(StandardTags.hoverEvent());
            resolvers.add(StandardTags.clickEvent());
        } else if (player.hasPermission("core.chat.color")) {
            resolvers.add(StandardTags.color());
            resolvers.add(StandardTags.gradient());
        } else if (player.hasPermission("core.chat.rainbow")) {
            resolvers.add(StandardTags.rainbow());
        }

        message$component = messageBuilder.deserialize(ref.message, resolvers.toArray(new TagResolver[0]));

        component = messageBuilder.deserialize(ColorEnums.translateAlternateColorCodes(format(player)
                        .replace("§", "&")
                        .replace(">>", "»")
                        .replace("<<", "«")),
                StandardTags.color(),
                StandardTags.gradient(),
                StandardTags.rainbow(),
                StandardTags.hoverEvent(),
                StandardTags.clickEvent(),
                StandardTags.decorations(),
                StandardTags.reset(),
                TagResolver.resolver("message", Tag.inserting(message$component)));


        Bukkit.getConsoleSender().sendMessage(Component.text("[CHAT] ").append(component));

        Component finalComponent = component;

        event.message(finalComponent);


        //Function boolean to send message
        for (BiFunction<String, AsyncChatEvent, Boolean> stringAsyncChatEventBooleanBiFunction : events) {
            if(!ref.close) {
                ref.close = !stringAsyncChatEventBooleanBiFunction.apply(ref.message, event);
            } else break;
        }

        if(ref.close) return;


        //Send message to all players
        User user = UserCache.getInstance().compute(event.getPlayer().getUniqueId());

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (user.getChat() == 1) {
                Api.sendMessage(event.getPlayer(), Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz wysłać wiadomości ponieważ wyłączyłeś wiadomości na czacie!");
            }
            Api.sendMessage(all, event.message());
        }
    }

    private static Pattern hexPattern = Pattern.compile("&#[A-F0-9a-f]{6}");

    private String format(Player player) {

        UserManager userManager = AxerrNicknamer.getUserManager();
        String realName;
        if (userManager.getUser(player.getName()) == null) {
            realName = player.getName();
        } else realName = userManager.getUser(player.getName()).getCustomName();
        String finalRealName = PlaceholderAPI.setPlaceholders(player, "<hover:show_text:\" <#4287f5>Statystyki gracza <#9c9898>" + realName + "\n \n <#E7E7E7>Saldo: <#FFF88F>%economy_money% <#FFC42E>$\n <#E7E7E7>Śmierci: <#ff6e6e>%statistic_deaths% <#ff4545>☠\n <#E7E7E7>Zabójstwa: <#4DFFFF>%statistic_player_kills% <#1AE6E6>⚔\n <#E7E7E7>Przegrane godziny: <#ffd56c>%statistic_hours_played%g <#ffc942>⌚\n <#E7E7E7>Wykopane bloki: <#10F70C>%statistic_mine_block% <#09b106>⛏\n <#E7E7E7>Punkty rankingu: <#4eed6e>%mineteams_profile_ranking%pkt\n <#E7E7E7>Ilość powitanych nowych graczy: <#8eeb6c>%Greeter_amount%\n <#E7E7E7>Średnia ocena profilu: %survivalcore_rate%&8/<#54f542>5\n\"><click:suggest_command:/msg " + player.getName() + " >" + realName + "</click></hover>");

        String message;
        if (player.hasPermission("core.chat.rainbow")) {
            message = "<rainbow><message>";
        } else message = "<message>";

        String finalMessage = message;

        var ref = new Object() {
            String replacedFormat = Main.pluginConfig.getChat().getFormat().replace("{WORLD}", player.getWorld().getName()).replace("{PREFIX}", ChatApi.getPrefix(player)).replace("{PLAYER}", finalRealName).replace("{SUFFIX}", ChatApi.getSuffix(player)).replace("{MESSAGE}", finalMessage);
        };
        ref.replacedFormat = ref.replacedFormat.replace("§", "&");
        hexPattern.matcher(ref.replacedFormat).results().forEach(matchResult -> {
            ref.replacedFormat = ref.replacedFormat.replace(matchResult.group(), "<" + matchResult.group()
                    .replace("&", "")+ ">");
        });

        return IconHelper.transformIcons(PlaceholderAPI.setPlaceholders(player, ref.replacedFormat), PluginConfig.IMAGES_CHAT);
    }
}
