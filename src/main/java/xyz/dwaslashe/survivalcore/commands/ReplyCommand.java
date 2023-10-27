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

import java.util.List;
import java.util.Objects;

public class ReplyCommand extends Command {
    public ReplyCommand() {
        super("reply", "/reply <tresc>", "", "r");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length < 1) {
            wrongUsage();
        } else {
            if (args.length > 0) {
                Player secondPlayer = MsgCommand.getLastMsg().get(player);
                if (secondPlayer == null) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz komu odpisać");
                    return;
                }

                String msg = StringUtils.join(args, " ", 0, args.length);
                User userPlayer = UserCache.getInstance().compute(player.getUniqueId());
                User userSecondPlayer = UserCache.getInstance().compute(secondPlayer.getUniqueId());

                if (userSecondPlayer.getIgnoreAllPlayers() == 1) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz wysłać wiadomości ponieważ dana osoba wyłączyła wysyłanie prywatnych wiadomości!");
                    return;
                }

                if (MsgCommand.getInputPlayer(secondPlayer.getName(), userPlayer.getIgnorePlayers()) || MsgCommand.getInputPlayer(player.getName(), userSecondPlayer.getIgnorePlayers())) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz wysłać wiadomości ponieważ dana osoba Cię wyciszyła lub ją wyciszyłeś!");
                    return;
                }

                MsgCommand.getLastMsg().put(player, secondPlayer);
                MsgCommand.getLastMsg().put(secondPlayer, player);
                SocialSpyCommand.getList()
                       .stream()
                        .map(o -> o = Bukkit.getPlayer((String) o))
                        .filter(Objects::nonNull)
                        .forEach(po -> {
                            ((Player) po).sendMessage(Api.fixColor("&c&lSocialSPY &8[ &#B3F003" + player.getDisplayName() + " &8> &#B3F003" + secondPlayer.getDisplayName() + " &8] &8» &#E7E7E7" + msg));
                        });

                Api.sendMessage(player, "&8[ &#B3F003TY &8> &#B3F003" + secondPlayer.getDisplayName() + " &8] &8» &#E7E7E7" + msg);
                Api.sendMessage(secondPlayer, "&8[ &#B3F003" + player.getDisplayName() + " &8> &#B3F003TY &8] &8» &#E7E7E7" + msg);
                //if (userPlayer.getMsgBossBar() == 0) {
                //    BossBar bar = Bukkit.createBossBar(Api.fixColor("&8[ &#B3F003" + player.getDisplayName() + " &8> &#B3F003TY &8] &8» &#E7E7E7" + msg), BarColor.GREEN, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                //    bar.addPlayer(secondPlayer);
                //    bar.setProgress(1);
                //    int[] bar_color = {0};
                //    Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
                //        @Override
                //        public void run() {
                //            if (player.getPlayer() != null && player.getPlayer().isOnline()) {
                //                if (bar.getProgress() > 0.02) {
                //                    bar.setProgress(bar.getProgress() - 0.02);
                //                    ++bar_color[0];
                //                    if (bar_color[0] == 1) {
                //                        bar.setColor(BarColor.GREEN);
                //                    } else {
                //                    }
                //                } else if (userSecondPlayer.getMsgBossBar() == 0) {
                //                    bar.setVisible(false);
                //                    bar.removePlayer(secondPlayer.getPlayer());
                //                }
                //            } else if (userSecondPlayer.getMsgBossBar() == 0) {
                //                bar.removePlayer(secondPlayer.getPlayer());
                //            }
                //        }
                //    }, 0, 2);
                //}
            }
        }
    }
}
