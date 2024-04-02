package xyz.dwaslashe.survivalcore.commands.managers;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.managers.CooldownManager;
import xyz.dwaslashe.survivalcore.utils.Api;
import java.util.Arrays;
import java.util.List;


public abstract class Command extends org.bukkit.command.Command {


    protected CommandSender sender;
    protected boolean onlyPlayer;
    protected boolean onlyConsole;

    public CommandSender getSender() {
        return sender;
    }

    public boolean isOnlyPlayer() {
        return onlyPlayer;
    }

    public void setOnlyPlayer(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
    }

    public boolean isOnlyConsole() {
        return onlyConsole;
    }

    public void setOnlyConsole(boolean onlyConsole) {
        this.onlyConsole = onlyConsole;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getPermission() {
        return super.getPermission();
    }

    @Override
    public void setPermission(String permission) {
        super.setPermission(permission);
    }

    @Override
    public List<String> getAliases() {
        return super.getAliases();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }

    public void wrongUsage() {
        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Poprawne użycie&8: &#fcb419{usage}".replace("{usage}", getUsage()));
    }

    public void offlinePlayer() {
        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Ten gracz jest &#fcb419nieaktywny!");
    }

    public Command(String name, String usage, String description, String... aliases){
        super(name, usage, description, Arrays.asList(aliases));
        setUsage(usage);
        setDescription(description);
    }

    @Override
    public org.bukkit.command.Command setDescription(String description) {
        return super.setDescription(description);
    }

    @Override
    public org.bukkit.command.Command setUsage(String usage) {
        return super.setUsage(usage);
    }

    @Override
    public org.bukkit.command.Command setAliases(List<String> aliases) {
        return super.setAliases(aliases);
    }

    @Override
    public boolean setLabel(String name) {
        return super.setLabel(name);
    }

    @Override
    public String getPermissionMessage() {
        return super.getPermissionMessage();
    }

    public abstract List<String> tabCompleteExecute(CommandSender sender, String[] args);

    public abstract void commandExecute(CommandSender sender, String[] args);

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        this.sender = sender;
        if (args.length > 0) {
            if (tabCompleteExecute(sender, args) != null) {
                if (hasPermission()) {
                    if (tabCompleteExecute(sender, args).contains("[players]")) {
                        return super.tabComplete(sender, alias, args);
                    } else
                        return tabCompleteExecute(sender, args);
                }
            }
        }
        return ImmutableList.of();
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        this.sender = commandSender;
        if (commandSender instanceof ConsoleCommandSender) {
            if (onlyPlayer) {
                sender.sendMessage("Komenda jest tylko dla gracza");
            } else {
                commandExecute(commandSender, strings);
            }
        } else {
            if (!hasPermission()) {
                ((Player) commandSender).sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &#FF3131Nie posiadasz uprawnień &8(&#FFC42E{permission}&8) &8<<".replace("{permission}", getPermission())));
            } else {
                commandExecute(commandSender, strings);
            }
        }
        return false;
    }

    public boolean hasPermission() {
        return getPermission() == null || sender.hasPermission("core.command.*") || sender.hasPermission(getPermission());
    }
}