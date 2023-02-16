package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.cache.TestUserCache;
import xyz.dwaslashe.survivalcore.cache.TestWarpCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.TestUser;
import xyz.dwaslashe.survivalcore.objects.TestWarp;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class TestCommand extends Command {

    public TestCommand() {
        super("test", "/test", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {

        Player player = (Player)sender;

        TestUser user = TestUserCache.getInstance().compute(player.getUniqueId());

        if (args.length == 0) {
            TestUserCache.getInstance().compute(player.getUniqueId(), User -> {
                if (User.getDeaths() != 1) {
                    User.setDeaths(1);
                    Api.sendMessage(player, "powiodlo sie");
                } else {
                    Api.sendMessage(player, "nie powiodlo sie");
                }
            });
        } else if (args[0].equalsIgnoreCase("1")) {
            TestWarpCache.getInstance().compute("test", Warp -> {
                Warp.setLocation(player.getLocation());
                Api.sendMessage(player, "powiodlo sie warp");
            });
        } else if (args[0].equalsIgnoreCase("2")) {

            TestWarp warp = TestWarpCache.getInstance().compute("test");
            player.teleport(warp.getLocation());
            Api.sendMessage(player, "pomyslnie tepnieto" + warp.getLocation().toString());
        }
    }
}
