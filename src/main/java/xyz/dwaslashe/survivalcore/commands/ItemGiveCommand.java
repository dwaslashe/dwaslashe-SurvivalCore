package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
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
            Player p2 = Bukkit.getPlayer(args[1]);

            Integer id;
            try {
                id = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                wrongUsage();
                return;
            }

            Main.getPlugin().getItemCache().getItem(id).ifPresentOrElse(customItem -> {
                customItem.give(p2);
                Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie dano użytkownikowi &#fcb419" + p2.getName() + " &#4cf739przedmiot o id &#fcb419" + customItem.id());
            }, () -> Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Item o tym &#fcb419id &#fc2419nie istnieje"));
        } else sender.sendMessage(getUsage());
    }
}
