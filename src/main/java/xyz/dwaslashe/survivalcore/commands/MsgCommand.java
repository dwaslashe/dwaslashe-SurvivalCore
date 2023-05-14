package xyz.dwaslashe.survivalcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.User;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MsgCommand extends Command {
    static HashMap<Player, Player> lastMsg = new HashMap();
    public MsgCommand() {
        super("msg", "/msg <nick> <tresc>", "", "tell", "whisper");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length < 2) {
            wrongUsage();
        } else if (args.length > 1) {
            Player p2 = Bukkit.getPlayer(args[0]);
            if (p2 == null) {
                offlinePlayer();
                return;
            }
            String msg = StringUtils.join(args, " ", 1, args.length);

            if(msg.isEmpty()) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cWiadomość nie może być pusta");
                return;
            }

            lastMsg.put(p, p2);
            lastMsg.put(p2, p);

            if (IgnoreCommand.blockMsg.contains(p2)) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz wysłać wiadomości ponieważ dana osoba wyłączyła wysyłanie prywatnych wiadomości!");
                return;
            }

            if (IgnoreCommand.ignoreMsg.containsValue(p) || IgnoreCommand.ignoreMsg.containsValue(p2)) {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz wysłać wiadomości ponieważ dana osoba Cię wyciszyła!");
                return;
            }

            SocialSpyCommand.getList()
                    .stream()
                    .map(o -> o = Bukkit.getPlayer((String) o))
                    .filter(Objects::nonNull)
                    .forEach(po -> {
                        ((Player) po).sendMessage(Api.fixColor("&c&lSocialSPY &8[ &#B3F003" + p.getDisplayName() + " &8> &#B3F003" + p2.getDisplayName() + " &8] &8» &#E7E7E7" + msg));
                    });

            Api.sendMessage(p, "&8[ &#B3F003TY &8> &#B3F003" + p2.getDisplayName() + " &8] &8» &#E7E7E7" + msg);
            Api.sendMessage(p2, "&8[ &#B3F003" + p.getDisplayName() + " &8> &#B3F003TY &8] &8» &#E7E7E7" + msg);
            User userPlayer = UserCache.getInstance().compute(p.getUniqueId());
            User userPlayer2 = UserCache.getInstance().compute(p2.getUniqueId());
            if (userPlayer.getMsgbossbar() == 0) {
                BossBar bar = Bukkit.createBossBar(Api.fixColor("&8[ &#B3F003" + p.getDisplayName() + " &8> &#B3F003TY &8] &8» &#E7E7E7" + msg), BarColor.GREEN, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                if (userPlayer2.getMsgbossbar() == 0) {
                    bar.addPlayer(p2);
                }
                bar.setProgress(1);
                int[] bar_color = {0};
                Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        if (p.getPlayer() != null && p.getPlayer().isOnline()) {
                            if (bar.getProgress() > 0.02) {
                                bar.setProgress(bar.getProgress() - 0.02);
                                ++bar_color[0];
                                if (bar_color[0] == 1) {
                                    bar.setColor(BarColor.GREEN);
                                } else {
                                }
                            } else if (userPlayer2.getMsgbossbar() == 0) {
                                bar.setVisible(false);
                                bar.removePlayer(p2.getPlayer());
                            }
                        } else if (userPlayer2.getMsgbossbar() == 0) {
                            bar.removePlayer(p2.getPlayer());
                        }
                    }
                }, 0, 2);
            }
        }
    }

    public static HashMap<Player, Player> getLastMsg() {
        return lastMsg;
    }

}