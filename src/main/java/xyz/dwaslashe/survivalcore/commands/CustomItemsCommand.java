package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.CustomItemApi;

import java.util.List;
import java.util.logging.Level;

public class CustomItemsCommand extends Command {
    public CustomItemsCommand() {
        super("customitems", "/customitems", "");
        setPermission("core.command.customitems");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            StringBuilder builder = new StringBuilder();
            for (CustomItemApi customItem : Main.getItems())
                builder.append(" ").append(customItem.getCustomModelData());
            Api.sendMessage(s, Main.pluginConfig.getMessages().getPrefix() + "&aID&8: &e" + (builder.toString().isEmpty() ? " Brak" : builder.toString()));
            return;
        }
        if (args.length != 2) {
            wrongUsage();
        }
        int id = 0;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        CustomItemApi item = getItemByID(id);
        if (item == null) {
            Api.sendMessage(s, "&cPrzedmioty o tym id nie istnieje! Liste znajdziesz pod /givemodel list");
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            offlinePlayer();
        }
        give(player, item);
    }


    void give(Player player, CustomItemApi item) {
        Api.sendMessage(player, "&7Dostales przedmiot o ID &c" + item.getCustomModelData());
        player.getInventory().addItem(new ItemStack[] { item.getItemStack() }).values().forEach(itemStack -> {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            Api.sendMessage(player, "&cZauwazono, ze nie posiadasz miejsca w ekwipunku! Przedmiot zostaje wyrzucony na ziemie..");
            Main.getPlugin().getLogger().log(Level.WARNING, "System nadawania przedmiotu zauwazyl, ze gracz \"" + player.getName() + "\" nie posiada miejsca w ewkipunku.. Przedmiot zostal zrespiony na ziemi.");
        });
        if (!this.sender.getName().equals(player.getName()))
            Api.sendMessage(player, "&7Gracz &n" + player.getName() + "&7 dostal przedmiot o ID " + item.getCustomModelData());
    }

    public static CustomItemApi getItemByID(int id) {
        for (CustomItemApi item : Main.getItems()) {
            if (item.getCustomModelData() == id)
                return item;
        }
        return null;
    }
}
