package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class CoreCommand extends Command {
    public CoreCommand() {
        super("core", "/core <author, server, reload, blockrecipes> <true, false>", "");
        setPermission("core.command.core");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("author", "server", "reload", "blockrecipes"), args[0]);
        else if (args.length == 2) return Api.startsWith(Arrays.asList("true", "false"), args[1]);
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            wrongUsage();
        } else if (args[0].equalsIgnoreCase("author")) {
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPlugin wykonany przez dwaslashe, kontakt discord #FF500Bdwaslashe");
        } else if (args[0].equalsIgnoreCase("server")) {
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aCały ram&8: &f" + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + "&8/&7" + (Runtime.getRuntime().maxMemory() / 1024 / 1024));
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aUżyty ram&8: &f" + ((Runtime.getRuntime().totalMemory() / 1024 / 1024) - (Runtime.getRuntime().freeMemory() / 1024 / 1024)));
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aNie użyty ram&8: &f" + (Runtime.getRuntime().freeMemory() / 1024 / 1024));
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aProcesory&8: &f" + (Runtime.getRuntime().availableProcessors()));
        } else if (args[0].equalsIgnoreCase("reload")) {
            Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przeładowano Plugin!");
            Main.pluginConfig.load();
            Main.pluginCommands.load();
            Main.pluginRank.load();
            Main.pluginVouchers.load();
            Main.pluginVapes.load();
            Main.pluginEvents.load();
        } else if (args[0].equalsIgnoreCase("blockrecipes")) {
            if (args.length >= 2 && Api.isBoolean(args[1])) {
                Main.pluginConfig.getRecipes().setBlockMaterials(Boolean.parseBoolean(args[1]));
            } else Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aFunkcja jest: &e" + Main.pluginConfig.getRecipes().isBlockMaterials());
        }
    }
}
