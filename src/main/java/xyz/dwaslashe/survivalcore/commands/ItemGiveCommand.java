package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.model.impl.UserImpl;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemGiveCommand extends Command {

    public ItemGiveCommand() {
        super("itemgive", "/itemgive <id> <gracz>", "");
        setPermission("core.command.itemgive");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("0", "1"), args[0]);
        else if (args.length == 2) return Collections.singletonList("[players]");
        return null;
    }
    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        if(args.length == 0){
            wrongUsage();
        } else if(args.length == 2){
            Integer id;
            try {
                id = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                wrongUsage();
                return;
            }
            Main.getPlugin().getItemCache().getItem(id).ifPresentOrElse(customItem -> {
                Main.getPlugin().getUserCache().getOnline(args[1]).ifPresentOrElse(user -> {
                    customItem.give(((UserImpl)user).getPlayer());
                    //((UserImpl) user).getPlayer().sendMessage("Otrzymales przedmiot o id " + customItem.id());
                    Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie dano użytkownikowi &e" + user.name() + " &aprzedmiot o id &e" + customItem.id());
                }, () -> offlinePlayer());
            }, () -> Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefix() + "&cItem o tym &eid &cnie istnieje"));
        } else sender.sendMessage(getUsage());
    }
}
