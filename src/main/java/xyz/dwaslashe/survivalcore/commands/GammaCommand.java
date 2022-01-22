package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Collections;
import java.util.List;

public class GammaCommand extends Command {
    public GammaCommand() {
        super("gamma", "/gamma", "");
        setPermission("core.command.gamma");
        setOnlyPlayer(true);
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 50, false, false, false));
        Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie włączono &egamme");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }
}
