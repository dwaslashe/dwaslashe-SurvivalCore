package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class DayCommand extends Command {
    public DayCommand() {
        super("day", "/day", "", "dzien");
        setPermission("core.command.day");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        if (!(s instanceof Player)) {
            Bukkit.getWorld("world").setTime(0);
            Api.sendMessage(s, "&#4cf739Pomyślnie zmieniłeś pogode");
        } else {
            final Player p = (Player) s;
            if (CooldownManager.checkDelay(p) == true) {
                return;
            }
            CooldownManager.addCooldown(p, "10m");
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zmieniłeś pogode");
            Bukkit.getWorld("world").setTime(0);
        }
    }

}
