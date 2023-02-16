package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.List;

public class AdmitsCommand extends Command {
    public AdmitsCommand() {
        super("przyznajesie", "/przyznajesie", "");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length >= 0) {
            if (CheckCommand.checks.contains(p.getName())) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 2d przyznanie sie do uzywania niedozwolonego oprogramowania");
                CheckCommand.checks.remove(p.getName());
            } else {
                Api.sendMessage(p, Main.pluginConfig.getMessages().getPrefix() + "&cNie jestes sprawdzany");
            }
        }
    }
}
