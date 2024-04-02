package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.objects.Protection;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminProtectionCommand extends Command {
    public AdminProtectionCommand() {
        super("adminprotection", "/adminprotection <gracz> <czas(max 1d)>", "");
        setPermission("core.command.adminprotection");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        else if (args.length == 2) return Api.startsWith(Arrays.asList("10m", "5m", "1h"), args[1]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            Player secondPlayer = Bukkit.getPlayer(args[0]);
            if (secondPlayer != null) {
                if (!args[1].isEmpty() || (TimerApi.getTime("1d") < TimerApi.getTime(args[1]))) {
                    Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie dałeś ochrone na &#ffd56c" + args[1] + " &fᎠ &#4cf739graczowi &#fcb419" + secondPlayer.getName());

                    Protection protection = Protection.compute(secondPlayer.getUniqueId());
                    protection.setProtection(System.currentTimeMillis() + TimerApi.getTime(args[1]));
                    protection.setMaxTimeProtection(TimerApi.getTime(args[1]));

                    if (secondPlayer.isOnline()) {
                        protection.actualize(secondPlayer.getPlayer());
                        secondPlayer.getPlayer().sendTitle(Api.fixColor("&#0394fc&lOCHRONA"), Api.fixColor("&f楸 &#4cf739Twoja ochrona została włączona przez &#ffd56c" + args[1] + " &fᎠ&#4cf739! &f楸"));
                        Api.sendMessage(secondPlayer.getPlayer(), Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twoja ochrona początkowa została włączona i trwa &#ffd56c" + args[1] + " minut &fᎠ&#4cf739! Jeśli chcesz wyłączyć ochronę wpisz &#fcb419/ochrona off");
                    }
                } else wrongUsage();
            } else offlinePlayer();
        } else wrongUsage();
    }

}
