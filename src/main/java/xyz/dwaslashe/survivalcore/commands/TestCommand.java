package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.LocationApi;

import java.util.List;

public class TestCommand extends Command {

    public TestCommand() {
        super("test", "/test", "");
        setPermission("core.command.test");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        Player player = (Player)s;
        World world = Bukkit.getWorld("world");
        Location loc = LocationApi.getRandomLocation(world);
        player.teleport(loc);
    }
}
