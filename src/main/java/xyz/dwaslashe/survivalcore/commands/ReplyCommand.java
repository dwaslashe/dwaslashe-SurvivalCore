package xyz.dwaslashe.survivalcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
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
        Player p = (Player) sender;
        if (args.length < 1) {
            wrongUsage();
        } else {
            if (args.length > 0) {
                Player p2 = MsgCommand.getLastMsg().get(p);
                if (p2 == null) {
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz komu odpisać");
                    return;
                }

                String msg = StringUtils.join(args, " ", 0, args.length);

                MsgCommand.getLastMsg().put(p, p2);
                MsgCommand.getLastMsg().put(p2, p);
                SocialSpyCommand.getList()
                       .stream()
                        .map(o -> o = Bukkit.getPlayer((String) o))
                        .filter(Objects::nonNull)
                        .forEach(po -> {
                            ((Player) po).sendMessage(Api.fixColor("&c&lSocialSPY &8[ #edc72f" + p.getName() + " &8> #edc72fTY &8] &8» #edc72f" + msg));
                        });
                Api.sendMessage(p, "&8[ #edc72fTY &8> #edc72f" + p2.getName() + " &8] &8» #edc72f" + msg);
                Api.sendMessage(p2, "&8[ #edc72f" + p.getName() + " &8> #edc72fTY &8] &8» #edc72f" + msg);
                BossBar bar = Bukkit.createBossBar(Api.fixColor("&8[ #edc72f" + p.getName() + " &8> #edc72fTY &8] &8» #edc72f" + msg), BarColor.YELLOW, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC  );
                bar.addPlayer(p2);
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
                                    bar.setColor(BarColor.YELLOW);
                                } else {
                                }
                            } else {
                                bar.setVisible(false);
                                bar.removePlayer(p2.getPlayer());
                            }
                        } else {
                            bar.removePlayer(p2.getPlayer());
                        }
                    }
                }, 0, 2);
            }
        }
    }
}
