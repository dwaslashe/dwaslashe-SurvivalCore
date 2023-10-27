package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import java.util.List;

public class TicketCommand extends Command {
    public TicketCommand() {
        super("ticket", "/ticket", "");
        setPermission("core.command.ticket");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
    }

}
