package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.List;

public class FlyCommand extends Command {

    public FlyCommand() {
        super("fly", "/fly <nick, szybkość> <nick>", "");
        setPermission("core.command.fly");
        setOnlyPlayer(true);
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            p.setAllowFlight(!p.getAllowFlight());
            Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + (p.getAllowFlight() ? "&aPomyślnie włączono latanie" : "&cPomyślnie wyłączono latanie"));
        }

        Player p2;
        int speed;
        if (args.length == 1) {
            if (!p.hasPermission("core.command.admin")) {
                p.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.admin&8) &8<<"));
                return;
            }
            if (Api.isInt(args[0])) {
                try {
                    speed = Integer.parseInt(args[0]);
                    p.setFlySpeed((float) speed / 10.0F);
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aUstawiłeś prędkość latanaia na &e" + speed);
                } catch (IllegalArgumentException var9) {
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cMusisz podać pomiędzy &e1 - 10");
                }
            } else {
                p2 = Bukkit.getPlayer(args[0]);
                if (p2 == null) {
                    offlinePlayer();
                    return;
                } else {
                    p2.setAllowFlight(!p2.getAllowFlight());
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + (p.getAllowFlight() ? "&aPomyślnie włączono latanie dla &e" + p2.getName() : "&cPomyślnie wyłączono latanie dla &e" + p2.getName()));
                    Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + (p.getAllowFlight() ? "&aWłączono latanie przez &e" + p.getName() : "&cWyłączono laatanie przez &e" + p.getName()));
                }
            }
        }

        if (args.length == 2) {
            if (Api.isInt(args[1])) {
                try {
                    p2 = Bukkit.getPlayer(args[0]);
                    if (p2 == null) {
                        offlinePlayer();
                        return;
                    } else {
                        speed = Integer.parseInt(args[1]);
                        p2.setFlySpeed((float) speed / 10.0F);
                        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aUstawiłeś prędkość latania na &e" + speed + " &adla &e" + p2.getName());
                        Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + "&aTwoja prędkość lataniaa została zmieniona na &e" + speed + " &aprzez &e" + p.getName());
                    }
                } catch (IllegalArgumentException var8) {
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cMusisz podać pomiędzy &e1 - 10");
                }
            } else {
                p2 = Bukkit.getPlayer(args[0]);
                if (p2 == null) {
                    offlinePlayer();
                    return;
                } else {
                    p2.setAllowFlight(!p2.getAllowFlight());
                    Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + (p.getAllowFlight() ? "&aPomyślnie włączono latanie dla &e" + p2.getName() : "&cPomyślnie wyłączono latanie dla &e" + p2.getName()));
                    Api.sendMessage(p2, Main.pluginConfig.getMessages().getPrefix() + (p.getAllowFlight() ? "&aWłączono latanie przez &e" + p.getName() : "&cWyłączono laatanie przez &e" + p.getName()));
                }
            }
        }

        if (args.length > 2) {
            wrongUsage();
        }
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        if (args.length == 2) return Collections.singletonList("[players]");
        return null;
    }

}

